import java.util.Vector;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

class T122_Java2LevelUp{
	
	private Scanner sc = new Scanner(System.in);
	private User user;
	private Vector<Asset> asset;

	public T122_Java2LevelUp(){

		showHomeMenu();

	}

	public static void main(String[] args){
		new T122_Java2LevelUp();
	}

	public void showHomeMenu(){
		int inputChoice;

		do{
			clear(0);
			System.out.println("BillionR");
			System.out.println("========");
			System.out.println("1. New Game");
			System.out.println("2. Load Game");
			System.out.println("3. How to Play");
			System.out.println("4. Exit");
			System.out.print("Choice : ");

			inputChoice = inputInt();

			switch(inputChoice){
			case 1:
				newGame();
				break;
			case 2:
				loadGame();
				break;
			case 3:
				showHowToPlay();
				break;
			case 4:
				showExitSplash();
			}
		}while(inputChoice != 4);
	}

	public void showExitSplash(){
		clear(0);
		System.out.println("                 - KEEP FIGHTING AND SHARE OUR GREATEST SKILL -");
		System.out.println("                       - HIGH QUALITY ON PERFECT EFFORT -");
		System.out.println("                                    *~15-1~*");
		for(int a=0;a<10;a++)
			System.out.println();
		sc.nextLine();
	}

	public void showHowToPlay(){
		clear(0);
		System.out.println("BillionR - How to Play");
		System.out.println("======================\n");

		System.out.println("1. You're the CEO of BR Company, a new startup company. You start with $5000.\n");

		System.out.println("2. You can buy new assets. Assets are divided into 3 categories: Service, Store, and Factory.\n");

		System.out.println("3. You can operate your asset to generate revenue. Each asset category has different way in operating.\n");

		System.out.println("4. Service stations generate revenue per second while operating.");
		System.out.println("   Stores generate revenue by selling products while operating.");
		System.out.println("   Factories produce merchandise to be sold by your store.\n");

		System.out.println("Your Goal is simple: earn money to become a BillionR!\n");

		System.out.println("Press enter if you understand...");
		sc.nextLine();
	}

	public void newGame(){
		String name;

		clear(0);

		do{
			System.out.print("Input your name [3..20 characters | alphabets and space only] : ");
			name = sc.nextLine();
		}while(name.length() < 3 || name.length() > 20 || !isAlphaOrSpace(name));

		user = new User(name, 5000);
		asset = new Vector<Asset>();

		showUserMenu();

	}

