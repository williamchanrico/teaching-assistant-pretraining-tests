import java.util.Vector;


public class Order {
	public static int idNumber = 1;


	public String id;
	public Vector<Menu> menu;
	public int tableNumber;

	Order(){
		id = "OID" + String.format("%02d", idNumber++);
		menu = new Vector<Menu>();
	}

	public void addMenu(String name, int quantity){
		menu.add(new Menu(name, quantity));
	}
}
