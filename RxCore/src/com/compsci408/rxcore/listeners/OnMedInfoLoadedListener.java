package com.compsci408.rxcore.listeners;

import com.compsci408.rxcore.datatypes.Medication;

public abstract class OnMedInfoLoadedListener {

	/**
	 * Callback function for describing behavior
	 * upon successful loading of medication info.
	 * @param med Medication whose info has been requested
	 */
	public abstract void onMedInfoLoaded(Medication med);
}
