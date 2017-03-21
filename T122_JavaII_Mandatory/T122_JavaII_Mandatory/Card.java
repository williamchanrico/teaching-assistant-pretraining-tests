abstract class Card{
	private static int numOfCards = 0;
	private String name;
	private String id;
	private String type;
	private int price;
	private int no;

	public Card(String name, int price){
		this.no = numOfCards;
		this.name = name;
		this.price = price;
	}

	public void setType(String type){
		this.type = type;
	}

	public void setID(String type){
		if(type.equals("Monster"))
			this.id = String.format("MN%03d", ++numOfCards);
		else
			this.id = String.format("MG%03d", ++numOfCards);
	}

	public String getType(){
		return type;
	}

	public static int getNumOfCards(){
		return numOfCards;
	}

	public String getID(){
		return id;
	}

	public int getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public int getNo(){
		return no;
	}
}