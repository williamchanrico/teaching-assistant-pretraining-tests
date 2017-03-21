#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <windows.h>

typedef struct player{
	char name[25];
	char gender[10];
	int highScore;
} Player;

void clear();
void inputData(char name[], char gender[]);
void showOpeningSplash();
void showCurrentInfo(char name[], char gender[], int &highScore);
void showMenu();
void showHowToPlay();
void showExitSplash();
void showTopPlayer(Player player[], int numOfPlayers);
void bubbleSort(Player player[], int size);
void processRawData(char rawData[][50], Player player[], int numOfPlayers);
void fillGrid(char grid[][10], int choice);
void printGrid(char grid[][10], int size, int X1, int Y1, int X2, int Y2);
void randomSortHorizontal(char grid[][10], int len);
void randomSortVertical(char grid[][10], int idx, int len);
void swapPlayer(Player *x, Player *y);
void swapSymbol(char *x, char *y);
void updateHighScore(char name[], char gender[], int highScore);
int getHighScore(char rawData[][50]);
int startGameLogic(char name[], char gender[]);
int playGame(char grid[][10], char name[], char gender[], int size);
int inputCoord(int size, char x);
int maxi(int x, int y);


int main(){
	char name[25];
	char gender[10];
	char rawData[100][50];
	int highScore = 0;
	int choice;
	int numOfPlayers;

	showOpeningSplash();

	inputData(name, gender);

	numOfPlayers = getHighScore(rawData);

	Player player[100];

	processRawData(rawData, player, numOfPlayers);
	
	while(true){
		clear();
		showCurrentInfo(name, gender, highScore);
		showMenu();
		printf("Choice >> ");
		scanf("%d", &choice);
		fflush(stdin);
		switch(choice){
			case 1:
				highScore = maxi(highScore, startGameLogic(name, gender));
				player[numOfPlayers].highScore = maxi(highScore, player[numOfPlayers].highScore);
				strcpy(player[numOfPlayers].name, name);
				strcpy(player[numOfPlayers].gender, gender);
				break;
			case 2:
				showTopPlayer(player, numOfPlayers + (highScore != 0? 1 : 0));
				break;
			case 3:
				showHowToPlay();
				break;
			case 4:
				updateHighScore(name, gender, highScore);
				showExitSplash();
				getchar();
				return 0;
				break;
			default:
				break;
		}
	}
}

int maxi(int x, int y){
	return (x > y? x : y);
}

void swapSymbol(char *x, char *y){
	*x = *x + *y;
	*y = *x - *y;
	*x = *x - *y;
}

void randomSortHorizontal(char grid[][10], int idx, int len){
	srand(time(NULL));
	if(rand() % 4 == 0) return;
	int swapped = 1;
	while(swapped){
		swapped = false;
		for(int a=0;a<(len - 1);a++)
			if(grid[idx][a] > grid[idx][a + 1]){
				swapSymbol(&grid[idx][a], &grid[idx][a + 1]);
				swapped = 1;
			}
	}
}

void randomSortVertical(char grid[][10], int idx, int len){
	srand(time(NULL));
	if(rand() % 5 == 0) return;
	int swapped = 1;
	while(swapped){
		swapped = 0;
		for(int a=0;a<(len - 1);a++)
			if(grid[a][idx] < grid[a + 1][idx]){
				swapSymbol(&grid[a][idx], &grid[a + 1][idx]);
				swapped = 1;
			}
	}
}

void fillGrid(char grid[][10], int choice){
	char symbols[32];
	int idx = 0;

	for(int a=1;a<=6;a++)
		symbols[idx++] = a;
	for(int a=97;a<=122;a++)
		symbols[idx++] = a;

	switch(choice){
		case 1:
			idx = 0;
			for(int a=0;a<=1;a++)
				for(int b=0;b<4;b++)
					grid[a][b] = symbols[idx++];
			idx = 0;
			for(int a=3;a>=2;a--)
				for(int b=3;b>=0;b--)
					grid[a][b] = symbols[idx++];
			idx = 4;
			break;
		case 2:
			idx = 0;
			for(int a=2;a>=0;a--)
				for(int b=0;b<6;b++)
					grid[a][b] = symbols[idx++];
			idx = 0;
			for(int a=5;a>=3;a--)
				for(int b=5;b>=0;b--)
					grid[a][b] = symbols[idx++];
			idx = 6;
			break;
		case 3:
			idx = 0;
			for(int a=7;a>=4;a--)
				for(int b=7;b>=0;b--)
					grid[a][b] = symbols[idx++];
			idx = 0;
			for(int a=3;a>=0;a--)
				for(int b=0;b<=7;b++)
					grid[a][b] = symbols[idx++];
			idx = 8;
			break;
	}
	for(int a=0;a<choice;a++)
		randomSortVertical(grid, a, idx);
	for(int a=0;a<choice;a++)
		randomSortHorizontal(grid, a, idx);
}

