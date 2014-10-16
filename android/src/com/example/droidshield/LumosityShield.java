package com.example.droidshield;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class LumosityShield implements SensorShield, SensorEventListener {

	//value of the accelerometer reading
	private float current_value[];
	
	private SensorManager mSensorManager;
    private Sensor mLumosity;
    
    //every shield has a reference to the service it is working for. 
    @SuppressWarnings("unused")
	private ShieldService ss; //useful for creating activities, etc.
    
    public LumosityShield(ShieldService s)
    {
    	ss= s;
    	mSensorManager = (SensorManager)s.getSystemService(Activity.SENSOR_SERVICE);
    	mLumosity = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    	Log.i("lumosity-Shield", "Created object");
    	
    }
    
	@Override
	public byte[] getValue(DroidConnect dev) {
		
		//nothing to read from dev, just return proximity value
		Log.i("lumosity-Shield", "getting value");
		
		//register the listener
		mSensorManager.registerListener(this, mLumosity, SensorManager.SENSOR_DELAY_NORMAL);
		
		while(current_value == null){
			//wait for callback to set value
		}
		
		//value array that is sent back
		byte vals[]; 
		
		vals = new byte[1];
		vals[0] = (byte)current_value[0]; //always 0
		Log.i("lumosity-shield",Float.toString(current_value[0]));
		
		
		//set current_value to null; so that next call won't get this value
		current_value = null;
		
		return vals; 
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		//assign the current_value to the reading
		current_value = event.values;
		mSensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// do nothing
	}

}
