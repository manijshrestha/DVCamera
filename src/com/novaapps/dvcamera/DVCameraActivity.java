/**
 * Copyright 2011 Novaapps
 * Licensed under Apache 2.0
 */
package com.novaapps.dvcamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.novaapps.dvcamera.R.id;
import com.novaapps.dvcamera.ui.CameraPreview;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class DVCameraActivity extends Activity {
	
	protected static final int MEDIA_TYPE_IMAGE = 1;
	
	private Camera mCamera;
	private CameraPreview mCameraPreview;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        
        FrameLayout preview = (FrameLayout) findViewById(id.camera_preview);
        preview.addView(mCameraPreview);
        
        //Adding listener
        Button captureButton = (Button) findViewById(id.button_capture);
        captureButton.setOnClickListener(
        		new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						mCamera.takePicture(null, null, mPicture);
						
					}
				});
        
    }

    /**
     * Helper method to access the camera returns null if
     * it cannot get the camera or does not exist
     * @return
     */
	private Camera getCameraInstance() {
		Camera camera = null;

		try {
			camera = Camera.open();
		} catch (Exception e) {
			// cannot get camera or does not exist
		}
		return camera;
	}
	
	
	/** Create a File for saving the image */
	private static File getOutputMediaFile(){

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");

	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");

	    return mediaFile;
	}
	
	/***  Listners for Picture Callback***/
	PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			 File pictureFile = getOutputMediaFile();
		        if (pictureFile == null){
		            return;
		        }

		        try {
		            FileOutputStream fos = new FileOutputStream(pictureFile);
		            fos.write(data);
		            fos.close();
		        } catch (FileNotFoundException e) {

		        } catch (IOException e) {
		        
		        }
			
		}
		
	};
}