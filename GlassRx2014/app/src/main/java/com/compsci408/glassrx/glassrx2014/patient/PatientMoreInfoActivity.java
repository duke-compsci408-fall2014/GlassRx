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

public class PatientMoreInfoActivity extends Activity {

    View card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String myText = getIntent().getStringExtra("text");
        int myImage = getIntent().getIntExtra("pic", 0);
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        card = new CardBuilder(this, CardBuilder.Layout.COLUMNS)
                .setText(myText)
                .setTimestamp("just now")
                .addImage(myImage)
                .getView();
        // Display the card we just created
        setContentView(card);
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