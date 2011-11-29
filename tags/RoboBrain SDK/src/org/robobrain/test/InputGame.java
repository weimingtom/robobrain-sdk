package org.robobrain.test;

import org.robobrain.sdk.game.Engine;
import org.robobrain.sdk.game.World;
import org.robobrain.sdk.graphics.TextureManager;
import org.robobrain.sdk.input.Accelerometer;
import org.robobrain.sdk.input.Multitouch;

import android.util.Log;

public class InputGame extends Engine{
	public static final int SPRITE_BAT = 1;
	private Bat bat;
	
	@Override 
	public void init() {
		super.init();
		mWorld = new World();
		
		TextureManager.registerTexture("images/bat.png", SPRITE_BAT);
		
		bat = new Bat();
		float w = ((float)mWorld.getWidth() / 2.0f);
		float h = ((float)mWorld.getHeight() / 2.0f);
		bat.setX(w);
		bat.setY(h);
		mWorld.addEntity(bat);
		Multitouch.clear();
	}
	
	@Override
	public void update(long time) {
		super.update(time);
		if (Multitouch.getState(0) == Multitouch.POINTER_DOWN) {
			bat.setX(Multitouch.getX(0));
			bat.setY(Multitouch.getY(0));
		}
		if (Multitouch.getState(0) == Multitouch.POINTER_MOVE) {
			bat.setX(Multitouch.getX(0));
			bat.setY(Multitouch.getY(0));
		}
	}
}
