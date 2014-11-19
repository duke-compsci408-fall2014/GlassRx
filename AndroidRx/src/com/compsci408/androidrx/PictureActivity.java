package com.compsci408.androidrx;

import com.compsci408.androidrx.patient.PatientMedicationActivity;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.listeners.OnPictureTakenListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PictureActivity extends Activity {

	SurfaceView mSurface;
	ImageButton mTakePictureButton;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		
		mSurface = (SurfaceView) findViewById(R.id.surfaceview_camera);
		mTakePictureButton = (ImageButton) findViewById(R.id.imagebutton_take_picture);
		
		mController = Controller.getInstance(this);
		
		mTakePictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mController.takePicture(mSurface.getHolder(), false, new OnPictureTakenListener() {

					@Override
					public void onPictureTaken(boolean success) {
						if (success) {
							Intent intent = new Intent(PictureActivity.this, PatientMedicationActivity.class);
							startActivity(intent);
							overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
							finish();
						}
					}
					
				});
			}
			
		});
		
		mController.startCamera(mSurface.getHolder());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			Intent i = new Intent(this, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}