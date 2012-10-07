
// This class provides interfaces to access the DRM rights.
public class DrmRights

    // FIXME: permissions

        /**
         * The DRM permission of play.
         */
        public static final int DRM_PERMISSION_PLAY = 1;

        /**
         * The DRM permission of display.
         */
        public static final int DRM_PERMISSION_DISPLAY = 2;

        /**
         * The DRM permission of execute.
         */
        public static final int DRM_PERMISSION_EXECUTE = 3;

        /**
         * The DRM permission of print.
         */
        public static final int DRM_PERMISSION_PRINT = 4;



    /**
     * native method: get the constraint information of the given permission.
     *
     * @param permission    the given permission.
     * @param constraint    the instance of constraint.
     *
     * @return #JNI_DRM_SUCCESS if succeed.
     *         #JNI_DRM_FAILURE if fail.
     */
    private native int nativeGetConstraintInfo(int permission, DrmConstraintInfo constraint);

    /**
     * native method: consume the rights of the given permission.
     *
     * @param permission    the given permission.
     *
     * @return #JNI_DRM_SUCCESS if succeed.
     *         #JNI_DRM_FAILURE if fail.
     */
    private native int nativeConsumeRights(int permission);


