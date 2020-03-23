package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.healthapp.Constants.AGE;
import static com.example.healthapp.Constants.TARGET_WORKOUT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ActivityLifecycle", "onCreate");
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, SecondActivity.class);
        final EditText ageTxt = findViewById(R.id.ageNb);
        
        ImageButton hardBtn = findViewById(R.id.hard);
        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(TARGET_WORKOUT, "HARD");
                String age = ageTxt.getText().toString();
                if (!age.equals("")) {
                    Integer ageValue = Integer.parseInt(age);
                    if (ageValue > 200) {
                        Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();

                    } else {
                        intent.putExtra(AGE, age);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton moderateBtn = findViewById(R.id.moderate);
        moderateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(TARGET_WORKOUT, "MODERATE");
                String age = ageTxt.getText().toString();
                if (!age.equals("")) {
                    Integer ageValue = Integer.parseInt(age);
                    if (ageValue > 200) {
                        Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra(AGE, age);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton lightBtn = findViewById(R.id.light);
        lightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(TARGET_WORKOUT, "LIGHT");
                String age = ageTxt.getText().toString();
                if (!age.equals("")) {
                    Integer ageValue = Integer.parseInt(age);
                    if (ageValue > 200) {
                        Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();

                    } else {
                        intent.putExtra(AGE, age);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton veryLightBtn = findViewById(R.id.verylight);
        veryLightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(TARGET_WORKOUT, "VERY LIGHT");
                String age = ageTxt.getText().toString();
                if (!age.equals("")) {
                    Integer ageValue = Integer.parseInt(age);
                    if (ageValue >200) {
                        Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();

                    } else {
                        intent.putExtra(AGE, age);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please introduce a valid age value!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ActivityLifecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ActivityLifecycle", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ActivityLifecycle", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ActivityLifecycle", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ActivityLifecycle", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ActivityLifecycle", "onStop");
    }
}
