package com.atguigu.beijingnews;

import com.atguigu.beijingnews.utils.ConstantUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 新闻详情页面
 * @author 杨光福
 * @Time 2015-7-29  下午2:21:19
 */
public class NewsDetailActivity extends Activity {

	/**
	 * 详情新闻的链接
	 */
	private Uri uri;
	/**
	 *  微信大量使用，看新闻，看视频
	 */
	private WebView webview;
	private ProgressBar pd_loading;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		uri = getIntent().getData();
		
		settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);//设置支持JavaScript
		
		//设置文字大小
//		settings.setTextSize(TextSize.SMALLEST);
		pd_loading.setVisibility(View.VISIBLE);
		//加载网页
		Log.e("4444", uri.toString());
		String a = ConstantUtils.base_ip + uri.toString().substring(uri.toString().lastIndexOf(":"), uri.toString().length());
		webview.loadUrl(a);
//		webview.loadUrl("http://www.atguigu.com/teacher.shtml");
		
		//监听网页加上是否完成
		webview.setWebViewClient(new WebViewClient(){
			
			//当网页加载完成后回调这个方法
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				pd_loading.setVisibility(View.GONE);
				
			}
		});
		
		//设置增加变大变小的按钮
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);//设置双击变大变小
		
		
	}

	private void initView() {
		setContentView(R.layout.activity_news_detail);
		
		findViewById(R.id.ib_base_back).setVisibility(View.VISIBLE);
		findViewById(R.id.tv_titlebar).setVisibility(View.GONE);
		findViewById(R.id.ib_base_textsize).setVisibility(View.VISIBLE);
		findViewById(R.id.ib_base_share).setVisibility(View.VISIBLE);
		pd_loading = (ProgressBar) findViewById(R.id.pd_loading);
		
		webview = (WebView) findViewById(R.id.webview);
		
		//设置点击事件
		findViewById(R.id.ib_base_back).setOnClickListener(mOnClickListener);
		findViewById(R.id.ib_base_textsize).setOnClickListener(mOnClickListener);
		findViewById(R.id.ib_base_share).setOnClickListener(mOnClickListener);
		
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ib_base_back:
				//退出详情页面
				finish();
				
				break;
			case R.id.ib_base_textsize://设置文字置大小
				showChangTextSizeDialog();
				
				break;
				
			case R.id.ib_base_share://分享
				
				showShare();
				
				break;

			default:
				break;
			}
			
		}
	};
	
	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("呵呵，我正在给尚硅谷Android班上课呢");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("mnt/sdcard/haizi.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
//		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
//		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		 oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		 oks.show(this);
		 }

	/**
	 * 临时文字的大小
	 */
	private int tempTextSize;
	/**
	 * 真正文字要设置的大小
	 */
	protected int realTextSize = 2;
	private WebSettings settings;
	/**
	 * 对话框
	 */
	protected void showChangTextSizeDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("设置文字大小");
		String[] items ={"超大字号","大字号","正常字号","小字号","超小字号"};
		builder.setSingleChoiceItems(items, realTextSize, new DialogInterface.OnClickListener() {
			
			

			@Override
			public void onClick(DialogInterface dialog, int which) {
				tempTextSize = which;
				
			}
		});
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				realTextSize = tempTextSize;
				//设置文字大小
				changTextSize(realTextSize);
				
			}
		});
		builder.setPositiveButton("取消", null);
		
		builder.show();
		
	}

	/**
	 * 设置位置大小
	 * @param realTextSize2
	 */
	protected void changTextSize(int realTextSize) {
		// TODO Auto-generated method stub
		switch (realTextSize) {
		case 0://超大
			settings.setTextSize(TextSize.LARGEST);
			break;
		case 1:
			settings.setTextSize(TextSize.LARGER);
			break;//大字号
		case 2:
			settings.setTextSize(TextSize.NORMAL);
			break;//正常字号
		case 3:
			settings.setTextSize(TextSize.SMALLER);
			break;//小字号
		case 4:
			settings.setTextSize(TextSize.SMALLEST);
			break;//超小字号

		default:
			break;
		}
		
	}
	
}
