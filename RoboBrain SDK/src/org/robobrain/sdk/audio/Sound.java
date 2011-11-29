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

package org.robobrain.sdk.audio;

// Encapsulates a single sound. For use with SoundManager.
class Sound implements Cloneable {
	public int id;
	public float leftVol;
	public float rightVol;
	public boolean loop;
	public int editID;
	
	public Sound(int id) {
		this.id = id;
		this.leftVol = 1.0f;
		this.rightVol = 1.0f;
		this.loop = false;
		this.editID = 0;
	}
	
	public Sound(int id, float leftVol, float rightVol, boolean loop, int otherID) {
		this.id = id;
		this.leftVol = leftVol;
		this.rightVol = rightVol;
		this.loop = loop;
		this.editID = otherID;
	}
	
	public Sound clone() {
		return new Sound(this.id, this.leftVol, this.rightVol, this.loop, this.editID);
	}
}
