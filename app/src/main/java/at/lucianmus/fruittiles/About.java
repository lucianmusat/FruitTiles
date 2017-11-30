package at.lucianmus.fruittiles;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Calendar;


public class About extends Activity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.about);
		TextView copyright = (TextView) findViewById(R.id.copyright);
		copyright.setText("Copyright (C) " + Calendar.getInstance().get(Calendar.YEAR));
		ImageView img = (ImageView) findViewById(R.drawable.thanks);
	}
 
}
