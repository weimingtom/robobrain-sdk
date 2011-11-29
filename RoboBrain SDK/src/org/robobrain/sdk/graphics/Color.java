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
 * The Color class encapsulates a 32bit RGBA color.
 * @author James Johnson
 *
 */
public class Color implements Cloneable {
	/**
	 * A convenience constant for the color Black.
	 */
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	/**
	 * A convenience constant for the color  White.
	 */
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	/**
	 * A convenience constant for the color Gray.
	 */
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 0.5f);
	/**
	 * A convenience constant for the color Red.
	 */
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	/**
	 * A convenience constant for the color Green.
	 */
	public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	/**
	 * A convenience constant for the color Blue.
	 */
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	/**
	 * A convenience constant for the color Cyan.
	 */
	public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f, 1.0f);
	/**
	 * A convenience constant for the color Magenta.
	 */
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f, 1.0f);
	/**
	 * A convenience constant for the color Yellow.
	 */
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	
	/**
	 * The Color's Red channel.
	 */
	public float r;
	/**
	 * The Color's Green channel. Ranges from 0.0f - 1.0f.
	 */
	public float g;
	/**
	 * The Color's Blue channel. Ranges from 0.0f - 1.0f.
	 */
	public float b;
	/**
	 * The Color's Alpha channel. Ranges from 0.0f - 1.0f.
	 */
	public float a;
	
	/**
	 * Initializes the Color to black.
	 */
	public Color() {
		this.r = 0.0f;
		this.g = 0.0f;
		this.b = 0.0f;
		this.a = 1.0f;
	}
	
	/**
	 * Initializes the Color to a specific value.
	 * @param r
	 * The amount of Red, from 0.0f - 1.0f.
	 * @param g
	 * The amount of Green, from 0.0f - 1.0f.
	 * @param b
	 * The amount of Blue, from 0.0f - 1.0f.
	 * @param a
	 * The amount of transparency (Alpha), from 0.0f - 1.0f.
	 */
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Initializes the Color to a specific value.
	 * @param r
	 * The amount of Red, from 0.0f - 1.0f.
	 * @param g
	 * The amount of Green, from 0.0f - 1.0f.
	 * @param b
	 * The amount of Blue, from 0.0f - 1.0f.
	 */
	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0f;
	}
	
	/**
	 * Creates an exact copy of the Color.
	 */
	public Color clone() {
		return new Color(this.r, this.g, this.b, this.a);
	}
}
