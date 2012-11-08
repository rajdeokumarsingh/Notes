// Tells Robotium to send a key code: Right, Left, Up, Down, Enter or other.
public void sendKeyCode(int keycode)
    inst.sendCharacterSync(keycode);


// Simulates pressing the hardware back key.
public void goBack()
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

// Removes invisible {@code View}s
public static <T extends View> ArrayList<T> removeInvisibleViews(ArrayList<T> viewList)

/**
 * Filters views
 * 
 * @param classToFilterBy the class to filter
 * @param viewList the ArrayList to filter from
 * @return an ArrayList with filtered views
 */
public static <T extends View> ArrayList<T> filterViews(Class<T> classToFilterBy, ArrayList<View> viewList) {
    if (classToFilterBy.isAssignableFrom(view.getClass())) {
        filteredViews.add(classToFilterBy.cast(view));
    }

/**
 * Filters all views not within the given set
 *
 * @param classSet contains all classes that are ok to pass the filter
 * @param viewList the ArrayList to filter form
 * @return an ArrayList with filtered views
 */
public static ArrayList<View> filterViewsToSet(Class<View> classSet[],
        ArrayList<View> viewList) {

/**
 * Checks if a view matches a certain string and returns the amount of matches
 * 
 * @param regex the regex to match
 * @param view the view to check
 * @param uniqueTextViews set of views that have matched
 * @return amount of total matches
 */
public static int checkAndGetMatches(String regex, TextView view, Set<TextView> uniqueTextViews){

/**
 * Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/". 
 * Requires write permission (android.permission.WRITE_EXTERNAL_STORAGE) in AndroidManifest.xml of the application under test.
 */
public void takeScreenshot(final View view, final String name) {
    activityUtils.getCurrentActivity(false).runOnUiThread(new Runnable() {
        view.destroyDrawingCache();
        view.buildDrawingCache(false);
        Bitmap b = view.getDrawingCache();
        FileOutputStream fos = null;
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss");
        String fileName = null;
        if(name == null)
            fileName = sdf.format( new Date()).toString()+ ".jpg";
        else
            fileName = name + ".jpg";
        File directory = new File(Environment.getExternalStorageDirectory() + "/Robotium-Screenshots/");
        directory.mkdir();

        File fileToSave = new File(directory,fileName);
        fos = new FileOutputStream(fileToSave);
        if (b.compress(Bitmap.CompressFormat.JPEG, 100, fos) == false)
            Log.d(LOG_TAG, "Compress/Write failed");
        fos.flush();
        fos.close();
        view.destroyDrawingCache();
    }
}

