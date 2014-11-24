package com.compsci408.glassrx.glassrx2014.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.compsci408.glassrx.glassrx2014.R;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;

public class PatientMedListActivity extends Activity {

    View card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        card = new CardBuilder(this, CardBuilder.Layout.TEXT)
                .setText("1.  Aricept\n2.  Vitamin E\n3.  Flintstones")
                .setTimestamp("just now")
                .getView();


        setContentView(card);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        menu.add(Menu.NONE,10, Menu.NONE, "Aricept");
        menu.add(Menu.NONE,11,Menu.NONE, "Vitamin E");
        menu.add(Menu.NONE,12,Menu.NONE, "Flintstones");

        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.med_list, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(Menu.NONE,10, Menu.NONE, "Aricept");
//        menu.add(Menu.NONE,11,Menu.NONE, "Vitamin E");
//        menu.add(Menu.NONE,12,Menu.NONE, "Flintstones");


        getMenuInflater().inflate(R.menu.med_list, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
        Intent intent;

        switch (item.getItemId()) {
                case 10:
                    intent = new Intent(this, PatientMoreInfoActivity.class);
                    intent.putExtra("text", "Name: Aricept\nUse: Alzheimer's\nSide Effects: Nausea, Abdominal Pain");
                    intent.putExtra("pic", R.drawable.ic_aricept);
                    startActivity(intent);
                    break;

                case 11:
                    intent = new Intent(this, PatientMoreInfoActivity.class);
                    intent.putExtra("text", "Name: Vitamin E\nUse: Daily Vitamin\nSide Effects: Fatigue, Weakness");
                    intent.putExtra("pic", R.drawable.ic_vitamin_e);
                    startActivity(intent);
                    break;
                case 12:
                    intent = new Intent(this, PatientMoreInfoActivity.class);
                    intent.putExtra("text", "Name: Flintstones\nUse: Daily Vitamin\nSide Effects: Heartburn, Headache");
                    intent.putExtra("pic", R.drawable.ic_flintstones);
                    startActivity(intent);
                    break;
                case R.id.main_menu_item:
                    startService(new Intent(this, NextMedLiveCard.class));
                    break;
                default:
                    return true;
            }
            return true;
//        }
        // Good practice to pass through to super if not handled
//        return super.onMenuItemSelected(featureId, item);
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
