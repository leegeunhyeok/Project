#include <stdio.h>
#include <windows.h>
#include "data.h"


/*입력버퍼 지우는 함수*/
void Flush_buffer(void)
{
	FlushConsoleInputBuffer(GetStdHandle(STD_INPUT_HANDLE));
}

/*커서 위치변경 함수*/
void gotoxy(int x, int y)
{
	COORD CursorPosition = {x,y};
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), CursorPosition);
}


/*글자색 설정 함수*/
void SetColor(int num)
{
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), num);
}


/*커서 지우는 함수*/
void CursorHide(void)
{
    CONSOLE_CURSOR_INFO cursorInfo = { 0, };
    cursorInfo.dwSize = 1;
    cursorInfo.bVisible = FALSE;
    SetConsoleCursorInfo(GetStdHandle(STD_OUTPUT_HANDLE), &cursorInfo);
    return 0;
}


/*화살표키로 선택하는 목록을 위한 함수*/
int ListArrow(int x, int y, int MIN, int MAX)
{
	SetColor(CYAN);
	gotoxy(x,y);
	printf(">");
	
	while(1)
	{
		if(GetAsyncKeyState(VK_UP)<0 && MIN < y){// 화살표 위쪽 키를 눌렀을 때 
			gotoxy(x,y--);
			printf(" ");
			gotoxy(x,y);
			printf(">");
		}
	
		
		if(GetAsyncKeyState(VK_DOWN)<0 && MAX > y){// 화살표 아래쪽 키를 눌렀을 때 
			gotoxy(x,y++);
			printf(" ");
			gotoxy(x,y);
			printf(">");
		}
		
		if(GetAsyncKeyState(VK_RETURN) & 0x8000){
			SetColor(WHITE);
			return y-MIN;
		}
		Sleep(80);
	}
}

/*테두리 그리는 함수*/
void FrameDraw(void)
{
	int i;
	const int right=0,left=78; 
	system("cls");
	gotoxy(0,0);
	printf("◎──────────────────────────────────────◎");
	
	for(i=1; i<39; i++){
		gotoxy(right,i);
		printf("│");
	}
	
	for(i=1; i<39; i++){
		gotoxy(left,i);
		printf("│");
	}
	
	gotoxy(0,38);
	printf("◎──────────────────────────────────────◎");
}

