class Booth{
	private String name;
	private int size;
	private int fame;

	public Booth(String name, int size, int fame){
		this.name = name;
		this.size = size;
		this.fame = fame;
	}

	public String getName(){
		return name;
	}

	public int getSize(){
		return size;
	}

	public int getFame(){
		return fame;
	}
}