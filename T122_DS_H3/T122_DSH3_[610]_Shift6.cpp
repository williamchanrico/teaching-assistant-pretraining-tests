#include <stdio.h>
#include <windows.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

struct Powder{
	char name[25];
	int stock;
} powder[4];

struct PQueue{ //Custom Priority Queue for Order List
	char powderName[25];
	char customerName[25];
	char employeeNumber[10];
	int quantity;
	struct PQueue *next;
	struct PQueue *prev;
} *head = NULL, *tail = NULL, *curr = NULL;

struct PairOfInt{
	int first;
	int second;
};

struct PairOfInt popPQueue(int idx);
int printPQueue();
int powderIdx(char powderName[]);
void pushPQueue(char powderName[], char customerName[], int quantity, char employeeNumber[]);
int isPQueueEmpty();
void initPowderStore();
void transition();
void printPowderList();
void showOpeningSplash();
void clear();
int validEmployeeNumber(char employeeNumber[]);

int main(){
	int choice;

	showOpeningSplash();

	initPowderStore();

	do{
		clear();
		printf("Powder List\n");

		printPowderList();

		printf("\nOrder List\n");

		printPQueue();

		printf("\nBaking FAwder\n");
		printf("=============\n");
		printf("1. Add Powder Stock\n");
		printf("2. Add Order\n");
		printf("3. Serve\n");
		printf("4. Cancel Order\n");
		printf("5. Exit\n");
		printf("Input your choice: ");

		scanf("%d", &choice);
		fflush(stdin);

		switch(choice){
			char customerName[25];
			char powderName[25];
			char employeeNumber[10];
			char cancel;
			int quantity;
			int idx;
			int add;

			case 1:
				transition();

				printf("Powder List\n");
				printPowderList();

				printf("Add Stock\n");
				printf("=========\n");

				do{
					printf("Input powder's index[1-4]: ");
					scanf("%d", &idx);
					fflush(stdin);
				}while(idx < 1 || idx > 4);

				printf("\n%s\n", powder[idx - 1].name);
				printf("=========\n");

				do{
					printf("Add %s\'s stock by[>0]: ", powder[idx - 1].name);
					scanf("%d", &add);
					fflush(stdin);
				}while(add < 1);

				powder[idx - 1].stock += add;
				printf("%s Powder added to %d!", powder[idx - 1].name, powder[idx - 1].stock);

				getchar();
				fflush(stdin);
				transition();
				break;
			case 2:
				transition();
				printf("Add Order\n");
				printf("=========\n");

				do{
					printf("Input customer's name[3-20 characters]: ");
					gets(customerName);
					fflush(stdin);
				}while(strlen(customerName) < 3 || strlen(customerName) > 20);

				do{
					printf("Input powder's name [%s|%s|%s|%s]: ", (powder[0].stock > 0 ? powder[0].name: "-"), (powder[1].stock > 0 ? powder[1].name : "-"), (powder[2].stock > 0 ? powder[2].name: "-"), (powder[3].stock > 0 ? powder[3].name: "-"));
					gets(powderName);
					fflush(stdin);
				}while(powderIdx(powderName) == -1 || powder[powderIdx(powderName)].stock == 0);

				do{
					printf("Input Employee Number[XYYY]: ");
					gets(employeeNumber);
					fflush(stdin);
				}while(!validEmployeeNumber(employeeNumber));

				do{
					printf("Input %s\'s quantity [1-%d]: ", powder[powderIdx(powderName)].name, powder[powderIdx(powderName)].stock);
					scanf("%d", &quantity);
					fflush(stdin);
				}while(quantity < 1 || quantity > powder[powderIdx(powderName)].stock);

				pushPQueue(powderName, customerName, quantity, employeeNumber);

				powder[powderIdx(powderName)].stock -= quantity;

				printf("Order added!");

				getchar();
				fflush(stdin);
				transition();
				break;
			case 3:
				transition();

				if(isPQueueEmpty()){
					printf("No Order Yet!");
				}else{
					popPQueue(1);
					printf("Order Served!");
				}

				getchar();
				fflush(stdin);
				transition();
				break;
			case 4:
				transition();

				if(isPQueueEmpty()){
					printf("No Order Yet!");
				}else{
					printf("Order List\n");
					quantity = printPQueue();
					printf("Cancel Order\n");
					printf("============\n");

					do{
						printf("Input order's index[1-%d]: ", quantity);
						scanf("%d", &idx);
						fflush(stdin);
					}while(idx < 1 || idx > quantity);

					do{
						printf("Are you sure you want to cancel order[Y/N]: ");
						scanf("%c", &cancel);
						fflush(stdin);
					}while(cancel != 'Y' && cancel != 'N');

					if(cancel == 'Y'){
						printf("Order canceled!");
						struct PairOfInt pair = popPQueue(idx);
						powder[pair.first].stock += pair.second;
					}else{
						printf("Order cancel canceled!");
					}
				}
				getchar();
				fflush(stdin);
				transition();
				break;
			case 5:
				transition();
				printf("                   Keep Fighting and Share Our Greatest Skill!");
				getchar();
				fflush(stdin);
				break;
		}
	}while(choice != 5);
}

