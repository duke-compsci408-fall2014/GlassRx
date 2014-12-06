package com.compsci408.rxcore;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.datatypes.AccountType;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.datatypes.Patient;
import com.compsci408.rxcore.datatypes.Prescription;
import com.compsci408.rxcore.datatypes.Schedule;
import com.compsci408.rxcore.listeners.OnLoginAttemptedListener;
import com.compsci408.rxcore.listeners.OnMedicationsLoadedListener;
import com.compsci408.rxcore.listeners.OnPrescriptionAddedListener;
import com.compsci408.rxcore.listeners.OnPrescriptionLoadedListener;
import com.compsci408.rxcore.listeners.OnSchduleAddedListener;
import com.compsci408.rxcore.listeners.OnImageCapturedListener;
import com.compsci408.rxcore.listeners.OnMedInfoLoadedListener;
import com.compsci408.rxcore.listeners.OnPatientsLoadedListener;
import com.compsci408.rxcore.listeners.OnPictureTakenListener;
import com.compsci408.rxcore.listeners.OnScheduleLoadedListener;
import com.compsci408.rxcore.requests.ResponseCallback;
import com.compsci408.rxcore.requests.ServerRequest;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.SurfaceHolder;

/**
 * AndroidRx application controller.  Contains all intermediary
 * functionality between views and data models.
 * @author Evan
 */
public class Controller {

	public static Controller instance;
	
	private Context mContext;
	private static ServerRequest mServerRequest;
	private static CameraManager mCameraManager;
	
	private String mUsername;
	
	private int mPatientId;
	private int mProviderId;
	private int mMedId = 1;	
	
	private String mPatientName;
	private String mMedName;
	
	private int mDayOfWeek;
	private int mTimeRange;
	
	private ProgressDialog progressDialog;
	
