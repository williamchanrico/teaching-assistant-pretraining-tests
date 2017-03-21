#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>
#include <conio.h>
#include <time.h>

#define WALL -1
#define FOOD -2
#define UP 119
#define DOWN 115
#define LEFT 97
#define RIGHT 100

int mapWidth = 140;
int mapHeight = 40;
int mapSize = mapWidth * mapHeight;
int map[5600];
int snakeHeadX;
int snakeHeadY;
int direction;
int snakeLength;
int gameIsRunning;

struct Node{
	char username[25];
	char password[25];
	char type[10];
	int score;
	struct Node *left;
	struct Node *right;
	struct Node *parent;
};

struct Queue{
	struct Node *node;
	struct Queue *next;
} *headQueue = NULL, *tailQueue = NULL;

struct Node *leftMost(struct Node *root);
int search(struct Node *root, char username[]);
int searchLogin(struct Node *root, char username[], char password[]);
int updateScore(struct Node **root, char username[], int score);
int startGame();
int isQueueEmpty();
void popQueue();
void pushQueue(struct Node *node);
void getData(struct Node **root);
void updateData(struct Node *root, FILE *out);
void deleteNode(struct Node **root, char username[]);
void push(struct Node **root, char username[], char password[], int score, char type[], struct Node *parent);
void printScore(struct Node *root);
void printTitle();
void clear();
void constructMap();
void getFood();
void printMap();
void moveSnake(int x, int y);
void updatePosition();
void changeDirection(char keypress);
char mapObjectAt(int x);

int main(){
	struct Node *BST = NULL;
	char username[25];
	char password[25];
	int score = -1;
	int foundCode;
	int choice;

	
	system("mode con: cols=150 lines=50");
	system("cls");

	printTitle();

	getData(&BST);

	clear();
	while(true){
		printf("Input  your Username: ");
		gets(username);
		printf("Input your Password: ");
		gets(password);
		foundCode = searchLogin(BST, username, password);
		if(!foundCode)
			printf("Invalid username/password\n\n\n");
		else
			break;
	}
	if(foundCode == 1){

		do{
			clear();
			printf("Welcome, %s\n", username);
			printf("================================\n");
			printf("1. Play Snake\n");
			printf("2. View Score\n");
			printf("3. Save and Exit\n");
			printf("Choose: ");
			scanf("%d", &choice);
			fflush(stdin);
			switch(choice){
				case 1:
					score = startGame();
					if(updateScore(&BST, username, score) == 1){
						printf("\n\t\tNew Highscore!");
					}
					getchar();
					fflush(stdin);
					break;
				case 2:
					clear();
					printScore(BST);
					getchar();
					fflush(stdin);
					break;
				case 3:
					FILE *out = fopen("user.txt", "w");
					updateData(BST, out);
					fclose(out);

					getchar();
					fflush(stdin);
					break;
			}
		}while(choice != 3);

	}else if(foundCode == 5){
		do{
			int input;

			clear();
			printf("Welcome, %s\n", username);
			printf("================================\n");
			printf("1. Play Snake\n");
			printf("2. View Score\n");
			printf("3. Master User\n");
			printf("4. Save and Exit\n");
			printf("Choose: ");
			scanf("%d", &choice);
			fflush(stdin);
			switch(choice){
				case 1:
					score = startGame();
					if(updateScore(&BST, username, score) == 1){
						printf("\n\t\tNew Highscore!");
					}
					getchar();
					fflush(stdin);
					break;
				case 2:
					clear();
					printScore(BST);
					getchar();
					fflush(stdin);
					break;
				case 3:
					do{
						clear();
						printScore(BST);
						printf("\n\n");
						printf("MASTER USER\n");
						printf("===========\n");
						printf("1. Insert New User\n");
						printf("2. Delete User\n");
						printf("3. Back\n");
						printf("Choose: ");
						scanf("%d", &input);
						fflush(stdin);

						if(input == 1){
							char inputUsername[25];
							char inputPassword[25];
							char inputType[25];

							while(true){
								printf("Input Username [3..20]: ");
								gets(inputUsername);
								fflush(stdin);
								if(strlen(inputUsername) < 3 || strlen(inputUsername) > 20){
									continue;
								}else if(search(BST, inputUsername)){
									printf("The inputted username already exist");
									getchar();
									fflush(stdin);
									continue;
								}
								break;
							}
							while(true){
								printf("Input Password [substring of username]: ");
								gets(inputPassword);
								fflush(stdin);
								if(strstr(inputUsername, inputPassword) == NULL){
									printf("Password must be a substring of the inputted username");
									getchar();
									fflush(stdin);
									continue;
								}
								break;
							}
							do{
								printf("Input Type [admin|member]: ");
								gets(inputType);
								fflush(stdin);
							}while(strcmp(inputType, "admin") != 0 && strcmp(inputType, "member") != 0);
							push(&BST, inputUsername, inputPassword, 0, inputType, NULL);
							printf("\nNew User Inserted Successfully\n");
							getchar();
							fflush(stdin);
						}else if(input == 2){
							char inputUsername[25];

							printf("Input Username to be deleted: ");
							gets(inputUsername);
							fflush(stdin);
							if(strcmp(username, inputUsername) == 0){
								printf("You can\'t delete yourself");
							}else if(search(BST, inputUsername)){
								deleteNode(&BST, inputUsername);
								printf("\nUser \"%s\" has been deleted", inputUsername);
							}
							getchar();
							fflush(stdin);
						}
					}while(input != 3);
					break;
				case 4:
					FILE *out = fopen("user.txt", "w");
					updateData(BST, out);
					fclose(out);

					getchar();
					fflush(stdin);
					break;
			}
		}while(choice != 4);

	}
}

