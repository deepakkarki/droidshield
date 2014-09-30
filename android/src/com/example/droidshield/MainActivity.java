/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
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

	DroidConnect dev;
	OnClickListener connect = new OnClickListener() 
	{
		@Override
		public void onClick(View conn_button) {
			Button con = (Button)conn_button;
			
			//device is not connected
			if(!dev.isConnected()){
				//TODO : replace the following hard code with code to scan bt device
				
				//add code to select mac of bluetooth
				dev.setMAC("00:12:12:24:16:30"); 
				
				//try to connect
				if(dev.connect()){
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
			
			//else device is connected
			
			else{
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
		public void onClick(View conn_button) {
			//start the service -- which is not yet written
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dev = new DroidConnect();
		Button conn = (Button)findViewById(R.id.connect_button);
		Button service = (Button)findViewById(R.id.start_service);
		conn.setOnClickListener(connect);
		service.setOnClickListener(start_service);
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
