package com.compsci408.androidrx.patient;

import java.util.ArrayList;
import java.util.List;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.adapters.PrescriptionAdapter;
import com.compsci408.androidrx.util.ConstrainedTimePicker;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Prescription;
import com.compsci408.rxcore.datatypes.TimeFrame;
import com.compsci408.rxcore.listeners.OnPrescriptionLoadedListener;

import android.app.Activity;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TimePicker;

public class PatientNewPrescriptionsActivity extends Activity {

	ListView lvNewPrescriptions;
	
	ConstrainedTimePicker timePicker;
	
	private Controller mController;
	private List<Prescription> mPrescriptions;
	private PrescriptionAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_new_prescriptions);
		
		mController = Controller.getInstance(this);
		
		lvNewPrescriptions = (ListView) findViewById(R.id.listview_new_prescriptions);
		
		mController.getPrescriptionsForPatient(new OnPrescriptionLoadedListener() {

			@Override
			public void onPrescriptionLoaded(List<Prescription> prescription) {
				mPrescriptions = new ArrayList<Prescription>();
				for (Prescription p :prescription) {
					if (!p.getSet()) {
						mPrescriptions.add(p);
					}
				}
				mAdapter = new PrescriptionAdapter(PatientNewPrescriptionsActivity.this, 
						R.layout.prescription_list_item, mPrescriptions, true);
				
				mAdapter.clear();
				mAdapter.addAll(mPrescriptions);
				mAdapter.notifyDataSetChanged();
				lvNewPrescriptions.setAdapter(mAdapter);
			}
			
		});
		
		setListeners();
		
	}

	private void setListeners() {
		lvNewPrescriptions.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Prescription p = (Prescription) parent.getItemAtPosition(position);
				mController.setMedName(p.getMedication());
				mController.setDayOfWeek(mController.getDayFromDate(p.getDay_to_take()));
				
				TimeFrame timeframe = null;
				for (TimeFrame tf : TimeFrame.values()) {
					if (tf.getId() == p.getGeneral_time()) {
						timeframe = tf;
					}
				}
				
				timePicker = new ConstrainedTimePicker(PatientNewPrescriptionsActivity.this, 
						new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						
					}
					
				}, timeframe.getStartHour(), 0, false, mController.getDayOfWeek());
				timePicker.setMax(timeframe.getEndHour(), 59);
				timePicker.setMin(timeframe.getStartHour(), 0);
				timePicker.show();
				
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_new_prescriptions, menu);
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}