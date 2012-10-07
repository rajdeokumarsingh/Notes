

// To seperate a text line, use
-  StringTokenizer.java (/home/jiangrui/android/install/android2.1-r2.bk/dalvik/libcore/luni/src/main/java/java/util)
|-   package
|-   method
||     StringTokenizer [StringTokenizer]
||     StringTokenizer [StringTokenizer]
||     StringTokenizer [StringTokenizer]
||     countTokens [StringTokenizer]
||     hasMoreElements [StringTokenizer]
||     hasMoreTokens [StringTokenizer]
||     nextElement [StringTokenizer]
||     nextToken [StringTokenizer]
||     nextToken [StringTokenizer]

StringTokenizer st = new StringTokenizer(line, ";");

while(st.hasMoreTokens()) {
    String token = st.nextToken();
    ...
}

// StringBuilder should be used in single thread
// StringBuffer could be used in multiple thread
 StringBuilder.java (/home/jiangrui/android/install/android2.1-r2.bk/dalvik/libcore/luni/src/main/java/java/lang)
 |-   package
 ||     java.lang
 |-   method
 ||     StringBuilder [StringBuilder]
 ||     append [StringBuilder]
 ||     appendCodePoint [StringBuilder] 
 ||     delete [StringBuilder]
 ||     deleteCharAt [StringBuilder]
 ||     insert [StringBuilder]
 ||     replace [StringBuilder]
 ||     reverse [StringBuilder]
 ||     toString [StringBuilder]
 ||     readObject [StringBuilder]
 ||     writeObject [StringBuilder]

