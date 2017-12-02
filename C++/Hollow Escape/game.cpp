#include "game.h"

char map[35][100];

char map1[35][100] = {
	"000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
	"011111110000000000000000000000000000000000000111111111111111111100000000000000000000000000000000",
	"010000011111111111111111111111111111111111100100000000000000000100000000000000000000000000000000",
	"0100p00000000000000000l0000000000000000000100100000000000000000100000000000000000000000000000000",
	"010000011111111100111111100011111111111100111111110001111110000100000000000000000000000000000000",
	"011111110000000100100000100010000000000100000000000001000000000100000000000000000000000000000000",
	"000000000000000100100000100010000000000100000000000001000000000100000000000000000000000000000000",
	"0000000011111111001000001000100000000001000000000000010000s0000100000000000000000000000000000000",
	"000000001000000000100000100011111110000111111aa1111111000000000100000000000000000000000000000000",
	"0000000010000k0000100000100000000011000100000000000001111111111100000000000000000000000000000000",
	"000000001000000000100000100000000001000100000000000000000100000000000000000000000000000000000000",
	"000000001111111111100000111111111001111100110011111111000101110000000000000000000000000000000000",
	"000000000000000000000000100000000000000r00110011000001000101B10000000000000000000000000000000000",
	"000000111111111100000000100011111111111111110A11000001h00101010000000111100000000000000000000000",
	"00000013000300010000000010001000000000000011111100000100h101010000000103111111100000000000000000",
	"0000111000000001111111111000100000000000000000000000010hh10101000000010000000E100000000000000000",
	"00001k1000000000000000001000100011111000000000000000010r0101010000000100111111100000000000000000",
	"000010000000000000000000100011111313111111111111110001h0h111011000000100100000000000000000000000",
	"0000111000000001111111001000b0000000000000000000010001000r00001000000100100000000000000000000000",
	"0000001000300031D1000100111111111000111111111111010001111111111000000100100000000000000000000000",
	"000000111111111101000100000000101000100C100r0010010000000000000000000100100011111111100000000000",
	"000000100010r00101000100000000101000101111r10r10010000000000000000000100100010000000100000000000",
	"0000001010101011011111111111dd11110110100r0100r001000000000000000000010010001000Z000100000000000",
	"000000101010101000000000120000000100100001011110011111111100000000000100100010000000100000000000",
	"000000101010100000000000100000002100111111111111110000000100000000011100100011110111100000000000",
	"0000001010001000000000000000000001100c000000000000000s000100000000010000100000010100000000000000",
	"000000101111111111111111111111111111111011111111110000000100000000010130100000010100000000000000",
	"00000010000000000000000000000000000000000000000001111111111111111111011e100000010100000000000000",
	"000000111111111111111111111100011111111111111111000000000l00l000000r0010100000010100000000000000",
	"000000000000000000000000000100010000000000000001111111111111111111111110111111110110000000000000",
	"00000000000000000000000011110001111000000000000000000000000000000000010000000000r010000000000000",
	"000000000000000000000000120000000010000000000000000000000000000000000111111111111110000000000000",
	"000000000000000000000000100000000210000000000000000000000000000000000000000000000000000000000000",
	"00000000000000000000000010000k000010000000000000000000000000000000000000000000000000000000000000",
	"000000000000000000000000111111111110000000000000000000000000000000000000000000000000000000000000"    
};



