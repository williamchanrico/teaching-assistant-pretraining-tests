#include <stdio.h>
#include <windows.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>

struct Date{
	int day;
	int month;
	int year;
};

struct Participant{
	int id;
	char name[25];
	char address[25];
	struct Date birthdate;

	struct Participant *next;
	struct Participant *prev;
} *head = NULL, *tail = NULL, *curr = NULL;

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

void showExitSplash(){
	system("cls");
	for(int a=0;a<4;a++)
		printf("\n");
	printf("                   .NMMm\n");
	printf("                   JMMMM\n");
	printf("                   ,MMMm,.\n");
	printf("                   .MMMMMMMNNg,.\n");
	printf("                   .MMMMMM#T''MM)\n");
	printf("                   .MMMMMMF  M#\n");
	printf("                MMNMMMMMMMb .Mi\n");
	printf("                     .HMMMM,JMF - Run Like You Just Stole Something! -\n");
	printf("                     ..MMMMMb             - marathonQuotes -\n");
	printf("                   .MMMMMMMMM;\n");
	printf("                 .MMMMMMMMMMMN,\n");
	printf("                 WMMr`    .WMMMp\n");
	printf("                  7MMr      .WMMN&.\n");
	printf("                   ,MN.        7WMMN,\n");
	printf("                     MN,          ?WMm,\n");
	printf("                    .MMMb            MMMM\n");
	printf("                   .MMB^              WMF\n");
	printf("                                      .#!\n");
}

void popHead(){
	if(head != NULL){
		if(head == tail){
			free(head);
			head = tail = NULL;
		}else{
			head = head->next;
			free(head->prev);
			head->prev = NULL;
		}
	}
}

void popTail(){
	if(head != NULL){
		if(head == tail){
			free(tail);
			head = tail = NULL;
		}else{
			tail = tail->prev;
			free(tail->next);
			tail->next = NULL;
		}
	}
}

void view(){
	clear();
	int total = 0;
	curr = head;
	if(curr != NULL){
		printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 218, 196, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 191);
		printf("%c ID   %c Participant Name     %c Birthdate  %c Address              %c\n", 179, 179, 179, 179, 179);
		printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 195, 196, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 180);
		while(curr != NULL){
			printf("%c %-4d %c %-20s %c %04d-%02d-%02d %c %-20s %c\n", 179, curr->id, 179, curr->name, 179, curr->birthdate.year, curr->birthdate.month, curr->birthdate.day, 179, curr->address, 179);
			curr = curr->next;
			total++;
		}
		printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 192, 196, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 217);

		printf("\n\n\nCurrently %d participant[s] registered", total);
	}else{
		printf("Currently no participant registered..");
	}
}

void popSearch(int id){
	curr = head;
	while(id != curr->id){
		curr = curr->next;
	}
	if(id == head->id){
		popHead();
	}else if(id == tail->id){
		popTail();
	}else{
		curr->next->prev = curr->prev;
		curr->prev->next = curr->next;
		free(curr);		
	}
}

int viewSearch(char keyword[]){
	clear();
	int total = 0;
	int idTemp[1000];
	curr = head;
	printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 218, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 194, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 191);
	printf("%c No  %c ID   %c Participant Name     %c Birthdate  %c Address              %c\n", 179, 179, 179, 179, 179, 179);
	printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 195, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 197, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 180);
	while(curr != NULL){
		if(strstr(curr->name, keyword) != 0){
			printf("%c %-3d %c %-4d %c %-20s %c %04d-%02d-%02d %c %-20s %c\n", 179, total + 1, 179, curr->id, 179, curr->name, 179, curr->birthdate.year, curr->birthdate.month, curr->birthdate.day, 179, curr->address, 179);
			idTemp[++total] = curr->id;
		}
		curr = curr->next;
	}
	printf("%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c%c\n", 192, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 193, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 196, 217);

	printf("\n\n\n%d participant[s] found\n", total);
	printf("Select participant to delete, 'r' to requery, or 'b' to back to main menu\n\n");
	int input;
	while(true){
		printf("Choose [1-%d|r|b] : ", total);
		scanf("%d", &input);
		char test = getchar();
		fflush(stdin);
		if(test == 'r') return 1;
		else if(test == 'b') return 2;
		else if(input < 1 || input > total){
			printf("Invalid input");
			getchar();
			fflush(stdin);
			continue;
		}
		popSearch(idTemp[input]);
		printf("Participant removed...");
		getchar();
		fflush(stdin);
		break;
	}
	return 0;
}

