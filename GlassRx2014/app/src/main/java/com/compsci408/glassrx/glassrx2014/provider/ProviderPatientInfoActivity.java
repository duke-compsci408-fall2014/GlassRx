package com.compsci408.glassrx.glassrx2014.provider;

import com.compsci408.glassrx.glassrx2014.R;
//import com.compsci408.glassrx.glassrx2014.patient.PatientMainActivity;
import com.compsci408.glassrx.glassrx2014.patient.PatientMedListActivity;
import com.compsci408.glassrx.glassrx2014.rxcore.Controller;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Patient;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Prescription;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnPrescriptionLoadedListener;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class ProviderPatientInfoActivity extends Activity {


    View card;

    private Controller mController;
    TextView patientText;
    String toSet = "";
    Patient mPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        setContentView(R.layout.activity_provider_more_info);

        mController = Controller.getInstance(this);

        mPatient = mController.getmPatient();

        mController = Controller.getInstance(this);


        patientText = (TextView) findViewById(R.id.text13);

        patientText.setText("Loading...");

        mController.getAllPrescriptions(new OnPrescriptionLoadedListener() {

            @Override
            public void onPrescriptionLoaded(List<Prescription> prescription) {
                Set<String> currentMeds = new HashSet<String>();
                toSet += "Name: " + mPatient.getName();

                for (int i = 0; i < prescription.size(); i++) {
                    currentMeds.add(prescription.get(i).getMedication());
                }
                toSet += "\nCurrently taking:  ";
                for(String s : currentMeds){
                    toSet += s + ", ";
                }

                toSet += "\nDrug Allergies: " + mPatient.getDrug_allergies();
                mController.showProgress(false);
                patientText.setText(toSet);
            }

        });

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.more_info, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {

            return true;
        }
        // Good practice to pass through to super if not handled
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
            openOptionsMenu();
            return true;
        }
        return super.onKeyDown(keycode, event);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}