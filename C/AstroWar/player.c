#include <stdio.h>
#include <windows.h>
#include <time.h>

#include "setting.h"
#include "data.h"


/*총알 발사 초기 설정*/
void BulletSet(void)
{
	if(Bullet_Check == 0){
		Bullet_XPOS=Player_x;
		Bullet_Check = 1;
		Ammo--;	
	}
	Bullet_YPOS=Player_y-1;
	Sound_Play(S_SHOOT);
	VolumeSetSound();
}


/*장전키를 눌렀을 때*/
void Reload(void)
{ 
	Sound_Play(S_RELOAD);
	VolumeSetSound();
	Reloading=1; //장전 중... 
}


/*장전시간 변수 reld가 80이 넘으면 장전완료*/
void ReloadAmmo(void)
{
	if(reld > 60){
		Reloading=0; //장전 끝 
		reld=0;
		Ammo=10;
	}	
}


/*총알 상태 출력*/
void AmmoDraw(void)
{
	SetColor(GRAY);
	int i;
	for(i=0; i<Ammo; i++){ //총알그리기 
		gotoxy(69,38-i);
		printf("●");
	} 
	for(i=10-Ammo; i>0; i--){ //총알지우기 
		gotoxy(69,28+i);
		printf("  ");
	}
	SetColor(WHITE);
} 


/*발사중인 총알 그리기*/
void BulletDraw(void)
{
	gotoxy(Bullet_XPOS, Bullet_YPOS);
	SetColor(GREEN);
	printf("|");
	SetColor(WHITE);
}


/*총알 지우기*/
void BulletClear(void)
{
	gotoxy(Bullet_XPOS, Bullet_YPOS);
	printf(" ");
}


/*총알 충돌체크*/
void BulletCrashCheck(void)
{
	int i;
	for(i=0; i<MeteoMAX; i++){
		if((Bullet_XPOS >= MeteoX[i] && Bullet_XPOS <= MeteoX[i]+3) && (Bullet_YPOS <= MeteoY[i]+1 || Bullet_YPOS <= MeteoY[i]+2)){
			MeteoDestroy(i);
			break;
		}
	}
}


/*총알 통합 제어함수*/
void BulletCtrl(void)
{
	if (Bullet_YPOS < 1) {
		BulletClear();
		Bullet_Check=0; 
		return;
	}
	
	BulletClear();	
	BulletCrashCheck();
	
	Bullet_YPOS--;
	if(Bullet_Check == 0){
		Bullet_YPOS=0;
		Bullet_XPOS=0;
		return;
	}
	BulletDraw();
}


/*생명 상태 출력*/
void LifeDraw(void)
{
	SetColor(RED);
	int i;
	for(i=0; i<Life; i++){ //생명그리기 
		gotoxy(8,38-i);
		printf("♥");
	} 
	for(i=5-Life; i>0; i--){ //생명지우기 
		gotoxy(8,33+i);
		printf("  ");
	}
	SetColor(WHITE);
} 


/*플레이어 초기설정*/
void PlayerReset(void)
{
	Life = 5; 
	Ammo = 10;
	Player_x= 39;
	Player_y= 37;
	PlayerDraw();
}


/*플레이어 생성*/
void PlayerDraw(void)
{
	gotoxy(Player_x, Player_y);
	printf("^");
	gotoxy(Player_x-1, Player_y+1);
	printf("<#>");
}
 
 
/*플레이어 지우기*/
void PlayerClear(void)
{
	gotoxy(Player_x, Player_y);
	printf(" ");
	gotoxy(Player_x-1, Player_y+1);
	printf("   ");
}


/*키보드 조작*/
void PlayerCtrl(void)
{
	LifeDraw();
	AmmoDraw();
	if(GetAsyncKeyState(VK_LEFT)<0 && Player_x > X_MIN){
		PlayerClear();
		Player_x--;
		PlayerDraw();
	}
	
	if(GetAsyncKeyState(VK_RIGHT)<0 && Player_x < X_MAX){
		PlayerClear();
		Player_x++;
		PlayerDraw();
	}
		
	if(GetAsyncKeyState(VK_SPACE)<0 && Bullet_Check == 0 && Ammo>0 && Reloading == 0){
		BulletSet();
		BulletCtrl();
	}
	
	if(GetAsyncKeyState(VK_CONTROL)<0 && Bullet_Check == 0 && Ammo !=10){
		Reload();
	}
}

