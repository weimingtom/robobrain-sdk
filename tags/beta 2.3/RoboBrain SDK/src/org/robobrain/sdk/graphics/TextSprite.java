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
 * The TextSprite simply draws a String to the screen. It is analogous to a Label.
 * @author James Johnson
 */
public class TextSprite extends Renderable {
	private BitmapFont mFont;
	private String mMessage;
	
	/**
	 * Initialize the TextSprite
	 * @param texture
	 * The Texture containing the font. It must be 16x16 units square and 
	 * contain the first UTF-8 codepage in order.
	 */
	public TextSprite(Texture texture) {
		if (texture == null) {
			return;
		}
		mFont = new BitmapFont(texture);
		mMessage = null;
	}
	
	/**
	 * Initialize the TextSprite
	 * @param texture
	 * The Texture containing the font. It must be 16x16 units square and 
	 * contain the first UTF-8 codepage in order.
	 * @param message
	 * The String to be drawn to the screen.
	 */
	public TextSprite(Texture texture, String message) {
		if (texture == null) {
			return;
		}
		mFont = new BitmapFont(texture);
		mMessage = message;
	}
	
	/**
	 * Sets the message to be displayed on the screen.
	 * @param message
	 * The string to be displayed on the screen.
	 */
	public void setMessage(String message) {
		mMessage = message;
	}
	
	/**
	 * Draws the message to the screen.
	 * @param gl
	 * A valid OpenGL ES 1.0 object
	 */
	@Override 
	public void draw(GL10 gl) {
		mFont.begin(gl);
		mFont.drawString(gl, mMessage, x, y);
		mFont.end(gl);
	}

}
