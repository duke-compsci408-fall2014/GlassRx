package com.compsci408.glassrx.glassrx2014;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.compsci408.glassrx.glassrx2014.patient.NextMedLiveCard;
import com.compsci408.glassrx.glassrx2014.patient.PatientMedListActivity;
import com.compsci408.glassrx.glassrx2014.provider.ProviderPatientListActivity;
import com.compsci408.glassrx.glassrx2014.rxcore.Controller;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.AccountType;
import com.compsci408.glassrx.glassrx2014.rxcore.datatypes.Prescription;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnLoginAttemptedListener;
import com.compsci408.glassrx.glassrx2014.rxcore.listeners.OnPrescriptionLoadedListener;

import java.util.List;


public class MenuActivity extends Activity {

    View card;

    private Controller mController;

    private OnLoginAttemptedListener mListener;

    private Context mContext;

    String mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        card = new CardBuilder(this, CardBuilder.Layout.TEXT)
                .setText("Welcome to GlassRx!\nAre you a patient or a provider?")
                .getView();


        mController = Controller.getInstance(this);

        mListener = new OnLoginAttemptedListener() {

            @Override
            public void onLoginSuccess(int accountType) {
                if (accountType == AccountType.PATIENT.getId()) {
                    mController.getPendingPrescriptionsForPatient(new OnPrescriptionLoadedListener() {

                        @Override
                        public void onPrescriptionLoaded(
                                List<Prescription> prescription) {
//                            startService(new Intent(MenuActivity.this, NextMedLiveCard.class));
                            startActivity(new Intent(MenuActivity.this,
                                   PatientMedListActivity.class));
                        }

                    });

                } else {
                    startActivity(new Intent(MenuActivity.this,
                            ProviderPatientListActivity.class));
                }
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            @Override
            public void onLoginFailed(String response) {
//                showProgress(false);
                card = new CardBuilder(mContext, CardBuilder.Layout.TEXT)
                        .setText("Login failed! \n" + response)
                        .getView();
                setContentView(card);
            }
        };



        setContentView(card);
    }


    public void attemptLogin() {
        // Reset errors.
//        mUsernameView.setError(null);
//        mPasswordView.setError(null);
//        mErrorView.setText("");
        String accountType = mAccountType;


        // Store values at the time of the login attempt.
        String username = accountType.equals("Patient") ? "vw22" : "rs123";
        String password = accountType.equals("Patient") ? "vincentwang" : "ryanshaw";

//        boolean cancel = false;
//        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid username.
//        if (TextUtils.isEmpty(username)) {
//            mUsernameView.setError(getString(R.string.error_field_required));
//            focusView = mUsernameView;
//            cancel = true;
//        } else if (!isUsernameValid(username)) {
//            mUsernameView.setError(getString(R.string.error_invalid_username));
//            focusView = mUsernameView;
//            cancel = true;
//        }

//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            mController.logIn(username, password, accountType, mListener);

//        }
    }

    private boolean isUsernameValid(String username) {
        // TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        // TODO: Replace this with your own logic
        return password.length() > 4;
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
                mAccountType = "Patient";
                card = new CardBuilder(mContext, CardBuilder.Layout.TEXT)
                        .setText("Loading...")
                        .getView();
                setContentView(card);
                attemptLogin();

//                startService(new Intent(this, NextMedLiveCard.class));
                break;
            case R.id.provider_item:

                mAccountType = "Provider";

                card = new CardBuilder(mContext, CardBuilder.Layout.TEXT)
                        .setText("Loading... \n")
                        .getView();
                setContentView(card);
                attemptLogin();

//                startActivity(new Intent(this, ProviderPatientListActivity.class));
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
