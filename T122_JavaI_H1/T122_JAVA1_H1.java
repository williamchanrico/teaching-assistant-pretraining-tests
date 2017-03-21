import java.util.*;
import java.lang.Character;
import java.lang.String;
import java.lang.StringBuffer;
import java.lang.Math;

public class T122_JAVA1_H1{

	public static void showExitSplash(){
		System.out.println(" ,-_/,.           .   ,.   ,   ,.       .       .-,--.");
		delay(100);
		System.out.println(" ' |_|/ ,-. ,-. ,-|   `|  /|  / ,-. ,-. | ,      '|__/ ,-. ,-. ,-. . ,-. ,-.");
		delay(100);
		System.out.println("  /| |  ,-| |   | |    | / | /  | | |   |<       ,|    ,-| `-. `-. | | | | |");
		delay(100);
		System.out.println("  `' `' `-^ '   `-^    `'  `'   `-' '   ' ` :;   `'    `-^ `-' `-' ' `-' ' '");
		delay(100);
		System.out.println("                                            '\n");
		delay(100);
		System.out.println("           .   ,-,-,-.       .         ,-_/ .    ,-_/,.\n");
		delay(100);
		System.out.println(" ,-. ,-. ,-|   `,| | |   ,-. | , ,-.   '  | |-   ' |_|/ ,-. ,-. ,-. ,-. ,-. ,-.\n");
		delay(100);
		System.out.println(" ,-| | | | |     | ; | . ,-| |<  |-'   .^ | |     /| |  ,-| | | | | |-' |   | |\n");
		delay(100);
		System.out.println(" `-^ ' ' `-^     '   `-' `-^ ' ` `-'   `--' `'    `' `' `-^ |-' |-' `-' '   ' '\n");
		delay(100);
		System.out.println("                                                ? BlueJack 14-NULL ?");
	}

