class Phone extends CartItem{

	private String name;
	private String brand;
	private String os;
	private int memory;
	private int size;

	public Phone(int productID, String name, String brand, String os, int memory, int size, int price, int quantity){
		super(productID, price, quantity);
		this.name = name;
		this.brand = brand;
		this.os = os;
		this.memory = memory;
		this.size = size;
	}

	public String getName(){
		return name;
	}

	public String getBrand(){
		return brand;
	}

	public String getOS(){
		return os;
	}

	public int getMemory(){
		return memory;
	}

	public int getSize(){
		return size;
	}

}