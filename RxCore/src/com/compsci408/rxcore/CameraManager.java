package com.compsci408.rxcore;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

	public boolean canCapture() {
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
	public void captureImage(SurfaceHolder preview, final OnImageCapturedListener listener) {
		setCamId(CameraInfo.CAMERA_FACING_BACK);
		if (canCapture()) {
			try {
				setCanCapture(false);
				Camera cam = Camera.open(mCamId);
				
				cam.setPreviewDisplay(preview);
				cam.takePicture(null, null, new PictureCallback() {
	
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {	
						listener.onImageCaptured(data);
						camera.release();
						setCanCapture(true);
					}
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

}
