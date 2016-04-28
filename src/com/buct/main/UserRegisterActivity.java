package com.buct.main;



import org.json.JSONArray;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.buct.bean.User;
import com.buct.yunprint.R;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserRegisterActivity extends Activity {
	private EditText mUser_register_nc,mUser_register_mobile,mUser_register_password1,mUser_register_password2;
	private TextView mtop_right;
	String name,mobile,password,repassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		init();
		mtop_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				name=mUser_register_nc.getText().toString();
				mobile=mUser_register_mobile.getText().toString();
				password=mUser_register_password1.getText().toString();
				repassword=mUser_register_password2.getText().toString();
				Log.e("TAGRE",password);
				Log.e("TAGER1", repassword);
				if (password.equals(repassword)) {
					register();
					
				}else {
					Toast.makeText(getApplicationContext(), "密码不对", Toast.LENGTH_LONG).show();
					
				}
				
				
			}

		
		});
	}
	
	private void register() {
		String url="http://www.yunprint.com/Api/User/register/token/9a4c7622/uid/dukang/name/%E6%9D%9C%E5%BA%B7/pwd/abc1232/mobile/13345982387";
		//Toast.makeText(getApplicationContext(), "密码对", Toast.LENGTH_LONG).show();
		RequestQueue mQueue=Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
			 @Override  
             public void onResponse(JSONArray response){
				 Gson gson=new Gson();
				  User user=gson.fromJson(response.toString(), User.class);
				  if (user.getResult().equals("true")) {
					  Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
					  Intent intent=new Intent();
					  intent.setClass(UserRegisterActivity.this, LoginActivity.class);
					  startActivity(intent);
				  }else {
					  Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_LONG).show();
				  }
			 }
		}, new Response.ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				Toast.makeText(getApplicationContext(), "该用户名已注册", Toast.LENGTH_LONG).show();
			}
		});
		mQueue.add(jsonArrayRequest);
	}
	
	private void init() {
		mUser_register_nc=(EditText) findViewById(R.id.user_register_nc);
		mUser_register_mobile=(EditText) findViewById(R.id.user_register_mobile);
		mUser_register_password1=(EditText) findViewById(R.id.user_register_password1);
		mUser_register_password2=(EditText) findViewById(R.id.user_register_password2);
		mtop_right=(TextView) findViewById(R.id.top_right);
		
	
	}
}
