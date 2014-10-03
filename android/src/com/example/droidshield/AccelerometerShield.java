package com.example.droidshield;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerShield implements SensorShield, SensorEventListener {

	//value of the accelerometer reading
	static String value;
	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShieldService ss;
    //every 
    public AccelerometerShield(ShieldService s)
    {
    	ss= s;
    	mSensorManager = (SensorManager)s.getSystemService(Activity.SENSOR_SERVICE);
    	mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	Log.i("Acc-Shield", "Created object");
    }
    
	@Override
	public String getValue(DroidConnect dev) {
		// TODO extract data from dev, it will specify x/y/z axis reading 
		
		Log.i("Acc-Shield", "getting value");
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
