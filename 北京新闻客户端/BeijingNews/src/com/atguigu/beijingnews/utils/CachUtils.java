package com.atguigu.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 记录软件的一些参数
 * 
 * @author 杨光福
 * 
 */
public class CachUtils {
	private static SharedPreferences sp;

	/**
	 * 取boolean 如果为true:参数保存过
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		if (sp == null) {
			 sp = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}

		return sp.getBoolean(key, false);
	}

	/**
	 * 保存一个boolean类型的参数
	 * @param context
	 * @param key
	 * @param values
	 */
	public static void setBoolean(Context context, String key, boolean values) {
		if (sp == null) {
			 sp = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		Editor editors = sp.edit();
		editors.putBoolean(key, values);
		editors.commit();
	}
	
	
	/**
	 * 保存软件的缓存信息
	 * @param context 上下文
	 * @param key 
	 * @param values
	 */
	public static void setString(Context context, String key, String values) {
		if (sp == null) {
			 sp = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}
		Editor editors = sp.edit();
		editors.putString(key, values);
		editors.commit();
	}
	
	
	/**
	 * 得到软件保存的缓存信息
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		if (sp == null) {
			 sp = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
		}

		return sp.getString(key, "");
	}
	
	
	

}
