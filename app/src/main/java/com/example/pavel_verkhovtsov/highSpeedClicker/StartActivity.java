package com.example.pavel_verkhovtsov.highSpeedClicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class StartActivity extends Activity {

    private Button startButton;
    private TextView highScoreTextView;
    SharedPreferences sharedPref;
    public static int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startButton = (Button) findViewById(R.id.startButton);
        highScoreTextView = (TextView) findViewById(R.id.highScope_text);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);
        highScoreTextView.setText(getString(R.string.high_score_text) + highScore);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        long highScore = sharedPref.getInt(getString(R.string.saved_high_score), 0);
        if (score > highScore) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.saved_high_score), score);
            editor.commit();
            highScore = score;
        }
        highScoreTextView.setText(getString(R.string.high_score_text) + highScore);


    }

}