void pushQueue(struct Node *node){
	struct Queue *temp = (struct Queue *) malloc(sizeof(struct Queue));
	
	temp->node = node;

	if(headQueue == NULL){
		headQueue = tailQueue = temp;
		headQueue->next = tailQueue;
	}else{
		tailQueue->next = temp;
		tailQueue = temp;
	}
	tailQueue->next = NULL;
}

void popQueue(){
	if(headQueue != NULL){
		struct Queue *temp = headQueue;

		headQueue = headQueue->next;
		free(temp);
	}
}

int isQueueEmpty(){
	if(headQueue == NULL)
		return 1;
	return 0;
}

void printScore(struct Node *root){
	int space = 0;
	int start = 1;

	if(root != NULL){
		struct Node *delimiter = (struct Node *) malloc(sizeof(struct Node));

		delimiter->score = -1;
		pushQueue(root);
		pushQueue(delimiter);
		while(!isQueueEmpty() && !(headQueue == tailQueue && headQueue->node == delimiter)){
			struct Queue *temp = headQueue;

			if(temp->node->score == -1){
				start = 1;
				for(int a=0;a<space;a++)
					printf("    ");
				printf("|\n");
				for(int a=0;a<space;a++)
					printf("    ");
				printf("%c%c%c%c", 200, 196, 196, 196);
				space++;
				pushQueue(delimiter);
			}else{
				if(!start){
					for(int a=0;a<space;a++)
						printf("    ");
				}
				start = 0;
				printf("%-20s | %3d\n", temp->node->username, temp->node->score);
				if(temp->node->right != NULL) pushQueue(temp->node->right);
				if(temp->node->left != NULL) pushQueue(temp->node->left);
			}
		
			popQueue();
		}
		free(delimiter);
		popQueue();
	}

}

void clear(){
	for(int a=0;a<50;a++)
		printf("\n");
}

