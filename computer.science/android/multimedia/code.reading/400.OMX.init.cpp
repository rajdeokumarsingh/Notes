OpenMax IL 主要内容如下所示:

    组件(Component):
        每一个组件实现一种功能 

    端口(Port): 
        组件的输入输出接口

    隧道化(Tunneled):
        让两个组件直接连接的方式



OMXMaster负责加载多个plugin

    在构造函数中默认添加了两个plugin:
        addPlugin(new SoftOMXPlugin);
        addPlugin("libstagefrighthw.so");

    OMXPluginBase
        代表一个plugin, 一般是一个动态库so。
            比如 /system/lib/libstagefrighthw.so, 是厂商提供的动态库

        每个plugin中会有多个component
            通过调用 OMXPluginBase::makeComponentInstance 将一个component实例化

            OMX_COMPONENTTYPE 是component的接口
                接口定义在:
                    OMX_Component.h OMX_Core.h



OMXCodec
OMXComponent

mVideoSource
    video decoder

解码流程
    1. OMXCodec使用OMX_EmptyThisBuffer传递未解码的buffer给component
    2. component收到该命令后会读取input port buffer中的数据,将其组装成帧进行解码
    3. 读取buffer中的数据完成后会调用EmptyBufferDone通知OMXCodec

    4. OMXCodec使用OMX_FillThisBuffer传递空的bffer给component用于存储解码后的帧,
    5. component收到该命令后将解码好的帧数据复制到该buffer上,
    6. 然后调用FillBufferDone通知OMXCodec



