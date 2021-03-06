package com.atguigu.beijingnews.base.impl.detailmenu;

import java.util.List;

import com.atguigu.beijingnews.NewsDetailActivity;
import com.atguigu.beijingnews.R;
import com.atguigu.beijingnews.domain.NewsCenterBean.NewsCenterMenuTag;
import com.atguigu.beijingnews.domain.TabDetailBean;
import com.atguigu.beijingnews.domain.TabDetailBean.News;
import com.atguigu.beijingnews.domain.TabDetailBean.Topnews;
import com.atguigu.beijingnews.utils.CachUtils;
import com.atguigu.beijingnews.utils.ConstantUtils;
import com.atguigu.beijingnews.view.HorizontalScrollViewPager;
import com.atguigu.beijingnews.view.RefreshListView;
import com.atguigu.beijingnews.view.RefreshListView.OnRefreshListenter;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 新闻详情页面对应的页签页面
 * 
 * @author 杨光福
 * @Time 2015-7-27 上午9:27:25
 */
public class TabDetailPager extends DetailMenuBasePager {

	private static final String READ_ARRAY_ID = "read_array_id";

	/**
	 * 网络请求数据对应的链接
	 */
	private String url;

	@ViewInject(R.id.viewpager_tab_detail_topnews)
	private HorizontalScrollViewPager viewpager_tab_detail_topnews;

	@ViewInject(R.id.tv_title_tab_detail)
	private TextView tv_title_tab_detail;

	@ViewInject(R.id.ll_point_group_tab_detail)
	private LinearLayout ll_point_group_tab_detail;

	@ViewInject(R.id.lv_tab_detail_news_list)
	private RefreshListView lv_tab_detail_news_list;

	/**
	 * 页签页面对应的数据
	 */
	private NewsCenterMenuTag newsCenterMenuTag;

	/**
	 * 顶部新闻对应的数据
	 */
	private List<Topnews> topnews;

	/**
	 * 新闻列表的数据
	 */
	private List<News> newsLists;

	/**
	 * 加载图片的工具-XUtils
	 */
	private BitmapUtils bitmapUtils;

	/**
	 * 上一次被高亮显示的点的位置
	 */
	private int prePointSelect = 0;

	private MyAdapter adapter;

	public TabDetailPager(Activity activity) {
		super(activity);
	}

	public TabDetailPager(Activity activity, NewsCenterMenuTag newsCenterMenuTag) {
		super(activity);
		this.newsCenterMenuTag = newsCenterMenuTag;
		bitmapUtils = new BitmapUtils(activity);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
	}

