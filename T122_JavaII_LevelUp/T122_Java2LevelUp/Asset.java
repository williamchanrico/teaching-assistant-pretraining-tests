abstract class Asset{
	String name;
	String type;
	int value;

	public Asset(String name, String type, int value){
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName(){
		return name;
	}

	public String getType(){
		return type;
	}

	public int getTypeCode(){
		if(type.equals("Service"))
			return 0;
		else if(type.equals("Store"))
			return 1;
		else if(type.equals("Factory"))
			return 2;
		return -1;
	}

	public int getValue(){
		return value;
	}

}