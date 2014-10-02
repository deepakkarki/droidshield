/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

/*
 * This is the ActuatorShield interface. 
 * All actuator services that will be made available through this app will implement this.
 * eg : light output, vibrate, alarm, store to SD, send SMS/Text/Tweet, custom UI elements
 */

public interface ActuatorShield {
	public String setValue(char [] data);
}
