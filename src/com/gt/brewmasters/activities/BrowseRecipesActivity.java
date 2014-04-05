package com.gt.brewmasters.activities;

import com.gt.brewmasters.R;

import android.app.Activity;
import android.os.Bundle;

public class BrowseRecipesActivity extends Activity{
	// Debugging
    private static final String TAG = "Brewmaster";
    private static final boolean D  = true;
    //for example....
    //if(D) Log.d(TAG, "not connected");
	
	private static Brewmasters appContext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //get context for global application vars
        appContext = Brewmasters.getAppContext();
        
        initUi();
        
    }
    
    public void initUi(){
    	setContentView(R.layout.activity_login);
    }
}
