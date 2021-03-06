package com.compsci408.androidrx.provider;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.adapters.MedSearchResultsAdapter;
import com.compsci408.androidrx.adapters.PrescriptionAdapter;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.datatypes.Prescription;
import com.compsci408.rxcore.listeners.OnPrescriptionAddedListener;

/**
 * {@link Activity} for adding new {@link Prescription}s for
 * for a given medication and patient.  Allows
 * provider to set a medication name, dosage, and
 * dosage description.
 * @author Evan
 */
public class NewPrescriptionActivity extends Activity {

	Button addTimeComplete;
	CalendarView calendar;
	AutoCompleteTextView medName;
	EditText medDose;
	EditText doseDescription;
	
	private CalendarFragment calendarFrag;
	private PrescriptionsToAddFragment prescriptionsToAddFrag;
	private NewPrescriptionFragment newPrescriptionFrag;
	
	private Medication mMedication;
	private List<Prescription> mPrescriptions;
	private PrescriptionAdapter mAdapter;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_prescription);
		
		mController = Controller.getInstance(this);
		mPrescriptions = new ArrayList<Prescription>();
		mAdapter = new PrescriptionAdapter(this, R.layout.prescription_list_item, mPrescriptions,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						int position = (int) v.getTag();
						mPrescriptions.remove(position);
						mAdapter.clear();
						mAdapter.addAll(mPrescriptions);
						mAdapter.notifyDataSetChanged();
						prescriptionsToAddFrag.setAdapter(mAdapter);
					}
			
		}, true);
		
		prescriptionsToAddFrag = new PrescriptionsToAddFragment();
		calendarFrag = new CalendarFragment();
		newPrescriptionFrag = new NewPrescriptionFragment();
		
		getFragmentManager().beginTransaction()
			.replace(R.id.layout_schedule_set, prescriptionsToAddFrag).commit();		

		
		medName = (AutoCompleteTextView) findViewById(R.id.textview_new_med_name);
		
		medName.setAdapter(new MedSearchResultsAdapter(this, android.R.layout.simple_list_item_1));
		medName.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mMedication = (Medication) parent.getItemAtPosition(position);
				medName.setText(mMedication.getName());
			} 
			
		});
		
		medDose =  (EditText) findViewById(R.id.edittext_dose_quantity);
		doseDescription = (EditText) findViewById(R.id.edittext_dose_description);
		
		
	}

	public void makeNewCalendar(boolean fromRight) {
		android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
		
		if (fromRight) {
			ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
				.replace(R.id.layout_schedule_set, calendarFrag).commit();
		}
		else {
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
			.replace(R.id.layout_schedule_set, calendarFrag).commit();
		}

		getFragmentManager().executePendingTransactions();
	}
	
	public void makeNewPrescription(GregorianCalendar date){
		
		getFragmentManager().beginTransaction()
			.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
			.replace(R.id.layout_schedule_set, newPrescriptionFrag).commit();
		
		getFragmentManager().executePendingTransactions();
		
		newPrescriptionFrag.setDate(date);
	}
	
	public void createPrescriptions(boolean[] times, List<String> dates) {
		
		getFragmentManager().beginTransaction()
			.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
			.replace(R.id.layout_schedule_set, prescriptionsToAddFrag).commit();
	
		getFragmentManager().executePendingTransactions();
		
		for (int i = 0; i < dates.size(); i++) {
			for (int j = 0; j < times.length; j++) {
				if (times[j]) {
					Prescription p = new Prescription();
					p.setDay_to_take(dates.get(i));
					p.setGeneral_time(j);
					mPrescriptions.add(p);
				}
			}
		}
		mAdapter.clear();
		mAdapter.addAll(mPrescriptions);
		mAdapter.notifyDataSetChanged();
		prescriptionsToAddFrag.setAdapter(mAdapter);
	}
	
	public void addAllPrescriptions() {
		
		if (checkInput()) {
			
			mController.showProgress("Adding Events", true);
			int dose = Integer.parseInt(medDose.getText().toString());
			String description = doseDescription.getText().toString();
			String medication = medName.getText().toString();
			
			for (int i = 0; i < mPrescriptions.size(); i++) {

				mPrescriptions.get(i).setDose(dose);
				mPrescriptions.get(i).setDose_description(description);
				mPrescriptions.get(i).setMedication(medication);
				mPrescriptions.get(i).setPatientID(mController.getPatientId());
				mPrescriptions.get(i).setPhysicianID(mController.getProviderId());
				mPrescriptions.get(i).setSet(false);
				
			}
			
			mController.addPrescriptions(mPrescriptions, new OnPrescriptionAddedListener() {
				
				@Override
				public void onPrescriptionAdded(boolean success) {
					mController.showProgress(false);
					finish();
				}
				
			});
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_time, menu);
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
			Intent i = new Intent(this, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private boolean checkInput() {
		
		medName.setError(null);
		medDose.setError(null);
		doseDescription.setError(null);
		
		boolean valid = true;
		
		if (medName.getText().length() == 0) {
			medName.setError("Enter a valid name");
			valid = false;
		}
		if (medDose.getText().length() == 0) {
			medDose.setError("Enter a dose");
			valid = false;
		}
		if (doseDescription.getText().length() == 0) {
			doseDescription.setError("Enter a description");
			valid = false;
		}
		return valid;
		
		
	}

}