void printGrid(char grid[][10], int size, int X1, int Y1, int X2, int Y2){
	printf("  ");
	for(int a=0;a<size;a++)
		printf(" %d ", a);
	printf("\n");
	for(int a=0;a<size;a++){
		printf("%d ", a);
		for(int b=0;b<size;b++){
			if((b == X1 && a == Y1) || (b == X2 && a == Y2))
				printf("[%c]", grid[a][b]);
			else if(grid[a][b] == ' ')
				printf("   ");
			else
				printf("[-]");
		}
		printf("\n");
	}
}

int inputCoord(int size, char x){
	int input;
	int valid = 0;

	do{
		printf("Input %c [0..%d]: ", x, size - 1);
		valid = scanf("%d", &input);
		if(getchar() != '\n');
			valid = 0;
	}while(!valid && (input < 0 || input >= size));
	return input;
}

int playGame(char grid[][10], char name[], char gender[], int size){
	int score = 0;
	int X1;
	int X2;
	int Y1;
	int Y2;
	
	while(true){
		clear();
		showCurrentInfo(name, gender, score);
		printGrid(grid, size, -1, -1, -1, -1);
		do{
			X1 = inputCoord(size, 'X');
			Y1 = inputCoord(size, 'Y');
		}while(grid[Y1][X1] == ' ');
		clear();
		showCurrentInfo(name, gender, score);
		printGrid(grid, size, X1, Y1, -1, -1);
		do{
			X2 = inputCoord(size, 'X');
			Y2 = inputCoord(size, 'Y');
		}while(grid[Y2][X2] == ' ');
		clear();
		showCurrentInfo(name, gender, score);
		printGrid(grid, size, X1, Y1, X2, Y2);

		if(grid[Y1][X1] == grid[Y2][X2]){
			score += 100;
			grid[Y1][X1] = ' ';
			grid[Y2][X2] = ' ';
			if(score >= (100 * size * size / 2)){
				printf("Congratulation !! You completed map ");
				if(size == 4) printf("1");
				else if(size == 6) printf("2");
				else if(size == 8) printf("3");
				getchar();
				fflush(stdin);
				return score;
			}else{
				printf("Find Success !!!\n");
				printf("Your score added by 100 !!!\n");
				getchar();
				fflush(stdin);
			}
		}else{
			printf("Find Failed !!!");
			getchar();
			fflush(stdin);
		}
	}
}

int startGameLogic(char name[], char gender[]){
	int choice;
	int valid = 0;
	char grid[10][10];

	do{
		clear();
		printf("Choose your map\n");
		printf("==================\n");
		printf("1. Map 1 -> 4x4\n");
		printf("2. Map 2 -> 6x6\n");
		printf("3. Map 3 -> 8x8\n");
		printf("Choice >> ");
		valid = scanf("%d", &choice);
		if(getchar() != '\n' || choice > 3 || choice < 1)
			valid = 0;
		fflush(stdin);
	}while(!valid);

	fillGrid(grid, choice);

	switch(choice){
		case 1:
			printGrid(grid, 4, -1, -1, -1, -1);
			return playGame(grid, name, gender, 4);
			break;
		case 2:
			printGrid(grid, 6, -1, -1, -1, -1);
			return playGame(grid, name, gender, 6);
			break;
		case 3:
			printGrid(grid, 8, -1, -1, -1, -1);
			return playGame(grid, name, gender, 8);
			break;
	}
}

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

