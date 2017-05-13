package com.teachinginformationmanagementsystem.activity;

import com.teachinginformationmanagementsystem.util.TitleLayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment02 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_main02,container, false);
		
		TitleLayout rlTitle = (TitleLayout) view.findViewById(R.id.titlelayout_mf02);
		rlTitle.setBtnVisible(View.GONE);
		rlTitle.setText("我的课表");
		return view;
	}

	
}