void printTitle(){
	char titleSplash[10][150];

	strcpy(titleSplash[0], "                           _____ _   _          _  ________   __  __           _____ _______ ______ _____");
	strcpy(titleSplash[1], "                          / ____| \\ | |   /\\   | |/ /  ____| |  \\/  |   /\\    / ____|__   __|  ____|  __ \\");
	strcpy(titleSplash[2], "                         | (___ |  \\| |  /  \\  | ' /| |__    | \\  / |  /  \\  | (___    | |  | |__  | |__) |");
	strcpy(titleSplash[3], "                          \\___ \\| . ` | / /\\ \\ |  < |  __|   | |\\/| | / /\\ \\  \\___ \\   | |  |  __| |  _  /");
	strcpy(titleSplash[4], "                          ____) | |\\  |/ ____ \\| . \\| |____  | |  | |/ ____ \\ ____) |  | |  | |____| | \\ \\");
	strcpy(titleSplash[5], "                         |_____/|_| \\_/_/    \\_\\_|\\_\\______| |_|  |_/_/    \\_\\_____/   |_|  |______|_|  \\_\\");
	for(int a=0;a<5;a++)
		printf("\n");
	for(int a=0;a<6;a++){
		for(int b=0;b<strlen(titleSplash[a]);b++){
			printf("%c", titleSplash[a][b]);
			Sleep(3);
		}
		printf("\n");
	}
	getchar();
	fflush(stdin);
}

void push(struct Node **root, char username[], char password[], int score, char type[], struct Node *parent){
	if(*root == NULL){
		(*root) = (struct Node *) malloc(sizeof(struct Node));
		strcpy((*root)->username, username);
		strcpy((*root)->password, password);
		strcpy((*root)->type, type);
		(*root)->parent = parent;
		(*root)->score = score;
		(*root)->left = (*root)->right = NULL;
	}else{
		if(strcmp(username, (*root)->username) < 0){
			push(&(*root)->left, username, password, score, type, *root);
		}else if(strcmp(username, (*root)->username) > 0){
			push(&(*root)->right, username, password, score, type, *root);
		}else{
			printf("User already exists.");
			getchar();
			fflush(stdin);
		}
	}
}

struct Node *leftMost(struct Node *root){
	if(root->left == NULL)
		return root;
	return root->left;
}

void deleteNode(struct Node **root, char username[]){
	if(*(root) == NULL){
		return;
	}
	if(strcmp(username, (*root)->username) < 0){
		deleteNode(&(*root)->left, username);
	}else if(strcmp(username, (*root)->username) > 0){
		deleteNode(&(*root)->right, username);
	}else{
		if((*root)->left == NULL && (*root)->right == NULL){

			struct Node *temp = (*root);
			if((*root)->parent != NULL){
				if((*root)->parent->left == (*root)){
					(*root)->parent->left = NULL;
				}else{
					(*root)->parent->right = NULL;
				}
			}
			*root = NULL;
			free(temp);

		}else if((*root)->left != NULL && (*root)->right != NULL){

			struct Node *temp = leftMost((*root)->right);
			
			strcpy((*root)->username, temp->username);
			strcpy((*root)->password, temp->password);
			strcpy((*root)->type, temp->type);
			(*root)->score = temp->score;
			deleteNode(&temp, temp->username);

		}else{
			struct Node *temp = ((*root)->left == NULL? (*root)->right : (*root)->left);
			struct Node *temp2 = (*root);
			temp->parent = (*root)->parent;
			
			if((*root)->parent->left == (*root)){
				((*root)->parent)->left = temp;
			}else{
				((*root)->parent)->right = temp;
			}
			free(temp2);
		}

	}
}

void getData(struct Node **root){
	FILE *in = fopen("user.txt", "r");
	char username[25];
	char password[25];
	char type[10];
	int score;
	while(fscanf(in, "%[^'|']|%[^'|']|%d|%s\n", username, password, &score, type) != EOF){
		push(root, username, password, score, type, NULL);
	}
	fclose(in);
}

int searchLogin(struct Node *root, char username[], char password[]){
	int val;

	if(root == NULL) return 0;

	val = strcmp(username, root->username);

	if(val == 0){
		if(strcmp(root->password, password) == 0)
			if(strcmp(root->type, "admin") == 0)
				return 5;
			else
				return 1;
		return 0;
	}else if(val < 0){
		return searchLogin(root->left, username, password);
	}else{
		return searchLogin(root->right, username, password);
	}
}

int search(struct Node *root, char username[]){
	int val;

	if(root == NULL) return 0;

	val = strcmp(root->username, username);

	if(val == 0){
		return 1;
	}else if(val < 0){
		return search(root->right, username);
	}else{
		return search(root->left, username);
	}
}

