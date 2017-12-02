#include "main.h"

int main(void) {
	std::cout<<"Loading..";
	Game g = Game();
	int choose;
	
	while(true) {
		g.drawTitle();
		g.playBgm(MAIN); // 배경음악 재생  

		choose = g.drawMenu(54, 28, 3);
		switch(choose) {
			case 0:
				g.gStart();
				break;
					
			case 1:
				g.help(); 
				break;
					
			case 2: 	
				return 0;
		}
		system("cls");
	}
}
