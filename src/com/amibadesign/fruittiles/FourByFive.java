package com.amibadesign.fruittiles;

import java.util.Random;

import com.amibadesign.fruittiles.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

public class FourByFive extends Activity {

	public static int flipped=0;
	public static TileFour firstTile=null;
	public static TileFour secondTile=null;
	private static Context context;
	public static int active_tiles=20;
	public static long startTime;
	public static long stopTime;
	public static long elapsed;	
	public static TileFour tile1;
	public static TileFour tile2;
	public static TileFour tile3;
	public static TileFour tile4;
	public static TileFour tile5;
	public static TileFour tile6;
	public static TileFour tile7;
	public static TileFour tile8;
	public static TileFour tile9;
	public static TileFour tile10;
	public static TileFour tile11;
	public static TileFour tile12;
	public static TileFour tile13;
	public static TileFour tile14;
	public static TileFour tile15;
	public static TileFour tile16;
	public static TileFour tile17;
	public static TileFour tile18;
	public static TileFour tile19;
	public static TileFour tile20;
	public static ImageView highscoreImg;
	private boolean doubleBackToExitPressedOnce=false;
	
	public static Context getContext(){
		return context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		FourByFive.context = getApplicationContext();
		setContentView(R.layout.fourbyfive);		
		int[] valori = {0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,5,5,3,3};				
		startTime=System.nanoTime();
		highscoreImg = (ImageView) findViewById(R.id.HighScore);

		//randomly scramble the values vector
		Random rgen = new Random();
		for (int i=0; i < valori.length; i++) {
		int randomPosition = rgen.nextInt(valori.length);
		int temp = valori[i];
		valori[i] = valori[randomPosition];
		valori[randomPosition] = temp;
		}
		//end scramble
		
		//create the 16 tiles
		for (int i=0; i < 20; i++)
		{

			switch(i){

				case 0: tile1 = new TileFour(this, R.id.imageView1,valori[i]); break;
				case 1: tile2 = new TileFour(this, R.id.imageView2,valori[i]); break;
				case 2:	tile3 = new TileFour(this, R.id.imageView3,valori[i]);break;
				case 3:	tile4 = new TileFour(this, R.id.imageView4,valori[i]);break;
				
				case 4: tile5 = new TileFour(this, R.id.imageView5,valori[i]);break;
				case 5: tile6 = new TileFour(this, R.id.imageView6,valori[i]);break;
				case 6:	tile7 = new TileFour(this, R.id.imageView7,valori[i]);break;
				case 7: tile8 = new TileFour(this, R.id.imageView8,valori[i]);break;
				
				case 8: tile9 = new TileFour(this, R.id.imageView9,valori[i]);break;
				case 9: tile10 = new TileFour(this, R.id.imageView10,valori[i]);break;
				case 10: tile11 = new TileFour(this, R.id.imageView11,valori[i]);break;
				case 11: tile12 = new TileFour(this, R.id.imageView12,valori[i]);break;
				
				case 12: tile13 = new TileFour(this, R.id.imageView13,valori[i]);break;
				case 13: tile14 = new TileFour(this, R.id.imageView14,valori[i]);break;
				case 14: tile15 = new TileFour(this, R.id.imageView15,valori[i]);break;
				case 15: tile16 = new TileFour(this, R.id.imageView16,valori[i]);break;	
				
				case 16: tile16 = new TileFour(this, R.id.imageView17,valori[i]);break;	
				case 17: tile16 = new TileFour(this, R.id.imageView18,valori[i]);break;	
				case 18: tile16 = new TileFour(this, R.id.imageView19,valori[i]);break;	
				case 19: tile16 = new TileFour(this, R.id.imageView20,valori[i]);break;	
			}
		}
		//Toast.makeText(context,"Old Highscore: "+getHighscore(),Toast.LENGTH_SHORT).show();
		Toast.makeText(context,"Level 5 ",Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//android stores internally data from applications and can be set and get anytime without writing to a file
	public static long getHighscore() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore4x5", 0);
	    return test;
	}

    static void setHighscore(long data) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context); 
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong("highscore4x5", data);
        editor.commit(); // Very important
    }
	
	public static long getGlobalHighscore() {
		SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
		long test = app_preferences.getLong("highscore", 0);
	    return test;
	}
	
    static void setGlobalHighscore(long data) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context); 
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong("highscore", data);
        editor.commit(); // Very important
    }	
    
    public static void highscoreAnimation(){    	    	
       	//highscoreImg.setImageResource(R.drawable.high1);
    	/* highscoreImg.setBackgroundResource(R.drawable.highscore_animation);
		AnimationDrawable frameAnimation2 = (AnimationDrawable) highscoreImg.getBackground();
		frameAnimation2.stop();
		frameAnimation2.start(); */
    	Toast.makeText(context,"New Highscore!",Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
        	MainMenu.resetAll();
			Intent i = new Intent(FourByFive.getContext(), MainMenu.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			FourByFive.getContext().startActivity(i);
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
