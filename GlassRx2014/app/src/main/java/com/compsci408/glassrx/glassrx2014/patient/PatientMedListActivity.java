package com.compsci408.glassrx.glassrx2014.patient;

import android.app.Activity;
import android.content.Context;
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

import com.compsci408.glassrx.glassrx2014.MenuActivity;
import com.compsci408.glassrx.glassrx2014.R;
import com.compsci408.glassrx.glassrx2014.rxcore.Controller;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Schedule;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnScheduleLoadedListener;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatientMedListActivity extends Activity implements SensorEventListener {

    View card;
    private SensorManager mSensorManager;
    private Sensor mOrientation;

    private Menu menu;

    ListView listView ;

    ArrayAdapter<String> mCurrentAdapter;

    private Controller mController;

    private ArrayList<String> medList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        setContentView(R.layout.activity_med_list);


        medList = new ArrayList<String>();
        mController = Controller.getInstance(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mOrientation, SensorManager.SENSOR_DELAY_GAME);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        mController.getPatientSchedule(new OnScheduleLoadedListener() {

            @Override
            public void onScheduleLoaded(List<Schedule> schedule) {
                Set<String> currentMeds = new HashSet<String>();
                String med;
                for (int i = 0; i < schedule.size(); i++) {
//                    Log.e("Schedule: ", schedule.get(i).getDay_to_take()+ " " + schedule.get(i).getTime_to_take());

                    med = schedule.get(i).getMedication();
                    currentMeds.add(med);
                    if(!medList.contains(med)) {
                        medList.add(med);
                    }
                }


                mCurrentAdapter = new ArrayAdapter<String>(
                        PatientMedListActivity.this,
                        android.R.layout.simple_list_item_1,
                        new ArrayList<String>(currentMeds));
                listView.setAdapter(mCurrentAdapter);
                mController.showProgress(false);
            }

        });


//        // Defined Array values to show in ListView
//        String[] values = new String[] { "Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };
//
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
//
//        // Assign adapter to ListView
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos,
                                    long id) {
//                mController.setMedName((String) parent.getItemAtPosition(pos));
                    mController.setMedName("Aspirin");

//                Log.d("", "item tapped: "+pos); //working !
//                Intent i = new Intent(PatientMedListActivity.this, TestActivity.class);
//                i.putExtra("i", pos);

                Intent i = new Intent(PatientMedListActivity.this, PatientMoreInfoActivity.class);
                startActivity(i);
            }

        });

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
            listView.setSelection(selection);
        }
    }


//        super.onCreate(savedInstanceState);
//        card = new CardBuilder(this, CardBuilder.Layout.TEXT)
//                .setText("1.  Aricept\n2.  Vitamin E\n3.  Flintstones")
//                .setTimestamp("just now")
//                .getView();
//
//
//        setContentView(card);


    @Override
    public boolean onPrepareOptionsMenu(Menu menu){


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onPreparePanel(int featureId,View view, Menu menu){
        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);

//        for(int key : medList.keySet()){
//            menu.add(Menu.NONE, key, Menu.NONE, medList.get(key));
////            medList.remove(key);
//        }

        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);
        invalidateOptionsMenu();

        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        menu.add(Menu.NONE, -1, 1000, "Log Out");
        while(medList==null){}
        for(int i = 0; i < medList.size(); i++){
            menu.add(Menu.NONE, i, 1, medList.get(i));
//            medList.remove(key);
        }

//        menu.add(Menu.NONE,11,Menu.NONE, "Jahlil Okafor");
//        menu.add(Menu.NONE,12,Menu.NONE, "Justise Winslow");


        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.med_list, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        getWindow().invalidatePanelMenu(WindowUtils.FEATURE_VOICE_COMMANDS);

        return super.onCreatePanelMenu(featureId, menu);
//        menu.add(Menu.NONE, 3, Menu.NONE, "HELLO");
//

//
//        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
//
//
//            getMenuInflater().inflate(R.menu.med_list, menu);
//            return true;
//        }
//        // Pass through to super to setup touch menu.
//        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {




        getMenuInflater().inflate(R.menu.med_list, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {

        if(item.getItemId() == -1){
            finish();

        }
       int i = item.getItemId();
        mController.setMedName("Aspirin");
        Intent intent = new Intent(PatientMedListActivity.this, PatientMoreInfoActivity.class);
        startActivity(intent);
            return true;
//        }
        // Good practice to pass through to super if not handled
//        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_DPAD_DOWN) {
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
