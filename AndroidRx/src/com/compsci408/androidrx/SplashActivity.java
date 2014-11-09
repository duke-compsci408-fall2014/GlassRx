package com.compsci408.androidrx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * A splash screen activity to initialize on launch
 * while any necessary resource loading and network
 * operations occur.
 * @author Evan
 */
public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 4000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable() { 
            @Override
            public void run() {
                new SplashLogoTask().execute();
            }
        }, SPLASH_TIME_OUT/7);
		
		new Handler().postDelayed(new Runnable() { 
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
	}
	
	/**
	 * A class for creating a title {@link TextView} which
	 * appears on-screen while the splash screen is present.
	 * @author Evan
	 *
	 */
	class SplashLogoTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
			
					Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Android.ttf");
					Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.title_animation);
					
					TextView titleView = (TextView) SplashActivity.this.findViewById(R.id.textview_splash_title);
					titleView.setTypeface(typeFace);
					titleView.startAnimation(anim);
					titleView.setVisibility(View.VISIBLE);
				}
				
			});
			return null;
		}
	}
}
