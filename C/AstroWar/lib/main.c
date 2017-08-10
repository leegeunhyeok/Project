#include <stdio.h>

#include <conio.h>

#include <stdlib.h>

#include <malloc.h>

#include <fmod.h>




#pragma comment (lib,libfmodex.a)




FMOD_SYSTEM *g_System; // Note: FMOD system 변수선언

static FMOD_SOUND *g_Sound;

 

void Init()

{

    FMOD_System_Create(&g_System);

    FMOD_System_Init(g_System,32,FMOD_INIT_NORMAL,NULL);

    FMOD_System_CreateSound(g_System,"main.wav",FMOD_DEFAULT,0,&g_Sound);

}




void Release()

{

     FMOD_Sound_Release(g_Sound);

     FMOD_System_Close(g_System);

     FMOD_System_Release(g_System);

}




int main(int argc, char *argv[])

{

  int nkey;

  

  FMOD_CHANNEL *channel = NULL;

  

  float volume = 0.5f;

  

  FMOD_BOOL IsPlaying;

  

  Init();

  

  while(1)

  {

          if(_kbhit())

          {

                      nkey = _getch();

                      if(nkey == 'q') // q 키가 눌리면 프로그램 종료

                      break;

                      

                      switch(nkey)

                      {

                      case 'p':

                           FMOD_System_PlaySound(g_System,FMOD_CHANNEL_FREE,g_Sound,0,&channel);

                      break;

                      

                      case 's':

                           FMOD_Channel_Stop(channel);

                      break;

                     

                      case 'c':

                                     FMOD_Channel_IsPlaying(channel,&IsPlaying);

                                     if(IsPlaying == 1)

                                     printf("노래 잘 나오고 있엉\n");

                                     else

                                     printf("ㄴㄴ 노래 없다네\n");

                                     

                           break;

                     

                      

                      case 'u':

                           volume += 0.1f;

                           if(volume > 1.0f)

                           volume = 1.0f;

                           FMOD_Channel_SetVolume(channel,volume);

                           break;

                           

                      case 'd':

                           volume -= 0.1f;

                           if(volume <0.0f)

                           volume = 0.0f;

                           FMOD_Channel_SetVolume(channel,volume);

                           break;

                      

                      }

          }

          

          FMOD_Channel_IsPlaying(channel,&IsPlaying);

          if(IsPlaying == 1)

          FMOD_System_Update(g_System);

  }

                            

  Release();

  

  system("PAUSE"); 

  return 0;

}


