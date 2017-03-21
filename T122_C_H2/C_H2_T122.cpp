#include <stdio.h>
#include <windows.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct dict{
	char list[26][500][30];
	int listSize[26];
	int maxPhrases;
	int totalPhrases;

	void construct(){
		totalPhrases = 0;
		for(int a=0;a<26;a++)
			listSize[a] = 0;
	}

	int findPhrase(char phrase[]){
		for(int a=0;a<listSize[tolower(phrase[a]) - 'a'];a++)
			if(strstr(list[tolower(phrase[a]) - 'a'][a], phrase))
				return 1;
		return 0;
	}

	void searchPhrase(){
		char searchPhrase[35];
		int printTitle;
		int notFound = 1;

		do{
			printf("Search phrase: ");
			gets(searchPhrase);
		}while(strlen(searchPhrase) == 0);

		printf("\nResult:\n");
		for(int a=0;a<26;a++){
			char thePhrase[35];

			printTitle = 0;
			for(int b=0;b<listSize[a];b++){
				strcpy(thePhrase, list[a][b]);
				char *found = strstr(thePhrase, searchPhrase);
				if(found && !printTitle){
					printf("\n%c", 'A' + a);
					printTitle = 1;
					notFound = 0;
				}
				if(found){
					thePhrase[0] = toupper(thePhrase[0]);
					for(int c=0;c<strlen(thePhrase);c++){
						char *found = strchr(thePhrase, ' ');
						while(found != NULL){
							*(found + 1) = toupper(*(found + 1));
							found = strchr(found + 1, ' ');
						}
					}
					printf("\n\t%s\n", thePhrase);
				}
			}
		}
		if(notFound)
			printf("\nSearch not found....");
		else
			printf("\n");
		getchar();
		fflush(stdin);
	}

	void showAllPhrases(){
		printf("Total phrases added : %d\n\n", totalPhrases);
		for(int a=0;a<26;a++){
			printf("%c\n", 'A' + a);
			if(listSize[a] == 0){
				printf("\t[EMPTY]\n\n");
				continue;
			}
			for(int b=0;b<listSize[a];b++){
				char thePhrase[35];
				
				strcpy(thePhrase, list[a][b]);
				thePhrase[0] = toupper(thePhrase[0]);
				for(int c=0;c<strlen(thePhrase);c++){
					char *found = strchr(thePhrase, ' ');
					while(found != NULL){
						*(found + 1) = toupper(*(found + 1));
						found = strchr(found + 1, ' ');
					}
				}
				printf("\t%s\n\n", thePhrase);
			}
		}
		getchar();
		fflush(stdin);
	}

	void addPhrases(){
		char phrase[35];
		int idx;

		while(true){
			char input[35];
			int valid = 1;

			do{
				printf("Input phrase[starts with alphabet, 5-30 chars]: ");
				gets(input);
				fflush(stdin);
			}while(strlen(input) == 0);
			for(int a=0, b=strlen(input);a<b;a++)
				if(b < 5 || b > 30 || (input[a] != ' ' && !isalpha(input[a]))){
					valid = 0;
					break;
				}else{
					input[a] = tolower(input[a]);
				}
			if(valid){
				strcpy(phrase, input);
				totalPhrases++;
				break;
			}
		}
		idx = tolower(phrase[0]) - 'a';
		if(findPhrase(phrase)){
			totalPhrases--;
			phrase[0] = toupper(phrase[0]);
			for(int c=0;c<strlen(phrase);c++){
				char *found = strchr(phrase, ' ');
				while(found != NULL){
					*(found + 1) = toupper(*(found + 1));
					found = strchr(found + 1, ' ');
				}
			}
			printf("\'%s\' word is already inputted..", phrase);
		}else if(listSize[idx] >= maxPhrases){
			totalPhrases--;
			printf("Word '%c' inventory is full..", toupper(phrase[0]));
		}else{
			strcpy(list[idx][listSize[idx]++], phrase);
			printf("\nInsert Success!");
		}
		getchar();
		fflush(stdin);
	}
} Dict;

void clear();
void showOpeningSplash();
void showMenu(int maxPhrases);
void showExitSplash();
int inputChoice();
int inputMaxPhrases();

int main(){
	int choice;
	int maxPhrases;
	
	showOpeningSplash();

	maxPhrases = inputMaxPhrases();
	
	Dict dict;

	dict.construct();
	dict.maxPhrases = maxPhrases;

	while(true){
		showMenu(maxPhrases);
		choice = inputChoice();
		switch(choice){
			case 1:
				dict.addPhrases();
				break;
			case 2:
				dict.showAllPhrases();
				break;
			case 3:
				if(dict.totalPhrases == 0){
					printf("Inventory is empty");
					getchar();
					fflush(stdin);
				}else{
					dict.searchPhrase();
				}
				break;
			case 4:
				showExitSplash();
				return 0;
				break;
		}
	}
}


