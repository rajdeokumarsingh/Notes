
探索Android中的Parcel机制

http://blog.csdn.net/caowenbin/article/details/6532217

一．先从Serialize说起
    我们都知道JAVA中的Serialize机制，译成串行化、序列化……，其作用是能将数据对象存入字节流当中，
        在需要时重新生成对象。主要应用是利用外部存储设备保存对象状态，以及通过网络传输对象等。

二．Android中的新的序列化机制
    在Android系统中，定位为针对内存受限的设备，因此对性能要求更高，
        另外系统中采用了新的IPC（进程间通信）机制，必然要求使用性能更出色的对象传输方式。
        在这样的环境下，Parcel被设计出来，其定位就是轻量级的高效的对象序列化和反序列化机制。

        比如一个activity需要传递一个对象到另外activity，可以将该对象继承Parcel类，
        对象需要实现
        public class MyColor implements Parcelable
            public void writeToParcel(Parcel dest, int flags) 
                // 将对象内部输入写到dest中
                dest.writeInt(networkId); 
                dest.writeString(SSID);
                ...

            public static final Creator<WifiConfiguration> CREATOR =
                new Creator<WifiConfiguration>() { 
                    public WifiConfiguration createFromParcel(Parcel in) {
                        WifiConfiguration config = new WifiConfiguration();
                        config.networkId = in.readInt();
                        config.SSID = in.readString();
                        ...
                        return config;
                    }
                public WifiConfiguration[] newArray(int size) {
                    return new WifiConfiguration[size];
                }
            }

        发送activity:
            MyColor color=new MyColor(); // 继承Parcel类, 实现其方法
            color.setColor(Color.RED);  

            Intent intent=new Intent();  
            intent.setClass(this, SubActivity.class);  
            intent.putExtra("MyColor", color);  
            startActivityForResult(intent,SUB_ACTIVITY); 

        接收方activity:
            Intent intent=getIntent();  
            if (intent!=null){  
                if (intent.hasExtra("MyColor")){  
                    // 反序列化后是一个新的MyColor对象
                    MyColor color =intent.getParcelableExtra("MyColor");  
                    findViewById(R.id.text).setBackgroundColor(color.getColor());  
                }  
            }  

o
