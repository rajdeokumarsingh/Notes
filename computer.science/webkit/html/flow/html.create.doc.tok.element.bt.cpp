
create tokenizer
create element
create document

(gdb) c
Continuing.

0x3177c8, qName=@0x4556e964, createdByParser=<value optimized out>)
    at external/webkit/WebCore/dom/Document.cpp:887
887	        LOG_WML();


(gdb) c
Program received signal SIGINT, Interrupt.
[Switching to Thread 375]
__futex_syscall3 () at bionic/libc/arch-arm/bionic/atomics_arm.S:182
182	    ldmia   sp!, {r4, r7}
Current language:  auto; currently asm
(gdb) b Document.cpp:883
Note: breakpoint 1 also set at pc 0xa84bea5e.
Breakpoint 3 at 0xa84bea5e: file external/webkit/WebCore/dom/Document.cpp, line 883.
(gdb) b Document.cpp:887
Note: breakpoint 2 also set at pc 0xa84beaa4.
Breakpoint 4 at 0xa84beaa4: file external/webkit/WebCore/dom/Document.cpp, line 887.
(gdb) b DOMImplementation.cpp:391
No line 391 in file "external/webkit/WebCore/dom/DOMImplementation.cpp".
(gdb) c
Continuing.

[Switching to Thread 386]

0x38e710, qName=@0x4556e964, createdByParser=<value optimized out>)
    at external/webkit/WebCore/dom/Document.cpp:887
887	        LOG_WML();
(gdb) bt
#0  WebCore::Document::createElement (this=0x38e710, qName=@0x4556e964, createdByParser=<value optimized out>)
    at external/webkit/WebCore/dom/Document.cpp:887

#1  0xa84dc158 in WebCore::XMLTokenizer::startElementNs (this=0x459228, xmlLocalName=<value optimized out>, 0, 0x0) at external/webkit/WebCore/dom/XMLTokenizerLibxml2.cpp:787 

0xcc <Address 0xcc out of bounds>, 
    prefix=0xa874cab8 "\031UL�1UL���1�!UL�%UL�)UL�-UL��\235L�\021�L�ɮL�M�K�m�L���]�!�]���L���L�\005�1�\021�1�!�K�\021\227L�\031\227L�UWK�-fK��JK�\225LK��FK�9IK�AYK��\227L�i]4�ɱ1�ͱ1�m]4�ѱ1�ձ1�ٱ1�ݱ1���1���1�\205\235L��8K�q]4�u]4���1���1�q\233L�5<K��;K�Y;K�A�L�"..., 

write data:
0x0) at external/webkit/WebCore/dom/XMLTokenizerLibxml2.cpp:1111
#3  0xa85f6e38 in xmlParseStartTag2 (ctxt=0x38f030, pref=<value optimized out>, URI=<value optimized out>, tlen=<value optimized out>)
    at external/libxml2/parser.c:9031
#4  0xa85f9e7e in xmlParseTryOrFinish (ctxt=0x38f030, terminate=0) at external/libxml2/parser.c:10747
#5  0xa85faabe in xmlParseChunk (ctxt=0x38f030, chunk=0x43d4d2 "", size=4323616, terminate=<value optimized out>)
    at external/libxml2/parser.c:11480

#6  0xa84dca3c in WebCore::XMLTokenizer::doWrite (this=0x459228, parseString=@0x4556ebc0)
    at external/webkit/WebCore/dom/XMLTokenizerLibxml2.cpp:662
#7  0xa85c50e2 in WebCore::XMLTokenizer::write (this=0x459228, s=@0x4556ebf0) at external/webkit/WebCore/dom/XMLTokenizer.cpp:139
#8  0xa83556fc in WebCore::FrameLoader::write (this=<value optimized out>, str=<value optimized out>, len=<value optimized out>, 
    flush=<value optimized out>) at external/webkit/WebCore/loader/FrameLoader.cpp:960
#9  0xa8355728 in WebCore::FrameLoader::addData (this=0x1, bytes=0xcc <Address 0xcc out of bounds>, length=-1468740936)
    at external/webkit/WebCore/loader/FrameLoader.cpp:1527
#10 0xa8433352 in android::FrameLoaderClientAndroid::committedLoad (this=0x2700e0, loader=0x31df60, 
    data=0x43e9e0c0 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.2//EN\" \"http://www.wapforum.org/DTD/wml12.dtd\">\r\n<wml>\r\n  <card>\r\n    <p>\r\n      欢��\216使�\224�WAP��\213��\225系��\237<"..., length=546)
    at external/webkit/WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp:699
