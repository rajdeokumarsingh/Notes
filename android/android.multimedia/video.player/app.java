
////////////////////////////////////////////////////////////////////////////////
// Gallery application 
////////////////////////////////////////////////////////////////////////////////

src/com/android/camera/MovieView.java
    播放视频的主activity

    <activity android:name="com.android.camera.MovieView"
            android:label="@string/movieviewlabel"
            android:screenOrientation="landscape"  <!--横向显示-->
            android:configChanges="orientation|keyboardHidden"
            android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen"> <!--全屏，没有title--> 

        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="rtsp" /> <!--捕获rtsp theme-->
        </intent-filter>

        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="video/*" />
            <data android:mimeType="application/sdp" /> <!--mimetype--> 
        </intent-filter>

        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="http" /> <!--捕获http theme-->
            <data android:mimeType="video/mp4" />
            <data android:mimeType="video/3gp" />
            <data android:mimeType="video/3gpp" />
            <data android:mimeType="video/3gpp2" />
        </intent-filter>
    </activity>


    view组成
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/root"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        <!--VideoView， framework中播放视频的view-->
        <VideoView android:id="@+id/surface_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_centerInParent="true" />

        <!--进度条-->
        <LinearLayout android:id="@+id/progress_indicator"
                android:orientation="vertical"
                android:layout_centerInParent="true"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
            <ProgressBar android:id="@android:id/progress"
                    style="?android:attr/progressBarStyleLarge"
                    android:layout_gravity="center"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content" />
            <TextView android:paddingTop="5dip"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center"
                    android:text="@string/loading_video" android:textSize="14sp"
                    android:textColor="#ffffffff" />
        </LinearLayout>
    </RelativeLayout>

    
    // 封装了一个MovieViewControl
    // 封装了一个VideoView
    @Override
    public void onCreate(Bundle icicle) {
        mControl = new MovieViewControl(rootView, this, intent.getData())

    // 提供一个broadcast reciever, 收到短信和wap push的时候， 震动一下
        // 闹钟又如何？ FM Radio又如何?

    @Override
    public void onPause() {
        super.onPause();
        if (mControlResumed) {
            mControl.onPause();
            mControlResumed = false;

        if (isStreamingScheme(mScheme)) {
            unregisterReceiver(mIntentReceiver);

    @Override
    public void onResume() {
        super.onResume();

        if (mFocused && mResumed && !mControlResumed) {
            mControl.onResume();
            mControlResumed = true;
        if (isStreamingScheme(mScheme)) {
            registerReceiver(mIntentReceiver, mIntentFilter);

src/com/android/camera/MovieViewControl.java

    // MediaPlayer事件的监听器
    // 封装了VideoView
    // 封装了MediaController, 暂停，继续，快进，等等按钮
    public class MovieViewControl implements MediaPlayer.OnErrorListener,
            MediaPlayer.OnCompletionListener, OnConnectCompleteListener 

        public void onPause()
            mHandler.removeCallbacksAndMessages(null);
            mPlayStateHandler.removeCallbacksAndMessages(null);
            setBookmark(mVideoView.getCurrentPosition());

            mPositionWhenPaused = mVideoView.getCurrentPosition();
            mWasPlayingWhenPaused = mVideoView.isPlaying();
            mVideoView.pause();