int printPQueue(){
	int numOfPQueue = 0;

	printf("=============================================================================\n");
	printf("| No. | Customer's Name      | Powder's Name   | Quantity | Employee Number |\n");
	printf("=============================================================================\n");
	curr = head;
	for(int a=0;curr != NULL;a++){
		printf("| %-3d | %-20s | %-15s | %-8d | %-15s |\n", a + 1, curr->customerName, curr->powderName, curr->quantity, curr->employeeNumber);
		curr = curr->next;
		numOfPQueue++;
	}
	printf("=============================================================================\n");
	return numOfPQueue;
}

void pushPQueue(char powderName[], char customerName[], int quantity, char employeeNumber[]){
	struct PQueue *temp = (struct PQueue *) malloc(sizeof(struct PQueue));

	temp->quantity = quantity;
	strcpy(temp->powderName, powderName);
	strcpy(temp->customerName, customerName);
	strcpy(temp->employeeNumber, employeeNumber);

	if(head == NULL){
		head = tail = temp;
	}else if(head == tail){
		if(employeeNumber[0] < head->employeeNumber[0]){
			head->prev = temp;
			temp->next = head;
			head = temp;
		}else{
			head->next = temp;
			temp->prev = head;
			tail = temp;
		}
	}else{
		curr = head;
		while(employeeNumber[0] >= curr->next->employeeNumber[0])
			curr = curr->next;
		curr->next->prev = temp;
		temp->next = curr->next;
		curr->next = temp;
		temp->prev = curr;
	}
	tail->next = NULL;
	head->prev = NULL;
}

int powderIdx(char powderName[]){
	if(strcmp(powderName, "Calumet") == 0) return 0;
	if(strcmp(powderName, "Clabber Girl") == 0) return 1;
	if(strcmp(powderName, "Royal") == 0) return 2;
	if(strcmp(powderName, "Davis") == 0) return 3;
	return -1;
}

struct PairOfInt popPQueue(int idx){
	struct PairOfInt pair;

	if(head != NULL){
		curr = head;
		for(int a=1;a<idx;a++)
			curr = curr->next;
		pair.first = powderIdx(curr->powderName);
		pair.second = curr->quantity;
		if(curr == head){
			if(head == tail){
				head = tail = NULL;
				free(curr);
			}else{
				head = head->next;
				head->prev = NULL;
				free(curr);
			}
		}else if(curr == tail){
			tail = tail->prev;
			tail->next = NULL;
			free(curr);
		}else{
			curr->next->prev = curr->prev;
			curr->prev->next = curr->next;
			free(curr);
		}
	}
	return pair;
}

int isPQueueEmpty(){
	if(head == NULL) return 1;
	return 0;
}

void initPowderStore(){
	strcpy(powder[0].name, "Calumet");
	strcpy(powder[1].name, "Clabber Girl");
	strcpy(powder[2].name, "Royal");
	strcpy(powder[3].name, "Davis");

	powder[0].stock = 10;
	powder[1].stock = 8;
	powder[2].stock = 13;
	powder[3].stock = 27;
}

void transition(){
	for(int a=0;a<30;a++){
		printf("\n");
		Sleep(25);
	}
}

void printPowderList(){
	printf("======================================\n");
	printf("| No. | Name                 | Stock |\n");
	printf("======================================\n");
	for(int a=0;a<4;a++)
		printf("| %-3d | %-20s | %-5d |\n", a + 1, powder[a].name, powder[a].stock);
	printf("======================================\n");
}

void showOpeningSplash(){
	system("cls");
	printf("\n");
	printf("                         _              _     __\n");
	printf("                         |_) _  |  o __ (_|   |_  _     _| _  __\n");
	printf("                         |_)(_| |< | | |__|   |  (_|^/(_|(/_ |\n\n\n");
	printf("Press enter to continue...");
	getchar();
	fflush(stdin);
}

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

int validEmployeeNumber(char employeeNumber[]){
	if((employeeNumber[0] != 'B' && employeeNumber[0] != 'S' && employeeNumber[0] != 'O') || strlen(employeeNumber) > 4)
		return 0;
	for(int a=1;a<4;a++)
		if(!isdigit(employeeNumber[a]))
			return 0;
	return 1;
}
