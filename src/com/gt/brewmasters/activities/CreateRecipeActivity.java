package com.gt.brewmasters.activities;

import java.io.File;
import java.util.ArrayList;

import com.gt.brewmasters.R;
import com.gt.brewmasters.structures.Ingredient;
import com.gt.brewmasters.utils.IngredientListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class CreateRecipeActivity extends Activity implements OnClickListener {
	// Debugging
    private static final String TAG = "Brewmaster";
    private static final boolean D  = true;
    //for example....
    //if(D) Log.d(TAG, "not connected");
    
    private static final int ADD_INGREDIENT=1;
	
	private static Brewmasters appContext;
	private Button addIngredient;
	private ListView ingredientListView;
	IngredientListAdapter adapter;
	ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //get context for global application vars
        appContext = Brewmasters.getAppContext();
        
        initUi();
        
    }
    
    public void initUi(){
    	setContentView(R.layout.activity_create_recipe);
    	
    	View header = getLayoutInflater().inflate(R.layout.create_recipe_header, null); 
    	View footer = getLayoutInflater().inflate(R.layout.create_recipe_footer, null); 
    	
    	ingredientListView = (ListView) findViewById(R.id.recipe_ingredient_list);
    	ingredientListView.addHeaderView(header);
    	ingredientListView.addFooterView(footer);
    	
    	addIngredient = (Button) this.findViewById(R.id.recipe_add_ingredient_button);
    	addIngredient.setOnClickListener(this);
    	
    	updateIngredientList();
    	
    }
    
    public void updateIngredientList() {
    	//setup the listView
    	ListView ingredients = (ListView) this.findViewById(R.id.recipe_ingredient_list);
    	
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter(R.layout.profile_info_row);
		adapter = new IngredientListAdapter(this, R.layout.ingredient_row, ingredientList);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		ingredients.setAdapter(adapter);
		
//		for( int i = 0; i <ingredientList.size(); i++) {
//			Log.v(TAG, i+" " +ingredientList.get(i).getName());
//		}
    }
    
    public void onRemoveClick(View v) {
	    int pos = (Integer)v.getTag();
	    Ingredient ingredientRemove = adapter.getItem(pos);
	    adapter.remove(ingredientRemove);
	    updateIngredientList();
    }
    
    //Ensures that all information to create a recipe has been entered before allowing the user to proceed
//    private Boolean checkValidInput() {
//    	Boolean retVal = false;
//
//    	//required fields
//    	name   = ingredientName.getText().toString();
//    	amount = Integer.valueOf(unitAmount.getText().toString());
//    	
//    	unit = (Integer) unitSpinner.getSelectedItem();
//    	ingredientType = (Integer) typeSpinner.getSelectedItem();
//    	
//    	//Check that a value was entered for the name and amount fields
//    	if(!name.contentEquals("") && amount>0) {
//    		retVal = true;
//    	}
//    	
//    	return retVal;
//    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode != RESULT_OK)
			return;

		switch(requestCode)
		{

		case ADD_INGREDIENT:
			if (D) Log.v(TAG, "back from add ingredient");
        	if(data!=null) {
        		Bundle extras = data.getExtras();
        		Bundle ingredientInfo = extras.getBundle("ingredient");
        		//if (D) Log.v(TAG, ingredientInfo.toString());
        		
            	String name =  ingredientInfo.getString("name");
            	int amount = ingredientInfo.getInt("amount");
            	String unit = ingredientInfo.getString("unit");
            	String ingredientType = ingredientInfo.getString("ingredientType");
            	
            	Ingredient ingredient = new Ingredient(name, ingredientType, amount, unit);
            	//Log.v(TAG, ingredient.toString());
            	//Log.v(TAG, "just created");
            	ingredientList.add(ingredient);
            	updateIngredientList();
        		
        	}
			break;
			
		}		
	}    

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.recipe_add_ingredient_button:
				Intent myIntent = new Intent(this, AddIngredientActivity.class);
				this.startActivityForResult(myIntent, ADD_INGREDIENT);
				break;
		}
		
	}
}
