package com.atguigu.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	/**
	 * 这个方法不能少些，在布局文件使用的时候
	 * @param context
	 * @param attrs
	 */
	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
//		super.onTouchEvent(arg0);
		return false;
	}
	
	/**
	 * 拦截事件的方法，返回true:拦截了，触发当前控件的onTouchEvent
	 * 返回false，事件继续传递给孩子
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
