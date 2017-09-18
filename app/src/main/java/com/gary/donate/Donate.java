package com.gary.donate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.view.View.OnClickListener;


public class Donate extends AppCompatActivity {

    private Button donateButton;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    private TextView totalText;
    private int totalDonated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        //audio initialized when app is launched
        final MediaPlayer launchAudio = MediaPlayer.create(this, R.raw.launch_music);
        launchAudio.start();

        //Makes sound when button pressed but will not calculate donation???
       final MediaPlayer mp = MediaPlayer.create(this, R.raw.donate_music);
        Button buttonNoise = (Button)this.findViewById(R.id.donateButton);
        buttonNoise.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        donateButton = (Button) findViewById(R.id.donateButton);
        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        amountText = (EditText) findViewById(R.id.amountText);
        totalText = (TextView) findViewById(R.id.totalText);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);

        progressBar.setMax(10000);

        if (donateButton != null) {
            Log.v("Donate", "Really got this donate button");

        }

        String totalDonatedStr = "Total so far: €0";
        totalText.setText(totalDonatedStr);
    }

    public void donateButtonPressed(View view) {
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.payBuddy ? "PayBuddy" : "Direct";

        int donatedAmount = amountPicker.getValue();

        if (donatedAmount == 0) {
            String text = amountText.getText().toString();
            if (!text.equals("")) {
                donatedAmount = Integer.parseInt(text);
            }
        }
        if (totalDonated >= progressBar.getMax()) {
            Toast toast = Toast.makeText(this, "Doh!!! Target Exceeded!", Toast.LENGTH_SHORT);
            toast.show();
            Log.v("Donate", "Doh!!! Target Exceeded: " + totalDonated);
        } else {
            totalDonated = totalDonated + donatedAmount;
            progressBar.setProgress(totalDonated);
            Log.v("Donate", donatedAmount + " donated by " + method + "\nCurrent total " + totalDonated);

            String totalDonatedStr = "Total so far: €" + totalDonated;
            totalText.setText(totalDonatedStr);
        }
    }
}