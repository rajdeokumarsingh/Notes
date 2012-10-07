
// get system new line
System.getProperty("line.separator");


// read or write unicode instead of characters

-  InputStreamReader.java 
|-   package                  
||     java.io                
|                             
|-   class                    
||     InputStreamReader      
|-   method                   
||     InputStreamReader [InputStreamReader]
||     InputStreamReader [InputStreamReader]
||     InputStreamReader [InputStreamReader]
||     InputStreamReader [InputStreamReader]
||     close [InputStreamReader]
||     getEncoding [InputStreamReader]
    /* Reads a single character(2 bytes) from this reader and returns it as an integer
    * with the two higher-order bytes set to 0*/
    int read()
||     read [InputStreamReader]        
||     read [InputStreamReader]            
||     isOpen [InputStreamReader]      
||     ready [InputStreamReader]    

// convert keyboard input to unicode
// use system encoding for default
InputStreamReader inr = new InputStreamReader(System.in);
int uchar = inr.read();

// specify the encoding
InputStreamReader inr = new InputStreamReader(
    new FileInputStream("test.dat"), "ISO8859_5");
int uchar = inr.read();

FileWriter fw = new FileWriter("test.dat");
FileWriter fw = new FileWriter(new FileOutputStream("test.dat"));

binary output
    DataOutputStream
Text output
    PrintWriter

// read text
BufferedReader in = new BufferedReader(
    new FileReader("test.dat")); // FileReader can convert a byte to a unicode character
String line = in.readLine();

// read 








