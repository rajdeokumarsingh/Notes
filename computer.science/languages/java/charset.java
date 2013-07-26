

Charset cset = new Charset("ISO-8859-1");

// output all aliases of "ISO-8859-1"
Set<String> aliases = cset.aliases();
for(String alias : aliases)
    System.out.println(alias);


// output all supported charsets
Set<String, Charset> charsets = Charset.availableCharsets();
for(String name : charsets.keySet())
    System.out.println(name);


// use '?' for unknown character


// convert unicode to a specific charset
String str = "fdjk jfkd fjdkf fdjkf";
ByteBuffer bf = cset.encode(str);
byte[] bytes = bf.array();


// convert a specific charset to unicode 
byte[] bf = ...;
ByteBuffer bf = ByteBuffer.wrap(bf, offset, length);
CharBuffer cb = cset.decode(bf);
String str = cb.toString();



