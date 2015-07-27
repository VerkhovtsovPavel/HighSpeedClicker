package com.example.pavel_verkhovtsov.highSpeedClicker;

import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by Pavel_Verkhovtsov on 7/27/2015.
 */
public class UpdateTimeTask extends TimerTask {

    private TextView targetTextView;

    public UpdateTimeTask(TextView textView){
        this.targetTextView = textView;
    }

    public void run() {

    }
}
