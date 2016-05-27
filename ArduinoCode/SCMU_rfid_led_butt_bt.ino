#include <AddicoreRFID.h>
#include <SPI.h>
#include <SoftwareSerial.h>

#define uchar unsigned char
#define uint  unsigned int

//4 bytes tag serial number, the first 5 bytes for the checksum byte
uchar serNumA[5];

uchar fifobytes;
uchar fifoValue;

AddicoreRFID myRFID; // create AddicoreRFID object to control the RFID module

/////////////////////////////////////////////////////////////////////
//set the pins
/////////////////////////////////////////////////////////////////////
const int chipSelectPin = 10;
const int led = 9 ;
const int button = 8;


int buttonState = 0; 
char command;
String string;
boolean ledon = false;

//Maximum length of the array
#define MAX_LEN 16

void setup() {
  Serial.begin(9600);                        // RFID reader SOUT pin connected to Serial RX pin at 9600bps

  // start the SPI library:
  SPI.begin();

  pinMode(led, OUTPUT);
  pinMode(button, INPUT);
  pinMode(chipSelectPin, OUTPUT);             // Set digital pin 10 as OUTPUT to connect it to the RFID /ENABLE pin
  digitalWrite(chipSelectPin, LOW);         // Activate the RFID reader


  myRFID.AddicoreRFID_Init();
}

void loop()
{
  uchar i, tmp, checksum1;
  uchar status;
  uchar str[MAX_LEN];
  uchar RC_size;
  uchar blockAddr;  //Selection operation block address 0 to 63
  String mynum = "";
  //Find tags, return tag type
  buttonState = digitalRead(button);
  if (buttonState == HIGH) {
    Serial.println("NXT+");
    delay(1000);
  }
  str[1] = 0x4400;
  status = myRFID.AddicoreRFID_Request(PICC_REQIDL, str);
  if (status == MI_OK) { //detetou resposta rfid
    status = myRFID.AddicoreRFID_Anticoll(str); //ve se ha colisoes
    if (status == MI_OK)//não tem problema de colisao
    {
      checksum1 = str[0] ^ str[1] ^ str[2] ^ str[3];
      Serial.print("~");
      Serial.print(str[0]);
      Serial.print(",");
      Serial.print(str[1], BIN);
      Serial.print(",");
      Serial.print(str[2], BIN);
      Serial.print(",");
      Serial.print(str[3], BIN);
      Serial.print(",");
      Serial.print(str[4], BIN);
      Serial.print(";");
      Serial.print(checksum1, BIN);
      Serial.print("+");
      Serial.println();
      delay(1000);
    }
  }

  myRFID.AddicoreRFID_Halt();      //Command tag into hibernation

  if (Serial.available() > 0)
  {
    string = "";
  }

  while (Serial.available() > 0)
  {
    command = ((byte)Serial.read());
    if (command == ':')
      break;
    else
      string += command;
    Serial.println(string);
  }

  //control the led
  //with the data from the BT
  if (string == "TO") //turn on
  {
    ledOn();
  }

  if (string == "TF") //turn off
  {
    ledOff();
  }
  if(string == "TB") // blink
  {
      ledOn();
      delay(500);
      ledOff();
      delay(1000);
    
  }


}


void ledOn()
{
  if (!ledon) {
    analogWrite(led, 255);
    ledon = true;
    delay(1);
  }
}

void ledOff()
{
  if (ledon) {
    analogWrite(led, 0);
    ledon = false;
    delay(1);
  }
}




