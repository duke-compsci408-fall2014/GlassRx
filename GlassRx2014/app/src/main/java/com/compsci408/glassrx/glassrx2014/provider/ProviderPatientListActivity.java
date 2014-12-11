package com.compsci408.glassrx.glassrx2014.provider;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.compsci408.glassrx.glassrx2014.R;
//import com.compsci408.glassrx.glassrx2014.patient.PatientMainActivity;
import com.compsci408.glassrx.glassrx2014.patient.NextMedLiveCard;
import com.compsci408.glassrx.glassrx2014.patient.PatientMoreInfoActivity;
import com.compsci408.glassrx.glassrx2014.rxcore.Controller;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Patient;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnPatientsLoadedListener;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class ProviderPatientListActivity extends Activity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mOrientation;


//    PatientListAdapter mAdapter;


    ListView patientList;

    ArrayAdapter<String> mCurrentAdapter;

    private Controller mController;

    private ArrayList<Patient> patientArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        setContentView(R.layout.activity_patient_list);

        patientList = (ListView) findViewById(R.id.list);

        patientArrayList = new ArrayList<Patient>();
        mController = Controller.getInstance(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_GAME);


        mController.getPatients(new OnPatientsLoadedListener() {

            @Override
            public void onPatientsLoaded(List<Patient> patients) {
//                String s = "hi";
                ArrayList<String> s = new ArrayList<String>();
                Patient p;
                for(int i = 0; i < patients.size(); i++){
                    p = patients.get(i);
                    patientArrayList.add(p);
                    s.add(p.getName());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        ProviderPatientListActivity.this,
                        android.R.layout.simple_list_item_1,
                        s);

                patientList.setAdapter(arrayAdapter);

//                mFilter = mAdapter.getFilter();

                mController.showProgress(false);
            }

        });



        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Patient selected = patientArrayList.get(position);
//                Patient selected = (Patient) patientList.getAdapter().getItem(position);
                mController.setPatientId(selected.getPatientID());
                mController.setPatientName(selected.getName());
                mController.setmPatient(selected);

                Intent intent = new Intent(ProviderPatientListActivity.this, ProviderPatientInfoActivity.class);
                startActivity(intent);
            }
        });


        // Get ListView object from xml

    }

    @Override
    public boolean onPreparePanel(int featureId,View view, Menu menu){
        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);


        invalidateOptionsMenu();

        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        while(patientArrayList==null){}
        for(int i = 0; i < patientArrayList.size(); i++){
            menu.add(Menu.NONE, i, 1, patientArrayList.get(i).getName());
        }

        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.med_list, menu);
            return true;
        }

        // Pass through to super to setup touch menu.
        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);

        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        getMenuInflater().inflate(R.menu.med_list, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            int i = item.getItemId();
            Patient selected = patientArrayList.get(i);
            mController.setPatientId(selected.getPatientID());
            mController.setPatientName(selected.getName());
            mController.setmPatient(selected);
    //        mController.setMedName(patientArrayList.get(i).getName());
            Intent intent = new Intent(ProviderPatientListActivity.this, ProviderPatientInfoActivity.class);
            startActivity(intent);
            return true;
        }
        Log.e("something got selected", "");
        // Good practice to pass through to super if not handled
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {
    /* *** need only pitch angle (head bouncing backwards and forward)*** */
        float pitch_angle = event.values[1];

    /* *** first interval head angle degrees *** */
        int top_head_angle_limit = -110;
        int bot_head_angle_limit = -70;

    /* *** second interval *** */
        int top_list_item_limit = 0;
        int bot_list_item_limit = 10; //mCurrentAdapter.getCount();

    /* *** linear mapping/ map interval [-110,-70](head angle degrees) -> [0, 8] list items *** */
    /* *** linear mapping formula: (val - A)*(b-a)/(B-A) + a *** */
        if(pitch_angle >= top_head_angle_limit && pitch_angle <= bot_head_angle_limit){
            int selection = (int) ((pitch_angle - top_head_angle_limit)*(bot_list_item_limit - top_list_item_limit)/(bot_head_angle_limit - top_head_angle_limit) + top_list_item_limit);
//            Log.e("selection: ", String.valueOf(selection));
            patientList.setSelection(selection);
        }
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