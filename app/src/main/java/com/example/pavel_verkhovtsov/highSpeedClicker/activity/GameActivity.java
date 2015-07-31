package com.example.pavel_verkhovtsov.highSpeedClicker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel_verkhovtsov.highSpeedClicker.R;
import com.example.pavel_verkhovtsov.highSpeedClicker.add.GameTypes;

import java.util.ArrayList;
import java.util.Random;


import static android.view.View.OnClickListener;

//TODO Check Russian localization
//TODO High scope not always saving
public class GameActivity extends Activity {

    private final int buttonCount = 6;
    private final int secondPerTurn = 55;

    private static TextView timerTextView;
    private TextView needClickButtonTextView;

    private Button[] buttons;

    private int needClickButton;
    private int score;

    private Random rand = new Random();

    private static CountDownTimer timer;

    private GameTypes type;

    private ArrayList<Integer> colors = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        needClickButtonTextView = (TextView) findViewById(R.id.needClickTextView);

        type = GameTypes.valueOf(getIntent().getExtras().getString("mode"));

        fillButtonsArray();
        OnClickListener onClickListener = createGameOnClickListener();

        for (Button button : buttons)
            button.setOnClickListener(onClickListener);

        if (savedInstanceState == null) {
            timer = new CountDownTimer((secondPerTurn + 1) * 1000, 1000) {
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
                if (checkAnswer(v))
                    nextTurn();
                else {
                    gameOver();
                }
            }
        };
    }

    private boolean checkAnswer(View v) {
        switch (type) {
            case NUMBERTYPE:
                int realClickedButton = Integer.parseInt((String) ((TextView) v).getText());
                return needClickButton == realClickedButton;
            case COLORTYPE:
                int realColor = ((ColorDrawable) v.getBackground()).getColor();
                return realColor == colors.get(needClickButton - 1);
        }
        return false;
    }

    private void fillButtonsArray() {
        LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        buttons = new Button[buttonCount];
        for (int i = 0; i < buttonCount; i++) {
            Button tButton = createButtonByMode(i);

            buttonsLayout.addView(tButton);
            buttons[i] = tButton;
        }
    }


    private void nextTurn() {
        score++;
        generateTurnData();
    }

    private void generateTurnData() {
        needClickButton = rand.nextInt(buttonCount) + 1;
        switch (type) {
            case NUMBERTYPE:
                needClickButtonTextView.setText(getString(R.string.click_to) + needClickButton);
                break;
            case COLORTYPE:
                needClickButtonTextView.setTextColor(colors.get(needClickButton - 1));
                break;
        }
        timer.start();
    }

    private Button createButtonByMode(int number) {
        Button surrogateButton = new Button(getApplicationContext());

        switch (type) {
            case COLORTYPE:
                int color = rand.nextInt();
                surrogateButton.setBackgroundColor(color);
                colors.add(color);
                break;
            case NUMBERTYPE:
                surrogateButton.setText(String.valueOf(number + 1));
                break;
        }

        return surrogateButton;
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

        if(!colors.isEmpty())
            needClickButtonTextView.setTextColor(colors.get(needClickButton-1));


    }

    public void gameOver() {
        Toast.makeText(GameActivity.this, String.format(getString(R.string.game_over_text), score), Toast.LENGTH_LONG).show();
        timer.cancel();
        StartActivity.score = score;
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
