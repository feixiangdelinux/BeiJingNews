package com.atguigu.beijingnews.utils;

/**
 * ����Ĳ�����
 * @author ��⸣
 * @Time 2015-7-25  ����11:13:08
 */
public class ConstantUtils {
	public static String base_ip =  "http://192.168.1.100";
	public static String base_url =  base_ip+":8080/zhbj";

	
	/**
	 * ��������������������
	 */
	public static String newscenter_url = base_url+"/categories.json";
	
	
	/**
	 * ͼ��������ַ
	 */
	public static String photos_url = base_url+ "/photos/photos_1.json";
}
