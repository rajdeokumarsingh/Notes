

// HTML 文档对象模型（HTML Document Object Model）
    // 通过 JavaScript，重构整个 HTML 文档。
    // 可以添加、移除、改变或重排页面上的项目


// DOM 被分为不同的部分 （核心、XML及HTML）和级别（DOM Level 1/2/3）
    // Core DOM: 定义了一套标准的针对任何结构化文档的对象
    // XML DOM: 定义了一套标准的针对 XML 文档的对象
    // HTML DOM 定义了一套标准的针对 HTML 文档的对象。

    
/* 节点
    根据 DOM，HTML 文档中的每个成分都是一个节点。
    DOM 是这样规定的：
        整个文档是一个文档节点
        每个 HTML 标签是一个元素节点
        包含在 HTML 元素中的文本是文本节点
        每一个 HTML 属性是一个属性节点
        注释属于注释节点 */

/*查找并访问节点
    通过使用 getElementById() 和 getElementsByTagName() 方法
    通过使用一个元素节点的 parentNode、firstChild 以及 lastChild 属性 */
    document.getElementById("id_name"); 

    document.getElementsByTagName("tag_name"); 
        // getElementsByTagName() 可被用于任何的 HTML 元素：
        document.getElementById('ID').getElementsByTagName("标签名称"); 

    // 对firstChild最普遍的用法是访问某个元素的文本： 
    var text=x.firstChild.nodeValue; 

    // parentNode 属性常被用来改变文档的结构。
    var x=document.getElementById("maindiv");
    x.parentNode.removeChild(x); 

    /*根节点
        有两种特殊的文档属性可用来访问根节点：*/
    // 返回存在于 XML 以及 HTML 文档中的文档根节点。
    document.documentElement
    // 对HTML页面的特殊扩展，提供了对 <body> 标签的直接访问。
    document.body

/*节点信息
    每个节点都拥有包含着关于节点某些信息的属性。这些属性是：
    nodeName（节点名称）
        元素节点的 nodeName 是标签名称
        属性节点的 nodeName 是属性名称
        文本节点的 nodeName 永远是 #text
        文档节点的 nodeName 永远是 #document
        注释：nodeName 所包含的 XML 元素的标签名称永远是大写的

    nodeValue（节点值）
        对于文本节点，nodeValue 属性包含文本。
        对于属性节点，nodeValue 属性包含属性值。
        nodeValue 属性对于文档节点和元素节点是不可用的。

    nodeType（节点类型） 
        整形值: 元素 1,  属性 2, 文本 3, 注释 8, 文档 9 */


/* Window 对象
    Window 对象表示浏览器中打开的窗口。
    如果文档包含框架（frame 或 iframe 标签），
        浏览器会为 HTML 文档创建一个 window 对象，
        并为每个框架创建一个额外的 window 对象。*/

    // 属性：
        .frames[]: 返回窗口中所有命名的框架
        document: 对Document对象的只读引用
        location:用于窗口或框架的Location对象
        history: 对History对象的只读引用
        Navigator: 对Navigator对象的只读引用
        parent: 返回父窗口
        Screen: 对Screen 对象的只读引用
        ...

    // 方法:
        alert(): alert显示带有一段消息和一个确认按钮的警告框
        confirm(): confirm显示带有一段消息以及确认按钮和取消按钮的对话框
        blur(): blur把键盘焦点从顶层窗口移开
        close(): close关闭浏览器窗口


/* Navigator 对象
    Navigator 对象包含有关浏览器的信息。*/

/* Screen 对象
    Screen 对象包含有关客户端显示屏幕的信息 */

/* History 对象
    History 对象包含用户（在浏览器窗口中）访问过的 URL
    History 对象是 window 对象的一部分，可通过 window.history 属性对其进行访问 */

    length: 返回浏览器历史列表中的 URL 数量

    back(): back加载 history 列表中的前一个URL
          window.history.back();

    forward(): forward加载 history 列表中的下一个URL
          window.history.forward();

    go(): go加载 history 列表中的某个具体页面
          window.history.go(-1);


/* Location 对象
    Location 对象包含有关当前 URL 的信息。
    Location 对象是 Window 对象的一个部分，可通过 window.location 属性来访问。*/

    window.location="/index.html";

    hash: 设置或返回从井号 (#) 开始的 URL（锚）
    host: 设置或返回主机名和当前 URL 的端口号
    hostname: 设置或返回当前 URL 的主机名
    href: 设置或返回完整的 URL
    pathname: 设置或返回当前 URL 的路径部分
    port: 设置或返回当前 URL 的端口号
    protocol: 设置或返回当前 URL 的协议
    search: 设置或返回从问号 (?) 开始的 URL（查询部分）


    assign(): 加载新的文档
    reload(): 重新加载当前文档
    replace(): 用新的文档替换当前文档。


