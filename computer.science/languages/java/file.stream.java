
// create a FileInputStream 1
FileInputStream fin = new FileInputStream("/home/jiangrui/test");

// create a FileInputStream 2
File f = new File("/home/jiangrui/test");
FileInputStream fin = new FileInputStream("/home/jiangrui/test");


// read data instread of characters from stream
FileInputStream fin = new FileInputStream("/home/jiangrui/test.data");
DataInpuStream din = new DataInpuStream(fin); // DataInpuStream is a filter

double s  = din.readDouble();

// read data instread of characters from stream
DataInpuStream din = new DataInpuStream(
    new BufferedInputStream(
    new FileInputStream("/home/jr/test.dat")))


// read file content to a buffer. No need to do I/O everytime when invoking read
BufferedInputStream
    BufferedInputStream(InputStream in);
    BufferedInputStream(InputStream in, int n);

// write file content to a buffer. No need to do I/O everytime when invoking read
BufferedOutputStream

// Allows reading from and writing to a file in a random-access manner
RandomAccessFile implements DataInput, DataOutput, Closeable

    RandomAccessFile rin = new RandomAccessFile("./test.dat", "r");
    RandomAccessFile rout = new RandomAccessFile("./test.dat", "rw");

    // Gets the current position within this file. 
    long getFilePointer()

    // Moves this file's file pointer to a new position
    void seek(long pos)

    // Returns the length of this file in bytes
    long length()




