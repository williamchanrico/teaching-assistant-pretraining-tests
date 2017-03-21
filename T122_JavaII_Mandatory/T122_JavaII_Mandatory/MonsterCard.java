class MonsterCard extends Card{

	private int star;
	private int attack;
	private int defence;

	public MonsterCard(String name, int price, int star, int attack, int defence){
		super(name, price);
		this.star = star;
		this.attack = attack;
		this.defence = defence;
		setType("Monster");
		setID("Monster");
	}

	public int getStar(){
		return star;
	}

	public int getAttack(){
		return attack;
	}

	public int getDefence(){
		return defence;
	}
}