// 타이틀 출력  
void Game::drawTitle(){
	setColor(white, black);
	std::cout<<"\n\n";
	std::cout<<"                                                     @@          @@\n";
	std::cout<<"                                                  (@@              @@)\n";
	std::cout<<"                     @@)               @@          @:    (:@@:)    :@          @@               (@@\n";
	std::cout<<"                    @                 :#            @@@@@@0@@0@@@@@@            #:                 @\n";
	std::cout<<"                   (##@@#:      (#@#<--(@@@@      _-@@            @@-_      @@@@)-->#@#)     :#@@##)\n";
	std::cout<<"                        (@@@@@@@@@         @@@@@@@                    @@@@@@@         @@@@@@@@@)\n\n";
	//
	std::cout<<"                       ####    ####   ######   ####      ####        ######   ####           ####\n";
	std::cout<<"                        ##      ##   ##    ##   ##        ##        ##    ##   ##             ##\n";
	std::cout<<"                        ##      ##  ##      ##  ##        ##       ##      ##  ##             ##\n";
	std::cout<<"                        ##########  ##      ##  ##        ##       ##      ##   ##    ###    ##\n";
	std::cout<<"                        ##      ##  ##      ##  ##        ##       ##      ##   ##   ## ##   ##\n";
	std::cout<<"                        ##      ##  ##      ##  ##        ##       ##      ##    ## ##   ## ##\n";
	std::cout<<"                        ##      ##   ##    ##   ##     #  ##     #  ##    ##     ## ##   ## ##\n";
	std::cout<<"                       ####    ####   ######   ######### #########   ######       ####   ####\n\n";
	//
	std::cout<<"            @@)                                      * E S C A P E *                                     (@@\n";
	std::cout<<"           @     @@         @@@)                                                        (@@@         @@     @\n";
	std::cout<<"          @    _@  @       @          @@@@@@    _____@@#<<(==)>>#@@_____    @@@@@@          @       @  @_    @\n";
	std::cout<<"          (#@@#@       @@@@:@@@@@@@@@@  || @@@@@  | @ << (-==-) >> @ |  @@@@@ ||  @@@@@@@@@@:@@@@       @#@@#)\n";
	std::cout<<"               @@@@@@@@              @@@@@   |  @@@@ --<< (==) >>-- @@@@  |   @@@@@              @@@@@@@@\n";
	std::cout<<"                                      @   @###@@@  @@@@_        _@@@@  @@@###@   @\n";
	std::cout<<"                                                       @-  ()  -@\n";
	std::cout<<"                                                        @------@\n";
	std::cout<<"                                                         @@__@@\n";
	
	gotoxy(112, 34);
	std::cout<<"v"<<VERSION;
}

