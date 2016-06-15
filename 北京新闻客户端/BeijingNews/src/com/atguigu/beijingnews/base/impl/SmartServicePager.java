package com.atguigu.beijingnews.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BasePager;

/**
 * 智慧服务
 * @author 杨光福
 *
 */
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		super.initData();
		System.out.println("智慧服务的数据被初始化了...");
		tv_titlebar.setText("智慧服务");
		
		TextView tv = new TextView(activity);
		tv.setText("智慧服务的内容");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.RED);
		//添加到内容里面
		fl_content_fragment.addView(tv);
		
	}

}
