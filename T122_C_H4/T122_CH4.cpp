#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct data{
	char title[25];
	char director[25];
	int year;
} Movie;

void clear();
void printMovieList(Movie movie[], int numOfMovie);
void printMenuList();
void swapMovie(Movie *x, Movie *y);
void bubbleSort(Movie movie[], int numOfMovie, int sortBy);
void putMovieList(Movie movie[], int numOfMovie);
int getMovieFile(Movie movie[]);

int main(){
	char title[25];
	char director[25];
	int year;
	int numOfMovie;
	int choice;

	Movie movie[1000];

	numOfMovie = getMovieFile(movie);

	do{
		clear();

		printMovieList(movie, numOfMovie);
		printf("\n\n");

		printMenuList();

		printf("Choose : ");
		scanf("%d", &choice);
		fflush(stdin);

		switch(choice){
			case 1:
				int valid;

				clear();
				do{
					printf("Input title movie[3..20]: ");
					gets(title);
					fflush(stdin);
				}while(strlen(title) < 3 || strlen(title) > 20) ;
				do{
					printf("Input created year[1990..2020]: ");
					valid = scanf("%d", &year);
					if(getchar() != '\n' || year < 1990 || year > 2020 || year % 3 != 0 || year % 2 == 0){
						printf("Created Year must be odd number that can be divided by 3!\n");
						valid = 0;
					}
					fflush(stdin);
				}while(!valid);
				while(true){
					printf("Input directed by[5..20]|min 1 spaces: ");
					gets(director);
					fflush(stdin);
					valid = 0;
					for(int a=0, len=strlen(director);a<len;a++){
						if(director[a] == ' '){
							valid = 1;
						}else if(!isalpha(director[a])){
							valid = 0;
							break;
						}
					}
					if(valid) break;
				}
				clear();
				printf("==============\n");
				printf("|Insert Movie|\n");
				printf("==============\n\n");
				printf("Title\t\t: %s\n", title);
				printf("Created At\t: %d\n", year);
				printf("Directed By\t: %s\n", director);
				printf("Insert Success");
				getchar();
				fflush(stdin);
				strcpy(movie[numOfMovie].title, title);
				strcpy(movie[numOfMovie].director, director);
				movie[numOfMovie++].year = year;
				break;
			case 2:
				int movieNumber;

				clear();
				printMovieList(movie, numOfMovie);
				printf("\n\n");
				printf("Input number of movie: ");
				scanf("%d", &movieNumber);
				fflush(stdin);
				clear();
				if(movieNumber < 1 || movieNumber > numOfMovie){
					printf("Number is invalid");
					getchar();
					fflush(stdin);
				}else{
					printf("Title\t\t: %s\n", movie[movieNumber - 1].title);
					printf("Created At\t: %d\n", movie[movieNumber - 1].year);
					printf("Directed By\t: %s\n\n", movie[movieNumber - 1].director);
					printf("This movie has been deleted");
					for(int a=movieNumber-1;a<numOfMovie - 1;a++) //loop the to-be deleted movie to the last movie before deletion to keep the order after deletion, or if we dont care about the order, we could swap it directly to the last movie and delete it
						swapMovie(&movie[a], &movie[a + 1]);
					numOfMovie--; //"delete" the last movie
					getchar();
					fflush(stdin);
				}
				break;
			case 3:
				char keyword[25];
				int found;

				clear();
				printf("Input title movie to search: ");
				gets(keyword);
				clear();
				found = 0;
				if(strlen(keyword) != 0){
					for(int a=0;a<numOfMovie;a++){
						if(strstr(movie[a].title, keyword)){
							found = 1;
							printf("Title\t\t: %s\n", movie[a].title);
							printf("Created At\t: %d\n", movie[a].year);
							printf("Directed By\t: %s\n", movie[a].director);
							printf("----------------------------------------------------\n\n");
						}
					}
				}
				if(!found) printf("Title is not found");
				getchar();
				fflush(stdin);
				break;
			case 4:
				int sortBy;

				clear();
				do{
					printf("Sort Movie\n");
					printf("==========\n\n");
					printf("1. Sort Title by Ascending\n");
					printf("2. Sort Title by Descending\n");
					printf("3. Sort Created At by Ascending\n");
					printf("4. Sort Created At by Descending\n");
					printf("5. Cancel\n");
					printf("Choose : ");
					scanf("%d", &sortBy);
					if(sortBy > 0 && sortBy < 5){
						bubbleSort(movie, numOfMovie, sortBy);
						break;
					}
				}while(sortBy != 5);
				break;
			case 5:
				putMovieList(movie, numOfMovie);
				clear();
				printf("Thanks for using this program %c", 1);
				getchar();
				fflush(stdin);
				return 0;
				break;
		}
	}while(choice != 5);
}

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

