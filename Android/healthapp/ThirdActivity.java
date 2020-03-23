package com.example.healthapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.example.healthapp.Constants.ACTIVITY;
import static com.example.healthapp.Constants.AGE;
import static com.example.healthapp.Constants.DURATION;
import static com.example.healthapp.Constants.PULSE_AVG;
import static com.example.healthapp.Constants.SPEED_AVG;
import static com.example.healthapp.Constants.TARGET_WORKOUT;

public class ThirdActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1252;

    private TextView activityText, responseText, velocityText;
    private Button startBtn, stopBtn;
    private String velocity;
    private String pulse;
    private LocationManager locationManager;
    private DatabaseReference dbRef;
    private Intent activityIntent;
    private int userAge;
    private String targetWorkout;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double lowerBound;
    private double upperBound;
    private int sumPulse = 0;
    private double sumSpeed = 0;
    private int countPulse = 0;
    private double countSpeed = 0;
    private boolean stopReceivingData = false;
    private boolean startPressed = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Got the permission", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Can not proceed! Permission needed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        velocityText = findViewById(R.id.velocityText);
        velocityText.setText("0.00");
        activityText = findViewById(R.id.activityText);
        responseText = findViewById(R.id.responseText);

        startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTime = LocalDateTime.now();
                configureFirebase();
                gps();
                startPressed = true;
                stopReceivingData = false;
            }
        });

        stopBtn = findViewById(R.id.stopBtn);
        activityIntent = getIntent();

        userAge = Integer.parseInt(Objects.requireNonNull(activityIntent.getStringExtra(AGE)));

        targetWorkout = activityIntent.getStringExtra(TARGET_WORKOUT);

        setPulseBounds();

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!startPressed) {
                    Toast.makeText(ThirdActivity.this, "Press Start first!", Toast.LENGTH_SHORT).show();
                } else {
                    startPressed = false;
                    endTime = LocalDateTime.now();

                    String duration = getDuration();

                    if (countPulse == 0) {
                        countPulse = 1;
                    }

                    int pulseAvg = sumPulse / countPulse;

                    if (countSpeed == 0) {
                        countSpeed = 1;
                    }

                    double speedAvg = sumSpeed / countSpeed;

                    Intent intent = new Intent();
                    Bundle extras = new Bundle();

                    extras.putString(DURATION, duration);
                    extras.putString(TARGET_WORKOUT, targetWorkout);
                    extras.putInt(AGE, userAge);
                    extras.putString(ACTIVITY, activityIntent.getStringExtra(ACTIVITY));
                    extras.putInt(PULSE_AVG, pulseAvg);
                    extras.putDouble(SPEED_AVG, speedAvg);

                    intent.putExtras(extras);

                    intent.setComponent(new ComponentName(ThirdActivity.this, FourthActivity.class));
                    startActivity(intent);

                    stopReceivingData = true;
                }
            }
        });

        setActivityName();
    }

    private void configureFirebase() {
        final MediaPlayer beep = MediaPlayer.create(this, R.raw.beep);

        dbRef = FirebaseDatabase.getInstance().getReference().child("values");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!stopReceivingData) {
                    pulse = dataSnapshot.child("pulse").getValue().toString();
                    int pulseVal = Integer.parseInt(pulse);

                    sumPulse += pulseVal;
                    countPulse += 1;

                    if (pulseVal < lowerBound) {
                        beep.start();
                        Toast.makeText(ThirdActivity.this, "Your heart rate is below the lower limit!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pulseVal > upperBound) {
                            beep.start();
                            Toast.makeText(ThirdActivity.this, "Your heart rate is above the upper limit!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    responseText.setText(pulse);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void gps() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= 23) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }

            } else { 

            }
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location == null) {
                    velocityText.setText("0.00");
                } else {
                    if (!stopReceivingData) {
                        double velocityValue = location.getSpeed() * 3.6;
                        velocity = String.format("%.2f", velocityValue);

                        sumSpeed += velocityValue;
                        countSpeed += 1;

                        velocityText.setText(velocity);
                        //Toast.makeText(ThirdActivity.this, "lat: " + location.getLatitude() + "long: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });
    }

    private void setActivityName() {
        String s = "";

        if (activityIntent.hasExtra(ACTIVITY)) {
            s = activityIntent.getStringExtra(ACTIVITY);
            activityText.setText(s);
        }
    }

    private void setPulseBounds() {
        int maximumPulse = 220 - userAge;

        switch (targetWorkout) {
            case "HARD":
                lowerBound = 0.8 * maximumPulse;
                upperBound = 0.9 * maximumPulse;
                break;
            case "MODERATE":
                lowerBound = 0.7 * maximumPulse;
                upperBound = 0.8 * maximumPulse;
                break;
            case "LIGHT":
                lowerBound = 0.6 * maximumPulse;
                upperBound = 0.7 * maximumPulse;
                break;
            case "VERY LIGHT":
                lowerBound = 0.5 * maximumPulse;
                upperBound = 0.6 * maximumPulse;
                break;
        }

    }

    private String getDuration() {
        long hours = startTime.until(endTime, ChronoUnit.HOURS);
        startTime = startTime.plusHours(hours);

        long mins = startTime.until(endTime, ChronoUnit.MINUTES);
        startTime = startTime.plusMinutes(mins);

        long sec = startTime.until(endTime, ChronoUnit.SECONDS);

        return hours + ":" + mins + ":" + sec;
    }

}
