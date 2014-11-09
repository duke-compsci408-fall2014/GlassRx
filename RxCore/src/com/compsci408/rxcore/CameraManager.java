package com.compsci408.rxcore;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;

import com.compsci408.rxcore.listeners.OnImageCapturedListener;

/**
 * Class for handling image capture
 * @author Evan
 */
public class CameraManager {
	
	private static CameraManager instance;
	
	private int mCamId;
	private boolean mCanCapture;
	
	public static CameraManager getInstance() {
		if (instance == null) {
			instance = new CameraManager();
		}
		return instance;
	}

	public int getCamId() {
		return mCamId;
	}

	public void setCamId(int mCamId) {
		this.mCamId = mCamId;
	}

	public boolean isCanCapture() {
		return mCanCapture && Camera.getNumberOfCameras() > 0;
	}

	public void setCanCapture(boolean mCanCapture) {
		this.mCanCapture = mCanCapture;
	}	
	
	
	/**
	 * Capture an image using the device's camera
	 * @param id Id of camera to be used
	 * @param listener Callback describing what should be
	 * done with the image data
	 */
	public void captureImage(int id, final OnImageCapturedListener listener) {
		setCamId(id);
		if (isCanCapture()) {
			setCanCapture(false);
			Camera cam = Camera.open(mCamId);
			cam.takePicture(null, null, new PictureCallback() {

				@Override
				public void onPictureTaken(byte[] data, Camera camera) {	
					listener.onImageCaptured(data);
					camera.release();
					setCanCapture(true);
				}
			});
		}
	}
	

}