void Game::help(){
	system("cls");
	gotoxy(53, 3);
	std::cout<<"<<{ 도움말 }>>";
	gotoxy(44, 4);
	std::cout<<"Hollow Escape는 탈출형 게임입니다.";
	gotoxy(35, 5);
	std::cout<<"Hollow Knight라는 게임의 방식을 일부 적용시켰습니다.";
	
	gotoxy(35, 7);
	std::cout<<"플레이어가 목적지에 도착하면 되는 간단한 게임입니다.";
	gotoxy(28, 8);
	std::cout<<"잠겨있는 문, 레버, 방해물 및 위험요소들이 있으니 조심해야합니다.";
	
	gotoxy(53, 12);
	std::cout<<"<<{ 조작법 }>>";
	gotoxy(52, 13);
	std::cout<<"이동: 화살표 키";
	gotoxy(54, 14);
	std::cout<<"선택: ENTER";
	
	gotoxy(30, 16);
	std::cout<<"플레이어: ";
	setColor(cyan, black);
	std::cout<<"O";
	setColor(white, black);
	std::cout<<" - 체력은 5칸이며 죽으면 그 위치에 그림자가 풀어집니다.";
	
	gotoxy(30, 17);
	std::cout<<"그림자: ";
	setColor(darkgray, black);
	std::cout<<"O";
	setColor(white, black);
	std::cout<<" - 그림자의 위치로 오면 다시 플레이어에게 흡수됩니다.";
	
	gotoxy(30, 18);
	std::cout<<"열쇠: ";
	setColor(yellow, black);
	std::cout<<"*";
	setColor(white, black);
	std::cout<<" - 잠긴문을 열 수 있습니다.";
	
	gotoxy(30, 19);
	std::cout<<"잠긴 문: ";
	setColor(brown, black);
	std::cout<<"{"; 
	setColor(white, black);
	std::cout<<" - 열쇠를 이용하여 열 수 있습니다.";
	
	gotoxy(30, 20);
	std::cout<<"기계 문: ";
	setColor(brown, black);
	std::cout<<"+"; 
	setColor(white, black);
	std::cout<<" - 연결된 레버를 작동시키면 열 수 있습니다."; 
	
	gotoxy(30, 21);
	std::cout<<"레버: ";
	setColor(brown, black);
	std::cout<<"!"; 
	setColor(white, black);
	std::cout<<" - 연결된 기계문을 열어줍니다."; 
	
	gotoxy(30, 22);
	std::cout<<"가시: ";
	setColor(red, black);
	std::cout<<"x";
	setColor(white, black);
	std::cout<<" - 부딛히면 체력이 떨어집니다."; 
	
	gotoxy(30, 23);
	std::cout<<"총알: ";
	setColor(lightred, black);
	std::cout<<"o";  
	setColor(white, black);
	std::cout<<" - 부딛히면 체력이 떨어집니다."; 
	
	gotoxy(30, 24);
	std::cout<<"의자: ";
	setColor(lightgreen, black);
	std::cout<<"="; 
	setColor(white, black);
	std::cout<<" - 부활지점을 의자가 있는위치로 변경합니다."; 
	
	gotoxy(30, 25);
	std::cout<<"바위: ";
	setColor(darkgray, black);
	std::cout<<"@"; 
	setColor(white, black);
	std::cout<<" - 플레이어가 밀어낼 수 있습니다."; 
	
	gotoxy(30, 26);
	std::cout<<"차원문: ";
	setColor(black, white);
	std::cout<<"O";
	setColor(white, black);
	std::cout<<" - 목적지 (주의: 그림자가 있어야 들어갈 수 있습니다.)"; 
	
	setColor(white, black);
	gotoxy(40, 29);
	std::cout<<"ENTER 키를 누르면 메인화면으로 이동합니다.";
	
	while(true){
		Sleep(delay);
		int n = keyControl();
		
		if(n == ENTER){
			break;
		}
	} 
}

// 메뉴 출력 및 선택 기능  
int Game::drawMenu(const int x, const int y, const int max){
	bool loop = true;
	int dir = 0;
	
	gotoxy(x, y);
	std::cout<<"  게임시작"; // Code: 0
	gotoxy(x, y+1);
	std::cout<<"  게임방법"; // Code: 1
	gotoxy(x, y+2);
	std::cout<<"    종료"; // Code: 2
	
	gotoxy(x-1, y+dir);
	std::cout<<">";
	gotoxy(x+12, y+dir);
	std::cout<<"<";
	
	while(loop){
		Sleep(100);
		int n = keyControl();
		switch(n){		
			case 2: {
				if(dir > 0) {
					gotoxy(x-1, y+dir);
					std::cout<<" ";
					gotoxy(x+12, y+dir);
					std::cout<<" ";
					dir--;
					gotoxy(x-1, y+dir);
					std::cout<<">";
					gotoxy(x+12, y+dir);
					std::cout<<"<";
				}
				break;
			}
					
			case 3: {
				if(dir < 2) {
					gotoxy(x-1, y+dir);
					std::cout<<" ";
					gotoxy(x+12, y+dir);
					std::cout<<" ";
					dir++;
					gotoxy(x-1, y+dir);
					std::cout<<">";
					gotoxy(x+12, y+dir);
					std::cout<<"<";
				}
				break;
			}
				
			case 4: {
				loop = false;
				break;
			}
		}
	}
	return dir; // 메뉴코드 리턴  
}

