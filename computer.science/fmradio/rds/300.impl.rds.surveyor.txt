
RDSSurveyor/src/eu/jacquet80/rds/input/V4LTunerGroupReader.java

    public GroupReaderEvent getGroup() throws IOException {
        
    }

RDSSurveyor/src/eu/jacquet80/rds/core/RDS.java


RDSSurveyor/src/eu/jacquet80/rds/core/GroupLevelDecoder.java
{
    RDSSurveyor/src/eu/jacquet80/rds/core/Station.java
    {
        // 对应一个频道的RDS信息

        RDSSurveyor/src/eu/jacquet80/rds/core/Text.java
            代表一个rds tring, 如ps

        AFList
        内部类，维护一组目的跳转的频率

        RDSTime
        接口类, 用于显示不同的时间

        // channel和frequency的对应, [0, 240] <--> [875,1079]
        protected static int channelToFrequency(int channel) {
            if(channel >= 0 && channel <= 204) return 875 + channel;  
            else if(channel == 205) return -1;      // -1 = filler code
            else return 0;
        }
    }
    ^
        |
        |
        RDSSurveyor/src/eu/jacquet80/rds/core/TunedStation.java
        {
            // 包括一个Application数组
            // 可能和ODA相关
            RDSSurveyor/src/eu/jacquet80/rds/app/Application.java {
                public interface ChangeListener {
                    public void notifyChange();
                }   
            }

        }

    RDSSurveyor/src/eu/jacquet80/rds/log/Log.java {
        包括一组LogMessage和LogMessageVisitor
        增加了一个message后，所有的visitor都会被调用

        RDSSurveyor/src/eu/jacquet80/rds/log/LogMessage.java {
            protected RDSTime time;
            public abstract void accept(LogMessageVisitor visitor);

        }

        RDSSurveyor/src/eu/jacquet80/rds/log/LogMessageVisitor.java
        public interface LogMessageVisitor {
            void visit(GroupReceived groupReceived);
            void visit(EONReturn eonReturn);
            ...
        }
    }
}

RDSSurveyor/src/eu/jacquet80/rds/core/Text.java {
    代表一个rds tring, 如ps

    // FIXME: 为什么是char[], 而不是byte[]
    private final char[] currentText; // 保存当前的解码后的字符串

    private int currentFlags = 0;  // 当前的RT a/b类型的位图， 0 for A类, 1 for B类
    private int latest = -1; // 上一次的a/b flag
    public void setFlag(int abFlag) {                                         
        // set a bit corresponding to the current flag
        currentFlags |= (1 << abFlag);   
        latest = abFlag;        
    }

    // 保存获取的所有历史消息
    private final List<String> messages = new ArrayList<String>(); 
    // 历史消息的个数
    private int currentIndex = 0;

    private boolean empty; // currentText是否为空

    private int currentTicks; // 每向Text中添加字符后，currentTicks加一
                              // reset后清0

    // 记录String和其出现的频率, 用于统计出频率最高的RT
    private Map<String, Integer> tickHistory = new HashMap<String, Integer>();

    // 记录最后一次添加字符串的位置和长度
    private int latestPos = -1, latestLen = -1;


    // 使用场景
    {
        src/rds/core/TunedStation.java {
            private Text rt = new Text(64);
        }


        src/rds/core/GroupLevelDecoder.java {
            if(type == 2 && (blocksOk[2] || blocksOk[3])) {                       
                int addr = blocks[1] & 0xF;                                       
                int ab = (blocks[1]>>4) & 1;                                      
                        
                // First extract the 4 potential characters                   
                char ch1 = RDS.toChar( (blocks[2]>>8) & 0xFF);                
                char ch2 = RDS.toChar(blocks[2] & 0xFF);                      
                char ch3 = RDS.toChar( (blocks[3]>>8) & 0xFF);                
                char ch4 = RDS.toChar(blocks[3] & 0xFF);                      

                Text rt = workingStation.getRT();
                if(version == 0 && blocksOk[2] && blocksOk[3]) {              
                    rt.setChars(addr, ch1, ch2, ch3, ch4); 
                }

                rt.setFlag(ab);
            }
    }

}

RDSSurveyor/src/rds/core/RDS.java {
}

