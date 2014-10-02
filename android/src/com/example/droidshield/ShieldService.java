/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.droidshield.DroidConnect;

public class ShieldService extends Service {

	DroidConnect dev;
	Thread shield_thread;
	boolean running;
	SensorShield sensors[];
	ActuatorShield actuators[];
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override 
	public void onCreate()
	{
		super.onCreate();
		shield_thread = new Thread(new ShieldThread(), "shield_thread");
		running = true;
		shield_thread.start();
		//tell main activity that service has started
		MainActivity.service_active = true;
	}
	
	public void setup()
	{
		//sets up the arrays 'sensors' and 'actuators'
		sensors = new SensorShield[127];
		actuators = new ActuatorShield[127];
		
		sensors[0] = new AccelerometerShield();
		sensors[1] = new ProximityShield();
		actuators[0] = new VibrationShield();
	}
	
	@Override
	public int onStartCommand(Intent i, int flags, int startId)
	{
		//don't do anything if a startService call is made
		//and already service is running; hence all the logic is in onCreate - 
		//viz not called if an instance of service is already running.
		//**Is this even required???**
		return START_STICKY;
		
	}
	
	@Override
	public void onDestroy()
	{
		//stop the thread
		running = false;
		
		//tell main activity that service has ended
		MainActivity.service_active = false;
		
		super.onDestroy();
	}
	
	class ShieldThread implements Runnable 
	{
		@Override
		public void run() 
		{
			startShield();
		}
		
		public void startShield()
		{
			while(running){
				//get data from bluetooth device
				//parse it
				//get result
				//if result != null
					//send it back
			}
		}	
	}
	
}
