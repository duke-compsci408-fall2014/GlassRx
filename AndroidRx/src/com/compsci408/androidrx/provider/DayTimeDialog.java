package com.compsci408.androidrx.provider;

import java.util.ArrayList;
import java.util.List;

import com.compsci408.androidrx.R;
import com.compsci408.rxcore.Controller;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * {@link DialogFragment} which allows a provider
 * to set a specific time at which to take a
 * a medication.  Also allows for repeating alarms
 * to be set.
 * @author Evan
 */
public class DayTimeDialog extends DialogFragment {
	
	private TimePicker mTimePicker;
	private CheckBox mRepeatWeeks;
	private EditText mWeekCount;
	
	private String mDate;
	
	private Controller mController;
	
	List<Integer> mAlarms;
	
	public DayTimeDialog(String date) {
		super();
		mDate = date;
		mAlarms = new ArrayList<Integer>();
	}
	
	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    // Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View view = inflater.inflate(R.layout.dialog_day_time, null);
		
		mController = Controller.getInstance(getActivity());
		
		mTimePicker = (TimePicker) view.findViewById(R.id.timepicker_alarm_time);
		mRepeatWeeks = (CheckBox) view.findViewById(R.id.checkbox_repeat_weekly);
		mWeekCount = (EditText) view.findViewById(R.id.edittext_week_count);
		
		mRepeatWeeks.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					mWeekCount.setVisibility(View.VISIBLE);
				} else {
					mWeekCount.setVisibility(View.INVISIBLE);
				}
			}
			
		});
				
		builder.setView(view);
		builder.setTitle(mDate);
		builder.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               //TODO:  Add alarms
		        	   DayTimeDialog.this.dismiss();
		       }
		   })
		   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		           DayTimeDialog.this.dismiss();
		       }
		   });
		// Create the AlertDialog object and return it
	    return builder.create();
	}

}
