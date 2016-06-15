package com.atguigu.beijingnews.domain;

import java.util.List;

/**
 * 图组详情页面的数据
 * @author 杨光福
 * @Time 2015-7-31  上午9:38:36
 */
public class PhotosMenuDetailBean {
	
	public PhotosMenuDetailData data;
	
	public String retcode;
	
	
	public class PhotosMenuDetailData{
		public String countcommenturl;
		public String more;
		public List<News> news;
		public String title;
		public List topic;
		@Override
		public String toString() {
			return "PhotosMenuDetailData [countcommenturl=" + countcommenturl
					+ ", more=" + more + ", news=" + news + ", title=" + title
					+ ", topic=" + topic + "]";
		}
		
		
	}
	
	public class News{
		public Boolean comment;
		public String commentlist;
		public String commenturl;
		public String id;
		public String largeimage;
		public String listimage;
		public String pubdate;
		public String smallimage;
		public String title;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "News [comment=" + comment + ", commentlist=" + commentlist
					+ ", commenturl=" + commenturl + ", id=" + id
					+ ", largeimage=" + largeimage + ", listimage=" + listimage
					+ ", pubdate=" + pubdate + ", smallimage=" + smallimage
					+ ", title=" + title + ", type=" + type + ", url=" + url
					+ "]";
		}
		
		
		
	}

}
