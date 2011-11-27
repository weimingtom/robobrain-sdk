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

package org.robobrain.sdk.audio;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.robobrain.sdk.GameActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
	/** Maximum number of channels that can be mixed at one time. */
	public static final int MAX_CHANNELS = 32;
	
	private static Context sContext;
	private static SoundPool sSoundPool;
	private static ConcurrentHashMap<Integer, Sound> sSounds;
	private static boolean sInitialized = false;
	
	/**
	 * Register an integer handle for a sound file.
	 * @param filename
	 * Path to the sound file in the assets folder.
	 * @param id
	 * The handle for this audio file
	 */
	public static void registerSound(String filename, int id) {
		if (!sInitialized) {
			init();
		}
		if (sSounds == null) {
			init();
		}
		if (sSounds.containsKey(id)) {
			Log.d("Sound Manager", "Duplicate key in texture HashMap.");
			return;
		}
		
		int sndID = loadSound(filename);
		Sound snd = new Sound(sndID);
		sSounds.put(id, snd);
	}
	
	/**
	 * Plays a sound.
	 * @param id
	 * The handle of the sound to be played.
	 */
	public static void play(int id) {
		if (!sInitialized) {
			return;
		}
		if (!sSounds.containsKey(id)) {
			return;
		}
		Sound snd = sSounds.get(id);
		int l = 0;
		if (snd.loop) {
			l = -1;
		}
		int r = sSoundPool.play(snd.id, snd.leftVol, snd.rightVol, 0, l, 1);
		if (r != 0) {
			snd.editID = r;
			sSounds.remove(id);
			sSounds.put(id, snd);
		}
	}
	
	/**
	 * Plays a sound but overrides its loop setting.
	 * @param id
	 * The handle of the sound to be played.
	 * @param loop
	 * Set this to true to loop the sound.
	 */
	public static void play(int id, boolean loop) {
		if (!sInitialized) {
			return;
		}
		if (!sSounds.containsKey(id)) {
			return;
		}
		Sound snd = sSounds.get(id);
		int l = 0;
		if (loop) {
			l = -1;
		}
		int result = sSoundPool.play(snd.id, snd.leftVol, snd.rightVol, 0, l, 1);
		try {
			Thread.sleep(300);
		} catch (InterruptedException ex) {
			// do nothing
		}
		if (result != 0) {
			snd.editID = result;
			sSounds.remove(id);
			sSounds.put(id, snd);
		} 
		Log.v("SoundManager", "Play");
	}
	
	/**
	 * Sets the volume for a sound.
	 * @param id
	 * The handle of the sound.
	 * @param left
	 * Volume of the left channel. 0 = no sound, 1 = max volume.
	 * @param right
	 * Volume of the right channel. 0 = no sound, 1 = max volume.
	 */
	public static void setVolume(int id, float left, float right) {
		if (!sInitialized) {
			return;
		}
		if (!sSounds.containsKey(id)) {
			return;
		}
		Sound snd = sSounds.get(id);
		snd.leftVol = left;
		snd.rightVol = right;
		sSounds.remove(id);
		sSounds.put(id, snd);
	}
	
	/**
	 * Sets a sound to loop.
	 * @param id
	 * The handle of the sound to be looped.
	 * @param loop
	 * Set this to true to loop the sound.
	 */
	public static void setLoop(int id, boolean loop) {
		if (!sInitialized) {
			return;
		}
		if (!sSounds.containsKey(id)) {
			return;
		}
		Sound snd = sSounds.get(id);
		snd.loop = loop;
		sSounds.remove(id);
		sSounds.put(id, snd);
	}
	
	/**
	 * Stops a sound.
	 * @param id
	 * The handle of the sound to be stopped.
	 */
	public static void stop(int id) {
		if (!sInitialized) {
			return;
		}
		if (!sSounds.containsKey(id)) {
			return;
		}
		Sound snd = sSounds.get(id);
		sSoundPool.stop(snd.id);
		if (snd.editID != 0) {
			sSoundPool.stop(snd.editID);
		} else {
			Log.e("SoundManager", "Error stopping sound.");
		}
	}
	
	/**
	 * Stops all playing sounds.
	 */
	public static void stopAll() {
		for (int id : sSounds.keySet()) {
			stop(id);
		}
	}
	
	/**
	 * Unloads a sound from memory.
	 * @param id
	 * The handle of the sound to be unloaded.
	 */
	public static void unloadSound(int id) {
		if (!sInitialized) {
			return;
		}
		if (!sSounds.containsKey(id)) {
			return;
		}
		Sound snd = sSounds.get(id);
		sSoundPool.unload(snd.id);
		sSounds.remove(id);
	}
	
	/**
	 * Unloads all sounds loaded by SoundManager.
	 */
	public static void unloadAll() {
		for (int id : sSounds.keySet()) {
			unloadSound(id);
		}
		sSounds.clear();
	}
	
	/**
	 * Releases all resources the SoundManager is using. Call this at least once
	 * before shutting down your game.
	 */
	public static void release() {
		if (sSounds.size() > 0) {
			unloadAll();
		}
		sSoundPool.release();
		sSounds = null;
		sSoundPool = null;
		
	}
	
	// Initializes the sound player.
	private static void init() {
		sContext = GameActivity.getContext();
		sSoundPool = new SoundPool(MAX_CHANNELS, AudioManager.STREAM_MUSIC, 0);
		sSounds = new ConcurrentHashMap<Integer, Sound>();
		sInitialized = true;
	}
	
	// Loads a sound file into memory from disk.
	private static int loadSound(String filename) {
		AssetManager assetManager = sContext.getAssets();
		int id = -1;
        try {
            AssetFileDescriptor descriptor = assetManager.openFd(filename);
            id = sSoundPool.load(descriptor, 1);
        } catch (IOException e) {
            Log.e("Sound Loading Error:", e.getMessage());
        }
        Log.d("SoundManager", "Loading " + filename + ", ID: " + id);
		return id;
	}
}
