#include<SoftwareSerial.h>
SoftwareSerial SerialEsp(D6, D5);

#include <ESP8266WiFi.h>
#include <Firebase.h>
#include <FirebaseArduino.h>
#include <FirebaseCloudMessaging.h>
#include <FirebaseError.h>
#include <FirebaseHttpClient.h>
#include <FirebaseObject.h>
#include <ESP8266WebServer.h>

#define FIREBASE_HOST "***"
#define FIREBASE_AUTH "***"

const char* ssid = "***";
const char* password = "***";

ESP8266WebServer server(80);

int sum = 0;
int count = 0;

void setup() {

  Serial.begin(9600);
  SerialEsp.begin(9600);

  Serial.println();
  Serial.print("Connecting to ");
  Serial.print(ssid);

  WiFi.begin(ssid, password);

  while(WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  Serial.println("Wifi connected");
  Serial.println(WiFi.localIP());
  
  delay(2000);
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH); 
}

void loop() {

    if(SerialEsp.available())
    {
        int value = SerialEsp.read();
        Serial.println(value);
       
        if(value>40)
        {
            Firebase.setString("values/pulse", String(value));
        }
    }
}

void handleRoot() {
  String message = "";
  message += analogRead(0);
  server.send(200, "text/plain", message);
}
