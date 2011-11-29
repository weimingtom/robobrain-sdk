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
 * Simple holder class for Keyboard input. This class gets updated every time
 * the user taps a real or virtual key. Note: This only records the last key press.
 * It does not buffer the input, so it is not safe to use for typing.
 * @author James Johnson
 */
public class Keyboard {
	/**
	 * The Keyboard's state is unknown.
	 */
	public static final int KEY_INDETERMINATE = -1;
	/**
	 * The Keyboard has a key being held down.
	 */
	public static final int KEY_UP = 0;
	/**
	 * A key on the Keyboard has been released.
	 */
	public static final int KEY_DOWN = 1;
	
	/**
	 * Current state of the Keyboard.
	 */
	public static int state = KEY_INDETERMINATE;
	
	/**
	 * The code for key that is being affected. Use the Android KeyEvent class to
	 * determine which one. For example: 
	 * {@code}
	 * if (Keyboard.key == KeyEvent.KEYCODE_ENTER) {
	 *     ...
	 * }
	 */
	public static int key = KEY_INDETERMINATE;
}
