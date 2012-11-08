    // 为EditText追加text
    public void setEditText(final EditText editText, final String text)
        final String previousText = editText.getText().toString();
        inst.runOnMainSync(new Runnable() {
            public void run()
            {
                editText.setInputType(InputType.TYPE_NULL); 
                editText.performClick();
                closeSoftKeyboard(editText);
                editText.setText(previousText + text);
                editText.setCursorVisible(false);
            }
        });

    // 为EditText设置text
    public void typeText(final EditText editText, final String text)
        inst.runOnMainSync(new Runnable() {
            public void run() {
                editText.setInputType(InputType.TYPE_NULL);
            }
        });
        clicker.clickOnScreen(editText, false, 0);
        closeSoftKeyboard(editText);
        inst.sendStringSync(text);


    /**
     * Hides the soft keyboard
     * @param editText the edit text in focus
     */
    @SuppressWarnings("static-access")
    private void closeSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager)activityUtils.getCurrentActivity(false).
            getSystemService(activityUtils.getCurrentActivity(false).INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


