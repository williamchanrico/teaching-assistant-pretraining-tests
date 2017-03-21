import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

class T122_Java2Mandatory{

	private Scanner sc = new Scanner(System.in);
	private Vector<User> vecUser = new Vector<User>();
	private Vector<Card> vecDeck = new Vector<Card>();
	private User currentUser;

	public T122_Java2Mandatory(){

		getDecksData();

		getUsersData();

		showOpeningSplash();

		showHomeMenu();

		putUsersData();

		putDecksData();

	}

	public static void main(String[] args){
		new T122_Java2Mandatory();
	}

	public void putDecksData(){

		try{
			FileWriter fw = new FileWriter("AllFile\\Deck.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int a=0;a<Card.getNumOfCards();a++){
				if(vecDeck.get(a).getType().equals("Spell")){
					bw.write(String.format("%s#%s#%s#%d#%d#%d#%d#%d#", vecDeck.get(a).getID(), vecDeck.get(a).getName(), ((SpellCard)vecDeck.get(a)).getDescription(), ((SpellCard)vecDeck.get(a)).getDamage(), vecDeck.get(a).getPrice(), ((SpellCard)vecDeck.get(a)).getAllEnemyHeroTarget(), ((SpellCard)vecDeck.get(a)).getMonsterLifepointTarget(), ((SpellCard)vecDeck.get(a)).getSingleMultiTarget()));
				}else{
					bw.write(String.format("%s#%s#%d#%d#%d#%d#", vecDeck.get(a).getID(), vecDeck.get(a).getName(), ((MonsterCard)vecDeck.get(a)).getStar(), ((MonsterCard)vecDeck.get(a)).getAttack(), ((MonsterCard)vecDeck.get(a)).getDefence(), vecDeck.get(a).getPrice()));
				}
				bw.newLine();
			}

			bw.close();

		}catch(FileNotFoundException e){

			System.out.println("\"Deck.txt\" file not found");
			sc.nextLine();
			System.exit(0);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void putUsersData(){
		try{

			FileWriter fw = new FileWriter("AllFile\\User.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);

			for(int a=0;a<vecUser.size();a++){
				bw.write(String.format("%s#%s#%s#%s#%d#", vecUser.get(a).getID(), vecUser.get(a).getUsername(), vecUser.get(a).getEmail(), vecUser.get(a).getPassword(), vecUser.get(a).getMoney()));
				bw.newLine();
			}

			bw.close();

		}catch(FileNotFoundException e){

			System.out.println("\"User.txt\" file not found");
			sc.nextLine();
			System.exit(0);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void getDecksData(){
		try{

			String aLine;
			FileReader fr = new FileReader("AllFile\\Deck.txt");
			BufferedReader br = new BufferedReader(fr);

			while((aLine = br.readLine()) != null){
				String[] tok = aLine.split("#");
				if(tok.length == 6){
					vecDeck.add(new MonsterCard(tok[1], Integer.parseInt(tok[5]), Integer.parseInt(tok[2]), Integer.parseInt(tok[3]), Integer.parseInt(tok[4])));
				}else{
					vecDeck.add(new SpellCard(tok[1], Integer.parseInt(tok[4]), tok[2], Integer.parseInt(tok[3]), Integer.parseInt(tok[5]), Integer.parseInt(tok[6]), Integer.parseInt(tok[7])));
				}
			}

			br.close();

		}catch(FileNotFoundException e){

			System.out.println("\"Deck.txt\" file not found");
			sc.nextLine();
			System.exit(0);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void getUsersData(){
		
		try{

			String aLine;
			FileReader fr = new FileReader("AllFile\\User.txt");
			BufferedReader br = new BufferedReader(fr);

			while((aLine = br.readLine()) != null){
				String[] tok = aLine.split("#");
				vecUser.add(new User(tok[1], tok[2], tok[3], Integer.parseInt(tok[4]), getUserDeck(tok[0])));
			}

			br.close();

		}catch(FileNotFoundException e){

			System.out.println("\"User.txt\" file not found");
			sc.nextLine();
			System.exit(0);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public Card getCardByID(String id){
		for(int a=0;a<vecDeck.size();a++)
			if(vecDeck.get(a).getID().equals(id)){
				return vecDeck.get(a);
			}
		return null;
	}

	public Card getCardByName(String name){
		for(int a=0;a<vecDeck.size();a++)
			if(vecDeck.get(a).getName().equals(name))
				return vecDeck.get(a);
		return null;
	}

	public Vector<Card> getUserDeck(String id){
		Vector<Card> deck = new Vector<Card>();

		try{

			String aLine;
			FileReader fr = new FileReader("AllFile\\UserDeck\\" + id + ".txt");
			BufferedReader br = new BufferedReader(fr);

			while((aLine = br.readLine()) != null){
				String[] tok = aLine.split("#");
				deck.add(getCardByID(tok[1]));
			}

			br.close();

		}catch(FileNotFoundException e){

			System.out.printf("\"%s.txt\" file not found", id);
			sc.nextLine();
			System.exit(0);

		}catch(IOException e){

			e.printStackTrace();

		}

		return deck;
	}

	public Vector<Card> generateUserDeck(String id){
		Vector<Card> deck = new Vector<Card>();

		try{
			File file = new File("AllFile\\UserDeck\\" + id + ".txt");
			file.createNewFile();

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			int idx = randomIntRange(0, vecDeck.size() - 1);

			for(int a=0;a<40;a++){
				deck.add(vecDeck.get(idx));
				idx = (idx + 1) % vecDeck.size();
			}

			Collections.shuffle(deck);

			for(int a=0;a<40;a++){
				bw.write(String.format("%d#%s#", a + 1, deck.get(a).getID()));
				bw.newLine();
			}

			bw.close();

		}catch(IOException e){

			e.printStackTrace();

		}
		return deck;
	}

	public void updateUserDeck(String id){

		Vector<Card> deck = currentUser.getDeck();

		try{
			File file = new File("AllFile\\UserDeck\\" + id + ".txt");
			file.createNewFile();

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			for(int a=0;a<deck.size();a++){
				bw.write(String.format("%d#%s#", a + 1, deck.get(a).getID()));
				bw.newLine();
			}

			bw.close();

		}catch(IOException e){

			e.printStackTrace();

		}
	
	}

	public void register(){
		String username;
		String password;
		String email;

		System.out.println(String.format("ID : SL%03d", User.getNumOfUsers() + 1));

		do{
			System.out.print("Input Username : ");
			username = sc.nextLine();

			if(username.isEmpty())
				System.out.println("Username cannot be empty");
			else if(username.length() < 5 || username.length() > 40)
				System.out.println("Username\'s length must between 5 and 40");
			else if(wordsCount(username) < 3)
				System.out.println("Username must be 3 words or more");

		}while(username.length() < 5 || username.length() > 40 || wordsCount(username) < 3);

		do{
			System.out.print("Input your email : ");
			email = sc.nextLine();

			if(!email.endsWith(".com"))
				System.out.println("Email must ends with .com");
			else if(email.endsWith("@.com"))
				System.out.println("\'.\' and \'@\' character cannot side by side");
			

		}while(!email.endsWith(".com") || email.endsWith("@.com"));

		do{
			System.out.print("Input Password : ");
			password = sc.nextLine();

			if(password.isEmpty())
				System.out.println("Password cannot be empty");
			else if(password.length() < 8)
				System.out.println("Password\'s length must more than equals 8 characters");

		}while(password.length() < 8);

		vecUser.add(new User(username, email, password, 2000, generateUserDeck(String.format("SL%03d", User.getNumOfUsers() + 1))));

		System.out.println("Success inserted");
		sc.nextLine();
	}

	public void showHomeMenu(){

		int inputChoice;

		do{
			clear(0);

			System.out.println("SLU-GI-OH");
			System.out.println("========");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print("Choose : ");

			inputChoice = inputInt();

			switch(inputChoice){
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			}

		}while(inputChoice != 3);
	}

	public void login(){
		String username;
		String password;
		String continueLogin;


		while(true){

			do{
				System.out.print("Input Username : ");
				username = sc.nextLine();

				if(username.equals("admin"))
					break;

				if(username.isEmpty())
					System.out.println("Username cannot be empty");
				else if(username.length() < 5 || username.length() > 40)
					System.out.println("Username\'s length must between 5 and 40");
				else if(wordsCount(username) < 3)
					System.out.println("Username must be 3 words or more");

			}while(username.length() < 5 || username.length() > 40 || wordsCount(username) < 3);

			do{
				System.out.print("Input Password : ");
				password = sc.nextLine();

				if(username.equals("admin") && password.equals("admin")){
					showAdminMenu();
					return;
				}

				if(password.isEmpty())
					System.out.println("Password cannot be empty");
				else if(password.length() < 8)
					System.out.println("Password\'s length must more than equals 8 characters");

			}while(password.length() < 8);

			for(int a=0;a<vecUser.size();a++){
				if(vecUser.get(a).matchUser(username, password)){
					currentUser = vecUser.get(a);
					showUserMenu();
					return;
				}
			}

			System.out.println("\nUser not found");
			
			do{

				System.out.println("Do you want continue to login [Y/N]? : ");
				continueLogin = sc.nextLine();

			}while(!continueLogin.equals("Y") && !continueLogin.equals("N"));

			if(continueLogin.equals("N"))
				return;
		}
	}

	public void showAdminMenu(){
		String inputName;
		String inputDescription;
		int inputChoice;
		int inputCardType;
		int inputStar;
		int inputAttack;
		int inputDefence;
		int inputPrice;
		int inputDamage;
		int inputAllEnemyHeroTarget; 	// All Target = 1 | Enemy = 2 | Hero = 3
		int inputMonsterLifepointTarget; // Monster = 1 | Lifepoint = 2
		int inputSingleMultiTarget;  	// All Target or Lifepoint = 0 | Single = 1 | Multi = 2



		do{
			clear(0);
			System.out.println("Hello Admin");
			System.out.println("===========");
			System.out.println("1. Add List Card");
			System.out.println("2. Logout");
			System.out.print("Choose >> ");

			inputChoice = inputInt();

			if(inputChoice == 1){
				do{
					clear(0);
					System.out.println("Choose your card type");
					System.out.println("=====================");
					System.out.println("1. Monster");
					System.out.println("2. Spell");
					System.out.println("3. Exit");
					System.out.print("Choose : ");
					inputCardType = inputInt();

					switch(inputCardType){
					case 1:
						do{
							System.out.print("Name\t: ");
							inputName = sc.nextLine();

							if(inputName.isEmpty())
								System.out.println("Name can\'t be empty");

						}while(inputName.isEmpty());

						do{
							System.out.print("Star\t: ");
							inputStar = inputInt();

							if(inputStar < 1 || inputStar > 12)
								System.out.println("Star must between 1 and 12");

						}while(inputStar < 1 || inputStar > 12);

						do{

							System.out.print("Attack\t: ");
							inputAttack = inputInt();

						}while(inputAttack == -1);

						do{
							
							System.out.print("Defence\t: ");
							inputDefence = inputInt();

						}while(inputDefence == -1);

						do{
							
							System.out.print("Price\t: ");
							inputPrice = inputInt();

						}while(inputPrice == -1);

						vecDeck.add(new MonsterCard(inputName, inputPrice, inputStar, inputAttack, inputDefence));
						
						System.out.println("Insert Success");
						sc.nextLine();

						break;
					case 2:
						do{
							System.out.print("Name\t\t: ");
							inputName = sc.nextLine();

							if(inputName.isEmpty())
								System.out.println("Name can\'t be empty");

						}while(inputName.isEmpty());

						do{
							System.out.print("Description\t: ");
							inputDescription = sc.nextLine();

							if(inputDescription.isEmpty())
								System.out.println("Description can\'t be empty");
							else if(wordsCount(inputDescription) < 5)
								System.out.println("Description must be 5 words or more");

						}while(wordsCount(inputDescription) < 5);

						do{

							System.out.print("Damage\t\t: ");
							inputDamage = inputInt();

						}while(inputDamage == -1);

						do{
							
							System.out.print("Price\t\t: ");
							inputPrice = inputInt();

						}while(inputPrice == -1);

						do{
							System.out.println("\nTarget");
							System.out.println("======");
							System.out.println("1. All Target");
							System.out.println("2. Enemy");
							System.out.println("3. Hero");
							System.out.print("Input your target : ");
							inputAllEnemyHeroTarget = inputInt();
						}while(inputAllEnemyHeroTarget < 1 || inputAllEnemyHeroTarget > 3);

						do{
							System.out.println("\nTarget");
							System.out.println("======");
							System.out.println("1. Monster");
							System.out.println("2. Lifepoints");
							System.out.print("Input your target : ");
							inputMonsterLifepointTarget = inputInt();
						}while(inputMonsterLifepointTarget < 1 || inputMonsterLifepointTarget > 2);

						if(inputAllEnemyHeroTarget == 1 || inputMonsterLifepointTarget == 2){

							vecDeck.add(new SpellCard(inputName, inputPrice, inputDescription, inputDamage, inputAllEnemyHeroTarget, inputMonsterLifepointTarget, 0));
						
						}else{

							do{
								System.out.println("\nTarget");
								System.out.println("======");
								System.out.println("1. Single Target");
								System.out.println("2. Multi Target");
								System.out.print("Input your target : ");
								inputSingleMultiTarget = inputInt();
							}while(inputSingleMultiTarget < 1 || inputSingleMultiTarget > 2);

							vecDeck.add(new SpellCard(inputName, inputPrice, inputDescription, inputDamage, inputAllEnemyHeroTarget, inputMonsterLifepointTarget, inputSingleMultiTarget));
						}

						System.out.println("Insert Success");
						sc.nextLine();

						break;
					}


				}while(inputCardType != 3);
			}

		}while(inputChoice != 2);
	}

	public void buyCard(){
		int idx = randomIntRange(0, vecDeck.size() - 1);
		int inputCardIdx;
		
		clear(0);


		System.out.println("-----------------------------------------------------------------------");
		System.out.println("|No |Type   |Card Name                     |Description         |Price|");
		System.out.println("-----------------------------------------------------------------------");
		for(int a=1;a<=5;a++){
			int b = (a + idx) % vecDeck.size();
			System.out.printf("|%-3d|%-7s|%-30s|%-20s|%-5d|\n", a, vecDeck.get(b).getType().equals("Monster") ? "Monster" : "Magic", vecDeck.get(b).getName(), vecDeck.get(b).getType().equals("Monster") ? "" : ((SpellCard)vecDeck.get(b)).getDescription(), vecDeck.get(b).getPrice());
		}
		System.out.println("---------------------------------------------------------------------");

		do{

			System.out.print("\nWhat do you want to buy [6 to exit] : ");
			inputCardIdx = inputInt();

		}while(inputCardIdx < 1 || inputCardIdx > 6);

		if(inputCardIdx == 6)
			return;

		Card temp = getCardByName(vecDeck.get((inputCardIdx + idx) % vecDeck.size()).getName());

		if(currentUser.hasCard(temp)){
			System.out.println("You already have that card");
		}else if(currentUser.getMoney() < temp.getPrice()){
			System.out.println("You don\'t have enough money");			
		}else{
			currentUser.buyCard(temp);
			System.out.println("You bought the card");
		}

		sc.nextLine();
	}

	public void showUserMenu(){
		User backupUser = currentUser.copy();
		int inputChoice;

		do{
			clear(0);
			System.out.println("Hello, " + currentUser.getUsername());
			System.out.println("Money   : " + currentUser.getMoney());
			System.out.println("===========");
			System.out.println("1. Play Game");
			System.out.println("2. My Card");
			System.out.println("3. Buy Card");
			System.out.println("4. Save");
			System.out.println("5. Refresh");
			System.out.println("6. Logout");

			do{

				System.out.print("Choose >> ");
				inputChoice = inputInt();

			}while(inputChoice < 1 || inputChoice > 6);

			switch(inputChoice){
			case 2:
				clear(0);
				currentUser.showDeck();
				sc.nextLine();
				break;
			case 3:
				buyCard();
				break;
			case 4:
				clear(0);
				updateUserDeck(currentUser.getID());
				System.out.println("Saved");
				sc.nextLine();
				break;
			case 5:
				clear(0);
				currentUser = backupUser;
				System.out.println("Refreshed");
				sc.nextLine();
				break;
			}
			

		}while(inputChoice != 6);

	}

	public int wordsCount(String x){
		return x.split("\\s+").length;
	}

	public void showOpeningSplash(){
		int milliseconds = 150;
		clear(0);

		System.out.println("           _____ _     _    _         _____ _____       ____  _    _");
		delay(milliseconds);
		System.out.println("          / ____| |   | |  | |       / ____|_   _|     / __ \\| |  | |");
		delay(milliseconds);
		System.out.println("         | (___ | |   | |  | |______| |  __  | |______| |  | | |__| |");
		delay(milliseconds);
		System.out.println("          \\___ \\| |   | |  | |______| | |_ | | |______| |  | |  __  |");
		delay(milliseconds);
		System.out.println("          ____) | |___| |__| |      | |__| |_| |_     | |__| | |  | |");
		delay(milliseconds);
		System.out.println("         |_____/|______\\____/        \\_____|_____|     \\____/|_|  |_|");
		
		for(int a=0;a<8;a++){
			System.out.print("\n");
			delay(milliseconds);
		}

		sc.nextLine();
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

	public int randomIntRange(int min, int max){
		Random random = new Random();

		return random.nextInt(max - min + 1) + min;
	}
}