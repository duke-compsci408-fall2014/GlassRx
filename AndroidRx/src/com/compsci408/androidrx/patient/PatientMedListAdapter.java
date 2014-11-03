package com.compsci408.androidrx.patient;

import com.compsci408.androidrx.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compsci408.rxcore.datatypes.Medication;


public class PatientMedListAdapter extends ArrayAdapter<Medication> {

	Context mContext;
	int mLayoutId;
	Medication[] mData = null;
	
	
	public PatientMedListAdapter(Context context, int layoutResource, Medication[] data) {
		super(context, layoutResource, data);
		mContext = context;
		mLayoutId = layoutResource;
		mData = data;		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MedicationHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutId, parent, false);
			
			holder = new MedicationHolder();
			holder.icon = (ImageView) row.findViewById(R.id.imageview_med_icon);
			holder.name = (TextView) row.findViewById(R.id.textview_med_name);
			holder.nickname = (TextView) row.findViewById(R.id.textview_med_nickname);
			
			row.setTag(holder);
			
		}
		
		else {
			holder = (MedicationHolder) row.getTag();
		}
		
		Medication medication = mData[position];
		holder.name.setText(medication.getName());
		holder.nickname.setText("(" + medication.getNickname() + ")");
		
		return row;
	}
	
	static class MedicationHolder {
		ImageView icon;
		TextView name;
		TextView nickname;
	}
}
