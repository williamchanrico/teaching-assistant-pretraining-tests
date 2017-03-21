import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;
import java.lang.String;


public class T122_JavaII_H1 {
	
	public static Scanner sc = new Scanner(System.in);
	public static Vector<Order> vecOrder = new Vector<Order>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int inputMenu;
		
		clear(0);
		printTitle(200);
		
		do{
			clear(0);
			printTitle(0);
			
			printOrderList();
			
			printMenu();
			
			do{
				
				System.out.print(" >> ");
				inputMenu = inputInt();
				
			}while(inputMenu < 1 || inputMenu > 6);
			
			switch(inputMenu){
			case 1:
				addOrder();
				break;
			case 2:
				changeOrderStatus();
				break;
			case 3:
				deleteDeliveredOrders();
				break;
			case 4:
				sortByStatus();
				break;
			case 5:
				clearOrderList();
				break;
			case 6:
				showExitSplash();
				System.exit(0);
				break;
			}
			
		}while(inputMenu != 6);
		
	}

	public static void clearOrderList(){
		String clear;

		clear(0);

		if(vecOrder.size() == 0){
			System.out.println(" No queue list");
			sc.nextLine();
			return;
		}

		do{
			System.out.print(" Are you sure want to clear all queue data?\n");
			System.out.print(" [Y/N | case sensitive] : ");
			clear = sc.nextLine();
		}while(!clear.equals("Y") && !clear.equals("N"));

		if(clear.equals("Y")){
			vecOrder.clear();
			System.out.println(" Cleared!");
			sc.nextLine();
		}


	}

	public static void sortByStatus(){
		boolean swapped = true;

		while(swapped){
			swapped = false;

			for(int a=1;a<vecOrder.size();a++){
				if(vecOrder.get(a - 1).menu.get(0).status.compareTo(vecOrder.get(a).menu.get(0).status) < 0){
					Collections.swap(vecOrder, a, a - 1);
					swapped = true;
				}
			}
		}
	}

	public static void deleteDeliveredOrders(){
		
		clear(0);

		if(vecOrder.size() == 0){
			System.out.println(" No queue list");
			sc.nextLine();
			return;
		}

		for(int a=0;a<vecOrder.size();a++){
			if(vecOrder.get(a).menu.get(0).status.equals("Delivered"))
				break;

			if(a == vecOrder.size() - 1){
				System.out.println(" No Delivered Food");
				sc.nextLine();
				return;
			}
		}

		for(int a=0;a<vecOrder.size();a++)
			if(vecOrder.get(a).menu.get(0).status.equals("Delivered")){
				vecOrder.removeElementAt(a);
				a--;
			}

		System.out.println(" Delivered food deleted");
		sc.nextLine();
	}

	public static void changeOrderStatus(){
		String id;

		clear(0);

		if(!printPendingOrderList())
			return;

		do{

			System.out.print(" Input OrderID [\'cancel\' to cancel]: ");
			id = sc.nextLine();

			for(int a=0;a<vecOrder.size();a++){

				if(vecOrder.get(a).id.equals(id)){
					for(int b=0;b<vecOrder.get(a).menu.size();b++)
						vecOrder.get(a).menu.get(b).status = "Delivered";
					System.out.println(" Changed");
					sc.nextLine();
					return;
				}

			}

		}while(!id.equals("cancel"));
	}

	public static void showExitSplash(){
		String[] exitSplash = new String[25];

		exitSplash[0] = "         ____    ___                                          __";
		exitSplash[1] = "        /\\  _`\\ /\\_ \\                     __                 /\\ \\";
		exitSplash[2] = "        \\ \\ \\L\\ \\//\\ \\    __  __     __  /\\_\\     __      ___\\ \\ \\/'\\";
		exitSplash[3] = "         \\ \\  _ <'\\ \\ \\  /\\ \\/\\ \\  /'__`\\\\/\\ \\  /'__`\\   /'___\\ \\ , <";
		exitSplash[4] = "          \\ \\ \\L\\ \\\\_\\ \\_\\ \\ \\_\\ \\/\\  __/ \\ \\ \\/\\ \\L\\.\\_/\\ \\__/\\ \\ \\\\`\\";
		exitSplash[5] = "           \\ \\____//\\____\\\\ \\____/\\ \\____\\_\\ \\ \\ \\__/.\\_\\ \\____\\\\ \\_\\ \\_\\";
		exitSplash[6] = "            \\/___/ \\/____/ \\/___/  \\/____/\\ \\_\\ \\/__/\\/_/\\/____/ \\/_/\\/_/";
		exitSplash[7] = "                                         \\ \\____/";
		exitSplash[8] = "                                          \\/___/";
		exitSplash[9] = "                         ____ .________         ____";
		exitSplash[10] = "                        /_   ||   ____/        /_   |";
		exitSplash[11] = "                         |   ||____  \\   ______ |   |";
		exitSplash[12] = "                         |   |/       \\ /_____/ |   |";
		exitSplash[13] = "                         |___/______  /         |___|";
		exitSplash[14] = "                                    \\/";
		exitSplash[15] = "=Keep Fighting and Share Our Greatest Skill=";

		clear(0);

		for(int a=0;a<15;a++){
			System.out.println(exitSplash[a]);
			delay(250);
		}

		System.out.print("                ");
		for(int a=0;a<exitSplash[15].length();a++){
			System.out.print(exitSplash[15].charAt(a));
			delay(100);
		}

		sc.nextLine();
	}

	public static void addOrder(){
		String more;
		String menu;
		int quantity;
		
		Order newOrder = new Order();

		clear(0);

		do{

			System.out.print(" Input table no [1-10]: ");
			newOrder.tableNumber = inputInt();
			
			if(newOrder.tableNumber == -1){
				System.out.println(" Input must be number!\n");
			}

		}while(newOrder.tableNumber < 1 || newOrder.tableNumber > 10);
		
		while(true){


			do{

				System.out.print(" Input menu [5..15]: ");
				menu = sc.nextLine();

			}while(menu.length() < 5 || menu.length() > 15);


			do{

				System.out.print(" Input qty [1-20]: ");
				quantity = inputInt();
				
				if(quantity == -1){
					System.out.println(" Input must be number!\n");
				}

			}while(quantity < 1 || quantity > 20);

			newOrder.addMenu(menu, quantity);

			do{

				System.out.print(" Add more menu ? [Y/N | case insensitive]: ");
				more = sc.nextLine();

			}while(!more.equalsIgnoreCase("Y") && !more.equalsIgnoreCase("N"));

			if(more.equalsIgnoreCase("N")){
				vecOrder.add(newOrder);
				break;
			}
		}
	}
	
	public static int inputInt(){
		int input = -1;
		
		try{
			
			input = sc.nextInt();
			
		}catch(Exception e){
			
			input = -1;
			
		}
		
		sc.nextLine();

		return input;
	}

	public static void printTitle(int milliseconds){
		String[] title = new String[25];

	    title[0] = "                   ___         __           _                              _";
	    title[1] = "          /\\/\\    /   \\___    /__\\ ___  ___| |_ __ _ _   _ _ __ __ _ _ __ | |_";
	    title[2] = "         /    \\  / /\\ / __|  / \\/// _ \\/ __| __/ _` | | | | '__/ _` | '_ \\| __|";
	    title[3] = "        / /\\/\\ \\/ /_//\\__ \\ / _  \\  __/\\__ \\ || (_| | |_| | | | (_| | | | | |_";
	    title[4] = "        \\/    \\/___,' |___/ \\/ \\_/\\___||___/\\__\\__,_|\\__,_|_|  \\__,_|_| |_|\\__|\n";

	    title[5] = "        ,---.                        ,---.          |";
	    title[6] = "        |   |.   .,---..   .,---.    `---.,   .,---.|--- ,---.,-.-.";
	    title[7] = "        |   ||   ||---'|   ||---'        ||   |`---.|    |---'| | |";
	    title[8] = "        `---\\`---'`---'`---'`---'    `---'`---|`---'`---'`---'` ' '";
	    title[9] = "                                          `---'";
		
		for(int a=0;a<10;a++){

			System.out.println(title[a]);
			delay(milliseconds);

		}
	}

	public static void clear(int milliseconds){
		for(int a=0;a<25;a++){
			System.out.print("\n");
			delay(milliseconds);
		}
	}

	public static void delay(int milliseconds){
		try{

			Thread.sleep(milliseconds);

		}catch(Exception e){
			
			e.printStackTrace();
		
		}
	}

	public static void printMenu(){
		System.out.println(" 1. Add Queue");
		System.out.println(" 2. Change Status");
		System.out.println(" 3. Delete Delivered Queue");
		System.out.println(" 4. Sort by Status");
		System.out.println(" 5. Clear Queue List");
		System.out.println(" 6. Exit");
	}

	public static void printOrderList(){

		if(vecOrder.size() == 0){
			System.out.print(" No queue list\n\n");
			return;
		}
		
		System.out.print(" +-----+---------+------------+-----------------+--------+-----------------+\n");
		System.out.print(" | No. | OrderID | Table No   | Menu            | Qty    | Status          |\n");
		System.out.print(" +-----+---------+------------+-----------------+--------+-----------------+\n");
		for(int a=0;a<vecOrder.size();a++){
			System.out.printf(" | %-3d | %-7s | %-10d | %-15s | %-6d | %-15s |\n", a + 1, vecOrder.get(a).id, vecOrder.get(a).tableNumber, vecOrder.get(a).menu.get(0).name, vecOrder.get(a).menu.get(0).quantity, vecOrder.get(a).menu.get(0).status);
			if(vecOrder.get(a).menu.size() > 0){
				
				for(int b=1;b<vecOrder.get(a).menu.size();b++){
					System.out.printf(" |     |         |            | %-15s | %-6d | %-15s |\n", vecOrder.get(a).menu.get(b).name, vecOrder.get(a).menu.get(b).quantity, vecOrder.get(a).menu.get(b).status);
				}
				
			}
			System.out.print(" +-----+---------+------------+-----------------+--------+-----------------+\n");
		}
	}

	public static boolean printPendingOrderList(){
		boolean allFoodDelivered = true;

		for(int a=0;a<vecOrder.size();a++){
			if(vecOrder.get(a).menu.get(0).status.equals("Pending")){
				allFoodDelivered = false;
				break;
			}
		}

		if(allFoodDelivered){
			System.out.println(" All food have been delivered");
			sc.nextLine();
			return false;
		}
		
		System.out.print(" +-----+---------+------------+-----------------+--------+-----------------+\n");
		System.out.print(" | No. | OrderID | Table No   | Menu            | Qty    | Status          |\n");
		System.out.print(" +-----+---------+------------+-----------------+--------+-----------------+\n");
		for(int a=0;a<vecOrder.size();a++){
			if(vecOrder.get(a).menu.get(0).status.equals("Pending")){
				
				System.out.printf(" | %-3d | %-7s | %-10d | %-15s | %-6d | %-15s |\n", a + 1, vecOrder.get(a).id, vecOrder.get(a).tableNumber, vecOrder.get(a).menu.get(0).name, vecOrder.get(a).menu.get(0).quantity, vecOrder.get(a).menu.get(0).status);
				
				if(vecOrder.get(a).menu.size() > 0){
					for(int b=1;b<vecOrder.get(a).menu.size();b++)
						System.out.printf(" |     |         |            | %-15s | %-6d | %-15s |\n", vecOrder.get(a).menu.get(b).name, vecOrder.get(a).menu.get(b).quantity, vecOrder.get(a).menu.get(b).status);
				}
				System.out.print(" +-----+---------+------------+-----------------+--------+-----------------+\n");
			}
		}
		
		return true;
	}



}
