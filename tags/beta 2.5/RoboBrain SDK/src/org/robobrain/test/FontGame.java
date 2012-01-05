package org.robobrain.test;

import javax.microedition.khronos.opengles.GL10;

import org.robobrain.sdk.game.Engine;
import org.robobrain.sdk.game.Entity;
import org.robobrain.sdk.game.World;
import org.robobrain.sdk.graphics.TextSprite;
import org.robobrain.sdk.graphics.Texture;
import org.robobrain.sdk.graphics.TextureManager;

public class FontGame extends Engine {
	public static final int FONT_TEXTURE = 2;
	
	@Override
	public void init() {
		TextureManager.registerTexture("images/arial.png", FONT_TEXTURE);
		
		mWorld = new World();
		TextEntity te = new TextEntity();
		te.setX(0);
		te.setY(100);
		mWorld.addEntity(te);
	}
}

class TextEntity extends Entity {
	public TextEntity() {
		Texture tex = TextureManager.getTexture(FontGame.FONT_TEXTURE);
		TextSprite ts = new TextSprite(tex);
		ts.setMessage("Hello, Fonts!");
		mRenderable = ts;
	}
	
	@Override 
	public void update(long time) {
		// Don't animate me...
	}
	
	@Override 
	public void draw(GL10 gl) {
		mRenderable.draw(gl);
	}
}
