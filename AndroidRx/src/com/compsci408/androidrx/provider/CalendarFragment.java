package com.compsci408.androidrx.provider;

import java.util.GregorianCalendar;

import com.compsci408.androidrx.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;

/**
 * Fragment which contains a {@link CalendarView}
 * which a provider can use to select a date on
 * which to add a prescription event.
 * @author Evan
 *
 */
public class CalendarFragment extends Fragment{
	
	CalendarView calendar;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        
        calendar = (CalendarView) view.findViewById(R.id.calendar_med_schedule);
        setListeners();
		
		return view;
    }

	private void setListeners() {
		calendar.setOnDateChangeListener(new OnDateChangeListener() {

					@Override
					public void onSelectedDayChange(CalendarView view,
							int year, int month, int dayOfMonth) {
						((NewPrescriptionActivity) getActivity())
								.makeNewPrescription(new GregorianCalendar(year, month, dayOfMonth));						
					}
					
				});
	}

}
