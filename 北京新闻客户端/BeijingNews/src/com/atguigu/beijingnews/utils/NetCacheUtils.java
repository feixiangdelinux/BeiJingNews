package com.atguigu.beijingnews.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

/**
 * 网络缓存的工具类
 * @author 杨光福
 * @Time 2015-7-31  上午11:05:40
 */
public class NetCacheUtils {


	/**
	 * 请求图片成功
	 */
	public static final int SUCCESS = 1;
	/**
	 * 请求图片失败
	 */
	public static final int FAIL = 2;
	private Handler handler;
	private ExecutorService service;
	/**
	 * 本地缓存
	 */
	private LocalCacheUtils localCacheUtils;
	/**
	 * 内存缓存
	 */
	private MemoryCacheUtils memoryCacheUtils;

	public NetCacheUtils(Handler handler,LocalCacheUtils localCacheUtils,MemoryCacheUtils memoryCacheUtils) {
		this.handler = handler;
		this.localCacheUtils = localCacheUtils;
		this.memoryCacheUtils = memoryCacheUtils;
		//线程池的使用
		service = Executors.newFixedThreadPool(10);
	}

	public Bitmap getBitmapFromUrl(String listimage,int postion) {
		
		
		service.execute(new MyThread(listimage,postion));

//		new Thread(new MyThread(listimage,postion)).start();//创建子线程加载图片
		
		return null;
	}
	
	class MyThread implements Runnable{
		
		private String url;
		private int position;

		public MyThread(String url,int position){
			this.url = url;
			this.position = position;
		}

		@Override
		public void run() {

			
			try {
				HttpURLConnection conn =  (HttpURLConnection) new URL(url).openConnection();
				conn.setRequestMethod("GET");//请求方法GET
				conn.setReadTimeout(4000);//读取超时时间
				conn.setConnectTimeout(4000);//链接超时时间
				conn.connect();//链接
				int code = conn.getResponseCode();
				if(code ==200){
					InputStream is = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					
					is.close();
					conn.disconnect();
					
					Message msg = Message.obtain();
					msg.what = SUCCESS;
					msg.obj = bitmap;
					msg.arg2 = position;
					handler.sendMessage(msg );
					
					
					//保存到内存中一份
					memoryCacheUtils.putBitmap(url,bitmap);
					//保存到sdcard中一份
					localCacheUtils.putBitmap(url,bitmap);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Message msg = Message.obtain();
				msg.what = FAIL;
				handler.sendMessage(msg );
				
				e.printStackTrace();
			}
			
		}
		
	}

}
