class Kiosk implements Runnable{
	private boolean open = false;
	private Asset asset;
	private int typeCode;
	private int profit;
	private int money;
	
	public Kiosk(Asset asset, int typeCode){
		this.asset = asset;
		this.typeCode = typeCode;
		this.money = 0;
	}

	public int stop(){
		open = false;
		return money;
	}

	public void run(){
		open = true;
		while(open){
			switch(typeCode){
			case 0:
				clear(0);
				profit = asset.getValue() / 500;
				System.out.printf("Received $%d.\n", profit);
				System.out.println("Press enter to finish the service.");
				money += profit;
				delay(1000);
				break;
			case 1:

				if(((Store)asset).getStock() == 0){
					System.out.println("Sold Out.");
					System.out.println("Press enter to finish the service.");
					return;
				}

				clear(0);
				profit = asset.getValue() / 200;
				System.out.printf("Sold a product for $%d.\n", profit);
				System.out.println("Press enter to finish the service.");
				((Store)asset).decreaseStock(1);
				money += profit;
				delay(1000);
				break;
			case 2:
				System.out.println("Feature not yet implemented");
				return;
			}
		}
	}

	public void clear(int milliseconds){
		for(int a=0;a<25;a++){
			System.out.println();
			delay(milliseconds);
		}
	}

	public void delay(int milliseconds){
		try{
			Thread.sleep(milliseconds);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}