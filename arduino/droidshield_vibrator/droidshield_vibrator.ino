int pin = 13;
int val;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); //blocking shield
}

void loop() {
  
  //switch on the vibrator for 5 sec
  Serial.write(129); 
  Serial.write(5);
  
  delay(5000);
  delay(5000);
  
  //after 10 sec, make vibration again
}

void init_shield() {
  while(!Serial.available()){}
  Serial.read();//init byte
  digitalWrite(pin, HIGH);
  delay(1000);
  digitalWrite(pin, LOW);
}
