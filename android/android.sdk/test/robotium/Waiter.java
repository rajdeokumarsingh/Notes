
// 检查当前activity stack的栈顶是否是需要的activity
// Waits for the given {@link Activity}.
// @param name the name of the {@code Activity} to wait for e.g. {@code "MyActivity"}
public boolean waitForActivity(String name, int timeout)
    while(SystemClock.uptimeMillis() < endTime){
        if(currentActivity != null && currentActivity.getClass().getSimpleName().equals(name))
            return true;

        currentActivity = activityUtils.getCurrentActivity();
    }