int updateScore(struct Node **root, char username[], int score){
	int val;

	if((*root) != NULL){
		val = strcmp((*root)->username, username);
		if(val == 0){
			if((*root)->score < score){
				(*root)->score = score;
				return 1;
			}
		}else if(val < 0){
			return updateScore(&(*root)->right, username, score);
		}else{
			return updateScore(&(*root)->left, username, score);
		}
	}
	return 0;
}

void updateData(struct Node *root, FILE *out){
	if(root != NULL){
		fprintf(out, "%s|%s|%d|%s\n", root->username, root->password, root->score, root->type);
		updateData(root->left, out);
		updateData(root->right, out);
	}
}

int startGame(){
	HANDLE hOutput = GetStdHandle(STD_OUTPUT_HANDLE);
	
	system("cls");
	constructMap();
	gameIsRunning = 1;
	while(gameIsRunning){
		if(kbhit())
			changeDirection(getch());
		updatePosition();
		if(!gameIsRunning) break;
		
		COORD coord = {0, 0};
		SetConsoleCursorPosition(hOutput, coord);
		
		printMap();
		//Sleep(0);
	}
	printf("\n\t\tGAME OVER");
	return snakeLength - 2;
}

void constructMap(){
	snakeLength = 2;
	direction = RIGHT;
	snakeHeadX = 20;
	snakeHeadY = 20;

	for(int a=0;a<mapSize;a++)
		map[a] = 0;
	for(int a=0;a<mapHeight;a++)
		map[a] = map[a + ((mapWidth - 1) * mapHeight)] = WALL;
	for(int a=0;a<mapWidth;a++)
		map[a * mapHeight] = map[(mapHeight - 1) + (a * mapHeight)] = WALL;
	getFood();
}

void updatePosition(){
	switch(direction){
		case UP:
			moveSnake(-1, 0);
			break;
		case RIGHT:
			moveSnake(0, 1);
			break;
		case DOWN:
			moveSnake(1, 0);
			break;
		case LEFT:
			moveSnake(0, -1);
			break;
	}
	for(int a=0;a<mapSize;a++)
		if(map[a] > 0)
			map[a]--;
}

void moveSnake(int x, int y){
	int newPosX = snakeHeadX + x;
	int newPosY = snakeHeadY + y;

	if(map[newPosX + newPosY * mapHeight] == FOOD){
		snakeLength++;
		getFood();
	}else if(map[newPosX + newPosY * mapHeight] != 0){
		gameIsRunning = 0;
	}
	snakeHeadX = newPosX;
	snakeHeadY = newPosY;
	map[snakeHeadX + snakeHeadY * mapHeight] = snakeLength + 1;
}

void getFood(){
	int xPos = 0;
	int yPos = 0;

	do{
		srand(time(NULL));
		xPos = (rand() % (mapHeight - 2)) + 1;
		yPos = (rand() % (mapWidth - 2)) + 1;
	}while(map[xPos + yPos * mapHeight] != 0);

	map[xPos + yPos * mapHeight] = FOOD;
}

void changeDirection(char keypress){
	   switch(keypress) {
			case UP:
		        if(direction != DOWN)
		        	direction = UP;
		        break;
		    case RIGHT:
		        if(direction != LEFT)
		        	direction = RIGHT;
		        break;
		    case DOWN:
		        if(direction != UP)
		        	direction = DOWN;
		        break;
		    case LEFT:
		        if(direction != RIGHT)
		        	direction = LEFT;
		        break;
	    }
}

char mapObjectAt(int x){
	if(x > 0) return 176;
	else if(x == WALL) return 177;
	else if(x == FOOD) return '*';
	return ' ';
}

void printMap(){
	printf("\n\n\n");
	for(int a=0;a<mapHeight;a++){
		printf("     ");
		for(int b=0;b<mapWidth;b++)
			printf("%c", mapObjectAt(map[a + b * mapHeight]));
		printf("\n");
	}

	printf("\n\n\t\tSCORE : %d", snakeLength - 2);
}
