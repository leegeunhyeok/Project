#include "sound.h"

void Sound::Init(){
	FMOD_System_Create(&system);
    FMOD_System_Init(system, 10, FMOD_INIT_NORMAL, NULL);
    Load();
}

void Sound::Load(){
	FMOD_System_CreateSound(system, ".\\sound\\main.mp3", FMOD_LOOP_NORMAL, 0, &sound[0]); 
	FMOD_System_CreateSound(system, ".\\sound\\1.mp3", FMOD_LOOP_NORMAL, 0, &sound[1]);
}

void Sound::Update(){
	if(isPlaying) {
		FMOD_System_Update(system);
	}
}

void Sound::StopSound(){
	FMOD_Channel_Stop(channel);
}

void Sound::SoundPlay(int target){
	FMOD_System_PlaySound(system, FMOD_CHANNEL_FREE, sound[target], 0, &channel);
}


