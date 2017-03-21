import java.util.Scanner;
import java.util.Vector;

/*

-Hierarchy-

(Class)Phone inherits -> (Class)CartItem

(Class)PhoneShopCustomer inherits 	-> (Class)User
(Class)PhoneShopCustomer implements -> (Interface)BlueJackPhoneShopCustomer

*/

class T122_Java2H3OnSite{

	private Scanner sc = new Scanner(System.in);
	private Vector<Phone> vecProduct = new Vector<Phone>();
	private PhoneShopCustomer user;
		
	public T122_Java2H3OnSite(){

		initProduct();

		showHomeMenu();

	}

	public static void main(String[] args){
		new T122_Java2H3OnSite();
	}

	public void initProduct(){
		vecProduct.add(new Phone(1, "Nexus 6", "Nexus", "Lollipop", 32, 6, 1200000, 7));
		vecProduct.add(new Phone(2, "Blackberry Bold", "Blackberry", "BB OS 6.0", 4, 3, 1450000, 10));
		vecProduct.add(new Phone(3, "iPhone 6S", "Apple", "iOS 7", 32, 5, 1600000, 9));
	}

	public void showHomeMenu(){
		int inputChoice;

		do{
			clear(0);
			System.out.println("  ____  _             _            _");
			System.out.println(" | __ )| |_   _  ___ (_) __ _  ___| | __");
			System.out.println(" |  _ \\| | | | |/ _ \\| |/ _` |/ __| |/ /");
			System.out.println(" | |_) | | |_| |  __/| | (_| | (__|   <");
			System.out.println(" |____/|_|\\__,_|\\___|/ |\\__,_|\\___|_|\\_\\");
			System.out.println(" |  _ \\| |__   ___ |__/_   ___");
			System.out.println(" | |_) | '_ \\ / _ \\| '_ \\ / _ \\");
			System.out.println(" |  __/| | | | (_) | | | |  __/");
			System.out.println(" |_|__ |_| |_|\\___/|_| |_|\\___|");
			System.out.println(" / ___|| |__   ___  _ __");
			System.out.println(" \\___ \\| '_ \\ / _ \\| '_ \\");
			System.out.println("  ___) | | | | (_) | |_) |");
			System.out.println(" |____/|_| |_|\\___/| .__/");
			System.out.println("                   |_|");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.print("Choice [1..3] : ");
			
			inputChoice = inputInt();


			switch(inputChoice){
			case 1:
				showRegisterMenu();
				break;
			case 2:
				showLoginMenu();
				break;
			}

		}while(inputChoice != 3);
	}

	public void showRegisterMenu(){
		String inputName;
		String inputPhone;
		String inputPassword;

		clear(0);

		if(user != null){

			System.out.println("You've already registered.");
			System.out.println("Please try to login.");
			sc.nextLine();
			return;

		}

		System.out.println("Registration\n");
		
		do{

			System.out.print("Input name [5..20][Must be alphabet] : ");
			inputName = sc.nextLine();

		}while(!isAlpha(inputName) || inputName.length() < 5 || inputName.length() > 20);

		do{
			System.out.print("Input phone [5..11] : ");
			inputPhone = sc.nextLine();
		}while(!isDigit(inputPhone) || inputPhone.length() < 5 || inputPhone.length() > 11);

		do{

			System.out.print("Input password [5..10] : ");
			inputPassword = sc.nextLine();

		}while(inputPassword.length() < 5 || inputPassword.length() > 10);

		do{

			System.out.print("Input confirm password [5..10] : ");

		}while(!inputPassword.equals(sc.nextLine()));

		clear(0);
		
		user = new PhoneShopCustomer(inputName, inputPhone, inputPassword);

		System.out.println("Register success.");
		System.out.println("Please Login.");

		sc.nextLine();
	}

	public void showLoginMenu(){
		String inputNameOrPhone;
		String inputPassword;


		if(user == null){

			System.out.println("Please register first.");
			sc.nextLine();
			return;

		}

		while(true){

			clear(0);

			System.out.print("Input name or phone to login : ");
			inputNameOrPhone = sc.nextLine();

			System.out.print("Input password : ");
			inputPassword = sc.nextLine();

			if(!user.matchUser(inputNameOrPhone, inputPassword))
				System.out.println("Incorrect Name/Phone or Password.");
			else
				break;

			sc.nextLine();

		}

		showUserMenu();
	}

	public void showUserMenu(){
		int inputChoice;

		do{
			clear(0);
			System.out.println("1. View Product");
			System.out.println("2. View Cart");
			System.out.println("3. Checkout");
			System.out.println("4. Back");

			do{

				System.out.print("Choice : ");
				inputChoice = inputInt();
				
			}while(inputChoice < 1 || inputChoice > 4);

			switch(inputChoice){
			case 1:
				viewProduct();
				break;
			case 2:
				viewCart();
				break;
			case 3:
				checkOut();
				break;
			}

		}while(inputChoice != 4);
	}

