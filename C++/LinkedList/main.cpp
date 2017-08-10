#include "link.h"
#include <iostream>

using namespace std;

void add_random(int);

int main(void){
	int n, data, ex = 1;
	Linked_list list;
	Linked_list* p = &list;
	p->create();
	
	while(true){
		cout<<"1. ADD\n2. RANDOM DATA ADD\n3. SEARCH\n4. PRINT\n5. DELETE\n0. EXIT"<<endl;
		cin>>n;
		
		switch(n)
		{
			case 1:
				cout<<"Input INTEGER data : ";
				cin>>data;
				p->add(data);
				break;
				
			case 2:
				cout<<"Node count: ";
				cin>>n;
				//add_random(n);
				break;
			
			case 3: 
				break;
				
			case 4:
				p->print();
				break;
				
			case 5:
				p->del();
				break;
				
			case 0: 
				ex = 0;
				break;
				
			default:
				break;
		}
		system("cls");
		if(ex==0) break;
	}
	return 0;
}
