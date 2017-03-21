class Store extends Asset{
	int stock;

	public Store(String name, String type, int value, int stock){
		super(name, type, value);
		this.stock = stock;
	}

	public int getStock(){
		return stock;
	}

	public void decreaseStock(int x){
		stock -= x;
	}
}