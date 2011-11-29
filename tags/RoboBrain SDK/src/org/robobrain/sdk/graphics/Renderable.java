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

import javax.microedition.khronos.opengles.GL10;

/**
 * The base Renderable object. Don't instantiate is directly. Derive your 
 * custom classes from this that need to be drawn by OpenGL. Both Sprite
 * and SimpleSprite are derived from this class.
 * @author James Johnson
 */
public class Renderable {
	/**
	 * The Renderable's position along the X axis.
	 */
	public float x;
	
	/**
	 * The Renderable's position along the Y axis.
	 */
	public float y;
	
	/**
	 * The angle of rotation applied to the Renderable. Ranges from 0-360 degrees.
	 */
	public float rotation;
	
	/**
	 * The amount the Renderable has been scaled. 1.0f = normal size.
	 */
	public float scale;
	
	/**
	 * Set to false if you don't want to draw the Renderable.
	 */
	public boolean visible;
	
	/**
	 * Updates the Renderable each frame.
	 * @param time
	 * The number of milliseconds elapsed since the last frame.
	 */
	public void update(long time) {}
	
	/**
	 * Draws the Renderable to the screen.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 */
	public void draw(GL10 gl) {}
	
	/**
	 * Gets the width of the Renderable.
	 * @return
	 * The width of the Renderable in pixels.
	 */
	public int getWidth() { return 0; }
	
	/**
	 * Gets the height of the Renderable.
	 * @return
	 * The height of the Renderable in pixels.
	 */
	public int getHeight() { return 0;}
	
	/**
	 * Gets the Texture used by the Renderable.
	 * @return
	 * The Texture used by the Renderable.
	 */
	public Texture getTexture() { return null; }
}
