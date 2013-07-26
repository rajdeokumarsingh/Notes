    
// get int from binary data
byte[] tmp = new byte[3]; // MSB, big endian; if LSB, little endian, need to reverse the byte array
System.arraycopy(allContent, sgddEndPos, tmp, 0, tmp.length);
BigInteger bi = new BigInteger(tmp);
System.out.println("sgfNum: " + bi.intValue()); 
