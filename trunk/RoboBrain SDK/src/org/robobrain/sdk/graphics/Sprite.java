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

import android.util.Log;

/**
 * An animated Renderable object. Draws an animated Texture to the screen. It is 
 * in effect a Sprite sheet.Note: The Sprite's origin is at the center of it's
 *  bounding Rectangle.
 * @author James Johnson
 *
 */
public class Sprite extends Renderable{
	public static final int VERTEX_SIZE = (2 + 2) * 4; // Float x2 + Short x2
	
	/** 
	 * This color tints the Sprite. It defaults to white.
	 */
	public Color color;
	
	protected Texture mTexture;
	protected FloatBuffer mVertices;
	protected ShortBuffer mIndices;
	protected int mWidth;
	protected int mHeight;
	protected int mHalfWidth;
	protected int mHalfHeight;
	protected int mCurrentFrame;
	protected int mMaxFrames;
	protected int mColumns;
	protected int mRows;
	protected float mColUnit;
	protected float mRowUnit;
	protected float mTop;
	protected float mBottom;
	protected float mLeft;
	protected float mRight;
	protected int mTime;
	
	/**
	 * Initializes the Sprite. Note: Sprites default to an animation frame rate of 15fps.
	 * @param texture
	 * The Texture containing all of the animation frames for this Sprite. Please note
	 * that all textures must be sized to a power of 2. (e.g. 32x32 pixels, 64x64 pixels, etc) 
	 * @param width
	 * The width of one frame in pixels.
	 * @param height
	 * The height of one frame in pixels.
	 * @param numFrames
	 * The total number of frames in the animation.
	 */
	public Sprite(Texture texture, int width, int height, int numFrames) {
		if (texture == null) {
			return;
		}
		mTexture = texture;
		
		mWidth = width;
		mHeight = height;
		mHalfWidth = mWidth / 2;
		mHalfHeight = mHeight / 2;
		mMaxFrames = numFrames;
		mCurrentFrame = 0;
		
		mColumns = texture.getWidth() / mWidth;
		mRows = texture.getHeight() / mHeight;
		mColUnit = (1.0f / (float)mColumns);
		mRowUnit = (1.0f / (float)mRows);
		mLeft = 0.0f;
		mRight = mColUnit;
		mTop = 0.0f;
		mBottom = mRowUnit;
		
		x = 0;
		y = 0;
		scale = 1.0f;
		rotation = 0;
		mTime = 0;
		
		color = Color.WHITE;
		
		generateFrameVerts();
	}
	
	/**
	 * Updates the Renderable each frame and advances the animation.
	 * @param time
	 * The number of milliseconds elapsed since the last frame.
	 */
	@Override
	public void update(long time) {
		mTime += time;
		if (mTime < 66) {
			return;
		}
		mTime = 0;
		mCurrentFrame++;
		if (mCurrentFrame > (mMaxFrames - 1)) {
			mCurrentFrame = 0;
		}
		generateFrameRect();
		generateFrameVerts();
	}
	
	/**
	 * Draws the Texture to the screen.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 */
	@Override
	public void draw(GL10 gl){
		gl.glColor4f(color.r, color.g, color.b, color.a);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(rotation, 0, 0, 1);
		gl.glScalef(scale * GLRenderer.getScale(), scale * GLRenderer.getScale(), 1.0f);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, mIndices);
	}
	
	private void generateFrameVerts() {		
		// Vertices
		float[] verts = { 
			-mHalfWidth, -mHalfHeight, mLeft, mTop,
			mHalfWidth, -mHalfHeight, mRight, mTop,
			mHalfWidth, mHalfHeight, mRight, mBottom,
			-mHalfWidth, mHalfHeight, mLeft, mBottom,	
		};
		
		GL10 gl = GLRenderer.getGL();
		
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
	}
	
	private void generateFrameRect() {
		int left = mCurrentFrame % mColumns;
		int right = left + 1;
		int top = mCurrentFrame / mRows;
		int bottom = top + 1;
		
		mLeft = ((float)left) * mColUnit;
		mRight = ((float)right) * mColUnit;
		mTop = ((float)top) * mRowUnit;
		mBottom = ((float)bottom) * mRowUnit;
	}
	
	
	/**
	 * Gets the width of the Sprite. It is equal to the width of one frame of animation.
	 * @return
	 * The width of the SimpleSprite in pixels.
	 */
	@Override
	public int getWidth() { return mWidth; }
	
	/**
	 * Gets the height of the Sprite. It is equal to the width of one frame of animation.
	 * @return
	 * The height of the SimpleSprite in pixels.
	 */
	@Override
	public int getHeight() { return mHeight; }
	
	/**
	 * One half of the Sprite's height.
	 * @return
	 * The height of the Sprite divided by 2.
	 */
	public int getHalfWidth() { return mWidth; }
	
	/**
	 * One half of the Sprite's width.
	 * @return
	 * The width of the Sprite divided by 2.
	 */
	public int getHalfHeight() { return mHeight; }
	
	/**
	 * Gets the Texture used by the SimpleSprite.
	 * @return
	 * The Texture used by the SimpleSprite.
	 */
	@Override
	public Texture getTexture() { 
		return mTexture; 
	}
	
	//public int getTextureID() { return mTexture.getGLID(); }
}
