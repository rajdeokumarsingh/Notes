action
    系统自定义的action
    frameworks/base/core/java/android/content/Intent.java

    // Activity Action: Start as a main entry point, does not expect to receive data.
    ACTION_MAIN = "android.intent.action.MAIN";

        // example:
        <activity android:name="BrowserActivity" ...>
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.LAUNCHER" />
                <category android:name="android.intent.category.BROWSABLE" />
            </intent-filter>
        </activity>

    /* Activity Action: Display the data to the user. This is the most common
     action performed on data -- it is the generic action you can use on
     a piece of data to get the most reasonable thing to occur.  
     For example,
         when used on a contacts entry it will view the entry; 
         when used on a mailto: URI it will bring up a compose window 
                filled with the information supplied by the URI; 
         when used with a tel: URI it will invoke the dialer.
    */
    ACTION_VIEW = "android.intent.action.VIEW";

    /* A synonym for {@link #ACTION_VIEW}, the "standard" action 
       that is performed on a piece of data. */
    ACTION_DEFAULT = ACTION_VIEW;
    
        // example:
        // 场景: 浏览器发出intent, 音乐应用在线播放
            // 音乐应用的filter
            <activity android:name="com.android.music.StreamStarter"
                    android:theme="@android:style/Theme.Dialog" >
                <intent-filter>
                    <action android:name="android.intent.action.VIEW" />
                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />
                    <data android:scheme="http" />
                    <data android:mimeType="audio/mp3"/>
                    <data android:mimeType="audio/x-mp3"/>
                    <data android:mimeType="audio/mpeg"/>
                    <data android:mimeType="audio/mp4"/>
                    <data android:mimeType="audio/mp4a-latm"/>
                    <data android:mimeType="application/ogg"/>
                    <data android:mimeType="application/x-ogg"/>
                    <data android:mimeType="audio/ogg"/>
                </intent-filter>
            <activity>

            // 浏览器发出播放消息, StreamStarter
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url)); // url是一个http开头的字符串, android:scheme会过滤
            intent.setType(mimetype); // audio/mp3... , android:mimeType会过滤
            startActivity(intent);

            // StreamStarter 启动
            onCreate
                Uri uri = getIntent().getData(); // 获取在线播放地址

            // 判断系统中是否有应用能处理这个intent
            ResolveInfo info = getPackageManager().resolveActivity(intent, 
                PackageManager.MATCH_DEFAULT_ONLY);
            if(info != null) {
            ...
            }

    /* Used to indicate that some piece of data should be attached to some other place.  
       For example, image data could be attached to a contact.  
       It is up to the recipient to decide where the data should be attached; 
       the intent does not specify the ultimate destination.
      <p>Input: {@link #getData} is URI of data to be attached.
      <p>Output: nothing.
     */
    ACTION_ATTACH_DATA = "android.intent.action.ATTACH_DATA";

        // examples:
        <activity android:name="com.android.camera.Wallpaper"
            android:label="@string/camera_setas_wallpaper"
            android:icon="@drawable/ic_launcher_gallery">
            <intent-filter android:label="@string/camera_setas_wallpaper">
                <action android:name="android.intent.action.ATTACH_DATA" />
                <data android:mimeType="image/*" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

        // 启动wallpaper应用
        Uri u = image.fullSizeImageUri();
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.setDataAndType(u, image.getMimeType());
        intent.putExtra("mimeType", image.getMimeType());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        ((Activity) mContext).startActivity(Intent.createChooser(intent, mContext
                .getText(Res.string.set_image)));
            public static Intent createChooser(Intent target, CharSequence title) 
                Intent intent = new Intent(ACTION_CHOOSER);
                intent.putExtra(EXTRA_INTENT, target);
                if (title != null) {  
                    intent.putExtra(EXTRA_TITLE, title);
                }
                return intent;

        // Wallpaper应用启动后
        Uri imageToUse = getIntent().getData();
        if (imageToUse != null) {          
            Intent intent = new Intent();  
            intent.setClass(this, CropImage.class);
            intent.setData(imageToUse);    
            formatIntent(intent);          
            startActivityForResult(intent, CROP_DONE);                                                                                       
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);                                                                     
            intent.setType("image/*");     
            intent.putExtra("crop", "true");
            formatIntent(intent);          
            startActivityForResult(intent, PHOTO_PICKED);                                                                                    
        }

    /**
     * Activity Action: Provide explicit editable access to the given data.
     * <p>Input: {@link #getData} is URI of data to be edited.
     * <p>Output: nothing.
     */
    ACTION_EDIT = "android.intent.action.EDIT";  

        // example:
        <activity android:name="ApnEditor" android:label="@string/apn_edit">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <action android:name="android.intent.action.EDIT" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:mimeType="vnd.android.cursor.item/telephony-carrier" />
            </intent-filter>

            <intent-filter>
                <action android:name="android.intent.action.INSERT" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:mimeType="vnd.android.cursor.dir/telephony-carrier" />
            </intent-filter>
        </activity>

        // 启动ApnEditor Activity
        int pos = Integer.parseInt(preference.getKey());
        Uri url = ContentUris.withAppendedId(Telephony.Carriers.CONTENT_URI, pos);
        startActivity(new Intent(Intent.ACTION_EDIT, url));

        // ApnEditor 启动后
        final Intent intent = getIntent();
        final String action = intent.getAction();

        if (action.equals(Intent.ACTION_EDIT)) 
            mUri = intent.getData();
        else if (action.equals(Intent.ACTION_INSERT)) 
            if (mFirstTime || icicle.getInt(SAVED_POS) == 0)
                mUri = getContentResolver().insert(intent.getData(), new ContentValues());
            setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));
        