void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

void showOpeningSplash(){
	char openingSplash[25][100];
	clear();
	strcpy(openingSplash[0], "               ____.____   ______________           .___.__");
	strcpy(openingSplash[1], "              |    |\\   \\ /   /\\______   \\ ____   __| _/|__|____");
	strcpy(openingSplash[2], "              |    | \\   Y   /  |     ___// __ \\ / __ | |  \\__  \\");
	strcpy(openingSplash[3], "          /\\__|    |  \\     /   |    |   \\  ___// /_/ | |  |/ __ \\_");
	strcpy(openingSplash[4], "          \\________|   \\___/    |____|    \\___  >____ | |__(____  /");
	strcpy(openingSplash[5], "                                              \\/     \\/         \\/");
	for(int a=0;a<6;a++)
		printf("%s\n", openingSplash[a]);
	printf("\n                                 BY JV 15-1\n");
	printf("\n                        Press any key to continue . . .");
	for(int a=0;a<7;a++)
		printf("\n");
	getchar();
	fflush(stdin);
}

int inputMaxPhrases(){
	int input;
	int valid;

	do{
		printf("Input max phrases per alphabet[numeric, min 1]: ");
		valid = scanf("%d", &input);
		if(getchar() != '\n')
			valid = 0;
		fflush(stdin);
	}while(!valid || input < 1);
	return input;
}

void showMenu(int maxPhrases){
	clear();
	printf("Max phrases per alphabet : %d\n\n", maxPhrases);
	printf("1. Insert new phrase\n");
	printf("2. View All Phrases\n");
	printf("3. Search Phrases\n");
	printf("4. Exit\n");
}

int inputChoice(){
	int choice;
	int valid;

	do{
		printf(">>  ");
		valid = scanf("%d", &choice);
		if(getchar() != '\n')
			valid = 0;
		fflush(stdin);
	}while(!valid);
	return choice;
}

void showExitSplash(){
	char exitSplash[25][100];

	clear();
	strcpy(exitSplash[0], "     $$$$$$$\\  $$\\                                             $$\\");
	strcpy(exitSplash[1], "     $$  __$$\\ $$ |                                            $$ |");
	strcpy(exitSplash[2], "     $$ |  $$ |$$ |$$\\   $$\\  $$$$$$\\  $$\\  $$$$$$\\   $$$$$$$\\ $$ |  $$\\");
	strcpy(exitSplash[3], "     $$$$$$$\\ |$$ |$$ |  $$ |$$  __$$\\ \\__| \\____$$\\ $$  _____|$$ | $$  |");
	strcpy(exitSplash[4], "     $$  __$$\\ $$ |$$ |  $$ |$$$$$$$$ |$$\\  $$$$$$$ |$$ /      $$$$$$  /");
	strcpy(exitSplash[5], "     $$ |  $$ |$$ |$$ |  $$ |$$   ____|$$ |$$  __$$ |$$ |      $$  _$$<");
	strcpy(exitSplash[6], "     $$$$$$$  |$$ |\\$$$$$$  |\\$$$$$$$\\ $$ |\\$$$$$$$ |\\$$$$$$$\\ $$ | \\$$\\");
	strcpy(exitSplash[7], "     \\_______/ \\__| \\______/  \\_______|$$ | \\_______| \\_______|\\__|  \\__|");
	strcpy(exitSplash[8], "                                 $$\\   $$ |");
	strcpy(exitSplash[9], "                                 \\$$$$$$  |");
	strcpy(exitSplash[10], "                                  \\______/");
	strcpy(exitSplash[11], "                         $$\\  $$$$$$$\\         $$\\");
	strcpy(exitSplash[12], "                       $$$$ | $$  ____|      $$$$ |");
	strcpy(exitSplash[13], "                       \\_$$ | $$ |           \\_$$ |");
	strcpy(exitSplash[14], "                         $$ | $$$$$$$\\ $$$$$$\\ $$ |");
	strcpy(exitSplash[15], "                         $$ | \\_____$$\\\\______|$$ |");
	strcpy(exitSplash[16], "                         $$ | $$\\   $$ |       $$ |");
	strcpy(exitSplash[17], "                       $$$$$$\\$$$$$$  |     $$$$$$\\");
	strcpy(exitSplash[18], "                       \\______|\\______/      \\______|");
	strcpy(exitSplash[19], "");
	strcpy(exitSplash[20], "                  Keep Fighting and Share Our Greatest Skills");
	strcpy(exitSplash[21], "                        Press any key to continue . . .");

	for(int a=0;a<22;a++){
		for(int b=0;b<strlen(exitSplash[a]);b++){
			printf("%c", exitSplash[a][b]);
			Sleep(3);
		}
		printf("\n");
	}
	printf("\n");
	getchar();
	fflush(stdin);
}
