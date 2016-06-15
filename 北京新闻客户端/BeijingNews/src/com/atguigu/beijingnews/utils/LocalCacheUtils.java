package com.atguigu.beijingnews.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class LocalCacheUtils {

	private String dirPath = "/mnt/sdcard/beijingnews";


	/**
	 * ����url����ͼƬ
	 * @param url
	 * @param bitmap
	 */
	public void putBitmap(String url, Bitmap bitmap) {
		try {
			
			String fileName = MD5Encoder.encode(url);
			///mnt/sdcard/beijingnews/lskkskkskjkjsk
			File file = new File(dirPath , fileName);
			
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			
			FileOutputStream fls = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, fls);
			fls.flush();
			fls.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
    /**
     * ����url������ȡͼƬ
     * @param listimage
     * @return
     */
	public Bitmap getBitmapFromUrl(String url) {
		// TODO Auto-generated method stub
		
		try {
			String fileName = MD5Encoder.encode(url);
			///mnt/sdcard/beijingnews/lskkskkskjkjsk
			File file = new File(dirPath , fileName);
			
			FileInputStream fis = new FileInputStream(file);
			Bitmap bitmap = BitmapFactory.decodeStream(fis);
			fis.close();
			if(bitmap != null){
				return bitmap;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

}
