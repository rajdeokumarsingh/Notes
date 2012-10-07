// 1. 设置MediaScanner的Locale
// 2. 提供扫描内部，外部文件系统的接口。
// 3. 对于外部文件系统，提供扫描目录和文件的接口
// 4. 通过一个thread来完成扫描工作

public class MediaScannerService extends Service implements Runnable  

    // 1. create MediaScanner
    // 2. setLocale for the MediaScanner
    private MediaScanner createMediaScanner() {
        MediaScanner scanner = new MediaScanner(this);
        Locale locale = getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String localeString = null;
            if (country != null) {
                scanner.setLocale(language + "_" + country);
            else
                scanner.setLocale(language);

        return scanner;


    private void scan(String[] directories, String volumeName) {
        // don't sleep while scanning
        mWakeLock.acquire();

        // Jiang Rui: insert an record to the media db
        // to indicate the scan is in progress
        ContentValues values = new ContentValues();
        values.put(MediaStore.MEDIA_SCANNER_VOLUME, volumeName);
        Uri scanUri = getContentResolver().insert(MediaStore.getMediaScannerUri(), values);

        // Jiang Rui: send a scanner-start broadcast 
        Uri uri = Uri.parse("file://" + directories[0]);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_STARTED, uri));

        // Jiang Rui: begin scanning
        if (volumeName.equals(MediaProvider.EXTERNAL_VOLUME)) {
            openDatabase(volumeName);
        MediaScanner scanner = createMediaScanner();
        scanner.scanDirectories(directories, volumeName);

        getContentResolver().delete(scanUri, null, null);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_FINISHED, uri));
        mWakeLock.release();

    public void onCreate()
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.
        Thread thr = new Thread(null, this, "MediaScannerService");
        thr.start();

    private Uri scanFile(String path, String mimeType) {
        String volumeName = MediaProvider.INTERNAL_VOLUME;
        String externalStoragePath = Environment.getExternalStorageDirectory().getPath();

        if (path.startsWith(externalStoragePath)) {
            volumeName = MediaProvider.EXTERNAL_VOLUME;
            openDatabase(volumeName);
        }
        MediaScanner scanner = createMediaScanner();
        return scanner.scanSingleFile(path, volumeName, mimeType);
    }

    // Jiang Rui: provide scanner interface
    private final IMediaScannerService.Stub mBinder =
        new IMediaScannerService.Stub() {
            public void requestScanFile(String path, String mimeType, IMediaScannerListener listener)
                Bundle args = new Bundle();
                args.putString("filepath", path);
                args.putString("mimetype", mimeType);
                if (listener != null) {
                    args.putIBinder("listener", listener.asBinder());
                startService(new Intent(MediaScannerService.this,
                            MediaScannerService.class).putExtras(args));

            public void scanFile(String path, String mimeType) {
                requestScanFile(path, mimeType, null);








