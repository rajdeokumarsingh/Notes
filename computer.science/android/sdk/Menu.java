/**
 * file:///home/jiangrui/android/android-sdk/docs/guide/topics/ui/menus.html
 */
两种类型的Menu {
    Options Menu
        通过menu键来调出
        menu item 也可以放进Action Bar

    Context Menu
        通过长按UI部件调出

        Submenu
            用户选中的menu item后弹出
}

位于res/menu/
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/item1"
          android:title="@string/item1"
          android:icon="@drawable/group_item1_icon"
          android:showAsAction="ifRoom|withText"/>
    <group android:id="@+id/group">
        <item android:id="@+id/group_item1"
              android:onClick="onGroupItemClick"
              android:title="@string/group_item1"
              android:icon="@drawable/group_item1_icon" />
        <item android:id="@+id/group_item2"
              android:onClick="onGroupItemClick"
              android:title="@string/group_item2"
              android:icon="@drawable/group_item2_icon" />
    </group>
    <item android:id="@+id/submenu"
          android:title="@string/submenu_title"
          android:showAsAction="ifRoom|withText" >
        <menu>
            <item android:id="@+id/submenu_item1"
                  android:title="@string/submenu_item1" />
        </menu>
    </item>
</menu>

// XXX: 一般情况下，onCreateOptionsMenu仅仅只会被调用一次
Inflating a Menu Resource {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        // XXX: 多次inflate()一个menu, 会将xml中的item重复的添加到menu中
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
}

Responding to user action {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                newGame();
                return true;
            case R.id.help:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

Changing menu items at runtime {
    // 每次用户打开option menu的时候, onPrepareOptionsMenu都会被调用到
    onPrepareOptionsMenu()

    public void invalidateOptionsMenu ()
        Since: API Level 11
        Declare that the options menu has changed, so should be recreated. 
        The onCreateOptionsMenu(Menu) method will be called the next time it needs to be displayed.
}

Creating Submenus
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/file"
        android:icon="@drawable/file"
        android:title="@string/file" >
        <!-- "file" submenu -->
        <menu>
            <item android:id="@+id/create_new"
                android:title="@string/create_new" />
            <item android:id="@+id/open"
                android:title="@string/open" />
        </menu>
    </item>
</menu>




