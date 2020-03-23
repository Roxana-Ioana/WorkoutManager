package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.example.healthapp.Constants.ACTIVITY;
import static com.example.healthapp.Constants.DURATION;
import static com.example.healthapp.Constants.PULSE_AVG;
import static com.example.healthapp.Constants.SPEED_AVG;
import static com.example.healthapp.Constants.TARGET_WORKOUT;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        Intent intent = getIntent();
        TextView activityTxt = findViewById(R.id.activityTxt);
        TextView targetWTxt = findViewById(R.id.targetWrkoutTxt);
        TextView durationTxt = findViewById(R.id.durationTxt);
        TextView speedAvgText = findViewById(R.id.speedAvgVal);
        TextView pulseAvgText = findViewById(R.id.pulseAvgVal);
        Bundle extras = intent.getExtras();

        if (intent.hasExtra(ACTIVITY)) {
            activityTxt.setText(extras.getString(ACTIVITY));
        }

        if (intent.hasExtra(TARGET_WORKOUT)) {
            targetWTxt.setText(extras.getString(TARGET_WORKOUT));
        }

        if (intent.hasExtra(DURATION)) {
            durationTxt.setText(extras.getString(DURATION));
        }

        if (intent.hasExtra(SPEED_AVG)) {
            Double speedAvg = extras.getDouble(SPEED_AVG);
            DecimalFormat numberFormat = new DecimalFormat("#.##");
            numberFormat.setRoundingMode(RoundingMode.CEILING);
            speedAvgText.setText(numberFormat.format(speedAvg));
        }

        if (intent.hasExtra(PULSE_AVG)) {
            Integer pulseAvg = extras.getInt(PULSE_AVG);
            pulseAvgText.setText(pulseAvg.toString());
        }
    }
}
