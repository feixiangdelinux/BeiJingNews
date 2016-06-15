package com.atguigu.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalScrollViewPager extends ViewPager {

	/**
	 * �ڲ����ļ���ʵ�������࣬����������췽��-������Щ���������
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
		//1.��ֱ���򻬶����ø��������¼�������Ϊfalse
		
		//2.ˮƽ���򻬶�
		//2.1������ͼƬ��0��λ�õ�ʱ���ø��������¼�������false
		//2.2.������ͼƬ���һ��λ�õ�ʱ���ø��������¼�������Ϊfalse
		//2.3�����������ø��಻���ص�ǰ�ؼ����¼���������ǰ�ؼ��Ϳ��������Ļ���������Ϊtrue
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			//1.��¼��ʼ����
			 startX =ev.getX();
			 startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			//2.�����µ�����
			float newX = ev.getX();
			float newY = ev.getY();
			//3.�������
			float distancesX = newX - startX;
			float distanceY = newY - startY;
			
			//ˮƽ���򻬶�
			if(Math.abs(distancesX)> Math.abs(distanceY)){
				//2.1������ͼƬ��0��λ�õ�ʱ���ø��������¼�������false
				if(getCurrentItem()==0&&distancesX>0){
					getParent().requestDisallowInterceptTouchEvent(false);
					//2.2.������ͼƬ���һ��λ�õ�ʱ���ø��������¼�������Ϊfalse
				}else if(getCurrentItem()==(getAdapter().getCount()-1)&&distancesX<0){
					getParent().requestDisallowInterceptTouchEvent(false);
					//2.3�����������ø��಻���ص�ǰ�ؼ����¼���������ǰ�ؼ��Ϳ��������Ļ���������Ϊtrue
				}else{
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				
			}else{
				//��ֱ���򻬶�
				
				//1.��ֱ���򻬶����ø��������¼�������Ϊfalse
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