int Game::drawMapList(){
	const int x = 53;
	int select = 0;
	bool loop = true;
	
	gotoxy(x, 7);
	std::cout<<"<<{  맵 선택  }>>";
	gotoxy(x-2, 10);
	std::cout<<"> 1. 잊혀진 교차로";
	gotoxy(x, 13);
	std::cout<<"2. 녹색길";
	gotoxy(x, 16);
	std::cout<<"3. 수정봉우리";
	gotoxy(x, 19);
	std::cout<<"4. 왕도의 수로";
	gotoxy(x, 22);
	std::cout<<"5. 백색궁전";
	gotoxy(x, 25);
	std::cout<<"     메인";
	Sleep(100);
	
	while(loop){
		Sleep(100);
		int n =	keyControl();
		
		switch(n){
			case 2:{
				if(select > 0){
					gotoxy(x-2, 10+(select*3));
					std::cout<<" ";
					select--;
					gotoxy(x-2, 10+(select*3));
					std::cout<<">";
				}
				break;
			}
			
			case 3:{
				if(select < 5){
					gotoxy(x-2, 10+(select*3));
					std::cout<<" ";
					select++;	
					gotoxy(x-2, 10+(select*3));
					std::cout<<">";
				}
				break;
			}
			
			case 4:{
				loop = false;
				break;
			}
		}
	}
	return select;
}

void Game::drawMap(){
	shooterCnt = 0;
	system("cls");
	for(int i=0; i<35; i++){
		for(int j=0; j<95; j++){
			char temp = map[i][j];
			if(temp == '0'){
				setColor(white, black);
				std::cout<<" ";
			} else if(temp == '1'){
				setColor(white, black);
				std::cout<<"#";
			} else if(temp == 'p'){
				px = j;
				py = i;
				cx = j;
				cy = i;
				map[i][j] = '0';
				setColor(cyan, black);
				std::cout<<"O";
			} else if(temp == 'l'){
				setColor(brown, black);
				std::cout<<"{";
			} else if(temp == 'k'){
				setColor(yellow, black);
				std::cout<<"*";
			} else if(temp == 'r'){
				setColor(darkgray, black);
				std::cout<<"@";
			} else if(temp == 's'){
				setColor(lightgreen, black);
				std::cout<<"=";
			} else if(temp == 'h'){
				setColor(red, black);
				std::cout<<"x";
			} else if(temp == '2' || temp == '3'){
				setColor(lightred, black);
				std::cout<<"o";	
				
				struct pos tempPos;
				if(temp == '2'){
					if(map[i][j-1] == '1'){ // 왼쪽에 벽이 있을경우  
						tempPos.dir = true; // 오른쪽으로 이동 
					} else {
						tempPos.dir = false;
					} 
				} else if(temp == '3') {
					if(map[i-1][j] == '1'){ // 위에 벽이 있을경우  
						tempPos.dir = true; // 아래로 이동 
					} else {
						tempPos.dir = false;
					} 
				}
				tempPos.x = j;
				tempPos.y = i; 
				shooter[shooterCnt++] = tempPos;
			} else if(temp == 'a' || temp == 'b' || temp == 'c' || temp == 'd' || temp == 'e') {
				setColor(brown, black);
				std::cout<<"+";
				switch(temp){
					case 'a':{
						locked[0] = j;
						locked[1] = i;
						break;
					}
					
					case 'b':{
						locked[2] = j;
						locked[3] = i;
						break;
					}
					
					case 'c':{
						locked[4] = j;
						locked[5] = i;
						break;
					}
					
					case 'd':{
						locked[6] = j;
						locked[7] = i;
						break;
					}
					
					case 'e':{
						locked[8] = j;
						locked[9] = i;
						break;
					}
				}
			} else if(temp == 'A' || temp == 'B' || temp == 'C' || temp == 'D' || temp == 'E'){
				setColor(brown, black);
				std::cout<<"!";
			} else if(temp == 'Z'){
				setColor(black, white);
				std::cout<<"O";
			} else {
				setColor(white, black);
				std::cout<<temp;
			}
		}
		std::cout<<"|\n";
	}
	drawUI();
}

