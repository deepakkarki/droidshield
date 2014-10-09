int pin = 13;
int val;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); //blocking shield
}

void loop() {
  
  //request for accelerometer data
  Serial.write((char)1); 
  Serial.write((char)1);
  while(Serial.available() == 0){
    //wait
  }
  
  val = Serial.read();
  
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
