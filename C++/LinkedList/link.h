#ifndef __LINK_H__
#define __LINK_H__

typedef struct node {
	struct node* next;
	int data;
}NODE;

class Linked_list {
	private:
		NODE* head;
		NODE* temp;
		
	public:
		void create();
		void add(int);
		void add_random(int);
		void print();
		void del();
		bool isEmpty();
};

#endif
