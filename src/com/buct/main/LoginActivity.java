package com.buct.main;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buct.bean.User;
import com.buct.yunprint.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
/**
 * 登陆模块的activity
 * @author xunmeng
 *
 */
public class LoginActivity extends Activity{
	private EditText ed_name;
	private EditText ed_password;
	private ImageView sub_login;
	private String token;
	//private RequestQueue requestQueue ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_submit);
		findview();
		getToken();
		sub_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Login();
				Login1();
			}
		});
		
	}
	
	private void Login1(){
		String url="http://www.yunprint.com/Api/Manager/auth";		
		String params=ed_name.getText().toString();
		String pwd=ed_password.getText().toString();
		url=url+"/token/"+token+"/mid/"+params+"/pwd/"+pwd;
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				  Log.e("bbb", response.toString());
				  Gson gson=new Gson();
				  User user=gson.fromJson(response.toString(), User.class);
				  if (user.getResult().equals("true")) {
					Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_LONG).show();
					//將用戶名一直存儲起來
					SharedPreferences sp=getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);
					Editor editor=sp.edit();
					editor.putString("username", ed_name.getText().toString());
					editor.commit();
					//結束，
					Intent intent=new Intent(LoginActivity.this,UserActivity.class);
					intent.putExtra("name", ed_name.getText().toString());
					startActivity(intent);
					
				  }else {
					  Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_LONG).show();
				  }
				  Log.e("ccc", user.getResult());
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				  Log.e("bbb1", arg0.toString());
			}
		});
		mQueue.add(jsonObjectRequest);
	}
	/**
	 * 登陆验证
	 */
	private void Login() {
		String url="http://www.yunprint.com/Api/Manager/auth";		
		String params=ed_name.getText().toString();
		String pwd=ed_password.getText().toString();
		url=url+"/token/"+token+"/mid/"+params+"/pwd/"+pwd;
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {  
	           @Override  
	           public void onResponse(String response) {  
	               Log.d("TAG1", response);
	               Log.d("TAG2","hello");
	               Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_LONG).show();
				   //使用JSONObject给response转换编码  
	              /* JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(response);
					String result=jsonObject.getString("result");
					Log.d("TAG4",result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
	               
				   //responseText.setText(jsonObject.toString());*/  
	           }  
	       }, new Response.ErrorListener() {  
	           @Override  
	           public void onErrorResponse(VolleyError error) {  
	              /* responseText.setText(error.getMessage());  */
	        	   Log.d("TAG3", error.getMessage());

	               Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_LONG).show();
	           }  
	       });
		mQueue.add(stringRequest);  
	}

	//获取授权码的
	private void getToken() {
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
	
		//接下来弄一个Request的请求对象
		//new出了一个StringRequest对象，StringRequest的构造函数需要传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是服务器响应成功的回调，第三个参数是服务器响应失败的回调        
		StringRequest stringRequest = new StringRequest("http://www.yunprint.com/Api/Token/getToken",
		                new Response.Listener<String>() {
		                    @Override
		                    public void onResponse(String response) {
		                        Log.d("TAG", response);
		                        token=response;
		                       //存储token
		                        SharedPreferences sp=getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);
		    					Editor editor=sp.edit();
		    					editor.putString("token", token);
		    					editor.commit();
		                        //ed_name.setText(token);
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
		//将request的请求对象加载到之前的请求队列里面去了
		mQueue.add(stringRequest);
	}

	private void findview() {
		ed_name=(EditText) findViewById(R.id.ed_name);
		ed_password=(EditText) findViewById(R.id.ed_password);
		sub_login=(ImageView) findViewById(R.id.img_button);
	}
}
