import java.util.Scanner;
import java.util.Random;
import java.util.Vector;
import java.util.Collections;

class T122_Java2OnSite_H2{

	private Scanner sc = new Scanner(System.in);
	private String name;
	private int money;
	private Vector<Flower> vecFlower;
	private Vector<Mushroom> vecMushroom;

	public T122_Java2OnSite_H2(){

		vecFlower = new Vector<Flower>();
		vecMushroom = new Vector<Mushroom>();

		clear(0);

		money = 5000;

		do{

			System.out.print(" Input your name [3..25 characters] : ");
			name = sc.nextLine();

			if(!isAlpha(name))
				System.out.println(" Name must be alphabet");


		}while(name.length() < 3 || name.length() > 25 || !isAlpha(name));

		showMenu();


	}

	public static void main(String[] args){
		new T122_Java2OnSite_H2();
	}

	public void showMenu(){
		int inputMenu;

		do{
			clear(0);

			System.out.print(" Let's start, " + name + " !\n");
			System.out.print(" Money : $ " + money + "\n\n\n");


			System.out.print("  PLANT RANNY\n");
			System.out.print(" +=====+=====+\n");
			System.out.print(" 1. Start to Plant\n");
			System.out.print(" 2. Water plant [need $25]\n");
			System.out.print(" 3. View Garden\n");
			System.out.print(" 4. Sell Seed(s)\n");
			System.out.print(" 5. Exit\n");

			do{

				System.out.print(" Choose >> ");
				inputMenu = inputInt();

			}while(inputMenu < 1 || inputMenu > 5);

			switch(inputMenu){
			case 1:
				startToPlant();
				break;
			case 2:
				waterPlant();
				break;
			case 3:
				bubbleSort();
				viewGarden();
				break;
			case 4:
				sellSeeds();
				break;
			}


		}while(inputMenu != 5);
	}

	public void bubbleSort(){
		boolean swapped = true;

		while(swapped){
			swapped = false;

			for(int a=1;a<vecFlower.size();a++){
				if(vecFlower.get(a - 1).getName().compareTo(vecFlower.get(a).getName()) > 0){
					Collections.swap(vecFlower, a, a - 1);
					swapped = true;
				}
			}
			for(int a=1;a<vecMushroom.size();a++){
				if(vecMushroom.get(a - 1).getName().compareTo(vecMushroom.get(a).getName()) > 0){
					Collections.swap(vecMushroom, a, a - 1);
					swapped = true;
				}
			}
		}
	}

	public void sellSeeds(){
		int inputPlantIndex;
		int inputSeeds;

		clear(0);

		if(Mushroom.getTotalMushroom() == 0){
			System.out.println(" You don't have any seed(s)");
			sc.nextLine();
			return;
		}
		System.out.println("+======+======+======+======+======+======+");
		System.out.println("| No. | Plant Name           |  Seed(s)   |");
		System.out.println("+======+======+======+======+======+======+");

		for(int a=0;a<Mushroom.getTotalMushroom();a++){
			System.out.printf("| %-3d | %-20s |  %-7d   |\n", a + 1, vecMushroom.get(a).getName(), vecMushroom.get(a).getSeeds());
		}
		System.out.println("+======+======+======+======+======+======+");

		do{

			System.out.printf(" Choose Mushroom [1..%d] : ", Mushroom.getTotalMushroom());
			inputPlantIndex = inputInt();

		}while(inputPlantIndex < 1 || inputPlantIndex > Mushroom.getTotalMushroom());

		if(vecMushroom.get(inputPlantIndex - 1).getSeeds() > 0){
			do{
				System.out.printf(" Sell Seed(s) [1..%d] : ", vecMushroom.get(inputPlantIndex - 1).getSeeds());
				inputSeeds = inputInt();
			}while(inputSeeds < 1 || inputSeeds > vecMushroom.get(inputPlantIndex - 1).getSeeds());

			int randomSoldPrice = inputSeeds * randomIntRange(50, 100);
			System.out.println(" You got $ " + randomSoldPrice + " . . .");
			money += randomSoldPrice;
		}else{
			System.out.println(" That mushroom don\'t have any seed . . .");
		}

		sc.nextLine();
	}

	public void viewGarden(){
		clear(0);

		if(vecFlower.size() == 0 && vecMushroom.size() == 0){
			System.out.println(" There's no plant in your garden");
			sc.nextLine();
			return;
		}
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		System.out.println("| No. | Plant Name      | Pot      | PlantType     | Lvl | W.Absorb | Seed(s)|");
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		for(int a=0;a<vecFlower.size();a++){
			System.out.printf("| %-3d | %-15s | %-8s | %-13s | %-3d | %-8d | -      |\n", a + 1, vecFlower.get(a).getName(), vecFlower.get(a).getPot(), vecFlower.get(a).getType(), vecFlower.get(a).getLevel(), vecFlower.get(a).getWaterAbsorb());
			System.out.println("|----------------------------------------------------------------------------|");
		}
		for(int a=0;a<vecMushroom.size();a++){
			System.out.printf("| %-3d | %-15s | %-8s | %-13s | %-3d | %-8d | %-7d|\n", a + 1 + vecFlower.size(), vecMushroom.get(a).getName(), vecMushroom.get(a).getPot(), vecMushroom.get(a).getType(), vecMushroom.get(a).getLevel(), vecMushroom.get(a).getWaterAbsorb(), vecMushroom.get(a).getSeeds());
			System.out.println("|----------------------------------------------------------------------------|");
		}

		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		sc.nextLine();
	}

