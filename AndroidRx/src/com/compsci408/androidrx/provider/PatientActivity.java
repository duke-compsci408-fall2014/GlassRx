package com.compsci408.androidrx.provider;

import java.util.ArrayList;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.MedicationActivity;
import com.compsci408.androidrx.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PatientActivity extends Activity {

	TextView patientName;
	ListView medList;
	Button addMed;
	
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient);
		
		adapter=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1,
	            listItems);
		
		Intent intent = getIntent();	
		
		patientName = (TextView) findViewById(R.id.patient_name);
		patientName.setText(intent.getStringExtra("PatientName"));
		
		adapter.addAll(getResources().getStringArray(R.array.med_list));
		medList = (ListView) findViewById(R.id.patient_med_list);
	    medList.setAdapter(adapter);
	    medList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent = new Intent(PatientActivity.this, MedicationActivity.class);
				intent.putExtra("MedName", parent.getItemAtPosition(position).toString());
				intent.putExtra("Nickname", "The Round White One");
				intent.putExtra("Dosage", "2 pills");
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
	    	
	    });
		
	    if (intent.getStringExtra("NewMed") != null) {
			listItems.add(intent.getStringExtra("NewMed"));
	        adapter.notifyDataSetChanged();
		}
		
		addMed = (Button) findViewById(R.id.add_med);
		addMed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PatientActivity.this, EditMedActivity.class);
				intent.putExtra("PatientName", patientName.getText().toString());
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