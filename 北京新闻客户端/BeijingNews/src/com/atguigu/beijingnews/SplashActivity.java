package com.atguigu.beijingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.atguigu.beijingnews.utils.CachUtils;

/**
 * 软件启动的第一个页面
 * 
 * @author 杨光福
 * 
 */
public class SplashActivity extends Activity {

	/**
	 * 标识是否进入过主页面
	 */
	public static final String START_MAIN = "start_main";
	private RelativeLayout rl_splahs_root;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置隐藏标题
		super.onCreate(savedInstanceState);
		
//		Utils utils = new Utils();
//		int result = utils.add(29, 18);
//		System.out.println("result ==="+result);
		setContentView(R.layout.activity_splash);
		rl_splahs_root = (RelativeLayout) findViewById(R.id.rl_splahs_root);

		// 旋转动画
		RotateAnimation ra = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setDuration(500);// 设置动画播放的时长
		ra.setFillAfter(true);// 设置动画停留在播放完成的状态

		// 渐变动画
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(1000);
		aa.setFillAfter(true);

		// 拉伸动画
		ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		sa.setDuration(500);
		sa.setFillAfter(true);

		// 把这些动画放入集合中,没有先后顺序
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(ra);
		set.addAnimation(aa);
		set.addAnimation(sa);

		// 播放动画
		rl_splahs_root.startAnimation(set);
		
		
		//监听动画是否播放完成
		set.setAnimationListener(new MyAnimationListener());
	}
	
	class MyAnimationListener implements AnimationListener{

		/**
		 * 当动画开始播放的时候回调这个方法
		 */
		@Override
		public void onAnimationStart(Animation animation) {
//			Toast.makeText(SplashActivity.this, "动画开始播放", 1).show();
		}
		/**
		 * 当动画播放结束时候回调这个方法
		 */
		@Override
		public void onAnimationEnd(Animation animation) {
			
			
			//判断是否进入过主页面
			boolean isStartMained = CachUtils.getBoolean(SplashActivity.this, START_MAIN);
			if(isStartMained){
				//进入主页面
				//2.跳转到主页面
				Intent intent = new Intent(SplashActivity.this,MainActivity.class);
				startActivity(intent);
			}else{
				//如果没有进入，就进入引导页面
				Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
				startActivity(intent);
//				Toast.makeText(SplashActivity.this, "动画结束播放", 1).show();
				
				
			}
			
			//关闭欢迎页面
			finish();
		
			
			
		}
		/**
		 * 当动画重复播放时候回调这个方法
		 */
		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
		
	}

}
