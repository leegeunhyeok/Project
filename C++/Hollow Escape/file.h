#include "main.h"

#ifndef __FILE_IO_H_
#define __FILE_IO_H_

#include <fstream>
#include <iostream>
#include <string.h>

#endif

#ifndef SAVEFILE_DIR
#define SAVEFILE_DIR ".\\data\\user.dat"
#define LEN 10

#endif


#ifndef __FILE_CLASS_
#define __FILE_CLASS_

class File {
	private:
		
	public:
		bool achievements[LEN];
		void Init();
		void removeData();
		void loadData();
		void saveData();
};

#endif
