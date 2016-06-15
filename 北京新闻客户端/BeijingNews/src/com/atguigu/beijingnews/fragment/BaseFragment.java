package com.atguigu.beijingnews.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ���˵���Ӧ��Fragment
 * @author ��⸣
 *
 */
public abstract class BaseFragment extends Fragment {
	
	
	/**
	 * Ҫ�󶨵���Ӧ��Actity��,��ʵ����������
	 */
	public Activity activity;

	/**
	 * ��Fragment��������ʱ�򱻻ص��������
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}
	
	/**
	 * ��Fragment����ͼ����������ʱ��ص�
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = initView();
		return view;
	}

	/**
	 * ��ÿ������ʵ���Լ��Ĳ��֣�ʵ�ֲ�ͬ�ķ��Ч��
	 * ÿ�����Ӷ�Ҫʵ��
	 * @return
	 */
	public abstract View initView();

	/**
	 * ��Activity�������˵�ʱ�򣬸ķ������ص�
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);//�����������ʡ��
		initData();
	}

	/**
	 * ������ʵ�����������ʱ�򣬳�ʼ����������
	 */
	public void initData() {
		
	}

	
	
	

}
