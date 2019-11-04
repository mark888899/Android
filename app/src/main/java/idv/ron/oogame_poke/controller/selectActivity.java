package idv.ron.oogame_poke.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;



import java.util.List;

import idv.ron.oogame_poke.R;
import idv.ron.oogame_poke.model.Pokemon;

public class selectActivity extends AppCompatActivity {

    private ImageView image;
    private TextView hp;
    private TextView name;
    private TextView attack;
    private TextView defense;
    private TextView bodytype;
    private TextView attribute;
    //先傳入所有的神奇寶貝
    List<Pokemon> fieldPokemons = Pokemon.getFieldPokemons();
    //預設為第二隻(eevee)
    private Pokemon ftpokemon=fieldPokemons.get(0);;

    private int a;
    MediaPlayer mPlayer;


//{"Eevee","Corsola","Pulse"};


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
        Intent intent = this.getIntent();
        a = intent.getIntExtra("ch",0);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        image=(ImageView)findViewById(R.id.change_Pokemon);
        name=(TextView)findViewById(R.id.change_name);
        hp=(TextView)findViewById(R.id.change_hp);
        attack=(TextView)findViewById(R.id.change_attack);
        defense=(TextView)findViewById(R.id.change_defense);
        bodytype=(TextView)findViewById(R.id.change_bodytype);
        attribute=(TextView)findViewById(R.id.change_attribute);


        RadioButton radBtCorsole=(RadioButton) findViewById(R.id.radBtCorsola);
        RadioButton radBtEevee=(RadioButton) findViewById(R.id.radBtEevee);
        RadioButton radBtPulse=(RadioButton)findViewById(R.id.radBtPulse);

        radBtCorsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageResource(R.drawable.corsola);
                name.setText(R.string.textCorsola);
                hp.setText(R.string.textCorsolaHp);
                attack.setText(R.string.textCorsolaAttack);
                defense.setText(R.string.textCorsolaDefense);
                bodytype.setText(R.string.textCorsolaBodytype);
                attribute.setText(R.string.textCorsolaAttribute);
                ftpokemon = fieldPokemons.get(1);
            }
        });
        radBtEevee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageResource(R.drawable.eevee);
                name.setText(R.string.textEevee);
                hp.setText(R.string.textEeveeHp);
                attack.setText(R.string.textEeveeAttack);
                defense.setText(R.string.textEeveeDefense);
                bodytype.setText(R.string.textEeveeBodytype);
                attribute.setText(R.string.textEeveeAttribute);
                ftpokemon = fieldPokemons.get(0);
            }
        });
        radBtPulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageResource(R.drawable.pulse);
                name.setText(R.string.textPulse);
                hp.setText(R.string.textPulseHp);
                attack.setText(R.string.textPulseAttack);
                defense.setText(R.string.textPulseDefense);
                bodytype.setText(R.string.textPulseBodytype);
                attribute.setText(R.string.textPulseAttribute);
                ftpokemon=fieldPokemons.get(2);
            }
        });




        Button selectPokemon2 = (Button)findViewById(R.id.select_done);
        selectPokemon2.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (attack.getText().toString().matches("")) {
                                                 Toast toast = Toast.makeText(selectActivity.this, R.string.error2, Toast.LENGTH_LONG);
                                                 toast.show();
                                             } else {
                                                 Intent intent = new Intent();
                                                 intent.setClass(selectActivity.this, MainActivity.class);
                                                 Pokemon.getMyPokemons().add(ftpokemon);
                                                 intent.putExtra("character",a);
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
