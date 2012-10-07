
// Define drm mime type

    // The "application/vnd.oma.drm.message" mime type.
    public static final String DRM_MIMETYPE_MESSAGE_STRING = "application/vnd.oma.drm.message";

    // The "application/vnd.oma.drm.content" mime type.
    public static final String DRM_MIMETYPE_CONTENT_STRING = "application/vnd.oma.drm.content";


// Define drm raw type

    // The DRM delivery type: Forward-Lock
    public static final int DRM_FORWARD_LOCK = 1;

    // The DRM delivery type: Combined Delivery
    public static final int DRM_COMBINED_DELIVERY = 2;

    // The DRM delivery type: Separate Delivery
    public static final int DRM_SEPARATE_DELIVERY = 3;

    // The DRM delivery type: Separate Delivery in DRM message
    public static final int DRM_SEPARATE_DELIVERY_DM = 4;


/**
 * Construct a DrmRawContent object.                                                                                                                     
 * 
 * @param inRawdata     object of DRM raw data stream.
 * @param len           the length of raw data can be read.
 * @param mimeTypeStr   the mime type of the DRM content.                                                                                                
 */
public DrmRawContent(InputStream inRawdata, int len, String mimeTypeStr)

    /* call native method to initialize this DRM content */
    id = nativeConstructDrmContent(inData, inDataLen, mimeType);

    /* init the rights issuer field. */
    rightsIssuer = nativeGetRightsAddress();

    /* init the raw content type. */
    rawType = nativeGetDeliveryMethod();

    /* init the media content type. */
    mediaType = nativeGetContentType();

    // encapsulte native method
    private native int nativeConstructDrmContent(InputStream data, int len, int mimeType);
    private native int nativeConstructDrmContentWithFile(InputStream data, int len, int mimeType,String fileName );
    private native String nativeGetRightsAddress();
    private native int nativeGetDeliveryMethod();
    private native int nativeReadContent(byte[] buf, int bufOff, int len, int mediaOff);
    private native String nativeGetContentType();
    private native int nativeGetContentLength();
    protected native void finalize();



