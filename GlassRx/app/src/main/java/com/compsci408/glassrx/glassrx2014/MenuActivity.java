package com.compsci408.glassrx.glassrx2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.compsci408.glassrx.glassrx2014.patient.PatientMainActivity;
import com.compsci408.glassrx.glassrx2014.provider.ProviderPatientListActivity;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;


public class MenuActivity extends Activity {

    View card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        card = new CardBuilder(this, CardBuilder.Layout.COLUMNS)
                .setText("Welcome to GlassRx\nAre you a patient or provider?")
                .getView();


        setContentView(card);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.patient_provider, menu);
            return true;
        }

        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient_provider, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.patient_item:
                startActivity(new Intent(this, PatientMainActivity.class));
                break;
            case R.id.provider_item:
                startActivity(new Intent(this, ProviderPatientListActivity.class));
                break;
            default:
                return true;
        }
        return true;
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
