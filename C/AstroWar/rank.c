#include "rank.h"

struct info rank[11];

/*게임이 끝난 후 사용자 이름과 점수,시간을 랭킹에 저장*/
void AddRank(int time, int score)
{
	Flush_buffer();
	gotoxy(33,13);
	printf("닉네임:");
	gotoxy(41,13);
	//scanf("%s",rank[10].name);
	fgets(rank[10].name,10,stdin);
	
	rank[10].time = time;
	rank[10].score = score;
	RankSort();
	FileSave();
}


void RankReset(void)
{
	int i;
	for(i=0; i<11; i++)
		rank[i].score=0; //점수가 0점인 상태로 파일을 저장하면 시간,점수,이름이 모두 초기화됨	
	FileSave();
}

       
/*점수에 따라 랭크 정렬*/
void RankSort(void)
{
	int i,j,cnt=0;
	struct info temp;
	
	for(i=0; i<10; i++)
	{
		for(j=0; j<11; j++)
		{
			if(rank[j].score < rank[j+1].score)
			{
				temp = rank[j];
				rank[j] = rank[j+1];
				rank[j+1] = temp;
			}
		}
	}
	FileSave();
}


/*파일을 열어 저장되어있던 데이터를 불러옴*/
void FileLoad(void)
{
	int i;
	FILE *savefile;
	
	savefile=fopen(".\\savefile\\rank.txt","rt");

	
	if(savefile == NULL){ //오류 or 파일 없을시 새로 생성 
		savefile=fopen(".\\savefile\\rank.txt","a");
		fclose(savefile);
		return;
	}
	
	
	for(i=0; i<10; i++)
		fscanf(savefile,"%d %d %s\n", &rank[i].time, &rank[i].score, &rank[i].name);
		
	fclose(savefile);
}


/*파일을 열어 TOP 10위 랭킹기록 후 저장*/
void FileSave(void)
{	
	int i;
	FILE *savefile;
	savefile=fopen(".\\savefile\\rank.txt","wt");
	
	for(i=0; i<10; i++){
		if(rank[i].score == 0) //점수가 0점이면
			fprintf(savefile,"0 0 ---\n");
		else	
		    fprintf(savefile,"%d %d %s\n", rank[i].time, rank[i].score, rank[i].name);	
	}
	fclose(savefile);
}




void RankDraw(void)
{
	const x=16;
	int i;
	FrameDraw();
	FileLoad();
	RankSort();
	
	gotoxy(10,36);
	SetColor(RED);
	printf("DELETE : 랭킹 초기화");
	gotoxy(55,36);
	SetColor(WHITE);
	printf("ESC : 나가기");
	
	for(i=0; i<10; i++){
		if(i==0) SetColor(14);
		gotoxy(x,(i+1)*3+2);
		printf("[%d위]",i+1);
		gotoxy(x+8,(i+1)*3+2);
		printf("닉네임: %s",rank[i].name);
		gotoxy(x+26,(i+1)*3+2);
		printf("시간: %d초",rank[i].time);
		gotoxy(x+40,(i+1)*3+2);
		printf("점수: %d",rank[i].score);
		SetColor(7);
		Sleep(200);
	}
	
	while(1)
	{
		if(GetAsyncKeyState(VK_ESCAPE) & 0x8000) break;
		if(GetAsyncKeyState(VK_DELETE) & 0x8000){
			RankReset();
			break;	
		}
	}
	system("cls");
}

