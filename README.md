DroidShield
===========

A framework that enables you to use your android phone as a shield for hardware platforms such as Arduino, Raspberry Pi, Beaglebone Black and more.

The framework contains the android app, and hardware specific libraries. The communitcation happens over serial-bluetooth or serial-usb. 

##Motivation
I started working on this framework because I was tired of not having the right sensors and actuators to prototype my project. Simple stuff like sending SMS or getting the geo-location required costly hardware add-ons. At the same time our mobiles are loaded with sensors and actuators - such as accelerometers, touch display, flash light, camera, proximity sensor, GPS, GSM capabilities (calling, text), 3G net connection, etc. just to name a few.

So I started asking myself what would be the easiest way to leverage the plethora of hardware utilities available on my mobile for my projects? What is the best way to seamlessly integrate them into my prototype? DroidShield was the result.

##Goals
Of course, at the high level the goal is to solve the problem stated under the motivation section. *How do you effectively enable the use of sensors and actuators on the mobile phone in your hardware projects/prototypes.* 

Being a bit more specific, the idea is to have an API for the hardware with which can get or push data to the mobile. The user should be able to connect via Bluetooth or usb-serial, and use the APIs.

For example
```c
#include "droidShield.h"

// witch on flash light
droidShield.flashLight.write(1);

// end SMS
droidShield.sms.send("9012345678", "Hello! How are you?");

// read accelerometer 'x' axis 
int x_val = droidShield.accel.read('x');
```

To make this happen, on the mobile side there is the droidShield mobile app. For the user it should  be a simple 2 step procedure :

1. Switch on the app - app connects to the arduino either via Bluetooth or USB.
2. User uses the API in his/her program.

No more complexity on the user side should be needed - no android side coding, no looking up complex documentation, no buying and configuring hardware add-ons. 
**Have an idea? Execute it!**


