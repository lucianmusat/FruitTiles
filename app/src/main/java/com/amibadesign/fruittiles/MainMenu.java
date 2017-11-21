package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
 
public class MainMenu extends Activity {
 
	Button newGameButton,aboutButton,highscoreButton;
	private boolean doubleBackToExitPressedOnce=false;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main_menu); 
		addListenerOnButton();
		resetAll();
	}
 
	public void addListenerOnButton() {
 
		newGameButton = (Button) findViewById(R.id.newGame);
		aboutButton = (Button) findViewById(R.id.exit);
		highscoreButton = (Button) findViewById(R.id.highScores);
		
 
		newGameButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				newGameButton.setBackgroundResource(R.drawable.newgame_pressed);
				newGameButton.setHeight(65);
				newGameButton.setWidth(390);
				final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
		        handler.postDelayed(new Runnable() {
		            @Override
		            public void run() {
						Intent intent = new Intent(MainMenu.this, Game.class);
						intent.putExtra("NR_TILES", "9");
						startActivity(intent);
		            }
		        }, 300);	
 
			}
 
		});
		
		aboutButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				
				aboutButton.setBackgroundResource(R.drawable.about_pressed);
				aboutButton.setHeight(59);
				aboutButton.setWidth(327);
				final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
		        handler.postDelayed(new Runnable() {
		            @Override
		            public void run() {
						Intent k = new Intent(MainMenu.this, About.class);
						startActivity(k);
		            }
		        }, 300);
			}
 
		});
		
		highscoreButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				
				highscoreButton.setBackgroundResource(R.drawable.highscores_pressed);
				highscoreButton.setHeight(75);
				highscoreButton.setWidth(390);
				final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
		        handler.postDelayed(new Runnable() {
		            @Override
		            public void run() {
						Intent k = new Intent(MainMenu.this, HighScore.class);
						startActivity(k);
		            }
		        }, 300);
			}
 
		});
	}
	
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
        	moveTaskToBack(true);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
             doubleBackToExitPressedOnce=false;   

            }
        }, 2000);
    } 
    
    public static void resetAll(){
    	
		TwoByTwo.flipped=0;
		TwoByTwo.firstTile=null;
		TwoByTwo.secondTile=null;
		TwoByTwo.active_tiles=4;
		TwoByTwo.startTime=0L;
		TwoByTwo.stopTime=0L;
		TwoByTwo.elapsed=0L;
		
		TwoByThree.flipped=0;
		TwoByThree.firstTile=null;
		TwoByThree.secondTile=null;
		TwoByThree.active_tiles=6;
		TwoByThree.startTime=0L;
		TwoByThree.stopTime=0L;
		TwoByThree.elapsed=0L;
		
		FourByFour.flipped=0;
		FourByFour.firstTile=null;
		FourByFour.secondTile=null;
		FourByFour.active_tiles=16;
		FourByFour.startTime=0L;
		FourByFour.stopTime=0L;
		FourByFour.elapsed=0L;
		
		FourByFive.flipped=0;
		FourByFive.firstTile=null;
		FourByFive.secondTile=null;
		FourByFive.active_tiles=20;
		FourByFive.startTime=0L;
		FourByFive.stopTime=0L;
		FourByFive.elapsed=0L;
    	
    }
 
}
