package com.buct.main;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.buct.bean.User;
import com.buct.bean.UserCenter;
import com.buct.yunprint.R;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserMessageActivity extends Activity {
	private TextView txt_mobile,txt_account,txt_qq,txt_mail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_message);
		init();//加载slidemenu
		findView();//加载控件
		load();
	}

	private void findView() {
		
		txt_mobile=(TextView) findViewById(R.id.mobile);
		txt_account=(TextView) findViewById(R.id.account);
		txt_mail=(TextView) findViewById(R.id.mail);
		txt_qq=(TextView) findViewById(R.id.qq);
	}

	// 将基本信息加载出来
	private void load() {
		String url = "http://www.yunprint.com/Api/User/getUser/token/b2147f90/uid/18686373614";
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e("user", response.toString());
						Gson gson = new Gson();
						UserCenter usCenter=gson.fromJson(response.toString(), UserCenter.class);
						txt_account.setText(usCenter.getMobile());
						txt_mail.setText(usCenter.getQq()+"@qq.com");
						txt_mobile.setText(usCenter.getMobile());

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("bbb1", arg0.toString());
					}
				});
		mQueue.add(jsonObjectRequest);
	}

	private void init() {

		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 这是sliding menu露出了的宽度
		slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		// 设置滑动的范围
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 使slidingMenu附加在Activity上面
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置布局文件
		slidingMenu.setMenu(R.layout.leftmenu);

		txtHome = (TextView) findViewById(R.id.home);
		txtStore = (TextView) findViewById(R.id.home1);
		txtPrint = (TextView) findViewById(R.id.home2);
		txtCollection = (TextView) findViewById(R.id.home3);
		txtHome.setOnClickListener(listener);
		txtPrint.setOnClickListener(listener);
		txtCollection.setOnClickListener(listener);
		txtStore.setOnClickListener(listener);
		imageView = (ImageView) findViewById(R.id.back);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(UserMessageActivity.this, UserActivity.class);
				startActivity(intent);
			}
		});
		// 进入个人中心的代码
		head_image = (ImageView) findViewById(R.id.head_image);
		head_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(UserMessageActivity.this, UserActivity.class);
				startActivity(intent);
			}
		});
	}

	private OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView txt = (TextView) v;
			Intent intent = new Intent();
			switch (txt.getId()) {
			case R.id.home:
				intent.setClass(UserMessageActivity.this, FMainActivity.class);
				break;
			case R.id.home1:
				intent.setClass(UserMessageActivity.this, LoginActivity.class);
				break;
			case R.id.home2:
				intent.setClass(UserMessageActivity.this, LoginActivity.class);
				break;
			case R.id.home3:
				intent.setClass(UserMessageActivity.this, MainActivity.class);
				break;

			default:
				break;
			}
			startActivity(intent);
		}
	};

	private SlidingMenu slidingMenu;
	private TextView txtHome, txtPrint, txtStore, txtCollection;
	private ImageView imageView, head_image;

}
