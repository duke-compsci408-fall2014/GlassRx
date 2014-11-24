package com.compsci408.rxcore.listeners;

import java.util.List;

import com.compsci408.rxcore.datatypes.Prescription;

public abstract class OnPrescriptionLoadedListener {

	public abstract void onPrescriptionLoaded(List<Prescription> prescription);
	
}
