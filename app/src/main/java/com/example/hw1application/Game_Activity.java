package com.example.hw1application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import com.bumptech.glide.Glide;

public class Game_Activity extends AppCompatActivity {

    private ShapeableImageView game_IMG_background;
    private ShapeableImageView[] game_IMG_allhearts;
    private ShapeableImageView[][] game_IMG_allpafs;
    private ShapeableImageView[] game_IMG_alloscars;
    private ExtendedFloatingActionButton game_BTN_left;
    private ExtendedFloatingActionButton game_BTN_right;

    final int DELAY=1000;
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, DELAY);
            movePafs();
            refreshUI();
        }
    };

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    Game_Manager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        findViews();

        Glide
                .with(Game_Activity.this)
                .load("https://cdn3.vectorstock.com/i/1000x1000/00/17/underwater-landscape-the-ocean-and-the-undersea-vector-10230017.jpg")
                .into(game_IMG_background);

        initViews();
        gameManager = new Game_Manager(
                game_IMG_allhearts.length,
                game_IMG_allpafs.length,
                game_IMG_alloscars.length);

        initGame();
        startTimer();
    }


    private void movePafs() {
        for(int i = 0; i<gameManager.getNumber_of_columns(); i++){
            if(gameManager.isInArray(gameManager.getPafsIndex()[i]))
                game_IMG_allpafs[gameManager.getPafsIndex()[i]][i].setVisibility(View.INVISIBLE);
        }

        gameManager.movePafs();

        for(int i = 0; i<gameManager.getNumber_of_columns(); i++)
        {
            if(gameManager.isInArray(gameManager.getPafsIndex()[i]))
                game_IMG_allpafs[gameManager.getPafsIndex()[i]][i].setVisibility(View.VISIBLE);

        }

    }

    private void initGame(){
        for(int i = 0; i< game_IMG_alloscars.length; i++){
           if(i!= gameManager.getOscarIndex())
                game_IMG_alloscars[i].setVisibility(View.INVISIBLE);
        }



        for(int i = 0; i< game_IMG_allpafs.length; i++){
            for(int j = 0; j< game_IMG_allpafs[0].length; j++)
                game_IMG_allpafs[i][j].setVisibility(View.INVISIBLE);
        }
    }

    private void initViews() {

        game_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked(false);
            }
        });

        game_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked(true);
            }
        });
    }

    private void clicked(boolean direction) {
        game_IMG_alloscars[gameManager.getOscarIndex()].setVisibility(View.INVISIBLE);
        gameManager.moveOscars(direction);
        game_IMG_alloscars[gameManager.getOscarIndex()].setVisibility(View.VISIBLE);

        refreshUI();
    }

    private void refreshUI() {

        if(gameManager.isCrash()){
            game_IMG_allhearts[gameManager.getLife()].setVisibility(View.INVISIBLE);

                vibrate();
                if(gameManager.getLife()!=0)
                    Toast
                            .makeText(this, "WTF man", Toast.LENGTH_SHORT)
                            .show();

        }

        if(gameManager.isGameOver()){
            Toast
                    .makeText(this, "Game Over!!!", Toast.LENGTH_SHORT)
                    .show();
            stopTimer();
            //TODO delay
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, DELAY+1500);

        }
    }

    private void startTimer(){
        handler.postDelayed(runnable,DELAY);
    }

    private void stopTimer(){
        handler.removeCallbacks(runnable);
    }

    private void findViews() {

        game_IMG_background = findViewById(R.id.game_IMG_background);
        game_IMG_allhearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)};

        game_IMG_allpafs = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_obstaclepirane1),
                        findViewById(R.id.game_IMG_obstaclepirane2),
                        findViewById(R.id.game_IMG_obstaclepirane3)},
                {findViewById(R.id.game_IMG_obstaclepirane4),
                        findViewById(R.id.game_IMG_obstaclepirane5),
                        findViewById(R.id.game_IMG_obstaclepirane6)},
                {findViewById(R.id.game_IMG_obstaclepirane7),
                        findViewById(R.id.game_IMG_obstaclepirane8),
                        findViewById(R.id.game_IMG_obstaclepirane9)}
        };

        game_IMG_alloscars = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_oscar1),
                findViewById(R.id.game_IMG_oscar2),
                findViewById(R.id.game_IMG_oscar3)
        };

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTM_right);

    }

}