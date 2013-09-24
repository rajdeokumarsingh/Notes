/*
 RDS Surveyor -- RDS decoder, analyzer and monitor tool and library.
 For more information see
   http://www.jacquet80.eu/
   http://rds-surveyor.sourceforge.net/

 Copyright (c) 2009, 2010 Christophe Jacquet

 This file is part of RDS Surveyor.

 RDS Surveyor is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 RDS Surveyor is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser Public License for more details.

 You should have received a copy of the GNU Lesser Public License
 along with RDS Surveyor.  If not, see <http://www.gnu.org/licenses/>.

*/

package rds.input;

import java.io.IOException;

import rds.input.group.GroupEvent;
import rds.input.group.GroupReaderEvent;
import rds.log.RealTime;


public class USBFMRadioGroupReader implements GroupReader {
    public native byte init();
    public native int getFrequency();
    public native void setFrequency(int frequency);
    public native void seek(boolean seekUp);
    public native void tune(boolean tuneUp);
    public native int getSignal();
    private native short[] getRDSRegisters();


    static {
        //System.out.println("Path: " + System.getProperty("java.library.path"));
        System.loadLibrary("USBRadio");
    }

    public static void main(String[] args) {


        USBFMRadioGroupReader radio = new USBFMRadioGroupReader();

        System.out.println("init: " + radio.init());
        System.out.println("freq: " + radio.getFrequency());
        radio.setFrequency(89900);
        System.out.println("freq: " + radio.getFrequency());


        while(true) {
            short[] res = radio.getRDSRegisters();
            if(res == null) continue;
            if((res[10] & 0x8000) == 0) continue;  // RSSI.RDS_Receive
            for(short s : res) {
                System.out.print(Integer.toHexString((s>>12) & 0xF));
                System.out.print(Integer.toHexString((s>>8) & 0xF));
                System.out.print(Integer.toHexString((s>>4) & 0xF));
                System.out.print(Integer.toHexString(s & 0xF) + " ");
            }
            System.out.println();
        }
    }
    @Override
    public GroupReaderEvent getGroup() throws IOException {
        while(true) {
            short[] res = getRDSRegisters();
            if(res == null) continue;
            if((res[10] & 0x8000) == 0) continue;  // RSSI.RDS_Receive

            return new GroupEvent(new RealTime(), new int[] {
                    0xFFFF & ((int)res[12]),
                    0xFFFF & ((int)res[13]),
                    0xFFFF & ((int)res[14]),
                    0xFFFF & ((int)res[15]) }, false);
        }
    }
}
