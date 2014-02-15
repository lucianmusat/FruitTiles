package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;



public class TileOne implements OnClickListener {
	public int isflipped=0;
	public int value=0;
	public int disabled=0;
	public ImageView img;
	public AnimationDrawable frameAnimation;
	
	public TileOne(final Activity parent, int id,int valoare){			
		img = (ImageView)parent.findViewById(id);		
		img.setBackgroundResource(R.drawable.animation1_big);
		value = valoare;   
		img.setOnClickListener(this);
		}
	
	public int getValue(){
		return value;

	}	 
	
	public void flipTile(){	
		disableTouch();
			switch(value){
			case 0:	TileOne.this.img.setBackgroundResource(R.drawable.animation1_big); break;
			case 1:	TileOne.this.img.setBackgroundResource(R.drawable.animation2_big); break;
//			case 2:	TileOne.this.img.setBackgroundResource(R.drawable.animation3); break;
//			case 3:	TileOne.this.img.setBackgroundResource(R.drawable.animation4); break;
//			case 4:	TileOne.this.img.setBackgroundResource(R.drawable.animation5); break;
//			case 5:	TileOne.this.img.setBackgroundResource(R.drawable.animation6); break;
//			case 6:	TileOne.this.img.setBackgroundResource(R.drawable.animation7); break;
//			case 7:	TileOne.this.img.setBackgroundResource(R.drawable.animation8); break;
		} 
		frameAnimation = (AnimationDrawable) TileOne.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();
		isflipped++;	
		TwoByTwo.flipped++;	
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
				TileOne.this.img.setBackgroundResource(R.drawable.animation_reverse);
				AnimationDrawable frameAnimation = (AnimationDrawable) TileOne.this.img.getBackground();
				frameAnimation.stop();
				frameAnimation.start();
				if(TwoByTwo.firstTile==TileOne.this)
				{
					if (TwoByTwo.secondTile!= null)
					{
						TwoByTwo.firstTile=TwoByTwo.secondTile;
						TwoByTwo.secondTile=null;
					}
					else
					if (TwoByTwo.secondTile == null)
						TwoByTwo.firstTile=null;	
				}	
				else
				if(TwoByTwo.secondTile==TileOne.this)
				{
					TwoByTwo.secondTile=null;
				}
				isflipped--;
				TwoByTwo.flipped--;				
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	enableTouch();				            	}
	        }, 400);		
	}
	
	public void closeTile(){											
		TileOne.this.img.setBackgroundResource(R.drawable.close_animation);
		AnimationDrawable frameAnimation = (AnimationDrawable) TileOne.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();	
		TwoByTwo.active_tiles--;
		TileOne.this.disableTile();
		Log.i("FruitTiles","Active tiles:"+TwoByTwo.active_tiles);
		
		//if is the last pair of tiles
		if (TwoByTwo.active_tiles == 0)
		{
			TwoByTwo.stopTime=System.nanoTime();
			TwoByTwo.elapsed=((TwoByTwo.stopTime-TwoByTwo.startTime)/1000000000);
						
			//Toast.makeText(TwoByTwo.getContext(),"Congrats! Finished in: "+TwoByTwo.elapsed+" seconds",Toast.LENGTH_SHORT).show();				
			
			if ((TwoByTwo.elapsed < TwoByTwo.getHighscore())||(TwoByTwo.getHighscore()==0)){
				//Toast.makeText(TwoByTwo.getContext(),"New Highscore!",Toast.LENGTH_SHORT).show();
				//make highscore animation
				TwoByTwo.highscoreAnimation();				
				TwoByTwo.setHighscore(TwoByTwo.elapsed);	
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	TwoByThree.flipped=0;
	            	TwoByThree.firstTile=null;
	            	TwoByThree.secondTile=null;
	            	TwoByThree.active_tiles=6;
	            	TwoByThree.startTime=0L;
	            	TwoByThree.stopTime=0L;
	            	TwoByThree.elapsed=0L;
	            	
	    			Intent i = new Intent(TwoByTwo.getContext(), TwoByThree.class);
	    			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			TwoByTwo.getContext().startActivity(i);			            	}
	        }, 1000);	

		}		
	}
	
	public void disableTile(){
		disabled=1;		
	}
	
	public void onClick(View arg0) {				
		if ((isflipped==0)&&(disabled==0)){				
			flipTile();						
			if (TwoByTwo.flipped==1)
				TwoByTwo.firstTile=TileOne.this;
			else
			if (TwoByTwo.flipped==2){
					TwoByTwo.secondTile=TileOne.this;
					if(TwoByTwo.firstTile.value == TwoByTwo.secondTile.value)
					{																		
						final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
				        handler.postDelayed(new Runnable() {
				            @Override
				            public void run() {
								TwoByTwo.firstTile.closeTile();
								TileOne.this.disableTile();
								TwoByTwo.secondTile.closeTile();																										
								TwoByTwo.flipped=0;							            	}
				        }, 400);

					}
				}
			else
			if (TwoByTwo.flipped==3)
				{
					TwoByTwo.firstTile.flipInverse();
					TwoByTwo.firstTile.flipInverse(); //after the first one is closed the second becomes the first
					TwoByTwo.firstTile=TileOne.this;
				}						
		}
		else
		{						
				flipInverse();
		}					
	}

	public void disableTouch(){
		TwoByTwo.tile1.img.setEnabled(false);
		TwoByTwo.tile2.img.setEnabled(false);
		TwoByTwo.tile3.img.setEnabled(false);
		TwoByTwo.tile4.img.setEnabled(false);		
	}
	public void enableTouch(){
		TwoByTwo.tile1.img.setEnabled(true);
		TwoByTwo.tile2.img.setEnabled(true);
		TwoByTwo.tile3.img.setEnabled(true);
		TwoByTwo.tile4.img.setEnabled(true);		
	}
}
