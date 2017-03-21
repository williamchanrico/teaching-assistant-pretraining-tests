import java.util.Scanner;
import java.util.Random;
import java.util.Vector;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


class T122_JavaIIH4OnSite{

	private Scanner sc = new Scanner(System.in);
	private User currentUser;
	private Vector<Pizza> currentPizza;
	private Vector<Booth> currentBooth;

	public T122_JavaIIH4OnSite(){
		showHomeMenu();
	}

	public void showHomeMenu(){
		int inputChoice;

		do{

			clear(0);

			printTitle();

			System.out.println("\n\nMenu");
			System.out.println("===============");
			System.out.println("1. Play Game");
			System.out.println("2. Load Game");
			System.out.println("3. Exit");

			do{

				System.out.print("Choose:");
				inputChoice = inputInt();

			}while(inputChoice < 1 || inputChoice > 3);

			switch(inputChoice){
			case 1:
				playGame();
				break;
			case 2:
				loadFiles();
				break;
			}

		}while(inputChoice != 3);

	}

	public File getCurrentUserSaveFile(){
		File newFile = new File("Save\\" + currentUser.getStoreName() + ".txt");
		if(!newFile.exists()){
			
			try{

				newFile.createNewFile();
				
			}catch(IOException e){

				e.printStackTrace();
			
			}
			
		}

		return newFile;
	}

	public void playGame(){
		int inputChoice;

		currentPizza = (currentPizza == null) ? new Vector<Pizza>() : currentPizza;
		currentBooth = (currentBooth == null) ? new Vector<Booth>() : currentBooth;

		if(currentUser == null){
			clear(0);
			String inputName;

			do{

				System.out.print("Please input your store's name [3..15] : ");
				inputName = sc.nextLine();

			}while(inputName.length() < 3 || inputName.length() > 15);

			currentUser = new User(inputName, 500);
		}
		do{

			clear(0);

			System.out.println("Money = " + currentUser.getMoney() + "G");
			System.out.println(currentUser.getStoreName() + "\'s Pizza");
			System.out.println("===============");
			System.out.println("1. Open store");
			System.out.println("2. See booth");
			System.out.println("3. See Pizza");
			System.out.println("4. Save Game");
			System.out.println("5. Back to menu");

			do{

				System.out.print("Choose : ");
				inputChoice = inputInt();

			}while(inputChoice < 1 || inputChoice > 5);
			
			switch(inputChoice){
			case 1:
				openShop();
				break;
			case 2:
				seeBooth();
				break;
			case 3:
				seePizza();
				break;
			case 4:
				saveGame();
				break;
			}

		}while(inputChoice != 5);

		currentUser = null;
	}

	public void openShop(){
		Vector<Thread> vecThread = new Vector<Thread>();
		Vector<Kiosk> vecKiosk = new Vector<Kiosk>();

		for(int a=0;a<currentBooth.size();a++){
			vecKiosk.add(new Kiosk(currentUser, currentBooth.get(a), currentPizza));
			vecThread.add(new Thread(vecKiosk.get(a)));
			vecThread.get(a).start();
		}

		sc.nextLine();

		for(int a=0;a<currentBooth.size();a++)
			vecKiosk.get(a).closeKiosk();


	}

	public void seeBooth(){
		String inputAdd;
		String inputBoothName;
		int addPrice;

		clear(0);
		if(currentBooth.size() == 0){
			System.out.println("You have no booth...");
		}else{

			System.out.println("======================================");
			System.out.println("| Name                 | Size | Fame |");
			System.out.println("======================================");
			for(int a=0;a<currentBooth.size();a++)
				System.out.printf("| %-20s | %-4d | %-4d |\n", currentBooth.get(a).getName(), currentBooth.get(a).getSize(), currentBooth.get(a).getFame());
			System.out.println("======================================\n");

		}

		addPrice = currentBooth.size() * 500;

		do{

			System.out.printf("Do you want to add new booth [Y, cost = %dG | N] : ", addPrice);
			inputAdd = sc.nextLine();

		}while(!inputAdd.equalsIgnoreCase("Y") && !inputAdd.equalsIgnoreCase("N"));

		if(inputAdd.equalsIgnoreCase("Y")){

			if(currentUser.getMoney() < addPrice){
				System.out.println("Your money is not enough...");
				return;
			}

			do{

				System.out.print("Please input your booth's name [3..10] : ");
				inputBoothName = sc.nextLine();

			}while(inputBoothName.length() < 3 || inputBoothName.length() > 10);

			currentBooth.add(new Booth(inputBoothName, randomIntRange(1, 7), 20));

			System.out.println("You have create a new booth");
			sc.nextLine();

		}

	}

