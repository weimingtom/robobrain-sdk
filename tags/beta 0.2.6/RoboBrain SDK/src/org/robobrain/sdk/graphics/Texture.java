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

/**
 * Encapsulates an OpenGL texture. All textures must sized to a power of 2. (e.g. 32x32, 64x64, etc.)
 * @author James Johnson
 *
 */
public class Texture {	
	public boolean loaded;
	
	private String mFilename;
	private int mID;
	private int mGLID;
	private int mWidth;
	private int mHeight;
	
	/**
	 * Initializes a Texture.
	 * @param filename
	 * The path to the image in the assets folder.
	 * @param id
	 * The handle to the Texture in TextureManager.
	 * @param glID
	 * The Texture's OpenGL ID.
	 * @param width
	 * The width of the Texture in pixels.
	 * @param height
	 * The height of the Texture in pixels.
	 */
	public Texture(String filename, int id, int glID, int width, int height) {
		mFilename = filename;
		mID = id;
		mGLID = glID;
		mWidth = width;
		mHeight = height;
		loaded = false;
	}
	
	/**
	 * Gets the Texture's handle in TextureManager.
	 * @return
	 * The Texture's handle in TextureManager.
	 */
	public int getID() { return mID; }
	
	/**
	 * Gets the Texture's OpenGL ID.
	 * @return
	 * The Texture's OpenGL ID.
	 */
	public int getGLID() { return mGLID; }
	
	/**
	 * Gets the width of the Texture.
	 * @return
	 * The width of the Texture in pixels.
	 */
	public int getWidth() { return mWidth; }
	
	/**
	 * Gets the height of the Texture.
	 * @return
	 * The height of the Texture in pixels.
	 */
	public int getHeight() { return mHeight; }
	
	/**
	 * Gets the Texture's file name.
	 * @return
	 * A String containing the path to the Texture's image in the assets folder.
	 */
	public String getFilename() { return mFilename; }
}
