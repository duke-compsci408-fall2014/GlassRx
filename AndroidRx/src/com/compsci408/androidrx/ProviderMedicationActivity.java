package com.compsci408.androidrx;

import java.util.ArrayList;
import java.util.List;

import com.compsci408.androidrx.adapters.PrescriptionAdapter;
import com.compsci408.androidrx.patient.PatientMedicationActivity;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Prescription;
import com.compsci408.rxcore.listeners.OnPrescriptionLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class ProviderMedicationActivity extends Activity {

	private TextView mMedName;
	private ListView mMedEvents;
	
	private List<Prescription> mPrescription;
	private PrescriptionAdapter mAdapter;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provider_medication);
		
		mMedName = (TextView) findViewById(R.id.textview_med_name);		
		mMedEvents = (ListView) findViewById(R.id.listview_provider_med_events);
		
		mController = Controller.getInstance(this);
		
		mMedName.setText(mController.getMedName());
		
		mPrescription = new ArrayList<Prescription>();
		mAdapter = new PrescriptionAdapter(ProviderMedicationActivity.this,
				R.layout.prescription_list_item,
				mPrescription);
		
		mController.getPrescription(new OnPrescriptionLoadedListener() {

			@Override
			public void onPrescriptionLoaded(List<Prescription> prescription) {
				mPrescription = prescription;
				
				mAdapter.clear();
				mAdapter.addAll(mPrescription);
				mAdapter.notifyDataSetChanged();
				mMedEvents.setAdapter(mAdapter);
			}
			
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.provider_medication, menu);
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