/* Activity Action: Pick an existing item, or insert a new item, and then edit it.
* <p>Input: {@link #getType} is the desired MIME type of the item to create or edit.
* The extras can contain type specific data to pass through to the editing/creating activity.
* <p>Output: The URI of the item that was picked.  This must be a content: 
* URI so that any receiver can access it.
*/
ACTION_INSERT_OR_EDIT = "android.intent.action.INSERT_OR_EDIT";

    // example: 添加或编辑联系人
    <activity android:name="ContactsListActivity"
        android:label="@string/contactsList"
        android:clearTaskOnLaunch="true" >
        ...
        <intent-filter>
            <action android:name="android.intent.action.INSERT_OR_EDIT" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="vnd.android.cursor.item/person" />
            <data android:mimeType="vnd.android.cursor.item/contact" />
            <data android:mimeType="vnd.android.cursor.item/raw_contact" />
        </intent-filter>
        ...
    </activity>

    // 发送intent 
    Intent createIntent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
    createIntent.setType("vnd.android.cursor.item/person");
    createIntent.putExtra(Insert.PHONE, mNumber);
    actions.add(new ViewEntry(R.drawable.sym_action_add,
        getString(R.string.recentCalls_addToContact), createIntent));

    // ContactsListActivity 启动
    onCreate
         final Intent intent = getIntent();
         String title = intent.getStringExtra(UI.TITLE_EXTRA_KEY);
         String action = intent.getAction();
         String component = intent.getComponent().getClassName();
         Bundle extras = intent.getExtras();

/* Activity Action: Pick an item from the data, returning what was selected.
* <p>Input: {@link #getData} is URI containing a directory of data
* (vnd.android.cursor.dir/*) from which to pick an item.
* <p>Output: The URI of the item that was picked.
*/
public static final String ACTION_PICK = "android.intent.action.PICK";

    // example:
    <activity android:name="ContactsListActivity"
        android:label="@string/contactsList"
        android:clearTaskOnLaunch="true" >
        ...
        <intent-filter>
            <action android:name="android.intent.action.PICK" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="vnd.android.cursor.dir/contact" android:host="com.android.contacts" />
            <data android:mimeType="vnd.android.cursor.dir/person" android:host="contacts" />
            <data android:mimeType="vnd.android.cursor.dir/phone_v2" android:host="com.android.contacts" />
            <data android:mimeType="vnd.android.cursor.dir/phone" android:host="contacts" />
            <data android:mimeType="vnd.android.cursor.dir/postal-address_v2" android:host="com.android.contacts" />
            <data android:mimeType="vnd.android.cursor.dir/postal-address" android:host="contacts" />
        </intent-filter>
        ...
    </activity>

    // ContactsListActivity 启动
    onCreate()
        if (Intent.ACTION_PICK.equals(action)) 
            final String type = intent.resolveType(this);
            if (Contacts.CONTENT_TYPE.equals(type))
                mMode = MODE_PICK_CONTACT;

ACTION_CREATE_SHORTCUT = "android.intent.action.CREATE_SHORTCUT";

category
data

flags:
    Intent intent = new Intent(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT
            | Intent.FLAG_RECEIVER_REPLACE_PENDING);
    intent.putExtra(WifiManager.EXTRA_NETWORK_INFO, mNetworkInfo);
    mContext.sendStickyBroadcast(intent);





