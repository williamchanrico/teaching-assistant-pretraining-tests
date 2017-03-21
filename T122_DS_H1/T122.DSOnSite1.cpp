#include <stdio.h>
#include <string.h>
#include <stdlib.h>

struct Date{
	int day;
	int month;
	int year;
};

struct Song{
	char title[25];
	char artist[25];
	char album[25];
	char genre[10];
	struct Date  date;
	struct Song *next;
} *head = NULL, *tail = NULL, *temp = NULL;

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

int printSongList(){
	int idx = 1;

	temp = head;
	printf("|%2s| %-15s | %-15s | %-6s | %-15s | %-10s|\n", "No", "Title", "Artist", "Genre", "Album", "Published");
	while(temp != NULL){
		printf("|%2d| %-15s | %-15s | %-6s | %-15s | %02d-%02d-%04d|\n", idx++, temp->title, temp->artist, temp->genre, temp->album, temp->date.day, temp->date.month, temp->date.year);
		temp = temp->next;
	}
	return idx - 1;
}

void printMenu(){
	printf("Bluejack Playlist\n");
	printf("-----------------\n");
	printf("1. Insert song\n");
	printf("2. Play song\n");
	printf("3. Exit\n");
}

void popSearch(int deleteIdx){
	temp = head;
	for(int a=0;a<deleteIdx - 1;a++){
		temp = temp->next;
	}
	if(temp == head && temp == tail){
		free(temp);
		head = tail = temp = NULL;
	}else if(temp == head){
		head = head->next;
		free(temp);
	}else if(temp == tail){
		struct Song *temp2 = (struct Song *) malloc(sizeof(struct Song));
		temp2 = head;
		while(temp2->next != tail){
			temp2 = temp2->next;
		}
		free(tail);
		tail = temp2;
		tail->next = NULL;
	}else{
		struct Song *temp2 = head;
		for(int a=1;a<deleteIdx-1;a++)
			temp2 = temp2->next;
		temp2->next = temp->next;
		free(temp);
	}
}

void pushHead(char title[], char artist[], char album[], char genre[], struct Date date){
	temp = (struct Song*) malloc(sizeof(struct Song));
	strcpy(temp->title, title);
	strcpy(temp->artist, artist);
	strcpy(temp->album, album);
	strcpy(temp->genre, genre);
	temp->date.day = date.day;
	temp->date.month = date.month;
	temp->date.year = date.year;
	if(head == NULL){
		head = tail = temp;
	}else{
		temp->next = head;
		head = temp;
	}
	tail->next = NULL;
}

void pushTail(char title[], char artist[], char album[], char genre[], struct Date date){
	temp = (struct Song*) malloc(sizeof(struct Song));
	strcpy(temp->title, title);
	strcpy(temp->artist, artist);
	strcpy(temp->album, album);
	strcpy(temp->genre, genre);
	temp->date.day = date.day;
	temp->date.month = date.month;
	temp->date.year = date.year;
	if(head == NULL){
		head = tail = temp;
	}else{
		tail->next = temp;
		tail = temp;
	}
	tail->next = NULL;
}

void pushSort(char title[], char artist[], char album[], char genre[], struct Date date){
	// puts(title);
	// puts(artist);
	// puts(album);
	// puts(genre);
	// printf("%d - %d - %d\n", date.day, date.month, date.year);

	if(head == NULL || strcmp(title, head->title) < 0){
		pushHead(title, artist, album, genre, date);
	}else if(strcmp(title, tail->title) > 0){
		pushTail(title, artist, album, genre, date);
	}else{
		temp = head;
		while(strcmp(title, temp->next->title) > 0){
			temp = temp->next;
		}

		struct Song *temp2 = (struct Song *) malloc(sizeof(struct Song));

		strcpy(temp2->title, title);
		strcpy(temp2->artist, artist);
		strcpy(temp2->album, album);
		strcpy(temp2->genre, genre);
		temp2->date.day = date.day;
		temp2->date.month = date.month;
		temp2->date.year = date.year;

		temp2->next = temp->next;
		temp->next = temp2;
	}
}

int main(){
	int choice;

	do{
		clear();
		printMenu();
		printf(">> ");
		scanf("%d", &choice);
		fflush(stdin);
		switch(choice){
			int valid;
			int total;
			int input;
			char title[25];
			char artist[25];
			char album[25];
			char genre[10];
			struct Date date;

			case 1:
				clear();
				valid = 1;
				do{
					printf("Insert title song [5-20]: ");
					gets(title);
				}while(!valid);
				valid = 1;
				do{
					printf("Insert artist [first letter is capital]: ");
					gets(artist);
				}while(!valid);
				valid = 1;
				do{
					printf("Insert album [min 2 words]: ");
					gets(album);
				}while(!valid);
				valid = 1;
				do{
					printf("Insert genre [Pop|Rock|Jazz|Blues]: ");
					gets(genre);
				}while(!valid);
				valid = 1;
				do{
					printf("Insert date [dd-mm-yyy]: ");
					scanf("%d-%d-%d", &date.day, &date.month, &date.year);
					fflush(stdin);
				}while(!valid);
				pushSort(title, artist, album, genre, date);
				printf("Input successful!");
				getchar();
				fflush(stdin);
				break;
			case 2:
				clear();
				total = printSongList();
				printf("\n");
				if(total == 0){
					printf("There is no song in your list.");
					getchar();
					fflush(stdin);
					break;
				}
				printf("Input song number [1-%d] | 0 to exit: ", total);
				scanf("%d", &input);
				fflush(stdin);
				if(input != 0){
					popSearch(input);
					printf("Song has been played.");
					getchar();
					fflush(stdin);
				}
				break;
			case 3:
				clear();
				printf("Thank you for using this application %c\n", 1);
				printf("\"Keep Fighting and Share Our Greatest Skills\"");
				getchar();
				fflush(stdin);
				break;
		}
	}while(choice != 3);
}