void Game::drawUI(){
	setColor(white, black);
	gotoxy(104, 5);
	std::cout<<"[ 정보 ]";
	
	gotoxy(103, 8);
	std::cout<<"열쇠: ";
	setColor(yellow, black);
	std::cout<<key;
	setColor(white, black);
	std::cout<<"개";
	
	setColor(white, black);
	gotoxy(103, 10);
	std::cout<<"생명: ";
	setColor(lightred, black);
	std::cout<<hp;
	
	setColor(white, black);
	gotoxy(102, 12);
	std::cout<<"그림자: ";
	setColor(darkgray, black);
	if(hasShadow){
		std::cout<<"있음";
	} else {
		std::cout<<"없음";
	}
	
	setColor(white, black);
	gotoxy(103, 30);
	std::cout<<"            ";
	gotoxy(103, 30);
	std::cout<<"좌표: "<<px<<", "<<py;
	
	gotoxy(100, 32);
	std::cout<<"                  ";
	gotoxy(100, 32);
	std::cout<<"체크포인트: ";
	setColor(lightgreen, black); 
	std::cout<<cx<<", "<<cy;
	setColor(white, black); 
}

int Game::drawPauseMenu(){
	const int x = 104;
	int select = 0;
	bool loop = true;
	
	gotoxy(x-2, 14);
	std::cout<<"> 계속하기";
	gotoxy(x, 15);
	std::cout<<"메인으로";
	Sleep(100);
	
	while(loop){
		Sleep(100);
		int n =	keyControl();
		
		switch(n){
			case 2:{
				if(select > 0){
					gotoxy(x-2, 14+select);
					std::cout<<" ";
					select--;
					gotoxy(x-2, 14+select);
					std::cout<<">";
				}
				break;
			}
			
			case 3:{
				if(select < 1){
					gotoxy(x-2, 14+select);
					std::cout<<" ";
					select++;	
					gotoxy(x-2, 14+select);
					std::cout<<">";
				}
				break;
			}
			
			case 4:{
				loop = false;
				break;
			}
		}
	}
	return select;
}

void Game::stopBgm(){
	sound.StopSound();
	bgmStarted = false;
}

void Game::playBgm(const int code){
	if(!bgmStarted) {
		sound.SoundPlay(code);
		bgmStarted = true;
	}
}

// 게임 루프 시작  
void Game::gStart(){
	system("cls");
	inGame = false;
	int n = drawMapList();
		
	switch(n){
		case 0:{
			setMap(0);
			inGame = true;
			break;
		}
		
		case 1:{
			setMap(1);
			inGame = true;
			break;
		}
		
		case 2:{
			setMap(2);
			inGame = true;
			break;
		}
		
		case 3:{
			setMap(3);
			inGame = true;
			break;
		}
		
		case 4:{
			setMap(4);
			inGame = true;
			break;
		}
		
		case 5:
		default: {
			break;
		}
	}
	
	if(inGame){
		gameInit();
		drawMap();
		Sleep(100);
	}
	
	while(inGame){ // 게임 루프 
		int k = keyControl();
		 
		switch(k){
			case DR_LEFT:{
				move(-1, 0);
				break;
			}
			
			case DR_RIGHT:{
				move(1, 0);
				break;
			}
			
			case DR_UP:{
				move(0, -1);
				break;
			}
			
			case DR_DOWN:{
				move(0, 1); // y+1
				break;
			}
			
			case ESCAPE:{
				int n = drawPauseMenu();
				if(n == 0) {
					gotoxy(100, 14);
					std::cout<<"            ";
					gotoxy(100, 15);
					std::cout<<"            ";
					break;	
				} else {
					return;
				}
			}
		}
		
		objectMgr();
		drawUI();
		hpCheck();
		Sleep(100);	
	}
}