#11 0xa8352c74 in WebCore::FrameLoader::committedLoad (this=<value optimized out>, loader=0xcc, 
    data=0xa874cab8 "\031UL�1UL���1�!UL�%UL�)UL�-UL��\235L�\021�L�ɮL�M�K�m�L���]�!�]���L���L�\005�1�\021�1�!�K�\021\227L�\031\227L�UWK�-fK��JK�\225LK��FK�9IK�AYK��\227L�i]4�ɱ1�ͱ1�m]4�ѱ1�ձ1�ٱ1�ݱ1���1���1�\205\235L��8K�q]4�u]4���1���1�q\233L�5<K��;K�Y;K�A�L�"..., 
    length=-1470237923) at external/webkit/WebCore/loader/FrameLoader.cpp:3351
#12 0xa850ac9c in WebCore::DocumentLoader::commitLoad (this=0x31df60, 
    data=0x43e9e0c0 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.2//EN\" \"http://www.wapforum.org/DTD/wml12.dtd\">\r\n<wml>\r\n  <card>\r\n    <p>\r\n      欢��\216使�\224�WAP��\213��\225系��\237<"..., length=546)
---Type <return> to continue, or q <return> to quit---
    at external/webkit/WebCore/loader/DocumentLoader.cpp:284
#13 0xa850acd0 in WebCore::DocumentLoader::receivedData (this=0x31df60, 
    data=0x43e9e0c0 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.2//EN\" \"http://www.wapforum.org/DTD/wml12.dtd\">\r\n<wml>\r\n  <card>\r\n    <p>\r\n      欢��\216使�\224�WAP��\213��\225系��\237<"..., length=546)
    at external/webkit/WebCore/loader/DocumentLoader.cpp:296
#14 0xa8354adc in WebCore::FrameLoader::receivedData (this=<value optimized out>, data=0xcc <Address 0xcc out of bounds>, 
    length=-1468740936) at external/webkit/WebCore/loader/FrameLoader.cpp:2164
#15 0xa850c64e in WebCore::MainResourceLoader::addData (this=0x20d840, 
    data=0x43e9e0c0 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.2//EN\" \"http://www.wapforum.org/DTD/wml12.dtd\">\r\n<wml>\r\n  <card>\r\n    <p>\r\n      欢��\216使�\224�WAP��\213��\225系��\237<"..., length=546, 
    allAtOnce=<value optimized out>) at external/webkit/WebCore/loader/MainResourceLoader.cpp:150
#16 0xa835ee18 in WebCore::ResourceLoader::didReceiveData (this=0x20d840, 
    data=0x43e9e0c0 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.2//EN\" \"http://www.wapforum.org/DTD/wml12.dtd\">\r\n<wml>\r\n  <card>\r\n    <p>\r\n      欢��\216使�\224�WAP��\213��\225系��\237<"..., length=546, 
    lengthReceived=<value optimized out>, allAtOnce=29) at external/webkit/WebCore/loader/ResourceLoader.cpp:259
#17 0xa850c62a in WebCore::MainResourceLoader::didReceiveData (this=0x20d840, 
    data=0x43e9e0c0 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n\r\n\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.2//EN\" \"http://www.wapforum.org/DTD/wml12.dtd\">\r\n<wml>\r\n  <card>\r\n    <p>\r\n      欢��\216使�\224�WAP��\213��\225系��\237<"..., length=546, 
    lengthReceived=546, allAtOnce=false) at external/webkit/WebCore/loader/MainResourceLoader.cpp:415
#18 0xa835e936 in WebCore::ResourceLoader::didReceiveData (this=0x1, data=<value optimized out>, length=-1470237923, lengthReceived=546)
    at external/webkit/WebCore/loader/ResourceLoader.cpp:409
#19 0xa843bff6 in android::WebCoreResourceLoader::AddData (env=0x26b098, obj=<value optimized out>, dataArray=<value optimized out>, 
    length=<value optimized out>) at external/webkit/WebKit/android/jni/WebCoreResourceLoader.cpp:219
#20 0x80213978 in dvmPlatformInvoke () at dalvik/vm/arch/arm/CallEABI.S:243
#21 0x8023de3e in dvmCallJNIMethod_general (args=<value optimized out>, pResult=<value optimized out>, method=0x411a5820, self=0x26be08)
    at dalvik/vm/Jni.c:1726
#22 0x8023721a in dvmCheckCallJNIMethod_general (args=0x1, pResult=0xcc, method=0xa874cab8, self=0xa85df31d) at dalvik/vm/CheckJni.c:136
#23 0x80218718 in dalvik_mterp () at dalvik/vm/mterp/out/InterpAsm-armv5te.S:10170
#24 0x8021e8c8 in dvmMterpStd (self=<value optimized out>, glue=0x4556ee30) at dalvik/vm/mterp/Mterp.c:109
#25 0x8021d794 in dvmInterpret (self=0x26be08, method=<value optimized out>, pResult=0x4556eed8) at dalvik/vm/interp/Interp.c:1367
#26 0x80253eee in dvmCallMethodV (self=0x26be08, method=0x4104da6c, obj=<value optimized out>, fromJni=<value optimized out>, 
    pResult=0x4556eed8, args={__ap = 0x8024825f}) at dalvik/vm/interp/Stack.c:535
