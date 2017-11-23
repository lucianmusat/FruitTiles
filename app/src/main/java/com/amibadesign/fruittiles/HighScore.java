package com.amibadesign.fruittiles;

import com.amibadesign.fruittiles.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class HighScore extends Activity {
	private static Context context;
	private boolean doubleBackToExitPressedOnce=false;
	Button button;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.highscore);
		HighScore.context = getApplicationContext();
		addListenerOnButton();
 
	}

	public void addListenerOnButton() {
 
		button = (Button) findViewById(R.id.reset);  
		TextView highscore = (TextView) findViewById(R.id.highscrtxt);
		TextView highscore1 = (TextView) findViewById(R.id.highscr1);
		TextView highscore2 = (TextView) findViewById(R.id.highscr2);
		TextView highscore3 = (TextView) findViewById(R.id.highscr3);
		TextView highscore4 = (TextView) findViewById(R.id.highscr4);
		TextView highscore5 = (TextView) findViewById(R.id.highscr5);
		
		String s = String.valueOf(getHighscore());
		String s1 = String.valueOf(getHighscore1());
		String s2 = String.valueOf(getHighscore2());
		String s3 = String.valueOf(getHighscore3());
		String s4 = String.valueOf(getHighscore4());
		String s5 = String.valueOf(getHighscore5());
		
		if(s!=null){
			highscore.setText("Overall highscore: "+s + " seconds");
			highscore1.setText("Level 1 highscore: "+s1 + " seconds");
			highscore2.setText("Level 2 highscore: "+s2 + " seconds");
			highscore3.setText("Level 3 highscore: "+s3 + " seconds");
			highscore4.setText("Level 4 highscore: "+s4 + " seconds");
			highscore5.setText("Level 5 highscore: "+s5 + " seconds");
		}
		
		
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				button.setBackgroundResource(R.drawable.reset_pressed);
				button.setHeight(59);
				button.setWidth(327);
				final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
		        handler.postDelayed(new Runnable() {
		            @Override
		            public void run() {
						setHighscore(0L);
						finish();
						startActivity(getIntent());
						Toast.makeText(context,"Highscores were reset!",Toast.LENGTH_SHORT).show();
		            }
		        }, 500);

			}
 
		});		 
	}
	
	public static long getHighscore() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore", 0);
	    return test;
	}
	
	public static long getHighscore1() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore4", 0);
	    return test;
	}
	public static long getHighscore2() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore16", 0);
	    return test;
	}
	public static long getHighscore3() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore3x4", 0);
	    return test;
	}
	public static long getHighscore4() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore4x4", 0);
	    return test;
	}
	public static long getHighscore5() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore4x5", 0);
	    return test;
	}
    private void setHighscore(long data) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context); 
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong("highscore4", data);
        editor.putLong("highscore16", data);
        editor.putLong("highscore2x3", data);
        editor.putLong("highscore3x4", data);
        editor.putLong("highscore4x4", data);
        editor.putLong("highscore4x5", data);
        editor.commit(); // Very important
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
