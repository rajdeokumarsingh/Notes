    private final ActivityUtils activityUtils;
    private final ViewFetcher viewFetcher;
    private final Waiter waiter;

    /**
     * Returns a {@code View} with a certain index, from the list of current {@code View}s of the specified type.
     *
     * @param classToFilterBy which {@code View}s to choose from, 如textview, button
     * @param index choose among all instances of this type, e.g. {@code Button.class} or {@code EditText.class}
     * @return a {@code View} with a certain index, from the list of current {@code View}s of the specified type
     */
    public <T extends View> T getView(Class<T> classToFilterBy, int index)
        return waiter.waitForAndGetView(index, classToFilterBy);
        // 例如, 获取第几个button

    /**
     * Returns a {@code View} that shows a given text, from the list of current {@code View}s of the specified type.
     *
     * @param classToFilterBy which {@code View}s to choose from
     * @param text the text that the view shows
     * @param onlyVisible {@code true} if only visible texts on the screen should be returned
     * @return a {@code View} showing a given text, from the list of current {@code View}s of the specified type
     */
    public <T extends TextView> T getView(Class<T> classToFilterBy, String text, boolean onlyVisible) 
        // 等到text的出现
        waiter.waitForText(text, 0, 10000, false, onlyVisible);

        // 遍历所有的view, 检查view的text是否和期望的匹配
        ArrayList<T> views = viewFetcher.getCurrentViews(classToFilterBy);
        T viewToReturn = null;
        for(T view: views){
            if(view.getText().toString().equals(text))
                viewToReturn = view;
        }

    /**
     * Returns a {@code View} with a given id.
     * @param id the R.id of the {@code View} to be returned
     */
    public View getView(int id){
        final Activity activity = activityUtils.getCurrentActivity(false);

        View view = activity.findViewById(id);
        if (view != null)
            return view;

        return waiter.waitForView(id);
    }



