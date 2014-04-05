package com.gt.brewmasters.structures;

public class Ingredient {
	
	private String name;
	
	private String type;
	private static final int HOPS   = 1;
	private static final int BARLEY = 2;
	private static final int YEAST  = 3;
	private static final int OTHER  = 4;
	
	private String typeName;
	private int amount;
	 
	private  String unit;
	private static final int OZ   = 1;
	private static final int LB   = 2;
	private static final int TBSP = 3;
	private static final int TSP  = 4;
	private static final int QTS  = 5;
	private static final int CUPS = 6;
	
	public Ingredient(String name, String type, int amount, String unit) {
		this.name = name;
		this.type=type;
		this.amount=amount;
		this.unit=unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return this.name+" "+ this.type+" "+this.amount+ " " + this.unit;
	}
	
}
