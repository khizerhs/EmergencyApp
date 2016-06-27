package com.omah.emergency;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Eme_MainActivity extends Activity {
	String type=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
			Button b=(Button) findViewById(R.id.Emergency);
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					SharedPreferences pref=getSharedPreferences("MYPREF", 0);
					String num1=pref.getString("num1","");
					String num2=pref.getString("num2","");
					String sm=pref.getString("sm","Emergency");
					
					
					if(num1.equals("") && num2.equals("")){
						Toast.makeText(Eme_MainActivity.this,"Set the Numbers and the message to be sent",Toast.LENGTH_SHORT).show();
					}
					else {
					GPSTracker gps = new GPSTracker(Eme_MainActivity.this);
					if(gps.canGetLocation())
					{
					String lat=String.valueOf(gps.getLatitude()); // returns latitude
					String lon=String.valueOf(gps.getLongitude()); // returns longitude
					type=gps.getType();
					
					String sms=sm.concat("\nMy Location \n" +
							"http://maps.google.com/maps?q="+lat+","+lon);
					
					Toast.makeText(Eme_MainActivity.this,type+" : "+lat+","+lon,Toast.LENGTH_SHORT).show();
					Log.d("hey",lat+","+lon);
					
					if(lat.equals("0"))
					{
						Toast.makeText(getApplicationContext(),
								"GPS co-ordinates not yet ready!! pls keep trying.",
								Toast.LENGTH_LONG).show();
					}
					else
					{
						
						try {
							SmsManager smsManager = SmsManager.getDefault();
							smsManager.sendTextMessage(num1, null, sms, null, null);
							smsManager.sendTextMessage(num2, null, sms, null, null);
							Toast.makeText(getApplicationContext(), "SMS Sent!",
										Toast.LENGTH_LONG).show();
						  } catch (Exception e) {
							Toast.makeText(getApplicationContext(),
								"SMS faild, please try again later!",
								Toast.LENGTH_LONG).show();
							e.printStackTrace();
						  }
					}
					}
					else{
					gps.showSettingsAlert();
						}
					
				
				}
					
						
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
		return true;
		
		
	}
	
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.change:
	
	        	Intent i =new Intent(this, Change.class);
	        	startActivity(i);
	        	
	        }
			return false;}

}
