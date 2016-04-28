package com.buct.main;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buct.base.CircleImageView;
import com.buct.bean.User;
import com.buct.yunprint.R;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 用户基本信息的目录
 * 
 * @author xunmeng
 */
public class UserActivity extends Activity {
	private TextView txt_name;
	private ImageView user_head_cicle;
	private RelativeLayout user_id, integral, support;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_center);
		find();
		getToken();
		init();
		user_show();
		onclick();//个人信息下面条目的点击事件
		
	}
	
	
	
	private void onclick() {
		//用户个人信息
		user_id.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(UserActivity.this, UserMessageActivity.class);
				startActivity(intent);
			}
		});
		//积分
		integral.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		support.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent();
				intent.setClass(UserActivity.this, UserHelpActivity.class);
				startActivity(intent);	
			}
		});
	}



	// 个人中心信息展示
	private void user_show() {
		SharedPreferences sp = getApplicationContext().getSharedPreferences(
				"config", MODE_PRIVATE);
		// txt_name.setText(sp.getString("username", "").toString()); //05a89817
		String mid = sp.getString("username", "").toString();
		//String url = "http://www.yunprint.com/Api/Manager/getManager/token/"+token+"/mid/lisi";
		String url="http://www.yunprint.com/Api/Manager/getManager/token/b2147f90/mid/lisi";
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.e("user", response.toString());
						Gson gson = new Gson();
						User user = gson.fromJson(response.toString(),
								User.class);
						txt_name.setText(user.getName());
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("bbb1", arg0.toString());
					}
				});
		mQueue.add(jsonObjectRequest);
	}

	// 获取授权码的
	private void getToken() {
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());

		// 接下来弄一个Request的请求对象
		// new出了一个StringRequest对象，StringRequest的构造函数需要传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是服务器响应成功的回调，第三个参数是服务器响应失败的回调
		StringRequest stringRequest = new StringRequest(
				"http://www.yunprint.com/Api/Token/getToken",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("TAG", response);
						token = response;
						// ed_name.setText(token);
						System.out.println("*********");
						System.out.println(token);
						System.out.println("*********");
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});
		// 将request的请求对象加载到之前的请求队列里面去了
		mQueue.add(stringRequest);
	}

	private void find() {
		txt_name = (TextView) findViewById(R.id.user_name);
		user_head_cicle = (ImageView) findViewById(R.id.user_head_cicle);
		user_id = (RelativeLayout) findViewById(R.id.user_message);
		integral = (RelativeLayout) findViewById(R.id.integral);
		support = (RelativeLayout) findViewById(R.id.user_help_layout);
	}

	// 打开slidemenu的代码。
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
		imageView = (ImageView) findViewById(R.id.title_bar_menu_btn);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 动态判断自动关闭或开启SlidingMenu
				slidingMenu.toggle();
			}
		});

		// 进入个人中心的代码
		head_image = (ImageView) findViewById(R.id.head_image);
		head_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(UserActivity.this, UserActivity.class);
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
				intent.setClass(UserActivity.this, MainActivity.class);
				break;
			case R.id.home1:
				intent.setClass(UserActivity.this, MainActivity.class);
				break;
			case R.id.home2:
				intent.setClass(UserActivity.this, MainActivity.class);
				break;
			case R.id.home3:
				intent.setClass(UserActivity.this, MainActivity.class);
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

	// slidemenu代码结束

}
