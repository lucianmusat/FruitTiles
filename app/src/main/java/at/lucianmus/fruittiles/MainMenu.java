package at.lucianmus.fruittiles;

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
 
	Button newGameButton, aboutButton, highScoreButton;
	private boolean doubleBackToExitPressedOnce=false;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main_menu);
		addListenerOnButton();
	}
 
	public void addListenerOnButton() {
 
		newGameButton = (Button) findViewById(R.id.newGame);
		aboutButton = (Button) findViewById(R.id.exit);
		highScoreButton = (Button) findViewById(R.id.highScores);
		
 
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
                        intent.putExtra("NR_TILES", "4");
                        startActivity(intent);
                        newGameButton.setBackgroundResource(R.drawable.newgame);
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
                        aboutButton.setBackgroundResource(R.drawable.about);
		            }
		        }, 300);
			}
		});
		
		highScoreButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				highScoreButton.setBackgroundResource(R.drawable.highscores_pressed);
				highScoreButton.setHeight(75);
				highScoreButton.setWidth(390);
				final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
		        handler.postDelayed(new Runnable() {
		            @Override
		            public void run() {
						Intent k = new Intent(MainMenu.this, HighScore.class);
						startActivity(k);
                        highScoreButton.setBackgroundResource(R.drawable.highscores);
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

}
