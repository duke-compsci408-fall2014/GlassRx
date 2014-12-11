package com.compsci408.rxcore.listeners;

import java.util.List;

import com.compsci408.rxcore.datatypes.Patient;

public abstract class OnPatientsLoadedListener {
	
	/**
	 * Callback function for describing behavior
	 * upon successful loading of patient information.
	 * @param patients List of loaded patients
	 */
	public abstract void onPatientsLoaded(List<Patient> patients);

}
