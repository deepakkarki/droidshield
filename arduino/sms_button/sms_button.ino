int pin = 13;
int val;
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(pin, OUTPUT);
  init_shield(); //blocking shield
}

void loop() {
  int i = 0;
  //flashy lights
  for(i = 0; i < 5; i++){
    digitalWrite(pin, HIGH);
    delay(250);digitalWrite(pin, LOW);
    delay(250);
  }
  send_sms("9019548381", "alert from arduino");
  digitalWrite(pin, HIGH);
  //most important thing!! 
  while(1){;}
}

void init_shield() {
  while(!Serial.available()){}
  Serial.read();//init byte
  digitalWrite(pin, HIGH);
  delay(1000);
  digitalWrite(pin, LOW);
}

void send_sms(String send_to, String data)
{
  //request for accelerometer data
  Serial.write(131); 
  Serial.print(send_to);
  Serial.write(data.length());
  Serial.print(data);

}
