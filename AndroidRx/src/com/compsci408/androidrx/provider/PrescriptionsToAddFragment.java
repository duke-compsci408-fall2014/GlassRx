package com.compsci408.androidrx.provider;

import com.compsci408.androidrx.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Fragment which contains a list of prescription
 * events staged to be added to the database.
 * @author Evan
 */
public class PrescriptionsToAddFragment extends Fragment {
	
	ListView prescriptionsToAdd;
	Button newPrescription;
	Button addPrescriptionsComplete;	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_prescriptions_to_add, container, false);
        
        prescriptionsToAdd = (ListView) view.findViewById(R.id.listview_prescriptions_to_add);
        newPrescription = (Button) view.findViewById(R.id.button_new_prescription);
        addPrescriptionsComplete = (Button) view.findViewById(R.id.button_add_prescriptions);
        setListeners();
        
        
        return view;
	}


	private void setListeners() {
		newPrescription.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((NewPrescriptionActivity) getActivity()).makeNewCalendar(false);				
			}
			
		});
		
		
		addPrescriptionsComplete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((NewPrescriptionActivity) getActivity()).addAllPrescriptions();
			}
			
		});
		
		
	}

	public void setAdapter(ArrayAdapter<?> adapter) {
		prescriptionsToAdd.setAdapter(adapter);
	}
}
