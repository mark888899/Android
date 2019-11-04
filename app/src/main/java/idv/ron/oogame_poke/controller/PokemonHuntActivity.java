package idv.ron.oogame_poke.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




import idv.ron.oogame_poke.R;
import idv.ron.oogame_poke.model.Pokemon;

// 我的寶可夢捕捉頁面
public class PokemonHuntActivity extends AppCompatActivity {
    private Pokemon fieldPokemon, myPokemon;
    private ImageView ivFieldPokemon, ivMyPokemon;
    private TextView tvResult;
    private Toast toast;
    private boolean myTurn = true;
    private TextView MyHp,EmemyHp,enemyname;
    private int myhp,enemyhp;
    private String myHp,enemyHp;
    MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SysApplication.getInstance().addActivity(this);
        try {
            mPlayer = MediaPlayer.create(this,R.raw.musicbattle);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


            mPlayer.setLooping(true);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pokemon_hunt);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fieldPokemon = (Pokemon) bundle.getSerializable("fieldPokemon");
            myPokemon = (Pokemon) bundle.getSerializable("myPokemon");
        }
        String title = String.format(
                getString(R.string.textPokemonHunt),
                myPokemon.getName(), fieldPokemon.getName());
        setTitle(title);
        handleViews();
    }

    private void handleViews() {
        tvResult = findViewById(R.id.tvResult);

        ivFieldPokemon = findViewById(R.id.ivFieldPokemon);
        ivFieldPokemon.setImageResource(fieldPokemon.getImage());

        ivMyPokemon = findViewById(R.id.ivMyPokemon);
        ivMyPokemon.setImageResource(myPokemon.getImage());

        enemyname=findViewById(R.id.enemyname);
        enemyname.setText(fieldPokemon.getName());

        MyHp=findViewById(R.id.hunt_myhp);
        myhp=myPokemon.getHp();
        myHp=Integer.toString(myhp);
        MyHp.setText(myHp);

        EmemyHp=findViewById(R.id.hunt_enemyhp);
        enemyhp=fieldPokemon.getHp();
        enemyHp=Integer.toString(enemyhp);
        EmemyHp.setText(enemyHp);


        Button attack1=findViewById(R.id.attack1);
        Button attack2=findViewById(R.id.attack2);

        attack1.setText(myPokemon.getFastMoveName());
        attack2.setText(myPokemon.getSecondMoveName());

        attack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = myPokemon.attackResult(fieldPokemon, myPokemon.getFastMove());
                tvResult.append(text + "\n");
                attackAnimation(myTurn, ivMyPokemon, ivFieldPokemon);



                enemyhp=fieldPokemon.getHp();
                enemyHp=Integer.toString(enemyhp);
                EmemyHp.setText(enemyHp);


                // 野生寶可夢沒有HP就結束，回到第一頁（MainActivity）
                if (fieldPokemon.getHp() <= 0) {
                    catchResult(true);
                    // 將我的寶可夢設為disable，避免user繼續點擊攻擊
                    ivMyPokemon.setEnabled(false);
                    return;
                }
                text = fieldPokemon.attackResult(myPokemon, fieldPokemon.getFastMove());
                tvResult.append(text + "\n");
                attackAnimation(myTurn, ivMyPokemon, ivFieldPokemon);
                if (myPokemon.getHp() <= 0) {
                    catchResult(false);
                }
                myhp=myPokemon.getHp();
                myHp=Integer.toString(myhp);
                MyHp.setText(myHp);
            }
        });

        attack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = myPokemon.attackResult(fieldPokemon, myPokemon.getSecondMove());
                tvResult.append(text + "\n");
                attackAnimation(myTurn, ivMyPokemon, ivFieldPokemon);


                enemyhp=fieldPokemon.getHp();
                enemyHp=Integer.toString(enemyhp);
                EmemyHp.setText(enemyHp);


                // 野生寶可夢沒有HP就結束，回到第一頁（MainActivity）
                if (fieldPokemon.getHp() <= 0) {
                    catchResult(true);
                    // 將我的寶可夢設為disable，避免user繼續點擊攻擊
                    ivMyPokemon.setEnabled(false);
                    return;
                }
                text = fieldPokemon.attackResult(myPokemon, fieldPokemon.getSecondMove());
                tvResult.append(text + "\n");
                attackAnimation(myTurn, ivMyPokemon, ivFieldPokemon);
                if (myPokemon.getHp() <= 0) {
                    catchResult(false);
                }

                myhp=myPokemon.getHp();
                myHp=Integer.toString(myhp);
                MyHp.setText(myHp);
            }
        });



    }

    /**
     * 設定攻擊與防禦寶可夢的動畫
     * @param myTurn 是否輪到我的寶可夢攻擊
     * @param myPokeView 我的寶可夢的ImageView
     * @param fieldPokeView 野生寶可夢的ImageView
     */
    private void attackAnimation(boolean myTurn, View myPokeView, View fieldPokeView) {
        // 設定攻擊寶可夢的動畫
        if (myTurn) {
            Animation attackerAnimation = getTranslateAnimation(-1);
            myPokeView.startAnimation(attackerAnimation);
            Animation defenderAnimation = getShakeAnimation();
            fieldPokeView.startAnimation(defenderAnimation);
        } else {
            Animation attackerAnimation = getTranslateAnimation(1);
            fieldPokeView.startAnimation(attackerAnimation);
            Animation defenderAnimation = getShakeAnimation();
            myPokeView.startAnimation(defenderAnimation);
        }
    }

    /**
     * 捕捉結果
     * @param catchSuccess 是否捕捉成功
     */
    private void catchResult(boolean catchSuccess) {
        if (catchSuccess) {
            // 抓到寶可夢，先將該寶可夢的HP加滿後再存入我的百寶箱內
            fieldPokemon.setHp(fieldPokemon.getFullhp());
            Pokemon.getMyPokemons().add(fieldPokemon);
            // 晃動寶可夢的ImageView代表被有效攻擊
            Animation shakeAnimation = getShakeAnimation();
            shakeAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // 當晃動完畢後寶可夢變成透明（代表被抓而消失了）
                    Animation alphaAnimation = getAlphaAnimation();
                    alphaAnimation.setFillAfter(true);
                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        // 當ImageView變透明後，Toast「抓到xxx」並關閉此頁回到MainActivity
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (toast != null) {
                                toast.cancel();
                            }
                            String text = String.format(
                                    getString(R.string.textPokemonCaught), fieldPokemon.getName());
                            toast = Toast.makeText(PokemonHuntActivity.this,
                                    text, Toast.LENGTH_SHORT);
                            toast.setGravity(
                                    Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                                    -200, 0);
                            toast.show();

                            finish();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    ivFieldPokemon.startAnimation(alphaAnimation);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            ivFieldPokemon.startAnimation(shakeAnimation);
        } else {
            if (toast != null) {
                toast.cancel();
            }
            String text = getString(R.string.textPokemonRunAway);
            toast = Toast.makeText(PokemonHuntActivity.this, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                    200, 0);
            toast.show();
            finish();
        }
    }


    private TranslateAnimation getTranslateAnimation(int direction) {
        // 設定移動方向與距離（100像素）
        int distance = direction * 100;
        long duration = 100;
        TranslateAnimation translateAnimation;
        translateAnimation = new TranslateAnimation(0, 0, 0, distance);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    private TranslateAnimation getShakeAnimation() {
        TranslateAnimation shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
        shakeAnimation.setStartOffset(200);
        shakeAnimation.setDuration(500);
        CycleInterpolator cycleInterpolator = new CycleInterpolator(5);
        shakeAnimation.setInterpolator(cycleInterpolator);
        return shakeAnimation;
    }

    private AlphaAnimation getAlphaAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(300);
        return alphaAnimation;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder ad = new AlertDialog.Builder(PokemonHuntActivity.this);
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