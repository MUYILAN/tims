package com.teachinginformationmanagementsystem.util;

import java.util.List;

import com.teachinginformationmanagementsystem.activity.R;
import com.teachinginformationmanagementsystem.model.Message;
import com.teachinginformationmanagementsystem.model.Source;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SourceAdapter extends ArrayAdapter<Source> {

	private int resourceId;
	
	public SourceAdapter(Context context, int resource, List<Source> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Source src = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView tvContent = (TextView) view.findViewById(R.id.tv_sou_name);
		TextView tvDate = (TextView) view.findViewById(R.id.tv_sou_date);
		tvContent.setText(src.getName());
		tvDate.setText(src.getDate());
		return view;
	}
}
