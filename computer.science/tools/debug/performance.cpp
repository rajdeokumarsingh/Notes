
################################################################################
1. log time stamp
################################################################################

    // print time stamp in cpp 
    {

        static uint32_t get_thread_msec() {
    #if defined(HAVE_POSIX_CLOCKS)
            struct timespec tm;

            clock_gettime(CLOCK_THREAD_CPUTIME_ID, &tm);

            return tm.tv_sec * 1000LL + tm.tv_nsec / 1000000;
    #else
            struct timeval tv;

            gettimeofday(&tv, NULL);
            return tv.tv_sec * 1000LL + tv.tv_usec / 1000;
    #endif
        }

        SkMSec now = get_thread_msec(); //SkTime::GetMSecs();
        // do something
        LOGD("duration %d ms\n", get_thread_msec() - now);
    }

    // print time stamp in java
    {
        long timeBefore = System.currentTimeMillis();
        // do something
        System.out.println("duration: " + (System.currentTimeMillis() - timeBefore));

    }

################################################################################
2.  android java profile 
################################################################################
file:///home/jiangrui/android/backup/android-sdk-2.2/docs/guide/developing/tools/traceview.html

android.os.Debug class, which provides a start-tracing method
(Debug.startMethodTracing()) and a stop-tracing method (Debug.stopMethodTracing()).

APK需要添加写sd卡的权限
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

trace

查看调用流程
    dmtracedump -o test.trace > test.gg

3. OProfile
    http://blog.csdn.net/dongjun7357/article/details/6400549
    http://blog.csdn.net/dongjun7357/article/details/6400520

4. GProfile
    http://blog.csdn.net/dongjun7357/article/details/6277682

// android native profile
// 如何对Android的本地代码进行profiling
http://www.kunli.info/2011/11/17/view-android-native-code-profiling/
http://stackoverflow.com/questions/2539932/how-to-view-android-native-code-profiling
q2dm
