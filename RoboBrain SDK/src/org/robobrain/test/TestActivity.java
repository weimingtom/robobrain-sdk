package org.robobrain.test;

import org.robobrain.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onSoundExampleClick(View view) {
    	startActivity(new Intent(this, SoundActivity.class));
    }
    
    public void onSpriteExampleClick(View view) {
    	startActivity(new Intent(this, SpriteActivity.class));
    }
    
    public void onInputExampleClick(View view) {
    	startActivity(new Intent(this, InputActivity.class));
    }
    
    public void onMusicExampleClick(View view) {
    	startActivity(new Intent(this, MusicActivity.class));
    }
    
    public void onFontExampleClick(View view) {
    	startActivity(new Intent(this, FontActivity.class));
    }
    
    public void onLayoutExampleClick(View view) {
    	startActivity(new Intent(this, LayoutActivity.class));
    }
}