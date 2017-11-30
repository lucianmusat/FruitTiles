package at.lucianmus.fruittiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class Game extends Activity {

    private Context context;
    private static int totalFlipped = 0;
    public static long startTime;
    public static long stopTime;
    public static long elapsed;
    public static List<Integer> tiles = new ArrayList<Integer>();
    private Integer nrTiles;

    List<Integer> tileNumber = Arrays.asList(4, 6, 12, 16, 20);
//    List<Integer> tileNumber = Arrays.asList(4, 20);

    private boolean doubleBackToExitPressedOnce=false;

    public Game(GridView gridView, Integer nrTiles) {
        this.nrTiles = nrTiles;
    }

    public Context getContext(){
        return context;
    }

    public Integer getNrTiles() {
        return nrTiles;
    }

    public Game() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = getApplicationContext();
        setContentView(R.layout.game);

        // Get number of tiles
        nrTiles = Integer.parseInt(getIntent().getStringExtra("NR_TILES"));

        // To measure the time it takes to complete the level
        startTime = System.nanoTime();

        Toast.makeText(context,nrTiles + " tiles",Toast.LENGTH_SHORT).show();

        // Create a grid with all the tiles
        final GridView gameGridView = (GridView)findViewById(R.id.gameGrid);
        gameGridView.setNumColumns((int) Math.sqrt(nrTiles));
        final ImageAdapter imgAdapter = new ImageAdapter(this, nrTiles);
        gameGridView.setAdapter(imgAdapter);

        gameGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                gameGridView.setEnabled(false);
                imgAdapter.tiles.get(position).flipTile(false);

                for (int i=0; i<imgAdapter.tiles.size(); i++){
                    if (imgAdapter.tiles.get(i).isFlipped() && !imgAdapter.tiles.get(i).isDisabled())
                        totalFlipped++;
                }

                // If a third tile is totalFlipped, flip back the first two
                if (totalFlipped > 2) {
                    for (int i = 0; i < imgAdapter.tiles.size(); i++) {
                        if (i != position && imgAdapter.tiles.get(i).isFlipped() && !imgAdapter.tiles.get(i).isDisabled())
                            imgAdapter.tiles.get(i).flipTile(false);
                    }
                }

                // If the tiles have the same image, close them both
                if (totalFlipped == 2){
                    int previousTilePosition = -1;
                    for (int i=0; i<imgAdapter.tiles.size(); i++){
                        if (imgAdapter.tiles.get(i).isFlipped() && !imgAdapter.tiles.get(i).isDisabled() && i != position)
                            previousTilePosition = i;
                    }
                    if (imgAdapter.tiles.get(position).getValue() == imgAdapter.tiles.get(previousTilePosition).getValue()){
                        imgAdapter.tiles.get(position).flipTile(true);
                        imgAdapter.tiles.get(previousTilePosition).closeTile(500);

                        // Check if all tiles have been closed
                        boolean tilesStillExist = false;
                        for (int i=0; i<imgAdapter.tiles.size(); i++)
                            if (!imgAdapter.tiles.get(i).isDisabled())
                                tilesStillExist = true;

                        // Calculate highscore
                        if (!tilesStillExist){
                            Toast.makeText(context,"Level Cleared!",Toast.LENGTH_SHORT).show();
                            stopTime=System.nanoTime();
                            elapsed=((stopTime-startTime)/1000000000);
                            if ((elapsed < getHighscore()) || (getHighscore()==0)) {
                                setHighscore(elapsed);
                                Toast.makeText(context, "Congratulations! New highscore: " + elapsed + "s", Toast.LENGTH_SHORT).show();
                            }
                            // Switch to next level
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Game.this, Game.class);
                                    int nextLevel = tileNumber.indexOf(nrTiles) + 1;
                                    if (nextLevel > tileNumber.size()-1) {
                                        // Go to main menu
                                        intent = new Intent(Game.this, MainMenu.class);
                                    } else
                                        nrTiles = tileNumber.get(nextLevel);
                                    intent.putExtra("NR_TILES", nrTiles.toString());
                                    startActivity(intent);
                                }
                            }, 3000);
                        }
                    }
                }

                totalFlipped = 0;

                // Enable clicking after a short while to avoid click spamming
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameGridView.setEnabled(true);                    }
                }, 50);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public long getHighscore() {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return app_preferences.getLong("highscore"+nrTiles, 0);
    }

    void setHighscore(long data) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong("highscore"+nrTiles, data);
        editor.apply(); // Very important
    }

    public void highscoreAnimation(){
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
            Intent i = new Intent(getContext(), MainMenu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(i);
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
