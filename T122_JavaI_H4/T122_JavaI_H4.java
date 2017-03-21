import java.util.*;
import java.lang.*;
import javax.swing.*;

class T122_JavaI_H4{

	static Scanner sc = new Scanner(System.in);

	static Vector<Fruit> vecStorage = new Vector<Fruit>();

	public static void main(String[] args){
		String name;
		String inputMainMenu;

		clear(0);
		while(true){
			System.out.print(" Input Name [Min 2 Words] : ");
			name = sc.nextLine();
			if(name.lastIndexOf(" ") != -1 && (name.length() - 1) > name.lastIndexOf(" ") && Character.isLetter(name.charAt(name.lastIndexOf(" ") + 1)))
				break;
			else
				System.out.println(" Name Must be Minimal 2 Words");
		}
		
		clear(0);

		do{
			System.out.println(" Welcome, " + name);
			printTime();
			inputMainMenu = inputCustomLine(" Where do you want to go? [farm|storage|exit]: ", " Input Must be Either \'farm\', \'storage\', or \'exit\'", "farm", "storage", "exit");

			if(inputMainMenu.equals("farm")){

				loadingAnimation(" Going to Farm ");
				goFarm();

			}else if(inputMainMenu.equals("storage")){

				loadingAnimation(" Going to Storage ");
				goStorage();

			}else if(inputMainMenu.equals("exit")){


				if(JOptionPane.showConfirmDialog(null, "Exit Confirmation", "Are you sure?", JOptionPane.OK_CANCEL_OPTION) == 0){

					System.out.println("\n Thankyou For using this Program");
					loadingAnimation(" Program Exiting ");
					delay(1000);
					
				}else{

					inputMainMenu = "";					
					clear(0);

				}

			}
		
		}while(!inputMainMenu.equals("exit"));

	}

	public static void goFarm(){
		String inputFarmMenu;
		int randomFruit;

		do{

			printTime();

			inputFarmMenu = inputCustomLine(" What do you want to do? [explore|back] : ", " Input Must be either \'explore\' or \'back\'", "explore", "back");
			
			if(inputFarmMenu.equals("explore")){
				Fruit newFruit;
				Random random = new Random();

				loadingAnimation(" Exploring ");

				printTime();

				randomFruit = random.nextInt(100) + 1;
				
				if(randomFruit <= 20){

					newFruit = new Fruit("Apple", randomInt(250, 299));

				}else if(randomFruit <= 40){

					newFruit = new Fruit("Orange", randomInt(170, 229));

				}else if(randomFruit <= 60){

					newFruit = new Fruit("Grape", randomInt(200, 249));

				}else if(randomFruit <= 80){

					newFruit = new Fruit("Strawberry", randomInt(125, 144));

				}else{

					newFruit = new Fruit("Lemon", randomInt(70, 139));

				}

				System.out.println(newFruit);

				String inputKeep = inputCustomLine(" Keep back to storage [yes|no] : ", " Input Must be Either \'yes\' or \'no\'", "yes", "no");

				if(inputKeep.equals("yes")){

					String description;

					while(true){

						description = JOptionPane.showInputDialog(null, "Description [5..45]", newFruit.name + " description", JOptionPane.QUESTION_MESSAGE);
						
						if(description.length() < 5 || description.length() > 45){
	
							JOptionPane.showMessageDialog(null, "Description Must be Between 5 and 45 Character Length");
						
						}else{

							newFruit.setDescription(description);
							break;

						}

					}

					vecStorage.add(newFruit);
					loadingAnimation(" Keeping Item ");

				}else if(inputKeep.equals("no")){

					loadingAnimation(" Let it go ");

				}

			}else if(inputFarmMenu.equals("back")){

				loadingAnimation(" Going Home ");

			}

		}while(!inputFarmMenu.equals("back"));
	}

	public static int randomInt(int x, int y){
		Random random = new Random();
		return random.nextInt(1 + y - x) + x;
	}

