package com.buct.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.Test;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buct.bean.Stores;
import com.buct.bean.User;
import com.buct.yunprint.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	  ArrayList<HashMap<String, Object>>users=new ArrayList<HashMap<String,Object>>();
	//private TextView mToken;
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		findView();
		init();
		Test();//测试数据添加的功能

		 SimpleAdapter simAdapter = new SimpleAdapter(this,
                 users,// 
                 R.layout.printstore_item,
                 new String[] { "print_store_img", "print_store_name", "print_store_des" },
                 new int[] { R.id.print_store_img, R.id.print_store_name, R.id.print_store_des });
		listView.setAdapter(simAdapter);
		simAdapter.notifyDataSetChanged();
	}

	
	//测试数据添加的功能
	private void Test() {
		//获取token值
		/*SharedPreferences sp = getApplicationContext().getSharedPreferences(
				"config", MODE_PRIVATE);
		String token = sp.getString("token", "").toString();
		String name=sp.getString("username", "").toString();*/
		String url="http://www.yunprint.com/Api/shop/getshops/token/b2147f90/mid/lisi";		
		RequestQueue mQueue=Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest request = new JsonArrayRequest(url,   
                new Response.Listener<JSONArray>() {  
  
                    @Override  
                    public void onResponse(JSONArray response) {//请求响应返回的JSONArray  
                          Log.e("store", response.toString());
                          for (int i = 0; i < response.length(); i++) {
                        	  HashMap<String, Object> user=new HashMap<String, Object>();
                        	  try {
								JSONObject ob=response.getJSONObject(i);
							    String	name=(ob.get("name").toString());
							    String  addr=(ob.get("addr").toString());
								user.put("print_store_img", R.drawable.ic_launcher);
								user.put("print_store_name", name);
								user.put("print_store_des", addr);
								users.add(user);
								//data.add(ob.get("name").toString());
								Log.e("TAGOB",ob.get("name").toString());
								//System.out.println(ob.get("name"));
                        	  } catch (JSONException e) {
								e.printStackTrace();
                        	  }	  
                          }
                          ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
                          
                         // SimpleAdapter simAdapter=new SimpleAdapter(this,users,R.layout.printstore_item,new String[]{"print_store_img","print_store_name","print_store_des"},new int[]{R.id.print_store_img,R.id.print_store_name,R.id.print_store_des});
                         
                          
                    }  
                },   
                new Response.ErrorListener() {  
  
                    @Override  
                    public void onErrorResponse(VolleyError error) {  
                          
                    }  
                });  
        mQueue.add(request);  		
	}



	private void findView() {
		listView=(ListView) findViewById(R.id.listview);
	}
	
	
	
	
	
	//侧拉菜单
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
			public void onClick(View arg0) {
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
				intent.setClass(MainActivity.this, UserActivity.class);
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
				intent.setClass(MainActivity.this, FMainActivity.class);
				break;
			case R.id.home1:
				intent.setClass(MainActivity.this, LoginActivity.class);
				break;
			case R.id.home2:
				intent.setClass(MainActivity.this, LoginActivity.class);
				break;
			case R.id.home3:
				intent.setClass(MainActivity.this, MainActivity.class);
				break;

			default:
				break;
			}
			startActivity(intent);
		}
	};

	private SlidingMenu slidingMenu;
	private TextView txtHome, txtPrint, txtStore, txtCollection;
	private ImageView imageView,head_image;

}
