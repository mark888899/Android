package idv.ron.oogame_poke.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.media.AudioManager;
import android.media.MediaPlayer;

import idv.ron.oogame_poke.R;

public class CoverActivity extends AppCompatActivity {
    MediaPlayer mPlayer;
    protected void onCreate(Bundle savedInstanceState) {
        SysApplication.getInstance().addActivity(this);
        try {
            mPlayer = MediaPlayer.create(this,R.raw.musicinfo);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mPlayer.setLooping(true);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        String title = getString(R.string.covermap);
        setTitle(title);
    }
    @Override
    protected void onResume()
    {
// TODO Auto-generated method stub
        super.onResume();
        mPlayer.start();
    }


    @Override
    protected void onPause()
    {
// TODO Auto-generated method stub


        super.onPause();
        mPlayer.pause();
    }


    @Override
    protected void onDestroy()
    {
// TODO Auto-generated method stub
        super.onDestroy();
        mPlayer.release();
    }
}


