package com.compsci408.glassrx.glassrx2014.rxcore.listeners;

import java.util.List;

import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Patient;

public abstract class OnPatientsLoadedListener {
	
	public abstract void onPatientsLoaded(List<Patient> patients);

}
