class Pizza{
	private String name;
	private int price;
	private int cost;
	private int tomatoes;
	private int meats;
	private int vegetables;

	public Pizza(String name, int price, int tomatoes, int meats, int vegetables){
		this.name = name;
		this.price = price;
		this.tomatoes = tomatoes;
		this.meats = meats;
		this.vegetables = vegetables;
		cost = 2 + ((tomatoes / 2) + (meats / 2) + (vegetables / 2));
	}

	public String getName(){
		return name;
	}

	public int getPrice(){
		return price;
	}

	public int getTomatoes(){
		return tomatoes;
	}

	public int getMeats(){
		return meats;
	}

	public int getVegetables(){
		return vegetables;
	}

	public int getCost(){
		return cost;
	}
}