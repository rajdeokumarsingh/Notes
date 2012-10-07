

base/core/res/res/layout/media_controller.xml

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" ... >

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingTop="4dip"
        android:orientation="horizontal">

    <ImageButton android:id="@+id/prev" style="@android:style/MediaButton.Previous" />
    <ImageButton android:id="@+id/rew" style="@android:style/MediaButton.Rew" />
    <ImageButton android:id="@+id/pause" style="@android:style/MediaButton.Play" />
    <ImageButton android:id="@+id/ffwd" style="@android:style/MediaButton.Ffwd" />
    <ImageButton android:id="@+id/next" style="@android:style/MediaButton.Next" />
    </LinearLayout>


/**                                                                                    
 * A view containing controls for a MediaPlayer. Typically contains the                
 * buttons like "Play/Pause", "Rewind", "Fast Forward" and a progress                  
 * slider. It takes care of synchronizing the controls with the state                  
 * of the MediaPlayer.
 */
public class MediaController extends FrameLayout {                                     
                                                                                       
    private MediaPlayerControl  mPlayer;    // VideoView
    private View                mAnchor;
    private View                mRoot;
    private WindowManager       mWindowManager;
    private Window              mWindow;    // PolicyManager.makeNewWindow(mContext); 
    private View                mDecor;     // mWindow.getDecorView();
    private ProgressBar         mProgress;
    private TextView            mEndTime, mCurrentTime;
    private boolean             mShowing;
    private boolean             mDragging;
    private static final int    sDefaultTimeout = 3000;
    private static final int    FADE_OUT = 1;
    private static final int    SHOW_PROGRESS = 2;
    private boolean             mUseFastForward;
    private boolean             mFromXml;
    private boolean             mListenersSet;
    private View.OnClickListener mNextListener, mPrevListener;
    StringBuilder               mFormatBuilder;
    Formatter                   mFormatter;
    private ImageButton         mPauseButton;
    private ImageButton         mFfwdButton;
    private ImageButton         mRewButton;
    private ImageButton         mNextButton;
    private ImageButton         mPrevButton;


    private void initFloatingWindow() 
        mWindowManager = (WindowManager)mContext.getSystemService("window");            
        mWindow = PolicyManager.makeNewWindow(mContext);
        mWindow.setWindowManager(mWindowManager, null, null);                           
        mWindow.requestFeature(Window.FEATURE_NO_TITLE);                                
        mDecor = mWindow.getDecorView();
        mDecor.setOnTouchListener(mTouchListener);                                      
        mWindow.setContentView(this);
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);             

        // While the media controller is up, the volume control keys should             
        // affect the media stream type
        mWindow.setVolumeControlStream(AudioManager.STREAM_MUSIC);                      

        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);                   
        requestFocus();                                                                 



    /**
     * VideoView会将它的this.getParent()(Gallery->MovieView->RelativeLayout)
     * 设置为controller的anchor view
     *
     * Set the view that acts as the anchor for the control view.
     * This can for example be a VideoView, or your Activity's main view.
     * @param view The view to which to anchor the controller when it is visible.
     */
    public void setAnchorView(View view) {
        mAnchor = view;

        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
                );

        removeAllViews();
        View v = makeControllerView();
        addView(v, frameParams);
    }

