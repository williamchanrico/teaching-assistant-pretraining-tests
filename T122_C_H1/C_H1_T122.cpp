#include <stdio.h>
#include <stdlib.h>

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

void showTitle(){
	clear();
	printf("                                 _                          _\n");
	printf("       _ _ ___ _ __  __ ___ __ _(_)  __ ___ _ ___ _____ _ _| |_ ___ _ _\n");
	printf("      | '_/ _ \\ '  \\/ _` \\ V  V / | / _/ _ \\ ' \\ V / -_) '_|  _/ -_) '_|\n");
	printf("      |_| \\___/_|_|_\\__,_|\\_/\\_/|_| \\__\\___/_||_\\_/\\___|_|  \\__\\___|_|\n");
	for(int a=0;a<19;a++)
		printf("\n");
}

int inputData(){
	int input;
	int valid;

	do{
		printf("Input Integer [1-1000]: ");
		valid = scanf("%d", &input);
		if(getchar() != '\n' || (input < 1 || input > 1000))
			valid = 0;
		fflush(stdin);
	}while(!valid);
	return input;
}

void *convertToRomanian(int x, char *rom){
	int idx = 0;
	
	while(x > 0){
		if(x >= 1000){
			*(rom + idx++) = 'M';
			x -= 1000;
		}else if(x >= 900){
			*(rom + idx++) = 'C';
			*(rom + idx++) = 'M';
			x -= 900;
		}else if(x >= 500){
			*(rom + idx++) = 'D';
			x -= 500;
		}else if(x >= 400){
			*(rom + idx++) = 'C';
			*(rom + idx++) = 'D';
			x -= 400;
		}else if(x >= 100){
			*(rom + idx++) = 'C';
			x -= 100;
		}else if(x >= 90){
			*(rom + idx++) = 'X';
			*(rom + idx++) = 'C';
			x -= 90;
		}else if(x >= 50){
			*(rom + idx++) = 'L';
			x -= 50;
		}else if(x >= 40){
			*(rom + idx++) = 'X';
			*(rom + idx++) = 'L';
			x -= 40;
		}else if(x >= 10){
			*(rom + idx++) = 'X';
			x -= 10;
		}else if(x == 9){
			*(rom + idx++) = 'I';
			*(rom + idx++) = 'X';
			x -= 9;
		}else if(x >= 5){
			*(rom + idx++) = 'V';
			x -= 5;
		}else if(x == 4){
			*(rom + idx++) = 'I';
			*(rom + idx++) = 'V';
			x -= 4;
		}else if(x >= 1){
			*(rom + idx++) = 'I';
			x -= 1;
		}
	}
	*(rom + idx) = '\0'; 
	return rom;
}

int main(){
	char *rom;

	rom = (char*) malloc(100 * sizeof(char));
	showTitle();
	printf("Romawi : %s", convertToRomanian(inputData(), rom));
	free(rom);
	getchar();
	return 0;
}
