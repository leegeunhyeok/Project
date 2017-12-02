#include "file.h"

// 货 颇老 积己  
void File::Init(){
	std::ofstream writeFile;
	writeFile.open(SAVEFILE_DIR);
	saveData();
}

void File::removeData(){ 
	for(int i=0; i<LEN; i++){
		achievements[i] = false;
	}
}

void File::loadData(){
	std::ifstream openFile(SAVEFILE_DIR);
	char str[2];
	int i=0;
	if(openFile.is_open()){
		while(!openFile.eof()){
			openFile.getline(str, 2);
			if(str[0] == '0'){
				achievements[i++] = false;
			} else {
				achievements[i++] = true;
			}
		}
		openFile.close();
	} else {
		Init();
	}
}

void File::saveData(){
	std::ofstream writeFile(SAVEFILE_DIR);
	if(writeFile.is_open()){
		for(int i=0; i<LEN; i++){
			if(achievements[i]){
				writeFile<<"1"<<std::endl;
			} else {
				writeFile<<"0"<<std::endl;
			}
		}
		writeFile.close();
	}
}
