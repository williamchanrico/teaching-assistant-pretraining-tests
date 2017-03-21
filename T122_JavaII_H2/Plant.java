class Plant{
	private String name;
	private String pot;
	private String type;
	private int level;
	private int waterAbsorb;
	private int maxWaterAbsorb;

	Plant(){

	}

	Plant(String name, String pot, String type){
		this.name = name;
		this.pot = pot;
		this.type = type;
		this.level = 1;
		this.waterAbsorb = 0;
	}

	public void checkWaterAbsorb(){
		if(waterAbsorb >= maxWaterAbsorb * level){
			waterAbsorb = 0;
			level++;
			System.out.println(" Your plant level up");
		}
	}

	public String getName(){
		return name;
	}

	public int getLevel(){
		return level;
	}

	public String getPot(){
		return pot;
	}

	public int getWaterAbsorb(){
		return waterAbsorb;
	}

	public int getMaxWaterAbsorb(){
		return maxWaterAbsorb;
	}

	public String getType(){
		return type;
	}

	public void setMaxWaterAbsorb(int maxWaterAbsorb){
		this.maxWaterAbsorb = maxWaterAbsorb;
	}

	public void addWaterAbsorb(int waterAbsorb){
		this.waterAbsorb += waterAbsorb;
	}

	public void setWaterAbsorb(int waterAbsorb){
		this.waterAbsorb = waterAbsorb;
	}

	public void levelUp(){
		level++;
	}

}