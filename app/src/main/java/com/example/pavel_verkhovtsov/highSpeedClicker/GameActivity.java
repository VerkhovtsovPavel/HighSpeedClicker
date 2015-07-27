package com.example.pavel_verkhovtsov.highSpeedClicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;

import static android.view.View.*;


public class GameActivity extends Activity{

    private final int buttonCount = 3;

    private TextView timerTextView;
    private TextView needClickButtonTextView;

    private Button[] buttons;

    private int needClickButton;
    private int score;

    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timerTextView = (TextView) findViewById(R.id.textView);
        needClickButtonTextView = (TextView) findViewById(R.id.needClickTextView);

        fillButtonsArray();
        OnClickListener onClickListener = createGameOnClickListener();

        for(Button button : buttons)
            button.setOnClickListener(onClickListener);

        Timer timer = new Timer();
        timer.schedule(new UpdateTimeTask(timerTextView), 0, 1000);

        if(savedInstanceState==null){
            generateTurnData();
        }
    }

    private OnClickListener createGameOnClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                int realClickedButton = Integer.parseInt((String) ((TextView) v).getText());
                if (needClickButton == realClickedButton)
                    nextTurn();
                else {
                    //TODO Move to separate method
                    Toast.makeText(GameActivity.this, String.format("Game over! You score %s clicks", score), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameActivity.this, StartActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    private void fillButtonsArray() {
        buttons = new Button[buttonCount];
        buttons[0] = (Button) findViewById(R.id.button1);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
    }

    private void nextTurn() {
        score++;
        generateTurnData();
    }

    private void generateTurnData(){
        needClickButton = rand.nextInt(buttonCount) + 1;
        needClickButtonTextView.setText("Click "+ needClickButton);
    }

    //TODO Move button horizontally by turn to turn
   /* private void moveButtons()
    {
        Random rand = new Random();

        button1.setLeft(rand.nextInt(300));
    }*/


    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("timer", timerTextView.getText().toString());
        outState.putString("needClickText", needClickButtonTextView.getText().toString());
        outState.putInt("score", score);

    }

    @Override
    public void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        timerTextView.setText(bundle.getString("timer"));
        needClickButtonTextView.setText(bundle.getString("needClickText"));
        score = bundle.getInt("score");
    }
}