void showOpeningSplash(){
	for(int a=0;a<6;a++)
		printf("\n");
	printf("               .___             .__                      __\n");
	printf("   _____     __| _/_ __  ______ |__| ____    _________ _/  |______    ____\n");
	printf("   \\__  \\   / __ |  |  \\ \\____ \\|  |/    \\  / ___\\__  \\\\   __\\__  \\  /    \\\n");
	printf("    / __ \\_/ /_/ |  |  / |  |_> >  |   |  \\/ /_/  > __ \\|  |  / __ \\|   |  \\\n");
	printf("   (____  /\\____ |____/  |   __/|__|___|  /\\___  (____  /__| (____  /___|  /\n");
	printf("        \\/      \\/       |__|           \\//_____/     \\/          \\/     \\/\n");
	printf("\n\n                                %c", 201);
	for(int a=0;a<12;a++)
		printf("%c", 205);
	printf("%c\n                                %c By AP 15-1 %c\n", 187, 186, 186);
	printf("                                %c", 200);
	for(int a=0;a<12;a++)
		printf("%c", 205);
	printf("%c\n\n                           Press Enter to continue...", 188);
	for(int a=0;a<7;a++)
		printf("\n");
	getchar();
	fflush(stdin);
}

void inputData(char name[], char gender[]){
	do{
		printf("Input your name [5..20] : ");
		gets(name);
		if(strlen(name) < 5 || strlen(name) > 20)
			printf("Name length must between 5 and 20 !\n");
	}while(strlen(name) < 5 || strlen(name) > 20);

	do{
		printf("Input your gender [Male|Female] : ");
		gets(gender);
		if(strcmp(gender, "Male") != 0 && strcmp(gender, "Female") != 0)
			printf("Gender must be Male or Female !\n");
	}while(strcmp(gender, "Male") != 0 && strcmp(gender, "Female") != 0);
}

void showCurrentInfo(char name[], char gender[], int &highScore){
	printf("%c", 201);
	for(int a=0;a<15;a++)
		printf("%c", 205);
	printf("%c\n%c Player's Data %c\n", 187, 186, 186);
	printf("%c", 200);
	for(int a=0;a<15;a++)
		printf("%c", 205);
	printf("%c\n", 188);
	printf("Name       : %s\n", name);
	printf("Gender     : %s\n", gender);
	printf("High Score : %d\n\n", highScore);
}

void showMenu(){
	printf("%c", 201);
	for(int a=0;a<6;a++)
		printf("%c", 205);
	printf("%c\n%c Menu %c\n", 187, 186, 186);
	printf("%c", 200);
	for(int a=0;a<6;a++)
		printf("%c", 205);
	printf("%c\n", 188);
	printf("1. Play game\n");
	printf("2. Show top Player\n");
	printf("3. How to play\n");
	printf("4. Exit\n");
}

void showHowToPlay(){
	clear();
	printf("How to play\n");
	printf("==============\n");
	printf("- Input the coordinate that asked by the program\n");
	printf("- The program will show the symbol that located in the coordinate that you had\n  already inputted\n");
	printf("- You must find the same symbol to destroy it\n");
	printf("- For every a couple of symbol that you had found you will get a score\n");
	printf("- If you play with the more bigger map, you will gain more high score\n");
	printf("- Play more to gain the highest score than the other player\n");
	getchar();
	fflush(stdin);
}

void showExitSplash(){
	char exitSplash[25][100];

	strcpy(exitSplash[0], "          __________.__                     ____.              __");
	strcpy(exitSplash[1], "          \\______   \\  |  __ __   ____     |    |____    ____ |  | __");
	strcpy(exitSplash[2], "           |    |  _/  | |  |  \\_/ __ \\    |    \\__  \\ _/ ___\\|  |/ /");
	strcpy(exitSplash[3], "           |    |   \\  |_|  |  /\\  ___//\\__|    |/ __ \\\\  \\___|    <");
	strcpy(exitSplash[4], "           |______  /____/____/  \\___  >________(____  /\\___  >__|_ \\");
	strcpy(exitSplash[5], "                  \\/                 \\/              \\/     \\/     \\/");
	strcpy(exitSplash[6], "                          ____ .________         ____");
	strcpy(exitSplash[7], "                         /_   ||   ____/        /_   |");
	strcpy(exitSplash[8], "                          |   ||____  \\   ______ |   |");
	strcpy(exitSplash[9], "                          |   |/       \\ /_____/ |   |");
	strcpy(exitSplash[10], "                          |___/______  /         |___|");
	strcpy(exitSplash[11], "                                    \\/");
	strcpy(exitSplash[12], "                  Keep Fighting and Share Our Greatest Skill");
	clear();
	for(int a=0;a<5;a++)
		printf("\n");
	for(int a=0;a<12;a++){
		printf("%s\n", exitSplash[a]);
		Sleep(250);
	}
	printf("\n%s", exitSplash[12]);
	for(int a=0;a<6;a++)
		printf("\n");
}