	public void seePizza(){
		String inputAdd;
		String inputPizzaName;
		int inputTomatoesAmount;
		int inputMeatsAmount;
		int inputVegetablesAmount;
		int inputPrice;

		clear(0);
		if(currentPizza.size() == 0){
			System.out.println("You have no pizza...");
		}else{

			for(int a=0;a<currentPizza.size();a++){
				System.out.printf("==============================\n");
				System.out.printf("| %-26s |\n", currentPizza.get(a).getName());
				System.out.printf("==============================\n");
				System.out.printf("| Price              : %4dG |\n", currentPizza.get(a).getPrice());
				System.out.printf("| Tomato             : %5d |\n", currentPizza.get(a).getTomatoes());
				System.out.printf("| Meat               : %5d |\n", currentPizza.get(a).getMeats());
				System.out.printf("| Veggie             : %5d |\n", currentPizza.get(a).getVegetables());
				System.out.printf("| Cost               : %4dG |\n", currentPizza.get(a).getCost());
				System.out.printf("==============================\n\n");
			}

		}

		do{

			System.out.print("Do you want to add new pizza [Y | N] : ");
			inputAdd = sc.nextLine();

		}while(!inputAdd.equalsIgnoreCase("Y") && !inputAdd.equalsIgnoreCase("N"));

		if(inputAdd.equalsIgnoreCase("Y")){

			do{

				System.out.print("Please input your pizza's name [3..20] : ");
				inputPizzaName = sc.nextLine();

			}while(inputPizzaName.length() < 3 || inputPizzaName.length() > 20);

			do{

				System.out.print("Please input tomato(es) amount [0..10] : ");
				inputTomatoesAmount = inputInt();

			}while(inputTomatoesAmount < 0 || inputTomatoesAmount > 10);

			do{

				System.out.print("Please input meat(s) amount [0..10] : ");
				inputMeatsAmount = inputInt();

			}while(inputMeatsAmount < 0 || inputMeatsAmount > 10);

			do{

				System.out.print("Please input vegetable(s) amount [0..10] : ");
				inputVegetablesAmount = inputInt();

			}while(inputVegetablesAmount < 0 || inputVegetablesAmount > 10);

			do{

				System.out.print("Please input your pizza's price [1..30] : ");
				inputPrice = inputInt();

			}while(inputPrice < 1 || inputPrice > 30);

			currentPizza.add(new Pizza(inputPizzaName, inputPrice, inputTomatoesAmount, inputMeatsAmount, inputVegetablesAmount));
			System.out.println("You have create a new pizza");
			sc.nextLine();

		}
	}

	public void saveGame(){
		PrintWriter pw;

		try{

			pw = new PrintWriter(getCurrentUserSaveFile());
			
			pw.println(currentUser.getMoney());

			pw.println("===Booth===");
			for(int a=0;a<currentBooth.size();a++){
				pw.printf("%s\'s %s#%d#%d", currentUser.getStoreName(), currentBooth.get(a).getName(), currentBooth.get(a).getSize(), currentBooth.get(a).getFame());
				pw.println();
			}
			pw.println("===Pizza===");
			for(int a=0;a<currentPizza.size();a++){
				pw.printf("%s#%d#%d#%d#%d", currentPizza.get(a).getName(), currentPizza.get(a).getPrice(), currentPizza.get(a).getTomatoes(), currentPizza.get(a).getMeats(), currentPizza.get(a).getVegetables());
				pw.println();
			}
			pw.close();

		}catch(FileNotFoundException e){

			e.printStackTrace();

		}
	}

	public void loadFiles(){
		int inputChoice;

		currentPizza = new Vector<Pizza>();
		currentBooth = new Vector<Booth>();

		clear(0);

		File file = new File("Save");

		if(!file.isDirectory() || !file.exists())
			file.mkdir();

		File[] arrFile = file.listFiles();

		if(arrFile.length == 0)
			System.out.println("        No save file founded...");

		System.out.println("Load Files");
		System.out.println("===============");
		for(int a=0;a<arrFile.length;a++)
			System.out.println(a + 1 + ". " + arrFile[a].getName());

		do{

			System.out.print("Choose [0 to cancel] : ");
			inputChoice = inputInt();

		}while(inputChoice < 0 || inputChoice > arrFile.length);

		if(inputChoice == 0)
			return;

		Scanner scFile;

		try{

			 scFile = new Scanner(arrFile[inputChoice - 1]);

		}catch(FileNotFoundException e){

			e.printStackTrace();
			return;

		}

		currentUser = new User(arrFile[inputChoice - 1].getName(), scFile.nextInt());

		scFile.nextLine();

		while(scFile.hasNextLine()){
			String temp = scFile.next();

			if(!temp.endsWith("\'s"))
				break;

			String[] tok = scFile.next().split("#");

			currentBooth.add(new Booth(tok[0], Integer.parseInt(tok[1]), Integer.parseInt(tok[2])));

		}

		while(scFile.hasNextLine()){
			String[] tok = scFile.nextLine().split("#");

			if(tok.length == 1){ //check last empty line
				break;
			}

			currentPizza.add(new Pizza(tok[0], Integer.parseInt(tok[1]), Integer.parseInt(tok[2]), Integer.parseInt(tok[3]), Integer.parseInt(tok[4])));

		}

		playGame();
	}

	public void printTitle(){
		System.out.println("            _____ _                _____                  _");
		System.out.println("           |  __ (_)              |  __ \\                | |");
		System.out.println("           | |__) | __________ _  | |  | | ___   ___   __| |");
		System.out.println("           |  ___/ |_  /_  / _` | | |  | |/ _ \\ / _ \\ / _` |");
		System.out.println("           | |   | |/ / / / (_| | | |__| | (_) | (_) | (_| |");
		System.out.println("           |_|   |_/___/___\\__,_| |_____/ \\___/ \\___/ \\__,_|");
	}
	
	public static void main(String[] args){
		new T122_JavaIIH4OnSite();
	}

	public void clear(int milliseconds){
		for(int a=0;a<25;a++){
			System.out.print("\n");
			delay(milliseconds);
		}
	}

	public void delay(int milliseconds){
		try{
			
			Thread.sleep(milliseconds);

		}catch(InterruptedException e){

			e.printStackTrace();
		
		}
	}

	public int randomIntRange(int min, int max){
		Random random = new Random();

		return random.nextInt(max - min + 1) + min;
	}

	public int inputInt(){
		int input;

		try{

			input = sc.nextInt();
		
		}catch(Exception e){

			input = -1;

		}

		sc.nextLine();

		return input;
	}
}