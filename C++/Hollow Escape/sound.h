#include "main.h"

#ifndef __SOUND_CLASS_
#define __SOUND_CLASS_

class Sound {
	private:
		void Init();
		void Load();
		FMOD_SYSTEM* system;
		FMOD_SOUND* sound[10]; 
		FMOD_CHANNEL* channel = NULL;
		FMOD_BOOL isPlaying;
		
	public:
		Sound(){
			Init();
		};
		void Update();
		void StopSound();
		void SoundPlay(const int);
};

#endif

#ifndef _SOUND_LIST__
#define _SOUND_LIST__

enum {
	MAIN,
	GET_ITEM,
	USE_KEY,
	CHECKPOINT,
	LEVER,
	PUSH,
	DAMAGE,
	DEATH,
	GET_SHADOW
};

#endif
