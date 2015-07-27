package com.example.pavel_verkhovtsov.lesson9;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import static android.view.View.*;


public class MainActivity extends Activity{

    TextView textView;
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = null;
                switch (v.getId()) {
                    case R.id.button1:
                        textView.setText(R.string.text1);
                        toast= Toast.makeText(MainActivity.this, R.string.text1, Toast.LENGTH_LONG);
                        break;
                    case R.id.button2:
                        textView.setText(R.string.text2);
                        toast= Toast.makeText(MainActivity.this, R.string.text2, Toast.LENGTH_LONG);
                        break;
                    case R.id.button3:
                        textView.setText(R.string.text3);
                        toast= Toast.makeText(MainActivity.this, R.string.text3, Toast.LENGTH_LONG);
                }
                toast.show();
            }
        };

        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning")
                        .setMessage("This is not a button")
                        .setCancelable(false)
                        .setNegativeButton("I realized my mistake",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("tbText", textView.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle)
    {
        super.onRestoreInstanceState(bundle);
        textView.setText(bundle.getString("tbText"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
