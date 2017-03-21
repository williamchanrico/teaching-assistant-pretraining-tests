import java.util.*;

class T122_JavaI_H3OnSite{

	static Scanner sc = new Scanner(System.in);
	public static Player player1;
	public static Player player2;
	public static Board board;
	public static int turn;

	public static void main(String[] args){

		int choice;

		do{
			clear(0);
			choice = showOpeningMenu();

			switch(choice){

				case 1:
					clear(0);

					player1 = new Player();
					player2 = new Player();

					player1.name = inputPlayerName(1);

					player2.name = inputPlayerName(2);

					System.out.println("\n Press ENTER to start the game..");
					sc.nextLine();

					startGame();

					break;
				case 2:
					showHowToPlay();
					break;
				case 3:
					showExitSplash();
					break;
			}

		}while(choice != 3);
	}

	public static void prepareNewGame(){
		board = new Board();
		player1.score = 0;
		player2.score = 0;
		turn = 1;
	}

	public static void startGame(){

		prepareNewGame();

		while(true){

			clear(0);
			printBoard(0);

			if(playerBoardEmpty(turn)){
				turn = (turn == 1 ? 2 : 1);
			}

			System.out.printf(" Player %s turns!\n", (turn == 1 ? player1.name : player2.name));

			int input = inputPitsChoice();

			if(input == 0) break;

			int steps = board.grid[idxOfPlayerBoard(turn, input)];

			board.grid[idxOfPlayerBoard(turn, input)] = 0;

			turn = run(steps, (idxOfPlayerBoard(turn, input) + 1) % 10);





			if(turn == 0){
				String inputExit;

				clear(0);

				System.out.printf("\t\t\t\t%s Win !\n", (player1.score > player2.score ? player1.name : player2.name));
				System.out.printf("\t\t\t\tScore : %d\n", (player1.score > player2.score ? player1.score : player2.score));

				for(int a=0;a<12;a++)
					System.out.print("\n");

				do{

					System.out.print("Do you want to restart the game? [ y | n | exit ] : ");
					inputExit = sc.nextLine();

				}while(!inputExit.equals("y") && !inputExit.equals("n") && !inputExit.equals("exit"));


				if(inputExit.equals("exit")){

					showExitSplash();

					System.exit(0);

				}

				if(inputExit.equals("y"))
					prepareNewGame();
				else
					return;

			}
		}

	}

	public static int run(int steps, int idx){

		clear(0);

		printBoard(steps);

		if(player1.score + player2.score == 32){
			delay(1000);
			return 0;
		}

		if(steps < 1){
			if((idx == 0 ? 9 : idx - 1) == (turn == 1 ? 0 : 5)){
				System.out.printf("\n\tPlayer %d Turns!!\n\n", turn);
				System.out.println("\tPress ENTER to continue..");
				sc.nextLine();
				return turn;
			}else{
				System.out.printf("\n\tPlayer %d Turns!!\n\n", (turn == 1 ? 2 : 1));
				System.out.println("\tPress ENTER to continue..");
				sc.nextLine();
				return (turn == 1 ? 2 : 1);
			}
			// System.out.println("ERROR : THIS LINE SHOULD NOT BE PRINTED EVER");
			// sc.nextLine();
			// Never mind, java's compiler ROCK ! ! !
		}

		player1.score += (idx == 0 ? 1 : 0);
		player2.score += (idx == 5 ? 1 : 0);

		board.grid[idx]++;
		delay(800);

		return run(steps - 1, (idx + 1) % 10);

	}

	public static boolean playerBoardEmpty(int playerNumber){
		for(int a=1;a<=4;a++)
			if(idxOfPlayerBoard(playerNumber, a) != 0)
				return false;
		return true;
	}

	public static void printBoard(int steps){

		if(turn == 1){
			System.out.printf("Step : %d\n", steps);
			System.out.printf("\t\t                                  %10s Score : %d\n", player2.name, player2.score);
	        System.out.printf("\t\t|--------|-----------------------|--------|\n");
	        System.out.printf("\t\t|        | %-3d | %-3d | %-3d | %-3d |        |\n", board.grid[1], board.grid[2], board.grid[3], board.grid[4]);
	        System.out.printf("\t\t|   %2d   |-----------------------|   %2d   |\n", board.grid[0], board.grid[5]);
	        System.out.printf("\t\t|        | %-3d | %-3d | %-3d | %-3d |        |\n", board.grid[9], board.grid[8], board.grid[7], board.grid[6]);
	        System.out.printf("\t\t|--------|-----------------------|--------|\n");
	        System.out.printf("\t\tPits No:   1     2     3     4\n\n");

	        System.out.printf("\t%10s Score : %d\n\n\n", player1.name, player1.score);
		}else{
			System.out.printf("Step : %d\n", steps);
			System.out.printf("\t\t                                  %10s Score : %d\n", player1.name, player1.score);
	        System.out.printf("\t\t|--------|-----------------------|--------|\n");
	        System.out.printf("\t\t|        | %-3d | %-3d | %-3d | %-3d |        |\n", board.grid[6], board.grid[7], board.grid[8], board.grid[9]);
	        System.out.printf("\t\t|   %2d   |-----------------------|   %2d   |\n", board.grid[5], board.grid[0]);
	        System.out.printf("\t\t|        | %-3d | %-3d | %-3d | %-3d |        |\n", board.grid[4], board.grid[3], board.grid[2], board.grid[1]);
	        System.out.printf("\t\t|--------|-----------------------|--------|\n");
	        System.out.printf("\t\tPits No:   1     2     3     4\n\n");

	        System.out.printf("\t%10s Score : %d\n\n\n", player2.name, player2.score);
		}

	}

