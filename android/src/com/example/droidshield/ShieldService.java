/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

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
		Log.i("ShieldService", "onCreate started");
		super.onCreate();
		dev = MainActivity.dev;
		if(dev == null)
		{
			Log.e("ShieldService","Device is null in main");
		}
		setup();
		shield_thread = new Thread(new ShieldThread(), "shield_thread");
		running = true;
		shield_thread.start();
		//tell main activity that service has started
		MainActivity.service_active = true;
	}
	
	public void setup()
	{
		//sets up the arrays 'sensors' and 'actuators'
		Log.i("ShieldService", "setting up stuff");
		sensors = new SensorShield[127];
		actuators = new ActuatorShield[127];
		
		sensors[1] = new AccelerometerShield(this);
		sensors[2] = new ProximityShield();
		actuators[1] = new VibrationShield(this);
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
		Log.i("ShieldService", "service being destroyed");
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
			Log.i("ShieldService", "Thread-run");
			startShield();
		}
		
		public void startShield()
		{
			byte [] retVal = null;
			Log.i("ShieldService", "infinite loop starting in thread");
			
			//TODO : come up with a better initialization sequence than one below
			//send init code
			dev.sendByte((byte)1);
			
			while(running){
				//get data from bluetooth device
				int opCode = (int)(dev.readByte() & 0xFF);
				//NOTE byte range -128 to 127; above code makes it into an int of 0-255 
				
				Log.i("acc-request-code", Integer.toString(opCode));
				
				if(opCode < 128 && sensors[opCode] != null){ 
					//TODO : maybe I need to use synchronize {}, just in case
					retVal = sensors[opCode].getValue(dev);
					Log.i("acc-data", Byte.toString(retVal[0]));
				}
				
				else if (opCode < 256 && actuators[opCode - 128] != null){
					retVal = actuators[opCode -128].setValue(dev);
				}
				
				else{
					//screw it
					continue;
					//TODO : wait..... I should clear the buffer?
				}
				if (retVal != null){
					dev.send(retVal);
				}
			}
		}
		
	}
	
}
