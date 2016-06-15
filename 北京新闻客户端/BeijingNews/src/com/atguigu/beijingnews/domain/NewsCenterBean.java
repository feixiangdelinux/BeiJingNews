package com.atguigu.beijingnews.domain;

import java.util.List;

/**
 * �����������ĵ�����
 * @author ��⸣
 * @Time 2015-7-25  ����11:25:10
 */
public class NewsCenterBean {
	
	public List<NewsCenterMenu> data;
	public List extend;
	public String retcode;
	
	/**
	 * ���˵���Ӧ������
	 * @author ��⸣
	 * @Time 2015-7-27  ����9:23:34
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
	 * ���˵���Ӧ��ҳǩҳ�������
	 * @author ��⸣
	 * @Time 2015-7-27  ����9:23:57
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
