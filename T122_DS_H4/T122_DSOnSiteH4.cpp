#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Node{
	char title[50];
	char genre[50];
	int pages;

	struct Node *left;
	struct Node *right;
	struct Node *parent;
};

int pushNode(struct Node **root, char title[], char genre[], int pages, struct Node *parent){
	if((*root) == NULL){
		(*root) = (struct Node *) malloc(sizeof(struct Node));
		strcpy((*root)->title, title);
		strcpy((*root)->genre, genre);
		(*root)->pages = pages;
		(*root)->parent = parent;
		(*root)->left = NULL;
		(*root)->right = NULL;
		return 1;
	}else{
		if(strcmp(title, (*root)->title) < 0)
			pushNode(&(*root)->left, title, genre, pages, (*root));
		else if(strcmp(title, (*root)->title) > 0)
			pushNode(&(*root)->right, title, genre, pages, (*root));
		else
			return 0;
	}
	return 1;
}

void updateNode(struct Node **root, char title[], char newTitle[], char newGenre[], int newPages){
	int val;

	if((*root) != NULL){
		val = strcmp(title, (*root)->title);
		if(val == 0){
			strcpy((*root)->title, newTitle);
			strcpy((*root)->genre, newGenre);
			(*root)->pages = newPages;
		}else if(val < 0){
			updateNode(&(*root)->left, title, newTitle, newGenre, newPages);
		}else{
			updateNode(&(*root)->right, title, newTitle, newGenre, newPages);
		}
	}
}

int findNode(struct Node *root, char title[]){
	int val;

	if(root != NULL){
		val = strcmp(title, root->title);
		if(val == 0) return 1;
		else if(val < 0) return findNode(root->left, title);
		else return findNode(root->right, title);
	}
	return 0;
}

int viewBST(struct Node *root){
	if(root != NULL){
		viewBST(root->left);
		printf("Title : %s\n", root->title);
		printf("Genre : %s\n", root->genre);
		printf("Pages : %d\n\n", root->pages);
		viewBST(root->right);
		return 1;
	}
	return 0;
}

struct Node *findLeftMost(struct Node *root){
	if(root != NULL){
		if(root->left != NULL)
			return findLeftMost(root->left);
		return root;
	}
}

void deleteNode(struct Node **root, char title[]){
	int val;

	if((*root) != NULL){

		val = strcmp(title, (*root)->title);

		if(val == 0){
			if((*root)->left == NULL && (*root)->right == NULL){
				if((*root)->parent != NULL){

					if((*root)->parent->left == (*root)){

						(*root)->parent->left = NULL;

					}else if((*root)->parent->right == (*root)){

						(*root)->parent->right = NULL;

					}
				
				}
				
				free((*root));

			}else if((*root)->left != NULL && (*root)->right != NULL){
				struct Node *temp = findLeftMost((*root)->right);
				(*root) = temp;
				deleteNode(&temp, temp->title);
			}else{
				struct Node *temp;

				if((*root)->left != NULL)
					temp = (*root)->left;
				else
					temp = (*root)->right;

				if((*root)->parent == NULL){
					struct Node *temp2 = (*root);
					(*root) = temp;
					(*root)->parent = NULL;
					free(temp2);
				}else if((*root)->parent->left == (*root)){
					temp->parent = (*root)->parent;
					(*root)->parent->left = temp;
					free(*root);
				}else if((*root)->parent->right == (*root)){
					temp->parent= (*root)->parent;
					(*root)->parent->right = temp;
					free(*root);
				}
			}
		}else if(val < 0){
			deleteNode(&(*root)->left, title);
		}else{
			deleteNode(&(*root)->right, title);
		}
	}
}

void clear(){
	for(int a=0;a<25;a++)
		printf("\n");
}

void showOpeningSplash(){
	clear();
	printf("__________       .__\n");
	printf("\\______   \\ ____ |__|\n");
	printf(" |       _// __ \\|  |\n");
	printf(" |    |   \\  ___/|  |\n");
	printf(" |____|_  /\\___  >__|\n");
	printf("        \\/     \\/\n");
	printf("Welcome to REIDB v1.00\n");
	getchar();
	fflush(stdin);
}

