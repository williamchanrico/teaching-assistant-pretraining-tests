import java.util.Vector;
import java.util.Scanner;
import java.util.Random;

class Kiosk implements Runnable{
	User user;
	Booth booth;
	Vector<Pizza> pizza;
	boolean open;


	public Kiosk(User user, Booth booth, Vector<Pizza> pizza){
		this.user = user;
		this.booth = booth;
		this.pizza = pizza;
		open = false;
	}

	public void openKiosk(){
		open = true;
		while(open){
			System.out.printf("%s's %s's %s is selling...\n", user.getStoreName(), booth.getName(), pizza.get(randomIntRange(0, pizza.size() - 1)).getName());
		}
	}

	public void closeKiosk(){
		open = false;
	}

	public void run(){
		Scanner sc = new Scanner(System.in);

		openKiosk();

	}

	public int randomIntRange(int min, int max){
		Random random = new Random();

		return random.nextInt(max - min + 1) + min;
	}
}