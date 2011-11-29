/*
 * Copyright (c) 2011 James Johnson
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the 
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 */

package org.robobrain.sdk;

import org.robobrain.sdk.game.Engine;
import org.robobrain.sdk.graphics.Color;
import org.robobrain.sdk.graphics.TextureManager;
import org.robobrain.sdk.input.Accelerometer;
import org.robobrain.sdk.input.Multitouch;
import org.robobrain.sdk.input.Keyboard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * An implementation of SurfaceView that uses the dedicated surface for displaying 
 * OpenGL rendering. It also captures input and routes it to the game's Engine.
 * @author James Johnson
 */
public class GLView extends GLSurfaceView implements SensorEventListener {
	private Context mContext;
	private GLRenderer mRenderer;
	
	/**
	 * Initializes the GLView and sets up input and the rendering surface.
	 * @param context A valid application Context. Usually from the hosting Activity.
	 */
	public GLView(Context context){
        super(context);
        init(context);
    }
	
	/**
	 * Initializes the GLView and sets up input and the rendering surface. 
	 * This constructor allows GLView to be used in a XML layout.
	 * @param context 
	 * A valid application Context. Usually from the hosting Activity.
	 * @param attrs
	 * A valid AttributeSet. Usually from the hosting Activity. 
	 */
	public GLView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	/**
     * Called as part of the activity lifecycle when the parent activity is going into the 
     * background, but has not (yet) been killed. 
     */
	@Override 
    public void onPause() {
    	super.onPause();
    	queueEvent(new Runnable() {
			 public void run() {
				 mRenderer.pause();
			 }
		 });
    }
	
	/**
	 * Called when the parent activity resumes after being in the background.
	 */
	@Override 
	public void onResume() {
		super.onResume();
		queueEvent(new Runnable() {
			 public void run() {
				 mRenderer.resume();
			 }
		 });
	}
	
	/**
	 * Called when the user or a pointer touches the screen.
	 */
	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
		int pointerId = event.getPointerId(pointerIndex);
		
		switch (action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				Multitouch.setState(pointerId, Multitouch.POINTER_DOWN);
				Multitouch.setX(pointerId, event.getX(pointerIndex));
				Multitouch.setY(pointerId, event.getY(pointerIndex));
				break;
				
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				Multitouch.setState(pointerId, Multitouch.POINTER_UP);
				Multitouch.setX(pointerId, event.getX(pointerIndex));
				Multitouch.setY(pointerId, event.getY(pointerIndex));
				break;
				
			case MotionEvent.ACTION_MOVE:
				int pointerCount = event.getPointerCount();
				for (int i = 0; i < pointerCount; i++) {
					pointerIndex = i;
					pointerId = event.getPointerId(pointerIndex);
					Multitouch.setState(pointerId, Multitouch.POINTER_MOVE);
					Multitouch.setX(pointerId, event.getX(pointerIndex));
					Multitouch.setY(pointerId, event.getY(pointerIndex));
				}
				break;
		}
		return true;
	}
	
	/**
	 * Ingored
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Ignore this
	}
	
	/**
	 * Called when the user tilts the phone.
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		Accelerometer.x = event.values[0];
		Accelerometer.y = event.values[1];
		Accelerometer.z = event.values[2];
	}
	
	/**
	 * Called when the user presses a key on a physical or virtual keyboard.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Keyboard.key = keyCode;
		Keyboard.state = Keyboard.KEY_DOWN;
		Log.d("Key", "down: " + keyCode);
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * Called when the user releases a key on a physical or virtual keyboard.
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Keyboard.key = keyCode;
		Keyboard.state = Keyboard.KEY_UP;
		Log.d("Key", "up: " + keyCode);
		return super.onKeyUp(keyCode, event);
	}
	
	/**
	 * Sets the background color of the drawing surface.
	 * @param color you wish to set the background to.
	 */
	public void setBackgroundColor(final Color color) {
		 queueEvent(new Runnable() {
			 public void run() {
				 mRenderer.setClearColor(color);
			 }
		 });
	}
	
	/**
	 * Registers your game's Engine with this renderer.
	 * @param game Your game's Engine object.
	 */
	public void registerGame(final Engine game) {
		queueEvent(new Runnable() {
			 public void run() {
				 mRenderer.registerGame(game);
			 }
		 });
	}
	
	/**
     * Sets the ideal width and height for your game. This will be used to 
     * calculate the ratio to scale your scene assets by in order to fit the 
     * player's phone screen.
     * @param width in pixels of the screen.
     * @param height in pixels of the screen.
     */
	public void setTargetSize(final int width, final int height) {
		queueEvent(new Runnable() {
			 public void run() {
				 mRenderer.setTargetSize(width, height);
			 }
		 });
	}
	
	// The internal initializer
	protected void init(Context context) {
		mContext = context;
	    mRenderer = new GLRenderer();
	    setRenderer(mRenderer);
	        
		setFocusable(true);
	    setFocusableInTouchMode(true);
	    requestFocus();
	        
	    SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
	    if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() < 1) {
			Log.d("Input", "No accelerometer present.");
		}
		Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
		}
	}
}

