#include "color.h"

HANDLE consoleHandle = GetStdHandle(STD_OUTPUT_HANDLE);

void hideCursor(){
	CONSOLE_CURSOR_INFO ConsoleCursor;
    ConsoleCursor.bVisible = false; // false ¼û±è 
    ConsoleCursor.dwSize = 1;
    SetConsoleCursorInfo(consoleHandle , &ConsoleCursor); // ¼³Á¤
} 

void setColor(const int forground, const int background){
	int code = forground + background * 16; 
	SetConsoleTextAttribute(consoleHandle, code);
}
  
