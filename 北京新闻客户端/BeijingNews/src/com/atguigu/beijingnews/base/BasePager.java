package com.atguigu.beijingnews.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;

/**
 * 基本的页面：是首页，新闻中心，智慧服务，政要指南，设置的公共类
 * @author 杨光福
 *
 */
public class BasePager {

	/**
	 * 上下文
	 */
	public Activity activity;
	
	/**
	 * 菜单按钮
	 */
	public ImageButton ib_base_menu;
	
	/**
	 * 标题
	 */
	public TextView tv_titlebar;
	
	/**
	 * 内容
	 */
	public FrameLayout fl_content_fragment;
	
	/**
	 * 公共视图
	 */
	public View rootView;
	
	public ImageButton ib_base_switch_list_grid;

	public BasePager(Activity activity) {
		this.activity = activity;
		
		rootView = initView();
		
	}

	private View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(activity, R.layout.base_pager, null);
		ib_base_menu = (ImageButton) view.findViewById(R.id.ib_base_menu);
		tv_titlebar = (TextView) view.findViewById(R.id.tv_titlebar);
		fl_content_fragment = (FrameLayout) view.findViewById(R.id.fl_content_fragment);
		ib_base_switch_list_grid = (ImageButton) view.findViewById(R.id.ib_base_switch_list_grid);
		
		ib_base_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//2.把SlidingMenu菜单收
				MainActivity mainActivity = (MainActivity) activity;
				mainActivity.getSlidingMenu().toggle();//开<->关
			}
		});
		return view;
	}
	
	
	/**
	 * 当孩子需要实现特有的数据的时候，重新该方法
	 */
	public void initData(){
		
	}
	
	

}
