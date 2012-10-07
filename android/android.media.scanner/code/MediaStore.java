
/*
1. 定义了media的meta信息
2. 封装了media provider的一些接口
    如查询图片
*/
public static final String AUTHORITY = "media";

private static final String CONTENT_AUTHORITY_SLASH = "content://" + AUTHORITY + "/";

    public interface MediaColumns extends BaseColumns
        // The data stream for the file
        // 文件绝对路径，等等
        public static final String DATA = "_data";

        public static final String SIZE = "_size";

        public static final String DISPLAY_NAME = "_display_name";

        public static final String TITLE = "title";

        // The time the file was added to the media provider, Units are seconds since 1970.
        public static final String DATE_ADDED = "date_added";

        // The time the file was last modified, Units are seconds since 1970.
        public static final String DATE_MODIFIED = "date_modified";

        public static final String MIME_TYPE = "mime_type";

    // Contains meta data for all available images. 
    public static final class Images {
        public interface ImageColumns extends MediaColumns {
            // The description of the image
            public static final String DESCRIPTION = "description";

            // The picasa id of the image
            public static final String PICASA_ID = "picasa_id";

            // Whether the video should be published as public or private
            public static final String IS_PRIVATE = "isprivate";

            // The latitude where the image was captured.
            public static final String LATITUDE = "latitude";

            // The longitude where the image was captured.
            public static final String LONGITUDE = "longitude";

            // The date & time that the image was taken in units
            // of milliseconds since jan 1, 1970.
            public static final String DATE_TAKEN = "datetaken";

            // The orientation for the image expressed as degrees.
            // Only degrees 0, 90, 180, 270 will work.
            public static final String ORIENTATION = "orientation";

            // The mini thumb id.
            public static final String MINI_THUMB_MAGIC = "mini_thumb_magic";

            // The bucket id of the image. This is a read-only property that
            // is automatically computed from the DATA column.
            public static final String BUCKET_ID = "bucket_id";

            // The bucket display name of the image. This is a read-only property that
            // is automatically computed from the DATA column.
            public static final String BUCKET_DISPLAY_NAME = "bucket_display_name";


    // 封装Media provider的图片接口
    public static final class Media implements ImageColumns
        public static final Cursor query(ContentResolver cr, Uri uri, String[] projection,
                String selection, String [] selectionArgs, String orderBy)

        // Retrieves an image for the given url as a {@link Bitmap}.
        public static final Bitmap getBitmap(ContentResolver cr, Uri url)
                throws FileNotFoundException, IOException

        // Insert an image and create a thumbnail for it.
        public static final String insertImage(ContentResolver cr, String imagePath,
                String name, String description) throws FileNotFoundException

        // Get the content:// style URI for the image media table on the given volume.
        public static Uri getContentUri(String volumeName)
            return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/images/media");

        // The content:// style URI for the internal storage.
        public static final Uri INTERNAL_CONTENT_URI = getContentUri("internal");

        // The content:// style URI for the "primary" external storage volume.
        public static final Uri EXTERNAL_CONTENT_URI = getContentUri("external");

        // The MIME type of of this directory of images.  
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/image";

        // The default sort order for this table
        public static final String DEFAULT_SORT_ORDER = ImageColumns.BUCKET_DISPLAY_NAME;

    /**
     * This class allows developers to query and get two kinds of thumbnails:
     * MINI_KIND: 512 x 384 thumbnail
     * MICRO_KIND: 96 x 96 thumbnail
     */
    public static class Thumbnails implements BaseColumns {
        public static final Cursor query(ContentResolver cr, Uri uri, String[] projection)

        public static final Cursor queryMiniThumbnail(ContentResolver cr, long origId, int kind, String[] projection)

        public static void cancelThumbnailRequest(ContentResolver cr, long origId)

        public static Bitmap getThumbnail(ContentResolver cr, long origId, int kind, BitmapFactory.Options options)

        public static void cancelThumbnailRequest(ContentResolver cr, long origId, long groupId) 

        // Get the content:// style URI for the image media table on the given volume.
        public static Uri getContentUri(String volumeName)
            return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/images/thumbnails");

        // The content:// style URI for the internal storage.
        public static final Uri INTERNAL_CONTENT_URI = getContentUri("internal");

        // The content:// style URI for the "primary" external storage volume.
        public static final Uri EXTERNAL_CONTENT_URI = getContentUri("external");

        // The data stream for the thumbnail
        public static final String DATA = "_data";

        /**
         * The original image for the thumbnal
         * <P>Type: INTEGER (ID from Images table)</P>
         */
        public static final String IMAGE_ID = "image_id";

        /**
         * The kind of the thumbnail
         * <P>Type: INTEGER (One of the values below)</P>
         */
        public static final String KIND = "kind";

        public static final int MINI_KIND = 1;
        public static final int FULL_SCREEN_KIND = 2;
        public static final int MICRO_KIND = 3;

        // The blob raw data of thumbnail
        public static final String THUMB_DATA = "thumb_data";

        // The width of the thumbnal
        public static final String WIDTH = "width";

        // The height of the thumbnail
        public static final String HEIGHT = "height";

    // Container for all audio content.
    public static final class Audio 
        // Columns for audio file that show up in multiple tables.
        public interface AudioColumns extends MediaColumns 

            // A non human readable key calculated from the TITLE, used for
            // searching, sorting and grouping
            public static final String TITLE_KEY = "title_key";

            // The duration of the audio file, in ms
            public static final String DURATION = "duration";

            // The position, in ms, playback was at when playback 
            // for this file was last stopped.
            public static final String BOOKMARK = "bookmark";

            // The id of the artist who created the audio file, if any
            public static final String ARTIST_ID = "artist_id";

            // The artist who created the audio file, if any
            public static final String ARTIST = "artist";

            // The artist credited for the album that contains the audio file
            public static final String ALBUM_ARTIST = "album_artist";

            // A non human readable key calculated from the ARTIST, used for
            // searching, sorting and grouping
            public static final String ARTIST_KEY = "artist_key";

            // The composer of the audio file, if any
            public static final String COMPOSER = "composer";

            // The id of the album the audio file is from, if any
            public static final String ALBUM_ID = "album_id";

            // The album the audio file is from, if any
            public static final String ALBUM = "album";

            // A non human readable key calculated from the ALBUM, used for
            // searching, sorting and grouping
            public static final String ALBUM_KEY = "album_key";

            // A URI to the album art, if any
            public static final String ALBUM_ART = "album_art";

            /**
             * The track number of this song on the album, if any.
             * This number encodes both the track number and the
             * disc number. For multi-disc sets, this number will
             * be 1xxx for tracks on the first disc, 2xxx for tracks
             * on the second disc, etc.
             */
            public static final String TRACK = "track";

            public static final String YEAR = "year";
            public static final String IS_MUSIC = "is_music";
            public static final String IS_PODCAST = "is_podcast";
            public static final String IS_RINGTONE = "is_ringtone";
            public static final String IS_ALARM = "is_alarm";
            public static final String IS_NOTIFICATION = "is_notification";
            ...

            // Converts a name to a "key" that can be used for grouping, sorting and searching.
            public static String keyFor(String name)

        public static final class Media implements AudioColumns

            public static Uri getContentUri(String volumeName)
                return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/audio/media");
            public static final Uri INTERNAL_CONTENT_URI = getContentUri("internal");
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri("external");

            // The MIME type for this table.
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/audio";

            // The default sort order for this table
            public static final String DEFAULT_SORT_ORDER = TITLE_KEY;


        // Columns representing an audio genre
        public interface GenresColumns {
            // The name of the genre
            public static final String NAME = "name";

        // Contains all genres for audio files
        public static final class Genres implements BaseColumns, GenresColumns 

            public static Uri getContentUri(String volumeName)
                return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/audio/genres");

            public static final Uri INTERNAL_CONTENT_URI = getContentUri("internal");
            public static final Uri EXTERNAL_CONTENT_URI = getContentUri("external");
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/genre";
            public static final String DEFAULT_SORT_ORDER = NAME;

                // Sub-directory of each genre containing all members.
                public static final class Members implements AudioColumns {

                    public static final Uri getContentUri(String volumeName, long genreId)
                        return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/audio/genres/" + genreId + "/members");

                    public static final String CONTENT_DIRECTORY = "members";
                    public static final String DEFAULT_SORT_ORDER = TITLE_KEY;
                    public static final String AUDIO_ID = "audio_id";
                    public static final String GENRE_ID = "genre_id";


        public interface PlaylistsColumns {
            public static final String NAME = "name";

            //The data stream for the playlist file
            public static final String DATA = "_data";

            // The time the file was added to the media provider, Units are seconds since 1970.
            public static final String DATE_ADDED = "date_added";

            // The time the file was last modified, Units are seconds since 1970.
            public static final String DATE_MODIFIED = "date_modified";



        public static final class Playlists implements BaseColumns, PlaylistsColumns {
            public static Uri getContentUri(String volumeName) {
                return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/audio/playlists");

            public static final Uri INTERNAL_CONTENT_URI = getContentUri("internal");

            public static final Uri EXTERNAL_CONTENT_URI = getContentUri("external");

            // The MIME type for this table.
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/playlist";

            // The MIME type for entries in this table.
            public static final String ENTRY_CONTENT_TYPE = "vnd.android.cursor.item/playlist";

            public static final String DEFAULT_SORT_ORDER = NAME;

            // Sub-directory of each playlist containing all members.
            public static final class Members implements AudioColumns {
                public static final Uri getContentUri(String volumeName,
                        long playlistId) {
                    return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName + "/audio/playlists/" + playlistId + "/members");

                // The ID within the playlist.
                public static final String _ID = "_id";

                // A subdirectory of each playlist containing all member audio files.
                public static final String CONTENT_DIRECTORY = "members";

                // The ID of the audio file
                public static final String AUDIO_ID = "audio_id";

                // The ID of the playlist
                public static final String PLAYLIST_ID = "playlist_id";

                // The order of the songs in the playlist
                public static final String PLAY_ORDER = "play_order";

    ...






