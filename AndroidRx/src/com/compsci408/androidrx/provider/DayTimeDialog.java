package com.compsci408.androidrx.provider;

import com.compsci408.androidrx.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class DayTimeDialog extends DialogFragment {
	
	private String mDate;
	
	public DayTimeDialog(String date) {
		super();
		mDate = date;
	}
	
	@SuppressLint("InflateParams") @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    // Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_day_time, null));
		builder.setTitle(mDate);
		builder.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               //TODO:  Add alarms
		       }
		   })
		   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		           //TODO:  Cancel input
		       }
		   });
		// Create the AlertDialog object and return it
	    return builder.create();
	}

}
