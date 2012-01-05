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

import android.util.FloatMath;

/**
 * Encapsulates a 2D Vector.
 * @author James Johnson
 *
 */
public class Vector implements Cloneable {
	/** Constant used to convert degrees to radians. */
	public static final float TO_RADIANS = (1.0f / 180.0f) * (float)Math.PI;
	
	/** Constant used to convert radians to degrees. */
	public static final float TO_DEGREES = (1.0f / (float)Math.PI) * 180.0f;
	
	/** The Vector's distance from the origin on the X-axis. */
	public float x;
	
	/** The Vector's distance from the origin on the Y-axis. */
	public float y;
	
	/** Initializes the Vector at the origin. */
	public Vector() {
		x = 0;
		y = 0;
	}
	
	/** Initializes the Vector at a specified point in space. */
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** 
	 * Adds a Vector to this one. 
	 * @param x
	 * The x component to add to this Vector.
	 * @param y
	 * The y component to add to this Vector.
	 * @return
	 */
	public Vector add(float x, float y) {
		this.x += x;
		this.x += y;
		return this;
	}
	
	/** 
	 * Adds a Vector to this one. 
	 * @param vector
	 * The Vector to add to this one.
	 * @return
	 */
	public Vector add(Vector vector) {
		this.x += vector.x;
		this.x += vector.y;
		return this;
	}
	
    /**
	 * Subtracts a Vector from this one.
	 * @param x
	 * The x component to subtract from this Vector.
	 * @param y
	 * The y component to subtract from this Vector.
	 * @return
	 */
	public Vector subtract(float x, float y) {
		this.x -= x;
		this.x -= y;
		return this;
	}
	
    /**
	 * Subtracts a Vector from this one.
	 * @param vector
	 * The Vector to subtract from this one.
	 * @return
	 */ 
	public Vector subtract(Vector vector) {
		this.x -= vector.x;
		this.x -= vector.y;
		return this;
	}
	
	/**
	 * Multiplies this Vector by a scalar.
	 * @param scalar
	 * The number to multiply both components by.
	 * @return
	 * This Vector after it has been multiplied.
	 */
	public Vector multiply(float scalar) {
		this.x *= scalar;
		this.x *= scalar;
		return this;
	}
	
	public float length() {
		return FloatMath.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public Vector normalize() {
		float len = length();
		if (len != 0.0f) {
			this.x /= len;
			this.y /= len;
		}
		return this;
	}
	
	public float angle() {
		float angle = (float)Math.atan2(this.y, this.x) * TO_DEGREES;
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}
	
	public Vector rotate(float angle) {
		float rads = angle * TO_RADIANS;
		float cos = FloatMath.cos(rads);
		float sin = FloatMath.sin(rads);
		
		float newX = this.x * cos - this.y * sin;
		float newY = this.x * sin + this.y * cos;
		
		this.x = newX;
		this.y = newY;
		
		return this;
	}
	
	public float distance(float x, float y) {
		float dx = this.x - x;
		float dy = this.y - y;
		return FloatMath.sqrt(dx * dx + dy * dy);
	}
	
	public float distance(Vector vector) {
		float dx = this.x - vector.x;
		float dy = this.y - vector.y;
		return FloatMath.sqrt(dx * dx + dy * dy);
	}
	
	public Vector clone() { return new Vector(this.x, this.y); }
}
