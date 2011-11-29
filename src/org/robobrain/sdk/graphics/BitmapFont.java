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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.robobrain.sdk.GLRenderer;
import org.robobrain.sdk.util.StringUtils;

import android.util.Log;

/**
 * Draws text to the screen via OpenGL
 * @author James Johnson
 *
 */
public class BitmapFont {
	/** Size of each vertext in bytes */
	public static final int VERTEX_SIZE = (2 + 2) * 4; // Float x2 + Short x2
	
	/** The width or height of one character in Textures coords */
	public static final float TEXTURE_UNIT = 1.0f / 16.0f;
	
	private Texture mTexture;
	private Color mColor;
	private FloatBuffer mVertices;
	private ShortBuffer mIndices;
	private int mWidth;
	private int mHeight;
	
	/** 
	 * Initializes the Bitmap Font
	 * @param texture
	 * The Texture containing the font. It must be 16x16 units square and 
	 * contain the first UTF-8 codepage in order.
	 */
	public BitmapFont(Texture texture) {	
		if (texture == null) {
			return;
		}
		mTexture = texture;
		mVertices = null;
		mIndices = null;
		mWidth = texture.getWidth() / 16;
		mHeight = texture.getHeight() / 16;
		mColor = Color.WHITE;
	}
	
	/**
	 * Draws one character on the screen. Please note that the origin is at 
	 * the characer's top left corner.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 * @param c
	 * The character to draw.
	 * @param x
	 * The position of the character on the X-Axis.
	 * @param y
	 * The position of the character on the Y-Axis.
	 */
	public void drawChar(GL10 gl, char c, float x, float y) {
		if (mTexture == null) {
			return;
		}
		int left = (int)c % 16;
		int right = left + 1;
		int top = (int)c / 16;
		int bottom = top + 1;
		
		float l = ((float)left) * TEXTURE_UNIT;
		float r = ((float)right) * TEXTURE_UNIT;
		float t = ((float)top) * TEXTURE_UNIT;
		float b = ((float)bottom) * TEXTURE_UNIT;
		
		float[] verts = { 
			0, 0, l, t,
			mWidth, 0, r, t,
			mWidth, mHeight, r, b,
			0, mHeight, l, b
		};
		
		// Vertices
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * VERTEX_SIZE);
		buf.order(ByteOrder.nativeOrder());
		mVertices = buf.asFloatBuffer();
		mVertices.put(verts);
		mVertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, mVertices);
		mVertices.position(2);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, VERTEX_SIZE, mVertices);
	
		// Indices
		short[] ind = { 0, 1, 2, 2, 3, 0 };
		ByteBuffer buf2 = ByteBuffer.allocateDirect(6 * 2);
		buf2.order(ByteOrder.nativeOrder());
		mIndices = buf2.asShortBuffer();
		mIndices.put(ind);
		mIndices.position(0);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glScalef(1.0f * GLRenderer.getScale(), 1.0f * GLRenderer.getScale(), 1.0f);
		gl.glTranslatef(x, y, 0);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, mIndices);
	}
	
	/**
	 * Draws a String to the screen. Please note that the origin is at 
	 * the String's top left corner.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 * @param s
	 * The string to draw.
	 * @param x
	 * The position of the string on the X-Axis.
	 * @param y
	 * The position of the string on the Y-Axis.
	 */
	public void drawString(GL10 gl, String s, float x, float y) {
		if (mTexture == null) {
			return;
		}
		if (s == null) {
			return;
		}
		int c = s.length();
		for (int i = 0; i < c; i++) {
			float cx = x + (i * mWidth);
			drawChar(gl, s.charAt(i), cx, y);
		}
	}
	
	/**
	 * Prepares the Font for drawing. Call this before any draw calls.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 */
	public void begin(GL10 gl) {
		if (mTexture == null) {
			return;
		}
		gl.glColor4f(mColor.r, mColor.g, mColor.b, mColor.a);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.getGLID());
	}
	
	/**
	 * Finishes drawing. Call after drawing text.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 */
	public void end(GL10 gl) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Set the text color.
	 * @param c
	 * The text color.
	 */
	public void setColor(Color c) {
		if (c != null) {
			mColor = c.clone();
		}
	}
	
	/**
	 * Gets the text color.
	 * @return
	 * The text color.
	 */
	public Color getColor() { return mColor; }
}
