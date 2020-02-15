#include<SoftwareSerial.h>
#define USE_ARDUINO_INTERRUPTS true    
#include <PulseSensorPlayground.h>       
int Threshold = 550;           // Determine which Signal to "count as a beat" and which to ignore.

//rx tx
SoftwareSerial SerialUno(5, 6);
PulseSensorPlayground pulseSensor;  

int PulseWire = 0;

const int analog = A0;
int val = 0;

void setup() {
  Serial.begin(9600);
  SerialUno.begin(9600);
  
  pulseSensor.analogInput(PulseWire);   
  pulseSensor.setThreshold(Threshold);   

  if (pulseSensor.begin()) {
    Serial.println("We created a pulseSensor Object !");  
  }
}
 
void loop() {
  
int myBPM = pulseSensor.getBeatsPerMinute();  

if (pulseSensor.sawStartOfBeat()) {            // Constantly test to see if "a beat happened". 
     Serial.println("♥  A HeartBeat Happened ! "); 
     Serial.print("BPM: ");                       
     SerialUno.write(myBPM);
     Serial.println(myBPM);
}

delay(20);               

}
