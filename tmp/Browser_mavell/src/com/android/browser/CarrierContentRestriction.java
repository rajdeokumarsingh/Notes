/*
 * Copyright (C) 2008 Esmertec AG.
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.browser;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.util.Log;


 

public class CarrierContentRestriction {
    private static final String TAG = "CarrierContentRestriction";
    private static final ArrayList<String> sSupportedImageTypes;
    private static final ArrayList<String> sSupportedAudioTypes;
    private static final ArrayList<String> sSupportedVideoTypes;
    private static final ArrayList<String> sSupportedTypes;

    static {
        sSupportedImageTypes = ContentType.getImageTypes();
        sSupportedAudioTypes = ContentType.getAudioTypes();
        sSupportedVideoTypes = ContentType.getVideoTypes();
        sSupportedTypes = ContentType.getSupportedTypes();
    }

    public CarrierContentRestriction() {
    }
 
    public boolean checkImageContentType(String contentType){
         return (null != contentType)&&sSupportedImageTypes.contains(contentType) ; 
     }

    public boolean checkAudioContentType(String contentType) {
    	return (null != contentType)&&sSupportedAudioTypes.contains(contentType) ;
    }

    public boolean checkVideoContentType(String contentType) {
    	return (null != contentType)&&sSupportedVideoTypes.contains(contentType) ;
    }
    
    public boolean checkSupportContentType(String contentType){
        return (null != contentType)&&sSupportedTypes.contains(contentType);
    }
    
    public boolean checkAndroidAPK(String contentType){
        return (null != contentType)&&("application/vnd.android.package-archive".equals(contentType));
    }
}
