#include <time.h>
#include "astro.h"

/*------ 색 코드값 열거형 ------*/
enum 
{
	BLACK=0, 
	BLUE, 
	CYAN=3, 
	WHITE=7, 
	GRAY, 
	GREEN=10,
	SKY_BLUE, 
	RED, 
	PURPLE=13,
	YELLOW
};
/*------사운드 코드 열거형------*/
enum 
{
	S_MAIN=0,
	S_SHOOT,
	S_RELOAD,
	S_DESTROY,
	S_LEVEL
};
/*------------------------------*/ 

int Playing;

int Life;
int Ammo;
int Reloading; 

int cnt; //운석 떨어지는 기능을 위한 변수, 시간차 변수 
int spn; //운석 스폰시간을 위한 변수 
int reld; //재장전 시간을 위한 변수
 
int SPEED; //운석 떨어지는 속도 
int SPAWN_SPEED; //스폰 속도 

int MeteoX[MeteoMAX]; //운석 X좌표 
int MeteoY[MeteoMAX]; //운석 Y좌표
int MeteoCount; //생성된 운석 갯수 


int Player_x; //플레이어 X 좌표 
int Player_y; //플레이어 Y 좌표

int Bullet_YPOS; //총알 Y좌표 
int Bullet_XPOS; //총알 X좌표  

int Dev_Status;

int Bullet_Check; //발사중인 총알 갯수체크 0=없음  1=있음  

int Score; //게임 점수 저장변수 


clock_t T;
clock_t STime;
clock_t ETime;
clock_t StartTime; //게임시작 시각 
clock_t ThisTime; //현재시각 저장변수 


