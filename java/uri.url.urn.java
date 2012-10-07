/* URL和URI

URI:统一资源标识符，用于标识一个web资源，包含了两个部分。
    (1)URL:统一资源定位符。能够精确的定位数据的URI
    (2)URN:统一资源名称。除了URL的URI

    在java中URI和URL是分开的两个类，URI类专门用于解析，URL用于通信。

URL
    1.URI分类
        绝对和相对：
            (1)绝对URI是指有确定的协议。比如http,ftp。后面以/进行分隔
            (2)相对URI是没有scheme的。

        透明和不透明：
            (1)不透明URI是不能够被解析的URI。不透明URI是绝对URI。scheme后面的部分不是以/进行分割。

            分层和不分层：
            (1)分层是绝对透明URI或相对URI。

        所有的网页端口都是80.

URI的格式：
    [scheme:]scheme-specific-part[#fragment]
        scheme表示用的协议，可以是http\https\ftp\file等。
        scheme-specific-part是其余部分。

    进一步细分：
        [scheme:][//authority][path][?query][#fragment]

        getScheme()获得scheme;
        getSchemeSpecificPart()
        getPath()
        getAuthority()

(2)相对标识符和绝对标识符的转换
    resolve和relative函数。

    示例代码：
    任务1：取得特定网址的html代码。
    任务2：分析地址信息。
    任务3：绝对地址和相对地址转换
*/


    URI uri = new URI("http://blog.csdn.net/xiazdong?query=test&more=1#fragment.test");
    System.out.println("uri string: " + uri.toString()); 
    System.out.println("getScheme: " + uri.getScheme());  
    System.out.println("getSchemeSpecificPart: " + uri.getSchemeSpecificPart());  
    System.out.println("getAuthority: " + uri.getAuthority());  
    System.out.println("getUserInfo: " +uri.getUserInfo());  
    System.out.println("getHost: " + uri.getHost());  
    System.out.println("getPort:" + uri.getPort());  
    System.out.println("getPath:" + uri.getPath());  
    System.out.println("getQuery:" + uri.getQuery());  
    System.out.println("getFragment:" + uri.getFragment());  

    String str = "/article/details/6705033";  
    URI combined = uri.resolve(str);// 根据uri的路径把str变成绝对地址  
    System.out.println("combined uri: " + combined.toString());

    URI relative = uri.relativize(new URI(str));  
    System.out.println("relative: " + relative.toString());

    /* output
        uri string: http://blog.csdn.net/xiazdong?query=test&more=1#fragment.test
        getScheme: http
        getSchemeSpecificPart: //blog.csdn.net/xiazdong?query=test&more=1
        getAuthority: blog.csdn.net
        getUserInfo: null
        getHost: blog.csdn.net
        getPort:-1
        getPath:/xiazdong
        getQuery:query=test&more=1
        getFragment:fragment.test
        combined uri: http://blog.csdn.net/article/details/6705033
        relative: /article/details/6705033
    */



