package com.sdk.example.app.theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sdk.example.SdkMainActivity;

public class ThemeExamples extends SdkMainActivity {
	protected List<Map<String, Object>> getData() {
		List<Map<String, Object>> nameList = new ArrayList<Map<String, Object>>();

		nameList.add(addItem(ThemeNoTitleBar.class, "Theme no title"));
		nameList.add(addItem(ThemeNoTitleBarFullScreen.class, "Theme no title, full screen"));
		nameList.add(addItem(ThemeLight.class, "Theme light"));
		nameList.add(addItem(ThemeLightNoTitleBarFullScreen.class, "Theme light, no title, full screen"));
		nameList.add(addItem(ThemeBlack.class, "Theme black"));
		nameList.add(addItem(ThemeWallpaper.class, "Theme wallpaper"));
		nameList.add(addItem(ThemeWallpaperSettings.class, "Theme wallpaper settings"));
		nameList.add(addItem(ThemeTranslucent.class, "Theme translucent"));
		nameList.add(addItem(ThemeDialog.class, "Theme dialog"));
//		nameList.add(addItem(ThemeDialogAlert.class, "Theme dialog, alert"));
//		nameList.add(addItem(ThemeDialogNoFrame.class, "Theme dialog, no frame"));
		nameList.add(addItem(ThemePanel.class, "Theme, panel"));
		nameList.add(addItem(ThemeHoloPanel.class, "Theme, holo panel"));
		nameList.add(addItem(ThemeActionBar.class, "Theme Action Bar"));

//		nameList.add(addItem(ThemeNoDisplay.class, "Theme no display"));
		return nameList;
	}
}
