/*
0. 总结charset的流程, 各个不同的入口，出口点
    设计测试case
*/

/*
测试case
    1. 模拟wap服务器错误, 修改http 响应头成utf-8, 访问百度

    2. 10086 四川移动

    3. www.monternet.com

    4. 邮件加载 指定的utf-8编码和邮件内容中的html meta charset不一致。 utf-8是正确的

    5. 写一个网页，主页面是gb2312字符编码, 其中一个子frame是访问不到的网页
        问题load data是否能影响其父frame的编码

    6. load一段data, 使用utf-8。 这段data中包括一个子frame， 子frame是用gb2312
        load data的时候父frame的编码，是否影响子frame的编码
*/

/* 设计
增加系统property, 设置浏览器使用的网络类型cmwap, cmnet, system(需要检查系统网络的类型)

需要添加一个接口， 区别是否是load data和http response
    isLoadingData

    //depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebCore/loader/SubstituteData.h#2
    //depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebCore/platform/network/ResourceResponseBase.cpp#2
    //depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebCore/platform/network/ResourceResponseBase.h#2
    done!


    //depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebKit/android/jni/WebCoreFrameBridge.cpp#3
    static void LoadData(JNIEnv *env, jobject obj, jstring baseUrl, jstring data,
            jstring mimeType, jstring encoding, jstring failUrl)

    //depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp#3
    static void loadDataIntoFrame(Frame* frame, KURL baseUrl, const String& url, const String& data) 


    //depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebCore/loader/DocumentLoader.cpp#2
    4, 5


编码逻辑修改:
//depot/pekall-android/platform/main4.0/WP8800/external/webkit/Source/WebCore/loader/TextResourceDecoder.cpp#5

bool TextResourceDecoder::checkForHeadCharset(const char* data, size_t len, bool& movedDataToBuffer)
    如果指定了http中的charset
        如果isLoadingData()
            忽略html meta charset
        否则(html response)
            如果cmwap, 继续检查meta charset
            否则
                忽略html meta charset
                
bool TextResourceDecoder::shouldAutoDetect()
    如果指定了http中的charset
        如果isLoadingData()
            return false; // 不要使用audo detect
        否则(html response)
            如果cmwap
                return true; // 对于cmwap，如果没有查到meta charset，还是要检查一下
            否则
                return false; 
 

*/
