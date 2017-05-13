package com.teachinginformationmanagementsystem.activity;

import java.util.ArrayList;
import java.util.List;

import com.teachinginformationmanagementsystem.model.Source;
import com.teachinginformationmanagementsystem.util.ContextUtil;
import com.teachinginformationmanagementsystem.util.HttpCallbackListener;
import com.teachinginformationmanagementsystem.util.HttpUtil;
import com.teachinginformationmanagementsystem.util.LogUtil;
import com.teachinginformationmanagementsystem.util.MessageAdapter;
import com.teachinginformationmanagementsystem.util.SourceAdapter;
import com.teachinginformationmanagementsystem.util.TitleLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KCZLActivity extends Activity {

	private int id;
	private TextView tvNullSource;
	private ListView listKCZL;
	private List<Source> sourceList = new ArrayList<Source>();
	private SourceAdapter adapter;
	private TitleLayout rlTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kczl);
		
		TitleLayout rlTitle = (TitleLayout) findViewById(R.id.titlelayout_kczl);
		rlTitle.setText("课程资料");
		
		SharedPreferences pref=ContextUtil.getContext().getSharedPreferences("course",0);
		id = pref.getInt("id", 0);

		tvNullSource = (TextView) findViewById(R.id.tv_nullsource);
		listKCZL = (ListView) findViewById(R.id.list_source);
		
		String url = HttpUtil.BASE_URL + "SourcesServlet";
		String message = "id="+id;
		HttpUtil.sendHttpRequest(url, message, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				LogUtil.d("课程资料的返回信息", response);	
				if (response.equals("FALSE")) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(ContextUtil.getContext(),"获取课程资料失败!", Toast.LENGTH_SHORT).show();
						}
					});
				}else if(response.equals("NULL")){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							listKCZL.setVisibility(View.GONE);
						}
					});
					
				}else{
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tvNullSource.setVisibility(View.GONE);
						}
					});
					
					
					String[] allSources = response.split(",");
					for(String s:allSources){
						LogUtil.d("下一个通知", s);
					}
					for(String s:allSources){
						String[] array = s.split("\\|");
						Source src = new Source(Integer.parseInt(array[0]), Integer.parseInt(array[1]), array[2], array[3]);
						sourceList.add(src);
					}
					////////////////////////////////
					adapter = new SourceAdapter(KCZLActivity.this, R.layout.item_source, sourceList);
					listKCZL.setAdapter(adapter);

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
		getMenuInflater().inflate(R.menu.kczl_activity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_addsrc:
			Intent intent = new Intent(KCZLActivity.this,AddMesActivity.class);
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
					/*String name = data.getStringExtra("name");
					String date = data.getStringExtra("date");
					Message mes = new Message(content, date);
					messageList.add(mes);
					if(adapter == null){
						adapter = new MessageAdapter(KCTZActivity.this, R.layout.item_message, messageList);
						listKCTZ.setAdapter(adapter);
					}
					adapter.notifyDataSetChanged();*/
					//OnCreate();
				}
				break;
			default:
		}
	}
	
}
