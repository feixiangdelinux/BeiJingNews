package com.atguigu.beijingnews.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 左侧菜单对应的Fragment
 * @author 杨光福
 *
 */
public abstract class BaseFragment extends Fragment {
	
	
	/**
	 * 要绑定到对应的Actity上,其实就是上下文
	 */
	public Activity activity;

	/**
	 * 当Fragment被创建的时候被回调这个方法
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}
	
	/**
	 * 当Fragment的视图，被创建的时候回调
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = initView();
		return view;
	}

	/**
	 * 让每个孩子实现自己的布局，实现不同的风格效果
	 * 每个孩子都要实现
	 * @return
	 */
	public abstract View initView();

	/**
	 * 当Activity被创建了的时候，改方法被回调
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);//这个方法不能省略
		initData();
	}

	/**
	 * 当孩子实现这个方法的时候，初始化特有数据
	 */
	public void initData() {
		
	}

	
	
	

}
