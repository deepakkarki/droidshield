int pin = 13;

void setup() {
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); 
}

void loop() {
  
  //if current acc data is > 5
  if(getAccData() > 90){
    //vibrate the phone
    vibrate(1);
  }
  
  else{
    //set output pin to high
    digitalWrite(pin, HIGH);
  }
  
  //wait
  delay(1200);
  
  //set output pin to low again===
  digitalWrite(pin, LOW);  
}



/****************** API *****************/


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
  Serial.write((char)1); 
  Serial.write((char)1);
  while(Serial.available() == 0){
    //wait
  }
  
  return Serial.read();
}

void vibrate(int time)
{
   //vibrate for one second
    Serial.write(129);
    Serial.write(time);
}
