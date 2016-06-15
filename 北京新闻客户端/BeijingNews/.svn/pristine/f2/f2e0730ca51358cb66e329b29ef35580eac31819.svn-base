package com.atguigu.beijingnews.fragment;

import java.util.ArrayList;

import com.atguigu.beijingnews.MainActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.base.BasePager;
import com.atguigu.beijingnews.base.impl.GovaffairsPager;
import com.atguigu.beijingnews.base.impl.HomePager;
import com.atguigu.beijingnews.base.impl.NewsCenterPager;
import com.atguigu.beijingnews.base.impl.SettingPager;
import com.atguigu.beijingnews.base.impl.SmartServicePager;
import com.atguigu.beijingnews.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 主页Fragment
 * @author 杨光福
 *
 */
public class ContentFragment extends BaseFragment {
	
	@ViewInject(R.id.viewpager_conteng_fragment)
	private NoScrollViewPager viewpager_conteng_fragment;
	
	
	@ViewInject(R.id.rg_content_fragment)
	private RadioGroup rg_content_fragment;
	
	/**
	 * 里面装的是5个页面，数据
	 */
	private ArrayList<BasePager> basePagers;

	@Override
	public View initView() {
		// TODO Auto-generated method stub
//		TextView textView = new TextView(activity);
//		textView.setText("主页菜单");
//		textView.setTextSize(25);
//		textView.setTextColor(Color.BLACK);
		View view = View.inflate(activity, R.layout.content_fragment, null);
		//把View注入到XUtils框架中：注入View和事件
		ViewUtils.inject(this, view);
//		viewpager_conteng_fragment = (ViewPager) view.findViewById(R.id.viewpager_conteng_fragment);
		
		return view;
	}
	
	@Override
	public void initData() {
		super.initData();//执行父类的initData()方法，如果是空的考虑注释点，但是一般建议不注释
		//设置默认首页
		rg_content_fragment.check(R.id.rb_home);
		
		//初始化数据-5个页面-添加5个页面
		 basePagers = new ArrayList<BasePager>();
		 basePagers.add(new HomePager(activity));
		 basePagers.add(new NewsCenterPager(activity));
		 basePagers.add(new SmartServicePager(activity));
		 basePagers.add(new GovaffairsPager(activity));
		 basePagers.add(new SettingPager(activity));
		
		//设置适配器
		
		viewpager_conteng_fragment.setAdapter(new MyPagerAdapter());
		
		
		//设置监听RadioGrou的变化，跳转到对应的页面（viewapger）
		rg_content_fragment.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		
		//设置默认不可用滑动
		isEnableSlidingMenu(false);
		
		//监听Viewpager那个页面被选中
		viewpager_conteng_fragment.setOnPageChangeListener(new MyOnPageChangeListener());
		
		basePagers.get(0).initData();
	}
	
	class MyOnPageChangeListener implements OnPageChangeListener{

		
		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			basePagers.get(position).initData();
			
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
	
	/**
	 * 是否设置支持SlidingMenu可以侧滑
	 * true:可以
	 * false:不可用侧滑
	 * @param isEnableSlidingMenu 
	 */
	private void isEnableSlidingMenu(boolean isEnableSlidingMenu) {
		MainActivity mainActivity = (MainActivity) activity;
		SlidingMenu slideMenu = mainActivity.getSlidingMenu();
		if(isEnableSlidingMenu){
			slideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slideMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	class MyOnCheckedChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			switch (checkedId) {
			case R.id.rb_home://首页
				
				viewpager_conteng_fragment.setCurrentItem(0);
				//不可用滑动
				isEnableSlidingMenu(false);
				break;
			case R.id.rb_newscenter://新闻
				viewpager_conteng_fragment.setCurrentItem(1);
				//设置可以滑动
				isEnableSlidingMenu(true);
				break;
			case R.id.rb_smartservice://智慧服务
				viewpager_conteng_fragment.setCurrentItem(2);
				//不可用滑动
				isEnableSlidingMenu(false);
				break;
			case R.id.rb_govaffairs://政要指南
				viewpager_conteng_fragment.setCurrentItem(3);
				//不可用滑动
				isEnableSlidingMenu(false);
				break;
			case R.id.rb_setting://设置
				viewpager_conteng_fragment.setCurrentItem(4);
				//不可用滑动
				isEnableSlidingMenu(false);
				break;

			default:
				break;
			}
			
		}
		
	}
	
	class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return basePagers.size();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager basePager = basePagers.get(position);
			View view = basePager.rootView;
			container.addView(view);
			//调用initData();
//			basePager.initData();//一定要调用
			return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 ==arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
			container.removeView((View)object);
		}

		
		
		
		
	}

	/**
	 * 得到新闻中心
	 * @return
	 */
	public NewsCenterPager getNewsCenterPager() {
		// TODO Auto-generated method stub
		NewsCenterPager newsCenterPager = (NewsCenterPager) basePagers.get(1);
		return newsCenterPager;
	}

}
