

/**
 * Creates a new activity stack and pushes the start activity 
 */
private void createStackAndPushStartActivity(){
    activityStack = new Stack<WeakReference<Activity>>();
    WeakReference<Activity> weakReference = new WeakReference<Activity>(activity);
    activity = null;
    activityStack.push(weakReference);
}

// Returns a {@code List} of all the opened/active activities.
public ArrayList<Activity> getAllOpenedActivities()
    // 返回activityStack中所有的activity

// This is were the activityMonitor is set up. The monitor will keep check
// for the currently active activity.
private void setupActivityMonitor()
    IntentFilter filter = null;
    activityMonitor = inst.addMonitor(filter, null, false);

// This is were the activityStack listener is set up. The listener will keep track of the
// opened activities and their positions.
private void setupActivityStackListener() {
    TimerTask activitySyncTimerTask = new TimerTask() {
        @Override
        public void run() {
            Activity activity = activityMonitor.getLastActivity();
            addActivityToStack(activity);
        }
    };
    activitySyncTimer.schedule(activitySyncTimerTask, 0, ACTIVITYSYNCTIME);
}

// Removes a given activity from the activity stack
// @param activity the activity to remove
private void removeActivityFromStack(Activity activity)

// Sets the Orientation (Landscape/Portrait) for the current activity.
public void setActivityOrientation(int orientation)
    Activity activity = getCurrentActivity();
    activity.setRequestedOrientation(orientation);

// Adds an activity to the stack
private void addActivityToStack(Activity activity){
    activitiesStoredInActivityStack.add(activity.toString());
    weakActivityReference = new WeakReference<Activity>(activity);
    activity = null;
    activityStack.push(weakActivityReference);

// Waits for an activity to be started if one is not provided
// by the constructor.
private final void waitForActivityIfNotAvailable(){
    if(activityStack.isEmpty() || activityStack.peek().get() == null){

        if (activityMonitor != null) {
            Activity activity = activityMonitor.getLastActivity();
            while (activity == null){
                sleeper.sleepMini();
                activity = activityMonitor.getLastActivity();
            }
            addActivityToStack(activity);
        }
        else{
            sleeper.sleepMini();
            setupActivityMonitor();
            waitForActivityIfNotAvailable();
        }
    }
}

// 返回activity stack上的第一个activity
// Returns the current {@code Activity}.
// @param shouldSleepFirst whether to sleep a default pause first
// @return the current {@code Activity}
public Activity getCurrentActivity(boolean shouldSleepFirst)
    waitForActivityIfNotAvailable();
    return activityStack.peek().get();

// Returns to the given {@link Activity}.
// @param name the name of the {@code Activity} to return to, e.g. {@code "MyActivity"}
public void goBackToActivity(String name) {
    ArrayList<Activity> activitiesOpened = getAllOpenedActivities();
    boolean found = false;

    // 遍历所有的activity, 看name是否存在
    for(int i = 0; i < activitiesOpened.size(); i++){
        if(activitiesOpened.get(i).getClass().getSimpleName().equals(name)){
            found = true;
            break;
        }
    }
    if(found){  // go back到该activity
        while(!getCurrentActivity().getClass().getSimpleName().equals(name)) {
            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
        }
    } else{
        for (int i = 0; i < activitiesOpened.size(); i++)
            Log.d(LOG_TAG, "Activity priorly opened: "+ activitiesOpened.get(i).getClass().getSimpleName());
        Assert.assertTrue("No Activity named " + name + " has been priorly opened", false);
    }
}

// Finalizes the solo object.
@Override
public void finalize() throws Throwable {
    activitySyncTimer.cancel();
    inst.removeMonitor(activityMonitor);
    super.finalize();
}

// Finish所有的后台activity
// All inactive activities are finished.
public void finishInactiveActivities()
    for (Iterator<WeakReference<Activity>> iter = activityStack.iterator(); iter.hasNext();)
        Activity activity = iter.next().get();
        if (activity != getCurrentActivity())
            finishActivity(activity);
            iter.remove();

// All activites that have been opened are finished.
public void finishOpenedActivities(){
    // Stops the activityStack listener
    activitySyncTimer.cancel();
    ArrayList<Activity> activitiesOpened = getAllOpenedActivities();
    // Finish all opened activities
    for (int i = activitiesOpened.size()-1; i >= 0; i--) {
        sleeper.sleep(MINISLEEP);
        finishActivity(activitiesOpened.get(i));
    }
    activitiesOpened = null;
    // Finish the initial activity, pressing Back for good measure
    finishActivity(getCurrentActivity());
    this.activity = null;
    sleeper.sleepMini();
    try {
        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
        sleeper.sleep(MINISLEEP);
        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
    } catch (Throwable ignored) {
        // Guard against lack of INJECT_EVENT permission
    }
    clearActivityStack();
}

// Clears the activity stack
private void clearActivityStack()
    activityStack.clear();
    activitiesStoredInActivityStack.clear();

// Finishes an activity
private void finishActivity(Activity activity){
    activity.finish();


