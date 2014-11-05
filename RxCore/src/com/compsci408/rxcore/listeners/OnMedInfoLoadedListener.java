package com.compsci408.rxcore.listeners;

import org.json.JSONObject;

import com.compsci408.rxcore.datatypes.Medication;

public abstract class OnMedInfoLoadedListener {

	public abstract void onMedInfoLoaded(Medication med);
}