void printMainMenu(){
	printf("1. View books based on authorn\n");
	printf("2. Insert books based on author\n");
	printf("3. Update book\n");
	printf("4. Delete book\n");
	printf("5. Exit\n");
	printf("Choice: ");
}

void printAuthorList(){
	clear();
	printf("1. Ivan Rei\n");
	printf("2. H. Eberly\n");
	printf("3. David S. Dummit\n");
	printf("4. S. Skienna\n");
	printf("5. Erin Catto\n");
	printf("Choose your author: ");
}

int main(){
	struct Node *BST[5];
	int choice;

	for(int a=0;a<5;a++)
		BST[a] = NULL;

	showOpeningSplash();

	do{
		clear();
		printMainMenu();
		scanf("%d", &choice);
		fflush(stdin);
		switch(choice){
			int input;
			int pages;
			char title[50];
			char newTitle[50];
			char genre[50];

			case 1:
				do{
					clear();
					printAuthorList();
					scanf("%d", &input);
					fflush(stdin);
				}while(input < 1 || input > 5);
				clear();
				if(!viewBST(BST[input - 1]))
					printf("There's no book for this author\n");
				getchar();
				fflush(stdin);
				break;
			case 2:
				do{
					clear();
					printAuthorList();
					scanf("%d", &input);
					fflush(stdin);
				}while(input < 1 || input > 5);
				do{
					clear();
					printf("New book title [5..40]: ");
					gets(title);
					fflush(stdin);
				}while(strlen(title) < 5 || strlen(title) > 40);
				do{
					clear();
					printf("Book genre [5..40]: ");
					gets(genre);
					fflush(stdin);
				}while(strlen(genre) < 5 || strlen(genre) > 40);
				do{
					clear();
					printf("Amount of pages [400..1000]: ");
					scanf("%d", &pages);
					if(getchar() != '\n'){
						fflush(stdin);
						continue;
					}
					fflush(stdin);
				}while(pages < 400 || pages > 1000);
				clear();
				if(pushNode(&BST[input - 1], title, genre, pages, NULL))
					printf("Successfully added book.\n");
				else
					printf("Book already exists.\n");
				getchar();
				fflush(stdin);
				break;
			case 3:
				do{
					clear();
					printAuthorList();
					scanf("%d", &input);
					fflush(stdin);
				}while(input < 1 || input > 5);
				clear();
				if(viewBST(BST[input - 1])){
					printf("Input name of the book you want to update: ");
					gets(title);
					if(findNode(BST[input - 1], title)){
						do{
							clear();
							printf("\nInput name of new title[5..40]: ");
							gets(newTitle);
							fflush(stdin);
						}while(strlen(newTitle) < 5 || strlen(newTitle) > 40);
						do{
							clear();
							printf("\nInput new genre [5..40]: ");
							gets(genre);
							fflush(stdin);
						}while(strlen(genre) < 5 || strlen(genre) > 40);
						do{
							clear();
							printf("\nInput new pages [400..1000]: ");
							scanf("%d", &pages);
							if(getchar() != '\n'){
								fflush(stdin);
								continue;
							}
							fflush(stdin);
						}while(pages < 400 || pages > 1000);
						clear();
						if(findNode(BST[input - 1], newTitle)){
							printf("The new title for the book already exist.\n");
						}else{
							updateNode(&BST[input - 1], title, newTitle, genre, pages);
							printf("Successfully updated book.\n");
						}
					}else{
						printf("\nNo book with title you specified.\n");
					}
				}else{
					printf("No book found for this author.\n");
				}
				getchar();
				fflush(stdin);
				break;
			case 4:
				do{
					clear();
					printAuthorList();
					scanf("%d", &input);
					fflush(stdin);
				}while(input < 1 || input > 5);
				clear();
				if(viewBST(BST[input - 1])){
					printf("Name of the book you want to delete: ");
					gets(title);
					fflush(stdin);
					if(findNode(BST[input - 1], title)){
						deleteNode(&BST[input - 1], title);
						printf("\nSuccessfully deleted book\n");
					}else{
						printf("\nBook not found.\n");
					}
				}else{
					printf("\nNo book found for this author.\n");
				}
				getchar();
				fflush(stdin);
				break;
			case 5:
				clear();
				break;
		}
	}while(choice != 5);
}