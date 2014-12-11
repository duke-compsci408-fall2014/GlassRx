package com.compsci408.rxcore;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import com.compsci408.rxcore.listeners.OnImageCapturedListener;

/**
 * Class for handling image capture
 * @author Evan
 */
public class CameraManager {
	
	private static CameraManager instance;
	
	private int mCamId;
	private Camera mCam;
	private boolean mCanCapture = false;
	
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
	 * Start the backwards-facing camera and display
	 * its preview on the given surface
	 * @param preview {@link SurfaceHolder} on which
	 * to display the camera's preview
	 */
	public void startCamera(SurfaceHolder preview) {
		if (!canCapture()) {
			try {
				setCamId(CameraInfo.CAMERA_FACING_BACK);
				setCanCapture(true);
				mCam = Camera.open(mCamId);
				mCam.setPreviewDisplay(preview);
				mCam.setDisplayOrientation(90);
				mCam.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Capture an image using the device's camera
	 * @param preview {@link SurfaceHolder} on which to display preview
	 * @param listener Callback describing what should be
	 * done with the image data
	 */
	public void captureImage(SurfaceHolder preview, final OnImageCapturedListener listener) {
		if (mCam == null) {
			startCamera(preview);
		}
		setCanCapture(false);
		mCam.takePicture(null, null, new PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {	
				listener.onImageCaptured(data);
				camera.stopPreview();
				camera.release();
				mCam = null;
			}
		});
	}
	

}
