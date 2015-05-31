package com.androidcamp.my;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class MyHttpRequest extends AsyncTask<String, Integer, Integer> {

	Context ctx;
	StringBuilder result    = null;
	
	// 연결타임아웃과 HTTP를 읽어온 성공여부를 정의
	public static int CONNECTION_TIME_OUT = 1000 * 5;
	public static int REQUEST_OK   = 0;
	public static int REQUEST_FAIL = -1;
	
	public MyHttpRequest(Context context) {
		ctx = context;
		result = new StringBuilder();
	}

	public String getString() {
		return result.toString();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		HttpURLConnection conn = null;

		try {
			URL url = new URL(arg0[0]);
			conn = (HttpURLConnection)url.openConnection();
			
			// 연결이 안되었으면 나가삼.
			if(conn == null) return -1;
			
			conn.setConnectTimeout(CONNECTION_TIME_OUT);
			conn.setRequestMethod(arg0[1]);		// GET or POST
			conn.setDoInput(true);				
			conn.setDoOutput(true);				
			
			int resCode = conn.getResponseCode();
			
			// HTTP를 제대로 가져왔다면...
			if(resCode == HttpURLConnection.HTTP_OK) {

				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = null;

				while(true) {

					line = reader.readLine();

					if(line == null) {
						break;
					}
					result.append(line);
					result.append("\n");
				}

				reader.close();
				conn.disconnect();

			} else {
				return REQUEST_FAIL;
			}

			
			
		} catch(Exception ex) {
			ex.printStackTrace();
			return REQUEST_FAIL;
		}
		return REQUEST_OK;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
