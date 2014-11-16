package com.compsci408.androidrx.adapters;

import java.util.List;

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
	List<Medication> mData = null;
	
	
	public PatientMedListAdapter(Context context, int layoutResource, List<Medication> data) {
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
			holder.name = (TextView) row.findViewById(android.R.id.text1);
			
			row.setTag(holder);
			
		}
		
		else {
			holder = (MedicationHolder) row.getTag();
		}
		
		Medication medication = mData.get(position);
		holder.name.setText(medication.getName());		
		return row;
	}
	
	static class MedicationHolder {
		TextView name;
	}
}