	public static void goStorage(){
		String inputStorageMenu;
		String input;
		int inputSell;

		do{
			if(vecStorage.isEmpty()){
				System.out.println(" Storage is Empty");
				return;
			}
			printTime();

			printStorage();

			inputStorageMenu = inputCustomLine(" What do you want to do? [sort|sell|back] : ", " Input Must be Either \'sort\', \'sell\' or \'back\'", "sort", "sell", "back");
			
			if(inputStorageMenu.equals("sort")){
				
				input = inputCustomLine(" Asc Sort By [name|weight] : ", " Input Must be Either \'name\' or \'weight\'", "name", "weight");

				if(input.equals("name")){
					sortByName();
					loadingAnimation(" Sorting by Name ");
				}else{
					sortByWeight();
					loadingAnimation(" Sorting by Weight ");
				}

			}else if(inputStorageMenu.equals("sell")){

				do{
					System.out.printf(" Input item index [1..%d] : ", vecStorage.size());
					inputSell = inputInt();
				}while(inputSell < 1 || inputSell > vecStorage.size());

				JOptionPane.showMessageDialog(null, vecStorage.get(inputSell - 1).name + " Sold Successfully");

				vecStorage.removeElementAt(inputSell - 1);

				loadingAnimation(" Refreshing Storage ");

			}else if(inputStorageMenu.equals("back")){

				loadingAnimation(" Going Home ");

			}

		}while(!inputStorageMenu.equals("back"));
	}

	public static void sortByWeight(){
		boolean swapped = true;

		while(swapped){
			swapped = false;
			for(int a=1;a<vecStorage.size();a++){
				if(vecStorage.get(a - 1).weight > vecStorage.get(a).weight){
					Collections.swap(vecStorage, a - 1, a);
					swapped = true;
				}
			}
		}
	}

	public static void sortByName(){
		boolean swapped = true;

		while(swapped){
			swapped = false;
			for(int a=1;a<vecStorage.size();a++){
				if(vecStorage.get(a - 1).name.compareTo(vecStorage.get(a).name) > 0){
					Collections.swap(vecStorage, a - 1, a);
					swapped = true;
				}
			}
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

	public static void printStorage(){

		if(vecStorage.isEmpty())
			return;

		System.out.println(" +---+---------------+-------+---------------------------------------------+");
		System.out.println(" |No.|Fruits         |Weight |Description                                  |");
		System.out.println(" +---+---------------+-------+---------------------------------------------+");
		for(int a=0;a<vecStorage.size();a++){
			System.out.printf(" |%-3d|%-15s|%7d|%-45s|\n", a + 1, vecStorage.get(a).name, vecStorage.get(a).weight, vecStorage.get(a).description);
		}
		System.out.println(" +---+---------------+-------+---------------------------------------------+\n");
	}

	public static void printTime(){
		Calendar calendar = Calendar.getInstance();

		System.out.println("                                                   " + calendar.getTime());
	}
	
	public static String inputCustomLine(String inputMessage, String failMessage, String...rules){
		String input;

		while(true){
			System.out.print(inputMessage);
			input = sc.nextLine();
			if(!Arrays.asList(rules).contains(input)){
				System.out.println(failMessage);
				continue;
			}
			break;
		}
		
		return input;
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

	public static void loadingAnimation(String message){
		String animationFrame = new String("/-\\|.");

		System.out.printf("%s", message);
		for(int a=0;a<3;a++){
			for(int b=0;b<500;b++){
				System.out.print(animationFrame.charAt(b % 4));
				delay(1);
				System.out.printf("\b");
			}
			System.out.printf(".");
		}
		delay(100);
		clear(0);
	}


}

class Fruit{
	String name;
	String description;
	int weight;

	Fruit(String name, int weight){
		this.name = name;
		this.weight = weight;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String toString(){
		return " Fruit Found   : " + name + "\n Fruits Weight : " + weight;
	}
}