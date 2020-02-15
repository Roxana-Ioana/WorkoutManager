package com.example.healthapp;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.healthapp.Constants.ACTIVITY;
import static com.example.healthapp.Constants.AGE;
import static com.example.healthapp.Constants.TARGET_WORKOUT;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent previousIntent = getIntent();

        final Intent intent = new Intent();

        intent.putExtra(TARGET_WORKOUT, previousIntent.getStringExtra(TARGET_WORKOUT));
        intent.putExtra(AGE, previousIntent.getStringExtra(AGE));

        intent.setComponent(new ComponentName(this, ThirdActivity.class));

        Button walkingBtn = findViewById(R.id.walkingBtn);
        Button runningBtn = findViewById(R.id.runningBtn);
        Button cyclingBtn = findViewById(R.id.cyclingBtn);

        String targetWorkout = previousIntent.getStringExtra(TARGET_WORKOUT);

        if (targetWorkout.equals("HARD")) {
            walkingBtn.setBackgroundColor(Color.parseColor("#FFE0B2"));
            runningBtn.setBackgroundColor(Color.parseColor("#FFE0B2"));
            cyclingBtn.setBackgroundColor(Color.parseColor("#FFE0B2"));

        } else {
            if (targetWorkout.equals("MODERATE")) {
                walkingBtn.setBackgroundColor(Color.parseColor("#CCFF90"));
                runningBtn.setBackgroundColor(Color.parseColor("#CCFF90"));
                cyclingBtn.setBackgroundColor(Color.parseColor("#CCFF90"));

            } else {
                if (targetWorkout.equals("LIGHT")) {
                    walkingBtn.setBackgroundColor(Color.parseColor("#84FFFF"));
                    runningBtn.setBackgroundColor(Color.parseColor("#84FFFF"));
                    cyclingBtn.setBackgroundColor(Color.parseColor("#84FFFF"));

                } else if (targetWorkout.equals("VERY LIGHT")) {
                    walkingBtn.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    runningBtn.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    cyclingBtn.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }
            }
        }

        walkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ACTIVITY, "Walking");
                startActivity(intent);
            }
        });

        runningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ACTIVITY, "Running");
                startActivity(intent);
            }
        });

        cyclingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(ACTIVITY, "Cycling");
                startActivity(intent);
            }
        });
    }

}
