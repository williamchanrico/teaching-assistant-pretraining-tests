#include <stdio.h>
#include <windows.h>

int inputMenu();
int inputAnswer();
int inputPlates();
void inputName(char name[]);
void gotoXY(int x, int y);
void showMenu();
void showOpeningSplash();
void startGameLogic();
void hanoi(int plates, char source, char temp, char target, int *counter);

int main(){
	int choice;

	showOpeningSplash();
	while(true){
		showMenu();
		choice = inputMenu();
		switch(choice){
			case 1:
				startGameLogic();
				break;
			case 2:
				break;
			case 3:
				break;
		}
	}
}

void gotoXY(int x, int y){
	HANDLE hConsoleOutput = GetStdHandle(STD_OUTPUT_HANDLE);
	COORD pos;
	pos.X = x;
	pos.Y = y;
	SetConsoleCursorPosition(hConsoleOutput, pos);
}

void showOpeningSplash(){
	char openingSplash[25][100];

    strcpy(openingSplash[0], "              __    __            __        __    __            __");
    strcpy(openingSplash[1], "             |  \\  |  \\          |  \\      |  \\  |  \\          |  \\");
    strcpy(openingSplash[2], "             | $$\\ | $$  ______   \\$$      | $$\\ | $$  ______   \\$$");
    strcpy(openingSplash[3], "             | $$$\\| $$ /      \\ |  \\      | $$$\\| $$ /      \\ |  \\");
    strcpy(openingSplash[4], "             | $$$$\\ $$|  $$$$$$\\| $$      | $$$$\\ $$|  $$$$$$\\| $$");
    strcpy(openingSplash[5], "             | $$\\$$ $$| $$  | $$| $$      | $$\\$$ $$| $$  | $$| $$");
    strcpy(openingSplash[6], "             | $$ \\$$$$| $$__/ $$| $$      | $$ \\$$$$| $$__/ $$| $$");
    strcpy(openingSplash[7], "             | $$  \\$$$ \\$$    $$| $$      | $$  \\$$$ \\$$    $$| $$");
    strcpy(openingSplash[8], "              \\$$   \\$$  \\$$$$$$  \\$$       \\$$   \\$$  \\$$$$$$  \\$$");
    strcpy(openingSplash[9], "");
    strcpy(openingSplash[10], "");
    strcpy(openingSplash[11], "                                Welcome to Noi Noi");
    strcpy(openingSplash[12], "                             Press Enter to continue...");

	system("cls");
	for(int a=0;a<6;a++)
		printf("\n");
	for(int a=0;a<13;a++){
		printf("\n%s", openingSplash[a]);
		Sleep(100);
	}
	getchar();
	fflush(stdin);
}

void showMenu(){

	system("cls");
	printf("Menu\n");
	printf("------\n");
	printf("1. Play\n");
	printf("2. About\n");
	printf("3. Exit\n");
	
}

int inputMenu(){
	int input;

	while(true){
		printf("Choose : ");
		scanf("%d", &input);
		if(getchar() != '\n' || input > 3 || input < 1){
			fflush(stdin);
			continue;
		}
		fflush(stdin);
		break;
	}
	return input;
}

void inputName(char name[]){
	char input[25];
	int validator;

	system("cls");
	while(true){
		validator = 0;
		printf("Enter your name[3-20] : ");
		gets(input);

		char temp[25];	
		char *tok;
		int len = strlen(input);

		strcpy(temp, input);
		tok = strtok(input, " ");
		while(tok != NULL){
			if(*tok != ' ') validator++;
			tok = strtok(NULL, " ");
		}
		if(len < 3 || len > 20 || validator < 2){
			printf("Name must containt 2 word or more\n");
			continue;
		}
		validator = 1;
		for(int a=0, len=strlen(temp);a<len;a++){
			if(temp[a] != ' ' && !isalpha(temp[a])){
				validator = 0;
				printf("Name must be alphabet\n");
				break;
			}
		}
		strcpy(name, temp);
		if(validator) break;
	}
}

void startGameLogic(){
	char name[25];
	int score[4];
	int level = 1;
	int plates;
	int usersAnswer;
	int ans;

	memset(score, 0, sizeof(score));

	inputName(name);
	while(true){
		Sleep(100);
		printf("\nLevel : %d\n", level);
		Sleep(100);
		printf("Hello %s\n", name);
		Sleep(100);
		plates = inputPlates();
		usersAnswer = inputAnswer();

		ans = 0;
		hanoi(plates, 'A', 'B', 'C', &ans);

		score[level] = (100 - abs(ans - usersAnswer));
		score[level] = (score[level] > 0? score[level] : 0);
		printf("\n");
		Sleep(100);
		printf("The minimum steps to solve this problem is = %d\n", ans); // 2^n - 1
		Sleep(100);
		printf("Your score is = %d\n", score[level]);
		Sleep(100);
		if(level == 1 && score[level] > 64){
			printf("Your level up to 2\n\n");
			score[0] += score[level];
			level++;
		}else if(level == 2 && score[level] > 74){
			printf("Your level up to 3\n\n");
			score[0] += score[level];
			level++;
		}else if(level == 3 && score[level] > 84){
			printf("\nPress Enter to continue...");
			getchar();
			fflush(stdin);
			score[0] += score[level];
			break;
		}
		while(true){
			fflush(stdin);
			printf("Do you want to continue [Y/N] : ");
			char con = getchar();
			if(con == 'N'){
				fflush(stdin);
				return;
			}else if(con == 'Y' || getchar() == '\n'){
				Sleep(25);
				break;
			}
		}
	}
	system("cls");
	printf("\nYOU FINISH THE GAME\n");
	printf("---------------------\n");
	printf("Your scores are :\n");
	for(int a=1;a<=3;a++)
		printf("Your score at level %d is : %d\n", a, score[a]);
	printf("Your total score is : %d\n", score[0]);
	getchar();
	fflush(stdin);
}

int inputPlates(){
	int input;
	int valid;

	while(true){
		printf("Enter the number of plates [1-9] : ");
		valid = scanf("%d", &input);
		if(input < 1 || input > 9 || getchar() != '\n')
			valid = 0;
		fflush(stdin);
		if(valid) break;
	}
	return input;
}

int inputAnswer(){
	int input;
	int valid;

	while(true){
		printf("How many moves will it take to move all the plates : "); //2^n - 1
		valid = scanf("%d", &input);
		if(input < 0 || getchar() != '\n')
			valid = 0;
		fflush(stdin);
		if(valid) break;
	}
	return input;
}

void hanoi(int plates, char source, char temp, char target, int *counter){
	if(plates > 1){
		hanoi(plates - 1, source, target, temp, counter);
		hanoi(1, source, temp, target, counter);
		hanoi(plates - 1, temp, source, target, counter);
	}else{
		if(*counter == 0)
			printf("\nThe Steps are : \n");
		printf("%3d. move the top plate from %c to %c\n", (*counter) + 1, source, target);
		(*counter)++;
		Sleep(100);
	}
}