void printMovieList(Movie movie[], int numOfMovie){
	if(numOfMovie == 0){
		printf("There are no movie inserted\n");
		return;
	}
	printf("==================================================================\n");
	printf("| No. | Title                | Created At | Directed By          |\n");
	printf("==================================================================\n");
	for(int a=0;a<numOfMovie;a++)
		printf("| %-3d | %-20s | %-10d | %-20s |\n", a + 1, movie[a].title, movie[a].year, movie[a].director);
	printf("==================================================================\n");
}

void printMenuList(){
	printf("Movie Media\n");
	printf("===========\n\n");
	printf("1. Insert Movie\n");
	printf("2. Delete Movie\n");
	printf("3. Search Movie\n");
	printf("4. Sort Movie\n");
	printf("5. Exit\n");
}

int getMovieFile(Movie movie[]){
	char buffer[255];
	int idx = 0;
	FILE *in = fopen("movielist.txt", "r");

	if(!in){
		perror("Error opening file");
		exit(0);
	}else{
		while(true){
			fgets(buffer, 100, in);
			if(feof(in)) break;
			strcpy(movie[idx].title, strtok(buffer, "#"));
			movie[idx].year = atoi(strtok(NULL, "#"));
			strcpy(movie[idx++].director, strtok(NULL, "\n"));
		}
	}
	return idx;
}

void putMovieList(Movie movie[], int numOfMovie){
	FILE *out = fopen("movielist.txt", "w");
	for(int a=0;a<numOfMovie;a++)
		fprintf(out, "%s#%d#%s\n", movie[a].title, movie[a].year, movie[a].director);
	fclose(out);
}

void swapMovie(Movie *x, Movie *y){
	char temp[25];

	strcpy(temp, x->title);
	strcpy(x->title, y->title);
	strcpy(y->title, temp);

	strcpy(temp, x->director);
	strcpy(x->director, y->director);
	strcpy(y->director, temp);

	x->year = x->year + y->year;
	y->year = x->year - y->year;
	x->year = x->year - y->year;
}

void bubbleSort(Movie movie[], int numOfMovie, int sortBy){
	int swapped = 1;
	while(swapped){
		swapped = 0;
		for(int a=0;a<numOfMovie - 1;a++){
			switch(sortBy){
				case 1:
					if(strcmp(movie[a].title, movie[a + 1].title) > 0){
						swapMovie(&movie[a], &movie[a + 1]);
						swapped = 1;
					}
					break;
				case 2:
					if(strcmp(movie[a].title, movie[a + 1].title) < 0){
						swapMovie(&movie[a], &movie[a + 1]);
						swapped = 1;
					}
					break;
				case 3:
					if(movie[a].year > movie[a + 1].year){
						swapMovie(&movie[a], &movie[a + 1]);
						swapped = 1;
					}
					break;
				case 4:
					if(movie[a].year < movie[a + 1].year){
						swapMovie(&movie[a], &movie[a + 1]);
						swapped = 1;
					}
					break;
			}
		}
	}
}
