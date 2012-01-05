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

package org.robobrain.sdk.graphics;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.microedition.khronos.opengles.GL10;

import org.robobrain.sdk.GLRenderer;
import org.robobrain.sdk.GameActivity;
import org.robobrain.sdk.util.StringUtils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Manages the creation and destruction of OpenGL textures throughout their life-cycle. 
 * @author James Johnson
 *
 */
public class TextureManager {
	/**
	 * Returned by Texture functions to signify an invalid texture.
	 */
	public static final int INVALID_ID = -1;
	
	private static ConcurrentHashMap<Integer, Texture> mTextures;
	
	/**
	 * Registers a numeric constant with a particular texture. After calling this
	 * function, use the ID to request the texture from the TextureManager.
	 * @param filename The image file to associate with the ID. 
	 * @param id The ID used as a handle for the texture.
	 */
	public static void registerTexture(String filename, int id) {		
		if (mTextures == null) {
			mTextures = new ConcurrentHashMap<Integer, Texture>();
		}
		if (id < 0) {
			Log.e("Texture Manager", "Invalid ID in registerTexture()");
			return;
		}
		if (mTextures.containsKey(id)) {
			Log.e("Texture Manager", "Duplicate key in texture HashMap.");
			return;
		}
		Texture tex = new Texture(filename, id, INVALID_ID, 0, 0);
		mTextures.put(id, tex);
	}
	
	/**
	 * Finds the requested texture and loads it into OpenGL if necessary.
	 * @param id 
	 * The handle for the texture
	 * @return 
	 * The valid Texture object or null if it is not available.
	 */
	public static Texture getTexture(int id) {
		if (mTextures == null) {
			return null;
		}
		if (id < 0) {
			Log.d("Texture Manager", "Invalid ID in getTexture()");
			return null;
		}
		Texture t = mTextures.get(id);
		if (t == null) {
			return null;
		}
		if (!t.loaded) {
			t = loadTexture(t.getFilename(), t.getID());
			if (t == null) {
				return null;
			}
			t.loaded = true;
			mTextures.remove(t.getID());
			mTextures.put(t.getID(), t);
		}
		
		Log.d("TextureManager", "Tex - File: " + t.getFilename() + ", Id:" + t.getID()+ ", GL Id: " + t.getGLID());
		return t;
	}
	
	/**
	 * Unloads all textures from video memory.
	 */
	public static void unloadAll() {
		if (mTextures == null) {
			return;
		}
		for (Texture tex : mTextures.values()) {
			Texture t = unloadTexture(tex);
			if (t != null) {
			    int id = tex.getID();
			    mTextures.remove(id);
			    mTextures.put(id, t);
			}
		}
		//mTextures.clear();
	}
	
	/**
	 * Releases any memory associated with the TextureManager.
	 */
	public static void release() {
		unloadAll();
		if (mTextures != null) {
			mTextures.clear();
		}
	}
	
	/**
	 * loads all textures into video memory.
	 */
	public static void loadAll() {
		if (mTextures == null) {
			return;
		}
		Log.d("Loading Textures", "Number = " + mTextures.size());
		for (Texture tex : mTextures.values()) {
			if (!tex.loaded) {
			    int id = tex.getID();
				Texture t = loadTexture(tex.getFilename(), id);
				//Log.v("Loading Texture", "file: " + tex.getFilename());
				mTextures.remove(id);
				mTextures.put(id, t);
			}
		}
	}
	
	// Loads one texture out of the assets folder into video memory
	private static Texture loadTexture(String filename, int id) {
		if (StringUtils.isNullorWhiteSpace(filename)) {
			Log.e("Load Bitmap:", "Invalid filename.");
			return null;
		}
		if (id < 0) {
			Log.e("Texture Manager", "Invalid ID in loadTexture()");
			return null;
		}
		AssetManager assets = GameActivity.getContext().getAssets();
		if (assets == null) {
			Log.e("Texture Manager", "Unable to load AssetManager.");
			return null;
		}
        InputStream is = null;
        try {
        	is = assets.open(filename);
        } catch (Throwable t) {
        	Log.e("Load Bitmap: ", t.getMessage());
        	return null;
        }
		Bitmap bmp = BitmapFactory.decodeStream(is);
		if (bmp == null) {
			Log.e("Load Texture", "Unable to decode bitmap.");
			return null;
		}
		
		GL10 gl = GLRenderer.getGL();
		if (gl == null) {
			Log.e("Load Texture", "Unable to create OpenGL texture.");
			bmp.recycle();
			return null;
		}
		int textureIDs[] = new int[1];
		gl.glGenTextures(1, textureIDs, 0);
		int glID = textureIDs[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, glID);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		int err = gl.glGetError();
		if (err != GL10.GL_NO_ERROR) {
			Log.e("Load Texture", "Unable to create OpenGL texture.");
			bmp.recycle();
			return null;
		}
		
		Texture t = new Texture(filename, id, glID, bmp.getWidth(), bmp.getHeight());
		t.loaded = true;
		bmp.recycle();
		return t;
	}
	
	// Deletes one texture from video memory.
	private static Texture unloadTexture(Texture texture) {
		if (texture == null) {
			return null;
		}
		if (!texture.loaded) {
			return null;
		}
		GL10 gl = GLRenderer.getGL();
		int glID = texture.getGLID();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, glID);
		int ids[] = { glID };
		gl.glDeleteTextures(1, ids, 0);
		texture.loaded = false;
		return texture;
	}
}
