#include <Servo.h>

#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "smart-security-minor-default-rtdb.firebaseio.com" 
#define FIREBASE_AUTH "bWdckVrV89mjV1Rvzz7DOjRe7jWBSYBkQnmKFMJn" 
#define WIFI_SSID "Bagichi" 
#define WIFI_PASSWORD "Bagichi123"  
#define servoPin 2    
String values,sensor_data;
String switchstate="";
int angle = 0;
Servo myservo; 
void setup() {
   
   Serial.begin(9600);
   delay(1000);
   WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                       
   Serial.print("connecting"); 
   while (WiFi.status() != WL_CONNECTED) { 
     Serial.print("."); 
     delay(500); 
   } 
   Serial.println(); 
   Serial.print("connected: "); 
   Serial.println(WiFi.localIP()); 
   Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
   Serial.println("I am here");
   pinMode(2,OUTPUT);
   myservo.attach(servoPin);
   myservo.write(0);
}

void loop() { 
   bool Sr =false;
 
  while(Serial.available()){
    
        //get sensor data from serial put in sensor_data
        sensor_data=Serial.readString(); 
        Sr=true;    
        
    }
  
delay(1000);

  if(Sr==true){  
    
  values=sensor_data;
  
  String lpg="";
  String humidity="";
  String temperature="";
  String state="";
  int i=0;
  while(values[i]!='*')
  {
    lpg+=values[i];        
    i++;
  }
  if(lpg=="YES")
  {
    myservo.write(0);
    delay(1000);
    moveservo();
  }
  i++;
  while(values[i]!='*')
  {
    humidity+=values[i];                                                                     
    i++;
  }
  i++;
  while(values[i]!='*')
  {
    temperature+=values[i];
    i++;
  }
  i++;
  while(values[i]!='\r')
  {
    state+=values[i];
    i++;
  }
  Firebase.setString("MartfRp7ApbBpnVr4ggd2GUVL2K3/gasLevels",lpg);
  Firebase.setString("MartfRp7ApbBpnVr4ggd2GUVL2K3/motionStatus",state);
  Firebase.setString("MartfRp7ApbBpnVr4ggd2GUVL2K3/humidity",humidity);
  Firebase.setString("MartfRp7ApbBpnVr4ggd2GUVL2K3/temperature",temperature);
  delay(1000);
  
  if (Firebase.failed()) {  
    Serial.println("Failed");
      return;
  }
  
}   
  
  switchstate=Firebase.getString("MartfRp7ApbBpnVr4ggd2GUVL2K3/switch");
  while(switchstate=="ON")
  {
    myservo.write(0);
    delay(1000);
    moveservo();
  }
}
void moveservo() 
{
  for (angle = 0; angle <= 360; angle += 1) {
    myservo.write(angle);
    delay(1);
  }
  myservo.write(0);
}
