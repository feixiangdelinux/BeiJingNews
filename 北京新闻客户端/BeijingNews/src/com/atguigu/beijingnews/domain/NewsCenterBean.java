package com.atguigu.beijingnews.domain;

import java.util.List;

/**
 * 代表新闻中心的数据
 * @author 杨光福
 * @Time 2015-7-25  上午11:25:10
 */
public class NewsCenterBean {
	
	public List<NewsCenterMenu> data;
	public List extend;
	public String retcode;
	
	/**
	 * 左侧菜单对应的数据
	 * @author 杨光福
	 * @Time 2015-7-27  上午9:23:34
	 */
	public class NewsCenterMenu{
		public List<NewsCenterMenuTag> children;
		public String id;
		public String title;
		public String type;
		public String retcode;
		public String url;
		public String url1;
		public String dayurl;
		public String excurl;
		public String weekurl;
		@Override
		public String toString() {
			return "NewsCenterMenu [children=" + children + ", id=" + id
					+ ", title=" + title + ", type=" + type + ", retcode="
					+ retcode + ", url=" + url + ", url1=" + url1 + ", dayurl="
					+ dayurl + ", excurl=" + excurl + ", weekurl=" + weekurl
					+ "]";
		}
		
		
	}
	
	/**
	 * 左侧菜单对应的页签页面的数据
	 * @author 杨光福
	 * @Time 2015-7-27  上午9:23:57
	 */
	public class NewsCenterMenuTag{
		public String id;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "NewsCenterMenuTag [id=" + id + ", title=" + title
					+ ", type=" + type + ", url=" + url + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "NewsCenterBean [data=" + data + ", extend=" + extend
				+ ", retcode=" + retcode + "]";
	}
	
	

}