#27 0x80254106 in dvmCallMethod (self=0x1, method=0xcc, obj=0xa874cab8, pResult=0x26be08) at dalvik/vm/interp/Stack.c:436
---Type <return> to continue, or q <return> to quit---q
Quit


create tokenizer
Breakpoint 11, WebCore::HTMLDocument::createTokenizer (this=0x4280c8) at external/webkit/WebCore/html/HTMLDocument.cpp:292
292    return new HTMLTokenizer(this, reportErrors);
(gdb) bt
#0  WebCore::HTMLDocument::createTokenizer (this=0x4280c8) at external/webkit/WebCore/html/HTMLDocument.cpp:292
#1  0xa84bc97a in WebCore::Document::implicitOpen (this=0x4280c8) at external/webkit/WebCore/dom/Document.cpp:1738
#2  0xa8355d2c in WebCore::FrameLoader::begin (this=0x270128, url=<value optimized out>, dispatch=<value optimized out>, 
origin=<value optimized out>) at external/webkit/WebCore/loader/FrameLoader.cpp:894
#3  0xa8355dc4 in WebCore::FrameLoader::receivedFirstData (this=0x270128) at external/webkit/WebCore/loader/FrameLoader.cpp:779
#4  0xa8355ee0 in WebCore::FrameLoader::setEncoding (this=0x270128, name=@0x4556ec7c, userChosen=false)
at external/webkit/WebCore/loader/FrameLoader.cpp:1517
#5  0xa843333a in android::FrameLoaderClientAndroid::committedLoad (this=0x2700e0, loader=0x390680, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279)
at external/webkit/WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp:696
#6  0xa8352c74 in WebCore::FrameLoader::committedLoad (this=<value optimized out>, loader=0x0, data=0xafd418dc "", length=1)
at external/webkit/WebCore/loader/FrameLoader.cpp:3351
#7  0xa850ac9c in WebCore::DocumentLoader::commitLoad (this=0x390680, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279)
at external/webkit/WebCore/loader/DocumentLoader.cpp:284
#8  0xa850acd0 in WebCore::DocumentLoader::receivedData (this=0x390680, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279)
at external/webkit/WebCore/loader/DocumentLoader.cpp:296
#9  0xa8354adc in WebCore::FrameLoader::receivedData (this=<value optimized out>, data=0x0, length=-1345054500)
at external/webkit/WebCore/loader/FrameLoader.cpp:2164
#10 0xa850c64e in WebCore::MainResourceLoader::addData (this=0x41d008, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279, 
allAtOnce=<value optimized out>) at external/webkit/WebCore/loader/MainResourceLoader.cpp:150
#11 0xa835ee18 in WebCore::ResourceLoader::didReceiveData (this=0x41d008, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279, 
lengthReceived=<value optimized out>, allAtOnce=true) at external/webkit/WebCore/loader/ResourceLoader.cpp:259
#12 0xa850c62a in WebCore::MainResourceLoader::didReceiveData (this=0x41d008, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforu---Type <return> to continue, or q <return> to quit---quit
Quit
) at external/webkit/WebCore/loader/MainResourceLoader.cpp:415
#13 0xa835e936 in WebCore::ResourceLoader::didReceiveData (this=0x4604c8, data=<value optimized out>, length=1, lengthReceived=1279)
at external/webkit/WebCore/loader/ResourceLoader.cpp:409
#14 0xa843bff6 in android::WebCoreResourceLoader::AddData (env=0x26b098, obj=<value optimized out>, dataArray=<value optimized out>, 
length=<value optimized out>) at external/webkit/WebKit/android/jni/WebCoreResourceLoader.cpp:219
#15 0x80213978 in dvmPlatformInvoke () at dalvik/vm/arch/arm/CallEABI.S:243
#16 0x8023de3e in dvmCallJNIMethod_general (args=<value optimized out>, pResult=<value optimized out>, method=0x411a5820, self=0x26be08)
at dalvik/vm/Jni.c:1726
#17 0x8023721a in dvmCheckCallJNIMethod_general (args=0x4604c8, pResult=0x0, method=0xafd418dc, self=0x1) at dalvik/vm/CheckJni.c:136
#18 0x80218718 in dalvik_mterp () at dalvik/vm/mterp/out/InterpAsm-armv5te.S:10170
#19 0x8021e8c8 in dvmMterpStd (self=<value optimized out>, glue=0x4556ee30) at dalvik/vm/mterp/Mterp.c:109
#20 0x8021d794 in dvmInterpret (self=0x26be08, method=<value optimized out>, pResult=0x4556eed8) at dalvik/vm/interp/Interp.c:1367
#21 0x80253eee in dvmCallMethodV (self=0x26be08, method=0x4104da6c, obj=<value optimized out>, fromJni=<value optimized out>, 
pResult=0x4556eed8, args={__ap = 0x8024825f}) at dalvik/vm/interp/Stack.c:535
#22 0x80254106 in dvmCallMethod (self=0x4604c8, method=0x0, obj=0xafd418dc, pResult=0x26be08) at dalvik/vm/interp/Stack.c:436
#23 0x8024825e in interpThreadStart (arg=<value optimized out>) at dalvik/vm/Thread.c:1670
#24 0xafd11040 in __thread_entry (func=0x802481bd <interpThreadStart+1>, arg=0x26be08, tls=<value optimized out>)
at bionic/libc/bionic/pthread.c:192
#25 0xafd10b24 in pthread_create (thread_out=<value optimized out>, attr=0x78, start_routine=0x802481bd <interpThreadStart+1>, arg=0x26be08)
at bionic/libc/bionic/pthread.c:328
#26 0x00000000 in ?? ()


