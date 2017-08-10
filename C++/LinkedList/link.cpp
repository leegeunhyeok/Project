#include "link.h"
#include <iostream>
#include <stdlib.h>
#include <time.h>

using namespace std;

void Linked_list::create(){
	head = new NODE;
	head->next = NULL;
}

void Linked_list::add(int data){
	temp = new NODE;
	temp->next = head->next;
	temp->data = data;
	head->next = temp;
}

void Linked_list::add_random(int count){
	srand((int)time(NULL));
	for(int i=0; i<count; i++){
		temp = new NODE;
		temp->next = head->next;
		temp->data = rand();
		head->next = temp;
	}
}

void Linked_list::del(){
	if(isEmpty()){
		return;
	}
	
	temp = head->next;
	head->next = temp->next;
	delete temp; 
}

void Linked_list::print(){
	int i =1;
	if(isEmpty()){
		return;
	}
			
	temp = head->next;
	while(temp != NULL){
		printf("[ %d ] %d\n",i++, temp->data);
		temp = temp->next;
	}
	system("pause");
}

bool Linked_list::isEmpty(){
	if(head == NULL || head->next == NULL){
		return true;
	}
	else return false;
}




