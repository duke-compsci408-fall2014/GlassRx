package com.compsci408.androidrx.provider;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EditMedActivity extends Activity {

	Button addMedComplete;
	Button addTime;
	EditText newMedName;
	ListView medAlarms;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_med);
		
		Intent intent = getIntent();
		
		addMedComplete = (Button) findViewById(R.id.button_add_med_complete);
		newMedName = (EditText) findViewById(R.id.edittext_med_name);
		addTime = (Button) findViewById(R.id.button_add_alarm);
		medAlarms = (ListView) findViewById(R.id.listview_med_alarm);
		
		newMedName.setText(intent.getStringExtra("MedName"));
		
		addMedComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditMedActivity.this, PatientActivity.class);
				intent.putExtra("NewMed", newMedName.getText().toString());
				intent.putExtra("PatientName", EditMedActivity.this.getIntent().getStringExtra("PatientName"));
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
			
		});
		
		addTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EditMedActivity.this, NewPrescriptionActivity.class);
				intent.putExtra("NewMed", newMedName.getText().toString());
				intent.putExtra("PatientName", EditMedActivity.this.getIntent().getStringExtra("PatientName"));
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}			
		});			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_med, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			startActivity(new Intent(EditMedActivity.this, LoginActivity.class));
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
