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

package rds.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Text {
    // radiotext-related variables
    private final char[] currentText;
    private int currentFlags = 0;
    private final List<String> messages = new ArrayList<String>();
    private int latest = -1;
    private boolean empty;
    private int currentTicks;
    private Map<String, Integer> tickHistory = new HashMap<String, Integer>();
    private int currentIndex = 0;
    private int latestPos = -1, latestLen = -1;

    public Text(int size) {
        currentText = new char[size];
        reset();
    }

    public void setChars(int position, char ... characters) {
        for(int i = 0; i < characters.length; i++) {
            // ignore characters set with value 0
            // FIXME: Jiang Rui, why?
            if(characters[i] == 0) continue;

            if(currentText[position * characters.length + i] != '\0' &&
                    characters[i] != currentText[position * characters.length + i]) {
                // this is a new RT message: save the previous message...
                if(!empty) {
                    String message;

                    /*
                    if(usesFlags) {
                        StringBuffer msg = new StringBuffer("[");
                        for(int f=0; f<2; f++)
                            if((currentFlags & (1<<f)) != 0) msg.append((char)('A' + f));
                        msg.append("] ");
                        message = msg.append(toString()).toString();
                    } else */
                    message = toString();
                    android.util.Log.i("Text",
                            "got a new string: " + message);

                    messages.add(message);
                    currentIndex++;

                    Integer prev = tickHistory.get(message);
                    tickHistory.put(message, currentTicks + (prev == null ? 0 : prev));

                    // ... and reset the message buffer
                    reset();
                }
                break;
            }
        }

        setChars(currentText, position, characters);

        //System.out.println("\n*** RT=" + getRT() + ",   msgs=" + rtMessages + " ***");
    }

    public void setFlag(int abFlag) {
        currentFlags |= (1 << abFlag);   // set a bit corresponding to the current flag
        latest = abFlag;
    }

    public void reset() {
        Arrays.fill(currentText, '\0');
        empty = true;
        currentTicks = 0;
    }


    public byte[] getRawBytes() {
        if(empty) return null;

        byte[] bytes = new byte[currentText.length];
        Arrays.fill(bytes, (byte)0);

        int j = 0;
        for(; j<currentText.length; j++) {
            if(currentText[j] == 0x0D)  // carriage return
                break;

            if(currentText[j] == 0) {
                bytes[j] = 0x20;        // space
                continue;
            }
            bytes[j] = (byte)currentText[j];
        }

        if(0 == j) return null;

        byte[] result = new byte[j];
        for(int i=0; i<j;i++) {
            result[i] = (byte)(bytes[i] & 0xFF);
            android.util.Log.i("Text", "hex bytes: " +
                    Integer.toHexString(result[i]));
        }

        return result;
    }

    public String toString() {
        if(empty) return null;

        StringBuffer res = new StringBuffer();

        for(int j=0; j<currentText.length; j++) {
            if(currentText[j] == 0x0D) break;
            if(currentText[j] == 0) res.append(" ");
            else if(currentText[j] >= 32) res.append(currentText[j]);
            else res.append('<').append(Integer.toString(currentText[j], 16)).append('>');
        }

        android.util.Log.i("Text", "toString: " + res.toString());
        return res.toString();
    }

    /**
     * Returns a string representing the current string, in which the latest
     * portion has been highlighted.
     *
     * Java HTML control codes are used for this. This is intended to be used
     * in Swing components.
     *
     * @return an HTML-tagged string representing the current text
     */
    public String toStringWithHighlight() {
        if(empty) return null;

        StringBuffer sb = new StringBuffer(toString());
        while(sb.length() < 64) sb.append(' ');
        String s = sb.toString();

        StringBuilder res = new StringBuilder("<html>");
        res.append(s.substring(0, latestPos * latestLen));
        res.append("<font color=blue>");
        res.append(s.substring(latestPos * latestLen, (latestPos+1)*latestLen));
        res.append("</font>").append(s.substring((latestPos+1)*latestLen));
        res.append("</html>");

        return res.toString().replaceAll("\\s", "&nbsp;");
    }

    public int getFlags() {
        return latest;
    }

    public List<String> getPastMessages(boolean includingCurrent) {
        if(!includingCurrent || empty)
            return messages;
        else {
            List<String> l = new ArrayList<String>(messages);
            l.add(toString());
            return l;
        }
    }

    private void setChars(char[] text, int position, char ... characters) {
        for(int i=0; i<characters.length; i++) {
            if(characters[i] != 0)
                text[position * characters.length + i] = characters[i];
        }
        currentTicks++;
        empty = false;
        latestPos = position;
        latestLen = characters.length;
    }

    public boolean isComplete() {
        for(int i=0; i<currentText.length; i++) {
            // PEKALL begin:
            // if(currentText[i] == '\0') return false;
            if(currentText[i] == '\0') {
                // check whether the characters end with a "carriage return"
                for(int j=1; j<=i; j++) {
                    if(0x0d == currentText[i-j]) {
                        return true;
                    }
                }
                return false;
            }
            // PEKALL end
        }
        return true;
    }

    public String getMostFrequentText() {
        String mft = isComplete() ? toString() : "";
        int mftOcc = 0;

        for(Map.Entry<String, Integer> e : tickHistory.entrySet()) {
            if(e.getValue() > mftOcc) {
                mftOcc = e.getValue();
                mft = e.getKey();
            }
        }

        return mft;
    }

    /**
     * If the "most frequent" text is defined, return it. Otherwise, return
     * the partial text being received.
     *
     * @return the most frequent text if defined, the current text otherwise
     */
    public String getMostFrequentOrPartialText() {
        String text = getMostFrequentText();
        if(text.length() == 0) text = toString();
        if(text == null) text = "";
        return text;
    }

    public String getLatestCompleteOrPartialText() {
        if(isComplete()) {
            return toString();
        } else if(messages.size()>0) {
            return messages.get(messages.size()-1);
        } else {
            String t = toString();
            if(t != null) return t; else return "";
        }

    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