	@Override
	public View initView() {

		View view = View.inflate(activity, R.layout.tab_detail, null);
		ViewUtils.inject(this, view);

		// //ListView加载头
		// Button uButton = new Button(activity);
		// uButton.setText("下拉刷新");
		//
		// lv_tab_detail_news_list.addHeaderView(uButton);

		View topnews = View.inflate(activity, R.layout.tab_detail_topnews, null);
		ViewUtils.inject(this, topnews);

		// lv_tab_detail_news_list.addHeaderView(topnews);
		lv_tab_detail_news_list.addSecondView(topnews);

		// 监听下拉刷新
		lv_tab_detail_news_list.setOnRefreshListenter(new MyOnRefreshListenter());

		// 设置ListView某一条点击事件
		lv_tab_detail_news_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// 得到具体某一条的ID
				int realPosition = position - 1;
				News newsItem = newsLists.get(realPosition);
				System.out.println("newsItem.id==" + newsItem.id + ",newsItem.title==" + newsItem.title);

				// 把某一条变灰
				// 先获取缓存的id,是否包哈当前的id,如果没有就保存，并且刷新是配置；否则就不用出来
				String readArrayId = CachUtils.getString(activity, READ_ARRAY_ID);// 已经保存的id
				if (!readArrayId.contains(newsItem.id)) {
					// 保存的值都是用他
					String values = "";
					// 第一次进入readArrayId="";
					if (TextUtils.isEmpty(readArrayId)) {
						values = newsItem.id + ",";
					} else {
						values = readArrayId + newsItem.id + ",";
					}

					CachUtils.setString(activity, READ_ARRAY_ID, values);

					// 适配器刷新
					adapter.notifyDataSetChanged();

				}

				String url = newsItem.url;
				Intent intent = new Intent(activity, NewsDetailActivity.class);
				intent.setData(Uri.parse(url));
				activity.startActivity(intent);

			}
		});

		return view;
	}

	private boolean isPullDownRefresh = false;

	/**
	 * 加载更多数据
	 */
	public boolean isLoadMore;

	/**
	 * 加载更多的请求链接
	 */
	private String moreUrl;

	private InternalHandler handler;

	class MyOnRefreshListenter implements OnRefreshListenter {

		@Override
		public void onPullDownRefresh() {
			isPullDownRefresh = true;
			getDataFromNet();
		}

		@Override
		public void onLoadMore() {
			if (TextUtils.isEmpty(moreUrl)) {
				isLoadMore = false;
				Toast.makeText(activity, "没有更多数据..", 1).show();
				lv_tab_detail_news_list.onRefreshFinish(false);
			} else {
				isLoadMore = true;
				getMoreDataForNet();
			}

		}

	}

	@Override
	public void initData() {
		super.initData();
		// tv.setText(newsCenterMenuTag.title);
		System.out.println("TabDetailPager==" + newsCenterMenuTag.url);
		// 网络请求的链接
		url = ConstantUtils.base_url + newsCenterMenuTag.url;
		System.out.println("TabDetailPager==url==" + url);
		Log.e("1111111111", url);
		// 得到缓存的数据
		String json = CachUtils.getString(activity, url);
		if (!TextUtils.isEmpty(json)) {
			// 解析数据和处理数据
			processData(json);
		}

		getDataFromNet();
	}

	/**
	 * 请求更多的数据
	 */
	public void getMoreDataForNet() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, moreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				// 解析和处理
				if (isLoadMore) {
					processData(responseInfo.result);
					// 加载更多的页面消掉
					lv_tab_detail_news_list.onRefreshFinish(false);
				}

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				if (isLoadMore) {
					isLoadMore = false;
					// 加载更多的页面消掉
					lv_tab_detail_news_list.onRefreshFinish(false);

				}

			}
		});

	}

	/**
	 * 解析和处理数据
	 * 
	 * @param json
	 */
	private void processData(String json) {
		// 解析数据
		Gson gson = new Gson();
		TabDetailBean tabDetailBean = gson.fromJson(json, TabDetailBean.class);

		// System.out.println("解析成功了："+tabDetailBean.data.topnews.get(0).title);

		moreUrl = tabDetailBean.data.more;
		if (TextUtils.isEmpty(moreUrl)) {
			moreUrl = "";
		} else {
			moreUrl = ConstantUtils.base_url + moreUrl;
		}

		System.out.println("请求更多的链接===" + moreUrl);

		if (!isLoadMore) {
			// 显示数据-设置适配器
			topnews = tabDetailBean.data.topnews;

			// 设置适配器
			viewpager_tab_detail_topnews.setAdapter(new MyPagerAdapter());

			// 把上一次的一次
			ll_point_group_tab_detail.removeAllViews();
			// 添加点
			for (int i = 0; i < topnews.size(); i++) {

				// 点的创建
				View topnews_point = new View(activity);
				topnews_point.setBackgroundResource(R.drawable.topnews_point_selector);

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
				if (i != 0) {
					params.leftMargin = 10;
				}
				topnews_point.setLayoutParams(params);

				topnews_point.setEnabled(false);

				// 把每一个点添加到线性布局里面去
				ll_point_group_tab_detail.addView(topnews_point);

			}

			// 设置第一个点为高亮
			ll_point_group_tab_detail.getChildAt(0).setEnabled(true);

			// 监听ViewPager的滑动
			viewpager_tab_detail_topnews.setOnPageChangeListener(new MyOnPageChangeListener());

			// 设置默认标题
			tv_title_tab_detail.setText(topnews.get(0).title);

			// 得到新闻列表的数据
			newsLists = tabDetailBean.data.news;

			// 设置ListView的适配器
			adapter = new MyAdapter();
			lv_tab_detail_news_list.setAdapter(adapter);

		} else {
			// 加载更多
			isLoadMore = false;

			// 原来的集合中加载-新的数据
			List<News> moreNews = tabDetailBean.data.news;

			newsLists.addAll(moreNews);

			// 刷新适配器
			adapter.notifyDataSetChanged();// getCount()-getView();

		}

		if (handler == null) {
			handler = new InternalHandler();
		}

		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler.postDelayed(new MyRunnable(), 4000);
		}

	}

	class InternalHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			int item = (viewpager_tab_detail_topnews.getCurrentItem() + 1) % topnews.size();
			viewpager_tab_detail_topnews.setCurrentItem(item);

			handler.postDelayed(new MyRunnable(), 4000);

		}
	}

	class MyRunnable implements Runnable {

		@Override
		public void run() {

			handler.sendEmptyMessage(0);

		}

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newsLists.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(activity, R.layout.tab_detail_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
				holder.tv_time = (TextView) view.findViewById(R.id.tv_time);

				// 把对应关系保存起来
				view.setTag(holder);

			}

			// 根据位置得到对应的数据
			News newsItem = newsLists.get(position);
			// 设置默认图片
			holder.iv_icon.setImageResource(R.drawable.news_pic_default);
			Log.e("3333", newsItem.listimage);
			String a = ConstantUtils.base_ip + newsItem.listimage.substring(newsItem.listimage.lastIndexOf(":"), newsItem.listimage.length());
			bitmapUtils.display(holder.iv_icon, a);
			holder.tv_title.setText(newsItem.title);
			holder.tv_time.setText(newsItem.pubdate);

			// 让点击的条目变灰色，其他的黑色
			String readArrayId = CachUtils.getString(activity, READ_ARRAY_ID);// 已经保存的id
			if (readArrayId.contains(newsItem.id)) {
				// 灰色
				holder.tv_title.setTextColor(Color.GRAY);
			} else {
				// 黑色
				holder.tv_title.setTextColor(Color.BLACK);
			}

			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_title;
		TextView tv_time;
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			// 文字要变化
			tv_title_tab_detail.setText(topnews.get(position).title);
			// 把上一个点的变成默认
			ll_point_group_tab_detail.getChildAt(prePointSelect).setEnabled(false);

			// 当前的点设置为红点
			ll_point_group_tab_detail.getChildAt(position).setEnabled(true);

			prePointSelect = position;

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

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return topnews.size();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(activity);
			imageView.setBackgroundResource(R.drawable.home_scroll_default);

			// 设置安卓X和Y轴拉伸
			imageView.setScaleType(ScaleType.FIT_XY);

			// 请求网络图片
			Topnews topnewsItem = topnews.get(position);
			String imageUrl = topnewsItem.topimage;
			String a = ConstantUtils.base_ip + imageUrl.substring(imageUrl.lastIndexOf(":"), imageUrl.length());

			bitmapUtils.display(imageView, a);

			container.addView(imageView);

			// 设置触摸事件
			imageView.setOnTouchListener(new MyOnTouchListener());

			return imageView;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:// 按下
				if (handler != null) {
					handler.removeCallbacksAndMessages(null);
				}
				System.out.println("移除消息");

				break;
			case MotionEvent.ACTION_UP:// 离开
				if (handler != null) {
					handler.postDelayed(new MyRunnable(), 4000);
				}
				System.out.println("发消息");

				break;
			case MotionEvent.ACTION_CANCEL:// 消息丢失和取消的

				if (handler != null) {
					handler.postDelayed(new MyRunnable(), 4000);
				}
				System.out.println("ACTION_CANCEL发消息");
				break;

			default:
				break;
			}
			return true;
		}

	}

	/**
	 * 根据url请求网络
	 */
	private void getDataFromNet() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				System.out.println("TabDetailPager请求成功==" + responseInfo.result);
				// 缓存数据
				CachUtils.setString(activity, url, responseInfo.result);
				if (isPullDownRefresh) {
					isPullDownRefresh = false;
					lv_tab_detail_news_list.onRefreshFinish(true);

				}

				// 解析和处理数据
				processData(responseInfo.result);

			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				System.out.println("TabDetailPager请求失败==" + msg);
				if (isPullDownRefresh) {
					isPullDownRefresh = false;
					lv_tab_detail_news_list.onRefreshFinish(false);

				}

			}
		});

	}

}
