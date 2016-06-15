package com.atguigu.beijingnews.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.beijingnews.R;

/**
 * 自定义ListView -添加下拉刷新
 * 
 * @author 杨光福
 * @Time 2015-7-29 上午9:03:04
 */
public class RefreshListView extends ListView {
	
	/**
	 * 加载更多
	 */
	private boolean isLoadMore = false;


	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initAnimaition(context);
		initHeaderView(context);
		initFooterView(context);
	}

	/**
	 * 添加底部View
	 * @param context
	 */
	private void initFooterView(Context context) {
		// TODO Auto-generated method stub
		
		footerView = View.inflate(context, R.layout.refresh_footer, null);
		footerView.measure(0, 0);
		footerViewHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		this.addFooterView(footerView);
		
		
		//监听ListView滑到底部
		this.setOnScrollListener(new MyOnScrollListener());
		
	}
	
	class MyOnScrollListener implements OnScrollListener{

		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			//当状态是停止或者惯性滚动的时候
			if(scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING){
				//最后一个了
				if(getLastVisiblePosition()==(getCount()-1)){
					
					//状态要改变
					isLoadMore = true;
					
					// 定位到最后一条
					setSelection(getCount());//只能是这个参数getCount()
					
					//显示加载更多的页面
					footerView.setPadding(0, 0, 0, 0);
					
					//调用接口，实现网络请求加载更多数据
					if(onRefreshListenter != null){
						onRefreshListenter.onLoadMore();
					}
					
				}
			}
			
			
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
		}
		
	}

	/**
	 * 初始化动画
	 * 
	 * @param context
	 */
	private void initAnimaition(Context context) {
		upAnin = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		
		upAnin.setDuration(500);
		upAnin.setFillAfter(true);
		
		
		downAnin = new RotateAnimation(-180, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		
		downAnin.setDuration(500);
		downAnin.setFillAfter(true);
		
	}

	/**
	 * 下拉刷新控件的高度
	 */
	private int headerViewHeight;
	/**
	 * 起始坐标
	 */
	private float startY = 0;
	/**
	 * 代表一整个空间：下拉刷新部分和ViewPager部分
	 */
	private LinearLayout headerView;
	/**
	 * 顶部新闻
	 */
	private View topnews;
	/**
	 * 下拉刷新控件
	 */
	private View pullDownRefresh;
	/**
	 * ListView在屏幕上的坐标
	 */
	private int listViewOnScreenY = -1;

	/**
	 * 下拉刷新
	 */
	public static final int PULL_DOWN_REFRESH = 0;
	/**
	 * 手松刷新
	 */
	public static final int RESELA_REFRESH = 1;

	/**
	 * 正在刷新
	 */
	public static final int REFRESHINT = 2;

	/**
	 * 当前下拉刷新控件的状态
	 */
	private int currentState = PULL_DOWN_REFRESH;
	/**
	 * 向下的红色箭头
	 */
	private ImageView iv_red_arrow;
	/**
	 * 红圈
	 */
	private ProgressBar pb_status;
	/**
	 * 下拉刷新的状态
	 */
	private TextView tv_status;
	/**
	 * 显示刷新时间
	 */
	private TextView tv_time;
	private RotateAnimation downAnin;
	private RotateAnimation upAnin;

	/**
	 * 添加下拉刷新
	 */
	private void initHeaderView(Context context) {
		headerView = (LinearLayout) View.inflate(context,
				R.layout.refresh_header, null);

		iv_red_arrow = (ImageView) headerView.findViewById(R.id.iv_red_arrow);
		pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
		tv_status = (TextView) headerView.findViewById(R.id.tv_status);
		tv_time = (TextView) headerView.findViewById(R.id.tv_time);

		pullDownRefresh = headerView.findViewById(R.id.ll_pull_down_refresh);
		// 测量
		pullDownRefresh.measure(0, 0);
		// 得到控件的高度
		headerViewHeight = pullDownRefresh.getMeasuredHeight();

		// 隐藏：-高
		// 完全显示：0
		// 两倍显示：高
		pullDownRefresh.setPadding(0, -headerViewHeight, 0, 0);

		// 注意把一整块视图添加到ListView中了：下拉刷新和ViewPager部分

		this.addHeaderView(headerView);

	}
	private OnRefreshListenter onRefreshListenter;
	/**
	 * 加载更多控件视图
	 */
	private View footerView;
	/**
	 * 加载更多控件的高
	 */
	private int footerViewHeight;
	
	/**
	 * 设置下拉刷新监听,add yangguangfu
	 */
	public void setOnRefreshListenter(OnRefreshListenter onRefreshListenter) {
		this.onRefreshListenter = onRefreshListenter;
	}

	/**
	 * 下拉刷新的接口
	 * @author 杨光福
	 * @Time 2015-7-29  上午10:53:00
	 */
	public interface OnRefreshListenter{
		/**
		 * 当下拉刷新的时候回调这个方法
		 */
		public void onPullDownRefresh();

		/**
		 * 加载更多的数据
		 */
		public void onLoadMore();
	}
	
	/**
	 * 下拉刷新完成
	 * true：请求成功
	 * false:请求失败
	 * 
	 */
	public void onRefreshFinish(boolean isSuccess){
		if(isLoadMore){
			//状态还原
			isLoadMore = false;
			footerView.setPadding(0, -footerViewHeight, 0, 0);
		}else{
			iv_red_arrow.clearAnimation();
			iv_red_arrow.setVisibility(View.VISIBLE);
			pb_status.setVisibility(View.GONE);
			tv_status.setText("下拉刷新...");
			//把下拉刷新隐藏
			pullDownRefresh.setPadding(0, -headerViewHeight, 0, 0);
			currentState = PULL_DOWN_REFRESH;
			if(isSuccess){
				tv_time.setText(getSystemTime());
			}
		}
		
	}

	/**
	 * 得到当前的系统时间
	 * @return
	 */
	private String getSystemTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(new Date());
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 1.第一次按下的Y轴坐标
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY == 0) {
				startY = ev.getY();
			}
			// 2.来到到新的坐标
			float endY = ev.getY();

			// 3.计算距离
			float distanceY = endY - startY;
			
			if(currentState ==REFRESHINT){
				break;
			}

			// 判断是否ViewPager是否完全显示，没有完全显示就，不让下拉刷新显示
			boolean isDispalySecondView = isDisplaySecondView();
			if (!isDispalySecondView) {
				break;
			}

			if (distanceY > 0) {

				// 设置下拉控件移动
				// 下拉刷新控件的Y轴坐标 = - 控件高 +（endY - starY）
				float paddingTop = -headerViewHeight + distanceY;

				if (paddingTop > 0 && currentState != RESELA_REFRESH) {
					currentState = RESELA_REFRESH;
					// 手松刷新
					System.out.println("手松刷新...");
					refreshHeaderView();
				} else if (paddingTop < 0 && currentState != PULL_DOWN_REFRESH) {
					currentState = PULL_DOWN_REFRESH;
					// 下拉刷新
					System.out.println("下拉刷新...");
					refreshHeaderView();
				}

				pullDownRefresh.setPadding(0, (int) paddingTop, 0, 0);

				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			startY = 0;
			//当是up的时候，并且状态是PULL_DOWN_REFRESH
			if(currentState == PULL_DOWN_REFRESH){
				
				pullDownRefresh.setPadding(0, -headerViewHeight, 0, 0);
			}else if(currentState == RESELA_REFRESH){
				//正在刷新
				currentState = REFRESHINT;
				refreshHeaderView();
				//paddingTop = -高度 ： 完全隐藏
				//paddingTop = 0 ： 完全显示
				//paddingTop = 高度 ： 显示两倍高度
				pullDownRefresh.setPadding(0, 0, 0, 0);
				//请求网络了
				
				//写接口
				if(onRefreshListenter != null){
					onRefreshListenter.onPullDownRefresh();
				}
			}
			
			break;

		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 处理下拉刷新的状态
	 */
	private void refreshHeaderView() {
		switch (currentState) {
		case PULL_DOWN_REFRESH:// 下拉刷新
			iv_red_arrow.setAnimation(downAnin);
			tv_status.setText("下拉刷新...");
			pb_status.setVisibility(View.GONE);
			iv_red_arrow.setVisibility(View.VISIBLE);

			break;
		case RESELA_REFRESH:// 手松刷新
			iv_red_arrow.setAnimation(upAnin);
			tv_status.setText("手松刷新...");

			break;
		case REFRESHINT:// 正在刷新
			iv_red_arrow.clearAnimation();
			iv_red_arrow.setVisibility(View.GONE);
			pb_status.setVisibility(View.VISIBLE);
			tv_status.setText("正在刷新...");

			break;

		default:
			break;
		}

	}

	/**
	 * 判断ViewPage是否完全显示 ListView的坐标小于或者等于ViewPager坐标的时候 ViewPager完全显示
	 * 
	 * @return
	 */
	private boolean isDisplaySecondView() {

		int[] location = new int[2];
		if (listViewOnScreenY == -1) {
			// 计算ListView在屏幕上的坐标
			this.getLocationOnScreen(location);
			listViewOnScreenY = location[1];
		}

		// ViewPager部分在屏幕上的坐标
		topnews.getLocationOnScreen(location);

		int topnewsOnScreenY = location[1];

		// if(listViewOnScreenY <=topnewsOnScreenY){
		// return true;
		// }else{
		// return false;
		// }

		return listViewOnScreenY <= topnewsOnScreenY;
	}

	/**
	 * 
	 * @param topnews
	 *            : 顶部新闻
	 */
	public void addSecondView(View topnews) {
		this.topnews = topnews;

		// 添加到顶部控件
		headerView.addView(topnews);

	}

}
