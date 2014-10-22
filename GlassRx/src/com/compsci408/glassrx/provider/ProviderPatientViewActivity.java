package com.compsci408.glassrx.provider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.compsci408.glassrx.R;
import com.compsci408.glassrx.R.id;
import com.compsci408.glassrx.R.menu;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;

public class ProviderPatientViewActivity extends Activity {

	View card;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        card = new CardBuilder(this, CardBuilder.Layout.TEXT)
        .setText("Jurrell Casey\n1.  Aricept\n2.  Vitamin E\n3.  Flintstones")
        .setTimestamp("just now")
        .getView();
		
		
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
        getMenuInflater().inflate(R.menu.med_list, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
            switch (item.getItemId()) {
                case R.id.main_menu_item:
                	startActivity(new Intent(this, ProviderMainActivity.class));
                    break;
                case R.id.item_number_menu_item:
                    startActivity(new Intent(this, ProviderMoreInfoActivity.class));
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
        	startActivity(new Intent(this, ProviderMainActivity.class));
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
