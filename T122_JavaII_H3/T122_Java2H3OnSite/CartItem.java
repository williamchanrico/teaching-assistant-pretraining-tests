class CartItem{

	private int productID;
	private int price;
	private int quantity;
	private int bookedQuantity;

	public CartItem(int productID, int price, int quantity){
		this.productID = productID;
		this.price = price;
		this.quantity = quantity;
		bookedQuantity = 0;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setBookedQuantity(int bookedQuantity){
		this.bookedQuantity = bookedQuantity;
	}

	public int getBookedQuantity(){
		return bookedQuantity;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getProductID(){
		return productID;
	}

	public int getPrice(){
		return price;
	}

	public void setProductID(int productID){
		this.productID = productID;
	}

	public void setPrice(int price){
		this.price = price;
	}
	
}