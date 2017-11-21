package com.amibadesign.fruittiles;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class Game extends Activity {

    private Context context;
    public static int active_tiles=4;
    private static int totalFlipped = 0;
    public static long startTime;
    public static long stopTime;
    public static long elapsed;
    public static List<Integer> tiles = new ArrayList<Integer>();
    private Integer nrTiles;
    public ImageView highscoreImg;
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

        startTime=System.nanoTime();
        highscoreImg = (ImageView) findViewById(R.id.HighScore);


        //Toast.makeText(context,"Old Highscore: "+getHighscore(),Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Level 1 ",Toast.LENGTH_SHORT).show();
//        Log.i("FruitTiles","Active tiles: "+tiles.size());

        GridView gameGridView = (GridView)findViewById(R.id.gameGrid);
        gameGridView.setNumColumns((int) Math.sqrt(nrTiles));
        final ImageAdapter imgAdapter = new ImageAdapter(this, nrTiles);
        gameGridView.setAdapter(imgAdapter);

        gameGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

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
                        imgAdapter.tiles.get(previousTilePosition).closeTile(); // TODO: add a delay here too
                    }
                }

                totalFlipped = 0;
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
        return app_preferences.getLong("highscore2x2", 0);
    }

    void setHighscore(long data) {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putLong("highscore2x2", data);
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
            MainMenu.resetAll();
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