	public static void printMenu(){
		System.out.println("1. View Travelling Packet");
		System.out.println("2. Booking");
		System.out.println("3. Exit");
	}
	public static void delay(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void clear(int delay){
		for(int a=0;a<25;a++){
			System.out.print("\n");
			delay(delay);
		}
	}

	public static void showOpening(){
		System.out.println("/__   \\_ __ __ ___   _____| |   / __\\___ _ __ | |_ ___ _ __");
		delay(100);
		System.out.println("  / /\\/ '__/ _` \\ \\ / / _ \\ |  / /  / _ \\ '_ \\| __/ _ \\ '__|");
		delay(100);
		System.out.println(" / /  | | | (_| |\\ V /  __/ | / /__|  __/ | | | ||  __/ |");
		delay(100);
		System.out.println(" \\/   |_|  \\__,_| \\_/ \\___|_| \\____/\\___|_| |_|\\__\\___|_|");
		delay(100);
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		boolean valid;
		String name = "";
		String temp[];
		String bookingCode = "";
		String origin = "";
		String destination = "";
		String reserve = "";
		int input;
		int balance = 20000000;
		int numOfPeople = 0;
		int travelCost;
		int packagePrice;
		double weight = 0;

		clear(0);

		showOpening();

		do{
			valid = true;
			System.out.print(" What's your name[3-20]? ");
			name = sc.nextLine();

			temp = name.split(" ");

			if(temp.length == 2){

				if(Character.isLowerCase(temp[0].charAt(0)) || Character.isLowerCase(temp[1].charAt(0))){
					System.out.println(" Username must be started with capital letter");
					valid = false;
				}

			}else{

				System.out.println(" Username must be two words");
				valid = false;

			}

		}while(name.length() < 3 || name.length() > 20 || !valid || temp.length != 2);

		Random random = new Random();

		bookingCode = temp[0].charAt(0) + "" + temp[1].charAt(0) + "" + (random.nextInt(900) + 100);

		clear(10);

		System.out.printf(" Hi, %s! Welcome to Travel Center\n\n", name);

		sc.nextLine();

		do{
			int choice;

			clear(10);
			printMenu();
			do{
				System.out.print(" Input[1-3]: ");
				try{
					input = sc.nextInt();
				}catch(Exception e){
					input = -1;
					System.out.printf(" Input must be number\n\n");
				}
				sc.nextLine();
			}while(input < 1 || input > 3);

			switch(input){
				case 1:


					do{
						clear(10);
						System.out.println(" 1. National Package");
						System.out.println(" 2. International Package");
						System.out.println(" 3. Back");

						do{
							System.out.print(" Input[1-3]: ");
							try{
								choice = sc.nextInt();
							}catch(Exception e){
								choice = -1;
								System.out.printf(" Input must be number\n\n");
							}
							sc.nextLine();
						}while(choice < 1 || choice > 3);



						if(choice == 1){
							clear(0);
							System.out.printf(" %c Pari Island %c\n", 3, 3);
							System.out.printf(" =======================\n");
							System.out.printf(" Pari Island or pulau pari is part of  Thousand Islands (Indonesian: Kepulauan Seribu).Located 1-2 hours from jakarta, Pulau pari which part of Kepulauan Seribu are a chain of islands to the north of Jakarta?s coast. It forms the only regency of Jakarta the capital of Indonesia.Pulau pari is very beautiful and offer many attraction or activities for tourism.\n The packet is include:\n");
							System.out.printf(" %c Google and Snorkel\n", 4);
							System.out.printf(" %c Live Vest (Rompi Penyelamat/Pelampung)\n", 4);
							System.out.printf(" %c Fin (Sepatu Katak)\n", 4);
							System.out.printf(" %c Rent traditional boat (perahu) for Snorkeling(10 person)\n", 4);
							System.out.printf(" Only 400.000 IDR/3 days\n\n");
							System.out.printf(" %c Raja Ampat Island %c\n", 3, 3);
							System.out.printf(" Located off the northwest tip of Bird's Head Peninsula on the island of New Guinea, in Indonesia's West Papua province.The main occupation for people around this area is fishing since the area is dominated by the sea.Although traditional culture still strongly exists, they are very welcoming to visitors. The packet is include:\n");
							System.out.printf(" %c Accommodation 2 nights at Frewen guest house Waigeo Raja Ampat\n", 4);
							System.out.printf(" %c 1 night at Royal Membramo  kota Sorong\n", 4);
							System.out.printf(" %c Transport at Sorong + Transport by Fast boat\n", 4);
							System.out.printf(" %c Meals as per program\n", 4);
							System.out.printf(" %c Carter outrigger boat during tour on the island\n", 4);
							System.out.printf(" Only 1.400.000 IDR/3 days\n\n");
							sc.nextLine();
						}else if(choice == 2){
							clear(0);
							System.out.printf(" %c Singapore Trip %c\n", 3, 3);
							System.out.printf(" =======================\n");
							System.out.printf(" Singapore is a city state on an island at the southern tip of the Malay peninsula. The town dates back to the 5th century, but its main development started with the arrival of Stamford Raffles in 1819. Since then Singapore has rapidly become a major business and trading centre, and has now the most active port in SE Asia. It is also a shoppers paradise, with a huge concentration of shopping complexes.\n The packet is include:\n");
							System.out.printf(" %c Chinatown Trip\n", 4);
							System.out.printf(" %c Gardens by the bay Camp\n", 4);
							System.out.printf(" %c Marina bay Photo\n", 4);
							System.out.printf(" %c Singapore river Trip\n", 4);
							System.out.printf(" Only 3.350.000 IDR/week\n\n");
							System.out.printf(" %c Australia Trip %c\n", 3, 3);
							System.out.printf(" Australia is a beautiful country that attracts millions of visitors each year. Popular tourist destinations like Sydney and Melbourne offer visitors world-class sightseeing, but there are less popular travel destinations in Australia that are often underrated and overlooked. Take a look at seven Australian destinations that deserve a lot more attention.\n The packet is include:\n");
							System.out.printf(" %c Adelaide Trip\n", 4);
							System.out.printf(" %c Darwin Garden Trip\n", 4);
							System.out.printf(" %c Broome Swim\n", 4);
							System.out.printf(" %c Tasmania Camp\n", 4);
							System.out.printf(" Only 10.000.000 IDR/6 days\n\n");
							sc.nextLine();
						}
					}while(choice != 3);
					break;
				case 2:

					System.out.printf(" Hi Te Tes,Your balance is %d. You may interested with our packet.\n", balance);
					System.out.println(" Let's book yours!");
					sc.nextLine();
					do{
						clear(10);
						System.out.println(" 1. Pari Island");
						System.out.println(" 2. Raja Ampat Island");
						System.out.println(" 3. Singapore");
						System.out.println(" 4. Australia");
						System.out.println(" 5. Back");

						do{
							System.out.print(" Input[1-5]: ");
							try{
								choice = sc.nextInt();
							}catch(Exception e){
								choice = -1;
								System.out.printf(" Input must be number\n\n");
							}
							sc.nextLine();
						}while(choice < 1 || choice > 5);


						if(choice != 5){
							clear(0);
							do{
								System.out.print(" Origin [must end with 'Place']: ");
								origin = sc.nextLine();
								if(origin.length() < 5 || origin.length() > 25){
									System.out.println(" Length must be between 5-25 characters");
								}else if(!origin.endsWith("Place")){
									System.out.println(" Must end with \"Place\"");
								}
							}while(origin.length() < 5 || origin.length() > 25 || !origin.endsWith("Place"));

							System.out.println(" ---------------------------------");
							System.out.println(" |Baggage Weight(kg)    |  Price |");
							System.out.println(" ---------------------------------");
							System.out.println(" |      1.5             | 250000 |");
							System.out.println(" |      2.5             | 450000 |");
							System.out.println(" |       5              | 600000 |");
							System.out.println(" ---------------------------------");

							do{
								System.out.print(" Baggage Weight[1.5][2.5][5]: ");
								try{
									weight = sc.nextDouble();
								}catch(Exception e){
									weight = -1;
									System.out.println(" Input must be number");
								}
								sc.nextLine();
							}while(weight != 1.5 && weight != 2.5 && weight != 5);

							do{
								System.out.print(" Number of people [1-50]: ");
								try{
									numOfPeople = sc.nextInt();
								}catch(Exception e){
									numOfPeople = -1;
									System.out.println(" Input must be number!");
								}
								sc.nextLine();
							}while(numOfPeople < 1 || numOfPeople > 50);
						}

						if(choice == 1){
							destination = "Pari Island";
							packagePrice = 400000;
							travelCost = Math.abs(11 - (origin.length() - 5));
						}else if(choice == 2){
							destination = "Raja Ampat Island";
							packagePrice = 1400000;
							travelCost = Math.abs(17 - (origin.length() - 5));
						}else if(choice == 3){
							destination = "Singapore";
							packagePrice = 3350000;
							travelCost = Math.abs(9 - (origin.length() - 5));
						}else if(choice == 4){
							destination = "Australia";
							packagePrice = 10000000;
							travelCost = Math.abs(9 - (origin.length() - 5));
						}else{
							destination = "";
							packagePrice = 0;
							travelCost = 0;
						}

						if(choice != 5){
							travelCost = travelCost * (weight == 1.5? 250000 : (weight == 2.5 ? 450000 : 600000));
							travelCost += packagePrice * numOfPeople;

							clear(10);

							System.out.println(" Your Detail Transaction");
							System.out.println(" --------------------------------------------");
							System.out.println("  Destination      : " + destination);
							System.out.println("  Origin           : " + origin);
							System.out.println("  Baggage Weight   : " + weight);
							System.out.println("  Number of People : " + numOfPeople);
							System.out.println("  The total price is " + travelCost);

							do{
								System.out.print(" Are you sure for this reservation?[Y/N]: ");
								reserve = sc.nextLine();
							}while(!reserve.equals("Y") && !reserve.equals("N"));

							if(reserve.equals("Y")){

								if(travelCost > balance){
									System.out.println(" Sorry your balance is not enough.");
								}else{
									int payment;
									do{
										System.out.printf(" Payment [%d IDR]: ", travelCost);
										try{
											payment = sc.nextInt();
										}catch(Exception e){
											payment = -1;
											System.out.print(" Sorry, the total price is " + travelCost + " IDR");
										}
										sc.nextLine();
									}while(payment != travelCost);

									balance -= payment;

									System.out.printf("\n\n Your remaining balance is %d IDR\n\n", balance);

									System.out.printf(" Thanks %s for your reservation.\n\n", name);

									System.out.printf(" Your booking code is %s\n\n", bookingCode);

									System.out.printf(" Have a nice day %s\n\n", name);

								}
								sc.nextLine();
							}
						}
					}while(choice != 5);
					break;
				case 3:
					clear(0);
					showExitSplash();
					break;
			}
		}while(input != 3);

	}
}