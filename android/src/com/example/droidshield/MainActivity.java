/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.droidshield.DroidConnect;
/*
 * This activity handles the UI when the user starts the app.
 * The key functionality is to make a bluetooth connection to the H/W
 * and to start the service so that the H/W can start interacting with it.  
 */

public class MainActivity extends ActionBarActivity {

	static DroidConnect dev;
	static boolean service_active; //variable will be set by the service while running
	
	//TODO : I have to make service button inactive until device is connected.
			//and make connect button inactive when service is running.
		//Aishwarya - ^ is your job, just 4 lines.
	OnClickListener connect = new OnClickListener() 
	{
		@Override
		public void onClick(View conn_button) {
			Button con = (Button)conn_button;
			Log.i("MainActivity", "onClick-started");
			
			//device is not connected
			if(!dev.isConnected()){
				//TODO : replace the following hard code with code to scan bt device
					//Aishwarya - your job; decently big
				
				//add code to select mac of bluetooth
				dev.setMAC("00:12:12:24:16:30"); //replace with dev.setDevice
				Log.i("MainActivity", "onClick-MACset");
				
				//try to connect
				if(dev.connect()){
					Log.i("MainActivity", "onClick-connected device");
					con.setText("Disconnect");
					TextView status = (TextView) findViewById(R.id.status_text);
					status.setText("Connected to device!");
				}
				
				else{
					//make a TOAST and warn that connection FAILED
					Context context = getApplicationContext();

					Toast toast = Toast.makeText(context, "Failed to connect!", Toast.LENGTH_SHORT);
					toast.show();
				}
			}
			
			//else device is connected; now close
			
			else{
				if(service_active){//remove this later; just disable this btn when service is running
					
					//make the "callback" myself
					start_service.onClick(findViewById(R.id.start_service));
				}
				dev.close();
				con.setText("Connect to H/W");
				TextView status = (TextView) findViewById(R.id.status_text);
				status.setText("");
				
			}
		}
	};
	
	OnClickListener start_service = new OnClickListener() 
	{
		@Override
		public void onClick(View ser_button) {
			Button ser = (Button) ser_button;
			
			TextView status = (TextView) findViewById(R.id.status_text);
			
			if(dev.isConnected()){//remove this; just disable service button when not connected.
				
				if(!MainActivity.service_active){
					status.setText("Service Started");
					ser.setText("Stop Service");
					//start the service 
					Intent i = new Intent(MainActivity.this, ShieldService.class); 
					startService(i);
				}
				
				else{
					//shutdown the service 
					status.setText("");
					ser.setText("Start Service");
					Intent i = new Intent(MainActivity.this, ShieldService.class); 
					stopService(i);
				}
			}
			
			else{
				//make a TOAST and warn that connect device first
				Context context = getApplicationContext();
				Toast toast = Toast.makeText(context, "Connect device first!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("MainActivity", "onCreate-started");
		setContentView(R.layout.activity_main);
		dev = new DroidConnect(this);
		Button conn = (Button)findViewById(R.id.connect_button);
		Button service = (Button)findViewById(R.id.start_service);
		conn.setOnClickListener(connect);
		service.setOnClickListener(start_service);
		Log.i("MainActivity", "onCreate-ended");
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
