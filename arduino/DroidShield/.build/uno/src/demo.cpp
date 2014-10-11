#include <Arduino.h>
#include "Accelerometer.h"
void setup();
void loop();
void init_shield();
#line 1 "src/demo.ino"
//#include "Accelerometer.h"

int pin = 13;
int val;
Accelerometer acc;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); //blocking shield
}

void loop() {
  val = acc.get_value(1); //y axis
  
  if(val > 5){
    digitalWrite(pin, HIGH);
  }
  else{
    digitalWrite(pin, LOW);
  }
  delay(250);
  
}

void init_shield() {
  while(!Serial.available()){}
  Serial.read();//init byte
  digitalWrite(pin, HIGH);
  delay(1000);
  digitalWrite(pin, LOW);
}
