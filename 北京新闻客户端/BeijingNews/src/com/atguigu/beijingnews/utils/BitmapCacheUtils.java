package com.atguigu.beijingnews.utils;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * ͼƬ�Ļ���
 * @author ��⸣
 * @Time 2015-7-31  ����11:01:56
 */
public class BitmapCacheUtils {
	
	/**
	 * ���绺��
	 */
	private NetCacheUtils netCacheUtils;
	
	/**
	 * ���ػ���
	 */
	private LocalCacheUtils localCacheUtils;
	
	/**
	 * �ڴ滺��
	 */
	
	private MemoryCacheUtils memoryCacheUtils;
	
	public BitmapCacheUtils(Handler handler){
		memoryCacheUtils = new MemoryCacheUtils();
		localCacheUtils = new LocalCacheUtils();
		netCacheUtils = new NetCacheUtils(handler,localCacheUtils,memoryCacheUtils);
	}


	/**
	 * ����url����ͼƬ
	 * @param listimage
	 * @return
	 */
	public Bitmap getBitmapFromUrl(String listimage,int postion) {
		//1.�ڴ�ȡͼƬ
		if(memoryCacheUtils != null){
			Bitmap bitmap= memoryCacheUtils.getBitmap(listimage);
			if(bitmap != null){
				System.out.println("���ڴ滺����ȡͼƬ��"+postion);
				return bitmap;
			}
		}
		
		
		//2.�ڱ���ȡͼƬ
		if(localCacheUtils != null){
			Bitmap bitmap= localCacheUtils.getBitmapFromUrl(listimage);
			if(bitmap != null){
				System.out.println("�ӱ��ػ�����ȡͼƬ��"+postion);
				return bitmap;
			}
		}
		//3.����������ͼƬ
		if(netCacheUtils != null){
			Bitmap bitmap = netCacheUtils.getBitmapFromUrl(listimage,postion);
			if(bitmap != null){
				System.out.println("�����绺����ȡͼƬ��"+postion);
				return bitmap;
			}
			
		}
		
		return null;
	}

}