void Game::move(const int x, const int y){
	char mapObject = map[py+y][px+x];
	
	if(mapObject == '0' || mapObject == '2' || mapObject == '3'){ // 비어있음 or 총알  
		drawPlayer(x, y);
		if(checkpoint && changedSpawnpoint){
			checkpoint = false;
			gotoxy(cx, cy);
			setColor(lightgreen, black);
			std::cout<<"=";
		}
	} else if(mapObject == 'k'){ // 열쇠 아이템  
		sound.SoundPlay(GET_ITEM);  
		key++;
		map[py+y][px+x] = '0';
		drawPlayer(x, y);
	} else if(mapObject == 'l'){ // 잠긴 문 
		if(key > 0){
			sound.SoundPlay(USE_KEY);  
			key--;
			map[py+y][px+x] = '0';
			gotoxy(px+x, py+y);
			std::cout<<" ";
		}
	} else if(mapObject == 'r'){ // 돌  
		if(map[py+(y*2)][px+(x*2)] == '0'){
			sound.SoundPlay(PUSH); 
			gotoxy(px+(x*2), py+(y*2));
			setColor(darkgray, black);
			std::cout<<"@";	
			map[py+y][px+x] ='0';
			map[py+y*2][px+x*2] ='r';
			drawPlayer(x, y);
		}
	} else if(mapObject == 'h'){
		sound.SoundPlay(DAMAGE);
		hp--;
	} else if(mapObject == 'A' || mapObject == 'B' || mapObject == 'C' || mapObject == 'D' || mapObject == 'E'){ // 레버
		sound.SoundPlay(LEVER);
		map[py+y][px+x] = '0';
		switch(mapObject){
			case 'A':{
				openDoor(locked[0], locked[1], 'a');
				break;
			}
			
			case 'B':{
				openDoor(locked[2], locked[3], 'b');
				break;
			}
			
			case 'C':{
				openDoor(locked[4], locked[5], 'c');
				break;
			}
			
			case 'D':{
				openDoor(locked[6], locked[7], 'd');
				break;
			}
			
			case 'E':{
				openDoor(locked[8], locked[9], 'e');
				break;
			}
		}
		drawPlayer(x, y);
	} else if(mapObject == 's'){ // 스폰 포인트
		changedSpawnpoint = true;
		sound.SoundPlay(CHECKPOINT);  
		cx = px+x;
		cy = py+y;
		checkpoint = true;
		drawPlayer(x, y);
	} else if(mapObject == 'Z'){
		if(hasShadow){
			gameClear();
		}
	}
}

int Game::keyControl(){
	if(GetAsyncKeyState(VK_LEFT) & 0x8000){ // < 0
		return DR_LEFT;
	}else if(GetAsyncKeyState(VK_RIGHT) & 0x8000){
		return DR_RIGHT;
	}else if(GetAsyncKeyState(VK_UP) & 0x8000){
		return DR_UP;
	}else if(GetAsyncKeyState(VK_DOWN) & 0x8000){
		return DR_DOWN;
	} else if(GetAsyncKeyState(VK_RETURN) & 0x8000){
		return ENTER;
	} else if(GetAsyncKeyState(VK_ESCAPE) & 0x8000){
		return ESCAPE;
	} else {
		return -1;
	}
}

void Game::setMap(const int n){
	switch(n){
		case 0:{
			memcpy(map, map1, sizeof(map));
			break;
		}
		
		case 1:{
			memcpy(map, map1, sizeof(map));
			break;
		}
		
		case 2:{
			memcpy(map, map1, sizeof(map));
			break;
		}
		
		case 3:{
			memcpy(map, map1, sizeof(map));
			break;
		}
		
		case 4:{
			memcpy(map, map1, sizeof(map));
			break;
		}
	}
}

void Game::gameInit(){
	key = 0;
	hp = 5;
	flag = 0;
	hasShadow = true;
	changedSpawnpoint = false;
	for(int i=0; i<10; i++){
		locked[i] = 0;
	}
	
	checkpoint = false;
}


