package com.buct.main;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buct.bean.User;
import com.buct.yunprint.R;
import com.buct.yunprint.R.layout;
import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserSuggestActivity extends Activity {
	private EditText mFacate, mSubjcet, mContent;
	private Button mSub_suggest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_suggest);
		init();
		mSub_suggest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				submit_suggest();
			}
		});

	}

	private void submit_suggest() {
		String url = "http://www.yunprint.com/Api/Agility/setFeedback/token/b2147f90/";//fdcate/1/sender/zhangsan/subject/white/content/123";
		String mfacate= mFacate.getText().toString();
		String msubjcet= mSubjcet.getText().toString();
		String mcontent= mContent.getText().toString();
		url=url+"fdcate/"+mfacate+"/sender/zhangsan/subject/"+msubjcet+"content"+mcontent;
		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("TAG1", response);
						Log.d("TAG2", "hello");
						Gson gson = new Gson();
						User user = gson.fromJson(response.toString(),
								User.class);
						if (user.getResult().equals("true")) {
							Toast.makeText(getApplicationContext(), "提交成功",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(), "提交失敗",
									Toast.LENGTH_LONG).show();

						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						/* responseText.setText(error.getMessage()); */
						Log.d("TAG3", error.getMessage());

						Toast.makeText(getApplicationContext(), "登陆失败",
								Toast.LENGTH_LONG).show();
					}
				});
		mQueue.add(stringRequest);
	}

	private void init() {
		mFacate = (EditText) findViewById(R.id.facate);
		mSubjcet = (EditText) findViewById(R.id.subject);
		mContent = (EditText) findViewById(R.id.content);
		mSub_suggest = (Button) findViewById(R.id.suggest_button);
	}
}
