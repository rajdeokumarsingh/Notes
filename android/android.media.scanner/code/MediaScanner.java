/**
 * Internal service helper that no-one should use directly.
 *
 * The way the scan currently works is:
 * - The Java MediaScannerService creates a MediaScanner (this class), and calls
 *   MediaScanner.scanDirectories on it.
 * - scanDirectories() calls the native processDirectory() for each of the specified directories.
 * - the processDirectory() JNI method wraps the provided mediascanner client in a native
 *   'MyMediaScannerClient' class, then calls processDirectory() on the native MediaScanner
 *   object (which got created when the Java MediaScanner was created).
 * - native MediaScanner.processDirectory() (currently part of opencore) calls
 *   doProcessDirectory(), which recurses over the folder, and calls
 *   native MyMediaScannerClient.scanFile() for every file whose extension matches.
 * - native MyMediaScannerClient.scanFile() calls back on Java MediaScannerClient.scanFile,
 *   which calls doScanFile, which after some setup calls back down to native code, calling
 *   MediaScanner.processFile().
 * - MediaScanner.processFile() calls one of several methods, depending on the type of the
 *   file: parseMP3, parseMP4, parseMidi, parseOgg or parseWMA.
 * - each of these methods gets metadata key/value pairs from the file, and repeatedly
 *   calls native MyMediaScannerClient.handleStringTag, which calls back up to its Java                                                      
 *   counterparts in this file.
 * - Java handleStringTag() gathers the key/value pairs that it's interested in.
 * - once processFile returns and we're back in Java code in doScanFile(), it calls
 *   Java MyMediaScannerClient.endFile(), which takes all the data that's been
 *   gathered and inserts an entry in to the database.    

 * In summary:
 * Java MediaScannerService calls
 * Java MediaScanner scanDirectories, which calls
 * Java MediaScanner processDirectory (native method), which calls
 * native MediaScanner processDirectory, which calls
 * native MyMediaScannerClient scanFile, which calls
 * Java MyMediaScannerClient scanFile, which calls
 * Java MediaScannerClient doScanFile, which calls
 * Java MediaScanner processFile (native method), which calls
 * native MediaScanner processFile, which calls
 * native parseMP3, parseMP4, parseMidi, parseOgg or parseWMA, which calls
 * native MyMediaScanner handleStringTag, which calls
 * Java MyMediaScanner handleStringTag.
 * Once MediaScanner processFile returns, an entry is inserted in to the database.
 *
 * {@hide}
 */

// hashes file path to FileCacheEntry.
private HashMap<String, FileCacheEntry> mFileCache;

private ArrayList<FileCacheEntry> mPlayLists;
private HashMap<String, Uri> mGenreCache;

private MyMediaScannerClient mClient = new MyMediaScannerClient();

private class MyMediaScannerClient implements MediaScannerClient

public MediaScanner(Context c)
    native_setup();
    mContext = c;




