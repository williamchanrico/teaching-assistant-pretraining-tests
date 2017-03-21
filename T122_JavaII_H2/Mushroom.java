class Mushroom extends Plant{
	private int seeds;
	private static int totalMushroom = 0;

	Mushroom(String name, String pot, String type){
		super(name, pot, type);
		this.totalMushroom++;
		this.seeds = 0;
		setMaxWaterAbsorb(500);
	}

	public static int getTotalMushroom(){
		return totalMushroom;
	}

	public int getSeeds(){
		return seeds;
	}

	public void checkWaterAbsorb(){
		if(getWaterAbsorb() >= getMaxWaterAbsorb() * getLevel()){
			setWaterAbsorb(0);
			levelUp();
			System.out.println(" Your plant level up");
			if(getLevel() % 2 == 0){
				seeds += (seeds * getLevel()) + 3;
			}
		}
	}
}