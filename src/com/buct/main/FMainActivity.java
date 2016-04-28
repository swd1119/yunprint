package com.buct.main;

import com.buct.yunprint.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
/**
 * 首页信息的展示
 * @author xunmeng
 *
 */
public class FMainActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}
	
	//打开slidemenu的代码。
	private void init() {
		slidingMenu=new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		//这是sliding menu露出了的宽度
		slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		//设置滑动的范围
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//使slidingMenu附加在Activity上面
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);		
		//设置布局文件
		slidingMenu.setMenu(R.layout.leftmenu);
		
		txtHome=(TextView)findViewById(R.id.home);
		txtStore=(TextView)findViewById(R.id.home1);
		txtPrint=(TextView)findViewById(R.id.home2);
		txtCollection=(TextView)findViewById(R.id.home3);
		txtHome.setOnClickListener(listener);
		txtPrint.setOnClickListener(listener);
		txtCollection.setOnClickListener(listener);
		txtStore.setOnClickListener(listener);
		txtPerson=(TextView)findViewById(R.id.person_center);
		txtPerson.setOnClickListener(listener);
		imageView=(ImageView)findViewById(R.id.title_bar_menu_btn);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//动态判断自动关闭或开启SlidingMenu
				slidingMenu.toggle();
			}
		});
		
		//进入个人中心的代码
		head_image=(ImageView)findViewById(R.id.head_image);
		head_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(FMainActivity.this, UserActivity.class);
				startActivity(intent);
			}
		});
	}

	private OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView txt=(TextView)v;
			Intent intent=new Intent();
			switch (txt.getId()) {
			case R.id.home:
				intent.setClass(FMainActivity.this, MainActivity.class);
				break;
			case R.id.home1:
				intent.setClass(FMainActivity.this, MainActivity.class);
				break;
			case R.id.home2:
				intent.setClass(FMainActivity.this, MainActivity.class);
				break;
			case R.id.home3:
				intent.setClass(FMainActivity.this, MainActivity.class);
				break;
			case R.id.person_center:
				intent.setClass(FMainActivity.this, UserActivity.class);
				break;
			default:
				break;
			}
			startActivity(intent);
		}
	};	
	
	private SlidingMenu slidingMenu;
	private TextView txtHome,txtPrint,txtStore,txtCollection,txtPerson;
	private	ImageView imageView,head_image;
	
	//slidemenu代码结束

	
	
	
}
