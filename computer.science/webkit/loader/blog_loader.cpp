WebKit 内核源代码分析(四)

摘要：本文介绍 WebCore 中 Loader 模块是如何加载资源的，
分主资源和派生资源分析 loader 模块的类关系。

关键词： WebKit,Loader,Network,ResouceLoader,SubresourceLoader

一、类结构及接口
Loader 模块是 Network 模块的客户。 Network 模块提供指定资源的获取和上传功能，
获取的资源可能来自网络、本地文件或者缓存。

对不同 HTTP 实现的适配会在 Network 层完成，
所以 Loader 接触到的基本上是同 OS 和 HTTP 实现无关的 Network 层接口。 



