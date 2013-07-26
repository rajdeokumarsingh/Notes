
hardware/libhardware_legacy/include/hardware_legacy/AudioHardwareInterface.h
    class AudioHardwareInterface
        virtual status_t setParameters(const String8& keyValuePairs) 
            // add two new keys to open and close FMR sound
            "fm_on"
            "fm_off"

            // reuse key "routing" to switch audio path of the FMR to 
            // speaker or handset
            "routing"

        // add a new interface
        /** set the fmr volume. Range is between 0.0 and 1.0 */
        virtual status_t setFmVolume(float volume) 

