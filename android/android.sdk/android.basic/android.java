raw folder
    considered as resources, 
    will be localized
    access through resource id

assets folder
    considered general-purpose content

在Launcher中显示应用
<intent-filter>
    <action android:name="android.intent.action.MAIN" />
    <category android:name="android.intent.category.LAUNCHER" />
</intent-filter>

搞清下面intent
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <action android:name="android.intent.action.EDIT" />
        <action android:name="android.intent.action.PICK" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="vnd.android.cursor.dir/vnd.google.note" />
    </intent-filter>

    <intent-filter>
        <action android:name="android.intent.action.GET_CONTENT" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="vnd.android.cursor.item/vnd.google.note" />
    </intent-filter>

--------------------------------------------------------------------------------
URI权限使用
--------------------------------------------------------------------------------
如果没有声明权限，则禁止访问?

申明URI权限：
<provider android:name="BrowserProvider"
        android:authorities="browser"
        android:multiprocess="true"
        android:readPermission="com.android.browser.permission.READ_HISTORY_BOOKMARKS"
        android:writePermission="com.android.browser.permission.WRITE_HISTORY_BOOKMARKS">
    <path-permission android:path="/bookmarks/search_suggest_query" android:readPermission="android.permission.GLOBAL_SEARCH" />
</provider>

获取URI权限:
    <uses-permission android:name="com.android.browser.permission.READ_HISTORY_BOOKMARKS"/>
    获取path-permission
        <uses-permission android:name="android.permission.GLOBAL_SEARCH" />

uri权限检查是在ContentProvider的内部类
    class Transport extends ContentProviderNative
    中自动完成的

--------------------------------------------------------------------------------
global variable
--------------------------------------------------------------------------------
android.app.Application class
public class MyApplication extends Application
{
    // global variable
    private static final String myGlobalVariable;
    @Override
    public void onCreate()
    {
        super.onCreate();
        //... initialize global variables here
        myGlobalVariable = loadCacheData();
    }
    public static String getMyGlobalVariable() {
        return myGlobalVariable;
    }
}

--------------------------------------------------------------------------------
Structure of resource
--------------------------------------------------------------------------------
// @[package:]type/name
    if no package specified, local resources, refer to R.java
    if android:type/name, global resources, refer to android.R.java

    // ID is a resource too!
    <TextView android:id="@+id/text">
    //Success: Creates an id called "text" in the local package’s R.java

资源在package中的存在形式：
    compiled into binary format
        string-resource XML files 
        layout-resource XML files
        XML files in the /res/xml/ 
            use Android-supplied XML readers to read the XML nodes.
    don’t get compiled into binary format
        XML files in the /res/raw/ 
            You must use explicit stream-based APIs to read these files. 
            Audio and video files fall into this category.

AAPT
    The resource compiler in the Android Asset Packaging Tool 
    compiles all the resources 
        except the raw resources
    places them all into the final .apk file

//color resources
    should be in /res/values
    The name of the file is arbitrary

    <resources>
        <color name="red">#f00</color>
        <color name="blue">#0000ff</color>
        <color name="green">#f0f0</color>
        <color name="main_back_ground_color">#ffffff00</color>
    </resources>

    // access resource in java
    int mainBackGroundColor = activity.getResources.getColor(
            R.color.main_back_ground_color);

    // access resource in xml
    <TextView android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:textColor="@color/red"
        android:text="Sample Text to Show Red Color"/>

// more about string resource
    in the /res/values subdirectory. The name of the file is arbitrary.
    <resources>
        <string name="simple_string">simple string</string>
        <string name="quoted_string">"quoted'string"</string>
        <string name="double_quoted_string">\"double quotes\"</string>
        <string name="java_format_string">hello %2$s java format string. %1$s again</string>
        <string name="tagged_string">
            Hello <b><i>Slanted Android</i></b>, You are bold.
        </string>
    </resources>

    //Read a double quoted string and set it in a text view
    String doubleQuotedString = activity.getString(R.string.double_quoted_string);
    textView.setText(doubleQuotedString);

    //Read a Java format string
    String javaFormatString = activity.getString(R.string.java_format_string);
    //Convert the formatted string by passing in arguments
    String substitutedString = String.format(javaFormatString, "Hello" , "Android");
    //set the output in a text view
    textView.setText(substitutedString);

// dimemsion
    <resources>
        <dimen name="mysize_in_pixels">1px</dimen>
        <dimen name="mysize_in_dp">5dp</dimen>
        <dimen name="medium_size">100sp</dimen>
    </resources>

    最好使用dp和sp
    float dimen = activity.getResources().getDimension(R.dimen.mysize_in_pixels);
    <TextView android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:textSize="@dimen/medium_size"/>

// Image Resources
    /res/drawable
        sample_image.jpg
        R.drawable.sample_image
    两个图像文件的前缀文件名不能是一样的

    <Button
        android:id="@+id/button1"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Dial"
        android:background="@drawable/sample_image"
    />

    //Call getDrawable to get the image
    BitmapDrawable d = activity.getResources().getDrawable(R.drawable.sample_image);
    //You can use the drawable then to set the background
    button.setBackgroundDrawable(d);

    //or you can set the background directly from the Resource Id
    button.setBackgroundResource(R.drawable.icon);

// Color-Drawable Resources
    <resources>
        <drawable name="red_rectangle">#f00</drawable>
        <drawable name="blue_rectangle">#0000ff</drawable>
        <drawable name="green_rectangle">#f0f0</drawable>
    </resources>

    // Get a drawable
    ColorDrawble redDrawable = (ColorDrawable)
        activity.getResources().getDrawable(R.drawable.red_rectangle);
    //Set it as a background to a text view
    textView.setBackground(redDrawable);

    <TextView android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:textAlign="center"
        android:background="@drawable/red_rectangle"/>

Arbitrary XML Resource Files

// 资源目录的结构
/res/values/strings.xml
    /colors.xml
    /dimens.xml
    /attrs.xml
    /styles.xml
    /drawable/*.png
            /*.jpg
            /*.gif
            /*.9.png
    /anim/*.xml
    /layout/*.xml
    /raw/*.*
    /xml/*.xml
    /assets/*.*/*.*


[Reviewed at 2011-09-05]
