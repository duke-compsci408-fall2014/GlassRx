package com.compsci408.androidrx.adapters;

import java.util.List;
import com.compsci408.rxcore.datatypes.Schedule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ScheduleAdapter extends ArrayAdapter<Schedule> {
	
	Context mContext;
	int mLayoutId;
	
	List<Schedule> mData;
	
	public ScheduleAdapter(Context context, int layoutResource, List<Schedule> data) {
		super(context, layoutResource, data);
		mContext = context;
		mLayoutId = layoutResource;
		mData = data;	
	}
	
	
	static class ScheduleHolder {
		TextView description;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ScheduleHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutId, parent, false);
			
			holder = new ScheduleHolder();
			holder.description = (TextView) row.findViewById(android.R.id.text1);
			
			row.setTag(holder);
			
		}
		
		else {
			holder = (ScheduleHolder) row.getTag();
		}
		
		Schedule schedule = mData.get(position);
		holder.description.setText(schedule.getMedication() + ":  " + schedule.getDay_to_take() 
				+ ", " + get12HourTime(schedule.getTime_to_take()));
		
		return row;
	}
	
	private String get12HourTime(String time) {
		try {
			String[] amPm = {"AM", "PM"};
			
			StringBuilder builder = new StringBuilder();
			
			String[] splitTime = time.split(":");
			
			builder.append(Integer.parseInt(splitTime[0]) % 12); 
			builder.append(":");
			builder.append(splitTime[1]);
			builder.append(amPm[Integer.parseInt(splitTime[0]) / 12]);
			
			return builder.toString();
		} catch (NumberFormatException e) {
			return time;
		}
		
		
		
	}
	
	
	
}

