package com.gt.brewmasters.structures;

import java.util.ArrayList;


public class Recipe {
//consists of ingredients
	private String name;
	private String description;
	//private String beerStyle;
	private int yieldSize;
	
	private int boilTemp;
	private int spargeTemp;
	private int mashTemp;
	
	private int boilDuration;
	private int spargeDuration;
	private int mashDuration;
	
	private Recipe(String name, ArrayList<Ingredient> ingredients) {
		for(int i=0; i<ingredients.size(); i++) {
			addIngredient(ingredients.get(i));
		}
	}
	
	private Recipe(String name, ArrayList<Ingredient> ingredients, String description) {
		
	}
	
	private void addIngredient(Ingredient ingredient) {
		
	}
	
}
