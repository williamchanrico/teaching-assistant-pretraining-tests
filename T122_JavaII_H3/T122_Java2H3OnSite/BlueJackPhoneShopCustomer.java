interface BlueJackPhoneShopCustomer{

	void emptyCart();
	boolean cartIsEmpty();
	void addToCart(Phone phone, int quantity);
	String getCartItemName(int productID);
	int getCartItemPrice(int productID);
	int getCartItemQuantity(int productID);
	int getCartSize();
	int getGrandTotal();
	
}