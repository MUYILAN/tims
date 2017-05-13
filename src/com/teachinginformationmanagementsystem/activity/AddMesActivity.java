package com.teachinginformationmanagementsystem.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder; 
import java.net.URLDecoder;
import com.teachinginformationmanagementsystem.util.ContextUtil;
import com.teachinginformationmanagementsystem.util.HttpCallbackListener;
import com.teachinginformationmanagementsystem.util.HttpUtil;
import com.teachinginformationmanagementsystem.util.LogUtil;
import com.teachinginformationmanagementsystem.util.TitleLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMesActivity extends Activity {
	
	private EditText content; 
	private Button btn_ok;
	private Button btn_cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addmes);
		
		///////////////////
		String SDPATH = Environment .getExternalStorageDirectory().getAbsolutePath();
		LogUtil.d("获取的路径", SDPATH);
		////////////////////////////////////////////
		TitleLayout rlTitle = (TitleLayout) findViewById(R.id.titlelayout_addmes);
		rlTitle.setText("添加通知");
		
		content = (EditText)findViewById(R.id.et_addmes_content);
		btn_ok = (Button) findViewById(R.id.btn_addmes_ok);
		btn_cancel = (Button) findViewById(R.id.btn_addmes_cancel);
		
		btn_ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = getIntent();
				int id = intent.getIntExtra("id", 0);
				
				final String strContent = content.getText().toString();
				
				//Time time = new Time();
				SimpleDateFormat fm = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				final String strDate = fm.format(date);
				
				LogUtil.d("Date",strDate);
				LogUtil.d("content",strContent);
				
				if(strContent.equals("")){
					Toast.makeText(ContextUtil.getContext(),"请填写通知内容!", Toast.LENGTH_SHORT).show();
				}else{
					String url = HttpUtil.BASE_URL + "AddMesServlet";
					String message = "sql=insert into message(mes_courseid,mes_content,mes_date) values ('"+id+"','"+strContent+"（这里有中文）','"+strDate+"')";
					LogUtil.d("8859-1:sql",message);
					
					HttpUtil.sendHttpRequest(url, message, new HttpCallbackListener() {
						@Override
						public void onFinish(final String response) {
							LogUtil.d("获取课程的返回信息", response);	
							if (response.equals("FALSE")) {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(ContextUtil.getContext(),"添加失败!", Toast.LENGTH_SHORT).show();
									}
								});
							}else{
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(ContextUtil.getContext(),"添加成功!", Toast.LENGTH_SHORT).show();
										Intent intent = new Intent();
										intent.putExtra("content", strContent);
										intent.putExtra("date", strDate);
										setResult(RESULT_OK, intent);
										finish();

									}
								});

							}
						}
						@Override
						public void onError(Exception e) {

							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(ContextUtil.getContext(),"连接服务器失败!", Toast.LENGTH_SHORT).show();
								}
								});
							}
						});
					}
				}
			});
				
		btn_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
        	
        });
	}
}
