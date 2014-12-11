package com.compsci408.rxcore.listeners;

public abstract class OnImageCapturedListener {
	
	/**
	 * Callback function for describing behavior
	 * upon completed image capture.
	 * @param data byte array representation of image captured
	 */
	public abstract void onImageCaptured(byte[] data);

}
