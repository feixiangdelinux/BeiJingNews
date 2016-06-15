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
 * ��������ҳ��
 * @author ��⸣
 * @Time 2015-7-29  ����2:21:19
 */
public class NewsDetailActivity extends Activity {

	/**
	 * �������ŵ�����
	 */
	private Uri uri;
	/**
	 *  ΢�Ŵ���ʹ�ã������ţ�����Ƶ
	 */
	private WebView webview;
	private ProgressBar pd_loading;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		uri = getIntent().getData();
		
		settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);//����֧��JavaScript
		
		//�������ִ�С
//		settings.setTextSize(TextSize.SMALLEST);
		pd_loading.setVisibility(View.VISIBLE);
		//������ҳ
		Log.e("4444", uri.toString());
		String a = ConstantUtils.base_ip + uri.toString().substring(uri.toString().lastIndexOf(":"), uri.toString().length());
		webview.loadUrl(a);
//		webview.loadUrl("http://www.atguigu.com/teacher.shtml");
		
		//������ҳ�����Ƿ����
		webview.setWebViewClient(new WebViewClient(){
			
			//����ҳ������ɺ�ص��������
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				pd_loading.setVisibility(View.GONE);
				
			}
		});
		
		//�������ӱ���С�İ�ť
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);//����˫������С
		
		
	}

	private void initView() {
		setContentView(R.layout.activity_news_detail);
		
		findViewById(R.id.ib_base_back).setVisibility(View.VISIBLE);
		findViewById(R.id.tv_titlebar).setVisibility(View.GONE);
		findViewById(R.id.ib_base_textsize).setVisibility(View.VISIBLE);
		findViewById(R.id.ib_base_share).setVisibility(View.VISIBLE);
		pd_loading = (ProgressBar) findViewById(R.id.pd_loading);
		
		webview = (WebView) findViewById(R.id.webview);
		
		//���õ���¼�
		findViewById(R.id.ib_base_back).setOnClickListener(mOnClickListener);
		findViewById(R.id.ib_base_textsize).setOnClickListener(mOnClickListener);
		findViewById(R.id.ib_base_share).setOnClickListener(mOnClickListener);
		
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ib_base_back:
				//�˳�����ҳ��
				finish();
				
				break;
			case R.id.ib_base_textsize://���������ô�С
				showChangTextSizeDialog();
				
				break;
				
			case R.id.ib_base_share://����
				
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
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 

		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
//		 oks.setTitle(getString(R.string.share));
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
//		 oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("�Ǻǣ������ڸ��й��Android���Ͽ���");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 oks.setImagePath("mnt/sdcard/haizi.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
//		 oks.setUrl("http://sharesdk.cn");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
//		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
//		 oks.setSite(getString(R.string.app_name));
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
//		 oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		 oks.show(this);
		 }

	/**
	 * ��ʱ���ֵĴ�С
	 */
	private int tempTextSize;
	/**
	 * ��������Ҫ���õĴ�С
	 */
	protected int realTextSize = 2;
	private WebSettings settings;
	/**
	 * �Ի���
	 */
	protected void showChangTextSizeDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("�������ִ�С");
		String[] items ={"�����ֺ�","���ֺ�","�����ֺ�","С�ֺ�","��С�ֺ�"};
		builder.setSingleChoiceItems(items, realTextSize, new DialogInterface.OnClickListener() {
			
			

			@Override
			public void onClick(DialogInterface dialog, int which) {
				tempTextSize = which;
				
			}
		});
		builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				realTextSize = tempTextSize;
				//�������ִ�С
				changTextSize(realTextSize);
				
			}
		});
		builder.setPositiveButton("ȡ��", null);
		
		builder.show();
		
	}

	/**
	 * ����λ�ô�С
	 * @param realTextSize2
	 */
	protected void changTextSize(int realTextSize) {
		// TODO Auto-generated method stub
		switch (realTextSize) {
		case 0://����
			settings.setTextSize(TextSize.LARGEST);
			break;
		case 1:
			settings.setTextSize(TextSize.LARGER);
			break;//���ֺ�
		case 2:
			settings.setTextSize(TextSize.NORMAL);
			break;//�����ֺ�
		case 3:
			settings.setTextSize(TextSize.SMALLER);
			break;//С�ֺ�
		case 4:
			settings.setTextSize(TextSize.SMALLEST);
			break;//��С�ֺ�

		default:
			break;
		}
		
	}
	
}
