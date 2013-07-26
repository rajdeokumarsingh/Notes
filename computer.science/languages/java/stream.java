访问文件和目录 {
    File类
        java.io.File
        访问文件和目录
            创建，删除，重命名, 判读文件是否存在， 可读，可写, 文件长度
            获取文件信息夹中文件，目录 

        文件系统的抽象
            可实现shell中cd, ls, mkdir, rm, rename等等功能

        无法获取文件的内容
            无法实现cat, vim的功能

        FileFilter
            获取文件夹信息时的过滤器
}

理解Java的IO流 {
    流可以看成是有序的数据序列

    分类 {

        {
            输入流：
                InputStream/Reader

            输出流：
                OutputStream/Writer
        }

        {
            字节流：
                InputStream/OutputStream
                bytes
            字符流：
                Reader/Writer
                chars
        }

        {
            节点流：
                low level stream

            处理流：
                high level stream
                用于封装节点流
        }
    }
}

输入流基本方法 {
    InputStream / Reader

    int read();
    int read(byte/char[] buf);
    int read(byte/char[] buf, int offset, int len);
    
    return -1表示读取过程完毕

    void close(); // FIXME: 完成后一定要关闭资源
}

输出流基本方法 {
    OutputStream / Writer

    void write(byte/char c);
    void write(byte[]/char[] buf);
    void write(byte[]/char[] buf, int offset, int len);

    Writer类方法
    void write(String s);
    void write(String s, int offset, int len);
}


处理流基本用法







/*
    input stream
        read byte sequence

    output stream
        write byte sequence

    Reader
        read unicode(2 bytes)
    Writer
        write unicode(2 bytes)

    InputStream
        // read a byte, return -1 if stream end, block
        int read();

        int read(byte[] b);

        int read(byte[] b, int offset, int length)

        // return how many bytes can be read
        int available();

        // release resources
        void close();

        // skip n byte in the stream
        long skip(long n);

        // Resets this stream to the last marked location
        void reset()

        FileInputStream

        System.in extends InputStream

    outputStream
        // write a byte, block
        void write(int b);

        // flush all buffered data
        void flush();

        // flush and release resources 
        void close();

        void write(byte[] buffer)

        void write(byte[] buffer, int offset, int count)
*/

    int len = in.available();
    if(len>0) {
        byte[] ba = new byte[len];
        in.read(ba);
    }

./file.stream.java
./data.stream.java    
./text.stream.java

