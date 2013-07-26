Content provider
    share data between application
        content provider

    share internal data 
        Preferences: A set of key/value pairs that you can persist to store application preferences
        Files: Files internal to applications, which you can store on a removable storage medium
        SQLite: SQLite databases, each of which is private to the package that creates that database
        Network: A mechanism that lets you retrieve or store data externally through the Internet

    build in provider
        Browser
        CallLog
        Contacts
            People
            Phones
            Photos
            Groups
        MediaStore
            Audio
                Albums
                Artists
                Genres
                Playlists
            Images
                Thumbnails
            Video
        Settings

sqlite> .tables
/*This command is a shortcut for
    SELECT name FROM sqlite_master
    WHERE type IN ('table','view') AND name NOT LIKE 'sqlite_%'
    UNION ALL
    SELECT name FROM sqlite_temp_master
    WHERE type IN ('table','view')
    ORDER BY 1*/

.schema people
    prints out a create statement for a table called people

// Structure of Android Content URIs
    content://*/*/*

    for example:
        content://com.google.provider.NotePad/notes/23
        authority portion: 
            com.google.provider.NotePad
        path section:
            notes/23
        path segments
            notes, 23

// Structure of Android MIME Types
    responsibility to return the MIME type for a given URI
    a MIME type has two parts: a type and a subtype.
        text/html
        text/css
        text/xml
        text/vnd.curl
        application/pdf
        application/rtf
        application/vnd.ms-excel

    The primary registered content types are
        application
        audio
        example
        image
        message
        model
        multipart
        text
        video

        subtype
            But if a vendor has proprietary data formats, the subtype name begins with vnd
                Microsoft Excel spreadsheets are identified by the subtype vnd.ms-excel

            Some subtypes start with x-;
                these are nonstandard subtypes that don’t have to be registered

Android MIME type
    //One single note
    vnd.android.cursor.item/vnd.google.note
    //A collection or a directory of notes
    vnd.android.cursor.dir/vnd.google.note

    It is primarily a directory of items or a single item
    必须固定的前缀(后面可以随便写)
        vnd.android.cursor.item/vnd
        vnd.android.cursor.dir/vnd

// constructing a URI
public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/notes");

// Reading Data Using URIs
    Uri myPersonUri = ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 23);

// get id from uri
    //Retrieve a note id from the incoming uri that looks like
    //content://.../notes/23
    int noteId = uri.getPathSegments().get(1);
    //ask a query builder to build a query
    //specify a table name
    queryBuilder.setTables(NOTES_TABLE_NAME);
    //use the noteid to put a where clause
    queryBuilder.appendWhere(Notes._ID + "=" + );

两种查询方式:
    //URI method
    managedQuery("content://com.google.provider.NotePad/notes/23"
            ,null ,null ,null ,null);

    //Where clause
    managedQuery("content://com.google.provider.NotePad/notes"
            ,null ,"_id=?" ,new String[] {23} ,null);

ContentValues 
    a dictionary of key/value pairs
    much like column names and their values

    ContentValues values = new ContentValues();
    values.put("title", "New note");
    values.put("note","This is a new note");

    ContentResolver contentResolver = activity.getContentResolver();
    Uri uri = contentResolver.insert(Notepad.Notes.CONTENT_URI, values);

    // Adding a File to a Content Provider
        android使用一个缺省的_data字段保存文件

    //Use the content resolver to get an output stream directly
    //ContentResolver hides the access to the _data field where
    //it stores the real file reference.
    OutputStream outStream = activity.getContentResolver().openOutputStream(newUri);
    someSourceBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
    outStream.close();

update
    int numberOfRowsUpdated = activity.getContentResolver().update(
            Uri uri, ContentValues values, String whereClause,
            String[] selectionArgs )
/* delete
    int numberOfRowsDeleted = activity.getContentResolver().delete(
            Uri uri, String whereClause, String[] selectionArgs ) */

Implementing Content Providers
    1. Plan your database, URIs, column names, and so on, and create a
        metadata class that defines constants for all of these metadata elements.
    2. Extend the abstract class ContentProvider.
    3. Implement these methods: query, insert, update, delete, and getType.
    4. Register the provider in the manifest file.
        <provider android:name="NotePadProvider"
        android:authorities="com.google.provider.NotePad"/>

        An authority is like a domain name for that content provider


    expand methods:
        query insert update delete getType
////////////////////////////////////////////////////////////////////////////////
// ContentObserver example 1:
////////////////////////////////////////////////////////////////////////////////
private class SettingsObserver extends ContentObserver {
    public SettingsObserver(Handler handler) {
        super(handler);
        ContentResolver cr = mContext.getContentResolver();
        cr.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.WIFI_USE_STATIC_IP), false, this);
        cr.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.WIFI_STATIC_IP), false, this);
        // ...
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }
}

////////////////////////////////////////////////////////////////////////////////
// ContentObserver example 2:
////////////////////////////////////////////////////////////////////////////////

// define a class inherit from ContentObserver
private class SettingObserver extends ContentObserver {
    SettingObserver() {
        super(new Handler());
    }

    @Override
    public void onChange(boolean selfChange) {
        maybeApplySettingAsync();
    }
}

// register the content observer to a uri
Uri uri = Settings.Secure.getUriFor(Settings.Secure.ALLOWED_GEOLOCATION_ORIGINS);
mContext.getContentResolver().registerContentObserver(uri, false, mSettingObserver);

// unregister the content observer to the uri
mContext.getContentResolver().unregisterContentObserver(mSettingObserver);



