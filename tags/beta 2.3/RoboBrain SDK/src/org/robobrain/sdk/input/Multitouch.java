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

package org.robobrain.sdk.input;

/**
 * Simple holder class for Multitouch input. This class gets updated every time
 * the user touches the screen. It can track up to 10 different pointers. (Fingers.)
 * @author James Johnson
 */
public class Multitouch {
	/**
	 * The maximum number of pointers being tracked.
	 */
	public static final int MAX_POINTERS = 10;
	
	/**
	 * The pointer is in an unknown state.
	 */
	public static final int POINTER_INVALID = -1;
	
	/**
	 * The pointer is touching the screen.
	 */
	public static final int POINTER_DOWN = 0;
	
	/**
	 * The pointer was removed from the screen.
	 */
	public static final int POINTER_UP = 1;
	
	/**
	 * The pointer was moved.
	 */
	public static final int POINTER_MOVE = 2;
	
	private static int mState[] = new int[MAX_POINTERS];
	private static float mX[] = new float[MAX_POINTERS];
	private static float mY[] = new float[MAX_POINTERS];
	
	/**
	 * Get the state of a pointer.
	 * @param pointer
	 * The index of the pointer to be queried. Ranges from 0 - (MAX_POINTERS - 1).
	 * @return
	 * The state the pointer is in.
	 */
	public static int getState(final int pointer) {
		synchronized (mState) {
			if ((pointer < 0) || (pointer > MAX_POINTERS - 1)) {
				return POINTER_INVALID;
			}
			return mState[pointer];
		}
	}
	
	/**
	 * Sets the value of a pointer. Not generally used by the game.
	 */
	public static void setState(final int pointer, final int value) {
		synchronized (mState) {
			if ((pointer < 0) || (pointer > MAX_POINTERS - 1)) {
				return;
			}
			mState[pointer] = value;
		}
	}
	
	/**
	 * Gets the position of a pointer along the X axis.
	 * @param pointer
	 * The index of the pointer to be queried. Ranges from 0 - (MAX_POINTERS - 1).
	 * @return
	 * The pointer's x coordinate.
	 */
	public static float getX(final int pointer) {
		synchronized (mX) {
			if ((pointer < 0) || (pointer > MAX_POINTERS - 1)) {
				return POINTER_INVALID;
			}
			return mX[pointer];
		}
	}
	
	/**
	 * Sets the x coordinate of a pointer. Not generally used by the game.
	 */
	public static void setX(final int pointer, final float value) {
		synchronized (mX) {
			if ((pointer < 0) || (pointer > MAX_POINTERS - 1)) {
				return;
			}
			mX[pointer] = value;
		}
	}
	
	/**
	 * Gets the position of a pointer along the Y axis.
	 * @param pointer
	 * The index of the pointer to be queried. Ranges from 0 - (MAX_POINTERS - 1).
	 * @return
	 * The pointer's y coordinate.
	 */
	public static float getY(final int pointer) {
		synchronized (mY) {
			if ((pointer < 0) || (pointer > MAX_POINTERS - 1)) {
				return POINTER_INVALID;
			}
			return mY[pointer];
		}
	}
	
	/**
	 * Sets the y coordinate of a pointer. Not generally used by the game.
	 */
	public static void setY(final int pointer, final float value) {
		synchronized (mY) {
			if ((pointer < 0) || (pointer > MAX_POINTERS - 1)) {
				return;
			}
			mY[pointer] = value;
		}
	}
	
	/**
	 * Clears all of the pointer data. Not generally used by the game.
	 */
	public static synchronized void clear() {
		for (int i = 0; i < MAX_POINTERS; i++) {
			mState[i] = POINTER_INVALID;
			mX[i] = 0.0f;
			mY[i] = 0.0f;
		}
	}
}