int searchById(int id){
	curr = head;
	while(curr != NULL && curr->id != id){
		curr = curr->next;
	}
	if(curr == NULL) return 0;
	return 1;
}

struct Participant *searchByName(char name[]){
	struct Participant *temp = head;
	while(temp != NULL && strstr(temp->name, name) == 0){
		temp = temp->next;
	}
	return temp;
}

void pushHead(int id, char name[], struct Date birthdate, char address[]){
	struct Participant *temp = (struct Participant *) malloc(sizeof(struct Participant));
	
	temp->id = id;
	temp->birthdate.day = birthdate.day;
	temp->birthdate.month = birthdate.month;
	temp->birthdate.year = birthdate.year;
	strcpy(temp->name, name);
	strcpy(temp->address, address);

	if(head == NULL){
		head = tail = temp;
	}else{
		temp->next = head;
		head->prev = temp;
		head = temp;
	}
	head->prev = NULL;
	tail->next = NULL;
}

void pushTail(int id, char name[], struct Date birthdate, char address[]){
	struct Participant *temp = (struct Participant *) malloc(sizeof(struct Participant));
	
	temp->id = id;
	temp->birthdate.day = birthdate.day;
	temp->birthdate.month = birthdate.month;
	temp->birthdate.year = birthdate.year;
	strcpy(temp->name, name);
	strcpy(temp->address, address);

	if(head == NULL){
		head = tail = temp;
	}else{
		temp->prev = tail;
		tail->next = temp;
		tail = temp;
	}
	head->prev = NULL;
	tail->next = NULL;
}

void pushSort(int id, char name[], struct Date birthdate, char address[]){
	if(head == NULL || id < head->id){
		pushHead(id, name, birthdate, address);
	}else if(id > tail->id){
		pushTail(id, name, birthdate, address);
	}else{
		struct Participant *temp = (struct Participant *) malloc(sizeof(struct Participant));
		
		temp->id = id;
		temp->birthdate.day = birthdate.day;
		temp->birthdate.month = birthdate.month;
		temp->birthdate.year = birthdate.year;
		strcpy(temp->name, name);
		strcpy(temp->address, address);

		curr = head;
		while(temp->id > curr->next->id){
			curr = curr->next;
		}
		curr->next->prev = temp;
		temp->next = curr->next;
		curr->next = temp;
		temp->prev = curr;
	}
}



void showOpeningSplash(){
	system("cls");
	for(int a=0;a<5;a++)
		printf("\n");
	printf("         ____    __    __    ____\n");
	printf("        (  _ \\  /__\\  (  )  (_  _)\n");
	printf("         ) _ < /(__)\\  )(__  _)(_\n");
	printf("        (____/(__)(__)(____)(____)\n");
	printf("             __  __    __    ____    __   ____  _   _  _____  _  _\n");
	printf("            (  \\/  )  /__\\  (  _ \\  /__\\ (_  _)( )_( )(  _  )( \\( )\n");
	printf("             )    (  /(__)\\  )   / /(__)\\  )(   ) _ (  )(_)(  )  (\n");
	printf("            (_/\\/\\_)(__)(__)(_)\\_)(__)(__)(__) (_) (_)(_____)(_)\\_)\n");
	printf("                             ___   ___  __   _\n");
	printf("                            (__ \\ / _ \\/  ) / )\n");
	printf("                             / _/( (_) ))( / _ \\\n");
	printf("                            (____)\\___/(__)\\___/\n");
	getchar();
	fflush(stdin);
}

