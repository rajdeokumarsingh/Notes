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

package rds.log;



public class GroupReceived extends LogMessage {
    private final int[] blocks;
    private final String analysis;
    private final int nbOk;

    public GroupReceived(RDSTime time, int[] blocks, int nbOk, String analysis) {
        super(time);

        this.blocks = blocks;
        this.nbOk = nbOk;
        this.analysis = analysis;
    }

    @Override
    public void accept(LogMessageVisitor visitor) {
        visitor.visit(this);
    }

    public int[] getBlocks() {
        return blocks;
    }

    public int getNbOk() {
        return nbOk;
    }

    public String getAnalysis() {
        return analysis;
    }

    public String toString(boolean includeTime) {
        StringBuilder sb = new StringBuilder();

        if(includeTime) {
            sb.append(getTime()).append(": ");
        }

        sb.append("[");

        for(int i=0; i<4; i++) {
            if(blocks[i] >= 0) sb.append(String.format("%04X", blocks[i]));
            else sb.append("----");
            if(i < 3) sb.append(' ');
        }
        sb.append("] ").append(getAnalysis());

        return sb.toString();
    }

    public String toString() {
        return toString(true);
    }

    public int getGroupType() {
        if(blocks[1] == -1) return -1;
        else return (blocks[1] >> 11) & 0x1F;
    }
}
