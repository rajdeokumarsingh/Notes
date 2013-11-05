package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
	private static final boolean ENABLE_DEBUG = true;

	public static String call = "\"result\":0"; 
	
	public static String call1 = "\"result\":\"0\""; 
	
	public static String AddLabel ="testaddname";
	
	public static String AddPolicy ="testaddPolicy";
	
	public static String AddSetting  = "testAddSetting1";
	public static String token;

	public static String mDevice_ids = "343";

	public static String mDevice_UUid = "";
	
	public static int mAppid;

	public static int mPolicyID;

	public static int mSettingID;

	public static int mDocID;

	public static int mUserId;

	public static int mlabelid;

	public static List<DevicesInfo> devices;
	
	public static final String mAddUserName = "adminxxd";

	public static String mFeedbackID = "";

	public static String mIOS_Device_uuid = "";

	public static int mSwitch = 0;

	public static Map<String, TestErrorInfo> ErrorLog = new HashMap<String, TestErrorInfo>();// TestErrorInfo

	public static void log(String Mes) {
		if (ENABLE_DEBUG) {
			StackTraceElement st = Thread.currentThread().getStackTrace()[3];
			System.out.println("" + "[" + st.getFileName() + ":"
					+ st.getLineNumber() + "]->" + Mes);
		}
	}

	public static void Showlog(Boolean show, String Mes) {
		if (ENABLE_DEBUG && show) {
			StackTraceElement st = Thread.currentThread().getStackTrace()[0];
			System.out.println("" + "[" + st.getFileName() + ":"
					+ st.getLineNumber() + "]->" + Mes);
		}
	}

	public static void addErrorinfo(String s1, String s2) {
		
		if (Util.mSwitch == 0) {
			TestErrorInfo mTestErrorInfo = new TestErrorInfo("" + s1, "内网异常 " + s2);
			Util.ErrorLog.put("" + s1, mTestErrorInfo);
		} else if (Util.mSwitch == 1) {
			TestErrorInfo mTestErrorInfo = new TestErrorInfo("" + s1, "外网异常 " + s2);
			Util.ErrorLog.put("" + s1, mTestErrorInfo);
		}
	}
	
	public static void getTestDevId(int num)
	{
		int[] s ;  
		s = new int[num*10000] ;  
		for(int i=0;i<s.length;i++)
		{
			s[i]=10000+i;
		}
	}

    /**
     * Sleep for a few seconds
     * @param seconds
     */
    public static final void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
