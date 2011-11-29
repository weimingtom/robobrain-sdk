package org.robobrain.test;

import org.robobrain.sdk.GameActivity;
import org.robobrain.sdk.graphics.Color;

import android.os.Bundle;

public class FontActivity extends GameActivity {	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initRenderer(320, 480);
		setBackgroundColor(Color.BLACK);
		registerGame(new FontGame());
	}
}
