import java.util.*;
import java.lang.*;

public class T122_JavaI_H2{

	static Scanner sc = new Scanner(System.in);
	static User user = new User();

	public static void main(String[] args){
		int input;
		boolean registered = false;

		do{

			printTitle();
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");

			do{
				System.out.print("Choose : ");
				input = inputInt();
			}while(input < 1 || input > 3);

			switch(input){
				case 1:
					boolean usernameCheck = false;
					boolean passwordCheck = false;

					clear(0);

					printTitle();

					if(!registered){
						System.out.print("\nThere\'s no data, register first!");
					}else{
						System.out.print("Username : ");
						usernameCheck = (user.username.equals(sc.nextLine()) ? true : false);
						System.out.print("Password : ");
						passwordCheck = (user.password.equals(sc.nextLine()) ? true : false);
						if(!usernameCheck || !passwordCheck){
							System.out.print("\nInvalid Username Password!");
						}else{
							System.out.print("\nSuccess Login!");
						}
					}
					sc.nextLine();

					if(usernameCheck && passwordCheck){
						mainMenu();
					}

					break;
				case 2:
					if(registered){
						System.out.print("You already registered!");
						sc.nextLine();
					}else{

						printTitle();

						System.out.println("Please fill the data first");
						System.out.println("==========================");

						do{
							System.out.print("Input your username[5..20 | must be Alphanumeric] : ");
							user.username = sc.nextLine();
						}while(user.username.length() < 5 || user.username.length() > 20);

						do{
							System.out.print("Input your password[5..20 | must be Alphanumeric] : ");
							user.password = sc.nextLine();
						}while(user.password.length() < 5 || user.password.length() > 20);

						do{
							System.out.print("Input your age[17..50] : ");
							user.age = inputInt();
						}while(user.age < 17 || user.age > 50);

						do{
							System.out.print("Input your address[must be started with 'St.' or must be ended with 'Street'] : ");
							user.address = sc.nextLine();
						}while(!user.address.startsWith("St.") && !user.address.endsWith("Street"));

						do{
							System.out.print("Input your email[must be ended with '@yahoo.com' or '@gmail.com'] : ");
							user.email = sc.nextLine();
						}while(!user.email.endsWith("@yahoo.com") && !user.email.endsWith("@gmail.com"));
						
						do{
							System.out.print("Input your hobby[5..10 characters] : ");
							user.hobby = sc.nextLine();
						}while(user.hobby.length() < 5 || user.hobby.length() > 10);

						System.out.print("\nRegister Success!");
						registered = true;
						sc.nextLine();
					}
					break;
				case 3:
					clear(0);
					printExitMessage();
					sc.nextLine();
					break;

			}
		}while(input != 3);

	}

