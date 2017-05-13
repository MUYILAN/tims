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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StuRegFragment extends Fragment {

	private EditText etRegUserAcc;
	private EditText etRegUserCls;
	private EditText etRegUserName;
	private EditText etRegUserTel;
	private EditText etRegUserPasswd;
	private EditText etRegUserPasswd0;
	private RadioGroup rgRegUserSex;
	//private RadioButton radioButton;
	private Button btnRegOK;
	//
	
	public void setBtnText(String text){
		if(btnRegOK != null){
			btnRegOK.setText(text);
		}
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.d("StuRegFra", "onCreateView");
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_stureg,container, false);
		etRegUserAcc = (EditText) view.findViewById(R.id.stureg_user_acc);
		etRegUserCls = (EditText) view.findViewById(R.id.stureg_user_cls);
		etRegUserName = (EditText) view.findViewById(R.id.stureg_user_name);
		etRegUserTel = (EditText) view.findViewById(R.id.stureg_user_tel);
		etRegUserPasswd = (EditText) view.findViewById(R.id.stureg_user_passwd);
		etRegUserPasswd0 = (EditText) view.findViewById(R.id.stureg_user_passwd0);
		rgRegUserSex = (RadioGroup) view.findViewById(R.id.stureg_user_sex);
		btnRegOK = (Button) view.findViewById(R.id.stureg_ok);

		/*rgRegUserSex.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
          
	        @Override  
	        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {  
	            // TODO Auto-generated method stub  
	          radioButton = (RadioButton) getActivity().findViewById(checkedId);    
	        }  
		});  */
		btnRegOK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String atyName = getActivity().getClass().getSimpleName();
				String acc = etRegUserAcc.getText().toString();
				String name = etRegUserName.getText().toString();
				//String sex = radioButton.getText().toString();
				String sex = rgRegUserSex.getCheckedRadioButtonId()==R.id.stureg_user_sex_male?"男":"女";
				String cls = etRegUserCls.getText().toString();
				String tel = etRegUserTel.getText().toString();
				String passwd = etRegUserPasswd.getText().toString();
				String passwd0 = etRegUserPasswd0.getText().toString();
				
				// TODO Auto-generated method stub
				if(acc.equals("") || name.equals("") || cls.equals("") || tel.equals("") || passwd.equals("") || passwd0.equals("")){
					Toast.makeText(ContextUtil.getContext(), "请填写完整信息!", Toast.LENGTH_LONG).show();
				}else if(!(passwd.equals(passwd0))){
					Toast.makeText(ContextUtil.getContext(), "密码不一致!", Toast.LENGTH_LONG).show();
				}else{
					String address = HttpUtil.BASE_URL+"RegisterServlet";
					String sql = "sql=insert into student values('"+acc+"','"+name+"','"+sex+"','"+cls+"','"+tel+"','"+passwd+"');";
					if(atyName.equals("ModifyActivity")){
						SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("user",0);
						String oldId = pref.getString("acc", "");
						sql += "&del=delete from student where stu_id='"+oldId+"'";
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
