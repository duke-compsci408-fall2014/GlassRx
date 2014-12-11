package com.compsci408.rxcore.listeners;

import java.util.ArrayList;
import com.compsci408.rxcore.datatypes.Medication;

public abstract class OnMedicationsLoadedListener {
	
	/**
	 * Callback function for describing behavior
	 * upon successful loading of medications.
	 * @param medications List of loaded medications
	 */
	public abstract void onMedicationsLoaded(ArrayList<Medication> medications);

}