void Game::objectMgr(){	
	for(int i=0; i<shooterCnt; i++){
		struct pos* target = &shooter[i];
		int x = target->x;
		int y = target->y;
		gotoxy(x, y);
		std::cout<<" ";
		
		char temp = map[y][x];
				
		if(temp == '2'){
			if(target->dir){
				if(map[y][x+1] == '0'){
					map[y][x] = '0';
					map[y][x+1] = '2';
					
					target->x++;
					
					gotoxy(x+1, y);
					setColor(lightred, black);
					std::cout<<"o";
				} else {
					shooter[i].dir = false;
				}
			} else {
				if(map[y][x-1] == '0'){
					map[y][x] = '0';
					map[y][x-1] = '2';
					
					target->x--;
					
					gotoxy(x-1, y);
					setColor(lightred, black);
					std::cout<<"o";
				} else {
					shooter[i].dir = true;
				}
			}
		} else if(temp == '3'){
			if(target->dir){
				if(map[y+1][x] == '0'){
					map[y][x] = '0';
					map[y+1][x] = '3';
					
					target->y++;
				
					gotoxy(x, y+1);
					setColor(lightred, black);
					std::cout<<"o";
				} else {
					shooter[i].dir = false;
				}
			} else {
				if(map[y-1][x] == '0'){
					map[y][x] = '0';
					map[y-1][x] = '3';
					
					target->y--;
					
					gotoxy(x, y-1);
					setColor(lightred, black);
					std::cout<<"o";
				} else {
					shooter[i].dir = true;
				}
			}
		}
		
		if(target->x == px && target->y == py){
			sound.SoundPlay(DAMAGE);
			hp--;
		}
	}
	
	if(px == sx && py == sy){
		sx = 0;
		sy = 0;
		sound.SoundPlay(GET_SHADOW);
		hasShadow = true;
	}
	
	drawPlayer(0, 0);
}

void Game::hpCheck(){
	if(hp <= 0){
		gameOver();
	}
}

// 게임 초기화  
void Game::Init(){
	system("mode con cols=120 lines=36 | title Hollow Escape"); 
	hideCursor();
}

void Game::drawPlayer(const int x, const int y){
	gotoxy(px, py);
	std::cout<<" ";
	gotoxy(px+x, py+y);
	setColor(cyan, black);
	std::cout<<"O";
	px+=x;
	py+=y;
	
	if(!hasShadow){
		gotoxy(sx, sy);
		setColor(darkgray, black);
		std::cout<<"0";
		setColor(white, black);
	}
}

void Game::gameOver(){
	sound.SoundPlay(DEATH);
	gotoxy(px, py);
	setColor(darkgray, black);
	std::cout<<"O";
	sx = px;
	sy = py;
	px = cx;
	py = cy;
	hp = 5;
	hasShadow = false;
	checkpoint = true;
	setColor(white, black);
	Sleep(3000);
}

void Game::gameClear(){
	inGame = false;
	setColor(black, white);
	gotoxy(26, 15);
	std::cout<<"                     "; gotoxy(30, 21);
	std::cout<<"                     "; gotoxy(30, 22);
	std::cout<<"     C L E A R !     "; gotoxy(30, 23);
	std::cout<<"                     "; gotoxy(30, 24);
	std::cout<<"                     ";
	Sleep(3000);
}

void Game::openDoor(const int x, const int y, const char target){
	for(int i=-1; i<=1; i++){
		if(map[y][x+i] == target){
			map[y][x+i] = '0';
			gotoxy(x+i, y);
			std::cout<<" ";
		}
	}
	
	for(int i=-1; i<=1; i++){
		if(map[y+i][x] == target){
			map[y+i][x] = '0';
			gotoxy(x, y+i);
			std::cout<<" ";
		}
	}
}
