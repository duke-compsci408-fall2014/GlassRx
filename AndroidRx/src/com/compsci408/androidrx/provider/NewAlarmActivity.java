package com.compsci408.androidrx.provider;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.listeners.OnAlarmAddedListener;

/**
 * {@link Activity} for adding new alerts for
 * for a given medication and patient.  Allows
 * provider to set a medication name, dosage, and
 * dosage description.
 * @author Evan
 */
public class NewAlarmActivity extends Activity implements OnDateChangeListener{

	private Button addTimeComplete;
	private CalendarView calendar;
	private EditText medName;

	private List<Alarm> mAlarms;
	private int mAddedCount;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_med);
		
		mController = Controller.getInstance(this);
		
		mAlarms = new ArrayList<Alarm>();
		
		medName = (EditText) findViewById(R.id.edittext_new_med_name);
		
		calendar = (CalendarView) findViewById(R.id.calendar_med_schedule);
		calendar.setOnDateChangeListener(this);
		
		final OnAlarmAddedListener listener = new OnAlarmAddedListener(){

			@Override
			public void onAlarmAdded(boolean success) {
				
			}
			
		};
		
		addTimeComplete = (Button) findViewById(R.id.button_add_med_complete);
		addTimeComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < mAlarms.size(); i++) {
					mController.addAlarm(mAlarms.get(i), listener);
				}
				Intent intent = new Intent(NewAlarmActivity.this, PatientActivity.class);
				intent.putExtra("PatientName", NewAlarmActivity.this.getIntent().getStringExtra("PatientName"));
				intent.putExtra("NewMedName", medName.getText().toString());
				startActivity(intent);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
			
		});
		
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
			startActivity(new Intent(NewAlarmActivity.this, LoginActivity.class));
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {
		String date = Integer.toString(month) + "/" + Integer.toString(dayOfMonth)
				+ "/" + Integer.toString(year);
		
		new DayTimeDialog(date).show(getFragmentManager(), "DayTimeDialog");
	}
	
	public void addAlarm(Alarm alarm) {
		mAlarms.add(alarm);
	}

}
