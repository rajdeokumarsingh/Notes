base/core/java/android/content/pm/PathPermission.java 

SecurityException
if (mContext.checkCallingOrSelfPermission(android.Manifest.permission.VIBRATE)
        != PackageManager.PERMISSION_GRANTED) {
    throw new SecurityException("Requires VIBRATE permission");
}