	public void startToPlant(){
		String inputType;
		String inputName;
		String inputPot;

		clear(0);

		do{

			System.out.print(" Choose Type [Flower | Mushroom] : ");
			inputType = sc.nextLine();

		}while(!inputType.equals("Flower") && !inputType.equals("Mushroom"));

		if((inputType.equals("Flower") && money < 150) || (inputType.equals("Mushroom") && money < 250)){
			System.out.println(" You don\'t have enough money");
			sc.nextLine();
			return;
		}

		do{
			System.out.print(" Input Plant Name [3..15 char | ends with \'" + inputType + "\'] : ");
			inputName = sc.nextLine();

		}while(!inputName.endsWith(inputType) && !inputName.endsWith(inputType));

		do{

			System.out.print(" Choose Plant Pot [ Box | Bottle ] : ");
			inputPot = sc.nextLine();

		}while(!inputPot.equals("Box") && !inputPot.equals("Bottle"));

		if(inputType.equals("Flower")){
			vecFlower.add(new Flower(inputName, inputPot, inputType));
			money -= 150;
		}else{
			vecMushroom.add(new Mushroom(inputName, inputPot, inputType));
			money -= 250;
		}

		System.out.println(" Successfully plant a new plant..");
		sc.nextLine();
	}

	public void waterPlant(){
		int inputPlantIndex;
		int inputChoice;

		clear(0);

		if(money < 25){
			System.out.println("You don\'t have enough money");
			sc.nextLine();
			return;
		}

		System.out.println(" Choose Plant that you want to watering :");
		System.out.println(" 1. Flower");
		System.out.println(" 2. Mushroom");
		System.out.println(" 3. Back");

		do{
			System.out.print(" Choose >> ");
			inputChoice = inputInt();
		}while(inputChoice < 1 || inputChoice > 3);

		clear(0);

		switch(inputChoice){
		case 1:

			if(vecFlower.size() == 0){
				System.out.println(" You don't have any flower(s)");
				sc.nextLine();
				return;
			}

			printFlowerList();

			do{

				System.out.printf(" Choose Plant [1..%d] : ", vecFlower.size());
				inputPlantIndex = inputInt();

			}while(inputPlantIndex < 1 || inputPlantIndex > vecFlower.size());
			
			vecFlower.get(inputPlantIndex - 1).addWaterAbsorb(randomIntRange(25, 50));
			
			vecFlower.get(inputPlantIndex - 1).checkWaterAbsorb();

			break;
		case 2:

			if(vecMushroom.size() == 0){
				System.out.println(" You don't have any mushroom(s)");
				sc.nextLine();
				return;
			}

			printMushroomList();

			do{

				System.out.printf(" Choose Plant [1..%d] : ", vecMushroom.size());
				inputPlantIndex = inputInt();

			}while(inputPlantIndex < 1 || inputPlantIndex > vecMushroom.size());

			vecMushroom.get(inputPlantIndex - 1).addWaterAbsorb(randomIntRange(25, 50));
			
			vecMushroom.get(inputPlantIndex - 1).checkWaterAbsorb();

			break;
		}

		System.out.println(" You have watered this plant. . .");

		money -= 25;

		
		sc.nextLine();
	}

	public void printFlowerList(){
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		System.out.println("| No. | Plant Name      | Pot      | PlantType     | PlantLvl | WaterAbsorb  |");
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		for(int a=0;a<vecFlower.size();a++){
			System.out.printf("| %-3d | %-15s | %-8s | %-13s | %-8d | %-12d |\n", a + 1, vecFlower.get(a).getName(), vecFlower.get(a).getPot(), vecFlower.get(a).getType(), vecFlower.get(a).getLevel(), vecFlower.get(a).getWaterAbsorb());
		}
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+\n\n");
	}

	public void printMushroomList(){
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		System.out.println("| No. | Plant Name      | Pot      | PlantType     | PlantLvl | WaterAbsorb  |");
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+");
		for(int a=0;a<vecMushroom.size();a++){
			System.out.printf("| %-3d | %-15s | %-8s | %-13s | %-8d | %-12d |\n", a + 1, vecMushroom.get(a).getName(), vecMushroom.get(a).getPot(), vecMushroom.get(a).getType(), vecMushroom.get(a).getLevel(), vecMushroom.get(a).getWaterAbsorb());
		}
		System.out.println("+======+======+======+======+======+======+======+======+======+======+======+\n\n");
	}

	public void clear(int milliseconds){
		for(int a=0;a<25;a++){
			System.out.print("\n");
			delay(milliseconds);
		}
	}

	public boolean isAlpha(String x){
		for(char a : x.toCharArray())
			if(!Character.isLetter(a))
				return false;
		return true;
	}

	public void delay(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public int inputInt(){
		int input = -1;

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