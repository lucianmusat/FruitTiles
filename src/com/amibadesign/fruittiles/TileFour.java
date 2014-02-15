package com.amibadesign.fruittiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;



public class TileFour implements OnClickListener {
	public int isflipped=0;
	public int value=0;
	public int disabled=0;
	public ImageView img;
	public AnimationDrawable frameAnimation;
	public long sessionHighscore;
	
	public TileFour(final Activity parent, int id,int valoare){			
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
			case 0:	TileFour.this.img.setBackgroundResource(R.drawable.animation1); break;
			case 1:	TileFour.this.img.setBackgroundResource(R.drawable.animation2); break;
			case 2:	TileFour.this.img.setBackgroundResource(R.drawable.animation3); break;
			case 3:	TileFour.this.img.setBackgroundResource(R.drawable.animation4); break;
			case 4:	TileFour.this.img.setBackgroundResource(R.drawable.animation5); break;
			case 5:	TileFour.this.img.setBackgroundResource(R.drawable.animation6); break;
			case 6:	TileFour.this.img.setBackgroundResource(R.drawable.animation7); break;
			case 7:	TileFour.this.img.setBackgroundResource(R.drawable.animation8); break;
		} 
		frameAnimation = (AnimationDrawable) TileFour.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();
		isflipped++;	
		FourByFive.flipped++;	
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
				TileFour.this.img.setBackgroundResource(R.drawable.animation_reverse);
				AnimationDrawable frameAnimation = (AnimationDrawable) TileFour.this.img.getBackground();
				frameAnimation.stop();
				frameAnimation.start();
				if(FourByFive.firstTile==TileFour.this)
				{
					if (FourByFive.secondTile!= null)
					{
						FourByFive.firstTile=FourByFive.secondTile;
						FourByFive.secondTile=null;
					}
					else
					if (FourByFive.secondTile == null)
						FourByFive.firstTile=null;	
				}	
				else
				if(FourByFive.secondTile==TileFour.this)
				{
					FourByFive.secondTile=null;
				}
				isflipped--;
				FourByFive.flipped--;				
			}
			final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
	        handler.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	            	enableTouch();				            	}
	        }, 400);		
	}
	
	public void closeTile(){											
		TileFour.this.img.setBackgroundResource(R.drawable.close_animation);
		AnimationDrawable frameAnimation = (AnimationDrawable) TileFour.this.img.getBackground();
		frameAnimation.stop();
		frameAnimation.start();	
		FourByFive.active_tiles--;
		TileFour.this.disableTile();
		
		//if is the last pair of tiles
		if (FourByFive.active_tiles == 0)
		{
			FourByFive.stopTime=System.nanoTime();
			FourByFive.elapsed=((FourByFive.stopTime-FourByFive.startTime)/1000000000);
						
			//Toast.makeText(FourByFive.getContext(),"Congrats! Finished in: "+FourByFive.elapsed+" seconds",Toast.LENGTH_SHORT).show();	
			
			if ((FourByFive.elapsed < FourByFive.getHighscore())||(FourByFive.getHighscore()==0))								
				FourByFive.setHighscore(FourByFive.elapsed);	
			
			
			sessionHighscore = TwoByTwo.elapsed + TwoByThree.elapsed + ThreeByFour.elapsed + FourByFour.elapsed + FourByFive.elapsed;
			
			if ((sessionHighscore < FourByFive.getGlobalHighscore())||(FourByFive.getGlobalHighscore()==0)){
				Toast.makeText(FourByFive.getContext(),"New Highscore!",Toast.LENGTH_SHORT).show();
				//FourByFive.highscoreAnimation();				
				FourByFive.setGlobalHighscore(sessionHighscore);	
			}
    		
	    			Intent i = new Intent(FourByFive.getContext(), MainMenu.class);
	    			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    			FourByFive.getContext().startActivity(i);			            	
	
		}		
	}
	
	public void disableTile(){
		disabled=1;		
	}
	
	public void onClick(View arg0) {				
		if ((isflipped==0)&&(disabled==0)){				
			flipTile();						
			if (FourByFive.flipped==1)
				FourByFive.firstTile=TileFour.this;
			else
			if (FourByFive.flipped==2){
					FourByFive.secondTile=TileFour.this;
					if(FourByFive.firstTile.value == FourByFive.secondTile.value)
					{																		
						final Handler handler = new Handler();  //handler for delayed animation. waits to flip, then close.
				        handler.postDelayed(new Runnable() {
				            @Override
				            public void run() {
								FourByFive.firstTile.closeTile();
								TileFour.this.disableTile();
								FourByFive.secondTile.closeTile();																										
								FourByFive.flipped=0;							            	}
				        }, 400);

					}
				}
			else
			if (FourByFive.flipped==3)
				{
					FourByFive.firstTile.flipInverse();
					FourByFive.firstTile.flipInverse(); //after the first one is closed the second becomes the first
					FourByFive.firstTile=TileFour.this;
				}						
		}
		else
		{						
				flipInverse();
		}					
	}

	public void disableTouch(){
		FourByFive.tile1.img.setEnabled(false);
		FourByFive.tile2.img.setEnabled(false);
		FourByFive.tile3.img.setEnabled(false);
		FourByFive.tile4.img.setEnabled(false);
		FourByFive.tile5.img.setEnabled(false);
		FourByFive.tile6.img.setEnabled(false);
		FourByFive.tile7.img.setEnabled(false);
		FourByFive.tile8.img.setEnabled(false);
		FourByFive.tile9.img.setEnabled(false);
		FourByFive.tile10.img.setEnabled(false);
		FourByFive.tile11.img.setEnabled(false);
		FourByFive.tile12.img.setEnabled(false);
		FourByFive.tile13.img.setEnabled(false);
		FourByFive.tile14.img.setEnabled(false);
		FourByFive.tile15.img.setEnabled(false);
		FourByFive.tile16.img.setEnabled(false);		
	}
	public void enableTouch(){
		FourByFive.tile1.img.setEnabled(true);
		FourByFive.tile2.img.setEnabled(true);
		FourByFive.tile3.img.setEnabled(true);
		FourByFive.tile4.img.setEnabled(true);
		FourByFive.tile5.img.setEnabled(true);
		FourByFive.tile6.img.setEnabled(true);
		FourByFive.tile7.img.setEnabled(true);
		FourByFive.tile8.img.setEnabled(true);
		FourByFive.tile9.img.setEnabled(true);
		FourByFive.tile10.img.setEnabled(true);
		FourByFive.tile11.img.setEnabled(true);
		FourByFive.tile12.img.setEnabled(true);
		FourByFive.tile13.img.setEnabled(true);
		FourByFive.tile14.img.setEnabled(true);
		FourByFive.tile15.img.setEnabled(true);
		FourByFive.tile16.img.setEnabled(true);		
	}
}
