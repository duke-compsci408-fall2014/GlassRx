package com.compsci408.glassrx.glassrx2014.patient;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.Menu;
import android.view.MenuItem;
import com.compsci408.glassrx.glassrx2014.R;

/**
 * A transparent {@link Activity} displaying a "Stop" options menu to remove the LiveCard.
 */
public class LiveCardMenuActivity extends Activity {

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Open the options menu right away.
        openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.next_med_live_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more_info_menu_item:
                Intent intent = new Intent(this, PatientMoreInfoActivity.class);
                intent.putExtra("text", "Name: Flintstones\n" +
                        "Use: Daily Vitamin\n" +
                        "Side Effects: Heartburn, Headache");
                intent.putExtra("pic", R.drawable.ic_flintstones);
                startActivity(intent);


                return true;
            case R.id.med_list_menu_item:
                startActivity(new Intent(this, PatientMedListActivity.class));
                return true;
            case R.id.action_stop:
                // Stop the service which will unpublish the live card.
                stopService(new Intent(this, NextMedLiveCard.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        // Nothing else to do, finish the Activity.
        finish();
    }
}
