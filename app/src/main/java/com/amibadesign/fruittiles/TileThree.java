package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;



public class TileThree implements OnClickListener {
	public int isflipped=0;
	public int value=0;
	public int disabled=0;
	public ImageView img;
	public AnimationDrawable frameAnimation;
	
	public TileThree(final Activity parent, int id,int valoare){			
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
			case 0:	TileThree.this.img.setBackgroundResource(R.drawable.animation1); break;
			case 1:	TileThree.this.img.setBackgroundResource(R.drawable.animation2); break;
			case 2:	TileThree.this.img.setBackgroundResource(R.drawable.animation3); break;
			case 3:	TileThree.this.img.setBackgroundResource(R.drawable.animation4); break;
			case 4:	TileThree.this.img.setBackgroundResource(R.drawable.animation5); break;
			case 5:	TileThree.this.img.setBackgroundResource(R.drawable.animation6); break;
			case 6:	TileThree.this.img.setBackgroundResource(R.drawable.animation7); break;
			case 7:	TileThree.this.img.setBackgroundResource(R.drawable.animation8); break;
		} 
		frameAnimation = (AnimationDrawable) TileThree.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();
		isflipped++;	
		FourByFour.flipped++;	
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
				TileThree.this.img.setBackgroundResource(R.drawable.animation_reverse);
				AnimationDrawable frameAnimation = (AnimationDrawable) TileThree.this.img.getBackground();
				frameAnimation.stop();
				frameAnimation.start();
				if(FourByFour.firstTile==TileThree.this)
				{
					if (FourByFour.secondTile!= null)
					{
						FourByFour.firstTile=FourByFour.secondTile;
						FourByFour.secondTile=null;
					}
					else
					if (FourByFour.secondTile == null)
						FourByFour.firstTile=null;	
				}	
				else
				if(FourByFour.secondTile==TileThree.this)
				{
					FourByFour.secondTile=null;
				}
				isflipped--;
				FourByFour.flipped--;				
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	enableTouch();				            	}
	        }, 400);		
	}
	
	public void closeTile(){											
		TileThree.this.img.setBackgroundResource(R.drawable.close_animation);
		AnimationDrawable frameAnimation = (AnimationDrawable) TileThree.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();	
		FourByFour.active_tiles--;
		TileThree.this.disableTile();
		
		//if is the last pair of tiles
		if (FourByFour.active_tiles == 0)
		{
			FourByFour.stopTime=System.nanoTime();
			FourByFour.elapsed=((FourByFour.stopTime-FourByFour.startTime)/1000000000);
						
			//Toast.makeText(FourByFour.getContext(),"Congrats! Finished in: "+FourByFour.elapsed+" seconds",Toast.LENGTH_SHORT).show();				
			
			if ((FourByFour.elapsed < FourByFour.getHighscore())||(FourByFour.getHighscore()==0)){
				//Toast.makeText(FourByFour.getContext(),"New Highscore!",Toast.LENGTH_SHORT).show();
				FourByFour.highscoreAnimation();
				FourByFour.setHighscore(FourByFour.elapsed);	
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits for eventual animations and then go to next game.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	        		FourByFive.flipped=0;
	        		FourByFive.firstTile=null;
	        		FourByFive.secondTile=null;
	        		FourByFive.active_tiles=20;
	        		FourByFive.startTime=0L;
	        		FourByFive.stopTime=0L;
	        		FourByFive.elapsed=0L;
	        		
	    			Intent i = new Intent(FourByFour.getContext(), FourByFive.class);
	    			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			FourByFour.getContext().startActivity(i);			            	}
	        }, 1000);	
		}		
	}
	
	public void disableTile(){
		disabled=1;		
	}
	
	public void onClick(View arg0) {				
		if ((isflipped==0)&&(disabled==0)){				
			flipTile();						
			if (FourByFour.flipped==1)
				FourByFour.firstTile=TileThree.this;
			else
			if (FourByFour.flipped==2){
					FourByFour.secondTile=TileThree.this;
					if(FourByFour.firstTile.value == FourByFour.secondTile.value)
					{																		
						final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
				        handler.postDelayed(new Runnable() {
				            @Override
				            public void run() {
								FourByFour.firstTile.closeTile();
								TileThree.this.disableTile();
								FourByFour.secondTile.closeTile();																										
								FourByFour.flipped=0;							            	}
				        }, 400);

					}
				}
			else
			if (FourByFour.flipped==3)
				{
					FourByFour.firstTile.flipInverse();
					FourByFour.firstTile.flipInverse(); //after the first one is closed the second becomes the first
					FourByFour.firstTile=TileThree.this;
				}						
		}
		else
		{						
				flipInverse();
		}					
	}

	public void disableTouch(){
		FourByFour.tile1.img.setEnabled(false);
		FourByFour.tile2.img.setEnabled(false);
		FourByFour.tile3.img.setEnabled(false);
		FourByFour.tile4.img.setEnabled(false);
		FourByFour.tile5.img.setEnabled(false);
		FourByFour.tile6.img.setEnabled(false);
		FourByFour.tile7.img.setEnabled(false);
		FourByFour.tile8.img.setEnabled(false);
		FourByFour.tile9.img.setEnabled(false);
		FourByFour.tile10.img.setEnabled(false);
		FourByFour.tile11.img.setEnabled(false);
		FourByFour.tile12.img.setEnabled(false);
		FourByFour.tile13.img.setEnabled(false);
		FourByFour.tile14.img.setEnabled(false);
		FourByFour.tile15.img.setEnabled(false);
		FourByFour.tile16.img.setEnabled(false);		
	}
	public void enableTouch(){
		FourByFour.tile1.img.setEnabled(true);
		FourByFour.tile2.img.setEnabled(true);
		FourByFour.tile3.img.setEnabled(true);
		FourByFour.tile4.img.setEnabled(true);
		FourByFour.tile5.img.setEnabled(true);
		FourByFour.tile6.img.setEnabled(true);
		FourByFour.tile7.img.setEnabled(true);
		FourByFour.tile8.img.setEnabled(true);
		FourByFour.tile9.img.setEnabled(true);
		FourByFour.tile10.img.setEnabled(true);
		FourByFour.tile11.img.setEnabled(true);
		FourByFour.tile12.img.setEnabled(true);
		FourByFour.tile13.img.setEnabled(true);
		FourByFour.tile14.img.setEnabled(true);
		FourByFour.tile15.img.setEnabled(true);
		FourByFour.tile16.img.setEnabled(true);		
	}
}
