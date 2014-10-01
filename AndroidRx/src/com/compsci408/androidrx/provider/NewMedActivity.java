package com.compsci408.androidrx.provider;

import com.compsci408.androidrx.R;
import com.compsci408.androidrx.R.id;
import com.compsci408.androidrx.R.layout;
import com.compsci408.androidrx.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewMedActivity extends Activity {

	Button addMedComplete;
	Button addTimeComplete;
	EditText newMedName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_med);
		
		addMedComplete = (Button) findViewById(R.id.add_med_complete);
		newMedName = (EditText) findViewById(R.id.new_med_title);
		addTimeComplete = (Button) findViewById(R.id.add_time_complete);
		
		addMedComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewMedActivity.this, PatientActivity.class);
				intent.putExtra("NewMed", newMedName.getText().toString());
				intent.putExtra("PatientName", NewMedActivity.this.getIntent().getStringExtra("PatientName"));
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
			
		});
		
		addTimeComplete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewMedActivity.this, NewTimeActivity.class);
				intent.putExtra("NewMed", newMedName.getText().toString());
				intent.putExtra("PatientName", NewMedActivity.this.getIntent().getStringExtra("PatientName"));
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addNewTime(View view){
		
	}
}
