package com.compsci408.androidrx.provider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.MedicationActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.adapters.PatientScheduleAdapter;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Schedule;
import com.compsci408.rxcore.listeners.OnScheduleLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * {@link Activity} which displays a given
 * patient's information, including his/her
 * name and all scheduled medication alerts.
 * Also provides the opportunity to add a new
 * alert (or series of alerts).
 * @author Evan
 */
public class PatientActivity extends Activity {

	TextView patientName;
	ListView medList;
	Button addMed;
	
    PatientScheduleAdapter mAdapter;
    
    private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient);
		
		mController = Controller.getInstance(this);
		
		mController.getPatientSchedule(new OnScheduleLoadedListener() {

			@Override
			public void onScheduleLoaded(List<Schedule> schedule) {
				Set<String> currentMeds = new HashSet<String>();
				
				
				mAdapter = new PatientScheduleAdapter(
		                PatientActivity.this, 
		                android.R.layout.simple_list_item_1,
		                schedule);
				medList.setAdapter(mAdapter);
			}
			
		});
		
		
		patientName = (TextView) findViewById(R.id.textview_patient_name);
		patientName.setText(mController.getPatientName());
		
		medList = (ListView) findViewById(R.id.listview_patient_meds);
		
	    medList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mController.setMedName(((Schedule) parent.getItemAtPosition(position)).getMedication());
				
				Intent intent = new Intent(PatientActivity.this, MedicationActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
	    	
	    });
		
		addMed = (Button) findViewById(R.id.button_add_med);
		addMed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mController.setPatientName(patientName.getText().toString());
				
				Intent intent = new Intent(PatientActivity.this, NewAlarmActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			startActivity(new Intent(PatientActivity.this, LoginActivity.class));
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
