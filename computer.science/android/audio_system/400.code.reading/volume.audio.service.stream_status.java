// inner class of AudioService

public class VolumeStreamState {
    private final int mStreamType;              // JR: every stream has a VolumeStreamState 

    private String mVolumeIndexSettingName;
    private String mLastAudibleVolumeIndexSettingName;
    private int mIndexMax;
    private final ConcurrentHashMap<Integer, Integer> mIndex =
        new ConcurrentHashMap<Integer, Integer>(8, 0.75f, 4);               // JR: map device and volume
    private final ConcurrentHashMap<Integer, Integer> mLastAudibleIndex =
        new ConcurrentHashMap<Integer, Integer>(8, 0.75f, 4);               // JR: map device and volume

    private ArrayList<VolumeDeathHandler> mDeathHandlers; //handles mute/solo clients death

    private VolumeStreamState(String settingName, int streamType) {

        mVolumeIndexSettingName = settingName;                              // JR: name is from System.VOLUME_SETTINGS[]
                                                                            // like volume_music, volume_fm
        mLastAudibleVolumeIndexSettingName = settingName + System.APPEND_FOR_LAST_AUDIBLE;

        mStreamType = streamType;
        mIndexMax = MAX_STREAM_VOLUME[streamType];
        AudioSystem.initStreamVolume(streamType, 0, mIndexMax);             // JR: initialize stream volume array in AudioPolicyManagerBase
        mIndexMax *= 10;

        // mDeathHandlers must be created before calling readSettings()
        mDeathHandlers = new ArrayList<VolumeDeathHandler>();

        readSettings();
    }

    public String getSettingNameForDevice(boolean lastAudible, int device) {
        String name = lastAudible ?
            mLastAudibleVolumeIndexSettingName :
            mVolumeIndexSettingName;                        // JR: name is stream name in System.VOLUME_SETTINGS, like volume_music, ...
        String suffix = AudioSystem.getDeviceName(device);  // JR: device name is like 'speaker', 'headset', ...
        if (suffix.isEmpty()) {
            return name;                                    // JR: for default device, just user stream name
        }
        return name + "_" + suffix;
    }

    public synchronized void readSettings() {
        int remainingDevices = AudioSystem.DEVICE_OUT_ALL;

        for (int i = 0; remainingDevices != 0; i++) {
            int device = (1 << i);
            if ((device & remainingDevices) == 0) {
                continue;
            }
            remainingDevices &= ~device;

            // retrieve current volume for device
            String name = getSettingNameForDevice(false /* lastAudible */, device);

            // if no volume stored for current stream and device, use default volume if default
            // device, continue otherwise
            int defaultIndex = (device == AudioSystem.DEVICE_OUT_DEFAULT) ?
                AudioManager.DEFAULT_STREAM_VOLUME[mStreamType] : -1;

            // JR: read volume settings from Settings
            int index = Settings.System.getIntForUser(
                    mContentResolver, name, defaultIndex, UserHandle.USER_CURRENT);
            if (index == -1) {
                continue;
            }

            // retrieve last audible volume for device
            name = getSettingNameForDevice(true  /* lastAudible */, device);
            // use stored last audible index if present, otherwise use current index if not 0
            // or default index
            defaultIndex = (index > 0) ?
                index : AudioManager.DEFAULT_STREAM_VOLUME[mStreamType];
            int lastAudibleIndex = Settings.System.getIntForUser(
                    mContentResolver, name, defaultIndex, UserHandle.USER_CURRENT);

            mLastAudibleIndex.put(device, getValidIndex(10 * lastAudibleIndex));

            if (muteCount() == 0) {
                mIndex.put(device, getValidIndex(10 * index));
            }
        }
    }

    public void applyDeviceVolume(int device) {
        AudioSystem.setStreamVolumeIndex(mStreamType,
                (getIndex(device, false  /* lastAudible */) + 5)/10,
                device);
    }

