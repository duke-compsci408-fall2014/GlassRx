package com.compsci408.glassrx.glassrx2014.provider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.compsci408.glassrx.glassrx2014.R;
//import com.compsci408.glassrx.glassrx2014.patient.PatientMainActivity;
import com.compsci408.glassrx.glassrx2014.patient.NextMedLiveCard;
import com.compsci408.glassrx.glassrx2014.patient.PatientMoreInfoActivity;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class ProviderPatientListActivity extends Activity {


    View card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        card = new CardBuilder(this, CardBuilder.Layout.TEXT)
                .setText("1.  Tyus Jones\n2.  Jahlil Okafor\n3.  Justise Winslow")
                .setTimestamp("just now")
                .getView();


        setContentView(card);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        menu.add(Menu.NONE, 10, Menu.NONE, "Tyus Jones");
        menu.add(Menu.NONE,11,Menu.NONE, "Jahlil Okafor");
        menu.add(Menu.NONE,12,Menu.NONE, "Justise Winslow");

        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            getMenuInflater().inflate(R.menu.med_list, menu);
            return true;
        }
        // Pass through to super to setup touch menu.
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(Menu.NONE, 10, Menu.NONE, "Tyus Jones");
//        menu.add(Menu.NONE,11,Menu.NONE, "Jahlil Okafor");
//        menu.add(Menu.NONE,12,Menu.NONE, "Justise Winslow");


        getMenuInflater().inflate(R.menu.med_list, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
        Intent intent;

        switch (item.getItemId()) {
            case 10:
                intent = new Intent(this, ProviderPatientInfoActivity.class);
                intent.putExtra("text", "Name: Tyus Jones\nDOB: 11/12/95\nMedications: Advil, Tylenol");
                intent.putExtra("pic", R.drawable.ic_tyus2);
                startActivity(intent);
                break;

            case 11:
                intent = new Intent(this, ProviderPatientInfoActivity.class);
                intent.putExtra("text", "Name: Jahlil Okafor\nDOB: 5/11/95\nMedications: Albuterol");
                intent.putExtra("pic", R.drawable.ic_jahlil);
                startActivity(intent);
                break;
            case 12:
                intent = new Intent(this, ProviderPatientInfoActivity.class);
                intent.putExtra("text", "Name: Justise Winslow\nDOB: 4/30/95\nMedications: Aricept, Flintstones");
                intent.putExtra("pic", R.drawable.ic_justise);
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