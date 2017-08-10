#include <stdio.h>
#include <fmod.h>
#include <Windows.h> 

#include "data.h"

FMOD_SYSTEM *g_System; //FMOD system 변수선언
FMOD_SOUND *g_Sound[5]; 
FMOD_CHANNEL *channel = NULL;

FMOD_BOOL IsPlaying;

float volume=0.5; //볼륨 0~1 

void Init(void)
{
    FMOD_System_Create(&g_System);
    FMOD_System_Init(g_System,10,FMOD_INIT_NORMAL,NULL); //최대 10개의 소리재생가능 

	/*사운드 생성*/ 
    FMOD_System_CreateSound(g_System,".\\sound\\main.mp3",FMOD_LOOP_NORMAL,0,&g_Sound[S_MAIN]); 
    FMOD_System_CreateSound(g_System,".\\sound\\shoot.wav",FMOD_DEFAULT,0,&g_Sound[S_SHOOT]);
    FMOD_System_CreateSound(g_System,".\\sound\\reload.wav",FMOD_DEFAULT,0,&g_Sound[S_RELOAD]);
    FMOD_System_CreateSound(g_System,".\\sound\\destroy.wav",FMOD_DEFAULT,0,&g_Sound[S_DESTROY]);
    FMOD_System_CreateSound(g_System,".\\sound\\level.wav",FMOD_DEFAULT,0,&g_Sound[S_LEVEL]);
}




void StopSound(void)
{
	FMOD_Channel_Stop(channel); //채널의 소리 모두정지 
}



void VolumeSetSound(void)
{
	FMOD_Channel_SetVolume(channel,volume); //설정 볼륨으로 소리크기 지정 
}



void SoundUpdate(void)
{
	if(IsPlaying == 1)
    	FMOD_System_Update(g_System);
}



void VolumeCtrl(void)
{
	FrameDraw();
	char n;
	gotoxy(27,8); SetColor(GREEN);
	printf("↑: 볼륨 증가 ◈ MAX 10 ◈");
	gotoxy(27,10); SetColor(RED);
	printf("↓: 볼륨 감소 ◈ MIN 0  ◈");
	gotoxy(30,12); SetColor(WHITE);
	printf("ESC : 설정 나가기");
	gotoxy(32,16);
	printf("소리 볼륨: ");
	while(1)
	{
		gotoxy(44,16); SetColor(YELLOW);
		printf("%02d",(int)(volume*10));
	
  	
        if(GetAsyncKeyState(VK_UP)<0 && volume < 1.0f){// 화살표 위쪽 키를 눌렀을 때 
           	volume += 0.1f;
           	FMOD_Channel_SetVolume(channel,volume);
		}
	
		
		if(GetAsyncKeyState(VK_DOWN)<0 && volume > 0.0f){// 화살표 아래쪽 키를 눌렀을 때 
			volume -= 0.1f;
           	FMOD_Channel_SetVolume(channel,volume);
		}
		
		if(GetAsyncKeyState(VK_ESCAPE)<0) break;
		Sleep(50);
	}
	SetColor(WHITE); 
}


/*-----해당 사운드 재생-----*/
void Sound_Play(int n)
{
	FMOD_System_PlaySound(g_System,FMOD_CHANNEL_FREE,g_Sound[n],0,&channel);
}
