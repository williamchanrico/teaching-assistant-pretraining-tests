import java.util.Vector;

class User implements SluGiOh_Player{
	private static int numOfUsers = 0;
	private String id;
	private String username;
	private String password;
	private String email;
	private int money;
	private Vector<Card> deck;

	public User(String username, String email, String password, int money, Vector<Card> deck){
		this.id = String.format("SL%03d", ++numOfUsers);
		this.username = username;
		this.password = password;
		this.email = email;
		this.money = money;
		this.deck = deck;
	}

	public User(String username, String email, String password, int money, Vector<Card> deck, String id){
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.money = money;
		this.deck = deck;
	}

	public User copy(){
		return new User(username, email, password, money, deck, id);
	}

	public void showDeck(){
		System.out.println("---------------------------------------------------------------------");
		System.out.println("|No   |Type      |Card Name                                         |");
		System.out.println("---------------------------------------------------------------------");
		for(int a=0;a<deck.size();a++)
			System.out.printf("|%-5d|%-10s|%-50s|\n", a + 1, deck.get(a).getType().equals("Monster") ? "Monster" : "Magic", deck.get(a).getName());
		System.out.println("---------------------------------------------------------------------");
	}

	public void buyCard(Card card){
		deck.add(card);
		money -= card.getPrice();
	}

	public Vector<Card> getDeck(){
		return deck;
	}

	public boolean hasCard(Card card){
		return deck.contains(card);
	}

	public String getUsername(){
		return username;
	}

	public String getEmail(){
		return email;
	}

	public String getPassword(){
		return password;
	}

	public int getMoney(){
		return money;
	}

	public String getID(){
		return id;
	}

	public static int getNumOfUsers(){
		return numOfUsers;
	}

	public void setMoney(int money){
		this.money = money;
	}

	public boolean matchUser(String username, String password){
		return (this.username.equals(username) && this.password.equals(password)) ? true : false;
	}

}