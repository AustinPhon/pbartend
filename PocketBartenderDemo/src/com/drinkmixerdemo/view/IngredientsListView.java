/**
 * 
 */
package com.drinkmixerdemo.view;

import com.drinkmixerdemo.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.drinkmixerdemo.dao.DataDAO;
import com.drinkmixerdemo.dao.DrinkListDAO;
import com.drinkmixerdemo.dao.IngredientsDAO;
import com.drinkmixerdemo.domain.NewDrinkDomain;
import com.drinkmixerdemo.domain.ScreenType;


/**
 * @author Darren
 * GETS A SET LIST OF CATEGORIES
 */
public class IngredientsListView extends ListViews {

	protected IngredientsDAO dataDAO = new IngredientsDAO();
	protected final String TYPE_LIQUOR = "Liquor";
	protected final String TYPE_MIXERS = "Mixers";
	protected final String TYPE_GARNISH = "Garnish";
	protected ScreenType ingtype = ScreenType.getInstance();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentListActivity(this);
        
        //check to see if this is new if yes change intent
        if(ScreenType.getInstance().screenType == ScreenType.SCREEN_TYPE_NEW)
        	intent = new Intent(this, AmountPicker.class);
        else
        {
        	intent = new Intent(this, DrinkListView.class);
        	ScreenType.getInstance().screenType=(ScreenType.SCREEN_TYPE_ING);
        }
    }
    
   protected void initComponents() throws Exception{
    	dataDAO.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
    	Cursor recordscCursor = dataDAO.retrieveAllIngredients(ingtype.type);
    	startManagingCursor(recordscCursor);
    	String[] from = new String[] { DrinkListDAO.COL_CAT_NAME };
		int[] to = new int[] { R.id.tfName};
    	SimpleCursorAdapter records = new SimpleCursorAdapter(this,
				R.layout.item_row, recordscCursor, from, to);
    	
		setListAdapter(records);
	}
   
   @Override
   protected void onListItemClick(ListView l, View v, int position, long id) {
   	Cursor cursor = (Cursor) l.getItemAtPosition(position);
   	//set id and name to create domain
   	NewDrinkDomain ndd = NewDrinkDomain.getInstance();
   	ndd.ingredientsName=(cursor.getString(cursor.getColumnIndexOrThrow(DataDAO.COL_CAT_NAME)));
   	super.onListItemClick(l, v, position, id);
   }
   

}
