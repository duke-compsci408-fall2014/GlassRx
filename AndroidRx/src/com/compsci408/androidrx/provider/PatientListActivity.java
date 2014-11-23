package com.compsci408.androidrx.provider;

import java.util.List;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.adapters.PatientListAdapter;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Patient;
import com.compsci408.rxcore.listeners.OnPatientsLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

/**
 * {@link Activity} which displays a list of the current
 * provider's associated patients as well as a {@link SearchView}
 * for filtering results in the event of a large number of
 * patients for a single provider.
 * @author Evan
 */
public class PatientListActivity extends Activity implements SearchView.OnQueryTextListener {

	ListView patientList;
	SearchView patientSearch;
	
	PatientListAdapter mAdapter;
	Filter mFilter;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_list);
		
		mController = Controller.getInstance(this);
		
		mController.showProgress("Loading Patients", true);
		
		mController.getPatients(new OnPatientsLoadedListener() {

			@Override
			public void onPatientsLoaded(List<Patient> patients) {
				mAdapter = new PatientListAdapter(
		                PatientListActivity.this, 
		                android.R.layout.simple_list_item_1,
		                patients);
				patientList.setAdapter(mAdapter);
				mFilter = mAdapter.getFilter();
				mController.showProgress(false);
			}
			
		});
		
		patientSearch = (SearchView) findViewById(R.id.search_patient);
		setUpSearch();
		patientList = (ListView) findViewById(R.id.listview_patients);
		patientList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Patient selected = (Patient) patientList.getAdapter().getItem(position);
				mController.setPatientId(selected.getPatientID());
				mController.setPatientName(selected.getName());
				
				
				Intent intent = new Intent(PatientListActivity.this, PatientActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
		patientList.setTextFilterEnabled(false);		
	}
	
	/**
	 * Configure the appearance of the {@link SearchView},
	 * as well as its response to changes in query text.
	 */
	private void setUpSearch() {
		patientSearch.setIconifiedByDefault(false);
        patientSearch.setOnQueryTextListener(this);
        patientSearch.setSubmitButtonEnabled(false);
        patientSearch.setQueryHint("Filter Results");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_list, menu);
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

	 public boolean onQueryTextChange(String newText) {
    	if (mFilter != null && newText != null) {
    		mFilter.filter(newText); 
    	}
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
