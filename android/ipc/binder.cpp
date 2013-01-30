kernel:
http://disanji.net/2011/02/28/android-bnder-design/

邓平凡:
cpp层的代码调用分析:
http://www.cnblogs.com/innost/archive/2011/01/09/1931456.html

int main(int argc, char** argv)

{
    sp<ProcessState> proc(ProcessState::self()); {
        /**
         * 1. 创建ProcessState
         * 2. 打开/dev/binder设备，这样的话就相当于和内核binder机制有了交互的通道
         * 3. 映射fd到内存，设备的fd传进去后，估计这块内存是和binder设备共享的
         */
    }

    sp<IServiceManager> sm = defaultServiceManager(); {
        /**
         * 1. 创建了主线程的IPCThreadState
         * 2. 创建了BpBinder，内部handle值为0. "0"hardcode对应service manager
         * 3. 最后返回的是BpServiceManager(new BpBinder(0))；
         */
    }

    MediaPlayerService::instantiate(); {
        // 1. 创建MediaPlayerService
        // 2. 将其加入到ServiceManger中
        defaultServiceManager()->addService(
                String16("media.player"), new MediaPlayerService());
    }

    ProcessState::self()->startThreadPool(); {
        //1. 启动Process的线程池
        //2. 创建一个子线程，并将子线程加入线程池
        //3. 该子线程从binder循环中获取数据并提交给对应的BnXXXService处理
            // 对应的BnXXXService保存在binder中binder_transaction_data的cookie域
            // 在ServiceManger的addService时注册到binder中去
    }

    IPCThreadState::self()->joinThreadPool(); {
        // 1. 主线程加入线程池
    }
}

Client                                                                      Server::doSth
    |                                                                           ^
    v                                                                           |
BpXXX::doSth()   ------------------------------------------------------> BnXXX::onTransact(DO_STH, data, reply)
    |                                                                           ^
    v                                                                           |
BpBinder::transact(DO_STH, data, replay)                                BBinder::transact()
    |                                                                           ^
    v                                                                           |
IPCThreadState::transact(mHandle, DO_STH, data, reply)         IPCThreadState::executeCommand(int32_t cmd)
    |                                                                           ^
    v                                                                           |
ioctl(driverFD, BINDER_WRITE_READ, &bwr)                        ioctl(driverFD, BINDER_WRITE_READ, &bwr);
    |                                                                           ^
    v                                                                           |
  --------------------------------------------------------------------------------
                                                binder driver


