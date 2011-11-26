package org.robobrain.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.robobrain.test.R;
import org.robobrain.sdk.GameActivity;
import org.robobrain.sdk.audio.SoundManager;

// Your Activity must be derived from GameActivity for the Sound system to work.
public class SoundActivity extends GameActivity {
	final static int SND_ONE_UP = 21;
	final static int SND_COIN = 22;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound);
        
        SoundManager.registerSound("sounds/one_up.wav", SND_ONE_UP);
        SoundManager.registerSound("sounds/coin.wav", SND_COIN);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//SoundManager.unloadAll();
		//SoundManager.release();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//SoundManager.unloadAll();
		SoundManager.release();
	}

	
	public void onPlayBtnClick(View view) {
		SoundManager.play(SND_ONE_UP);
	}
	
	public void onLoopBtnClick(View view) {
		SoundManager.play(SND_COIN, true);
	}
	
	public void onStopBtnClick(View view) {
		SoundManager.stop(SND_COIN);
	}
}
