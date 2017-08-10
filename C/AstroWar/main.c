#include <stdio.h>
#include <windows.h>
#include <time.h> 
#include <fmod.h>

#include "setting.h"
#include "data.h"


int Menu_YPos = 12;

/*타이틀 그리기*/
void Title(void)
{
	printf("\n\n");
	printf("      ■         ■■■   ■■■■■  ■■■■       ■■                       "); Sleep(80);
	printf("    ■  ■     ■     ■      ■      ■     ■    ■    ■                     "); Sleep(80);
	printf("    ■  ■     ■             ■      ■     ■   ■      ■                    "); Sleep(80);
	printf("   ■■■■     ■■■        ■      ■■■■    ■      ■                    "); Sleep(80);
	printf("  ■      ■          ■      ■      ■    ■    ■      ■                    "); Sleep(80);
	printf("  ■      ■   ■     ■      ■      ■     ■    ■    ■                     "); Sleep(80);
	printf("  ■      ■    ■■■        ■      ■      ■     ■■                       "); Sleep(80);
	printf("\n\n");
	printf("                                          ■          ■     ■     ■■■      "); Sleep(80);
	printf("                                          ■          ■   ■  ■   ■   ■     "); Sleep(80);
	printf("                                          ■    ■    ■   ■  ■   ■   ■     "); Sleep(80);
	printf("                                           ■  ■■  ■   ■■■■  ■■■      "); Sleep(80);
	printf("                                            ■■  ■■   ■      ■ ■   ■     "); Sleep(80);
	printf("                                             ■    ■    ■      ■ ■    ■    "); Sleep(80);
	
	gotoxy(51,30);
	printf("┌──[How To PLAY]──┐"); SetColor(RED);
	gotoxy(56,31);
	printf("Move :    ← →"); SetColor(YELLOW);
	gotoxy(55,32);
	printf("Shoot :    SPACE"); SetColor(GREEN);
	gotoxy(54,33); 
	printf("Reload :    Ctrl"); SetColor(SKY_BLUE);
	gotoxy(54,34); 
	printf("Select :    ENTER"); SetColor(WHITE);
	gotoxy(51,35);
	printf("└─────*─────┘");
	gotoxy(1,38);
	printf("Dev Option : %d",Dev_Status);
	gotoxy(67,38);
	printf("Version %g",VERSION);
	
	while(1)
	{
		if(GetAsyncKeyState(VK_SPACE) & 0x8000) break;
		
		cnt++;
		
		if(cnt>50){
			gotoxy(15,15);
			printf("               ");
			cnt=0;
		}
		cnt++;
		
		if(cnt>50){
			gotoxy(15,15);
			printf("Press SPACE Key");
			cnt=0;
		}
		Sleep(17);
	}
	gotoxy(15,15);
	printf("               ");
}


/*메인메뉴 출력, 제어함수*/
int MainMenu(void)
{
	int n; 
	Menu_YPos = 30;
	gotoxy(MainMenu_X+2, MainMenu_YMin);
	printf("게임시작"); //1
	gotoxy(MainMenu_X+2, MainMenu_YMin+1);
	printf("점수 랭킹"); //2
	gotoxy(MainMenu_X+2, MainMenu_YMin+2);
	printf("설정"); //3
	gotoxy(MainMenu_X+2, MainMenu_YMin+3);
	printf("종료"); //4
	
	n=ListArrow(MainMenu_X, MainMenu_YMin, MainMenu_YMin, MainMenu_YMax);
	system("cls");
	
	return n;
}


/*줄 생성*/
void LineDraw(int n)
{
	int loop;
	
	SetColor(SKY_BLUE);
	for (loop = 0; loop < 40; loop++) {
		gotoxy(n, loop);
		printf("┃");
	}
	
	for(loop=0; loop<5; loop++){
		gotoxy(6,34+loop);
		printf("│  │");
	}
	
	for(loop=0; loop<10; loop++){
		gotoxy(67,29+loop);
		printf("│  │");
	}
	gotoxy(6,33);
	printf("┌─┐"); gotoxy(6,39);
	printf("└─┘"); gotoxy(67,28);
	printf("┌─┐"); gotoxy(67,39);
	printf("└─┘"); gotoxy(65,6);
	printf("├──────"); gotoxy(67,7);
	printf("이동: ←→"); gotoxy(67,8);
	printf("발사: Space"); gotoxy(67,9);
	printf("장전: Ctrl"); 
}


