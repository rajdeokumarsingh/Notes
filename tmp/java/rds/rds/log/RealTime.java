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

import java.text.SimpleDateFormat;
import java.util.Date;

public class RealTime implements RDSTime {
    private final Date time;

    private final static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH.mm.ss");
    private static final SimpleDateFormat LONG_TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    @Override
    public Date getRealTime(RDSTime refStreamTime, Date refDate) {
        // TODO Auto-generated method stub
        return null;
    }

    public RealTime(Date time) {
        this.time = time;
    }

    public RealTime() {
        this.time = new Date();
    }

    public String toString() {
        return TIME_FORMAT.format(this.time);
    }

    public String toLongString() {
        return LONG_TIME_FORMAT.format(this.time);
    }
}