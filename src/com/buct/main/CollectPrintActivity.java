package com.buct.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.buct.yunprint.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CollectPrintActivity extends Activity {
 
	ArrayList<HashMap<String, Object>>collectStores=new ArrayList<HashMap<String,Object>>();
	private ListView mprint_listview;
	private TextView mprint_store_name,mprint_store_des,mprint_addr;
	private ImageView mprint_store_img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.collect_print_store);
		init();
		collectShow();
		 SimpleAdapter simAdapter = new SimpleAdapter(this,
				 collectStores,// 
                 R.layout.collect_print_item,
                 new String[] { "print_store_img", "print_store_name", "print_store_des","print_addr" },
                 new int[] { R.id.print_store_img, R.id.print_store_name, R.id.print_store_des,R.id.print_addr });
		 mprint_listview.setAdapter(simAdapter);
		simAdapter.notifyDataSetChanged();
	}

	private void collectShow() {
		String url="http://www.yunprint.com/Api/user/getfavorite/token/b2147f90/uid/chengyong";
		RequestQueue mQueue=Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
			@Override  
            public void onResponse(JSONArray response) {//请求响应返回的JSONArray  
				  Log.e("collect", response.toString());
				  for (int i = 0; i < response.length(); i++) {
					HashMap<String, Object>collectStore=new HashMap<String, Object>();
					try {
						JSONObject ob=response.getJSONObject(i);
						collectStore.put("print_store_img", R.drawable.ic_launcher);
						collectStore.put("print_store_name", ob.get("name").toString());
						collectStore.put("print_store_des", ob.get("descrip"));
						collectStore.put("print_addr", ob.get("addr"));
						collectStores.add(collectStore);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				  }
				  ((BaseAdapter) mprint_listview.getAdapter()).notifyDataSetChanged();
                  
			}
		}, new Response.ErrorListener() {
			@Override  
            public void onErrorResponse(VolleyError error) {  
                  
            }
		});
		mQueue.add(jsonArrayRequest);
	}

	private void init() {
		mprint_store_name=(TextView) findViewById(R.id.print_store_name);
		mprint_store_des=(TextView) findViewById(R.id.print_store_des);
		mprint_addr=(TextView) findViewById(R.id.print_addr);
		mprint_listview=(ListView) findViewById(R.id.print_listview);
	}
}
