package com.compsci408.rxcore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.compsci408.rxcore.alarms.Alarm;
import com.compsci408.rxcore.datatypes.AccountType;
import com.compsci408.rxcore.datatypes.Medication;
import com.compsci408.rxcore.datatypes.Patient;
import com.compsci408.rxcore.listeners.OnAlarmAddedListener;
import com.compsci408.rxcore.listeners.OnImageCapturedListener;
import com.compsci408.rxcore.listeners.OnMedInfoLoadedListener;
import com.compsci408.rxcore.listeners.OnMedicationsLoadedListener;
import com.compsci408.rxcore.listeners.OnPatientsLoadedListener;
import com.compsci408.rxcore.requests.ResponseCallback;
import com.compsci408.rxcore.requests.ServerRequest;
import com.google.gson.Gson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

public class Controller {
	
	public static Controller instance;
	
	private Context mContext;
	private static ServerRequest mServerRequest;
	private static CameraManager mCameraManager;
	
	private String mUsername;
	private int mPatientId;
	private String mPatientName;
	private int mProviderId;
	private String mMedName;
	private int mMedId = 1;	
	
	
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

	public void setUsername(String mUsername) {
		this.mUsername = mUsername;
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
	
	/**
	 * Log user with given username and password into the system.
	 * @param username Username entered by user
	 * @param password Password entered by user
	 * @param accountType Account type selected (patient or provider)
	 * @param callback ResponseCallback from server
	 * @return Response string from server
	 */
	public void logIn(String username, String password, String accountType, ResponseCallback callback) {
		
		setUsername(username);
		mServerRequest.doGet(getLogInURL(username, accountType), callback);
	}
	
	/**
	 * Construct the appropriate log in url
	 * @param username Username entered by user
	 * @param accountType  Account type selected by user
	 * @return URL needed to log in given user
	 */
	private String getLogInURL(String username, String accountType) {
		if (accountType.equals(AccountType.PATIENT.getName())) {
			return Constants.URL_LOG_IN_PATIENT + username + "%27&app_name=glass-rx";
		}
			return Constants.URL_LOG_IN_PROVIDER + username + "%27&app_name=glass-rx";
	}
	
	/**
	 * Log user out of system
	 * @param username Username entered by user
	 * @return Response string from server
	 */
	public String logOut(String username) {
		//TODO:  Implement log out
		return "";
	}
	
	/**
	 * Get all alarms associated with given patient
	 * @param patientId Id of patient
	 * @return List of {@link Alarm}s for given patient
	 */
	public List<Alarm> getAllAlarms(int patientId) {
		//TODO:  Implement function
		return null;
	}
	
	
	/**
	 * Get alarm which will occur soonest from now
	 * @param alarms List of {@link Alarm}s
	 * @return Next alarm to occur
	 */
	public Alarm getNextAlarm(List<Alarm> alarms) {
		//TODO:  Implement function
		return null;
	}
	
	
	/**
	 * Add an alarm to the database
	 * @param alarm Alarm to be added
	 * @return Response string from server
	 */
	public void addAlarm(Alarm alarm, final OnAlarmAddedListener listener) {
		
		String json = new Gson().toJson(alarm, Alarm.class);
		
		mServerRequest.doPost(Constants.URL_ADD_ALARM, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				//TODO distinguish between successes and failures
				listener.onAlarmAdded(true);
			}
			
		}, json);
	}
	
	/**
	 * Remove alarm with the given Id from the database
	 * @param alarmId Id of {@link Alarm} to be removed
	 * @return Response string from server
	 */
	public String removeAlarm(int alarmId) {
		//TODO:  Implement function
		return "";
	}
	
	public void getPatients(final OnPatientsLoadedListener listener) {
		
		final List<Patient> patients = new ArrayList<Patient>();
		final Gson gson = new Gson();
		
		mServerRequest.doGet(getPatientsURL(), new ResponseCallback() {

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
	
	private String getPatientsURL() {
		return Constants.URL_GET_PATIENTS + Integer.toString(mProviderId) + "&app_name=glass-rx";
	}
	
	public void getMedications(final OnMedicationsLoadedListener listener) {
		
		final List<Medication> medications = new ArrayList<Medication>();
		final Gson gson = new Gson();
		
		mServerRequest.doGet(getPatientsURL(), new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("record");
					for (int i = 0; i < array.length(); i++) {
						Medication p = gson.fromJson(array.getString(i), Medication.class);
						medications.add(p);
					}
					
					listener.onMedicationsLoaded(medications);
					
				} catch (JSONException e) {
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
	public void getMedInfo(final OnMedInfoLoadedListener listener) {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Constants.MED_ID, Integer.toString(mMedId)));
		
		mServerRequest.doGet(Constants.URL_GET_MED, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				Medication med = new Gson().fromJson(response.toString(), Medication.class);
				listener.onMedInfoLoaded(med);
			}
			
		});
	}
	
	
	// -------------------------Camera functionality below-------------------------------

	
	/**
	 * Take a picture using the device's camera
	 * @param upload Boolean indicating whether
	 * the image should be uploaded.  If false,
	 * save image locally.
	 */
	public void takePicture(final boolean upload) {
		OnImageCapturedListener listener = new OnImageCapturedListener() {

			@Override
			public void onImageCaptured(byte[] data) {
				
				if (!upload) {
					saveImage(data, mMedName, mContext);
				};
			}
			
		};
		
		mCameraManager.captureImage(Camera.CameraInfo.CAMERA_FACING_BACK, listener);
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
	
	private static void uploadImage(byte[] data) {
		
		String dataString = Base64.encodeToString(data, Base64.DEFAULT);
		
		mServerRequest.doPost(Constants.URL_UPLOAD_IMAGE, new ResponseCallback() {

			@Override
			public void onResponseReceived(JSONObject response) {
				
			}
			
		}, dataString);
	}
	
	
}
