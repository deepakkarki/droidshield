#ifndef Accelerometer_h
#define Accelerometer_h

#include "Arduino.h"
class Accelerometer
{
  public:
	Accelerometer();
	int get_value(int axis);
};
#endif



