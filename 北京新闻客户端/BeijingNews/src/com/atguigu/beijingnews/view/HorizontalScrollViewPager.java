package com.atguigu.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalScrollViewPager extends ViewPager {

	/**
	 * 在布局文件中实例化该类，采用这个构造方法-不能少些，否则崩溃
	 * @param context
	 * @param attrs
	 */
	public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private float startX;
	private float startY;
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//1.竖直方向滑动，让父类拦截事件，设置为false
		
		//2.水平方向滑动
		//2.1当滑动图片第0个位置的时候，让父类拦截事件，设置false
		//2.2.当滑动图片最后一个位置的时候，让父类拦截事件，设置为false
		//2.3正常滑动，让父类不拦截当前控件的事件，这样当前控件就可以正常的滑动，设置为true
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			//1.记录起始坐标
			 startX =ev.getX();
			 startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			//2.来到新的坐标
			float newX = ev.getX();
			float newY = ev.getY();
			//3.计算距离
			float distancesX = newX - startX;
			float distanceY = newY - startY;
			
			//水平方向滑动
			if(Math.abs(distancesX)> Math.abs(distanceY)){
				//2.1当滑动图片第0个位置的时候，让父类拦截事件，设置false
				if(getCurrentItem()==0&&distancesX>0){
					getParent().requestDisallowInterceptTouchEvent(false);
					//2.2.当滑动图片最后一个位置的时候，让父类拦截事件，设置为false
				}else if(getCurrentItem()==(getAdapter().getCount()-1)&&distancesX<0){
					getParent().requestDisallowInterceptTouchEvent(false);
					//2.3正常滑动，让父类不拦截当前控件的事件，这样当前控件就可以正常的滑动，设置为true
				}else{
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				
			}else{
				//竖直方向滑动
				
				//1.竖直方向滑动，让父类拦截事件，设置为false
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			
			
			
			break;
		case MotionEvent.ACTION_UP:
			
			break;

		default:
			break;
		}
		
		
		return super.dispatchTouchEvent(ev);
	}
	

}