	public static void mainMenu(){
		int inputMenu;
		int inputIdx;
		int inputMoney;
		String inputBuy;

		do{
			
			clear(0);
			printShopTitle();

			System.out.println("Welcome, " + user.username);
			System.out.println("1. View Profile");
			System.out.println("2. Buy Product");
			System.out.println("3. View Product");
			System.out.println("4. Insert Product");
			System.out.println("5. Update Product");
			System.out.println("6. Delete Product");
			System.out.println("7. Exit");

			do{
				System.out.print("Choose : ");
				inputMenu = inputInt();
			}while(inputMenu < 1 || inputMenu > 7);

			switch(inputMenu){
				case 1:
					clear(0);
					printShopTitle();
					System.out.println("View Profile");
					System.out.println("============");
					System.out.println("Username        : " + user.username);
					System.out.println("Age             : " + user.age);
					System.out.println("Address         : " + user.address);
					System.out.println("Email           : " + user.email);
					System.out.println("Hobby           : " + user.hobby);
					sc.nextLine();
					break;
				case 2:
					clear(0);
					printShopTitle();
					System.out.println("Buy product");
					System.out.println("===========");
					if(user.vecShoppingCart.size() != 0){
						System.out.println("==============================================================");
						System.out.println("| No |Product Name|     Price     | Quantity |   Sub Total   |");
						System.out.println("==============================================================");
						for(int a=0;a<user.vecShoppingCart.size();a++){
							System.out.printf("| %-2d |%-12s| %-13d |    %-5d | %-13d |\n", a + 1, user.vecShoppingCart.get(a).name, user.vecShoppingCart.get(a).price, user.vecShoppingCart.get(a).quantity, user.vecShoppingCart.get(a).price * user.vecShoppingCart.get(a).quantity);
						}
						System.out.print("==============================================================\n");
						System.out.printf("|                  Grand Total               | %-13d |\n", user.grandTotal);
						System.out.print("==============================================================\n");
						do{
							System.out.print("Do  you want to buy it[Yes|No] : ");
							inputBuy = sc.nextLine();
						}while(!inputBuy.equals("Yes") && !inputBuy.equals("No"));

						if(inputBuy.equals("Yes")){
							do{
								System.out.print("Input your money : ");
								inputMoney = inputInt();
								if(inputMoney < user.grandTotal){
									System.out.println("Not enough money!");
									sc.nextLine();
								}
							}while(inputMoney < user.grandTotal);

							System.out.printf("Your change is : Rp. %d\n", calculateChange(user.grandTotal, inputMoney));
							System.out.printf("\nThank you %s, come again.", user.username);
						}else{
							System.out.printf("\nThank for coming %s.", user.username);
						}
						user.vecShoppingCart.removeAllElements();
						user.grandTotal = 0;
					}else{
						System.out.print("\nThere\'s no product, insert product first!");
					}
					sc.nextLine();
					break;
				case 3:
					clear(0);
					printShopTitle();
					System.out.println("View product");
					System.out.println("============");
					if(user.vecShoppingCart.size() != 0){
						System.out.println("==============================================================");
						System.out.println("| No |Product Name|     Price     | Quantity |   Sub Total   |");
						System.out.println("==============================================================");
						for(int a=0;a<user.vecShoppingCart.size();a++){
							System.out.printf("| %-2d |%-12s| %-13d |    %-5d | %-13d |\n", a + 1, user.vecShoppingCart.get(a).name, user.vecShoppingCart.get(a).price, user.vecShoppingCart.get(a).quantity, user.vecShoppingCart.get(a).price * user.vecShoppingCart.get(a).quantity);
						}
						System.out.print("==============================================================\n");
						System.out.printf("|                  Grand Total               | %-13d |\n", user.grandTotal);
						System.out.print("==============================================================\n");
					}else{
						System.out.print("\nThere\'s no product, insert product first!");
					}
					sc.nextLine();
					break;
				case 4:
					clear(0);
					printShopTitle();

					Product newProduct = new Product();

					System.out.println("Insert product");
					System.out.println("==============");
					
					do{
						System.out.print("Input your product name[Computer|Laptop|Keyboard|Harddisk] : ");
						newProduct.name = sc.nextLine();
					}while(!newProduct.name.equals("Computer") && !newProduct.name.equals("Laptop") && !newProduct.name.equals("Keyboard") && !newProduct.name.equals("Harddisk"));
					
					do{
						System.out.print("How many do you want to buy the product[1-20] : ");
						newProduct.quantity = inputInt();
					}while(newProduct.quantity < 1 || newProduct.quantity > 20);

					newProduct.setPrice();

					user.vecShoppingCart.add(newProduct);

					user.grandTotal += (newProduct.price * newProduct.quantity);

					System.out.println("\nInsert Success!");

					sc.nextLine();
					break;
				case 5:
					clear(0);
					printShopTitle();
					System.out.println("Update product");
					System.out.println("==============");
					if(user.vecShoppingCart.size() != 0){
						System.out.println("==============================================================");
						System.out.println("| No |Product Name|     Price     | Quantity |   Sub Total   |");
						System.out.println("==============================================================");
						for(int a=0;a<user.vecShoppingCart.size();a++){
							System.out.printf("| %-2d |%-12s| %-13d |    %-5d | %-13d |\n", a + 1, user.vecShoppingCart.get(a).name, user.vecShoppingCart.get(a).price, user.vecShoppingCart.get(a).quantity, user.vecShoppingCart.get(a).price * user.vecShoppingCart.get(a).quantity);
						}
						System.out.print("==============================================================\n");
						do{
							System.out.printf("Input index [1..%d] : ", user.vecShoppingCart.size());
							inputIdx = inputInt();
						}while(inputIdx < 1 || inputIdx > user.vecShoppingCart.size());

						user.grandTotal -= user.vecShoppingCart.get(inputIdx - 1).price * user.vecShoppingCart.get(inputIdx - 1).quantity;

						do{
							System.out.print("Input your product name[Computer|Laptop|Keyboard|Harddisk] : ");
							user.vecShoppingCart.get(inputIdx - 1).name = sc.nextLine();
						}while(!user.vecShoppingCart.get(inputIdx - 1).name.equals("Computer") && !user.vecShoppingCart.get(inputIdx - 1).name.equals("Laptop") && !user.vecShoppingCart.get(inputIdx - 1).name.equals("Keyboard") && !user.vecShoppingCart.get(inputIdx - 1).name.equals("Harddisk"));
						
						do{
							System.out.print("How many do you want to buy the product[1-20] : ");
							user.vecShoppingCart.get(inputIdx - 1).quantity = inputInt();
						}while(user.vecShoppingCart.get(inputIdx - 1).quantity < 1 || user.vecShoppingCart.get(inputIdx - 1).quantity > 20);

						user.vecShoppingCart.get(inputIdx - 1).setPrice();

						user.grandTotal += user.vecShoppingCart.get(inputIdx - 1).price * user.vecShoppingCart.get(inputIdx - 1).quantity;

						System.out.print("\nUpdate Success!");
					}else{
						System.out.print("\nThere\'s no product, insert product first!");
					}
					sc.nextLine();
					break;
				case 6:
					clear(0);
					printShopTitle();
					System.out.println("Delete product");
					System.out.println("==============");
					if(user.vecShoppingCart.size() != 0){
						System.out.println("==============================================================");
						System.out.println("| No |Product Name|     Price     | Quantity |   Sub Total   |");
						System.out.println("==============================================================");
						for(int a=0;a<user.vecShoppingCart.size();a++){
							System.out.printf("| %-2d |%-12s| %-13d |    %-5d | %-13d |\n", a + 1, user.vecShoppingCart.get(a).name, user.vecShoppingCart.get(a).price, user.vecShoppingCart.get(a).quantity, user.vecShoppingCart.get(a).price * user.vecShoppingCart.get(a).quantity);
						}
						System.out.print("==============================================================\n");
						do{
							System.out.printf("Input index [1..%d] : ", user.vecShoppingCart.size());
							inputIdx = inputInt();
						}while(inputIdx < 1 || inputIdx > user.vecShoppingCart.size());

						user.grandTotal -= user.vecShoppingCart.get(inputIdx - 1).price * user.vecShoppingCart.get(inputIdx - 1).quantity;

						user.vecShoppingCart.remove(inputIdx - 1);

						System.out.print("\nDelete Success!");
					}else{
						System.out.print("\nThere\'s no product, insert product first!");
					}
					sc.nextLine();
					break;
			}

		}while(inputMenu != 7);

	}

