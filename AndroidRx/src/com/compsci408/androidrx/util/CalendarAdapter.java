package com.compsci408.androidrx.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import com.compsci408.androidrx.R;
import com.compsci408.rxcore.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Custom adapter for maintaining and displaying 
 * scheduled events on a calendar. 
 * @author Evan
 */
public class CalendarAdapter extends BaseAdapter {
	
	private Context mContext;
	private Calendar mCalendar;
	private GregorianCalendar mLastMonth;
	
	public GregorianCalendar mLastMonthMax;
	private GregorianCalendar mSelectedDate;
	
	int mFirstDay;  
	int mMaxWeekNumber;  
	int mMaxPrev;  
	int mCalMaxPrev;  
	int mLastWeekDay;  
	int mDaysLeft;  
	int mMonthLength; 
	
	String mItemValue, mCurrentDateString;
	SimpleDateFormat mFormat;
	
	private List<String> mEvents;
	public static List<String> mDayString;
	private View mLastView;
	
	public CalendarAdapter(Context context, GregorianCalendar calendar) {
		CalendarAdapter.mDayString = new ArrayList<String>();
		Locale.setDefault(Locale.US);
		mCalendar = calendar;
		mSelectedDate = (GregorianCalendar) mCalendar.clone();
		mContext = context;
		mCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
		mEvents = new ArrayList<String>();
		mFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DATABASE, Locale.US);
		mCurrentDateString = mFormat.format(mSelectedDate.getTime());
		refresh();
	}

	@Override
	public int getCount() {
		return mDayString.size();
	}

	@Override
	public Object getItem(int position) {
		return mDayString.get(position);
	}
	
	public void setEvents(List<String> mDates) {
		for (int i = 0; i < mDates.size(); i++) {
			if (mDates.get(i).length() == 1) {
				mDates.set(i,  "0" + mDates.get(i));
			}
		}
		this.mEvents = mDates;
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		TextView dayView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.calendar_item, null);
		}
		
		dayView = (TextView) view.findViewById(R.id.date);
		String[] timeParts = mDayString.get(position).split("-");
		String value = timeParts[2].replaceFirst("^0*", "");
		
		if ((Integer.parseInt(value) > 1 && position < mFirstDay)
				|| (Integer.parseInt(value) < 7 && position > 28)) {
			dayView.setTextColor(Color.WHITE);
			dayView.setClickable(false);
			dayView.setFocusable(false);
		}
		else {
			dayView.setTextColor(mContext.getResources().getColor(R.color.duke_blue));
		}
		
		if (mDayString.get(position).equals(mCurrentDateString)) {
			setSelected(view);
			mLastView = view;
		}
		else {
			view.setBackgroundResource(R.color.light_gray);
		}
		dayView.setText(value);
		
		String date = mDayString.get(position);
		
		if (date.length() == 1) {
			date = "0" + date;
		}
		
		String month = "" + (mCalendar.get(GregorianCalendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		
		ImageView image = (ImageView) view.findViewById(R.id.date_icon);
		if (date.length() > 0 && mEvents != null && mEvents.contains(date)) {
			image.setVisibility(View.VISIBLE);
		}
		else {
			image.setVisibility(View.INVISIBLE);
		}
		return view;
		
	}
	
	/**
	 * Modify background of selected date
	 * to indicate that the date has been selected
	 * @param v  View of selected date
	 * @return Original view with modified background.
	 */
	public View setSelected(View v) {
		if (mLastView != null) {
			mLastView.setBackgroundResource(R.color.light_gray);
		}
		mLastView = v;
		v.setBackgroundResource(R.drawable.calendar_item_selected);
		return v;
	}
	
	/**
	 * Refresh the calendar adapter, clearing stored events,
	 * recalculating dates (in case the selected month has
	 * changed), and redrawing the views.
	 */
	public void refresh() {
		mEvents.clear();
		mDayString.clear();
		Locale.setDefault(Locale.US);
		mLastMonth = (GregorianCalendar) mCalendar.clone();
		mFirstDay = mCalendar.get(GregorianCalendar.DAY_OF_WEEK);
		mMaxWeekNumber = mCalendar.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		mMonthLength = mMaxWeekNumber * 7;
		mMaxPrev = getMaxPrevious();
		mCalMaxPrev = mMaxPrev - (mFirstDay - 1);
		mLastMonthMax = (GregorianCalendar) mLastMonth.clone();
		mLastMonthMax.set(GregorianCalendar.DAY_OF_MONTH, mCalMaxPrev + 1);
		
		for (int i = 0; i < mMonthLength; i ++) {
			mItemValue = mFormat.format(mLastMonthMax.getTime());
			mLastMonthMax.add(GregorianCalendar.DATE, 1);
			mDayString.add(mItemValue);
		}
		
	}
	
	
	/**
	 * Get the maximum date of the previous month
	 * @return  Integer value representing date of
	 * last day of previous month
	 */
	private int getMaxPrevious() {
		int maxPrev;
		if (mCalendar.get(GregorianCalendar.MONTH) == mCalendar
				.getActualMinimum(GregorianCalendar.MONTH)) {
			mLastMonth.set((mCalendar.get(GregorianCalendar.YEAR) - 1), 
					mCalendar.getActualMaximum(GregorianCalendar.MONTH), 1);
		} 
		else {
			mLastMonth.set(GregorianCalendar.MONTH, 
					mCalendar.get(GregorianCalendar.MONTH) - 1);
		}
		maxPrev = mLastMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		return maxPrev;
	}

}
