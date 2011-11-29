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

package org.robobrain.sdk.game;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.robobrain.sdk.GLRenderer;

import android.util.Log;

/**
 * The World class manages all of the game Entities during play.
 * @author James Johnson
 *
 */
public class World extends Entity {
	protected static World sInstance;
	
	/**
	 * A list of all of the Entities in play.
	 */
	protected ArrayList<Entity> mEntities;
	
	/**
	 * Initializes the World.
	 */
	public World() {
		mEntities = new ArrayList<Entity>();
		sInstance = this;
	}
	
	/**
	 * Updates the World and all of the Entities in it. 
	 * @param time
	 * The number of milliseconds elapsed since the last frame.
	 */
	@Override
	public void update(long time) {
		int count = mEntities.size();
		for (int i = 0; i < count; i++) {
			Entity e = mEntities.get(i);
			e.update(time);
			if ((e.getX() < 0) || (e.getX() > GLRenderer.getWidth()) ||
				(e.getY() < 0) || (e.getY() > GLRenderer.getHeight())) {
				e.onBounds(GLRenderer.getWidth(), GLRenderer.getHeight());
			}
		}
		
		for (int i = 0; i < count - 1; i++) {
			for (int j = i + 1; j < count; j++) {
				Entity s = mEntities.get(i);
				Entity r = mEntities.get(j);
				if (s.getRect().intersects(r.getRect())) {
					s.onCollision(r);
					r.onCollision(s);
				}
			}
		}
	}
	
	public void render(GL10 gl) {
		int c = mEntities.size();
		for (int i = 0; i < c; i++) {
			gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
			Entity e = mEntities.get(i);
			e.draw(gl);
		}
	}
	
	/**
	 * Adds an Entity to the World.
	 * @param entity
	 * The Entity to be added.
	 */
	public void addEntity(Entity entity) {
		if (entity == null) {
			Log.w("World", "Null Enity passed to addEntity().");
			return;
		}
		mEntities.add(entity);
	}
	
	/**
	 * Converts the World's internal list of Entities into an array.
	 * @return
	 * An array of Entities.
	 */
	public Entity[] entitiesToArray() {
		if (mEntities == null) {
			return null;
		}
		Entity[] ents = new Entity[mEntities.size()];
		ents = mEntities.toArray(ents);
		return ents;
	}
	
	/**
	 * Gets an instance of the World.
	 * @return
	 * An instance of the World.
	 */
	public static World getInstance() {
		return sInstance;
	}
	
	/**
	 * Gets the width of the World's playable area.
	 * @return
	 * The width of the World in pixels.
	 */
	@Override
	public int getWidth() { return GLRenderer.getWidth(); }
	
	/**
	 * Gets the height of the World's playable area.
	 * @return
	 * The height of the World in pixels.
	 */
	@Override
	public int getHeight() { return GLRenderer.getHeight(); }
}