	public static int calculateChange(int grandTotal, int inputMoney){
		System.out.print("Calculating");
		for(int a=0;a<4;a++){
			delay(250);
			System.out.print(".");
		}
		System.out.print("\n");
		return (inputMoney - grandTotal);
	}

	public static void printShopTitle(){
		System.out.println(" ____ ! _            _            _");
		System.out.println("|  _ \\| |          (_)          | |");
		System.out.println("| |_) | |_   _  ___ _  __ _  ___| | __");
		System.out.println("|  _ <| | | | |/ _ \\ |/ _` |/ __| |/ /");
		System.out.println("| |_) | | |_| |  __/ | (_| | (__|   <");
		System.out.println("|____/|_|\\__,_|\\___| |\\__,_|\\___|_|\\_\\");
		System.out.println("                  _/ |");
		System.out.println("                 |__/");
		System.out.println("  ____  _          _    _               _");
		System.out.println(" / __ \\| |        | |  | |             | |");
		System.out.println("| |  | | |  ______| |__| | __ _ _ __ __| |_      ____ _ _ __ ___");
		System.out.println("| |  | | | |______|  __  |/ _` | '__/ _` \\ \\ /\\ / / _` | '__/ _ \\");
		System.out.println("| |__| | |____    | |  | | (_| | | | (_| |\\ V  V / (_| | | |  __/");
		System.out.println(" \\____/|______|   |_|  |_|\\__,_|_|  \\__,_| \\_/\\_/ \\__,_|_|  \\___|");
		System.out.println("  _____ _");
		System.out.println(" / ____| |");
		System.out.println("| (___ | |__   ___  _ __");
		System.out.println(" \\___ \\| '_ \\ / _ \\| '_ \\");
		System.out.println(" ____) | | | | (_) | |_) |");
		System.out.println("|_____/|_| |_|\\___/| .__/");
		System.out.println("                   | |");
		System.out.println("                   |_|");
	}

