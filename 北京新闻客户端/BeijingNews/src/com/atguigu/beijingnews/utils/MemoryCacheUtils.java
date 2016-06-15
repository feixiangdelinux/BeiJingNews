package com.atguigu.beijingnews.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * �ڴ滺��
 * @author ��⸣
 * @Time 2015-7-31  ����2:17:12
 */
public class MemoryCacheUtils {
	
	/**
	 * ʹ���������ʹ���㷨�ļ���
	 */
	private LruCache<String, Bitmap> cache;

	public MemoryCacheUtils(){
		//�������8��֮һ
		int maxSize = (int) Runtime.getRuntime().maxMemory();//16MB
		cache = new LruCache<String, Bitmap>(maxSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
	}
	

	/**
	 * ��ͼƬ���浽�ڴ���
	 * @param url
	 * @param bitmap
	 */
	public void putBitmap(String url, Bitmap bitmap) {
		
		cache.put(url, bitmap);
		
	}

	/**
	 * ����Url���ڴ��в���ͼƬ
	 * @param url
	 * @return
	 */
	public Bitmap getBitmap(String url) {
		Bitmap bitmap = cache.get(url);
		
		return bitmap;
	}

}
