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
 * Encapsulates a 2D Rectangle.
 * @author James Johnson
 *
 */
public class Rectangle {
	public int x;
	public int y;
	public int width;
	public int height;
	
	/**
	 * Initializes the Rectangle at the origin with no width or height.
	 */
	public Rectangle() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}
	
	/**
	 * Initializes the Rectangle at specific point in space.
	 * @param x
	 * The Rectangle's position along the X axis.
	 * @param y
	 * The Rectangle's position along the Y axis.
	 * @param width
	 * The Rectangle's width in pixels.
	 * @param height
	 * The Rectangle's height in pixels.
	 */
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Determins if the Rectangle intersects with another Rectangle.
	 * @param rect
	 * The other Rectangle to test against.
	 * @return
	 * Returns true if the Rectangles intersect.  
	 */
	public boolean intersects(Rectangle rect) {
		if ((rect.getRight() < getLeft()) &&
			(rect.getLeft() > getRight()) && 
			(rect.getTop() > getBottom()) && 
			(rect.getBottom() < getTop())) {
			return false;
		}
		return true;
	}
	
	/*
	 * Gets the top side of the Rectangle.
	 */
	public int getTop() { return y; }
	/*
	 * Gets the bottom side of the Rectangle.
	 */
	public int getBottom() { return y + height; }
	/*
	 * Gets the left side of the Rectangle.
	 */
	public int getLeft() { return x; }
	/*
	 * Gets the right side of the Rectangle.
	 */
	public int getRight() { return x + width; }
}
