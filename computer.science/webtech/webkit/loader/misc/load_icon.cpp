Breakpoint 1, WebCore::IconLoader::create (frame=0x2a1990f0) at external/webkit/Source/WebCore/loader/icon/IconLoader.cpp:65
65	}
(gdb) bt
#0  WebCore::IconLoader::create (frame=0x2a1990f0) at external/webkit/Source/WebCore/loader/icon/IconLoader.cpp:65
#1  0x49889054 in continueIconLoadWithDecision (iconLoadDecision=<optimized out>, this=0x2a199130)
    at external/webkit/Source/WebCore/loader/FrameLoader.cpp:793
#2  WebCore::FrameLoader::continueIconLoadWithDecision (this=0x2a199130, iconLoadDecision=<optimized out>)
    at external/webkit/Source/WebCore/loader/FrameLoader.cpp:753
#3  0x4988913e in WebCore::FrameLoader::startIconLoader (this=0x2a199130) at external/webkit/Source/WebCore/loader/FrameLoader.cpp:750
#4  0x49a2cde8 in implicitClose (this=0x2a365f28) at external/webkit/Source/WebCore/dom/Document.cpp:2112
#5  WebCore::Document::implicitClose (this=0x2a365f28) at external/webkit/Source/WebCore/dom/Document.cpp:2078
#6  0x4988a79a in checkCompleted (this=0x2a199130) at external/webkit/Source/WebCore/loader/FrameLoader.cpp:885
#7  WebCore::FrameLoader::checkCompleted (this=0x2a199130) at external/webkit/Source/WebCore/loader/FrameLoader.cpp:853
#8  0x4988a84e in WebCore::FrameLoader::finishedParsing (this=0x2a199130) at external/webkit/Source/WebCore/loader/FrameLoader.cpp:819
#9  0x49a2c7ee in WebCore::Document::finishedParsing (this=0x2a365f28) at external/webkit/Source/WebCore/dom/Document.cpp:4289
#10 0x49a7197e in WebCore::HTMLTreeBuilder::finished (this=<optimized out>)
    at external/webkit/Source/WebCore/html/parser/HTMLTreeBuilder.cpp:2821
#11 0x49a6a1ba in prepareToStopParsing (this=0x2a34eb30) at external/webkit/Source/WebCore/html/parser/HTMLDocumentParser.cpp:151
#12 WebCore::HTMLDocumentParser::prepareToStopParsing (this=0x2a34eb30)
    at external/webkit/Source/WebCore/html/parser/HTMLDocumentParser.cpp:130
#13 0x49a69820 in WebCore::HTMLDocumentParser::attemptToEnd (this=0x2a34eb30)
    at external/webkit/Source/WebCore/html/parser/HTMLDocumentParser.cpp:399
#14 0x49a69866 in WebCore::HTMLDocumentParser::finish (this=0x2a34eb30)
    at external/webkit/Source/WebCore/html/parser/HTMLDocumentParser.cpp:427
#15 0x49a28a1e in WebCore::Document::finishParsing (this=<optimized out>) at external/webkit/Source/WebCore/dom/Document.cpp:2287
#16 0x49a7be80 in WebCore::DocumentWriter::endIfNotLoadingMainResource (this=0x2a2fdbdc)
    at external/webkit/Source/WebCore/loader/DocumentWriter.cpp:256
#17 0x4988a6ce in WebCore::FrameLoader::finishedLoading (this=0x2a199130) at external/webkit/Source/WebCore/loader/FrameLoader.cpp:2276
#18 0x49a7c746 in WebCore::MainResourceLoader::didFinishLoading (this=0x2a2fc988, finishTime=0)
    at external/webkit/Source/WebCore/loader/MainResourceLoader.cpp:555
#19 0x49892992 in WebCore::ResourceLoader::didFinishLoading (this=<optimized out>, finishTime=<optimized out>)
    at external/webkit/Source/WebCore/loader/ResourceLoader.cpp:523
#20 0x499858ac in android::WebUrlLoaderClient::didFinishLoading (this=0x2a330818)
    at external/webkit/Source/WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp:502
#21 0x499839f4 in DispatchToMethod<android::FlushSemaphore, void (android::FlushSemaphore::*)()> (method=
    (void (android::FlushSemaphore::*)(android::FlushSemaphore * const)) 0x49985871 <android::WebUrlLoaderClient::didFinishLoading()>, 
    obj=<optimized out>, arg=<optimized out>) at external/chromium/base/tuple.h:541
#22 RunnableMethod<android::FlushSemaphore, void (android::FlushSemaphore::*)(), Tuple0>::Run (this=<optimized out>)
    at external/chromium/base/task.h:332
#23 0x499848b2 in android::(anonymous namespace)::RunTask (v=<optimized out>)
    at external/webkit/Source/WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp:373
---Type <return> to continue, or q <return> to quit---
#24 0x498414ba in WTF::dispatchFunctionsFromMainThread () at external/webkit/Source/JavaScriptCore/wtf/MainThread.cpp:155
#25 0x4998bc7a in android::JavaSharedClient::ServiceFunctionPtrQueue () at external/webkit/Source/WebKit/android/jni/JavaSharedClient.cpp:134
#26 0x4074e294 in dvmPlatformInvoke () at dalvik/vm/arch/arm/CallEABI.S:258
#27 0x4077d1fc in dvmCallJNIMethod (args=0x4ae90f28, pResult=0x2a195108, method=0x44e25f68, self=0x2a1950f8) at dalvik/vm/Jni.cpp:1155
#28 0x40768b48 in dvmCheckCallJNIMethod (args=<optimized out>, pResult=0x2a195108, method=0x44e25f68, self=0x2a1950f8)
    at dalvik/vm/CheckJni.cpp:145
#29 0x407576a4 in dalvik_mterp () at dalvik/vm/mterp/out/InterpAsm-armv7-a.S:16240
#30 0x4075b544 in dvmInterpret (self=0x2a1950f8, method=<optimized out>, pResult=0x4af90ea8) at dalvik/vm/interp/Interp.cpp:1956
#31 0x4078f9d8 in dvmCallMethodV (self=0x2a1950f8, method=0x44c4fd40, obj=<optimized out>, fromJni=<optimized out>, pResult=0x4af90ea8, 
    args=...) at dalvik/vm/interp/Stack.cpp:526
#32 0x4078fa02 in dvmCallMethod (self=<optimized out>, method=<optimized out>, obj=<optimized out>, pResult=0x4af90ea8)
    at dalvik/vm/interp/Stack.cpp:429
#33 0x4078457a in interpThreadStart (arg=0x2a1950f8) at dalvik/vm/Thread.cpp:1538
#34 0x4002e3bc in __thread_entry (func=0x407844d9 <interpThreadStart(void*)>, arg=0x2a1950f8, tls=0x4af90f00)
    at bionic/libc/bionic/pthread.c:204
#35 0x4002dab4 in pthread_create (thread_out=0x4af90f00, attr=0xbee99790, start_routine=0x407844d9 <interpThreadStart(void*)>, 
    arg=<optimized out>) at bionic/libc/bionic/pthread.c:348
#36 0x2a0e1e18 in ?? ()
Cannot access memory at address 0x1
#37 0x2a0e1e18 in ?? ()
Cannot access memory at address 0x1
Backtrace stopped: previous frame identical to this frame (corrupt stack?)
(gdb) c
Continuing.

