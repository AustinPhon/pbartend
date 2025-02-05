package com.juggler.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.juggler.dao.QuiresDAO;
import com.juggler.domain.NewPassword;
import com.juggler.utils.Constants;
import com.juggler.utils.CustomCursorAdapter;
import com.juggler.utils.LoginAuthHandler;

public class WalletCatListActivity extends FooterListActivity implements OnTouchListener{
	
	private Intent intent;
	private static final int INTENT_NEXT_SCREEN = 0;
	private Button butPrev;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.list_frame);
		super.onCreate(savedInstanceState);
		
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
	    initialize();
	    super.onResume();
	}
	
	private void initialize() {
		
		//set screen type
		Constants.SCREEN_TYPE=Constants.HOME;
		   
		//set title
		Intent selectedIntent = getIntent();
		CharSequence text =  selectedIntent.getCharSequenceExtra(Constants.INTENT_EXTRA_SELECTED_TEXT);
		TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
		tvTitle.setText(text);
		
		// hide next button
		Button next = (Button) findViewById(R.id.butNext);
		next.setVisibility(View.GONE);
		butPrev = (Button) findViewById(R.id.butPrev);
		butPrev.setOnClickListener(this);
		butPrev.setOnTouchListener(this);
		
		Cursor recordscCursor = passDao.getCategories();
		String[] from = new String[] { QuiresDAO.COL_NAME };
		int[] to = new int[] { R.id.list_row};
    	CustomCursorAdapter records = new CustomCursorAdapter(this,
				R.layout.list_item, recordscCursor, from, to);
    	setListAdapter(records);
		/*setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, baseCats));*/
		getListView().setTextFilterEnabled(true);
		//set next intent
		intent = new Intent(this, WalletSubCatListActivity.class);
	}
	
	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {
		/*this is required to reset boolean on every action if the 
		activty is stoped with out this set the login screen shows*/
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
	 	lah.setLoginRequired(false);
	    super.onListItemClick(l, v, position, id);

	    NewPassword np = NewPassword.getInstance();
	    np.catId = id;
	    
	    v.setBackgroundResource(R.drawable.sm_item_spacer_arw_slct);
	    v.setPadding(15, 10,10,10);
	    
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_ROW, id);
	    intent.putExtra(Constants.INTENT_EXTRA_SELECTED_TEXT,((TextView)v).getText());
		startActivityForResult(intent, INTENT_NEXT_SCREEN);
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		if (v instanceof Button) {
			
			if(v == butPrev)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.prev_button_on);
					((Button)v).setPadding(20,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.prev_button);
					((Button)v).setPadding(20,0,10,0);
				}
			}
		}
		
		
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		super.onClick(v);
		if (v == butPrev) {
			finish();
		}
	}
}