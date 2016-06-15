package com.atguigu.beijingnews.base.impl.detailmenu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * ר���Ӧ������ҳ��
 * @author ��⸣
 * @Time 2015-7-25  ����2:25:37
 */
public class TopicMenuDetailPager extends DetailMenuBasePager {

	public TopicMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {

		TextView tv = new TextView(activity);
		tv.setText("ר���Ӧ������ҳ��");
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(25);
		tv.setTextColor(Color.RED);
		// ��ӵ���������
		return tv;
	}

}
