package com.example.droidshield;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerometerShield implements SensorShield, SensorEventListener {

	//value of the accelerometer reading
	private float current_value[];
	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    
    //every shield has a reference to the service it is working for. 
    @SuppressWarnings("unused")
	private ShieldService ss; //useful for creating activities, etc.
    
    public AccelerometerShield(ShieldService s)
    {
    	ss= s;
    	mSensorManager = (SensorManager)s.getSystemService(Activity.SENSOR_SERVICE);
    	mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	Log.i("Acc-Shield", "Created object");
    	
    }
    
/*
 *     The getValue function of AccelerometerShield class returns a 
 * @see com.example.droidshield.SensorShield#getValue(com.example.droidshield.DroidConnect)
 */
	@Override
	public byte[] getValue(DroidConnect dev) {
		
		//extract data from dev, it will specify x/y/z axis reading
		//(ch == 0/1/2/3) => x/y/z/all 
		byte ch = dev.readByte();
		
		Log.i("Acc-Shield", "getting value");
		
		//register the listener
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		while(current_value == null){
			//wait for callback to set value
		}
		
		//value array that is sent back
		byte vals[]; 
		
		//either one of x, y, z axis
		if(ch < 3){
			vals = new byte[1];
			vals[0] = (byte)current_value[ch];
		}
		
		//return x,y,z axis
		else{
				vals =  new byte[3];
				for(int i=0; i < 3; i++){
					vals[i] = (byte)current_value[i];
				}
		}
		
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
