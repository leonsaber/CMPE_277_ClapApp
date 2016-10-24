package com.example.leonzhang.clapapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Create by Fuyao Zhang on 10/4/16
 */

public class MainActivity extends AppCompatActivity {

    ActionListener actionListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionListener = new ActionListener(MainActivity.this);
        actionListener.setOnShakeListener(new ActionListener.OnShakeListener() {
            public void onShake() {
                actionListener.stop();
                startVibrato();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        actionListener.start();
                    }
                }, 10);
            }
        });
    }

    public void startVibrato(){
        MediaPlayer player;
        player = MediaPlayer.create(this, R.raw.clapping);
        player.setLooping(false);
        player.setVolume(1,1);
        player.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (actionListener!= null) {
            actionListener.stop();
        }
    }
}
