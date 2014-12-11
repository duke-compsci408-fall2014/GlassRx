package com.compsci408.androidrx.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import com.compsci408.androidrx.R;
import com.compsci408.rxcore.datatypes.Event;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Fragment for displaying a calendar of {@link Event}s.
 * This fragment contains functionality for selecting
 * different dates and viewing event information for
 * a selected date.  
 * @author Evan
 *
 */
public class EventCalendarFragment extends Fragment {

	private GregorianCalendar mMonth, mItemMonth;
	private CalendarAdapter mAdapter;
	
	//  Describes behavior for when a date is selected.
	//  Can be used to receive the descriptions of events
	//  for the selected day.
	private OnCalendarClickedCallback mCallback;
	
	private GridView mMedEventGridView;
	private RelativeLayout mPrevious, mNext;
	private TextView mTitle;
	
	private List<String> mDates;
	private List<Event> mEvents;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view =  inflater.inflate(R.layout.fragment_event_calendar, container, false);
		
		mMonth = (GregorianCalendar) GregorianCalendar.getInstance();
		mItemMonth = (GregorianCalendar) mMonth.clone();
		
		mMedEventGridView = (GridView) view.findViewById(R.id.gridview_calendar);
		mAdapter = new CalendarAdapter(getActivity(), mMonth);
		mMedEventGridView.setAdapter(mAdapter);
		
		mPrevious = (RelativeLayout) view.findViewById(R.id.layout_previous_month);
		mNext = (RelativeLayout) view.findViewById(R.id.layout_next_month);
		
		mTitle = (TextView) view.findViewById(R.id.textview_calendar_header);
		
		mEvents = new ArrayList<Event>();
		mDates = new ArrayList<String>();
		
		setListeners();		
		refreshCalendar();
		return view;
		
	}
	
	/**
	 * Set the callback for when a date is selected on the calendar
	 * @param callback  Callback to be set
	 */
	public void setCallback(OnCalendarClickedCallback callback) {
		mCallback = callback;
	}

	
	/**
	 * Set the listeners for all the views and widgets on
	 * the calendar.
	 */
	private void setListeners() {
		
		mPrevious.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		mNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});

		mMedEventGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				mDates.clear();
				
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				
				String selectedGridDate = CalendarAdapter.mDayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*", "");
				
				int gridvalue = Integer.parseInt(gridvalueString);
				
				//  If the user selected a date from the previous
				//  or next month, change months
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				//  If The callback has been set, call its method, passing
				//  all the events associated with the selected date.
				if (mCallback != null) {
					
					List<Event> selected = new ArrayList<Event>();
					for (int i = 0; i < mEvents.size(); i++) {
						if (((Event) mEvents.get(i))
								.getDay_to_take().equals(selectedGridDate)) {
							selected.add(mEvents.get(i));
						}
					}
					mCallback.onCalendarClicked(selected);
				}

			}

		});
	}
	
	
	/**
	 * Set the events to be displayed in this calendar.
	 * @param events  List of events to be displayed.
	 */
	public void setEvents(List<Event> events) {
		mEvents.clear();
		mItemMonth.clear();
		mEvents = events;
		for (Event e : mEvents) {
			mItemMonth.add(GregorianCalendar.DATE, 1);
			mDates.add(e.getDay_to_take());
		}
		mAdapter.setEvents(mDates);
	}
	
	/**
	 * Set the next month to be displayed, i.e. that month which is
	 * chronologically after the currently selected month
	 */
	private void setNextMonth() {
		if (mMonth.get(GregorianCalendar.MONTH) == mMonth
				.getActualMaximum(GregorianCalendar.MONTH)) {
			mMonth.set((mMonth.get(GregorianCalendar.YEAR) + 1),
					mMonth.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			mMonth.set(GregorianCalendar.MONTH,
					mMonth.get(GregorianCalendar.MONTH) + 1);
		}

	}

	/**
	 * Set the previous month to be displayed, i.e. that month which is
	 * chronologically prior to the currently selected month
	 */
	private void setPreviousMonth() {
		if (mMonth.get(GregorianCalendar.MONTH) == mMonth
				.getActualMinimum(GregorianCalendar.MONTH)) {
			mMonth.set((mMonth.get(GregorianCalendar.YEAR) - 1),
					mMonth.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			mMonth.set(GregorianCalendar.MONTH,
					mMonth.get(GregorianCalendar.MONTH) - 1);
		}

	}
	
	/**
	 * Refresh the calendar.  This method is called after the user has
	 * selected a new month to view.  It updates the events to be displayed
	 * and the title of the calendar (in the format 'MMMM yyyy').
	 */
	private void refreshCalendar() {
		mAdapter.refresh();
		mAdapter.notifyDataSetChanged();
		mAdapter.setEvents(mDates);

		mTitle.setText(android.text.format.DateFormat.format("MMMM yyyy", mMonth));
	}

}
