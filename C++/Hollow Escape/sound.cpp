#include "sound.h"

void Sound::Init(){
	FMOD_System_Create(&system);
    FMOD_System_Init(system, 10, FMOD_INIT_NORMAL, NULL);
    Load();
}

void Sound::Load(){ 
	FMOD_System_CreateSound(system, ".\\sound\\main.mp3", FMOD_LOOP_NORMAL, 0, &sound[MAIN]);
	FMOD_System_CreateSound(system, ".\\sound\\get-item.mp3", FMOD_DEFAULT, 0, &sound[GET_ITEM]);
	FMOD_System_CreateSound(system, ".\\sound\\use-key.mp3", FMOD_DEFAULT, 0, &sound[USE_KEY]);
	FMOD_System_CreateSound(system, ".\\sound\\checkpoint.mp3", FMOD_DEFAULT, 0, &sound[CHECKPOINT]); 
	FMOD_System_CreateSound(system, ".\\sound\\lever.mp3", FMOD_DEFAULT, 0, &sound[LEVER]);
	FMOD_System_CreateSound(system, ".\\sound\\push.mp3", FMOD_DEFAULT, 0, &sound[PUSH]);
	FMOD_System_CreateSound(system, ".\\sound\\damage.mp3", FMOD_DEFAULT, 0, &sound[DAMAGE]);
	FMOD_System_CreateSound(system, ".\\sound\\death.mp3", FMOD_DEFAULT, 0, &sound[DEATH]);
	FMOD_System_CreateSound(system, ".\\sound\\get-shadow.mp3", FMOD_DEFAULT, 0, &sound[GET_SHADOW]);
	FMOD_System_CreateSound(system, ".\\sound\\dream.mp3", FMOD_DEFAULT, 0, &sound[DREAM]);
	FMOD_System_CreateSound(system, ".\\sound\\get-essence.mp3", FMOD_DEFAULT, 0, &sound[GET_ESSENCE]);
	FMOD_System_CreateSound(system, ".\\sound\\last.mp3", FMOD_DEFAULT, 0, &sound[LAST]);
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
