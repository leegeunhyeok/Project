#include "main.h"

int main(void) {
	Game g = Game();
	Sound sound = Sound();
	g.drawTitle();
	sound.SoundPlay(0);
	
	while(1){
		sound.Update();	
	}
	return 0;
}
