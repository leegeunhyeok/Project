#include "main.h"

#ifndef __COLOR_LIST_
#define __COLOR_LIST_

enum {
	black,
	blue,
	green, 
	cyan, 
	red, 
	purple, 
	brown, 
	lightgray, 
	darkgray, 
	lightblue, 
	lightgreen,
	lightcyan,
	lightred,
	lightpurple,
	yellow,
	white
};

#endif

// 커서 숨기기  
void hideCursor(); 

// 콘솔 색 설정  
void setColor(const int, const int);

// 콘솔 좌표이동  
void gotoxy(const int, const int);
