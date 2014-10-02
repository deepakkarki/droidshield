package com.example.droidshield;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerShield implements SensorShield, SensorEventListener {

	//value of the accelerometer reading
	String value;
	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    public AccelerometerShield()
    {
    	 mSensorManager = (SensorManager)new Activity().getSystemService(Activity.SENSOR_SERVICE);
         mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    
	@Override
	public String getValue(DroidConnect dev) {
		// TODO take the data, char[0] will specify x/y/z axis reading 
		
		//register the listener
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		while(value == null){
			//wait for callback to set value
		}
		//get the value
		String v = value;
		
		//set value to null; so that next call won't get this value
		value = null;
		return v;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		//y-axis reading. b/w "0" to "9"
		value = Integer.toString((int)event.values[1]);
		mSensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// do nothing
	}

}