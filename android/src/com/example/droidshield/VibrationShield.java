package com.example.droidshield;

import android.content.Context;
import android.os.Vibrator;

public class VibrationShield implements ActuatorShield {

	//every shield has a reference to the service it is working for. 
	private ShieldService ss; //useful for creating activities, etc.
    
	public VibrationShield(ShieldService s)
	{
		ss = s;
	}
	
	@Override
	public byte[] setValue(DroidConnect dev) {
		
		//create a reference to vibrator on the phone 
		Vibrator v = (Vibrator) ss.getSystemService(Context.VIBRATOR_SERVICE);
		
		byte time = dev.readByte();
		
		//vibrate for 'time' seconds
		v.vibrate(time * 1000);
		
		return null;
	}

}
