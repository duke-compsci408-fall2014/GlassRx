package com.compsci408.glassrx.glassrx2014.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.compsci408.glassrx.glassrx2014.R;
import com.compsci408.glassrx.glassrx2014.rxcore.Controller;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Medication;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnMedInfoLoadedListener;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;

import java.util.ArrayList;
import java.util.List;

public class PatientMoreInfoActivity extends Activity {

    View card;

    String myText = "";

    private Controller mController;
    private TextView mMedText;

    private Medication mMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        setContentView(R.layout.activity_patient_more_info);



//        int myImage = getIntent().getIntExtra("pic", 0);
        mController = Controller.getInstance(this);

        mMedText = (TextView) findViewById(R.id.text12);

//        card = new CardBuilder(this, CardBuilder.Layout.COLUMNS)
//                .setText(myText)
//                .setTimestamp("just now")
//                .addImage(myImage)
//                .getView();

        mMedText.setText("Loading...");

        mController.getMedication(new OnMedInfoLoadedListener() {

            @Override
            public void onMedInfoLoaded(Medication med) {
                if(med==null){
                    mMedText.setText("Med is null");
                    return;
                }
                mMedication = med;
                List<String> details = new ArrayList<String>();
                myText += "Medication:"
                        + "  " + mMedication.getName();
                myText += "\nPurpose:"
                        + "  " + mMedication.getPurpose();
                myText += "\nSide Effects:"
                        + "  " + mMedication.getSide_effects();

                mMedText.setText(myText);
//                details.add(PatientMoreInfoActivity.this.getResources().getString(R.string.med_name)
//                        + "  " + mMedication.getName());
//                details.add(PatientMoreInfoActivity.this.getResources().getString(R.string.med_purpose)
//                        + "  " + mMedication.getPurpose());
//                details.add(PatientMoreInfoActivity.this.getResources().getString(R.string.med_side_effects)
//                        + "  " +mMedication.getSide_effects());



//                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                        PatientMoreInfoActivity.this,
//                        android.R.layout.simple_list_item_1,
//                        details);
//                mMedDetails.setAdapter(arrayAdapter);
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
        getMenuInflater().inflate(R.menu.more_info, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.main_menu_item:
                    startService(new Intent(this, NextMedLiveCard.class));
                    break;
                case R.id.med_list_menu_item:
                    startActivity(new Intent(this, PatientMedListActivity.class));
                    break;
                default:
                    return true;
            }
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