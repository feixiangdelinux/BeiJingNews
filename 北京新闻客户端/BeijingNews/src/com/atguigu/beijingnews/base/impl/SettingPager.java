package com.atguigu.beijingnews.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnews.base.BasePager;

/**
 * ��������
 * @author ��⸣
 *
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}
	@Override
	public void initData() {
		super.initData();
		System.out.println("�����е����ݱ���ʼ����...");
		tv_titlebar.setText("����");
		
		TextView tv = new TextView(activity);
		tv.setText("�������ĵ�����");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(Color.RED);
		//��ӵ���������
		fl_content_fragment.addView(tv);
		
	}

}
