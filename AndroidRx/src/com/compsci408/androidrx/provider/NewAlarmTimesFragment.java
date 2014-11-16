package com.compsci408.androidrx.provider;

import com.compsci408.androidrx.R;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Schedule;
import com.compsci408.rxcore.listeners.OnSchduleAddedListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

public class NewAlarmTimesFragment extends Fragment {
	
	Button buttonDone;
	TimePicker timePicker;

	private Controller mController;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_alarm_times, container, false);
        
        mController = Controller.getInstance(getActivity());
        
        timePicker = (TimePicker) view.findViewById(R.id.timepicker_alarm_time);
        
        buttonDone = (Button) view.findViewById(R.id.button_add_day_time);

        
        
        
        return view;
    }

}
