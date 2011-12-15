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

import javax.microedition.khronos.opengles.GL10;

import org.robobrain.sdk.GLRenderer;
import org.robobrain.sdk.graphics.Rectangle;
import org.robobrain.sdk.graphics.Renderable;

import android.util.Log;

/**
 * The base game Entity class. Derive all of your game objects from this class.
 * Things like the player, enemies, bullets, etc.
 * @author James Johnson
 *
 */
public class Entity {
	/**
	 * The Entity's velocity along the X axis.
	 */
	public float vx;
	
	/**
	 * The Entity's velocity along the Y axis.
	 */
	public float vy;
	
	/**
	 * The Entity's heading (direction, facing) along the X axis.
	 */
	public float dx;
	
	/**
	 * The Entity's heading (direction, facing) along the Y axis.
	 */
	public float dy;
	
	/**
	 * The Entity's internal Renderable object. Usually a Sprite.
	 */
	protected Renderable mRenderable;
	
	/**
	 * The Entity's speed. Used to calculate velocity.
	 */
	protected float mSpeed;
	
	/**
	 * The amount of friction to apply to the Entity each frame.
	 */
	protected float mFriction;
	
	/**
	 * A user created code to represent an Entity's type. For example: A
	 * spaceship, a missile, sword or enemy type.
	 */
	public int type;
	
	/**
	 * Used by the World to determine when to remove this Entity.
	 */
	public boolean remove;
	
	public Entity() {
		vx = 0;
		vy = 0;
		dx = 0;
		dy = 0;
		mRenderable = null;
		mSpeed = 1;
		mFriction = 0.8f;
		type = 0;
	}
	
	/**
	 * Updates the Entity and performs physics calculations, AI, etc. 
	 * Called once per frame.
	 * @param time
	 * The amount of time elapsed since the last frame.
	 */
	public void update(long time) {
		vx = dx * (mSpeed * GLRenderer.getScale());
		vy = dy * (mSpeed * GLRenderer.getScale());
		vx *= mFriction;
		vy *= mFriction;
		mRenderable.x += vx * time;
		mRenderable.y += vy * time;
		mRenderable.update(time);
	}
	
	/**
	 * Called when the Entity collides with another Entity.
	 * @param sender
	 * The Entity this one has collided with.
	 */
	public void onCollision(Entity sender) {
		//Log.d("Collision", sender.toString());
	}
	
	/**
	 * Called when the Entity has gone off screen.
	 * @param width
	 * The width of the screen in pixels.
	 * @param height
	 * The height of the screen in pixels.
	 */
	public void onBounds(int width, int height) {
		
	}
	
	/**
	 * Removes the Entity from the World.
	 */
	public void kill() {
		remove = true;
	}
	
	/**
	 * Sets the Entity's position along the X axis.
	 * @param x
	 * The Entity's position along the X axis.
	 */
	public void setX(float x) {
		mRenderable.x = x;
	}
	
	/**
	 * Gets the Entity's position along the X axis.
	 * @param x
	 * The Entity's position along the X axis.
	 */
	public float getX() {
		return mRenderable.x;
	}
	
	/**
	 * Sets the Entity's position along the Y axis.
	 * @param y
	 * The Entity's position along the Y axis.
	 */
	public void setY(float y) {
		mRenderable.y = y;
	}
	
	/**
	 * Gets the Entity's position along the Y axis.
	 * @param y
	 * The Entity's position along the Y axis.
	 */
	public float getY() {
		return mRenderable.y;
	}
	
	/**
	 * Gets the Entity's width.
	 * @return
	 * The Entity's width in pixels.
	 */
	public int getWidth() {
		return mRenderable.getWidth();
	}
	
	/**
	 * Gets the Entity's height.
	 * @return
	 * The Entity's height in pixels.
	 */
	public int getHeight() {
		return mRenderable.getHeight();
	}
	
	/**
	 * Gets the angle the Entity has been rotated.
	 * @return
	 * The angle of rotation. 0 = none, 360 = completely turned around.
	 */
	public float getRotation() {
		return mRenderable.rotation;
	}
	
	/**
	 * Rotates the Entity to a specific angle. 
	 * @param angle
	 * The angle of rotation. 0 = none, 360 = completely turned around.
	 */
	public void setRotation(float angle) {
		mRenderable.rotation = angle;
	}
	
	/**
	 * Gets the Entity's scale.
	 * @return
	 * The amount the Entity has been scaled. 1 = normal size.
	 */
	public float getScale() {
		return mRenderable.scale;
	}
	
	/**
	 * Scales the Entity.
	 * @param angle
	 * The amount to scale the Entity by. 1 = normal size.
	 */
	public void setScale(float angle) {
		mRenderable.scale = angle;
	}
	
	/**
	 * Gets the Entity's speed. Multiply this by its direction and the 
	 * amount time elapsed in one frame to calculate the Entity's velocity.
	 * @return
	 * The Entity's speed.
	 */
	public float getSpeed() {
		return mSpeed;
	}
	
	/**
	 * Gets the Entity's bounding Rectangle.
	 * @return
	 * The Entity's bounding Rectangle.
	 */
	public Rectangle getRect() {
		return new Rectangle((int)(mRenderable.x - mRenderable.getHalfWidth()),
							 (int)(mRenderable.y - mRenderable.getHalfHeight()),
							 mRenderable.getWidth(),
							 mRenderable.getHeight());
	}
	
	/**
	 * Gets the Entity's collision Rectangle.
	 * @return
	 * The Entity's collision Rectangle.
	 */
	public Rectangle getCollisionRect() {
		return getRect();
	}
	
	/**
	 * Determines if the Entity has collided with another Entity.
	 * @param receiver	the Entity to test against.
	 * @return
	 * true if a collision occurred. 
	 */
	public boolean hasCollided(Entity receiver) {
		Rectangle rect = getCollisionRect();
		if (rect.intersects(receiver.getCollisionRect())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the Renderable object used to represent the Entity. 
	 * Usually a Sprite.
	 * @return
	 * The Renderable object used to represent the Entity.
	 */
	public Renderable getRenderable() {
		return mRenderable;
	}
	
	/**
	 * Draws the Entity's Renderable to the screen.
	 * @param gl
	 * A valid OpenGL ES 1.0 object.
	 */
	public void draw(GL10 gl) {
		mRenderable.draw(gl);
	}
	
	// TODO: Add kill() function

}
