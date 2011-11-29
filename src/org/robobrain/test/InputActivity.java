package org.robobrain.test;

import org.robobrain.sdk.GameActivity;
import org.robobrain.sdk.game.Entity;
import org.robobrain.sdk.graphics.Color;
import org.robobrain.sdk.graphics.Sprite;
import org.robobrain.sdk.graphics.Texture;
import org.robobrain.sdk.graphics.TextureManager;

import android.os.Bundle;
import android.widget.TextView;

public class InputActivity extends GameActivity {    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initRenderer(480, 800);
        setBackgroundColor(Color.GRAY);
        registerGame(new InputGame());
    }
	
}