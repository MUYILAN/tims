package com.teachinginformationmanagementsystem.activity;

import com.teachinginformationmanagementsystem.util.TitleLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class KQGLActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kqgl);
		
		TitleLayout rlTitle = (TitleLayout) findViewById(R.id.titlelayout_kqgl);
		rlTitle.setText("考勤管理");
	}
}