    public synchronized void applyAllVolumes() {
        if (DEBUG_VOL) Log.d(TAG, "applyAllVolumes()");

        // apply default volume first: by convention this will reset all
        // devices volumes in audio policy manager to the supplied value
        AudioSystem.setStreamVolumeIndex(mStreamType,
                (getIndex(AudioSystem.DEVICE_OUT_DEFAULT, false /* lastAudible */) + 5)/10,
                AudioSystem.DEVICE_OUT_DEFAULT);
        // then apply device specific volumes
        Set set = mIndex.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            int device = ((Integer)entry.getKey()).intValue();
            if (device != AudioSystem.DEVICE_OUT_DEFAULT) {
                AudioSystem.setStreamVolumeIndex(mStreamType,
                        ((Integer)entry.getValue() + 5)/10,
                        device);
            }
        }
    }

    public boolean adjustIndex(int deltaIndex, int device) {
        return setIndex(getIndex(device,
                    false  /* lastAudible */) + deltaIndex,
                device,
                true  /* lastAudible */);
    }

    public synchronized boolean setIndex(int index, int device, boolean lastAudible) {
        int oldIndex = getIndex(device, false  /* lastAudible */);
        index = getValidIndex(index);
        mIndex.put(device, getValidIndex(index));

        if (oldIndex != index) {
            if (lastAudible) {
                mLastAudibleIndex.put(device, index);
            }
            return true;
        } else {
            return false;
        }
    }

    public synchronized int getIndex(int device, boolean lastAudible) {
        ConcurrentHashMap <Integer, Integer> indexes;
        if (lastAudible) {
            indexes = mLastAudibleIndex;
        } else {
            indexes = mIndex;
        }
        Integer index = indexes.get(device);
        if (index == null) {
            if (DEBUG_VOL) Log.d(TAG, "getIndex(), got null index");
            // there is always an entry for AudioSystem.DEVICE_OUT_DEFAULT
            index = indexes.get(AudioSystem.DEVICE_OUT_DEFAULT);
        }
        return index.intValue();
    }

    public synchronized void setLastAudibleIndex(int index, int device) {
        mLastAudibleIndex.put(device, getValidIndex(index));
    }

    public synchronized void adjustLastAudibleIndex(int deltaIndex, int device) {
        setLastAudibleIndex(getIndex(device,
                    true  /* lastAudible */) + deltaIndex,
                device);
    }

    public ConcurrentHashMap <Integer, Integer> getAllIndexes(boolean lastAudible) {
        if (lastAudible) {
            return mLastAudibleIndex;
        } else {
            return mIndex;
        }
    }

    public synchronized void setAllIndexesToMax() {
        Set set = mIndex.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            entry.setValue(mIndexMax);
        }
        set = mLastAudibleIndex.entrySet();
        i = set.iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry)i.next();
            entry.setValue(mIndexMax);
        }
    }

    public synchronized void mute(IBinder cb, boolean state) {
        VolumeDeathHandler handler = getDeathHandler(cb, state);
        handler.mute(state);
    }

    public int getStreamType() {
        return mStreamType;
    }

    private class VolumeDeathHandler implements IBinder.DeathRecipient {
        private IBinder mICallback; // To be notified of client's death
        private int mMuteCount; // Number of active mutes for this client

        VolumeDeathHandler(IBinder cb) {
            mICallback = cb;
        }

        // must be called while synchronized on parent VolumeStreamState
        public void mute(boolean state) {
            if (state) {
                if (mMuteCount == 0) {
                    // Register for client death notification
                    try {
                        // mICallback can be 0 if muted by AudioService
                        if (mICallback != null) {
                            mICallback.linkToDeath(this, 0);
                        }
                        mDeathHandlers.add(this);
                        // If the stream is not yet muted by any client, set level to 0
                        if (muteCount() == 0) {
                            Set set = mIndex.entrySet();
                            Iterator i = set.iterator();
                            while (i.hasNext()) {
                                Map.Entry entry = (Map.Entry)i.next();
                                int device = ((Integer)entry.getKey()).intValue();
                                setIndex(0, device, false /* lastAudible */);
                            }
                            sendMsg(mAudioHandler,
                                    MSG_SET_ALL_VOLUMES,
                                    SENDMSG_QUEUE,
                                    0,
                                    0,
                                    VolumeStreamState.this, 0);
                        }
                    } catch (RemoteException e) {
                        // Client has died!
                        binderDied();
                        return;
                    }
                } else {
                    Log.w(TAG, "stream: "+mStreamType+" was already muted by this client");
                }
                mMuteCount++;
            } else {
                if (mMuteCount == 0) {
                    Log.e(TAG, "unexpected unmute for stream: "+mStreamType);
                } else {
                    mMuteCount--;
                    if (mMuteCount == 0) {
                        // Unregister from client death notification
                        mDeathHandlers.remove(this);
                        // mICallback can be 0 if muted by AudioService
                        if (mICallback != null) {
                            mICallback.unlinkToDeath(this, 0);
                        }
                        if (muteCount() == 0) {
                            // If the stream is not muted any more, restore its volume if
                            // ringer mode allows it
                            if (!isStreamAffectedByRingerMode(mStreamType) ||
                                    mRingerMode == AudioManager.RINGER_MODE_NORMAL) {
                                Set set = mIndex.entrySet();
                                Iterator i = set.iterator();
                                while (i.hasNext()) {
                                    Map.Entry entry = (Map.Entry)i.next();
                                    int device = ((Integer)entry.getKey()).intValue();
                                    setIndex(getIndex(device,
                                                true  /* lastAudible */),
                                            device,
                                            false  /* lastAudible */);
                                }
                                sendMsg(mAudioHandler,
                                        MSG_SET_ALL_VOLUMES,
                                        SENDMSG_QUEUE,
                                        0,
                                        0,
                                        VolumeStreamState.this, 0);
                                    }
                        }
                    }
                }
            }
        }

        public void binderDied() {
            if (mMuteCount != 0) {
                // Reset all active mute requests from this client.
                mMuteCount = 1;
                mute(false);
            }
        }
    }

    private synchronized int muteCount() {
        int count = 0;
        int size = mDeathHandlers.size();
        for (int i = 0; i < size; i++) {
            count += mDeathHandlers.get(i).mMuteCount;
        }
        return count;
    }

    // only called by mute() which is already synchronized
    private VolumeDeathHandler getDeathHandler(IBinder cb, boolean state) {
        VolumeDeathHandler handler;
        int size = mDeathHandlers.size();
        for (int i = 0; i < size; i++) {
            handler = mDeathHandlers.get(i);
            if (cb == handler.mICallback) {
                return handler;
            }
        }
        // If this is the first mute request for this client, create a new
        // client death handler. Otherwise, it is an out of sequence unmute request.
        if (state) {
            handler = new VolumeDeathHandler(cb);
        } else {
            Log.w(TAG, "stream was not muted by this client");
            handler = null;
        }
        return handler;
    }
}

