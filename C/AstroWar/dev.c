#include "dev.h"

/*개발자옵션 On / Off 설정*/
void Option(void)
{
	int n,x=31,y=6;
	FrameDraw();
	gotoxy(32,4);
	printf("[-----설정-----]"); gotoxy(33,6);
	printf("소리 볼륨 설정 "); gotoxy(33,7);
	printf("플레이 설명"); gotoxy(33,8);
	printf("고급 설정"); gotoxy(33,9);
	printf("메인메뉴");
	
	n=ListArrow(x,y,6,9);
	
	switch(n)
	{
		case 0:
			VolumeCtrl();
			break;
			
		case 1:
			Help();
			break;
			
		case 2:
			Dev_OP();
			break;
			
		case 3:
			break; 
	}
	system("cls");
}



void Dev_OP(void)
{
	int n;
	FrameDraw();
	gotoxy(32,4);
	printf("● 고급설정 ●");
	gotoxy(36,5);
	printf("[ OFF ]");
	gotoxy(36,6);
	printf("[ O N ]");
	gotoxy(11,20);
	printf("◆ 고급설정은 게임내의 데이터를 화면에 출력하는 기능입니다.");
	gotoxy(13,21);
	printf("◇ 총알 좌표값");
	gotoxy(13,22);
	printf("◇ 운석 좌표값");
	gotoxy(13,23);
	printf("◇ 플레이어 좌표값");
	gotoxy(13,24);
	printf("◇ 장전, 스폰의 딜레이를 담당하는 변수값");
	gotoxy(13,25);
	printf("◇ 시간, 생명 수, 총알 수 출력");
	gotoxy(14,34); SetColor(RED);
	printf("※이 기능을 사용하면 게임속도가 저하될 수 있습니다※");
	SetColor(WHITE);
	
	n=ListArrow(34,5,5,6);
	
	if(n==0){
		Dev_Status = Dev_OFF;
	}
	else if(n==1){
		Dev_Status = Dev_ON;
	}
	else Dev_Status = Dev_OFF; 
}


/*플레이 도움말 출력*/
void Help(void)
{
	FrameDraw();
	gotoxy(2,3); 
	printf("▷ 조작키 ◁");
	gotoxy(3,4);
	printf("▶이동 : ← →");
	gotoxy(3,5);
	printf("▶발사 : Space");
	gotoxy(3,6);
	printf("▶장전 : Ctrl");
	gotoxy(2,8);
	printf("▷ 규칙 ◁");
	gotoxy(3,9); SetColor(RED); 
	printf("① 위에서 내려오는 운석을 맞춰서 점수를 올리면 됩니다."); 
	gotoxy(3,10); SetColor(YELLOW); 
	printf("② 운석과 부딛히거나 운석이 바닥에 닿으면 생명 1개가 줄어듭니다."); 
	gotoxy(3,11); SetColor(GREEN); 
	printf("③ 레벨(난이도)는 점수 50, 200, 400, 800을 달성하면 자동으로 올라갑니다.");
	gotoxy(3,12); SetColor(SKY_BLUE); 
	printf("④ 레벨(난이도)가 증가 할 때마다 생명 1개가 회복됩니다 (생명 최대 5개)"); 
	gotoxy(3,13); SetColor(PURPLE); 
	printf("⑤ 총알을 다 썼다면 장전을해서 다시 채울 수 있습니다."); 
	gotoxy(2,15); SetColor(WHITE);
	printf("▷ 개발자 ◁");
	gotoxy(3,16);
	printf("이근혁");
	
	gotoxy(33,25);
	printf("ESC : 그만보기");
	
	while(1){
		if(GetAsyncKeyState(VK_ESCAPE)& 0x8000) return;
	} 
}


/*개발자 옵션이 켜져있을 때 실행*/
void Dev_Option(void)
{
	gotoxy(0,1);
	printf("Dev: %d(ON)",Dev_Status);
	gotoxy(0,2); 
	
	SetColor(GREEN);
	printf("Bullet X: %02d",Bullet_XPOS);
	gotoxy(0,3);
	printf("Bullet Y: %02d",Bullet_YPOS);
	SetColor(WHITE);
	
	gotoxy(0,5); 
	printf("[Meteo Data]");
	gotoxy(0,6);
	printf("    X  Y");
	gotoxy(0,7); 
	
	SetColor(YELLOW);
	printf("1) %02d %02d",MeteoX[0],MeteoY[0]);
	gotoxy(0,8);
	printf("2) %02d %02d",MeteoX[1],MeteoY[1]);
	gotoxy(0,9);
	printf("3) %02d %02d",MeteoX[2],MeteoY[2]);
	gotoxy(0,10);
	printf("4) %02d %02d",MeteoX[3],MeteoY[3]);
	gotoxy(0,11);
	printf("5) %02d %02d",MeteoX[4],MeteoY[4]); 
	SetColor(WHITE);
	
	gotoxy(0,13);
	printf("cnt %03d",cnt);
	gotoxy(0,14);
	printf("spn %03d",spn);
	gotoxy(0,15);
	printf("reld %03d",reld);
	gotoxy(0,16);
	printf("Time:%d",ThisTime-T);
	gotoxy(0,18);
	printf("T %d",T);
	gotoxy(0,20);
	SetColor(RED);
	printf("Life:%d",Life); 
	SetColor(WHITE);
	gotoxy(0,21);
	printf("Ammo:%02d",Ammo);
	gotoxy(0,23);
	printf("F_Speed: %d",SPEED); //떨어지는 속도 
	gotoxy(0,24);
	printf("S_Speed: %d",SPAWN_SPEED); //스폰 속도 
	gotoxy(69,25);
	printf("X Pos: %02d",Player_x);
}
