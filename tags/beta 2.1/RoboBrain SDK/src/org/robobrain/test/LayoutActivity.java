package org.robobrain.test;

import org.robobrain.sdk.GLView;
import org.robobrain.sdk.GameActivity;
import org.robobrain.sdk.graphics.Color;

import android.app.Activity;
import android.os.Bundle;

public class LayoutActivity extends GameActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glview);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mGLView = (GLView)findViewById(R.id.renderer);
		if (mGLView != null) {
			setBackgroundColor(Color.GREEN);
			registerGame(new SpriteGame());
		}
	}
}
