#include <stdio.h>
#include <time.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>



void printTitle(){
	printf("\n");
	printf(" =============================\n");
	printf(" =          BLUE OJEG        =\n");
	printf(" =============================\n");
	printf("\n\n");
}

void startMainMenu(char username[]){
	srand(time(NULL));
	int choice;

	do{
		system("cls");
		printTitle();
		printf(" 1. Order\n");
		printf(" 2. View Order\n");
		printf(" 3. Cancel Order\n");
		printf(" 4. Log Out\n\n");
		printf(" > ");
		scanf("%d", &choice);
		fflush(stdin);
		if(choice == 1){
			int input;
			do{
				system("cls");
				printTitle();
				
				printf(" 1. Transport\n");
				printf(" 2. Food Courier\n");
				printf(" 3. Back\n\n");
				printf(" > ");
				scanf("%d", &input);
				fflush(stdin);
				if(input == 1){
					system("cls");
					char destination[25];
					char street[25];

					printTitle();
					
					while(true){
						printf(" Your destination[start with 'Uppercase' Letter]: ");
						gets(destination);
						if(destination[0] != toupper(destination[0])){
							printf(" Destination place must start with uppercase letter!\n");
							continue;
						}
						break;
					}
					printf("\n");
					while(true){
						printf(" Your address[contains 'Street']: ");
						gets(street);
						if(strstr(street, "Street") == NULL){
							printf(" Address must contains 'Street'!\n");
							continue;
						}
						char *tok = strtok(street, " ");
						int counter = 0;
						while(tok != NULL){
							counter++;
							tok = strtok(NULL, " ");
						}
						if(counter < 2){
							printf(" Address must have a name!\n");
							continue;
						}
						break;
					}
					int orderCode = (rand() % 500) + 100;
					char fileName[25];

					strcpy(fileName, username);
					strcat(fileName, "_ojeg.txt");

					FILE *out = fopen(fileName, "a+");
					fprintf(out, "OJ%d@%s-%s\n", orderCode, destination, street);
					fclose(out);

					printf("\n Success to order Ojeg!\n");
					printf(" Your order code for this order is OJ%d\n", orderCode);
					getchar();
					fflush(stdin);
				}else if(input == 2){
					printf("Sorry not yet implemented\n");
					getchar();
					fflush(stdin);
				}else if(input != 3){
					printf(" Out of option");
					getchar();
					fflush(stdin);
				}
			}while(input != 3);
		}else if(choice == 2){
			printf("Sorry not yet implemented\n");
			getchar();
			fflush(stdin);
		}else if(choice == 3){
				printf("Sorry not yet implemented\n");
					getchar();
					fflush(stdin);
		}
	}while(choice != 4);
}

int main(){
	char currentUser[25];
	char username[200][55];
	char password[200][55];
	int numOfUser = 0;

	int choice = 1;
	do{
		if(choice < 1 || choice > 3){
			printf(" Out of option");
			getchar();
			fflush(stdin);
		}
		system("cls");

		printTitle();

		printf(" 1. Login\n");
		printf(" 2. Register\n");
		printf(" 3. Exit & Save\n\n");
		printf(" > ");
		scanf("%d", &choice);
		fflush(stdin);
		
		if(choice == 1){
			FILE *in = fopen("user.txt", "r+");

			while(fscanf(in, "%[^#]#%[^\n]\n", username[numOfUser], password[numOfUser]) != EOF){
				numOfUser++;
			}
			fclose(in);

			char inputUsername[25];
			char inputPassword[25];

			printf(" Username  :\n");
			printf("     >");
			gets(inputUsername);
			printf(" Password  :\n");
			printf("     >");
			gets(inputPassword);
			fflush(stdin);
			for(int a=0;a<numOfUser;a++){
				if(strstr(username[a], inputUsername) && strstr(password[a], inputPassword)){
					printf("\n Success to login");
					getchar();
					fflush(stdin);
					startMainMenu(inputUsername);
					break;
				}
				if(a == numOfUser - 1){
					printf("\n Username and password invalid");
					getchar();
					fflush(stdin);
				}
			}
		}else if(choice == 2){
			FILE *out = fopen("user.txt", "a+");
			char inputUsername[25];
			char inputPassword[25];
			int valid;

			while(true){
				printf(" Your username [min.8 | max 25 character]: ");
				gets(inputUsername);
				if(strlen(inputUsername) < 8 || strlen(inputUsername) > 25){
					printf("  Username must from 8 to 25 character!\n");
					continue;
				}
				break;
			}
			printf("\n Note: password must contain uppercase,lowercase, and number character.\n");
			while(true){
				int upper = 0;
				int lower = 0;
				int number = 0;
				
				printf(" Your password: ");
				gets(inputPassword);
				for(int a=0, len=strlen(inputPassword);a<len;a++){
					if(inputPassword[a] == toupper(inputPassword[a]) && !isdigit(inputPassword[a])) upper = 1;
					if(inputPassword[a] == tolower(inputPassword[a]) && !isdigit(inputPassword[a])) lower = 1;
					if(isdigit(inputPassword[a])) number = 1;
					if(upper && lower && number) break;
				}
				if(upper && lower && number){
					fprintf(out, "%s#%s\n", inputUsername, inputPassword);
					fclose(out);
					printf("\n Success to register. You can login %c", 1);
					getchar();
					fflush(stdin);
					break;
				}else{
					printf(" Password invalid!\n");
				}
			}
		}else if(choice == 3){
			system("cls");
			printf("im not fast enough, cant finish in time. So, thanks for using this software :)\n");
			//showExitSplash();
			getchar();
			fflush(stdin);
		}
	}while(choice != 3);
	return 0;
}
