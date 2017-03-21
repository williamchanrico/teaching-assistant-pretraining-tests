import java.util.Vector;
import java.util.Random;

class PhoneShopCustomer extends User implements BlueJackPhoneShopCustomer{

	private Random random = new Random();
	private String orderID;
	private String phone;
	private int grandTotal;
	private Vector<Phone> vecCart;
	
	public PhoneShopCustomer(String name, String phone, String password){
		super(name, password);
		grandTotal = 0;
		orderID = String.format("%c%c%c-%02d", Character.toUpperCase(name.charAt(0)), Character.toUpperCase(name.charAt(1)), Character.toUpperCase(name.charAt(2)), random.nextInt(100));
		this.phone = phone;
		vecCart = new Vector<Phone>();
	}

	public String getOrderID(){
		return orderID;
	}

	public int getGrandTotal(){
		return grandTotal;
	}

	public void addToCart(Phone phone, int quantity){
		phone.setBookedQuantity(quantity);
		vecCart.add(phone);
		grandTotal += phone.getPrice() * quantity;
	}

	public String getCartItemName(int productID){
		return vecCart.get(productID - 1).getName();
	}

	public int getCartItemPrice(int productID){
		return vecCart.get(productID - 1).getPrice();
	}

	public int getCartItemQuantity(int productID){
		return vecCart.get(productID - 1).getBookedQuantity();
	}

	public boolean cartIsEmpty(){
		return vecCart.isEmpty();
	}

	public int getCartSize(){
		return vecCart.size();
	}

	public void emptyCart(){
		vecCart.clear();
		grandTotal = 0;
	}

	public boolean containsProduct(Phone phone){
		return vecCart.contains(phone);
	}

	public boolean matchUser(String nameOrPhone, String password){
		return ((getName().equals(nameOrPhone) || phone.equals(nameOrPhone)) && getPassword().equals(password)) ? true : false;
	}

	public String getPhone(){
		return phone;
	}

	public Vector<Phone> getCart(){
		return vecCart;
	}

}