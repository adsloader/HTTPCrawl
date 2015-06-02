package com.campandroid.httpcrawl;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidcamp.my.MyHttpRequest;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    
	Button btnGetWeather;
	TextView txtWeather;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		txtWeather = (TextView)findViewById(R.id.txtWeather);
		btnGetWeather = (Button)findViewById(R.id.btnGetWeather);
		btnGetWeather.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				// 1. ���� ���������� ��� ������ �ܾ�´�. 
				MyHttpRequest http = new MyHttpRequest(getApplicationContext());
				String sUrl = "http://api.openweathermap.org/data/2.5/weather?q=seoul&units=metric";
				
				try {
					int nResultCode = http.execute(sUrl, "GET").get();
				    if(nResultCode == MyHttpRequest.REQUEST_FAIL) return;
				    
				    // 2. ������ ���ڿ�(JSON)�� ó���Ѵ�.
				    String sJson = http.getString();
                    JSONObject json = new JSONObject(sJson);
					
                    JSONArray  json_weather  = json.getJSONArray("weather");
					JSONObject json_item     = json_weather.getJSONObject(0);
					
					String sToday  = json_item.getString("description");
					String sStatus = json_item.getString("main");
					
					// �ּ�, �ִ� �µ����Ѵ�.
					JSONObject  json_main  = json.getJSONObject("main");
					
					String sMin = json_main.getString("temp_min");
					String sMax   = json_main.getString("temp_max");
					
					txtWeather.setText(sToday +"\n"+ "�ּ�:" + sMin + " �ִ�:" + sMax);
					//txtWeather.setText(sToday);
					
					// 3. ������ ����� �̹����� �����Ѵ�.
					setWeatherImage(sStatus);
				    
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}} );
	}
	
	// ���ڿ��� �ް� ������ ����� �̹����� ����Ѵ�.
	void setWeatherImage(String sWeather){
		// ImageView�� ��������
		ImageView imgWeather = (ImageView)findViewById(R.id.imgWeather);
		
		// �ҹ��ڷ� �ٲپ��ּ���
		sWeather = sWeather.toLowerCase();
		
		// ���ڿ��� �м��� ��(..����ڵ�)..
		// �̹����� �����Ѵ�. <-- ������ ��ô ����.
		int nRES_ID = R.drawable.unknown;
		if( sWeather.indexOf("clear") > -1) {
			nRES_ID = R.drawable.sun;
		
		} else if(sWeather.indexOf("clouds") > -1){
			nRES_ID = R.drawable.cloud;
			
		} else if(sWeather.indexOf("rain") > -1){
			nRES_ID = R.drawable.rain;
			
		} 
		
		// �̹����� �����Ѵ�.
		imgWeather.setImageResource(nRES_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
