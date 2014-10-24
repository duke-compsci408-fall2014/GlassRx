package com.compsci408.androidrx.provider;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class PatientListActivity extends Activity implements SearchView.OnQueryTextListener {

	ListView patientList;
	SearchView patientSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_list);
		
		patientSearch = (SearchView) findViewById(R.id.patient_search);
		setUpSearch();
		patientList = (ListView) findViewById(R.id.patient_list);
		patientList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(PatientListActivity.this, PatientActivity.class);
				// TODO:  Pass appropriate patient identifier to load right data
				intent.putExtra("PatientId", position);
				intent.putExtra("PatientName", ((TextView)view).getText());	
				
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
		patientList.setTextFilterEnabled(true);
	}
	
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
			startActivity(new Intent(PatientListActivity.this, LoginActivity.class));
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	 public boolean onQueryTextChange(String newText) {
	        if (TextUtils.isEmpty(newText)) {
	            patientList.clearTextFilter();
	        } else {
	            patientList.setFilterText(newText.toString());
	        }
	        return true;
	    }

	    public boolean onQueryTextSubmit(String query) {
	        return false;
	    }
}