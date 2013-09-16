/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pekall.fmradio.test;

import android.os.Handler;
import android.os.Message;
import android.test.AndroidTestCase;
import android.util.Log;

import com.pekall.fmradio.FMRadioInterface;
import com.pekall.fmradio.FMRadioService;
import com.pekall.fmradio.Factory;

/**
 * Test cases for FMRadioStubInterface
 *
 */
public class FMDeviceStubTest extends AndroidTestCase {
    private static final String LOGTAG = "FMDeviceStubTest";

    private static final int FM_EVENT_CMD_DONE = 0;
    private static final int FM_EVENT_CMD_NONE = -1;

    FMRadioInterface mDevice;
    int mResult;
    int mCmd;
    int mState;

    Object mLock = new Object();

    private void waitResult() {
        synchronized (mLock) {
            try {
                mLock.wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Factory.useStubInterface(true);

        mDevice = Factory.getFMRadioInterface(new FMRadioService());
        mDevice.setResultHandler(mHandler);

        mResult = FM_EVENT_CMD_NONE;
        mCmd = FMRadioInterface.OnCmdDoneListener.FM_CMD_NONE;
        mState = FMRadioInterface.OnCmdDoneListener.FM_STATUS_FAILED;
    }

    public void test001PowerOn() {
        mDevice.powerOnChip();

        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_POWERON);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test002PowerOff() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.powerOffChip();

        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_POWEROFF);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test003OpenFMR() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();

        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_OPEN);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test003CloseFMR() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.closeFMRadio();

        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_CLOSE);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test004Mute() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.setFMMute(0); // mute
        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_SET_MUTE);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);

        mDevice.setFMMute(1); // unmute
        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_SET_MUTE);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test005GetFreq() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.getFMFreq();

        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_GET_FREQ);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test006SetFreq() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.setFMFreq(87800); // KHz

        waitResult();
        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_SET_FREQ);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test006SeekStation() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.seekFM(-1, 0); // seek backward
        waitResult();

        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_SEEK);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);

        mDevice.seekFM(-1, 1); // seek forward
        waitResult();

        assertEquals(mResult, FM_EVENT_CMD_DONE);
        assertEquals(mCmd, FMRadioInterface.OnCmdDoneListener.FM_CMD_SEEK);
        assertEquals(mState, FMRadioInterface.OnCmdDoneListener.FM_STATUS_SUCCESS);
    }

    public void test007RdsCallback() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.setRdsCallback(0);
        // TODO: Adding result message for setRdsCallback()
    }

    public void test008EnableRds() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.enableRds();
        // TODO: Adding result message for enableRds()
    }

    public void test009disableRds() {
        mDevice.powerOnChip();
        waitResult();

        mDevice.openFMRadio();
        waitResult();

        mDevice.disableRds();
        // TODO: Adding result message for disableRds()
    }

    @Override
    protected void tearDown() throws Exception {
        mDevice.closeFMRadio();
        mDevice.powerOffChip();
        super.tearDown();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.i(LOGTAG, "handle message: " + msg.what);

            mResult = msg.what;
            switch (msg.what) {
                case FM_EVENT_CMD_DONE:
                    mCmd = msg.arg1;
                    mState = msg.arg2;
                    break;
                default:
                    Log.e(LOGTAG, "Unknown message type " + msg.what);
            }

            synchronized (mLock) {
                mLock.notifyAll();
            }
            return;
        }
    };
}