	public static Controller getInstance(Context ctxt) {
		if (instance == null) {
			instance = new Controller();
		}
		instance.setContext(ctxt);
		mServerRequest = ServerRequest.getInstance(ctxt);
		mCameraManager = CameraManager.getInstance();
		return instance;
	}
	
	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}	
	
	public String getUsername() {
		return mUsername;
	}
	
	public void setUsername(String username) {
		this.mUsername = username;
	}

	public int getPatientId() {
		return mPatientId;
	}

	public void setPatientId(int mPatientId) {
		this.mPatientId = mPatientId;
	}
	
	public String getPatientName() {
		return mPatientName;
	}

	public void setPatientName(String patientName) {
		this.mPatientName = patientName;
	}
	
	public int getProviderId() {
		return mProviderId;
	}

	public void setProviderId(int mProviderId) {
		this.mProviderId = mProviderId;
	}

	public String getMedName() {
		return mMedName;
	}
	
	public void setMedName(String mMedName) {
		this.mMedName = mMedName;
	}

	public int getMedId() {
		return mMedId;
	}

	public void setMedId(int mMedId) {
		this.mMedId = mMedId;
	}
	
	
	public int getDayOfWeek() {
		return mDayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.mDayOfWeek = dayOfWeek;
	}

	public int getTimeRange() {
		return mTimeRange;
	}

	public void setTimeRange(int timeRange) {
		this.mTimeRange = timeRange;
	}

	public void showProgress(boolean show) {
		if (show) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.show();
		}
		
		else if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	public int getDayFromDate(String dateString) {
		try {
			Date date = new SimpleDateFormat(Constants.DATE_FORMAT_DATABASE, Locale.US)
								.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Show/hide a {@link ProgressDialog}
	 * @param message  Message to be shown
	 * @param show True if should be shown, false otherwise
	 */
	public void showProgress(String message, boolean show) {
		if (show && progressDialog == null) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setMessage(message);
			progressDialog.show();
		}
		
		else if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	
	/**
	 * Check to see if device is already logged in
	 * @return Integer value corresponding to AccountType
	 * from last login, or -1 if not logged in
	 */
	public int checkLogin() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		int login = pref.getInt(Constants.KEY_LOGIN, Constants.DEFAULT_VALUE);
		
		//  We are not logged in
		if (login == Constants.DEFAULT_VALUE) {
			return Constants.DEFAULT_VALUE;
		}
		
		//  If we were logged in, set appropriate user information
		if (login == Constants.LOGGED_IN) {
			if (pref.getInt(Constants.KEY_ACCOUNT_TYPE, Constants.DEFAULT_VALUE)
					== AccountType.PATIENT.getId()) {
				setPatientId(pref.getInt(Constants.KEY_LAST_USER, Constants.DEFAULT_VALUE));
			}
			else if (pref.getInt(Constants.KEY_ACCOUNT_TYPE, Constants.DEFAULT_VALUE)
					== AccountType.PROVIDER.getId()) {
				setProviderId(pref.getInt(Constants.KEY_LAST_USER, Constants.DEFAULT_VALUE));
			}
		}
		
		return pref.getInt(Constants.ACCOUNT_TYPE, 
				pref.getInt(Constants.KEY_ACCOUNT_TYPE, Constants.DEFAULT_VALUE));
	}
	
	/**
	 * Log user with given username and password into the system.
	 * @param username Username entered by user
	 * @param password Password entered by user
	 * @param accountType Account type selected (patient or provider)
	 * @param listener Listener indicating appropriate UI updates after
	 * web request completes
	 */
	public void logIn(String username, final String password, final String accountType, 
			final OnLoginAttemptedListener listener) {
		
		String url;
		
		if (accountType.toLowerCase(Locale.US)
				.contains(AccountType.PATIENT.getName().toLowerCase(Locale.US))) {
			url = Constants.URL_LOG_IN_PATIENT + username + "%27" + Constants.URL_SUFFIX;
		}
		else {
			url = Constants.URL_LOG_IN_PROVIDER + username + "%27" + Constants.URL_SUFFIX;
		}
		
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				JSONObject user = null;
				try {
					JSONArray array = response.getJSONArray("record");
					if (array.isNull(0)) {
						listener.onLoginFailed("Error:  Invalid credentials");
						return;
					}
					String userString = array.getString(0);
					user = new JSONObject(userString);
					if (user.getString(Constants.PASSWORD).equals(password)) {
						SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
						Editor editor = pref.edit();
						
						Calendar c = Calendar.getInstance(); 
						long seconds = c.getTimeInMillis();
						
						editor.putInt(Constants.KEY_LOGIN, Constants.LOGGED_IN);
						editor.putLong(Constants.KEY_LAST_ACTIVITY, seconds);
						
						//  Log in as patient and store appropriate login history
						if (accountType.contains(AccountType.PATIENT.getName().toLowerCase(Locale.US))) {
							setPatientId(user.getInt("patientID"));
							editor.putInt(Constants.KEY_ACCOUNT_TYPE, AccountType.PATIENT.getId());
							editor.putInt(Constants.KEY_LAST_USER, getPatientId());
							listener.onLoginSuccess(AccountType.PATIENT.getId());
						} 
						//  Log in as provider and store appropriate login history
						else {
							setProviderId(user.getInt("physicianID"));
							editor.putInt(Constants.KEY_ACCOUNT_TYPE, AccountType.PROVIDER.getId());
							editor.putInt(Constants.KEY_LAST_USER, getProviderId());
							listener.onLoginSuccess(AccountType.PROVIDER.getId());
						}
						editor.commit();
					} else {
						//TODO:  add more robust error checking
						listener.onLoginFailed("An error occurred.\nPlease try again.");
					}
				} catch (JSONException e1) {
					// TODO Improve exception handling
					listener.onLoginFailed("An error occurred.\nPlease try again.");
					e1.printStackTrace();
				}
			}
	    	
	    });
				
	}
	
	/**
	 * Log user out of system
	 * @param username Username entered by user
	 */
	public void logOut() {
		//TODO:  Implement log out
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		Editor editor = pref.edit();
		
		Calendar c = Calendar.getInstance(); 
		long seconds = c.getTimeInMillis();
		
		editor.putInt(Constants.KEY_LOGIN, Constants.DEFAULT_VALUE);
		editor.putLong(Constants.KEY_LAST_ACTIVITY, seconds);
		
		editor.commit();
	}
	