/*게임내의 기본 구조 설정*/
void MainSet(void)
{
	Score=0;
	Life=5;
	SPEED=EASY;
	SPAWN_SPEED=SPN_EASY;
	LineDraw(LEFT_LINE); //게임내 좌측프레임 출력 
	LineDraw(RIGHT_LINE); // " 우측프레임 출력 
	PlayerReset(); //플레이어 시작위치, 생명, 총알 초기화 
	SetColor(WHITE); //글자색 흰색 설정 
	StopSound();//타이틀 배경음악 종료
}


/*게임 점수, 시간 출력*/
void Board(void)
{	 
	gotoxy(68,1);
	printf("            ");
	gotoxy(68,1);
	printf("점수: %d",Score);
	gotoxy(68,3);
	printf(" %d 초",(ThisTime-T)/1000); //현재시각 - (게임시작시간 - 프로그램 실행시간) > (ms) / 1000 = N(sec) 
}


/*일시정지 메뉴 출력, 선택*/
int PauseGame(void)
{
	int n;
	const int x =34;
	Menu_YPos = 12;
	STime = clock(); //일시정지 시점의 시간저장 
	gotoxy(35,10);
	printf("[일시정지]");
	
	gotoxy(34,12);
	printf(">");
	
	gotoxy(36,12);
	printf("계속하기");
	gotoxy(36,13);
	printf("메인메뉴");
	gotoxy(36,14);
	printf("종료");
	
	
	n=ListArrow(x, Menu_YPos, Pause_YMin, Pause_YMax);
	
	ETime=clock();
	T+=ETime-STime;
	
	gotoxy(35,10);
	printf("          ");
	gotoxy(34,12);
	printf("          ");
	gotoxy(34,13);
	printf("          ");
	gotoxy(34,14);
	printf("          ");
	return n;
}


/*일시정지 메뉴 지우기*/
void ClearPauseMenu(void)
{
	int x=34,y=10,i; 
	for(i=0; i<5; i++){
		gotoxy(x,y+i);
		printf("           ");
	}	
}


/*생명이 0일시 게임종료*/
void GameOver(void)
{
	gotoxy(33,9);
	printf("┌─────┐");
	gotoxy(33,10);
	printf("│ 게임오버 │");
	gotoxy(33,11);
	printf("└─────┘");
	Sleep(2000);
	AddRank((ThisTime-T)/1000,Score);
} 


/*메인함수*/ 
int main(void) 
{
	int i,n=0, reStart;
	FileLoad();
	system("mode con: lines=40"); //세로길이 40
	system("title AstroWar"); //실행 타이틀 제목 AstroWar로 설정 
	CursorHide(); // Cmd창 커서 지우기
	Init(); //사운드 초기설정 

	//메인메뉴 이동(재시작)을 위한 반복문 
	do
	{
		Sound_Play(S_MAIN); //타이틀 사운드 실행 
		VolumeSetSound();
		
		while(1)
		{
			VolumeSetSound();
			Title();
    		n=MainMenu()+1;
    		
    		
    		if(n==1) break;
			else if(n==2) RankDraw();
			else if(n==3) Option();
			else return 0;
		}
    	
    	reStart=0;
		MainSet();
		PlayerDraw();
		MeteoReset();
		
		ThisTime=clock(); //현재시각 저장 
		T=ThisTime; //현재시각 

		srand((unsigned int)time(NULL)); //같은 난수 생성방지를 위한 Seed값 적용 
		StartTime = clock(); //게임시작시간 저장 
		
		
		while(n == 1) //재시작 or 시작시 반복문을 돌며 게임실행 
		{
			if(GetAsyncKeyState(VK_ESCAPE)<0) { //ESC키를 눌렀을 때 
				n=PauseGame()+1; //일시정지 함수 실행 
				
				if(n==0){ //반환값이 0일시 반복문 탈출 (게임종료)
					FileSave();
					return 0; 
				} 
				else if(n==2){ //반환값이 2일시 메인메뉴로 
					reStart=1; 
					break;
				}
			}
			
			
			if(Life == 0){
				GameOver();
				reStart=1;
				break;
			}
			
			if(Dev_Status == 1) Dev_Option();
			if(Reloading == 1){
				reld++;
				ReloadAmmo();
			}
			
			SoundUpdate();
			PlayerCtrl(); //플레이어 제어 
			BulletCtrl(); //총알 제어 
			MeteoCtrl(); //운석 제어 
			LevelCtrl(); //레벨 제어 
			Board(); //점수판 출력
			
			Sleep(17); //약 0.017초  
			ThisTime=clock(); //현재 시각 저장 
			
			cnt++;
			spn++; 
		}
		system("cls");
	}while(reStart == 1); 
	FileSave();
	return 0;
}
