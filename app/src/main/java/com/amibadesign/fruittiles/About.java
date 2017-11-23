package com.amibadesign.fruittiles;

import com.amibadesign.fruittiles.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;
 
public class About extends Activity {
	
	private boolean doubleBackToExitPressedOnce=false;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.about);		
		ImageView img = (ImageView) findViewById(R.drawable.thanks);
	}		
	
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
			Intent i = new Intent(getApplicationContext(), MainMenu.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getApplicationContext().startActivity(i);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click BACK again for Main Menu", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    } 	
 
}