//	/**
//	 * Get all alarms associated with given patient
//	 * @param patientId Id of patient
//	 * @return List of {@link Alarm}s for given patient
//	 */
//	public List<Alarm> getAllAlarms(int patientId) {
//		//TODO:  Implement function
//		return null;
//	}
//	
//	
//	/**
//	 * Get alarm which will occur soonest from now
//	 * @param alarms List of {@link Alarm}s
//	 * @return Next alarm to occur
//	 */
//	public Alarm getNextAlarm(List<Alarm> alarms) {
//		//TODO:  Implement function
//		return null;
//	}
	
	
	/**
	 * Add new {@link Prescription}s to the database
	 * @param prescription Prescription to be added
	 * @param listener  Listener describing UI updates after
	 * request is executed
	 */
	public void addPrescriptions(List<Prescription> prescriptions, final OnPrescriptionAddedListener listener) {
		
		JSONObject json = new JSONObject();
		JSONArray record = new JSONArray();
		for (int i = 0; i < prescriptions.size(); i++) {
			try {
				JSONObject prescriptionObject = new JSONObject(new Gson().toJson(prescriptions.get(i), Prescription.class));
				record.put(prescriptionObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			json.put("record", record);
			String jsonString = json.toString();
			mServerRequest.doPost(Constants.URL_ADD_PRESCRIPTION, new ResponseCallback() {
	
				@Override
				public void onResponseReceived(JSONObject response) {
					//TODO distinguish between successes and failures
					listener.onPrescriptionAdded(true);
				}
				
			}, jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Add new {@link Schedule}s to the database
	 * @param schedules Schedules to be added
	 * @param listener  Listener describing UI updates after
	 * request is executed
	 */
	public void addSchedules(List<Schedule> schedules, final OnSchduleAddedListener listener) {
		
		JSONObject json = new JSONObject();
		JSONArray record = new JSONArray();
		for (int i = 0; i < schedules.size(); i++) {
			try {
				JSONObject scheduleObject = new JSONObject(new Gson().toJson(schedules.get(i), Schedule.class));
				record.put(scheduleObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
			json.put("record", record);
			String jsonString = json.toString();
			mServerRequest.doPost(Constants.URL_ADD_SCHEDULE, new ResponseCallback() {

				@Override
				public void onResponseReceived(JSONObject response) {
					//TODO distinguish between successes and failures
					listener.onScheduleAdded(true);
				}
				
			}, jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Get all of the patients associated with the currently logged in provider.
	 * @param listener Listener describing UI updates after request is executed
	 */
	public void getPatientsForProvider(final OnPatientsLoadedListener listener) {
		
		final List<Patient> patients = new ArrayList<Patient>();
		final Gson gson = new Gson();
		
		String url = Constants.URL_GET_PATIENTS + Integer.toString(mProviderId) 
				+ Constants.URL_SUFFIX;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("record");
					for (int i = 0; i < array.length(); i++) {
						Patient p = gson.fromJson(array.getString(i), Patient.class);
						patients.add(p);
					}
					
					listener.onPatientsLoaded(patients);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
	/**
	 * Get all the schedules associated with the current patient
	 * @param listener Listener describing UI updates after
	 * request is executed
	 */
	public void getPatientSchedule(final OnScheduleLoadedListener listener) {
		
		final List<Schedule> schedule = new ArrayList<Schedule>();
		final Gson gson = new Gson();
		
		String url = Constants.URL_GET_PATIENT_SCHEDULE + Integer.toString(mPatientId) 
				+ "%27&app_name=glass-rx";
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("record");
					for (int i = 0; i < array.length(); i++) {
						Schedule s = gson.fromJson(array.getString(i), Schedule.class);
						schedule.add(s);
					}
					
					listener.onScheduleLoaded(schedule);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}

	
	
	/**
	 * Filter all of the medications by an input string.  Used for
	 * autocomplete functionality.
	 * @param input  Text entered by user
	 * @param listener Listener describing UI updates after
	 * request is executed
	 */
	public void filterMedicationsByName(String input, final OnMedicationsLoadedListener listener) {
		String url = Constants.URL_FILTER_MEDS_BY_NAME + input + "%25%27" + Constants.URL_SUFFIX;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				JSONArray array = null;
				try {
					array = response.getJSONArray("record");
					if (array.isNull(0)) {
						return;
					}
					ArrayList<Medication> meds = new ArrayList<Medication>();
					for (int i = 0; i < array.length(); i++) {
						meds.add(new Gson().fromJson(array.getString(i), Medication.class));
					}
					listener.onMedicationsLoaded(meds);
				} catch (JSONException e) {
					// TODO Improve exception handling
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
	/**
	 * Get general information for the selected medication
	 * and update the UI as indicated by the provided listener.
	 * @param listener Listener which describes UI updates upon
	 * successful web request.
	 */
	public void getMedicationByName(final OnMedInfoLoadedListener listener) {
		
		 String url = Constants.URL_GET_MED_BY_NAME + mMedName 
			+ "%27" + Constants.URL_SUFFIX;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				JSONArray array;
				try {
					array = response.getJSONArray("record");
					Medication med = new Gson().fromJson(array.getString(0), Medication.class);
					listener.onMedInfoLoaded(med);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
	}
	
//	public void getAllPrescriptions(final OnPrescriptionLoadedListener listener) {
//		String url = Constants.URL_GET_PATIENT_PRESCRIP + Integer.toString(mPatientId) 
//				+ "%27" + Constants.URL_SUFFIX ;
//		
//		mServerRequest.doGet(url, new ResponseCallback() {
//
//			@Override
//			public void onResponseReceived(JSONObject response) {
//				try {
//					JSONArray array = response.getJSONArray("record");
//					List<Prescription> prescription = new ArrayList<Prescription>();
//					for (int i = 0; i < array.length(); i++) {
//						Prescription p = new Gson().fromJson(array.getString(i), Prescription.class);
//						prescription.add(p);
//					}
//					listener.onPrescriptionLoaded(prescription);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				
//				
//			}
//			
//		});
//	}
	
	/**
	 * Get all of the prescriptions associated with the current patient.
	 * @param listener Listener describing UI updates after request is executed
	 */
	public void getPrescriptionsForPatient(final OnPrescriptionLoadedListener listener) {
		
		String url = Constants.URL_GET_PATIENT_PRESCRIP + Integer.toString(mPatientId) 
				+ "%27" + Constants.URL_SUFFIX ;
		
		mServerRequest.doGet(url, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("record");
					List<Prescription> prescription = new ArrayList<Prescription>();
					for (int i = 0; i < array.length(); i++) {
						Prescription p = new Gson().fromJson(array.getString(i), Prescription.class);
						if (!p.getSet()) {
							prescription.add(p);
						}
					}
					listener.onPrescriptionLoaded(prescription);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				
			}
			
		});
	}
	
	
	// -------------------------Camera functionality below-------------------------------

	
	
	/**
	 * Start the device's camera, if it exists.
	 * @param preview  {@link SurfaceHolder} on
	 * which the camera's preview is to be rendered.
	 */
	public void startCamera(SurfaceHolder preview) {
		mCameraManager.startCamera(preview);
	}
	
	/**
	 * Take a picture using the device's camera
	 * @param upload Boolean indicating whether
	 * the image should be uploaded.  If false,
	 * save image locally.
	 */
	public void takePicture(SurfaceHolder preview, final boolean upload, final OnPictureTakenListener callback) {
		
		
		OnImageCapturedListener listener = new OnImageCapturedListener() {

			@Override
			public void onImageCaptured(byte[] data) {
				boolean success = false;
				if (!upload) {
					success = saveImage(data, mMedName, mContext);
				};
				
				//TODO  Implement image upload
				
				callback.onPictureTaken(success);
			}
			
		};
		
		mCameraManager.captureImage(preview, listener);
	}
	
	/**
	 * Save an image locally using the given file name
	 * and create a thumbnail for it
	 * @param data Byte array of image data
	 * @param name Name of file to be created
	 * @return Boolean indicating whether image was
	 * successfully saved
	 */
	private static boolean saveImage(byte[] data, String name, Context context) {
		File filename;
		Bitmap bitmap;
		boolean success = false;
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			
			if (!new File(path + Constants.RX_DIRECTORY).isDirectory()) {
				new File(path + Constants.RX_DIRECTORY).mkdirs();
			}
			
			filename = new File (path + Constants.RX_DIRECTORY + name.toLowerCase(Locale.US));
			
			FileOutputStream out = new FileOutputStream(filename);
			
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			
			out.flush();
			out.close();
			
			MediaStore.Images.Media.insertImage(context.getContentResolver(), 
					filename.getAbsolutePath(), filename.getName(), filename.getName());
			success = true;
		} catch (Exception e) {
			//TODO Improve exception handling
			e.printStackTrace();
		}
		return success;
		
	}
	
	/**
	 * Load an image from the device's memory
	 * @param name Name of the file to be loaded
	 * (in this case, it is the name of the associated medication)
	 * @param context {@link Context} of the application
	 * @return {@link Bitmap} representation of the
	 * image to be loaded.
	 */
	public static Bitmap loadImage(String name, Context context) {
		String path = Environment.getExternalStorageDirectory().toString();
		File file = new File (path + Constants.RX_DIRECTORY + name.toLowerCase(Locale.US));
		if(file.exists()){
		    return BitmapFactory.decodeFile(file.getAbsolutePath());
		}
		return BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_menu_camera);
	}
	
	private static void uploadImage(byte[] data) {
		
		String dataString = Base64.encodeToString(data, Base64.DEFAULT);
		
		mServerRequest.doPost(Constants.URL_UPLOAD_IMAGE, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				
			}
			
		}, dataString);
	}
	
	
}
