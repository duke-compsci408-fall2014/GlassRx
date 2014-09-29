package com.compsci408.androidrx;

import com.example.androidrx.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 5000;
	private RelativeLayout mLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mLayout = (RelativeLayout) findViewById(R.id.splash_layout);
		
		new Handler().postDelayed(new Runnable() { 
            @Override
            public void run() {
                new SplashLogoTask().execute();
                finish();
            }
        }, SPLASH_TIME_OUT/4);
		
		new Handler().postDelayed(new Runnable() { 
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
	}
	
	class SplashLogoTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					final RelativeLayout.LayoutParams params = 
			                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
			                		RelativeLayout.LayoutParams.WRAP_CONTENT);
					params.addRule(RelativeLayout.CENTER_IN_PARENT);
					
					Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Android.ttf");
					Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.title_animation);
					
					final TextView titleView = new TextView(SplashActivity.this);
					titleView.setText("AndroidRx");
					titleView.setTextSize(22);
					titleView.setTextColor(getResources().getColor(android.R.color.black));
					titleView.setTypeface(typeFace);
					titleView.setVisibility(View.INVISIBLE);
					titleView.setLayoutParams(params);
					
					mLayout.addView(titleView);
					titleView.setVisibility(View.VISIBLE);
					titleView.startAnimation(anim);
				}
				
			});
			return null;
		}
	}
}