	public static void printExitMessage(){
		System.out.println("               _______ _                 _     __     __");
		System.out.println("              |__   __| |               | |    \\ \\   / /");
		System.out.println("      ______     | |  | |__   __ _ _ __ | | __  \\ \\_/ /__  _   _   ______");
		System.out.println("     |______|    | |  | '_ \\ / _` | '_ \\| |/ /   \\   / _ \\| | | | |______|");
		System.out.println("                 | |  | | | | (_| | | | |   <     | | (_) | |_| |");
		System.out.println("                 |_|  |_| |_|\\__,_|_| |_|_|\\_\\    |_|\\___/ \\__,_|");
	}

	public static void printTitle(){
		System.out.println(" ____ ! _            _            _");
		System.out.println("|  _ \\| |          (_)          | |");
		System.out.println("| |_) | |_   _  ___ _  __ _  ___| | __");
		System.out.println("|  _ <| | | | |/ _ \\ |/ _` |/ __| |/ /");
		System.out.println("| |_) | | |_| |  __/ | (_| | (__|   <");
		System.out.println("|____/|_|\\__,_|\\___| |\\__,_|\\___|_|\\_\\");
		System.out.println("                  _/ |");
		System.out.println("                 |__/");
		System.out.println("  ____  _          _    _               _");
		System.out.println(" / __ \\| |        | |  | |             | |");
		System.out.println("| |  | | |  ______| |__| | __ _ _ __ __| |_      ____ _ _ __ ___");
		System.out.println("| |  | | | |______|  __  |/ _` | '__/ _` \\ \\ /\\ / / _` | '__/ _ \\");
		System.out.println("| |__| | |____    | |  | | (_| | | | (_| |\\ V  V / (_| | | |  __/");
		System.out.println(" \\____/|______|   |_|  |_|\\__,_|_|  \\__,_| \\_/\\_/ \\__,_|_|  \\___|");
		System.out.println("  _____ _");
		System.out.println(" / ____| |");
		System.out.println("| (___ | |__   ___  _ __");
		System.out.println(" \\___ \\| '_ \\ / _ \\| '_ \\");
		System.out.println(" ____) | | | | (_) | |_) |");
		System.out.println("|_____/|_| |_|\\___/| .__/");
		System.out.println("                   | |");
		System.out.println("                   |_|");
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

	public static int inputInt(){
		int input;

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

}

class User{
	String username;
	String password;
	String address;
	String email;
	String hobby;
	int age;
	int grandTotal;
	Vector<Product> vecShoppingCart;

	User(String username, int age, String address, String email, String hobby){
		this.username = username;
		this.age = age;
		this.address = address;
		this.email = email;
		this.hobby = hobby;
		grandTotal = 0;
		vecShoppingCart = new Vector<Product>();
	}

	User(){
		vecShoppingCart = new Vector<Product>();
		grandTotal = 0;
	}
}

class Product{
	String name;
	int price;
	int quantity;

	public void setPrice(){
		if(name.equals("Computer")){
			price = 500000;
		}else if(name.equals("Laptop")){
			price = 4000000;
		}else if(name.equals("Keyboard")){
			price = 400000;
		}else if(name.equals("Harddisk")){
			price = 200000;
		}
	}
}