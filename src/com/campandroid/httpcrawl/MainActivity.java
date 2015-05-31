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
				String sUrl = "http://api.openweathermap.org/data/2.5/weather?id=1835848";
				
				try {
					int nResultCode = http.execute(sUrl, "GET").get();
				    if(nResultCode == MyHttpRequest.REQUEST_FAIL) return;
				    
				    // 2. ������ ���ڿ�(JSON)�� ó���Ѵ�.
                    JSONObject json = new JSONObject(http.getString());
					
                    JSONArray  json_weather  = json.getJSONArray("weather");
					JSONObject json_item     = json_weather.getJSONObject(0);
					
					String sToday = json_item.getString("description");
					
					// �ϸ�, ���� �ð��� ����Ѵ�.
//					JSONObject  json_sys  = json.getJSONObject("sys");
//					
//					String sSungRise = json_sys.getString("sunrise");
//					String sSunSet   = json_sys.getString("sunset");
//					
//					SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//					
//					long nSunRise  = new Long (sSungRise);  
//					long nSunSet   = new Long (sSunSet);  
//					
//					String sSunTime = "����: " + df.format(nSunRise) + " �ϸ�:" + df.format(nSunSet);
//					txtWeather.setText(sToday +"\n"+ sSunTime );
					txtWeather.setText(sToday);
					
					// 3. ������ ����� �̹����� �����Ѵ�.
					setWeatherImage(sToday);
				    
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
		
		// ���ڿ��� �м��� ��(..����ڵ�)..
		// �̹����� �����Ѵ�. <-- ������ ��ô ����.
		int nRES_ID = R.drawable.unknown;
		if( sWeather.indexOf("Clear") > -1) {
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
