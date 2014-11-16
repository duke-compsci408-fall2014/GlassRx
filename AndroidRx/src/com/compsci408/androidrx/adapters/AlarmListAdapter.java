package com.compsci408.androidrx.adapters;

import java.util.List;

import com.compsci408.androidrx.R;
import com.compsci408.rxcore.alarms.Alarm;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom adapter for maintaining and displaying
 * a list of alarm events. 
 * @author Evan
 */
public class AlarmListAdapter extends ArrayAdapter<Alarm> {

	Context mContext;
	int mLayoutId;
	List<Alarm> mData = null;
	
	public AlarmListAdapter(Context context, int layoutResource, List<Alarm> data) {
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
			holder.day = (TextView) row.findViewById(R.id.textview_alarm_day);
			holder.time = (TextView) row.findViewById(R.id.textview_alarm_time);
			
			row.setTag(holder);
			
		}
		
		else {
			holder = (MedicationHolder) row.getTag();
		}
		
		Alarm alarm = mData.get(position);
		holder.day.setText(alarm.getName() + ",");
		holder.time.setText(Integer.toString(alarm.getTimeHour()) + ":" + Integer.toString(alarm.getTimeMinute()));
		
		return row;
	}
	
	static class MedicationHolder {
		TextView day;
		TextView time;
	}

}
