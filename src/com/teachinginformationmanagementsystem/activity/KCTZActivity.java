package com.teachinginformationmanagementsystem.activity;

import java.util.ArrayList;
import java.util.List;

import com.teachinginformationmanagementsystem.model.Message;
import com.teachinginformationmanagementsystem.util.ContextUtil;
import com.teachinginformationmanagementsystem.util.HttpCallbackListener;
import com.teachinginformationmanagementsystem.util.HttpUtil;
import com.teachinginformationmanagementsystem.util.LogUtil;
import com.teachinginformationmanagementsystem.util.MessageAdapter;
import com.teachinginformationmanagementsystem.util.TitleLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KCTZActivity extends Activity {

	private int id;
	private TextView tvNullMessage;
	private ListView listKCTZ;
	private List<Message> messageList = new ArrayList<Message>();
	private MessageAdapter adapter;
	private TitleLayout rlTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kctz);
		
		rlTitle = (TitleLayout) findViewById(R.id.titlelayout_kctz);
		rlTitle.setText("课程通知");
		
		SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("course",0);
		id = pref.getInt("id", 0);

		tvNullMessage = (TextView) findViewById(R.id.tv_nullmessage);
		listKCTZ = (ListView) findViewById(R.id.list_message);
		
		String url = HttpUtil.BASE_URL + "MessagesServlet";
		String message = "id="+id;
		HttpUtil.sendHttpRequest(url, message, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				LogUtil.d("课程通知的返回信息", response);	
				if (response.equals("FALSE")) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(ContextUtil.getContext(),"获取课程通知失败!", Toast.LENGTH_SHORT).show();
						}
					});
				}else if(response.equals("NULL")){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							listKCTZ.setVisibility(View.GONE);
						}
					});
					
				}else{
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tvNullMessage.setVisibility(View.GONE);
						}
					});
					
					
					String[] allMessages = response.split(",");
					for(String s:allMessages){
						LogUtil.d("下一个通知", s);
					}
					for(String s:allMessages){
						String[] array = s.split("\\|");
						Message mes = new Message(array[0],array[1]);
						messageList.add(mes);
					}
					////////////////////////////////
					adapter = new MessageAdapter(KCTZActivity.this, R.layout.item_message, messageList);
					listKCTZ.setAdapter(adapter);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("user",0);
		String type = pref.getString("type", "");

		if(type.equals("student")){
			return super.onCreateOptionsMenu(menu);
		}
		getMenuInflater().inflate(R.menu.kctz_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_addmes:
			Intent intent = new Intent(KCTZActivity.this,AddMesActivity.class);
			intent.putExtra("id", id);
			startActivityForResult(intent, 1);
		break;
		default:
		}

		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					String content = data.getStringExtra("content");
					String date = data.getStringExtra("date");
					Message mes = new Message(content, date);
					messageList.add(mes);
					if(adapter == null){
						adapter = new MessageAdapter(KCTZActivity.this, R.layout.item_message, messageList);
						listKCTZ.setAdapter(adapter);
					}
					adapter.notifyDataSetChanged();
				}
				break;
			default:
		}
	}

}
