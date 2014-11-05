package com.compsci408.androidrx;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.compsci408.androidrx.patient.MainActivity;
import com.compsci408.androidrx.provider.PatientListActivity;
import com.compsci408.rxcore.Constants;
import com.compsci408.rxcore.Controller;
import com.compsci408.rxcore.datatypes.AccountType;
import com.compsci408.rxcore.requests.ResponseCallback;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {
	
	// UI references.
	private AutoCompleteTextView mUsernameView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	private Spinner mAccountTypeView;
	private TextView mTitleView;
	private TextView mErrorView;
	
	//  Controller reference
	private Controller mController;
	
	//  Callback from server
	private ResponseCallback mCallback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		// Set up the login form.
		mUsernameView = (AutoCompleteTextView) findViewById(R.id.username);
		populateAutoComplete();

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
		mSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		Typeface typefaceAndroid = Typeface.createFromAsset(getAssets(), "fonts/Android.ttf");
		
		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
		mAccountTypeView = (Spinner) findViewById(R.id.account_type);
		
		mTitleView = (TextView) findViewById(R.id.textview_androidrx_title);
	    mTitleView.setTypeface(typefaceAndroid);
	    
	    mErrorView = (TextView) findViewById(R.id.textview_login_error);
	    
	    mController = Controller.getInstance(this);
	    
	    mCallback = new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				showProgress(false);
				JSONObject user = null;
				try {
					JSONArray array = response.getJSONArray("record");
					String userString = array.getString(0);
					user = new JSONObject(userString);
				} catch (JSONException e1) {
					// TODO Improve exception handling
					e1.printStackTrace();
				}
				try {
					if (user.getString(Constants.PASSWORD).equals(mPasswordView.getText().toString())) {
						Intent intent;
						String accountType = mAccountTypeView.getSelectedItem().toString();
						
						if (accountType.equals(AccountType.PATIENT.getName())) {
							intent = new Intent(LoginActivity.this, MainActivity.class);
						} else {
							mController.setProviderId(user.getInt("physicianID"));
							intent = new Intent(LoginActivity.this, PatientListActivity.class);
						}
						
						startActivity(intent);
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					} else {
						mErrorView.setText("Error:  " + response);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	    	
	    };
	}

	private void populateAutoComplete() {
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		// Reset errors.
		mUsernameView.setError(null);
		mPasswordView.setError(null);
		mErrorView.setText("");

		// Store values at the time of the login attempt.
		String username = mUsernameView.getText().toString();
		String password = mPasswordView.getText().toString();
		String accountType = mAccountTypeView.getSelectedItem().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid username.
		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (!isUsernameValid(username)) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			focusView = mUsernameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mController.logIn(username, password, accountType, mCallback);
		}
	}

	private boolean isUsernameValid(String username) {
		// TODO: Replace this with your own logic
		return true;
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> usernames = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			usernames.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(usernames);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mUsernameView.setAdapter(adapter);
	}
}
