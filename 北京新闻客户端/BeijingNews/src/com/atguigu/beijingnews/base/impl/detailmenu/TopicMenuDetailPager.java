package com.atguigu.beijingnews.base.impl.detailmenu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 专题对应的详情页面
 * @author 杨光福
 * @Time 2015-7-25  下午2:25:37
 */
public class TopicMenuDetailPager extends DetailMenuBasePager {

	public TopicMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {

		TextView tv = new TextView(activity);
		tv.setText("专题对应的详情页面");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		// 添加到内容里面
		return tv;
	}

}