void swapPlayer(Player *x, Player *y){
	char charTemp[25];
	int intTemp;

	intTemp = x->highScore;
	x->highScore = y->highScore;
	y->highScore = intTemp;

	strcpy(charTemp, x->name);
	strcpy(x->name, y->name);
	strcpy(y->name, charTemp);

	strcpy(charTemp, x->gender);
	strcpy(x->gender, y->gender);
	strcpy(y->gender, charTemp);
}

void bubbleSort(Player player[], int size){
	int swapped;
	do{
		swapped = 0;
		for(int a=0;a<(size - 1);a++)
			if(player[a].highScore < player[a + 1].highScore){
				swapPlayer(&player[a], &player[a + 1]);
				swapped = 1;
			}
	}while(swapped);
}

void updateHighScore(char name[], char gender[], int highScore){
	FILE *out;

	out = fopen("highscore.txt", "a");
	fprintf(out, "\n%s#%s#%d", name, gender, highScore);
	fclose(out);
}

int getHighScore(char rawData[][50]){
	FILE *in;
	int numOfPlayers = 0;

	in = fopen("highscore.txt", "r");
	if(!in){
		perror("Error opening highscore.txt");
		exit(0);
	}else{
		while(!feof(in)){
			fgets(rawData[numOfPlayers], 50, in);
			numOfPlayers++;
		}
	}
	fclose(in);
	
	return numOfPlayers;
}

void showTopPlayer(Player player[], int numOfPlayers){
	clear();
	bubbleSort(player, numOfPlayers);
	printf("%c", 201);
	for(int a=0;a<53;a++)
		printf("%c", 205);
	printf("%c\n%c Top player                                          %c\n", 187, 186, 186);
	printf("%c%c%c%c%c%c", 204, 205, 205, 205, 205, 203, 206);
	for(int a=0;a<20;a++)
		printf("%c", 205);
	printf("%c", 203);
	for(int a=0;a<13;a++)
		printf("%c", 205);
	printf("%c", 203);
	for(int a=0;a<13;a++)
		printf("%c", 205);
	printf("%c\n", 185);
	printf("%c No.%c Name               %c Gender      %c High Score  %c\n", 186, 186, 186, 186, 186);
	printf("%c%c%c%c%c%c", 204, 205, 205, 205, 205, 206, 206);
	for(int a=0;a<20;a++)
		printf("%c", 205);
	printf("%c", 206);
	for(int a=0;a<13;a++)
		printf("%c", 205);
	printf("%c", 206);
	for(int a=0;a<13;a++)
		printf("%c", 205);
	printf("%c\n", 185);

	for(int a=0;a<numOfPlayers;a++)
		printf("%c %2d %c %18s %c %-6s      %c %-11d %c\n", 186, a + 1, 186, player[a].name, 186, player[a].gender, 186, player[a].highScore, 186);

	printf("%c%c%c%c%c%c", 200, 205, 205, 205, 205, 202);
	for(int a=0;a<20;a++)
		printf("%c", 205);
	printf("%c", 202);
	for(int a=0;a<13;a++)
		printf("%c", 205);
	printf("%c", 202);
	for(int a=0;a<13;a++)
		printf("%c", 205);
	printf("%c", 188);
	getchar();
	fflush(stdin);
}

void processRawData(char rawData[][50], Player player[], int numOfPlayers){
	for(int a=0;a<numOfPlayers;a++){
		strcpy(player[a].name, strtok(rawData[a], "#"));
		strcpy(player[a].gender, strtok(NULL, "#"));
		player[a].highScore = atoi(strtok(NULL, "\n"));
	}
	player[numOfPlayers].highScore = 0;
}
