package com.compsci408.androidrx.patient;

import java.util.ArrayList;
import java.util.List;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.adapters.PrescriptionAdapter;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.datatypes.Prescription;
import com.compsci408.rxcore.listeners.OnMedInfoLoadedListener;
import com.compsci408.rxcore.listeners.OnPrescriptionLoadedListener;

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

/**
 * Activity for displaying general information
 * and scheduled alert events for a given medication.
 * @author Evan
 */
public class PatientMedicationActivity extends Activity {

	private Medication mMedication;
	
	// UI references
	private ImageView mMedImage;
	private ListView mMedDetails;
	private ListView mMedEvents;
	private TextView mMedName;
	
	private List<Prescription> mPrescriptions;
	private PrescriptionAdapter mAdapter;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_medication);
		
		mController = Controller.getInstance(this);
		
		//  Load medication info onto screen
		mController.getMedicationByName(new OnMedInfoLoadedListener() {

			@Override
			public void onMedInfoLoaded(Medication med) {
				mMedication = med;
				List<String> details = new ArrayList<String>();
				
				details.add(PatientMedicationActivity.this.getResources().getString(R.string.med_name)
						+ "  " + mMedication.getName());
				details.add(PatientMedicationActivity.this.getResources().getString(R.string.med_purpose)
						+ "  " + mMedication.getPurpose());
				details.add(PatientMedicationActivity.this.getResources().getString(R.string.med_side_effects)
						+ "  " +mMedication.getSide_effects());
				
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
		                 PatientMedicationActivity.this, 
		                 android.R.layout.simple_list_item_1,
		                 details);
				mMedDetails.setAdapter(arrayAdapter);
			}			
		});
		
		mController.getPrescriptionsForPatient(new OnPrescriptionLoadedListener() {

			@Override
			public void onPrescriptionLoaded(List<Prescription> prescription) {
				mPrescriptions = prescription;
				mAdapter = new PrescriptionAdapter(PatientMedicationActivity.this,
													R.layout.prescription_list_item,
													mPrescriptions, false);
				
				mAdapter.clear();
				mAdapter.addAll(mPrescriptions);
				mMedEvents.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
			}
			
		});
		
		mMedName = (TextView) findViewById(R.id.textview_med_name);
		mMedName.setText(mController.getMedName());
		
		mMedImage = (ImageView) findViewById(R.id.imageview_med);
		
		mMedImage.setImageBitmap(Controller.loadImage(mController.getMedName(), this));
		
		mMedImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PatientMedicationActivity.this, PictureActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
			
		});
		
		
		mMedDetails = (ListView) findViewById(R.id.listview_med_details);	
		mMedEvents = (ListView) findViewById(R.id.listview_med_events);

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
			mController.logOut();
			Intent i = new Intent(PatientMedicationActivity.this, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
