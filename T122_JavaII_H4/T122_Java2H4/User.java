import java.util.Vector;

class User{
	private String storeName;
	private int money;
	
	public User(String storeName, int money){
		this.storeName = storeName;
		this.money = money;
	}

	public String getStoreName(){
		return storeName;
	}

	public int getMoney(){
		return money;
	}

}