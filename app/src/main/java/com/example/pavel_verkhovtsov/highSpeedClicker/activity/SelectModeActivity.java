package com.example.pavel_verkhovtsov.highSpeedClicker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pavel_verkhovtsov.highSpeedClicker.R;
import com.example.pavel_verkhovtsov.highSpeedClicker.add.GameTypes;

public class SelectModeActivity extends Activity {

    private Button numberModeButton;
    private Button colorModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        numberModeButton = (Button) findViewById(R.id.numberMode);
        colorModeButton = (Button) findViewById(R.id.colorMode);

        //TODO Combine listeners
        numberModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectModeActivity.this, GameActivity.class);
                intent.putExtra("mode", GameTypes.NUMBERTYPE.toString());
                startActivity(intent);
            }
        });

        colorModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectModeActivity.this, GameActivity.class);
                intent.putExtra("mode", GameTypes.COLORTYPE.toString());
                startActivity(intent);
            }
        });
    }
}
