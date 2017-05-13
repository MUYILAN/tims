package com.teachinginformationmanagementsystem.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class HttpUtil {
	public static final String BASE_URL = "http://192.168.42.252:8080/WebProject/";
	public static void sendHttpRequest(final String address,final String message,final HttpCallbackListener listener) {
		Context context = ContextUtil.getContext();
		if (!isNetworkAvailable(context)) {
			Toast.makeText(context, "网络不可用!",Toast.LENGTH_SHORT).show();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try{
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoInput(true);
					connection.setDoOutput(true);
					
					//connection.connect();
					
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes(message);
					out.flush();
				    out.close();
				        
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
			
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						e.printStackTrace();
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	 public static boolean isNetworkAvailable(Context context)
	    {
	        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
	        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        
	        if (connectivityManager == null)
	        {
	            return false;
	        }
	        else
	        {
	            // 获取NetworkInfo对象
	            @SuppressWarnings("deprecation")
				NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
	            
	            if (networkInfo != null && networkInfo.length > 0)
	            {
	                for (int i = 0; i < networkInfo.length; i++)
	                {
	                    System.out.println(i + ":状态:" + networkInfo[i].getState());
	                    System.out.println(i + ":类型:" + networkInfo[i].getTypeName());
	                    // 判断当前网络状态是否为连接状态
	                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
	                    {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }
}
