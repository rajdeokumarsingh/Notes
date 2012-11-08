
// 选择menu的第几个index
public void pressMenuItem(int index)
    // 按下menu键
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);

    // 通过上下左右键选择menu item
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);

    // 选择menu item
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);

// 选择第spinnerIndex个spinner的第itemIndex个(相对当前index的)item
public void pressSpinnerItem(int spinnerIndex, int itemIndex)
    // tap这个spinner 
    clicker.clickOnScreen(waiter.waitForAndGetView(spinnerIndex, Spinner.class));

    // 通过上下键选择item
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);

    // 选择确定item
    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
