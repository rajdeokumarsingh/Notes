
// FIXME: define mime type
    /**
     * The "application/vnd.oma.drm.rights+xml" mime type.
     */
    public static final String DRM_MIMETYPE_RIGHTS_XML_STRING = "application/vnd.oma.drm.rights+xml";

    /**
     * The "application/vnd.oma.drm.rights+wbxml" mime type.
     */
    public static final String DRM_MIMETYPE_RIGHTS_WBXML_STRING = "application/vnd.oma.drm.rights+wbxml";

    /**
     * The id of "application/vnd.oma.drm.rights+xml" mime type.
     */
    private static final int DRM_MIMETYPE_RIGHTS_XML = 3;

    /**
     * The id of "application/vnd.oma.drm.rights+wbxml" mime type.
     */
    private static final int DRM_MIMETYPE_RIGHTS_WBXML = 4;


// FIXME: encapsulate following interfaces
    /**
     * native method: install rights object to local client.
     *
     * @param data      input DRM rights object data to be installed.
     * @param len       the length of the data.
     * @param mimeType  the mime type of this DRM rights object. the value of this field includes:
     *                      #DRM_MIMETYPE_RIGHTS_XML
     *                      #DRM_MIMETYPE_RIGHTS_WBXML
     * @parma rights    the instance of DRMRights to be filled.
     *
     */
    private native int nativeInstallDrmRights(InputStream data, int len, int mimeType, DrmRights rights);

    /**
     * native method: query the given DRM content's rights object.
     *
     * @param content   the given DRM content.
     * @param rights    the instance of rights to set if have.
     *
     */
    private native int nativeQueryRights(DrmRawContent content, DrmRights rights);

    /**
     * native method: get how many rights object in current DRM agent.
     *
     * @return the number of the rights object.
     *         #JNI_DRM_FAILURE if fail.
     */
    private native int nativeGetNumOfRights();


    /**
     * native method: get all the rights object in current local agent.
     *
     * @param rights    the array instance of rights object.
     * @param numRights how many rights can be saved.
     *
     * @return the number of the rights object has been gotten.
     *         #JNI_DRM_FAILURE if fail.
     */
    private native int nativeGetRightsList(DrmRights[] rights, int numRights);

    /**
     * native method: delete a specified rights object.
     *
     * @param rights    the specified rights object to be deleted.
     *
     * @return #JNI_DRM_SUCCESS if succeed.
     *         #JNI_DRM_FAILURE if fail.
     */
    private native int nativeDeleteRights(DrmRights rights);




