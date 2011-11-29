package org.robobrain.test;

import org.robobrain.sdk.GameActivity;
import org.robobrain.sdk.graphics.Color;

import android.os.Bundle;

public class SpriteActivity extends GameActivity {	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initRenderer(480, 800);
		setBackgroundColor(Color.GRAY);
		registerGame(new SpriteGame());
	}
}
