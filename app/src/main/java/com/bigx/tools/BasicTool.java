/**
 * com.taozhenli.app.tool
 * BasicTool.java
 * 2015年7月6日 下午2:47:53
 * @author: z```s
 */
package com.bigx.tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * <p></p>
 * 2015年7月6日 Administrator
 * @author: z```s
 */
public class BasicTool {

	//private static final String TAG = BasicTool.class.getSimpleName();
	
	/**
	 * <p></p>
	 * @param context
	 * @param text
	 * 2015年7月6日 下午2:49:03
	 * @author: z```s
	 */
	public static void showToast(Context context, CharSequence text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @param resid
	 * 2015年7月6日 下午2:49:36
	 * @author: z```s
	 */
	public static void showToast(Context context, int resid) {
		Toast toast = Toast.makeText(context, resid, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @param text
	 * 2015年7月6日 下午2:49:03
	 * @author: z```s
	 */
	public static void showToast(Context context, CharSequence text, int duration) {
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @param resid
	 * 2015年7月6日 下午2:49:36
	 * @author: z```s
	 */
	public static void showToast(Context context, int resid, int duration) {
		Toast toast = Toast.makeText(context, resid, duration);
		toast.show();
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @param resid
	 * @return
	 * 2015年7月21日 上午11:55:18
	 * @author: z```s
	 */
	public static ProgressDialog showProgressDialog(Context context, int resid) {
		return ProgressDialog.show(context, "", context.getResources().getString(resid), true, false);
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @return
	 * 2015年7月6日 下午3:01:56
	 * @author: z```s
	 */
	public static String getPhoneMacAddress(Context context) {
		String mac = "00-00-00-00-00-00";
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = (null == wifiManager ? null : wifiManager).getConnectionInfo();
		if (null != wifiInfo) {
			if (!TextUtils.isEmpty(wifiInfo.getMacAddress())) {
				mac = wifiInfo.getMacAddress().replace(":", "-");
			}
		}
		return mac;
	}
	
}
