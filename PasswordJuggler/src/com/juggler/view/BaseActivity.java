package com.juggler.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.juggler.dao.PasswordDAO;
import com.juggler.dao.PasswordDbHelper;
import com.juggler.domain.NewPassword;
import com.juggler.utils.LoginAuthHandler;

public class BaseActivity extends Activity implements OnClickListener,OnTouchListener{

	protected Button bNext,bPrev,bDelete;
	protected PasswordDAO passDao;
	protected PasswordDbHelper myDatabaseAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //set up database for use
		passDao = new PasswordDAO();
		myDatabaseAdapter = PasswordDbHelper.getInstance(this);
		passDao.setSQLiteDatabase(myDatabaseAdapter.getDatabase());
        initialize();
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onRetainNonConfigurationInstance()
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
		lah.setDidRotate(true);
	    return super.onRetainNonConfigurationInstance();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		//check for login required again
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
		//Log.v("DID ROTATE: ",lah.isDidRotate()+"");
		if(lah.showLoginScreen())
		{
			lah.setLoginRequired(false);
		    //pop login window
			if(passDao.checkForPassword().getCount() > 0 )
			{
				if(!lah.isDidRotate())
				{
					lah.setDidRotate(false);
					startActivity(new Intent(this,LoginView.class));
				}
				else
					lah.setDidRotate(false);
			}
			else
				startActivity(new Intent(this,CreateLoginPasswordActivity.class));
		} 
		lah.setLoginRequired(true);
	    super.onResume();
	}
	
	private void initialize() {
		bNext = (Button)findViewById(R.id.butNext);
		bNext.setOnClickListener(this);
		bNext.setOnTouchListener(this);
		
		bPrev = (Button)findViewById(R.id.butPrev);
		bPrev.setOnClickListener(this);
		bPrev.setOnTouchListener(this);
		
		bDelete = (Button)findViewById(R.id.butDelete);
		if(bDelete != null)
		{
			bDelete.setOnClickListener(this);
			bDelete.setOnTouchListener(this);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	public boolean onTouch(View v, MotionEvent event) {
		/*this is required to reset boolean on every action if the 
		activty is stoped with out this set the login screen shows*/
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
	 	lah.setLoginRequired(false);
		
		if (v instanceof Button) {
			
			if(v==bNext)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.next_button_on);
					((Button)v).setPadding(10,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.next_button);
					((Button)v).setPadding(10,0,10,0);
				}
			}else if(v==bPrev){
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.prev_button_on);
					((Button)v).setPadding(20,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.prev_button);
					((Button)v).setPadding(20,0,10,0);
				}
			}else if(v == bDelete)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((Button)v).setBackgroundResource(R.drawable.delete_entry_on);
					((Button)v).setPadding(10,0,10,0);
				} 
				else{
					((Button)v).setBackgroundResource(R.drawable.delete_entry);
					((Button)v).setPadding(10,0,10,0);
				}
			}
		}
		
		
		return false;
	}
	
	 /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
    	
    	/*this is required to reset boolean on every action if the 
		activty is stoped with out this set the login screen shows*/
		LoginAuthHandler lah = LoginAuthHandler.getInstance(this);
	 	lah.setLoginRequired(false);
 	   	
 	   if(v == bPrev){
 		   NewPassword np = NewPassword.getInstance();
 		   np.clear();
 		   finish();
 	   }
    }
}
