int pin = 13;

void setup() {
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); 
}

void loop() {
  
  //if current prox data is > 5
  if(getProxData() == 0){
    //set digitalpin to high
    digitalWrite(pin, HIGH);
  }
  
  else{
    //set output pin to low
    digitalWrite(pin, LOW);
  }
  
  //wait
  delay(500);
   
}



/****************** API *****************/


void init_shield() {
  while(!Serial.available()){}
  Serial.read();//init byte
  digitalWrite(pin, HIGH);
  delay(1000);
  digitalWrite(pin, LOW);
}

int getProxData()
{
  //request for prox data
  Serial.write((char)2); 
  while(Serial.available() == 0){
    //wait
  }
  
  return Serial.read();
}
