package com.teachinginformationmanagementsystem.activity;

import com.teachinginformationmanagementsystem.util.ContextUtil;
import com.teachinginformationmanagementsystem.util.HttpCallbackListener;
import com.teachinginformationmanagementsystem.util.HttpUtil;
import com.teachinginformationmanagementsystem.util.LogUtil;



import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class TeaRegFragment extends Fragment {
	private EditText etRegUserAcc;
	private Spinner teaRegUserTitle;
	private EditText etRegUserName;
	private EditText etRegUserTel;
	private EditText etRegUserPasswd;
	private EditText etRegUserPasswd0;
	private RadioGroup rgRegUserSex;
	
	private Button btnRegOK;

	public void setBtnText(String text){
		if(btnRegOK != null){
			btnRegOK.setText(text);
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_teareg, container, false);	
		
		etRegUserAcc = (EditText) view.findViewById(R.id.teareg_user_acc);
		teaRegUserTitle = (Spinner) view.findViewById(R.id.teareg_user_title);
		etRegUserName = (EditText) view.findViewById(R.id.teareg_user_name);
		etRegUserTel = (EditText) view.findViewById(R.id.teareg_user_tel);
		etRegUserPasswd = (EditText) view.findViewById(R.id.teareg_user_passwd);
		etRegUserPasswd0 = (EditText) view.findViewById(R.id.teareg_user_passwd0);
		rgRegUserSex = (RadioGroup) view.findViewById(R.id.teareg_user_sex);
		btnRegOK = (Button) view.findViewById(R.id.teareg_ok);
		
		btnRegOK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String atyName = getActivity().getClass().getSimpleName();
				LogUtil.d("所在", getActivity().getClass().getSimpleName());
				String title = teaRegUserTitle.getSelectedItem().toString();
				String acc = etRegUserAcc.getText().toString();
				String name = etRegUserName.getText().toString();
				//String sex = radioButton.getText().toString();
				String sex = rgRegUserSex.getCheckedRadioButtonId()==R.id.stureg_user_sex_male?"男":"女";
				String tel = etRegUserTel.getText().toString();
				String passwd = etRegUserPasswd.getText().toString();
				String passwd0 = etRegUserPasswd0.getText().toString();
				
				// TODO Auto-generated method stub
				if(acc.equals("") || name.equals("") || title.equals("") || tel.equals("") || passwd.equals("") || passwd0.equals("")){
					Toast.makeText(ContextUtil.getContext(), "请填写完整信息!", Toast.LENGTH_LONG).show();
				}else if(!(passwd.equals(passwd0))){
					Toast.makeText(ContextUtil.getContext(), "密码不一致!", Toast.LENGTH_LONG).show();
				}else{
					String address = HttpUtil.BASE_URL+"RegisterServlet";
					String sql = "sql=insert into teacher values('"+acc+"','"+name+"','"+sex+"','"+title+"','"+tel+"','"+passwd+"');";
					if(atyName.equals("ModifyActivity")){
						SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("user",0);
						String oldId = pref.getString("acc", "");
						sql += "&del=delete from teacher where tea_id='"+oldId+"'";
					}
					LogUtil.d("提交前的数据", sql);
					HttpUtil.sendHttpRequest(address, sql, new HttpCallbackListener() {
						@Override
						public void onFinish(String response) {
							LogUtil.d("服务器的返回信息", response);
							if (response.equals("TRUE")) {
								Intent intent = new Intent(getActivity(), LoginActivity.class);
								startActivity(intent);
								getActivity().finish();
							}else{
								// 通过runOnUiThread()方法回到主线程处理逻辑
								getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(ContextUtil.getContext(),"操作失败!", Toast.LENGTH_SHORT).show();
								}
								});
								
							}
						}
						@Override
						public void onError(Exception e) {
								Toast.makeText(getActivity(),"连接服务器失败!", Toast.LENGTH_SHORT).show();
						}
					});
				}

			}
			
		});	
		
		return view;
	}
}
