package com.teachinginformationmanagementsystem.util;

import com.teachinginformationmanagementsystem.activity.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleLayout extends RelativeLayout {
	
	private ImageView btnBack;
	private TextView tvTitle;
	
	public TitleLayout(Context context){
		super(context);
		initTitleLayout(context, null);
	}
	
	public TitleLayout(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initTitleLayout(context, attrs);
	}
	
	
	private void initTitleLayout(Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
			LayoutInflater.from(context).inflate(R.layout.title, this);

			btnBack =  (ImageView) findViewById(R.id.btn_titleback);
			tvTitle = (TextView) findViewById(R.id.tv_titletext);
			
			btnBack.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((Activity) getContext()).finish();
				}
			});
	}

	public void setText(String text){
		tvTitle.setText(text);
	}
	
	public void setBtnVisible(int visibility){
		btnBack.setVisibility(visibility);
	}
}