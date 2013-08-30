/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager.utils;


public class InnerClass {
	final static String LOG_TAG = "InnerClass";


	public static Object[] StorageManager_getVolumeList(Object instance) {
		try {
			return (Object[]) Reflector.invokeMethod(instance, "getVolumeList", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] StorageManager_getVolumePaths(Object instance) {
		try {
			return (String[]) Reflector.invokeMethod(instance, "getVolumePaths", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String StorageManager_getVolumeState(Object instance, String mountPoint) {
		try {
			return (String) Reflector.invokeMethod(instance, "getVolumeState", new Class[] { String.class },
					new Object[] { mountPoint });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static boolean StorageVolume_isRemovable(Object instance) {
		try {
			return (Boolean) Reflector.invokeMethod(instance, "isRemovable", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int StorageVolume_getStorageId(Object instance) {
		try {
			return (Integer) Reflector.invokeMethod(instance, "getStorageId", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
