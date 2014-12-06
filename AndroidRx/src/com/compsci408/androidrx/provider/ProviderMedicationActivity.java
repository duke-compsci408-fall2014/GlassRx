package com.compsci408.androidrx.provider;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.compsci408.androidrx.LoginActivity;
import com.compsci408.androidrx.R;
import com.compsci408.androidrx.util.CalendarAdapter;
import com.compsci408.rxcore.Controller;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Activity which displays to a provider scheduled 
 * alert events for a given medication and patient.
 * @author Evan
 */
public class ProviderMedicationActivity extends Activity {

	private TextView mMedName;
	private GridView mMedEventsCalendar;
	private LinearLayout mLayout;
	
	private GregorianCalendar mMonth, mItemMonth;
	private CalendarAdapter mAdapter;
	
	private List<String> mPrescriptions;
	private List<String> mDates;
	private List<String> mDescriptions;
	
	private Controller mController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provider_medication);
		Locale.setDefault(Locale.US);
		
		mLayout = (LinearLayout) findViewById(R.id.layout_day_events);
		mMonth = (GregorianCalendar) GregorianCalendar.getInstance();
		mItemMonth = (GregorianCalendar) mMonth.clone();
		
		mMedName = (TextView) findViewById(R.id.textview_med_name);		
		
		mController = Controller.getInstance(this);
		mAdapter = new CalendarAdapter(this, mMonth);
		
		mMedEventsCalendar = (GridView) findViewById(R.id.gridview_calendar);
		mMedEventsCalendar.setAdapter(mAdapter);
		
		mMedName.setText(mController.getMedName());
		
		mPrescriptions = new ArrayList<String>();
		
//		loadEvents();
		
		
		

	}
	
//	private void loadEvents() {
//		mController.getPrescriptionsForPatient(new OnPrescriptionLoadedListener() {
//
//			@Override
//			public void onPrescriptionLoaded(List<Prescription> prescriptions) {
//				for (Prescription p : prescriptions) {
//					if (p.getMedication().equals(mController.getMedName())) {
//						mPrescriptions.add(p.toString(true, true));
//						mItemMonth.add(field, value);
//					}
//				}
//				
//			}
//		});
//	}

	private void setListeners() {
		
		RelativeLayout previous = (RelativeLayout) findViewById(R.id.layout_previous_month);
		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.layout_next_month);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		mMedEventsCalendar.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// removing the previous view if added
				if (((LinearLayout) mLayout).getChildCount() > 0) {
					((LinearLayout) mLayout).removeAllViews();
				}
				mDescriptions = new ArrayList<String>();
				mDates = new ArrayList<String>();
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

//				for (int i = 0; i < Utility.startDates.size(); i++) {
//					if (Utility.startDates.get(i).equals(selectedGridDate)) {
//						mDescriptions.add(Utility.nameOfEvent.get(i));
//					}
//				}
//
//				if (mDescriptions.size() > 0) {
//					for (int i = 0; i < mDescriptions.size(); i++) {
//						TextView rowTextView = new TextView(CalendarView.this);
//
//						// set some properties of rowTextView or something
//						rowTextView.setText("Event:" + mDescriptions.get(i));
//						rowTextView.setTextColor(Color.BLACK);
//
//						// add the textview to the linearlayout
//						mLayout.addView(rowTextView);
//
//					}
//
//				}

				mDescriptions = null;

			}

		});
	}
	
	protected void setNextMonth() {
		if (mMonth.get(GregorianCalendar.MONTH) == mMonth
				.getActualMaximum(GregorianCalendar.MONTH)) {
			mMonth.set((mMonth.get(GregorianCalendar.YEAR) + 1),
					mMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			mMonth.set(GregorianCalendar.MONTH,
					mMonth.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (mMonth.get(GregorianCalendar.MONTH) == mMonth
				.getActualMinimum(GregorianCalendar.MONTH)) {
			mMonth.set((mMonth.get(GregorianCalendar.YEAR) - 1),
					mMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			mMonth.set(GregorianCalendar.MONTH,
					mMonth.get(GregorianCalendar.MONTH) - 1);
		}

	}
	
	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.textview_calendar_header);

		mAdapter.refresh();
		mAdapter.notifyDataSetChanged();

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", mMonth));
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
