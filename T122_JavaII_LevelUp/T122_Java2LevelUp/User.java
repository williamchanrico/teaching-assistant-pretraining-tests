class User{
	String name;
	int money;

	public User(String name, int money){
		this.name = name;
		this.money = money;
	}

	public String getName(){
		return name;
	}

	public void addMoney(int money){
		this.money += money;
	}

	public int getMoney(){
		return money;
	}
}