	public void checkOut(){
		int inputMoney;

		clear(0);

		if(user.cartIsEmpty()){
			System.out.println("Cart is empty, cannot checkout");
			sc.nextLine();
			return;
		}

		System.out.println("Order ID : " + user.getOrderID());
		System.out.println("Total Price : IDR " + user.getGrandTotal());
		do{

			System.out.print("Input your money [min : IDR " + user.getGrandTotal() + "] : ");
			inputMoney = inputInt();

		}while(inputMoney < user.getGrandTotal());

		System.out.println("Change : IDR " + (inputMoney - user.getGrandTotal()));

		for(int a=0;a<vecProduct.size();a++){
			Phone temp = vecProduct.get(a);

			temp.setQuantity(temp.getQuantity() - temp.getBookedQuantity());
			temp.setBookedQuantity(0);
		}

		user.emptyCart();

		System.out.println("\nThanks for purchasing!");

		sc.nextLine();

	}

	public void viewCart(){
		clear(0);

		if(user.cartIsEmpty()){
			System.out.println("Cart is empty");
			sc.nextLine();
			return;
		}

		System.out.printf("Cart %d of %d\n", user.getCartSize(), vecProduct.size());
		System.out.println("______________________________");
		System.out.println("|Product Name   |Price   |Qty|");
		System.out.println("______________________________");
		for(int a=0;a<user.getCartSize();a++)
			System.out.printf("|%-15s|%-8d|%-3d|\n", user.getCartItemName(a + 1), user.getCartItemPrice(a + 1), user.getCartItemQuantity(a + 1));
		System.out.println("______________________________");

		System.out.println("Order ID : " + user.getOrderID());
		System.out.println("Total Price : IDR " + user.getGrandTotal());
		sc.nextLine();
	}

	public void viewProduct(){
		int inputChoice;
		int inputProductIdx;
		int inputQuantity;
		String inputEmptyCart;

		do{


			clear(0);
			System.out.println("____________________________________________________________________________");
			System.out.println("|Product ID |Product Name   |Brand     |Memory  |OS       |Size |Price     |");
			System.out.println("____________________________________________________________________________");
			for(int a=0;a<vecProduct.size();a++)
				System.out.printf("|%-10d |%-15s|%-10s|%-8d|%-9s|%-5d|%-10d|\n", vecProduct.get(a).getProductID(), vecProduct.get(a).getName(), vecProduct.get(a).getBrand(), vecProduct.get(a).getMemory(), vecProduct.get(a).getOS(), vecProduct.get(a).getSize(), vecProduct.get(a).getPrice());
			System.out.println("____________________________________________________________________________");
			System.out.println("1. Add to cart");
			System.out.println("2. Remove all from cart");
			System.out.println("3. Back");

			do{

				System.out.print("Choice [1..3] : ");
				inputChoice = inputInt();

			}while(inputChoice < 1 || inputChoice > 3);

			switch(inputChoice){
			case 1:

				if(user.getCartSize() == vecProduct.size()){
					System.out.println("Cart is already full");
					sc.nextLine();
					break;
				}

				do{

					System.out.printf("Product ID [1..%d] : ", vecProduct.size());
					inputProductIdx = inputInt();

				}while(inputProductIdx < 1 || inputProductIdx > 3);
				
				if(user.containsProduct(vecProduct.get(inputProductIdx - 1))){
					clear(0);
					System.out.println("You have already add this product to cart.");
				}else{

					if(vecProduct.get(inputProductIdx - 1).getQuantity() == 0){

						System.out.println("Sorry, this product is out of stock");
					
					}else{

						do{

							System.out.printf("Input Quantity [Max : %d] : ", vecProduct.get(inputProductIdx - 1).getQuantity());
							inputQuantity = inputInt();

						}while(inputQuantity < 1 || inputQuantity > vecProduct.get(inputProductIdx - 1).getQuantity());
						
						user.addToCart(vecProduct.get(inputProductIdx - 1), inputQuantity);

						System.out.println("Success add to cart");
					}
				}

				sc.nextLine();
				break;
			case 2:

				if(user.cartIsEmpty()){
					System.out.println("Cart is still empty");
					sc.nextLine();
					break;
				}

				clear(0);

				do{

					System.out.print("Empty cart ?[yes/no] ");
					inputEmptyCart = sc.nextLine();

				}while(!inputEmptyCart.equals("yes") && !inputEmptyCart.equals("no"));

				if(inputEmptyCart.equals("yes")){
					user.emptyCart();
					System.out.println("Empty cart success");
					sc.nextLine();
				}

				break;
			}
		}while(inputChoice != 3);
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

	public boolean isDigit(String x){
		for(char a : x.toCharArray())
			if(!Character.isDigit(a))
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

}