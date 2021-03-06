#include <Servo.h> 
Servo s;

int pin = 13;
int val;
void setup() {
  // put your setup code here, to run once:
  s.attach(11);
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); //blocking shield
}

void loop() {
  val = getAccData();
  s.write(val);
  delay(250);
  
}

void init_shield() {
  while(!Serial.available()){}
  Serial.read();//init byte
  digitalWrite(pin, HIGH);
  delay(1000);
  digitalWrite(pin, LOW);
}

int getAccData()
{
  //request for accelerometer data
  Serial.write(1); 
  Serial.write(1);
  while(Serial.available() == 0){
    //wait
  }
  
  return Serial.read();
}
