package com.example.pavel_verkhovtsov.highSpeedClicker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.view.View.OnClickListener;

//TODO Save best score
//TODO Check Russian localization
public class GameActivity extends Activity {

    private final int buttonCount = 6;
    private final int secondPerTurn = 5;

    private static TextView timerTextView;
    private TextView needClickButtonTextView;

    private Button[] buttons;

    private int needClickButton;
    private int score;

    private Random rand = new Random();

    private static CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        needClickButtonTextView = (TextView) findViewById(R.id.needClickTextView);

        fillButtonsArray();
        OnClickListener onClickListener = createGameOnClickListener();

        for (Button button : buttons)
            button.setOnClickListener(onClickListener);

        if (savedInstanceState == null) {
            timer = new CountDownTimer((secondPerTurn+1) * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    gameOver();
                }
            };

            generateTurnData();
        }
    }

    @Override
    public void onBackPressed() {
       gameOver();
    }

    private OnClickListener createGameOnClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                int realClickedButton = Integer.parseInt((String) ((TextView) v).getText());
                if (needClickButton == realClickedButton)
                    nextTurn();
                else {
                    gameOver();
                }
            }
        };
    }

    private void fillButtonsArray() {
        LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        buttons = new Button[buttonCount];
        for(int i=0; i<buttonCount; i++) {
            Button tButton = new Button(getApplicationContext());
            tButton.setText(String.valueOf(i+1));
            buttonsLayout.addView(tButton);
            buttons[i]=tButton;
        }
    }

    private void nextTurn() {
        score++;
        generateTurnData();
    }

    private void generateTurnData() {
        needClickButton = rand.nextInt(buttonCount) + 1;
        needClickButtonTextView.setText(getString(R.string.click_to) + needClickButton);
        timer.start();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("timer", timerTextView.getText().toString());
        outState.putString("needClickText", needClickButtonTextView.getText().toString());
        outState.putInt("score", score);
        outState.putInt("needClickInt", needClickButton);

    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        timerTextView.setText(bundle.getString("timer"));
        needClickButtonTextView.setText(bundle.getString("needClickText"));
        score = bundle.getInt("score");
        needClickButton = bundle.getInt("needClickInt");
    }

    public void gameOver() {
        Toast.makeText(GameActivity.this, String.format(getString(R.string.game_over_text), score), Toast.LENGTH_LONG).show();
        timer.cancel();
        StartActivity.score = score;
        finish();
    }
}
