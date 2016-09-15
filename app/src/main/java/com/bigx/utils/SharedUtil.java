/**
 * 2015年5月7日 下午10:05:08
 * SharedPreferencesUtils.java
 * com.yemaozi.shopkeeper.util
 */
package com.bigx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bigx.beans.UserBean;
import com.bigx.tools.BasicTool;

/**
 * <p>处理SharedPreferences工具类</p>
 * SharedUtil
 * 2015年5月7日 下午10:05:08
 * @author z```s
 */
public class SharedUtil {
	
	//夜猫子掌柜登录用户共享缓存文件名
	public static final String USER_SHARED_NAME = "bigx_user";
	
	public static final int LOGIN_STATUS = 100;
	
	public static final String DEFAULT_PREFIX = "taozhenli_";
	
	
	/**
	 * <p>卖家用户缓存文件参数值</p>
	 * SellUserParams
	 * 2015年5月7日 下午10:27:48
	 * @author z```s
	 */
	public static class UserParams {
		public static final String USER_ID = "userId";
		public static final String ACCOUNT = "account";
		public static final String NAME = "name";
		public static final String PORTRAIT_URI = "portraitUri";
		public static final String TOKEN = "token";
		public static final String LOGIN_STATUS = "loginStatus";
	}

	public static class BaseParams {
		public static final String IS_FIRST_USE = "isFirstUse";
	}
	
	/**
	 * <p>获取共享文件SharedPreferences</p>
	 * @param context 上下文
	 * @param name 文件名
	 * @param mode 文件方式
	 * @return
	 * 2015年5月7日 下午10:08:08
	 * @author z```s
	 */
	public static SharedPreferences getAppShared(Context context, String name, int mode) {
		if (context == null || StringUtil.isNullOrEmpty(name) || mode < 0x0000) {
			return null;
		}
		return context.getSharedPreferences(name, mode);
	}
	
	/**
	 * <p>获取共享文件SharedPreferences</p>
	 * @param context
	 * @param name 文件名
	 * @return
	 * 2015年5月7日 下午10:10:02
	 * @author z```s
	 */
	public static SharedPreferences getAppShared(Context context, String name) {
		return getAppShared(context, name, Context.MODE_APPEND);
	}
	
	/**
	 * <p>获取app基础配置共享文件</p>
	 * @param context
	 * @return
	 * 2015年7月2日 下午4:45:25
	 * @author: z```s
	 */
	public static SharedPreferences getAppBaseConfigShared(Context context) {
		return getAppShared(context, DEFAULT_PREFIX + "base");
	}
	
	/**
	 * <p>登录用户缓存文件</p>
	 * @param context
	 * @return
	 * 2015年5月7日 下午10:12:58
	 * @author z```s
	 */
	public static SharedPreferences getUserInfoShared(Context context) {
		return getAppShared(context, USER_SHARED_NAME);
	}

	/**
	 * <p>写入用户缓存数据</p>
	 * @param context
	 * @param user
	 * 2015年5月7日 下午10:22:44
	 * @author z```s
	 */
	public static boolean writeSharedUserInfo(Context context, UserBean user) {
		if (null == context || null == user) {
			return false;
		}
		SharedPreferences pref = getUserInfoShared(context);
		Editor editor = pref.edit();
		editor.putLong(UserParams.USER_ID, user.userId);
		editor.putString(UserParams.NAME, user.name);
		editor.putString(UserParams.ACCOUNT, user.account);
		editor.putString(UserParams.PORTRAIT_URI, user.portraitUri);
		editor.putString(UserParams.TOKEN, user.token);
		editor.putInt(UserParams.LOGIN_STATUS, LOGIN_STATUS);
		return editor.commit();
	}
	
	/**
	 * <p>读用户缓存信息</p>
	 * @param context
	 * @return
	 * 2015年5月7日 下午10:56:44
	 * @author z```s
	 */
	public static UserBean readSharedUserInfo(Context context) {
		if (null == context) {
			return null;
		}
		if (!isLogin(context)) {
			return null;
		}
		SharedPreferences pref = getUserInfoShared(context);
		long id = pref.getLong(UserParams.USER_ID, 0l);
		if (id < 1l) {
			return null;
		}
		UserBean user = new UserBean();
		user.userId = id;
		user.name = pref.getString(UserParams.NAME, "");
		user.account = pref.getString(UserParams.ACCOUNT, "");
		user.portraitUri = pref.getString(UserParams.PORTRAIT_URI, "");
		user.token = pref.getString(UserParams.TOKEN, "");
		return user;
	}
	
	/**
	 * <p>清除指定缓存数据</p>
	 * @param context
	 * @param name
	 * @param mode
	 * @return
	 * 2015年5月7日 下午11:17:16
	 * @author z```s
	 */
	public static boolean clearSharedData(Context context, String name, int mode) {
		if (null == context || StringUtil.isNullOrEmpty(name) || mode < 0x0000) {
			return false;
		}
		return getAppShared(context, name, mode).edit().clear().commit();
	}
	
	/**
	 * <p>清除指定缓存数据</p>
	 * @param context
	 * @param name
	 * @return
	 * 2015年5月7日 下午11:18:17
	 * @author z```s
	 */
	public static boolean clearSharedData(Context context, String name) {
		return clearSharedData(context, name, Context.MODE_APPEND);
	}
	
	/**
	 * <p>清楚用户缓存数据</p>
	 * @param context
	 * @return
	 * 2015年5月7日 下午11:12:48
	 * @author z```s
	 */
	public static boolean clearSharedUserInfo(Context context) {
		return clearSharedData(context, USER_SHARED_NAME);
	}
	
	/**
	 * <p>判断用户是否登录</p>
	 * @param context
	 * @return
	 * 2015年5月7日 下午11:09:53
	 * @author z```s
	 */
	public static boolean isLogin(Context context) {
		if (null == context) {
			return false;
		}
		boolean result;
		SharedPreferences pref = getUserInfoShared(context);
		long id = pref.getLong(UserParams.USER_ID, 0l);
		int loginStatus = pref.getInt(UserParams.LOGIN_STATUS, 0);
		if (id < 1l || loginStatus != LOGIN_STATUS) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
	
	/**
	 * <p>更改用户信息为注销信息</p>
	 * @param context
	 * @return
	 * 2015年5月7日 下午11:21:33
	 * @author z```s
	 */
	public static boolean updateLogoutSharedUserInfo(Context context) {
		if (null == context) {
			return false;
		}
		SharedPreferences pref = getUserInfoShared(context);
		Editor editor = pref.edit();
		editor.clear();
		editor.putLong(UserParams.USER_ID, 0l);
		editor.putInt(UserParams.LOGIN_STATUS, 0);
		return editor.commit();
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @return
	 * 2015年7月2日 下午4:50:22
	 * @author: z```s
	 */
	public static boolean isFirstUse(Context context) {
		if (null == context) {
			return true;
		}
		SharedPreferences pref = getAppBaseConfigShared(context);
		boolean isFirstUse = pref.getBoolean(BaseParams.IS_FIRST_USE, true);
		return isFirstUse;
	}
	
	/**
	 * <p></p>
	 * @param context
	 * @return
	 * 2015年7月2日 下午4:52:31
	 * @author: z```s
	 */
	public static boolean updateFirstUse(Context context, boolean rel) {
		if (null == context) {
			return false;
		}
		SharedPreferences pref = getAppBaseConfigShared(context);
		Editor editor = pref.edit();
		editor.putBoolean(BaseParams.IS_FIRST_USE, rel);
		return editor.commit();
	}
	
}