	public static int idxOfPlayerBoard(int playerNumber, int boardNumber){
		if(playerNumber == 1){
			switch(boardNumber){
				case 1:
					return 9;
				case 2:
					return 8;
				case 3:
					return 7;
				case 4:
					return 6;
			}
		}else{
			switch(boardNumber){
				case 1:
					return 4;
				case 2:
					return 3;
				case 3:
					return 2;
				case 4:
					return 1;
			}
		}
		return -1;
	}

	public static String inputPlayerName(int playerNumber){
		String input;
		boolean valid;

		do{
			valid = true;
			System.out.print(" Input Player " + playerNumber + " username : ");
			input = sc.nextLine();
			if(input.length() < 3 || input.length() > 10){
				System.out.println(" Username must be between 3 and 10 characters!");
				valid = false;
			}else{
				for(char a : input.toCharArray()){
					if(Character.isSpaceChar(a)){
						System.out.println(" White space not allowed!");
						valid = false;
						break;
					}
				}
			}
		}while(!valid);

		return input;
	}

	public static void showExitSplash(){
		clear(0);
		System.out.println("        $$$$$$$\\  $$\\                                             $$\\");
		System.out.println("        $$  __$$\\ $$ |                                            $$ |");
		System.out.println("        $$ |  $$ |$$ |$$\\   $$\\  $$$$$$\\  $$\\  $$$$$$\\   $$$$$$$\\ $$ |  $$\\");
		System.out.println("        $$$$$$$\\ |$$ |$$ |  $$ |$$  __$$\\ \\__| \\____$$\\ $$  _____|$$ | $$  |");
		System.out.println("        $$  __$$\\ $$ |$$ |  $$ |$$$$$$$$ |$$\\  $$$$$$$ |$$ /      $$$$$$  /");
		System.out.println("        $$ |  $$ |$$ |$$ |  $$ |$$   ____|$$ |$$  __$$ |$$ |      $$  _$$<");
		System.out.println("        $$$$$$$  |$$ |\\$$$$$$  |\\$$$$$$$\\ $$ |\\$$$$$$$ |\\$$$$$$$\\ $$ | \\$$\\");
		System.out.println("        \\_______/ \\__| \\______/  \\_______|$$ | \\_______| \\_______|\\__|  \\__|");
		System.out.println("                                    $$\\   $$ |");
		System.out.println("                                    \\$$$$$$  |");
		System.out.println("                                     \\______/");
		System.out.println("                            $$\\  $$$$$$$\\         $$\\");
		System.out.println("                          $$$$ | $$  ____|      $$$$ |");
		System.out.println("                          \\_$$ | $$ |           \\_$$ |");
		System.out.println("                            $$ | $$$$$$$\\ $$$$$$\\ $$ |");
		System.out.println("                            $$ | \\_____$$\\\\______|$$ |");
		System.out.println("                            $$ | $$\\   $$ |       $$ |");
		System.out.println("                          $$$$$$\\$$$$$$  |     $$$$$$\\");
		System.out.println("                          \\______|\\______/      \\______|\n\n");
		System.out.println("                           Thanks for using this app ^_^");
		System.out.println("                                    By IS15-1");
		sc.nextLine();
	}

	public static void showHowToPlay(){
		clear(0);
		System.out.println(" How to play");
		System.out.println(" ===============\n");
		System.out.println(" 1. You can only move the gems on your side");
		System.out.println(" 2. Each time you move, you pick all the gems in the pits and distribute");
		System.out.println("    them in clockwise direction to the next pits");
		System.out.println(" 3. If the last gem of move landed on your pits, then you can move again\n\n");

		System.out.println(" Press ENTER to return to main menu..");
		sc.nextLine();
	}

	public static int showOpeningMenu(){
		int inputMenu;

		clear(0);
		System.out.println(" 1. Start New Game!");
		System.out.println(" 2. How to play");
		System.out.println(" 3. Exit");

		do{
			System.out.print(" Choose [1..3] : ");
			inputMenu = inputInt();
		}while(inputMenu < 1 || inputMenu > 3);

		return inputMenu;
	}

	public static void delay(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void clear(int milliseconds){
		for(int a=0;a<25;a++){
			System.out.print("\n");
			delay(milliseconds);
		}
	}

	public static int inputInt(){
		int input = -1;

		do{
			try{
				input = sc.nextInt();
			}catch(Exception e){
				input = -1;
			}
			sc.nextLine();
		}while(input == -1);

		return input;
	}

	public static int inputPitsChoice(){
		String strInput;
		int intInput;

		do{
			System.out.print(" Choose the pits No [ 1 | 2 | 3 | 4 | cancel ] : ");
			strInput = sc.nextLine();
			try{
				intInput = Integer.parseInt(strInput);
			}catch(Exception e){
				if(strInput.equals("cancel"))
					intInput = 0;
				else
					intInput = -1;
			}

		}while(intInput < 0 || intInput > 4);

		return intInput;
	}


}

class Player{
	String name;
	int score;

	Player(){
		score = 0;
	}
}

class Board{
	int[] grid;

	Board(){
		grid = new int[10];
		for(int a=0;a<10;a++)
			grid[a] = (a % 5 == 0) ? 0 : 4;
	}
}
