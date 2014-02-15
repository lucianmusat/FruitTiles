package com.amibadesign.fruittiles;

import java.util.Random;

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

public class ThreeByFour extends Activity {

	public static int flipped=0;
	public static TileFive firstTile=null;
	public static TileFive secondTile=null;
	private static Context context;
	public static int active_tiles=12;
	public static long startTime;
	public static long stopTime;
	public static long elapsed;	
	public static TileFive tile1;
	public static TileFive tile2;
	public static TileFive tile3;
	public static TileFive tile4;
	public static TileFive tile5;
	public static TileFive tile6;
	public static TileFive tile7;
	public static TileFive tile8;
	public static TileFive tile9;
	public static TileFive tile10;
	public static TileFive tile11;
	public static TileFive tile12;
	public static ImageView highscoreImg;
	private boolean doubleBackToExitPressedOnce=false;

	
	public static Context getContext(){
		return context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ThreeByFour.context = getApplicationContext();
		setContentView(R.layout.threebyfour);		
		int[] valori = {0,0,1,1,2,2,3,3,4,4,5,5};				
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
		
		//create the 12 tiles
		for (int i=0; i < 12; i++)
		{

			switch(i){

				case 0: tile1 = new TileFive(this, R.id.imageView1,valori[i]);break;
				case 1: tile2 = new TileFive(this, R.id.imageView2,valori[i]);break;
				case 2:	tile3 = new TileFive(this, R.id.imageView3,valori[i]);break;
				case 3:	tile4 = new TileFive(this, R.id.imageView4,valori[i]);break;
				case 4:	tile5 = new TileFive(this, R.id.imageView5,valori[i]);break;
				case 5:	tile6 = new TileFive(this, R.id.imageView6,valori[i]);break;
				case 6:	tile7 = new TileFive(this, R.id.imageView7,valori[i]);break;
				case 7:	tile8 = new TileFive(this, R.id.imageView8,valori[i]);break;
				case 8:	tile9 = new TileFive(this, R.id.imageView9,valori[i]);break;
				case 9:	tile10 = new TileFive(this, R.id.imageView10,valori[i]);break;
				case 10:tile11 = new TileFive(this, R.id.imageView11,valori[i]);break;
				case 11:tile12 = new TileFive(this, R.id.imageView12,valori[i]);break;
			}
		}
		//Toast.makeText(context,"Old Highscore: "+getHighscore(),Toast.LENGTH_SHORT).show();
		Toast.makeText(context,"Level 3 ",Toast.LENGTH_SHORT).show();
		//Log.i("FruitTiles","Active tiles:"+ThreeByFour.active_tiles);

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
		long test = app_preferences.getLong("highscore3x4", 0);
	    return test;
	}

    static void setHighscore(long data) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context); 
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong("highscore3x4", data);
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
			Intent i = new Intent(ThreeByFour.getContext(), MainMenu.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ThreeByFour.getContext().startActivity(i);
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
