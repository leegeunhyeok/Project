#include "astro.h"

struct info{
	char name[10];
	int time;
	int score;
};

void AddRank(int, int);
void RankReset(void);
void RankSort(void);
void FileLoad(void);
void FileSave(void);
void RankDraw(void);

