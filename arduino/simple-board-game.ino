#include <ESP8266WiFi.h>
#include <WiFiUdp.h>

//Fill out wifi details here:
const char* ssid = "";
const char* password = "";

#include <SPI.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels

WiFiUDP Udp;
unsigned int localUdpPort = 42101;  // local port to listen on

char packetBuffer[255]; //buffer to hold incoming packet
char replyId[255]; //unique id which needs to be send back

//distance between cells/fields
int xGap = 24;
int yGap = 10;

//distance from the border of the screen
int distanceXFromBorder = 10;
int distanceYFromBorder = 5;

//MCU ONLY , default arduino value is 4
#define OLED_RESET LED_BUILTIN 
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

//Init board 
char board[5][4] = {
  {'1','2','1','2'} ,
  {'0','0','0','0'} ,
  {'0','0','0','0'} ,
  {'0','0','0','0'} ,
  {'2','1','2','1'}
};

void setup() {
  Serial.begin(115200);

  // SSD1306_SWITCHCAPVCC = generate display voltage from 3.3V internally
  if(!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) { // Address 0x3C for 128x32
    Serial.println(F("SSD1306 allocation failed"));
    for(;;); // Don't proceed, loop forever
  }

  display.display(); //adafruit LOGO
  delay(2000); 
  display.clearDisplay();  
  
  //Connecting to Wifi and optaining ip address
  Serial.printf("Connecting to %s ", ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }
  Serial.println(" connected");
  Udp.begin(localUdpPort);
  IPAddress myIP = WiFi.localIP();
  Serial.print("IP Address:");
  Serial.println(myIP);

  //Displaying ip
  display.setTextSize(2);             // Normal 1:1 pixel scale
  display.setTextColor(WHITE);        // Draw white text
  display.setCursor(0,0);             // Start at top-left corner
  display.println(myIP);
  display.display();
}

void loop() {
  int packetSize = Udp.parsePacket();
  if (packetSize) {
   
    // read the packet into packetBufffer
    int len = Udp.read(packetBuffer, 255);
    if (len > 0) {
      packetBuffer[len] = 0;
    }  

    //Fills out board matrix with received board
    int l = 0;
    for (int i =0 ; i < 5;i++){
      for (int j = 0 ; j < 4; j++){
        board[i][j] = packetBuffer[l];
      l++;
    }
    }

    //ignoring ',' char
    l++;
    int i = 0;

    //storing reply id and sending it back.
    while(packetBuffer[l] != ','){
      replyId[i]=packetBuffer[l];
      i++;
      l++;
      }
    Udp.beginPacket(Udp.remoteIP(), Udp.remotePort());
    Udp.write(replyId);
    Udp.endPacket();    
    drawBoard();
  }
  delay(10);
}

void drawBoard(void) {
  display.clearDisplay();

  for (int i = 0; i < 5;i++){ //y
    for (int j = 0; j < 4; j++){  //x

      //circleDraw for player
      if (board[i][j] == '1'){
        display.drawCircle(distanceXFromBorder + xGap/2 + (j * xGap), 
        distanceYFromBorder + yGap/2 + (i * yGap),4,WHITE);  
      }

      //X draw for player
      if (board[i][j] == '2'){
        display.drawLine(
        distanceXFromBorder + (j * xGap) ,
        distanceYFromBorder + (i * yGap),distanceXFromBorder + ((j+1) * xGap) ,
        distanceYFromBorder + ((i+1) * yGap),
        WHITE);
        
        display.drawLine(distanceXFromBorder + ((j+1) * xGap) ,
        distanceYFromBorder + (i * yGap),distanceXFromBorder + (j * xGap) ,
        distanceYFromBorder + ((i+1) * yGap),
        WHITE);
      }
    }
  }

  //Drawing borders
  for (int i =0 ; i < 5; i++){
   display.drawLine(
   distanceXFromBorder + (i*xGap),
   distanceYFromBorder,
   distanceXFromBorder +(i*xGap),
   distanceYFromBorder + (5 * yGap),
   WHITE);
  }
  
  for (int i =0 ; i < 6; i++){
   display.drawLine(
    distanceXFromBorder,
    distanceYFromBorder + (i * yGap),
    distanceXFromBorder + (4 * xGap),
    distanceYFromBorder + (i * yGap),
    WHITE);
  }

  display.display();
}
