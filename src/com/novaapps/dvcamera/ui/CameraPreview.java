/**
 * Copyright 2011 Novaapps
 * Licensed under Apache 2.0
 */
package com.novaapps.dvcamera.ui;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mSurfaceHolder;
	private Camera mCamera;
	private Context mContext;

	//Constructor that obtains context and camera
	public CameraPreview(Context context, Camera camera) {
		super(context);
		this.mContext = context;
		this.mCamera = camera;
		
		this.mSurfaceHolder = this.getHolder();
		this.mSurfaceHolder.addCallback(this); // we get notified when underlying surface is created and destroyed
		this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //this is a deprecated method, is not requierd after 3.0
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
           Toast.makeText(mContext, "Error setting camera preview: " , Toast.LENGTH_LONG);
        }

	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		mCamera.stopPreview();
		mCamera.release();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
			mCamera.startPreview();
			
		} catch (Exception e) {
			// intentionally left blank for a test
		}
	}
	
}
