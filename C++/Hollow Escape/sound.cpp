#include "sound.h"

void Sound::Init(){
	FMOD_System_Create(&system);
    FMOD_System_Init(system, 10, FMOD_INIT_NORMAL, NULL);
    Load();
}

void Sound::Load(){ 
	FMOD_System_CreateSound(system, ".\\sound\\main.mp3", FMOD_LOOP_NORMAL, 0, &sound[0]);
	FMOD_System_CreateSound(system, ".\\sound\\get-item.mp3", FMOD_DEFAULT, 0, &sound[1]);
	FMOD_System_CreateSound(system, ".\\sound\\use-key.mp3", FMOD_DEFAULT, 0, &sound[2]);
	FMOD_System_CreateSound(system, ".\\sound\\checkpoint.mp3", FMOD_DEFAULT, 0, &sound[3]); 
	FMOD_System_CreateSound(system, ".\\sound\\lever.mp3", FMOD_DEFAULT, 0, &sound[4]);
	FMOD_System_CreateSound(system, ".\\sound\\push.mp3", FMOD_DEFAULT, 0, &sound[5]);
	FMOD_System_CreateSound(system, ".\\sound\\damage.mp3", FMOD_DEFAULT, 0, &sound[6]);
	FMOD_System_CreateSound(system, ".\\sound\\death.mp3", FMOD_DEFAULT, 0, &sound[7]);
	FMOD_System_CreateSound(system, ".\\sound\\get-shadow.mp3", FMOD_DEFAULT, 0, &sound[8]);
}

void Sound::Update(){
	while(isPlaying) {
		FMOD_System_Update(system);
	}
}

void Sound::StopSound(){
	if(isPlaying){
		FMOD_Channel_Stop(channel);
	}
	isPlaying = false;
}

void Sound::SoundPlay(const int target){
	FMOD_System_PlaySound(system, FMOD_CHANNEL_FREE, sound[target], 0, &channel);
}
