/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

/*
 * This class helps us control the LED flash on the mobile if any
 * reads from the dev passed to it. if the incoming byte is 1, 
 * LED is switched on, else it is switched off
 */

public class FlashShield implements ActuatorShield 
{
	Camera camera;
	
	@SuppressWarnings("unused")
	private ShieldService ss; //useful for creating activities, etc.
    
	public FlashShield(ShieldService s)
	{
		ss = s;
	}
	
	@Override
	public byte[] setValue(DroidConnect dev) {
		byte ch = dev.readByte();
		
		if(ch == 1){
			camera = Camera.open();
			Parameters p = camera.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(p);
			camera.startPreview();
		}
		
		else {
			camera = Camera.open();
			Parameters p = camera.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
			camera.stopPreview();
			camera.release();
		}
		return null;
	}

}
