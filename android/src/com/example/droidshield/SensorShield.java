/**
 * Author : Deepak Karki
 * copyright 2014
 */
package com.example.droidshield;

/*
 * This is the SensorShield interface. 
 * All sensor services that will be made available through this app will implement this.
 * eg : accelerometer, magnetometer, gyroscope, light sensor, proximity sensor, GPS, NFC  
 * 		custom UI elements (sliders, keypad, etc.) , etc
 */

public interface SensorShield {
	//all values have to be written to bluetooth stream as string itself, so may as well return a string. 
	public byte[] getValue(DroidConnect dev);
	//But will it make better sense to have a object of custom type "Info" returned.
	//Info will have constructors of all types
	//Info(String), Info(int), Info(float), etc. 
	//i.value() will gives back the serialized byte[], viz written to bt stream. i => Info object  
}
