package idv.ron.oogame_poke.controller;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import idv.ron.oogame_poke.R;
import java.util.HashMap;
import java.util.Map;

public class StartActivity extends Activity{

    private ImageView image;
    private boolean a=true;
    private int character;
    MediaPlayer mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SysApplication.getInstance().addActivity(this);
        try
        {
            mPlayer = MediaPlayer.create(this, R.raw.pokemonbg);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mPlayer.setLooping(true);
        }
        catch (IllegalStateException e)
        {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        image = (ImageView) findViewById(R.id.selectCharacter);

        RadioButton radBtFemale = (RadioButton) findViewById(R.id.radBtFemale);
        RadioButton radBtMale = (RadioButton) findViewById(R.id.radBtMale);
        radBtFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageResource(R.drawable.girl);
                a=false;
                character=1;
            }
        });
        radBtMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageResource(R.drawable.boy);
                a=false;
                character=0;
            }
        });


        Button selectboy = (Button) findViewById(R.id.startcheck);
        selectboy.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             TextView name=(TextView)findViewById(R.id.editText);
                                             if (name.getText().toString().matches("")) {
                                                 Toast toast = Toast.makeText(StartActivity.this, "請輸入名稱!!", Toast.LENGTH_LONG);
                                                 toast.show();
                                             }else if(a) {
                                                 Toast toast = Toast.makeText(StartActivity.this, "請選擇角色", Toast.LENGTH_LONG);
                                                 toast.show();
                                             }else{

                                                     Intent intent = new Intent();
                                                     intent.putExtra("ch",character);
                                                     intent.setClass(StartActivity.this, selectActivity.class);
                                                     startActivity(intent);

                                             }
                                         }
                                     }
        );


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