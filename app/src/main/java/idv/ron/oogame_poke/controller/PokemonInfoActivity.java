package idv.ron.oogame_poke.controller;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import idv.ron.oogame_poke.R;
import idv.ron.oogame_poke.model.Pokemon;
import idv.ron.oogame_poke.model.skill.Move;

public class PokemonInfoActivity extends AppCompatActivity {
    private Pokemon myPK;
    private ImageView imagemyPokemon;
    private TextView name, bodytype, attribute, hp, attack, defense;
    private TextView firstMove, secondMove, ChargeMove;
    MediaPlayer mPlayer;


    protected void onCreate(Bundle savedInstanceState) {
        try {
            mPlayer = MediaPlayer.create(this,R.raw.musicinfo);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mPlayer.setLooping(true);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SysApplication.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myPK = (Pokemon) bundle.getSerializable("my");
        }
        String title = String.format(getString(R.string.textPokemonInfo), myPK.getName());
        setTitle(title);
        handleViews();

        Button selectPokemon2 = (Button) findViewById(R.id.linkboutton);
        selectPokemon2.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Intent intent = new Intent();
                                                  intent.setClass(PokemonInfoActivity.this, CoverActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
        );

    }

    private void handleViews() {
        imagemyPokemon = findViewById(R.id.pokemonimg);//改照片
        name = (TextView) findViewById(R.id.layout_info_name);
        hp = (TextView) findViewById(R.id.layout_info_hp);
        attack = (TextView) findViewById(R.id.layout_info_attack);
        defense = (TextView) findViewById(R.id.layout_info_defense);
        bodytype = (TextView) findViewById(R.id.layout_info_bodytype);
        attribute = (TextView) findViewById(R.id.layout_info_attribute);

        int a;

        imagemyPokemon.setImageResource(myPK.getImage());
        name.setText(myPK.getName());
        a = myPK.getHp();
        hp.setText(Integer.toString(a));
        a = myPK.getAttack();
        attack.setText(Integer.toString(a));
        a = myPK.getDefense();
        defense.setText(Integer.toString(a));
        bodytype.setText(myPK.getBodytupeText());
        attribute.setText(myPK.getAttributeText());

        setskill();
    }

    private void setskill() {
        firstMove = findViewById(R.id.layout_info_first);
        secondMove = findViewById(R.id.layout_info_second);
        ChargeMove = findViewById(R.id.layout_info_charge);
        String attackname, attack, sp;
        int a;


        Move move1 = myPK.getFastMove();
        attackname = move1.getName();
        a = move1.getPower();
        attack = Integer.toString(a);
        a = move1.getSp();
        sp = Integer.toString(a);
        String s = String.format(getString(R.string.textPokemonMove), attackname, attack, sp);
        firstMove.setText(s);

        Move move2 = myPK.getSecondMove();
        attackname = move2.getName();
        a = move2.getPower();
        attack = Integer.toString(a);
        a = move2.getSp();
        sp = Integer.toString(a);
        String s2 = String.format(getString(R.string.textPokemonMove), attackname, attack, sp);
        secondMove.setText(s2);

        Move move3 = myPK.getChargeMove();
        attackname = move3.getName();
        a = move3.getPower();
        attack = Integer.toString(a);
        a = move3.getSp();
        sp = Integer.toString(a);
        String s3 = String.format(getString(R.string.textPokemonCharge), attackname, attack, sp);
        ChargeMove.setText(s3);
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