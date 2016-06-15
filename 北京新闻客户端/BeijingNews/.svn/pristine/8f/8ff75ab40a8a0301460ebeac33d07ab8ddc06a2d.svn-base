package com.atguigu.beijingnews;

import java.util.ArrayList;

import com.atguigu.beijingnews.utils.CachUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 引导页面
 * @author 杨光福
 *
 */
public class GuideActivity extends Activity {
	
	private ViewPager viewpager;
	private Button start_main;
	private LinearLayout ll_group_point;
	private ArrayList<ImageView> imageViews;
	private View red_point;
	
	
	
	/**
	 * 两点间的间距
	 */
	private int margLeft;
	/**
	 * 红点在屏幕上移动的坐标
	 */
	private float maxLeft;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		start_main = (Button) findViewById(R.id.start_main);
		ll_group_point = (LinearLayout) findViewById(R.id.ll_group_point);
		red_point = findViewById(R.id.red_point);
		
		
		//设置适配器-实例化数据和自定义适配器
		
		//数据准备好了
		int ids[] = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
		imageViews = new ArrayList<ImageView>();
		for(int i=0 ; i<ids.length;i++){
			//根据id 资源创建图片
			ImageView imageView = new  ImageView(GuideActivity.this);
			imageView.setBackgroundResource(ids[i]);
			
			//把图片添加到集合中
			imageViews.add(imageView);
			
			
			
			//添加3个灰色的默认的点 //设置点的大小--默认的点-灰色
			View normal_point = new View(this);
			normal_point.setBackgroundResource(R.drawable.point_normal);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
			//设置点之间的间距
			if(i != 0){
				params.leftMargin = 10;
			}
			normal_point.setLayoutParams(params );
			
			
			ll_group_point.addView(normal_point);
			
			
		}
		
		//设置适配器
		viewpager.setAdapter(new MyPagerAdapter());
		
		
		//渲染-构造方法-onMeasure--onLayout-onDraw
		red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MYOnGlobalLayoutListener());
		
		//设置ViewPager页面改变的监听
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		
		//设置按钮的点击事件
		start_main.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//1.保存参数-标识已经进入主页面
				CachUtils.setBoolean(GuideActivity.this, SplashActivity.START_MAIN, true);
				
				//2.跳转到主页面
				Intent intent = new Intent(GuideActivity.this,MainActivity.class);
				startActivity(intent);
				
				//3.关闭引导页面
				finish();
				
				
				
			}
		});
		
		
		
	}
	class MyOnPageChangeListener implements OnPageChangeListener{

		/**
		 * 当某个页面滚动了的时候回调
		 * @param position 真正滚动的位置
		 * @param positionOffset 页面滑动的百分比
		 * @param positionOffsetPixels 滑动了多少像素
		 */
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			//红点在屏幕上移动的距离 = 间距*屏幕滑动的百分比（两点间的滑动的百分比）
//			 maxLeft = margLeft*positionOffset;
			
			//红点在屏幕上的坐标点 = 原来的坐标+红点在屏幕上移动的距离
			 maxLeft = margLeft*(position+positionOffset);
			 System.out.println("position=="+position+",positionOffset=="+positionOffset+",positionOffsetPixels=="+positionOffsetPixels);
			 
			 //移动红点
			 
			 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(10, 10);
			 //设置点之间的间距
			 params.leftMargin  = (int)maxLeft;
			 red_point.setLayoutParams(params);
			 
			
			
			
		}

		/**
		 * 当某个页面被选中的时候回调
		 * @param position
		 */
		@Override
		public void onPageSelected(int position) {
			
			if(position==imageViews.size()-1){
				//让按钮显示
				start_main.setVisibility(View.VISIBLE);
			}else{
				//让按钮隐藏
				start_main.setVisibility(View.GONE);
			}
			
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			
		}
		
	}
	
	class MYOnGlobalLayoutListener implements OnGlobalLayoutListener{

		

		@Override
		public void onGlobalLayout() {
			//取消监听执行onLayout方法
			red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			//得到两点之间的间距
			margLeft = ll_group_point.getChildAt(1).getLeft()-ll_group_point.getChildAt(0).getLeft();
			
		}
		
	}
	
	class MyPagerAdapter extends PagerAdapter{

		//得到页面的总数
		@Override
		public int getCount() {
			return imageViews.size();
		}

		/**
		 * 相当于getView()方法
		 * container：容器其实就是ViewPager
		 * position:页面对应的下标位置
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = imageViews.get(position);
			container.addView(imageView);
			return imageView;
		}
		
		/**
		 * view:当前页面
		 * object：instantiateItem返回的对象
		 * 作用：比较是否是同一个页面
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view==object;
		}

		

		/**
		 * 销毁对应坐标的页面
		 * object：要销毁的页面的对象
		 * position：要销毁的位置
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
			container.removeView((View)object);
		}
		
		
		
	}

}
