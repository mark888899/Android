package idv.ron.oogame_poke.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

import idv.ron.oogame_poke.R;
import idv.ron.oogame_poke.model.Pokemon;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

// 首頁
public class MainActivity extends AppCompatActivity {

    private ImageView image;
    MediaPlayer mPlayer;
    protected void onCreate(Bundle savedInstanceState) {
        SysApplication.getInstance().addActivity(this);
        try {
            mPlayer = MediaPlayer.create(this,R.raw.first);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mPlayer.setLooping(true);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=this.getIntent();
        int a=intent.getIntExtra("character",0);
        image=(ImageView)findViewById(R.id.Character);

        if(a==0){
            image.setImageResource(R.drawable.boy);
        }else{
            image.setImageResource(R.drawable.girl);
        }

        handleViews();
    }

    private void handleViews() {
        // 取得野生寶可夢後將圖片顯示在ImageView上
        List<Pokemon> fieldPokemons = Pokemon.getFieldPokemons();
        List<ImageView> imageViews = new ArrayList<>();
        ImageView imagePulse = findViewById(R.id.imagePulse);
        ImageView imageVulpix = findViewById(R.id.imageVulpix);
        ImageView imageMinun = findViewById(R.id.imageMinun);
        ImageView imageMew = findViewById(R.id.imageMew);
        ImageView imageSnorlax = findViewById(R.id.imageSnorlax);
        ImageView imageWailmer = findViewById(R.id.imageWailmer);
        ImageView imageDartini=findViewById(R.id.imageDartini);
        ImageView imageSlowbro = findViewById(R.id.imageSlowbro);

        imageViews.add(imagePulse);
        imageViews.add(imageVulpix);
        imageViews.add(imageMinun);
        imageViews.add(imageMew);
        imageViews.add(imageSnorlax);
        imageViews.add(imageWailmer);
        imageViews.add(imageDartini);
        imageViews.add(imageSlowbro);


        for (int i = 0; i < imageViews.size(); i++) {
            final Pokemon fieldPokemon = fieldPokemons.get(i+2);
            imageViews.get(i).setImageResource(fieldPokemon.getImage());
            // 點擊野生寶可夢後跳出選單讓user選擇捕捉方式
            imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(fieldPokemon, view);
                }
            });
        }

    }

    private void showPopupMenu(final Pokemon fieldPokemon, View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.hunt_menu);
        // 如果沒有寶可夢，移除「使用我的寶可夢」選項，只能用精靈球來獵捕野生寶可夢
        if (Pokemon.getMyPokemons().size() == 0) {
            popupMenu.getMenu().removeItem(R.id.myPokemonHunt);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent();
                switch (item.getItemId()) {
                    // 選擇「使用我的寶可夢」會開啟百寶箱頁面供user挑選作戰用的寶可夢
                    case R.id.myPokemonHunt:
                        intent.setClass(MainActivity.this, PokemonActivity.class);
                        break;
                    // 預設是使用精靈球捕捉，會開啟精靈球捕捉頁面
                    default:
                        intent.setClass(MainActivity.this, BallHuntActivity.class);
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("fieldPokemon", fieldPokemon);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
        popupMenu.show();
    }


    // 點擊包包會開啟百寶箱
    public void onBagClick(View view) {
        Intent intent = new Intent(this, PokemonBagActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
            ad.setTitle(R.string.exit);
            ad.setMessage(R.string.exit2);
            ad.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {//退出按鈕

                @Override
                public void onClick(DialogInterface dialog, int i) {
                    SysApplication.getInstance().exit();
                }
            });
            ad.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            });
            ad.show();
        }
        return true;
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
