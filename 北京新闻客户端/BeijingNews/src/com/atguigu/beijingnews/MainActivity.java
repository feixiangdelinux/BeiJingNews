package com.atguigu.beijingnews;

import com.atguigu.beijingnews.fragment.ContentFragment;
import com.atguigu.beijingnews.fragment.LeftmenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

/**
 * 主页面
 * @author 杨光福
 *
 */
public class MainActivity extends SlidingFragmentActivity {
	
	public static final String MAIN_FRAMENT_TAG = "main_frament_tag";
	public static final String LEFT_MENUT_TAG = "left_menut_tag";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//把标题隐藏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		//主页面
		setContentView(R.layout.activity_main);
		
		//设置左侧菜单
		setBehindContentView(R.layout.activity_leftmenu);
		
		SlidingMenu slidingMenu = getSlidingMenu();
		
		//设置支持风格：主页面+左侧菜单
		slidingMenu.setMode(SlidingMenu.LEFT);
		
		//设置滑动模式：全屏滑动
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		//设置主页面的距离：200
		slidingMenu.setBehindOffset(200);
		
		initFragment();
	}
	
	/**
	 * 得到左侧菜单
	 * @return
	 */
	public LeftmenuFragment getLeftmenuFragment(){
		FragmentManager manager = getSupportFragmentManager();
		LeftmenuFragment leftmenuFragment = (LeftmenuFragment) manager.findFragmentByTag(LEFT_MENUT_TAG);
		return leftmenuFragment;
	}
	
	/**
	 * 得到正文Fragment
	 * @return
	 */
	public ContentFragment getContentFragment(){
		FragmentManager manager = getSupportFragmentManager();
		ContentFragment contentFragment = (ContentFragment) manager.findFragmentByTag(MAIN_FRAMENT_TAG);
		return contentFragment;
	}

	/**
	 * 把布局文件替换成Fragment
	 */
	private void initFragment() {
		//1.得到FragmentManager
		FragmentManager manager = getSupportFragmentManager();
		//2.开启事务
		FragmentTransaction transaction =manager.beginTransaction();
		
		//3.替换
		transaction.replace(R.id.fl_leftmenue, new LeftmenuFragment(), LEFT_MENUT_TAG);
		transaction.replace(R.id.fl_main, new ContentFragment(), MAIN_FRAMENT_TAG);
		
		//4.提交
		transaction.commit();
		
	}

}
