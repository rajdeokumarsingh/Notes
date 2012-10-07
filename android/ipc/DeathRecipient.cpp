

// 任务: client端被kill, server段收到通知

////////////////////////////////////////////////////////////////////////////////
// Server端代码
////////////////////////////////////////////////////////////////////////////////

// Server声明自己为DeathRecipient
class FMRadioService : public BnFMRadioService, protected Thread,                                                                                            
    public IBinder::DeathRecipient 

   // IBinder::DeathRecipient
   virtual void binderDied(const wp<IBinder>& who);


   // 实现binderDied方法, client端被kill时这个函数会被调用
   void FMRadioService::binderDied(const wp<IBinder>& who)
       LOGI("binderDied() %p, tid %d, calling tid %d", who.unsafe_get(),
               gettid(), IPCThreadState::self()->getCallingPid());
       disableRds();
       closeFMRadio();


    // 通过调用linkToDeath()将client端的binder连接到death recipient
    void FMRadioService::setFMRadioClient(const sp<IFMRadioClient>& client)
        m_client = client;

        // Notify the native server if the client dies
        sp<IBinder> binder = client->asBinder();
        binder->linkToDeath(this);

