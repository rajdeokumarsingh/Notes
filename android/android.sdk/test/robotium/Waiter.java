// 检查当前activity stack的栈顶是否是需要的activity
// Waits for the given {@link Activity}.
// @param name the name of the {@code Activity} to wait for e.g. {@code "MyActivity"}
public boolean waitForActivity(String name, int timeout)
    while(SystemClock.uptimeMillis() < endTime){
        if(currentActivity != null && currentActivity.getClass().getSimpleName().equals(name))
            return true;

        currentActivity = activityUtils.getCurrentActivity();
    }

/**
 * Waits for a view to be shown.
 * 
 * @param viewClass the {@code View} class to wait for
 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
 * @return {@code true} if view is shown and {@code false} if it is not shown before the timeout
 */
public <T extends View> boolean waitForView(final Class<T> viewClass, final int index, boolean sleep, boolean scroll){
    Set<T> uniqueViews = new HashSet<T>();
    boolean foundMatchingView;

    while(true){
        if(sleep)
            sleeper.sleep();
                
        // 在当前activity的所有view中查找所有的viewClass类型的view
        foundMatchingView = searcher.searchFor(uniqueViews, viewClass, index);

        // 找到了就直接返回
        if(foundMatchingView)
            return true;

        // 没有找到就scroll down
        if(scroll && !scroller.scroll(Scroller.DOWN))
            return false;

        if(!scroll)
            return false;
    }
}

// Waits for a view to be shown within timeout
public <T extends View> boolean waitForView(final Class<T> viewClass, 
        final int index, final int timeout, final boolean scroll)


// Waits for two views to be shown.
public <T extends View> boolean waitForViews(final Class<T> viewClass, final Class<? extends View> viewClass2){


/**
 * Waits for a text to be shown.
 *
 * @param text the text that needs to be shown
 * @param expectedMinimumNumberOfMatches the minimum number of matches of text that must be shown. {@code 0} means any number of matches
 * @param timeout the amount of time in milliseconds to wait
 * @param scroll {@code true} if scrolling should be performed
 * @param onlyVisible {@code true} if only visible text views should be waited for
 * @return {@code true} if text is found and {@code false} if it is not found before the timeout
 * 
 */
public boolean waitForText(String text, int expectedMinimumNumberOfMatches, long timeout, boolean scroll, boolean onlyVisible) {
    final long endTime = SystemClock.uptimeMillis() + timeout;

    while (true) {
        final boolean timedOut = SystemClock.uptimeMillis() > endTime;
        if (timedOut){
            return false;
        }

        sleeper.sleep();

        // 查找所有view中是否包括了text的TextView
        final boolean foundAnyTextView = searcher.searchFor(TextView.class, text, 
                expectedMinimumNumberOfMatches, scroll, onlyVisible);

        if (foundAnyTextView){
            return true;
        }
    }
}


/**
 * Waits for and returns a View.
 * 
 * @param index the index of the view
 * @param classToFilterby the class to filter
 * @return view
 * 
 */
public <T extends View> T waitForAndGetView(int index, Class<T> classToFilterBy){

/**
 * Waits for a log message to appear.
 * Requires read logs permission (android.permission.READ_LOGS) in AndroidManifest.xml of the application under test.
 * 
 * @param logMessage the log message to wait for
 * @param timeout the amount of time in milliseconds to wait
 * 
 * @return true if log message appears and false if it does not appear before the timeout
 */
public boolean waitForLogMessage(String logMessage, int timeout){


// Returns the log in the given stringBuilder. 
// @param stringBuilder the StringBuilder object to return the log in
// @return the log
private StringBuilder getLog(StringBuilder stringBuilder){
    Process p = null;
    BufferedReader reader = null;
    String line = null;  

    p = Runtime.getRuntime().exec("logcat -d");
    reader = new BufferedReader(  
            new InputStreamReader(p.getInputStream())); 

    stringBuilder.setLength(0);
    while ((line = reader.readLine()) != null) {  
        stringBuilder.append(line); 
    }

    destroy(p, reader);
    return stringBuilder;
}



