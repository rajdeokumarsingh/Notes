
// Checks if a {@link CompoundButton} with a given index is checked.
public <T extends CompoundButton> boolean isButtonChecked(Class<T> expectedClass, int index)
    return (waiter.waitForAndGetView(index, expectedClass).isChecked());

// Checks if a {@link CompoundButton} with a given text is checked.
public <T extends CompoundButton> boolean isButtonChecked(Class<T> expectedClass, String text)
    waiter.waitForText(text, 0, 10000);

    ArrayList<T> list = viewFetcher.getCurrentViews(expectedClass);
    for(T button : list){
        if(button.getText().equals(text) && button.isChecked())
            return true;
    }
    return false;

// Checks if a {@link CheckedTextView} with a given text is checked.
public boolean isCheckedTextChecked(String text)
    waiter.waitForText(text, 0, 10000);

    ArrayList<CheckedTextView> list = viewFetcher.getCurrentViews(CheckedTextView.class);
    for(CheckedTextView checkedText : list){
        if(checkedText.getText().equals(text) && checkedText.isChecked())
            return true;
    }
    return false;

// Checks if a given text is selected in any {@link Spinner} located on the current screen.
public boolean isSpinnerTextSelected(String text)
    waiter.waitForAndGetView(0, Spinner.class);
    ArrayList<Spinner> spinnerList = viewFetcher.getCurrentViews(Spinner.class);
    for(int i = 0; i < spinnerList.size(); i++){
        if(isSpinnerTextSelected(i, text))
            return true;
    }
    return false;

// Checks if a given text is selected in a given {@link Spinner} 
public boolean isSpinnerTextSelected(int spinnerIndex, String text)
    Spinner spinner = waiter.waitForAndGetView(spinnerIndex, Spinner.class);

    TextView textView = (TextView) spinner.getChildAt(0);
    if(textView.getText().equals(text))
        return true;
    else
        return false;

