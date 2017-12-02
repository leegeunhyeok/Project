#include "main.h"

#ifndef __GAME_CLASS_
#define __GAME_CLASS_

struct pos {
	int x;
	int y;
	bool dir;
};

class Game {
	private:
		Sound sound;
		bool inGame;
		bool bgmStarted;
		bool checkpoint;
		bool hasShadow;
		bool changedSpawnpoint;
		int shooterCnt;
		int objectDelay;
		int delay;
		int flag;
		int key;
		int hp;
		int locked[10];
		struct pos shooter[100];
		void objectMgr();
		void hpCheck();
		void Init();

	public:
		Game(){
			sound = Sound();
			objectDelay = 3;
			delay = 100;
			flag = 0;
			Init();
		};
		void soundInit();
		void drawTitle();
		int drawMenu(const int, const int, const int);
		int drawMapList();
		void drawMap();
		void drawUI();
		int drawPauseMenu();
		void drawPlayer(const int, const int);
		void gameOver();
		void gameClear();
		void openDoor(const int, const int, const char);
		void stopBgm();
		void playBgm(const int);
		void gStart();
		void help();
		void setMap(const int);
		void gameInit();
		void move(const int, const int);
		int keyControl();	
		int px;
		int py;
		int sx;
		int sy;
		int cx;
		int cy;
};

#endif

#ifndef __DIRECTION_
#define __DIRECTION_

enum{
	DR_LEFT,
	DR_RIGHT,
	DR_UP,
	DR_DOWN,
	ENTER,
	ESCAPE
};

#endif
