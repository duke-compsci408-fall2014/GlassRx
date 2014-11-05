package com.compsci408.rxcore.listeners;

import java.util.List;

import com.compsci408.rxcore.datatypes.Medication;

public abstract class OnMedicationsLoadedListener {
	
	public abstract void onMedicationsLoaded(List<Medication> medications);

}
