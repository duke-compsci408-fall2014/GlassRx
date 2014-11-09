package com.compsci408.androidrx;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.compsci408.rxcore.Constants;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.listeners.OnMedInfoLoadedListener;
import com.compsci408.rxcore.requests.ResponseCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MedicationActivity extends Activity {

	private Medication mMedication;
	
	// UI references
	private ImageView mMedImage;
	private ListView mMedDetails;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medication);
		
		mMedImage = (ImageView) findViewById(R.id.imageview_med);
		
		mMedImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Implement picture-taking functionality
				
			}
			
		});
		
		
		mMedDetails = (ListView) findViewById(R.id.listview_med_details);
		
		mController = Controller.getInstance(this);
		
		
		//  Load medication info onto screen
		mController.getMedInfo(new OnMedInfoLoadedListener() {

			@Override
			public void onMedInfoLoaded(Medication med) {
				mMedication = med;
				List<String> details = new ArrayList<String>();
				
				details.add(MedicationActivity.this.getResources().getString(R.string.med_name)
						+ mMedication.getName());
				details.add(MedicationActivity.this.getResources().getString(R.string.med_purpose)
						+ mMedication.getPurpose());
				details.add(MedicationActivity.this.getResources().getString(R.string.med_side_effects)
						+ mMedication.getSide_effects());
				
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
		                 MedicationActivity.this, 
		                 android.R.layout.simple_list_item_1,
		                 details );
				mMedDetails.setAdapter(arrayAdapter);
			}			
		});
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medication, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			Intent i = new Intent(MedicationActivity.this, LoginActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
