package com.compsci408.androidrx.adapters;

import java.util.ArrayList;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.listeners.OnMedicationsLoadedListener;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

/**
 * Custom adapter for maintaining and displaying
 * a list of medication names based on filtered search results. 
 * @author Evan
 */
public class MedSearchResultsAdapter extends ArrayAdapter<Medication> implements Filterable{

	private ArrayList<Medication> mData;
	private int mLayoutId;
	
	private Controller mController;
	
	public MedSearchResultsAdapter(Context context, int resource) {
		super(context, resource);
        mData = new ArrayList<Medication>();
        mController = Controller.getInstance(context);
        mLayoutId = resource;
	}
	
	@Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Medication getItem(int index) {
        return mData.get(index);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View row = convertView;
		MedicationHolder holder = null;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
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

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                if(constraint != null) {
                    try {
                        mController.filterMedicationsByName(constraint.toString(), 
                        		new OnMedicationsLoadedListener() {

									@Override
									public void onMedicationsLoaded(
											ArrayList<Medication> medications) {
										mData = medications;
										filterResults.values = mData;
					                    filterResults.count = mData.size();
					                    publishResults(constraint, filterResults);
					                    
									}
                        	
                        });
                    }
                    catch(Exception e) {
                        Log.e(MedSearchResultsAdapter.class.getName(), e.getMessage());
                    }
                    filterResults.values = mData;
                    filterResults.count = mData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results != null && results.count > 0) {
                notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

}
