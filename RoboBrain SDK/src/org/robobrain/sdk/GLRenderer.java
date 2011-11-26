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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.robobrain.sdk.game.Engine;
import org.robobrain.sdk.game.Entity;
import org.robobrain.sdk.graphics.BitmapFont;
import org.robobrain.sdk.graphics.Color;
import org.robobrain.sdk.graphics.Texture;
import org.robobrain.sdk.graphics.TextureManager;

import android.opengl.GLSurfaceView;
import android.util.Log;

/**
 * GLRenderer sets up and provides an OpenGL ES 1.0 surface to draw on.
 * @author James Johnson
 */
public class GLRenderer implements GLSurfaceView.Renderer {
	private static GL10 sGL;
	private static int sWidth;
	private static int sHeight;
	private static float sScale;
	private static float sFPS;
	
	private Color mClearColor;
	private long mStartTime;
	private long mEndTime;
	private long mDeltaTime;
	private Engine mEngine;
	private int mTargetWidth;
	private int mTargetHeight;
	private boolean mPaused;
	
	/**
	 * Called when the surface is created or recreated.
	 */
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		sGL = gl;
		sScale = 1.0f;
		mPaused = false;
		sFPS = 0;
		if (mClearColor == null) {
			mClearColor = Color.BLACK;
		}
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		setClearColor(mClearColor);		
		TextureManager.loadAll();
	}
	
	/**
	 * Called when the surface is created or recreated.
	 */
	public void onDrawFrame(GL10 gl) {
		if (mPaused) {
			return;
		}
		mStartTime = System.currentTimeMillis();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		Entity[] list = null;
		if (mEngine != null) {
			mEngine.update(mDeltaTime);
			list = mEngine.getDisplayList();
		}
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				list[i].draw(gl);
			}
		}
		mEndTime = System.currentTimeMillis();
		mDeltaTime = mEndTime - mStartTime;
		if (mDeltaTime > 0) {
			sFPS = 1000.0f / (float)mDeltaTime;
		}
		//Log.d("FPS", "" + mFPS);
	}
	
	/**
	 * Called when the surface changed size.
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		sWidth = width;
		sHeight = height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, height, 0, 0, 1);
		
		if (mTargetWidth > 0) {
			sScale = (float)width / (float)mTargetWidth;
		} else {
			sScale = 1.0f;
		}
		
		if (mEngine != null) {
			if (!mEngine.getInitialized()) {
				if (mEngine != null) {
					mEngine.init();
				}
			}
		}
	}
	
	/**
	 * Sets the background color of the drawing surface.
	 * @param color you wish to set the background to.
	 */
	public void setClearColor(Color color) {
		if (color != null) {
			mClearColor = color.clone();
		}
		if (sGL != null) {
			sGL.glClearColor(mClearColor.r, mClearColor.g, mClearColor.b, mClearColor.a);
		}
	}
	
	/**
	 * Registers your game's Engine with this renderer.
	 * @param game Your game's Engine object.
	 */
	public void registerGame(Engine game) {
		if (game != null) {
			mEngine = game;
		}
		else {
			Log.d("Engine", "Invalid game passed to Engine.");
		}
	}
	
	/**
     * Sets the ideal width and height for your game. This will be used to 
     * calculate the ratio to scale your scene assets by in order to fit the 
     * player's phone screen.
     * @param width in pixels of the screen.
     * @param height in pixels of the screen.
     */
	public void setTargetSize(int width, int height) {
		mTargetWidth = width;
		mTargetHeight = height;
	}
	
	/**
	 * Pauses the Renderer.
	 */
	public void pause() {
		mPaused = true;
	}
	
	/**
	 * Unpauses the Renderer.
	 */
	public void resume() {
		mPaused = false;
	}
	
	/**
	 * Gets an instance of the OpenGL object.
	 * @return The OpengGL object.
	 */
	public static GL10 getGL() { return sGL; }
	
	/**
	 * Returns the actual width of the drawing surface.
	 * @return The width in pixels.
	 */
	public static int getWidth() { return sWidth; }
	
	/**
	 * Returns the actual height of the drawing surface.
	 * @return The height in pixels.
	 */
	public static int getHeight() { return sHeight; }
	
	/**
	 * Gets the scalar used to adjust the size of your game's assets
	 * to maintain their relative sizes and proportions.
	 * @return The amount your assets are being scaled.
	 */
	public static float getScale() { return sScale; }
	
	/**
	 * Gets the frame rate the Renderer is drawing at.
	 * @return
	 * Returns the frame rate in Frame Per Second.
	 */
	public static float getFPS() { return sFPS; }
}

