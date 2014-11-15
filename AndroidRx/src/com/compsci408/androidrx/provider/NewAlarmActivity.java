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
import android.widget.TimePicker;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.datatypes.Schedule;
import com.compsci408.rxcore.listeners.OnSchduleAddedListener;

/**
 * {@link Activity} for adding new alerts for
 * for a given medication and patient.  Allows
 * provider to set a medication name, dosage, and
 * dosage description.
 * @author Evan
 */
public class NewAlarmActivity extends Activity implements OnDateChangeListener, OnClickListener{

	Button addTimeComplete;
	CalendarView calendar;
	EditText medName;
	EditText medDose;
	EditText doseDescription;
	
	private List<Alarm> mAlarms;
	private String mDate;
	private int mAddedCount;
	
	private CalendarFragment calendarFrag;
	private NewAlarmTimesFragment timesFrag;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_alarm);
		
		mController = Controller.getInstance(this);
		
		calendarFrag = (CalendarFragment) getFragmentManager().
				findFragmentById(R.id.fragment_calendar);
		((CalendarView) calendarFrag.getView().
				findViewById(R.id.calendar_med_schedule)).setOnDateChangeListener(this);
		
		mAlarms = new ArrayList<Alarm>();
		
		medName = (EditText) findViewById(R.id.edittext_new_med_name);
		medDose =  (EditText) findViewById(R.id.edittext_dose_quantity);
		doseDescription = (EditText) findViewById(R.id.edittext_dose_description);
		
		
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
	
	public void addAlarm(Alarm alarm) {
		mAlarms.add(alarm);
	}
	
	@Override
	public void onSelectedDayChange(CalendarView view, int year, int month,
			int dayOfMonth) {

		mDate = Integer.toString(year) + "-" +
				Integer.toString(month + 1) + "-" +
				Integer.toString(dayOfMonth);
		
		timesFrag = new NewAlarmTimesFragment();
		
		getFragmentManager().beginTransaction()
			.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
			.add(R.id.layout_schedule_set, 
					timesFrag, 
					NewAlarmTimesFragment.class.toString())
			.hide(calendarFrag).commit();
		
		getFragmentManager().executePendingTransactions();
		
		((Button) timesFrag.getView().findViewById(R.id.button_add_day_time))
		.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		
		if (checkInput()) {
		
			TimePicker timePicker = (TimePicker) timesFrag.getView()
					.findViewById(R.id.timepicker_alarm_time);
			
			
			Schedule schedule = new Schedule();
			schedule.setDay_to_take(mDate);
			schedule.setTime_to_take(Integer.toString(timePicker.getCurrentHour()) + ":"
					+ Integer.toString(timePicker.getCurrentMinute()));
			schedule.setPatientID(mController.getPatientId());
			schedule.setMedicine(medName.getText().toString());
			
			mController.addSchedule(schedule, new OnSchduleAddedListener() {
	
				@Override
				public void onScheduleAdded(boolean success) {
					
					Intent intent = new Intent(NewAlarmActivity.this, PatientActivity.class);
					startActivity(intent);
					NewAlarmActivity.this.finish();
				}
				
			});
		}
		
	}
	
	private boolean checkInput() {
		
		medName.setError(null);
		medDose.setError(null);
		doseDescription.setError(null);
		
		boolean valid = true;
		
		if (medName.getText().length() == 0) {
			medName.setError("Enter a name");
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
