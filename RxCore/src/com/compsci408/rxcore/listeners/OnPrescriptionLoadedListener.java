package com.compsci408.rxcore.listeners;

import java.util.List;

import com.compsci408.rxcore.datatypes.Prescription;

public abstract class OnPrescriptionLoadedListener {

	/**
	 * Callback function for describing behavior
	 * upon successful loading of prescriptions.
	 * @param prescription List of loaded prescriptions
	 */
	public abstract void onPrescriptionLoaded(List<Prescription> prescription);
	
}
