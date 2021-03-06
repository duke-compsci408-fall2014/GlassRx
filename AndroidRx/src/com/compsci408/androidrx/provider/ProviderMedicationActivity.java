package com.compsci408.androidrx.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.util.EventCalendarFragment;
import com.compsci408.androidrx.util.OnCalendarClickedCallback;
import com.compsci408.rxcore.Constants;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Event;
import com.compsci408.rxcore.datatypes.Prescription;
import com.compsci408.rxcore.datatypes.Schedule;
import com.compsci408.rxcore.listeners.OnPrescriptionLoadedListener;
import com.compsci408.rxcore.listeners.OnScheduleLoadedListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Activity which displays to a provider scheduled 
 * alert events for a given medication and patient.
 * @author Evan
 */
public class ProviderMedicationActivity extends Activity {

	private TextView mMedName;
	private EventCalendarFragment mMedEventsCalendar;
	private LinearLayout mLayout;
	
	private List<Event> mEvents;
	private List<String> mDescriptions;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provider_medication);
		Locale.setDefault(Locale.US);
		
		mLayout = (LinearLayout) findViewById(R.id.layout_day_events);
		
		mMedName = (TextView) findViewById(R.id.textview_med_name);	
		
		mController = Controller.getInstance(this);
		
		mMedEventsCalendar = new EventCalendarFragment();
		getFragmentManager().beginTransaction()
			.replace(R.id.layout_event_calendar, mMedEventsCalendar).commit();
		
		mMedName.setText(mController.getMedName());
		
		mEvents = new ArrayList<Event>();
		
		setListeners();
		loadEvents();
	}
	
	private void loadEvents() {
		mEvents.clear();
		
		mController.getPrescriptionsForPatient(new OnPrescriptionLoadedListener() {

			@Override
			public void onPrescriptionLoaded(List<Prescription> prescriptions) {
				for (Prescription p : prescriptions) {
					if (p.getMedication().equals(mController.getMedName())
							&& !p.getSet()) {
						mEvents.add(p);
					}
				}
				mController.getSchedulesForPatient(new OnScheduleLoadedListener() {

					@Override
					public void onScheduleLoaded(List<Schedule> schedule) {
						for (Schedule s : schedule) {
							if (s.getMedication().equals(mController.getMedName())) {
								mEvents.add(s);
							}
						}
						mMedEventsCalendar.setEvents(mEvents);
						
					}
					
				});
			}
		});
		
		
	}

	private void setListeners() {

		mMedEventsCalendar.setCallback(new OnCalendarClickedCallback() {

			@Override
			public void onCalendarClicked(List<Event> selectedDayEvents) {
				mDescriptions = new ArrayList<String>();
				
				if (((LinearLayout) mLayout).getChildCount() > 0) {
					((LinearLayout) mLayout).removeAllViews();
				}
				
				for (int i = 0; i < selectedDayEvents.size(); i++) {
					mDescriptions.add(selectedDayEvents.get(i)
							.toFormattedString(false, Constants.DATE_FORMAT_READABLE));
				}
				
				if (mDescriptions.size() > 0) {
					for (int i = 0; i < mDescriptions.size(); i++) {
						TextView rowTextView = new TextView(ProviderMedicationActivity.this);

						// set some properties of rowTextView or something
						rowTextView.setText(mDescriptions.get(i));
						rowTextView.setTextColor(Color.BLACK);

						// add the textview to the linearlayout
						mLayout.addView(rowTextView);

					}

				}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.provider_medication, menu);
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