	public void loadGame(){
		String name;
		String money;

		try{

			File loadFile = new File("save.txt");
			Scanner scFile = new Scanner(loadFile);
			String aLine;

			String[] tok = scFile.nextLine().split("#");

			user = new User(tok[0], Integer.parseInt(tok[1]));
			asset = new Vector<Asset>();

			while(scFile.hasNextLine()){
				aLine = scFile.nextLine();
				tok = aLine.split("#");

				if(tok.length == 0){
					break;
				}else if(tok[0].equals("0")){
					asset.add(new Service(tok[1], "Service", Integer.parseInt(tok[2])));
				}else if(tok[0].equals("1")){
					asset.add(new Store(tok[1], "Store", Integer.parseInt(tok[2]), Integer.parseInt(tok[3])));
				}else if(tok[0].equals("2")){
					asset.add(new Factory(tok[1], "Factory", Integer.parseInt(tok[2])));
				}
			}

			scFile.close();

			showUserMenu();

		}catch(FileNotFoundException e){

			System.out.println("File not found");
			sc.nextLine();

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void showUserMenu(){
		int inputChoice;

		do{
			clear(0);

			System.out.printf("Good day, %s!\n", user.getName());
			System.out.printf("Your money : $%d\n", user.getMoney());
			System.out.println("==================");
			System.out.println("1. View Assets");
			System.out.println("2. Buy Asset");
			System.out.println("3. Operate Asset");
			System.out.println("4. Save and Quit");

			do{

				System.out.print("Choice :");
				inputChoice = inputInt();

			}while(inputChoice < 1 || inputChoice > 4);

			switch(inputChoice){
			case 1:
				clear(0);
				viewAsset();
				sc.nextLine();
				break;
			case 2:
				buyAsset();
				break;
			case 3:
				operateAsset();
				break;
			case 4:
				saveGame();
				break;
			}

		}while(inputChoice != 4);
	}

	public void viewAsset(){

		if(asset.size() == 0){
			System.out.println("You have no asset.");
			sc.nextLine();
			return;
		}

		System.out.printf("%s's Assets\n", user.getName());
		System.out.println("+-----+----------+----------------------+-------+-------+");
		System.out.println("| No. |   Type   |         Name         | Value | Stock |");
		System.out.println("+-----+----------+----------------------+-------+-------+");
		for(int a=0;a<asset.size();a++)
			System.out.printf("| %-3d | %-8s | %-20s | %-5d | %-5s |\n", a + 1, asset.get(a).getType(), asset.get(a).getName(), asset.get(a).getValue(), (asset.get(a).getTypeCode() == 1) ? String.format("%d", ((Store)asset.get(a)).getStock()) : "  -");
		System.out.println("+-----+----------+----------------------+-------+-------+");
	}

	public void operateAsset(){
		int inputChoice;

		clear(0);
		viewAsset();

		if(asset.size() == 0){
			return;
		}
		do{

			System.out.print("Choose asset to operate : ");
			inputChoice = inputInt();

		}while(inputChoice < 1 || inputChoice > asset.size());

		Kiosk theKiosk = new Kiosk(asset.get(inputChoice - 1), asset.get(inputChoice - 1).getTypeCode());
		
		Thread kiosk = new Thread(theKiosk);

		kiosk.start();

		sc.nextLine();

		user.addMoney(theKiosk.stop());
	}

	public void buyAsset(){
		String inputName;
		String inputType;
		int inputValue;


		clear(0);

		if(user.getMoney() < 1000){
			System.out.println("You don't have enough money (min. 1000).");
			sc.nextLine();
			return;
		}

		System.out.println("Buy Asset");
		System.out.println("=========");
		
		do{

			System.out.print("Input asset name [3..20 characters | alphabets and space only] : ");
			inputName = sc.nextLine();

		}while(inputName.length() < 3 || inputName.length() > 20 || !isAlphaOrSpace(inputName));

		do{

			System.out.print("Asset value [1000 - 50000] : ");
			inputValue = inputInt();

		}while(inputValue < 1000 || inputValue > 50000);

		do{

			System.out.print("Asset type [Service | Store | Factory] : ");
			inputType = sc.nextLine();

		}while(!inputType.equals("Service") && !inputType.equals("Store") && !inputType.equals("Factory"));

		clear(0);

		if(inputType.equals("Service")){
			asset.add(new Service(inputName, inputType, inputValue));
		}else if(inputType.equals("Store")){
			asset.add(new Store(inputName, inputType, inputValue, 20));
		}else if(inputType.equals("Factory")){
			asset.add(new Factory(inputName, inputType, inputValue));
		}

		System.out.println("Asset bought!");
		sc.nextLine();

		clear(0);
		System.out.println("Asset name : " + inputName);
		System.out.println("Type       : " + inputType);
		System.out.println("Value      : " + inputValue);
		sc.nextLine();
	}

	public void saveGame(){

		try{

			File saveFile = new File("save.txt");

			if(!saveFile.exists())
				saveFile.createNewFile();

			PrintWriter pw = new PrintWriter(new FileWriter(saveFile), false);

			pw.printf("%s#%d", user.getName(), user.getMoney());
			pw.println();

			for(int a=0;a<asset.size();a++){
				switch(asset.get(a).getTypeCode()){
					case 0:
					case 2:
						pw.printf("%d#%s#%d", asset.get(a).getTypeCode(), asset.get(a).getName(), asset.get(a).getValue());
						break;
					case 1:
						pw.printf("%d#%s#%d#%d", asset.get(a).getTypeCode(), asset.get(a).getName(), asset.get(a).getValue(), ((Store)asset.get(a)).getStock());
						break;
				}
				pw.println();
			}

			pw.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public boolean isAlphaOrSpace(String x){
		for(char a : x.toCharArray()){
			if(!Character.isLetter(a) && !Character.isSpaceChar(a))
				return false;
		}
		return true;
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

	public void clear(int milliseconds){
		for(int a=0;a<25;a++){
			System.out.println();
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
}