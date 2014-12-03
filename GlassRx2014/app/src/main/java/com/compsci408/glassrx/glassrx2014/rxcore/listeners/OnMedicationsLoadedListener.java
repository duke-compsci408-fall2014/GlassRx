package com.compsci408.glassrx.glassrx2014.rxcore.listeners;

import java.util.ArrayList;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Medication;

public abstract class OnMedicationsLoadedListener {
	
	public abstract void onMedicationsLoaded(ArrayList<Medication> medications);

}
