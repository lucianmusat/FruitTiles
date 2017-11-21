package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;


public class Tile {
    private boolean isflipped = false;
    private int value = 0;
    private boolean disabled = false;
    SquareImageView img;
    private Context context;

    public boolean isFlipped() {
        return this.isflipped;
    }

    private void flip() {
        this.isflipped = !this.isflipped;
    }

    public void setValue(int value) {
        this.value = value;
    }

    boolean isDisabled() {
        return this.disabled;
    }

    private void disable() {
        this.disabled = true;
    }

    public void enable() {
        this.disabled = false;
    }

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

    void flipTile(boolean alsoClose){
        if (!this.isDisabled()) {
            this.img.setEnabled(false);
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
            AnimationDrawable frameAnimation;
            if (!isFlipped() && !alsoClose) {
                // Flip back tile
                img.setBackgroundResource(R.drawable.animation_reverse);
                frameAnimation = (AnimationDrawable) this.img.getBackground();
                frameAnimation.start();
            } else {
                frameAnimation = (AnimationDrawable) this.img.getBackground();
                frameAnimation.start();
            }
        }

        if (alsoClose) {
            final Handler handler = new Handler();  //handler for delayed animation.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    img.setBackgroundResource(R.drawable.close_animation);
                    final AnimationDrawable frameAnimation2 = (AnimationDrawable) img.getBackground();
                    frameAnimation2.start();
                }
            }, 500);
            this.disable();
        }
    }

    public void closeTile(){
        img.setBackgroundResource(R.drawable.close_animation);
        final AnimationDrawable frameAnimation2 = (AnimationDrawable) img.getBackground();
        frameAnimation2.start();
        this.disable();

    }

}
