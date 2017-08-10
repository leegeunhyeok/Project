#include "meteo.h"

void MeteoSpawn(void)
{
	int i;
	spn=0;
	for(i=0; i<MeteoMAX; i++){
		if(MeteoX[i] == 0){
			MeteoX[i] = (rand() % 45)+15;
			MeteoY[i] = 0;
			MeteoCount++;
			break; 
		}
	}
}


void MeteoReset(void)
{
	int i;
	MeteoCount=0;
	for(i=0; i<MeteoMAX; i++){
		MeteoX[i]=0;
		MeteoY[i]=0;
	}
}

/*총알이랑 부딛혔을 시 실행됨
운석을 지우고 운석갯수 -1*/
void MeteoDestroy(int n) //운석 파괴 
{
	Sound_Play(S_DESTROY);
	VolumeSetSound();
	MeteoCount--;
	
	Score+=10; //점수 +10 
	
	gotoxy(MeteoX[n], MeteoY[n]);
	printf("    ");
	gotoxy(MeteoX[n], MeteoY[n]+1);
	printf("    ");
		
	Bullet_Check = 0; //총알 제거 
	
	MeteoX[n]=0; //운석  좌표 초기화 
	MeteoY[n]=0;
}


/*운석 지우기*/
void MeteoClear(void)
{
	int i;
	for(i=0; i<MeteoMAX; i++){
		gotoxy(MeteoX[i], MeteoY[i]);
		printf("    "); 
		gotoxy(MeteoX[i], MeteoY[i]+1);
		printf("    ");
	}
}


/*운석이 떨어지게 하는 함수*/
void MeteoFall(void)
{
	int i;
	cnt=0; 
	MeteoClear();
	for(i=0; i<MeteoMAX; i++){
		if(MeteoX[i] != 0)
			MeteoY[i]++;
			
		if(MeteoY[i] == 38 || (MeteoY[i] == Player_y-2 && Player_x-1 >= MeteoX[i]-1 && Player_x+1 <= MeteoX[i]+4)){
			VolumeSetSound();
			Sound_Play(S_DESTROY);
			VolumeSetSound();
			MeteoX[i] = 0;
			MeteoY[i] = 0; //운석이 바닥에 닿거나, 플레이어랑 부딛쳤을 때 해당 운석을 지우고 점수 -5
			Life--;
			MeteoCount--;
		}
	}
	MeteoDraw();	
}



/*운석 그리기*/
void MeteoDraw(void)
{
	SetColor(YELLOW);
	int i;
	for(i=0; i<MeteoMAX; i++){
		if(MeteoX[i] != 0){
			gotoxy(MeteoX[i], MeteoY[i]);
			printf("▨▧");
			gotoxy(MeteoX[i], MeteoY[i]+1);
			printf("▧▨");
		}
	}
	SetColor(WHITE); 
}


/*운석 통합 제어함수*/
void MeteoCtrl(void)
{
	if(cnt>SPEED) MeteoFall();

	if(MeteoCount == MeteoMAX){
		spn = 0;
		return;	
	}
	
	if(spn > SPAWN_SPEED && (MeteoCount >= 0 && MeteoCount <=MeteoMAX)){
		MeteoSpawn();
	}
}


/*현재 게임레벨 출력*/
void Level_Draw(int lv)
{
	gotoxy(67,5);
	if(lv==EASY) printf("레벨: EASY  ");
	else if(lv==NORMAL) printf("레벨: NORMAL");
	else if(lv==HARD) printf("레벨: HARD  ");
	else if(lv==CRAZY) printf("레벨: CRAZY  ");
	else if(lv==HELL) printf("레벨: HELL  ");
	else printf("레벨: ERROR");
} 


/*일정 점수가 되면 레벨업(속도증가)*/
void LevelCtrl(void)
{
	Level_Draw(SPEED);
	if(Score==50){
		if(Life < 5) Life++;
		SPEED=NORMAL;
		SPAWN_SPEED=SPN_NORMAL;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=30;
	}
	else if(Score==200){
		if(Life < 5) Life++;
		SPEED=HARD;
		SPAWN_SPEED=SPN_HARD;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=50;
	}
	else if(Score==400){
		if(Life < 5) Life++;
		SPEED=CRAZY;
		SPAWN_SPEED=SPN_CRAZY;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=70;
	}
	else if(Score==800){
		if(Life < 5) Life++;
		SPEED=HELL;
		SPAWN_SPEED=SPN_HELL;
		Sound_Play(S_LEVEL);
		VolumeSetSound();
		Score+=100;
	}  
}
