
////////////////////////////////////////////////////////////////////////////////
// Browser

// src/com/android/browser/BrowserSettings.java
syncSharedPreferences(Context ctx, SharedPreferences p)
    // get setting from preference
    loadsPageInOverviewMode = p.getBoolean("load_page", loadsPageInOverviewMode);


public void update(Observable o, Object arg)
    s.setLoadWithOverviewMode(b.loadsPageInOverviewMode);
            |
            V
////////////////////////////////////////////////////////////////////////////////
// framework
////////////////////////////////////////////////////////////////////////////////

// base/core/java/android/webkit/WebSettings.java

// Set whether the WebView loads a page with overview mode.
public void setLoadWithOverviewMode(boolean overview)
    mLoadWithOverviewMode = overview;


// Returns true if this WebView loads page with overview mode
public boolean getLoadWithOverviewMode() 
    return mLoadWithOverviewMode;
            ^
            | WebView get the overview settings
// base/core/java/android/webkit/WebView.java

private void updateZoomRange(WebViewCore.RestoreState restoreState,
            int viewWidth, int minPrefWidth, boolean updateZoomOverview) 





