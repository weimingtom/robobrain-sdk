package org.robobrain.test;

import org.robobrain.sdk.game.Engine;
import org.robobrain.sdk.game.Entity;
import org.robobrain.sdk.game.World;
import org.robobrain.sdk.graphics.SimpleSprite;
import org.robobrain.sdk.graphics.Sprite;
import org.robobrain.sdk.graphics.Texture;
import org.robobrain.sdk.graphics.TextureManager;
import org.robobrain.sdk.input.Keyboard;
import org.robobrain.sdk.input.Multitouch;

import android.view.KeyEvent;

public class SpriteGame extends Engine {
	public static final int SPRITE_BAT = 1;
	
	@Override 
	public void init() {
		super.init();
		mWorld = new World();
		
		TextureManager.registerTexture("images/bat.png", SPRITE_BAT);
		
		// Randomly draw 100 sprites
		for (int i = 0; i < 100; i++) {
			Bat bat = new Bat();
			float w = ((float)Math.random() * (float)mWorld.getWidth());
			float h = ((float)Math.random() * (float)mWorld.getHeight());
			bat.setX(w);
			bat.setY(h);
			mWorld.addEntity(bat);
		}
	}
}

class Bat extends Entity {
	public Bat() {
		super();
		Texture t = TextureManager.getTexture(SpriteGame.SPRITE_BAT);
		Sprite s = new Sprite(t, 64, 64, 4);
		mRenderable = s;
		mSpeed = 0.5f;
	}
}
