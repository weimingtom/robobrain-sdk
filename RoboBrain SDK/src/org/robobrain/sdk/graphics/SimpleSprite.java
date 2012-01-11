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
 * The simplest Renderable object. Draws a Texture to the screen.
 * Note: The SimpleSprite's origin is at the center of it's bounding Rectangle.
 * @author James Johnson
 *
 */
public class SimpleSprite extends Renderable {
	public final int VERTEX_SIZE = (2 + 2) * 4; // Float x2 + Short x2

	public Color color;
	
	private Texture mTexture;
	private FloatBuffer mVertices;
	private ShortBuffer mIndices;
	private int mWidth;
	private int mHeight;
	private int mHalfWidth;
	private int mHalfHeight;
	
	/**
	 * Initializes the SimpleSprite
	 * @param texture
	 * The Texture to be drawn. All textures must be sized to a power of 2. 
	 * (e.g. 32x32 pixels, 64x64 pixels, etc) 
	 */
	public SimpleSprite(Texture texture) {
		if (texture == null) {
			return;
		}
		mTexture = texture;
		
		mWidth = mTexture.getWidth();
		mHeight = mTexture.getHeight();
		mHalfWidth = mWidth / 2;
		mHalfHeight = mHeight / 2;
		
		x = 0;
		y = 0;
		scale = 1.0f;
		color = Color.WHITE;
		visible = true;
	}
	
	/**
     * Gets a new copy of the Renderable's texture from 
     * the TextureManager.
     */
    @Override 
    public void updateTexture() {
        mTexture = TextureManager.getTexture(mTexture.getID());
    }
	
	/**
	 * Draws the Texture to the screen.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 */
	@Override
	public void draw(GL10 gl) {
		if (!visible) {
			return;
		}
		generateFrameVerts();
		gl.glColor4f(color.r, color.g, color.b, color.a);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(rotation, 0, 0, 1);
		gl.glScalef(scale * GLRenderer.getScale(), scale * GLRenderer.getScale(), 1.0f);
		gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, mIndices);
	}
	
	public void draw(GL10 gl, float x, float y, float rotation, float scale) {
	    this.x = x;
	    this.y = y;
	    this.rotation = rotation;
	    this.scale = scale;
	    this.draw(gl);
	}
	
	private void generateFrameVerts() {	
		// Vertices
		float[] verts = { 
			-mHalfWidth, -mHalfHeight, 0.0f, 0.0f,
			mHalfWidth, -mHalfHeight, 1.0f, 0.0f,
			mHalfWidth, mHalfHeight, 1.0f, 1.0f,
			-mHalfWidth, mHalfHeight, 0.0f, 1.0f,	
		};
		
		GL10 gl = GLRenderer.getGL();
		
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
	
	/**
	 * Gets the width of the SimpleSprite.
	 * @return
	 * The width of the SimpleSprite in pixels.
	 */
	@Override
	public int getWidth() { return mWidth; }
	
	/**
	 * Gets the height of the SimpleSprite.
	 * @return
	 * The height of the SimpleSprite in pixels.
	 */
	@Override
	public int getHeight() { return mHeight; }
	
	/**
	 * One half of the SimpleSprite's width.
	 * @return
	 * The width of the SimpleSprite divided by 2.
	 */
	public int getHalfWidth() { return mWidth; }
	
	/**
	 * One half of the SimpleSprite's height.
	 * @return
	 * The height of the SimpleSprite divided by 2.
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

