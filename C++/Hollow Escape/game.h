#include "main.h"

#ifndef __GAME_CLASS_
#define __GAME_CLASS_

class Game {
	private:
		void Init();
	
	public:
		Game(){
			Init();
		};
		void soundInit();
		void drawTitle();
		void drawMenu();
		void loadData();
		void saveData();
};

#endif