create document
Breakpoint 12, HTMLDocument (this=0x20cfd0, frame=<value optimized out>) at external/webkit/WebCore/html/HTMLDocument.cpp:85
85    clearXMLVersion();
(gdb) bt
#0  HTMLDocument (this=0x20cfd0, frame=<value optimized out>) at external/webkit/WebCore/html/HTMLDocument.cpp:85
#1  0xa84b4f4e in WebCore::HTMLDocument::create (frame=0x270100) at external/webkit/WebCore/html/HTMLDocument.h:41
#2  0xa84b5580 in WebCore::DOMImplementation::createDocument (type=@0x270238, frame=0x270100, inViewSourceMode=<value optimized out>)
at external/webkit/WebCore/dom/DOMImplementation.cpp:329
#3  0xa8355b64 in WebCore::FrameLoader::begin (this=0x270128, url=<value optimized out>, dispatch=<value optimized out>, 
origin=<value optimized out>) at external/webkit/WebCore/loader/FrameLoader.cpp:839
#4  0xa8355dc4 in WebCore::FrameLoader::receivedFirstData (this=0x270128) at external/webkit/WebCore/loader/FrameLoader.cpp:779
#5  0xa8355ee0 in WebCore::FrameLoader::setEncoding (this=0x270128, name=@0x4556ec7c, userChosen=false)
at external/webkit/WebCore/loader/FrameLoader.cpp:1517
#6  0xa843333a in android::FrameLoaderClientAndroid::committedLoad (this=0x2700e0, loader=0x39b7b0, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279)
at external/webkit/WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp:696
#7  0xa8352c74 in WebCore::FrameLoader::committedLoad (this=<value optimized out>, loader=0x78c, data=0x788 <Address 0x788 out of bounds>, 
length=241) at external/webkit/WebCore/loader/FrameLoader.cpp:3351
#8  0xa850ac9c in WebCore::DocumentLoader::commitLoad (this=0x39b7b0, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279)
at external/webkit/WebCore/loader/DocumentLoader.cpp:284
#9  0xa850acd0 in WebCore::DocumentLoader::receivedData (this=0x39b7b0, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279)
at external/webkit/WebCore/loader/DocumentLoader.cpp:296
#10 0xa8354adc in WebCore::FrameLoader::receivedData (this=<value optimized out>, data=0x78c <Address 0x78c out of bounds>, length=1928)
at external/webkit/WebCore/loader/FrameLoader.cpp:2164
#11 0xa850c64e in WebCore::MainResourceLoader::addData (this=0x3c8ef8, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279, 
allAtOnce=<value optimized out>) at external/webkit/WebCore/loader/MainResourceLoader.cpp:150
#12 0xa835ee18 in WebCore::ResourceLoader::didReceiveData (this=0x3c8ef8, 
data=0x43e87fd8 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//ZH\" \"http://www.wapforum.org\r\n/DTD/wml_1.1.xml\">\r\n<wml>\r\n\r\n\t<head>\r\n    \t<meta http-equiv=\"Cache-Control\" conten"..., length=1279, 
lengthReceived=<value optimized out>, allAtOnce=241) at external/webkit/WebCore/loader/ResourceLoader.cpp:259
---Type <return> to continue, or q <return> to quit---q
Quit

