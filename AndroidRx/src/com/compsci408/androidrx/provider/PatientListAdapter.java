package com.compsci408.androidrx.provider;

import java.util.List;

import com.compsci408.androidrx.R;
import com.compsci408.rxcore.datatypes.Patient;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PatientListAdapter extends ArrayAdapter<Patient> {
	
	Context mContext;
	int mLayoutId;
	List<Patient> mData = null;
	
	
	public PatientListAdapter(Context context, int layoutResource, List<Patient> data) {
		super(context, layoutResource, data);
		mContext = context;
		mLayoutId = layoutResource;
		mData = data;		
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		PatientHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutId, parent, false);
			
			holder = new PatientHolder();
			holder.name = (TextView) row.findViewById(R.id.textview_patient_name);
			
			row.setTag(holder);
			
		}
		
		else {
			holder = (PatientHolder) row.getTag();
		}
		
		Patient patient = mData.get(position);
		holder.name.setText(patient.getName());
		
		return row;
	}
	
	static class PatientHolder {
		TextView name;
	}
}
