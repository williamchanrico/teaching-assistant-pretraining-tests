class SpellCard extends Card{

	private String description;
	private int damage;
	private int allEnemyHeroTarget; 	// All Target = 1 | Enemy = 2 | Hero = 3
	private int monsterLifepointTarget; // Monster = 1 | Lifepoint = 2
	private int singleMultiTarget;  	// All Target or Lifepoint = 0 | Single = 1 | Multi = 2

	public SpellCard(String name, int price, String description, int damage, int allEnemyHeroTarget, int monsterLifepointTarget, int singleMultiTarget){
		super(name, price);
		this.description = description;
		this.damage = damage;
		this.allEnemyHeroTarget = allEnemyHeroTarget;
		this.monsterLifepointTarget = monsterLifepointTarget;
		this.singleMultiTarget = singleMultiTarget;
		setType("Spell");
		setID("Spell");
	}

	public String getDescription(){
		return description;
	}

	public int getDamage(){
		return damage;
	}

	public int getAllEnemyHeroTarget(){
		return allEnemyHeroTarget;
	}

	public int getMonsterLifepointTarget(){
		return monsterLifepointTarget;
	}

	public int getSingleMultiTarget(){
		return singleMultiTarget;
	}
}