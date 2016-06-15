package com.atguigu.beijingnews.utils;

/**
 * 软件的参数类
 * @author 杨光福
 * @Time 2015-7-25  上午11:13:08
 */
public class ConstantUtils {
	public static String base_ip =  "http://192.168.1.100";
	public static String base_url =  base_ip+":8080/zhbj";

	
	/**
	 * 新闻中心网络请求链接
	 */
	public static String newscenter_url = base_url+"/categories.json";
	
	
	/**
	 * 图组的网络地址
	 */
	public static String photos_url = base_url+ "/photos/photos_1.json";
}
