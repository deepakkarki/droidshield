#include "Accelerometer.h"
#include "Arduino.h"

Accelerometer::Accelerometer()
{
}

int Accelerometer::get_value(int axis)
{
	if (axis < 0 || axis > 4){
		axis = 0; //default is x axis
	}
	while(Serial.available() != 0){
    		Serial.read();
	}
	
	Serial.write(1); 
  	Serial.write(axis);
  	
	while(Serial.available() == 0){
		//wait
	}
	
	//TODO : if(axis ==4)
	return Serial.read();
	
}
