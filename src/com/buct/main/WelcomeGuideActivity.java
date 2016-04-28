package com.buct.main;

import java.util.ArrayList;
import java.util.List;

import com.buct.adapter.ViewPageAdapter;
import com.buct.yunprint.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeGuideActivity extends Activity implements OnPageChangeListener{
	private ViewPager vp;
	private ViewPageAdapter vpAdapter;	
	private List<View> views;
	
	private ImageView[]dots;
	private int[] ids={R.id.iv1,R.id.iv2,R.id.iv3};
	
	private Button start_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_guide);
		initview();
		initDots();
	}
	
	private void initview(){
		LayoutInflater inflater=LayoutInflater.from(this);
		views=new ArrayList<View>();
		views.add(inflater.inflate(R.layout.welcome_one, null));
		views.add(inflater.inflate(R.layout.welcome_two, null));
		views.add(inflater.inflate(R.layout.welcome_three, null));
		
		vpAdapter =new ViewPageAdapter(views,this);
		vp=(ViewPager)findViewById(R.id.viewpager);
		vp.setAdapter(vpAdapter);
		
		start_btn=(Button) views.get(2).findViewById(R.id.start_btn);
		start_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(WelcomeGuideActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		vp.setOnPageChangeListener(this);
	}
	
	private void initDots(){
		dots=new ImageView[views.size()];
		for (int i = 0; i < dots.length; i++) {
			dots[i]=(ImageView)findViewById(ids[i]);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < ids.length; i++) {
			if (arg0==i) {
				dots[i].setImageResource(R.drawable.ball_red);
			}else {
				dots[i].setImageResource(R.drawable.ball_grey);				
			}
		}
		
	}


}
