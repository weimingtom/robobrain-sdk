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
import org.robobrain.sdk.input.Multitouch;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * A GameActivity creates a full screen OpenGL window and provides methods for 
 * registering and your game's Engine object with the Renderer. This class is not 
 * meant to be instantiated. Derive your game's Activity from this class.
 * @author James Johnson
 */
public class GameActivity extends Activity {
	/**
	 * The game's Engine. Will be set to your Game class.
	 */
	protected Engine mGame;
	
	/**
	 * The GameActivity's Context.
	 */
	protected static Context sContext;
	
	/**
	 * A View that manages the OpenGL Renderer.
	 */
	protected GLView mGLView;
	
	/**
	 * Called when the activity is starting. This is where most initialization 
	 * should go.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create a fullscreen window
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        					 
        
        // Init sound system
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        sContext = this;
        
        Multitouch.clear();
    }
    
    /**
     * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your 
     * activity to start interacting with the user.
     */
    @Override 
    public void onResume() {
    	super.onResume();
    	if (mGame != null) {
    		mGame.play();
    	}
    	if (mGLView != null) {
    		mGLView.onResume();
    	}
    	
	}
    
    /**
     * Called as part of the activity lifecycle when an activity is going into the 
     * background, but has not (yet) been killed. 
     */
    @Override 
    public void onPause() {
    	super.onPause();
    	TextureManager.unloadAll();
    	if (mGame != null) {
    		mGame.pause();
    	}
    	if (mGLView != null) {
    		mGLView.onPause();
    	}
    }
    
    /**
     * Perform any final cleanup before an activity is destroyed. This can happen either because 
     * the activity is finishing (someone called finish() on it, or because the system is 
     * temporarily destroying this instance of the activity to save space. You can distinguish 
     * between these two scenarios with the isFinishing() method. 
     */
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	TextureManager.unloadAll();
    	if (mGame != null) {
    		mGame.shutdown();
    	}
    	mGame = null;
    	sContext = null;
    	mGLView  = null;
    }
    
    /**
     * Sets the background color of the Activity
     */
    public void setBackgroundColor(Color color) {
    	mGLView.setBackgroundColor(color);
    }
    
    /**
     * Registers a game Engine class to be used by this Activity to run a game.
     */
    public void registerGame(Engine game) {
    	mGLView.registerGame(game);
    }
    
    /**
     * Initializes the OpenGL renderer. Call this in your derived Activity's onCreate() method.
     * @param width
     * Width in pixels of the desired screen.
     * @param height
     * Height in pixels of the desired screen.
     */
    public void initRenderer(int width, int height) {
    	// Initialize the Renderer
        mGLView = new GLView(this);
        setContentView(mGLView);
        setTargetSize(width, height);
    }
    
    /**
     * Sets the ideal width and height for your game. This will be used to 
     * calculate the ratio to scale your scene assets by in order to fit the 
     * player's phone screen.
     * @param Width in pixels of the screen.
     * @param Height in pixels of the screen.
     */
    public void setTargetSize(int width, int height) {
    	mGLView.setTargetSize(width, height);
    }
    
    /**
     * Gets the Activity's application Context.
     * @return A valid application Context.
     */
    public static Context getContext() { return sContext; }
}