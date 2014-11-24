//package com.compsci408.glassrx.glassrx2014.patient;
//
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//
//import com.compsci408.glassrx.glassrx2014.R;
//import com.google.android.glass.view.WindowUtils;
//import com.google.android.glass.widget.CardBuilder;
//
///**
// * An {@link Activity} showing a tuggable "Hello World!" card.
// * <p>
// * The main content view is composed of a one-card {CardScrollView} that provides tugging
// * feedback to the user when swipe gestures are detected.
// * If your Glassware intends to intercept swipe gestures, you should set the content view directly
// * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
// * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
// */
//public class PatientMainActivity extends Activity {
//
//    View card;
//
//    boolean hasImage = false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
//
//        if(hasImage){
//            card = new CardBuilder(this, CardBuilder.Layout.COLUMNS)
//                    .setText("Time:  2:00PM\n Med.:  Aricept\nDose:  2 Pills")
//                    .setTimestamp("just now")
//                    .addImage(0)
//                    .getView();
//            setContentView(card);}
//
//        else{
//            card = new CardBuilder(this, CardBuilder.Layout.TEXT)
//                    .setText("Next Medication:\nTime:  2:00PM\nMed.:  Aricept\nDose:  2 Pills")
//                    .setTimestamp("just now")
//                    .getView();
//            setContentView(card);
//        }
//    }
//
//    @Override
//    public boolean onCreatePanelMenu(int featureId, Menu menu) {
//        if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS) {
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }
//
//        return super.onCreatePanelMenu(featureId, menu);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.more_info_menu_item:
//                    startActivity(new Intent(this, PatientMoreInfoActivity.class));
//                    break;
//                case R.id.med_list_menu_item:
//                    startActivity(new Intent(this, PatientMedListActivity.class));
//                    break;
//                default:
//                    return true;
//            }
//            return true;
//    }
//
//    @Override
//    public boolean onKeyDown(int keycode, KeyEvent event) {
//        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
//            openOptionsMenu();
//            return true;
//        }
//
//        return super.onKeyDown(keycode, event);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//}
