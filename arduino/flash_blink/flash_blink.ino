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
  flash(1);
  delay(1000);
  flash(0);
  delay(1000);
}

void init_shield() {
  while(!Serial.available()){}
  Serial.read();//init byte
  digitalWrite(pin, HIGH);
  delay(1000);
  digitalWrite(pin, LOW);
}

void flash(int data)
{
  Serial.write(130);
  Serial.write(data);
}
