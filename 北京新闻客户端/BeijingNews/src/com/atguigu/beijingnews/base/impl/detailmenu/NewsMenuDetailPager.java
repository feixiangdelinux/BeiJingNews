package com.atguigu.beijingnews.base.impl.detailmenu;

import java.util.ArrayList;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.domain.NewsCenterBean.NewsCenterMenu;
import com.atguigu.beijingnews.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 新闻对应的详情页面
 * @author 杨光福
 * @Time 2015-7-25  下午2:25:37
 */
public class NewsMenuDetailPager extends DetailMenuBasePager {
	
	
	@ViewInject(R.id.indicator_news_menu_detail)
	private TabPageIndicator indicator_news_menu_detail;
	
	@ViewInject(R.id.viewpager_news_menu_detail)
	private ViewPager viewpager_news_menu_detail;
	
//	@ViewInject(R.id.ib_news_menu_next_tab)
//	private ImageButton ib_news_menu_next_tab;

	
	/**
	 * 左侧菜单对应的详情数据
	 */
	private NewsCenterMenu newsCenterMenu;

	/**
	 * 新闻详情页面对应的页签页面的集合
	 */
	private ArrayList<DetailMenuBasePager> tabDetailPagers;


	public NewsMenuDetailPager(Activity activity, NewsCenterMenu newsCenterMenu) {
		super(activity);
		this.newsCenterMenu = newsCenterMenu;
	}

	@Override
	public View initView() {
		View view = View.inflate(activity, R.layout.news_menu_detail, null);
		ViewUtils.inject(this, view);//注入view和事件
		return view;
	}
	
	//初始化数据
	@Override
	public void initData() {
		super.initData();
		tabDetailPagers = new ArrayList<DetailMenuBasePager>();
		for(int i=0;i<newsCenterMenu.children.size();i++){
			//根据有多少个页签对应的数据，创建多少个页面页面
			TabDetailPager detailPager = new TabDetailPager(activity,newsCenterMenu.children.get(i));
			tabDetailPagers.add(detailPager);
		}
		
		
		//设置适配器-把页面当成数据了
		viewpager_news_menu_detail.setAdapter(new MyPagerAdapter());
		
		//ViewPager和TabPageIndicator进行关联
		indicator_news_menu_detail.setViewPager(viewpager_news_menu_detail);
		
		
		//设置点击事件
//		ib_news_menu_next_tab.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				viewpager_news_menu_detail.setCurrentItem(viewpager_news_menu_detail.getCurrentItem()+1);
//				
//			}
//		});
		
		
		//监听ViewPager页面的变化
		//因为ViewPager和TabPageIndicator进行关联，监听页面改变就是用TabPageIndicator
		indicator_news_menu_detail.setOnPageChangeListener(new MyOnPageChangeListener());
		
		
	}
	
	class MyOnPageChangeListener implements OnPageChangeListener{

		
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if(position==0){
				//第一页面：北京
				//设置SlidingMenu可以滑动
				isEnableSlidinMenu(true);
			}else{
				//其他所有的页面，都不可用滑出SlidingMenu
				isEnableSlidinMenu(false);
			}
			
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		
		
	}
	
	//XUtils里面的注解，把点击事件给了nextTable(View view)方法
	//参数不能省略
	@OnClick(R.id.ib_news_menu_next_tab)
	public void nextTab(View view){
		viewpager_news_menu_detail.setCurrentItem(viewpager_news_menu_detail.getCurrentItem()+1);
		
	}
	
	
	/**
	 * 是否让SlidingMenu可以滑动
	 * @param isEnableSlidinMenu
	 */
	private void isEnableSlidinMenu(boolean isEnableSlidinMenu) {
		MainActivity mainActivity = (MainActivity) activity;
		SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
		if(isEnableSlidinMenu){
			
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		
	}

	class MyPagerAdapter extends PagerAdapter{
		
		 /**
		  * 设置标题
		  */
		@Override
		public CharSequence getPageTitle(int position) {
			return newsCenterMenu.children.get(position).title;
		}



		@Override
		public int getCount() {
			return tabDetailPagers.size();
		}
		
		
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			DetailMenuBasePager detailMenuBasePager = tabDetailPagers.get(position);
			View rootView = detailMenuBasePager.rootView;
			detailMenuBasePager.initData();//执行后才会调用TabDetailPager
			container.addView(rootView);
			return rootView;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
			container.removeView((View)object);
			
		}
		
		
	}

}
