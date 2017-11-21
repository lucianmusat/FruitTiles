package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class Tile {
    private boolean isflipped = false;
    private boolean isAnimationRunning = false;
    private int value = 0;
    private boolean disabled = false;
    SquareImageView img;
    private Context context;
    private AnimationDrawable frameAnimation;
    public boolean isFlipped() {
        return this.isflipped;
    }

    private void flip() {
        this.isflipped = !this.isflipped;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void disable() {

        this.disabled = true;
        Log.i("Tile", "Tile disabled!");
    }

    public void enable() {
        this.disabled = false;
        Log.i("Tile", "Tile enabled!");
    }

    private void checkIfAnimationDone(AnimationDrawable anim){
        final AnimationDrawable a = anim;
        int timeBetweenChecks = 300;
        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run(){
                if (a.getCurrent() != a.getFrame(a.getNumberOfFrames() - 1)){
                    isAnimationRunning = true;
                    checkIfAnimationDone(a);
                } else{
                    Toast.makeText(context, "ANIMATION DONE!", Toast.LENGTH_SHORT).show();
                    isAnimationRunning = false;
                }
            }
        }, timeBetweenChecks);
    };

    Tile(final Activity parent, int id, int valoare){
        img = new SquareImageView(parent);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        img.setBackgroundResource(R.drawable.animation1_big);
        value = valoare;
        context = parent;
//        img.setOnClickListener(this);
    }

    int getValue(){
        return value;
    }

    void flipTile(){
        if (!this.isDisabled()) {
            this.img.setEnabled(false);
            isAnimationRunning = true;
            switch (value) {
                case 0:
                    this.img.setBackgroundResource(R.drawable.animation1);
                    break;
                case 1:
                    this.img.setBackgroundResource(R.drawable.animation2);
                    break;
                case 2:
                    this.img.setBackgroundResource(R.drawable.animation3);
                    break;
                case 3:
                    this.img.setBackgroundResource(R.drawable.animation4);
                    break;
                case 4:
                    this.img.setBackgroundResource(R.drawable.animation5);
                    break;
                case 5:
                    this.img.setBackgroundResource(R.drawable.animation6);
                    break;
                case 6:
                    this.img.setBackgroundResource(R.drawable.animation7);
                    break;
                case 7:
                    this.img.setBackgroundResource(R.drawable.animation8);
                    break;

            }
            this.flip();
            if (!isFlipped()) {
                img.setBackgroundResource(R.drawable.animation_reverse);
                frameAnimation = (AnimationDrawable) this.img.getBackground();
                frameAnimation.stop();
                frameAnimation.start();
            } else {
                frameAnimation = (AnimationDrawable) this.img.getBackground();
                frameAnimation.stop();
                frameAnimation.start();
            }
            final Handler handler = new Handler();  //handler for delayed animation.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Tile.this.img.setEnabled(true);				            	}
            }, 400);
        }
        checkIfAnimationDone(frameAnimation);
        }

    public void closeTile(){
        this.img.setBackgroundResource(R.drawable.close_animation);
        AnimationDrawable frameAnimation = (AnimationDrawable) this.img.getBackground();
        frameAnimation.stop();
        frameAnimation.start();
        this.disable();
        Log.i("FruitTiles", "Tile closed!");
    }

//    public void onClick(View arg0) {
//        Toast.makeText(context,"Pressed: " + value,Toast.LENGTH_SHORT).show();
////        if (isDisabled())
////            Log.i("FruitTiles","Is disabled");
////        else
////            Log.i("FruitTiles","Is enabled");
//
//        if(!isDisabled())
//            flipTile();
//    }

}
