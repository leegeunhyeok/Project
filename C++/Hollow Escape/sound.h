#include "main.h"

#ifndef __SOUND_CLASS_
#define __SOUND_CLASS_

class Sound {
	private:
		void Init();
		void Load();
		FMOD_SYSTEM* system;
		FMOD_SOUND* sound[5]; 
		FMOD_CHANNEL* channel = NULL;
		FMOD_BOOL isPlaying;
		
	public:
		Sound(){
			Init();
		};
		void Update();
		void StopSound();
		void SoundPlay(int);
};

#endif
