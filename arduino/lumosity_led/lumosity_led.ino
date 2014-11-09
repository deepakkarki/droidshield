int pin = 13;

void setup() {
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); 
}

void loop() {
  
  //if current lumosity data is > 5
  if(getLumData() > 3){
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

int getLumData()
{
  //request for lumosity data
  Serial.write((char)3); 
  while(Serial.available() == 0){
    //wait
  }
  
  return Serial.read();
}
