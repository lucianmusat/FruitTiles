package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;



public class TileTwo implements OnClickListener {
	public int isflipped=0;
	public int value=0;
	public int disabled=0;
	public ImageView img;
	public AnimationDrawable frameAnimation;
	
	public TileTwo(final Activity parent, int id,int valoare){			
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
			case 0:	TileTwo.this.img.setBackgroundResource(R.drawable.animation1_big); break;
			case 1:	TileTwo.this.img.setBackgroundResource(R.drawable.animation2_big); break;
			case 2:	TileTwo.this.img.setBackgroundResource(R.drawable.animation3_big); break;
//			case 3:	TileOne.this.img.setBackgroundResource(R.drawable.animation4); break;
//			case 4:	TileOne.this.img.setBackgroundResource(R.drawable.animation5); break;
//			case 5:	TileOne.this.img.setBackgroundResource(R.drawable.animation6); break;
//			case 6:	TileOne.this.img.setBackgroundResource(R.drawable.animation7); break;
//			case 7:	TileOne.this.img.setBackgroundResource(R.drawable.animation8); break;
		} 
		frameAnimation = (AnimationDrawable) TileTwo.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();
		isflipped++;	
		TwoByThree.flipped++;	
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
				TileTwo.this.img.setBackgroundResource(R.drawable.animation_reverse);
				AnimationDrawable frameAnimation = (AnimationDrawable) TileTwo.this.img.getBackground();
				frameAnimation.stop();
				frameAnimation.start();
				if(TwoByThree.firstTile==TileTwo.this)
				{
					if (TwoByThree.secondTile!= null)
					{
						TwoByThree.firstTile=TwoByThree.secondTile;
						TwoByThree.secondTile=null;
					}
					else
					if (TwoByThree.secondTile == null)
						TwoByThree.firstTile=null;	
				}	
				else
				if(TwoByThree.secondTile==TileTwo.this)
				{
					TwoByThree.secondTile=null;
				}
				isflipped--;
				TwoByThree.flipped--;				
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	enableTouch();				            	}
	        }, 400);		
	}
	
	public void closeTile(){											
		TileTwo.this.img.setBackgroundResource(R.drawable.close_animation);
		AnimationDrawable frameAnimation = (AnimationDrawable) TileTwo.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();	
		TwoByThree.active_tiles--;
		TileTwo.this.disableTile();		
		
		//if is the last pair of tiles
		if (TwoByThree.active_tiles == 0)
		{
			TwoByThree.stopTime=System.nanoTime();
			TwoByThree.elapsed=((TwoByThree.stopTime-TwoByThree.startTime)/1000000000);
						
			//Toast.makeText(TwoByThree.getContext(),"Congrats! Finished in: "+TwoByThree.elapsed+" seconds",Toast.LENGTH_SHORT).show();					
			
			if ((TwoByThree.elapsed < TwoByThree.getHighscore())||(TwoByThree.getHighscore()==0)){
				//Toast.makeText(TwoByThree.getContext(),"New Highscore!",Toast.LENGTH_SHORT).show();
				//make highscore animation
				TwoByThree.highscoreAnimation();	
				TwoByThree.setHighscore(TwoByThree.elapsed);	
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	ThreeByFour.flipped=0;
	            	ThreeByFour.firstTile=null;
	            	ThreeByFour.secondTile=null;
	            	ThreeByFour.active_tiles=12;
	            	ThreeByFour.startTime=0L;
	            	ThreeByFour.stopTime=0L;
	            	ThreeByFour.elapsed=0L;
	        		
	    			Intent i = new Intent(TwoByThree.getContext(), ThreeByFour.class);
	    			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			TwoByThree.getContext().startActivity(i);			            	}
	        }, 1000);	
		}		
	}
	
	public void disableTile(){
		disabled=1;		
	}
	
	public void onClick(View arg0) {				
		if ((isflipped==0)&&(disabled==0)){				
			flipTile();						
			if (TwoByThree.flipped==1)
				TwoByThree.firstTile=TileTwo.this;
			else
			if (TwoByThree.flipped==2){
					TwoByThree.secondTile=TileTwo.this;
					if(TwoByThree.firstTile.value == TwoByThree.secondTile.value)
					{																		
						final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
				        handler.postDelayed(new Runnable() {
				            @Override
				            public void run() {
								TwoByThree.firstTile.closeTile();
								TileTwo.this.disableTile();
								TwoByThree.secondTile.closeTile();																										
								TwoByThree.flipped=0;							            	}
				        }, 400);

					}
				}
			else
			if (TwoByThree.flipped==3)
				{
					TwoByThree.firstTile.flipInverse();
					TwoByThree.firstTile.flipInverse(); //after the first one is closed the second becomes the first
					TwoByThree.firstTile=TileTwo.this;
				}						
		}
		else
		{						
				flipInverse();
		}					
	}

	public void disableTouch(){
		TwoByThree.tile1.img.setEnabled(false);
		TwoByThree.tile2.img.setEnabled(false);
		TwoByThree.tile3.img.setEnabled(false);
		TwoByThree.tile4.img.setEnabled(false);		
	}
	public void enableTouch(){
		TwoByThree.tile1.img.setEnabled(true);
		TwoByThree.tile2.img.setEnabled(true);
		TwoByThree.tile3.img.setEnabled(true);
		TwoByThree.tile4.img.setEnabled(true);		
	}
}
