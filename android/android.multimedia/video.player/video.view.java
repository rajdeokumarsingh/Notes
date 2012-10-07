////////////////////////////////////////////////////////////////////////////////
// Fraemwork
////////////////////////////////////////////////////////////////////////////////


/**
 * Displays a video file.  The VideoView class
 * can load images from various sources (such as resources or content
 * providers), takes care of computing its measurement from the video so that
 * it can be used in any layout manager, and provides various display options
 * such as scaling and tinting.
 */
public class VideoView extends SurfaceView implements MediaPlayerControl 
    // maintains a status machine
    private static final int STATE_ERROR              = -1;
    private static final int STATE_IDLE               = 0;
    private static final int STATE_PREPARING          = 1;
    private static final int STATE_PREPARED           = 2;
    private static final int STATE_PLAYING            = 3;
    private static final int STATE_PAUSED             = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_SUSPEND            = 6;
    private static final int STATE_RESUME             = 7;
    private static final int STATE_SUSPEND_UNSUPPORTED = 8;

    // 封装MediaPlayer, 通过其接口控制媒体播放
    private MediaPlayer mMediaPlayer = null;

    // 将MediaPlayer和surface view关联
    // mMediaPlayer.setDisplay(mSurfaceHolder);
    private SurfaceHolder mSurfaceHolder = null;

    // 媒体的控制panel, 包括开始/暂停， 快进，快退等等按钮
    private MediaController mMediaController;

    private void openVideo()
        // when play a movie, stop playing music
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(mPreparedListener);
        mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
        mMediaPlayer.setOnErrorListener(mErrorListener);
        mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);

        mMediaPlayer.setDataSource(mContext, mUri, mHeaders);
        mMediaPlayer.setDisplay(mSurfaceHolder);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.prepareAsync();

        // we don't set the target state here either, but preserve the
        // target state that was there before.
        mCurrentState = STATE_PREPARING;
        attachMediaController();


    // attach to media controller
    public void setMediaController(MediaController controller) {
        mMediaController = controller;
        attachMediaController();

    private void attachMediaController() {
        if (mMediaPlayer != null && mMediaController != null) {
            mMediaController.setMediaPlayer(this);
            View anchorView = this.getParent() instanceof View ?
                (View)this.getParent() : this;
            mMediaController.setAnchorView(anchorView);
            mMediaController.setEnabled(isInPlaybackState());

    // change the size of SurfaceView if the size of video is changed
    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener =
        new MediaPlayer.OnVideoSizeChangedListener() {
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                mVideoWidth = mp.getVideoWidth();
                mVideoHeight = mp.getVideoHeight();
                if (mVideoWidth != 0 && mVideoHeight != 0) {
                    getHolder().setFixedSize(mVideoWidth, mVideoHeight);


    MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            mCurrentState = STATE_PREPARED;

            // Get the capabilities of the player for this stream
            Metadata data = mp.getMetadata(MediaPlayer.METADATA_ALL,
                    MediaPlayer.BYPASS_METADATA_FILTER);

            // 是否可以暂停，前进，后退...
            if (data != null) {
                mCanPause = !data.has(Metadata.PAUSE_AVAILABLE)
                    || data.getBoolean(Metadata.PAUSE_AVAILABLE);
                mCanSeekBack = !data.has(Metadata.SEEK_BACKWARD_AVAILABLE)
                    || data.getBoolean(Metadata.SEEK_BACKWARD_AVAILABLE);
                mCanSeekForward = !data.has(Metadata.SEEK_FORWARD_AVAILABLE)
                    || data.getBoolean(Metadata.SEEK_FORWARD_AVAILABLE);

            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();

            // 开始播放视频
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                getHolder().setFixedSize(mVideoWidth, mVideoHeight);
                if (mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                    if (mTargetState == STATE_PLAYING) {
                        start();
                        if (mMediaController != null) {
                            mMediaController.show();



