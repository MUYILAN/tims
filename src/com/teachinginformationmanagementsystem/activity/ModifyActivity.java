package com.teachinginformationmanagementsystem.activity;

import com.teachinginformationmanagementsystem.util.ContextUtil;
import com.teachinginformationmanagementsystem.util.LogUtil;
import com.teachinginformationmanagementsystem.util.TitleLayout;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ModifyActivity extends Activity {

	private Fragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LogUtil.d("Modify", "onCreate");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_modify);
		
		TitleLayout rlTitle = (TitleLayout) findViewById(R.id.titlelayout_modify);
		rlTitle.setText("编辑资料");
		
		SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("user",0);
		String type = pref.getString("type", "");
		
		FragmentManager FragmentManager = getFragmentManager();
		FragmentTransaction transaction = FragmentManager.beginTransaction();
		if(type.equals("teacher")){
			fragment = new TeaRegFragment();			
		}else{
			fragment = new StuRegFragment();		
		}		
		transaction.replace(R.id.fl_modify, fragment).commit();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("user",0);
		String type = pref.getString("type", "");
		if(type.equals("teacher")){
			((TeaRegFragment) fragment).setBtnText("完成");	
				
		}else{
			((StuRegFragment) fragment).setBtnText("完成");	
		}		
	}

}
