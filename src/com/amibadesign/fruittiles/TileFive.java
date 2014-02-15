package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

//for Three By Four Activity

public class TileFive implements OnClickListener {
	public int isflipped=0;
	public int value=0;
	public int disabled=0;
	public ImageView img;
	public AnimationDrawable frameAnimation;
	public long sessionHighscore;
	
	public TileFive(final Activity parent, int id,int valoare){			
		img = (ImageView)parent.findViewById(id);		
		img.setBackgroundResource(R.drawable.animation1);
		value = valoare;   
		img.setOnClickListener(this);
		}
	
	public int getValue(){
		return value;

	}	 
	
	public void flipTile(){	
		disableTouch();
			switch(value){
			case 0:	TileFive.this.img.setBackgroundResource(R.drawable.animation1); break;
			case 1:	TileFive.this.img.setBackgroundResource(R.drawable.animation2); break;
			case 2:	TileFive.this.img.setBackgroundResource(R.drawable.animation3); break;
			case 3:	TileFive.this.img.setBackgroundResource(R.drawable.animation4); break;
			case 4:	TileFive.this.img.setBackgroundResource(R.drawable.animation5); break;
			case 5:	TileFive.this.img.setBackgroundResource(R.drawable.animation6); break;
			case 6:	TileFive.this.img.setBackgroundResource(R.drawable.animation7); break;
			case 7:	TileFive.this.img.setBackgroundResource(R.drawable.animation8); break;
		} 
		frameAnimation = (AnimationDrawable) TileFive.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();
		isflipped++;	
		ThreeByFour.flipped++;	
		final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	enableTouch();				            	}
        }, 400);		
	}
	
	public void flipInverse(){
		disableTouch();
			if (disabled==0){
				TileFive.this.img.setBackgroundResource(R.drawable.animation_reverse);
				AnimationDrawable frameAnimation = (AnimationDrawable) TileFive.this.img.getBackground();
				frameAnimation.stop();
				frameAnimation.start();
				if(ThreeByFour.firstTile==TileFive.this)
				{
					if (ThreeByFour.secondTile!= null)
					{
						ThreeByFour.firstTile=ThreeByFour.secondTile;
						ThreeByFour.secondTile=null;
					}
					else
					if (ThreeByFour.secondTile == null)
						ThreeByFour.firstTile=null;	
				}	
				else
				if(ThreeByFour.secondTile==TileFive.this)
				{
					ThreeByFour.secondTile=null;
				}
				isflipped--;
				ThreeByFour.flipped--;				
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	enableTouch();				            	}
	        }, 400);		
	}
	
	public void closeTile(){											
		TileFive.this.img.setBackgroundResource(R.drawable.close_animation);
		AnimationDrawable frameAnimation = (AnimationDrawable) TileFive.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();	
		ThreeByFour.active_tiles--;
		TileFive.this.disableTile();
		
		//if is the last pair of tiles
		if (ThreeByFour.active_tiles == 0)
		{
			ThreeByFour.stopTime=System.nanoTime();
			ThreeByFour.elapsed=((ThreeByFour.stopTime-ThreeByFour.startTime)/1000000000);
						
			//Toast.makeText(ThreeByFour.getContext(),"Congrats! Finished in: "+ThreeByFour.elapsed+" seconds",Toast.LENGTH_SHORT).show();	
			
			sessionHighscore = TwoByTwo.elapsed + TwoByThree.elapsed + FourByFour.elapsed + ThreeByFour.elapsed;
			
			if ((sessionHighscore < ThreeByFour.getHighscore())||(ThreeByFour.getHighscore()==0)){
				//Toast.makeText(ThreeByFour.getContext(),"New Highscore!",Toast.LENGTH_SHORT).show();
				ThreeByFour.highscoreAnimation();
				ThreeByFour.setHighscore(ThreeByFour.elapsed);	
			}
    		
			final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	        		FourByFour.flipped=0;
	        		FourByFour.firstTile=null;
	        		FourByFour.secondTile=null;
	        		FourByFour.active_tiles=16;
	        		FourByFour.startTime=0L;
	        		FourByFour.stopTime=0L;
	        		FourByFour.elapsed=0L;
	        		
	    			Intent i = new Intent(ThreeByFour.getContext(), FourByFour.class);
	    			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			ThreeByFour.getContext().startActivity(i);			            	}
	        }, 1000);			            	
	
		}		
	}
	
	public void disableTile(){
		disabled=1;		
	}
	
	public void onClick(View arg0) {				
		if ((isflipped==0)&&(disabled==0)){				
			flipTile();						
			if (ThreeByFour.flipped==1)
				ThreeByFour.firstTile=TileFive.this;
			else
			if (ThreeByFour.flipped==2){
					ThreeByFour.secondTile=TileFive.this;
					if(ThreeByFour.firstTile.value == ThreeByFour.secondTile.value)
					{																		
						final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
				        handler.postDelayed(new Runnable() {
				            @Override
				            public void run() {
								ThreeByFour.firstTile.closeTile();
								TileFive.this.disableTile();
								ThreeByFour.secondTile.closeTile();																										
								ThreeByFour.flipped=0;							            	}
				        }, 400);

					}
				}
			else
			if (ThreeByFour.flipped==3)
				{
					ThreeByFour.firstTile.flipInverse();
					ThreeByFour.firstTile.flipInverse(); //after the first one is closed the second becomes the first
					ThreeByFour.firstTile=TileFive.this;
				}						
		}
		else
		{						
				flipInverse();
		}					
	}

	public void disableTouch(){
		ThreeByFour.tile1.img.setEnabled(false);
		ThreeByFour.tile2.img.setEnabled(false);
		ThreeByFour.tile3.img.setEnabled(false);
		ThreeByFour.tile4.img.setEnabled(false);
		ThreeByFour.tile5.img.setEnabled(false);
		ThreeByFour.tile6.img.setEnabled(false);
		ThreeByFour.tile7.img.setEnabled(false);
		ThreeByFour.tile8.img.setEnabled(false);
		ThreeByFour.tile9.img.setEnabled(false);
		ThreeByFour.tile10.img.setEnabled(false);
		ThreeByFour.tile11.img.setEnabled(false);
		ThreeByFour.tile12.img.setEnabled(false);		
	}
	public void enableTouch(){
		ThreeByFour.tile1.img.setEnabled(true);
		ThreeByFour.tile2.img.setEnabled(true);
		ThreeByFour.tile3.img.setEnabled(true);
		ThreeByFour.tile4.img.setEnabled(true);
		ThreeByFour.tile5.img.setEnabled(true);
		ThreeByFour.tile6.img.setEnabled(true);
		ThreeByFour.tile7.img.setEnabled(true);
		ThreeByFour.tile8.img.setEnabled(true);
		ThreeByFour.tile9.img.setEnabled(true);
		ThreeByFour.tile10.img.setEnabled(true);
		ThreeByFour.tile11.img.setEnabled(true);
		ThreeByFour.tile12.img.setEnabled(true);		
	}
}
