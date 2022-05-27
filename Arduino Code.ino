#include <ArduinoJson.h>

/*#include <dht.h>
#include "dht.h"*/
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "smart-security-minor-default-rtdb.firebaseio.com" 
#define FIREBASE_AUTH "bWdckVrV89mjV1Rvzz7DOjRe7jWBSYBkQnmKFMJn" 
#define WIFI_SSID "Bagichi" 
#define WIFI_PASSWORD "Bagichi123"    
/*#define dht_apin A1
#define DHTTYPE DHT11*/
const int pirPin = 2;
const int trigPin = 7; 
const int echoPin = 6;
const int MQAO = A0;
const int MQDO = 4;
/*dht DHT;*/ 
String values;
void setup() {
   
   Serial.begin(9600);
   
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
   pinMode(pirPin, INPUT);
   pinMode(trigPin, OUTPUT);
   pinMode(echoPin, INPUT);
   pinMode(MQDO, INPUT);
   pinMode(MQAO, INPUT);
}

void loop() {  
   values="Ultrasonic Range: ";  
   values+=UltrasonicSensor();
   values+=" , Motion Status: ";
   values+=PIRSensor();
   values+=" , Gas Levels: ";
   values+=MQ3Sensor();
   /*DHT11Sensor();*/
   Serial.println(values);
   Firebase.setString("values", values);
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());  
      return;
  }
  delay(1000);
}

String PIRSensor() {
  String state;
  if(digitalRead(pirPin) == HIGH)
  {
    Serial.println("Motion detected.");
    state="Motion detected";
    delay(2000);
  }
  else
  {
    Serial.println("Motion Ended.");
    state="Motion Ended";
    delay(2000);
  }
  return state;
}

String UltrasonicSensor()
{
  long duration, inches;
  digitalWrite(trigPin,LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin,HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin,LOW);
  duration = pulseIn(echoPin, HIGH);
  inches = microsecondsToInches(duration);
  Serial.print(inches);
  Serial.print(" inches");
  Serial.println();
  delay(2000);
  return String(inches);
}
long microsecondsToInches(long microseconds) {
   return microseconds / 74 / 2;
}
String MQ3Sensor()
{
  int val=analogRead(MQAO);
  int limit=digitalRead(MQDO);
  Serial.print("LPG value: ");
  Serial.println(val);
  Serial.print("\n"); 
  delay(2000);
  return String(val);
}
/*void DHT11Sensor()
{
  DHT.read11(A1 );
    
    Serial.print("Current humidity = ");
    Serial.print(DHT.humidity);
    Serial.print("%  ");
    Serial.print("temperature = ");
    Serial.print(DHT.temperature); 
    Serial.println("C  ");
    
    delay(5000);
}*/