void printMenu(){
	printf("1. View Participant\n");
	printf("2. Register Participant\n");
	printf("3. Remove Participant\n");
	printf("4. Exit\n");
}

int main(){
	srand(time(NULL));
	int choice;
	int id;
	char temp[25];
	char name[25];
	char address[25];
	struct Date birthdate;

	showOpeningSplash();
	
	do{
		clear();
		printMenu();
		printf(">> ");
		scanf("%d", &choice);
		fflush(stdin);
		switch(choice){
			int valid;

			case 1:
				view();
				getchar();
				fflush(stdin);
				break;
			case 2:
				do{
					clear();
					printf("Would you like to input ID manually or using auto generate ID by system?\n");
					for(int a=0;a<72;a++)
						printf("%c", 196);
					printf("\n\n");
					printf("Choose \'auto\' or \'manual\'\t: ");
					scanf("%s", temp);
					fflush(stdin);
				}while(strcmp(temp, "auto") != 0 && strcmp(temp, "manual") != 0);
				printf("\n");
				if(strcmp(temp, "auto") == 0){
					printf("Generating ID");
					for(int a=0;a<3;a++){
						printf(" -");
						Sleep(500);
					}
					do{
						id = (rand() % 9000) + 1000;
					}while(searchById(id));
					printf(" -> God ID %d\n\n\n", id);
				}else{
					do{
						printf("Input ID [1000-9999]\t\t: ");
						scanf("%d", &id);
						fflush(stdin);
						if(searchById(id)){
							printf("ID already used, choose another ID\n\n");
						}else if(id < 1000 || id > 9999){
							printf("Invalid input\n\n");
						}else{
							printf("\n\n");
						}
					}while(id < 1000 || id > 9999 || searchById(id));
				}
				printf("Participant Info\n");
				for(int a=0;a<31;a++)
					printf("%c", 196);
				printf("\n");
				while(true){
					printf("Input Name [4-20]: ");
					gets(name);
					fflush(stdin);
					if(strlen(name) < 4 || strlen(name) > 20){
						printf("Name must be 4-20 char length\n\n");
					}else{
						break;
					}
				}
				do{
					printf("Birthdate [Y-M-D]: ");
					valid = 0;
					valid += scanf("%d", &birthdate.day);
					if(getchar() != '-')
						valid = -1;
					valid += scanf("%d", &birthdate.month);
					if(getchar() != '-')
						valid = -1;
					valid += scanf("%d", &birthdate.month);
					if(getchar() != '\n')
						valid = -1;
					fflush(stdin);
					if(valid != 3){
						printf("Invalid Date, date format is YYYY-MM-DD - ex: 1994-05-14\n\n");
					}
				}while(valid != 3);
				do{
					valid = 0;
					printf("Address\t\t : ");
					gets(address);
					fflush(stdin);

					char str[25];
					strcpy(str, address);
					char *tok = strtok(str, " ");
					while(tok != NULL){
						if(strcmp(tok, " ") != 0) valid++;
						tok = strtok(NULL, " ");
					}
					if(valid < 2 || strlen(address) > 20 || strlen(address) < 4){
						valid = 0;
					}else{
						valid = 1;
					}
				}while(!valid);
				pushSort(id, name, birthdate, address);
				printf("\n\nNew participant registered");
				getchar();
				fflush(stdin);
				break;
			case 3:
				while(true){
					view();
					printf("\n\nInput name to delete\t: ");
					gets(name);
					fflush(stdin);
					curr = searchByName(name);
					char again;
					if(curr == NULL){
						clear();
						printf("No participant found..\n");
						do{
							printf("Search name again? [y|n] : ");
							scanf("%c", &again);
							fflush(stdin);
						}while(again != 'y' && again != 'n');
						if(again != 'y') break;
					}else{
						int ok = viewSearch(name);
						if(ok == 2 || ok == 0) break;
					}
				}
				break;
			case 4:
				system("color 0B");
				showExitSplash();
				getchar();
				fflush(stdin);
				system("color 0F");
				break;
		}
	}while(choice != 4);
}