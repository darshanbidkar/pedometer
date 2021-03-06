package com.hci.pedometer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

/*
* Created by Darshan reddy and Darshan Bidkar
*
* PREAMBLE :
*
* Team members: Darshan Narayana reddy and Darshan Bidkar
* Class Name: Human computer Interactions (CS 6326.001)
*
* Assignment: Final Project to design a useful Pedometer
*
* Professor: John Cole
*
* Purpose: To design a useful, full pledged Android application which uses sensors.
*
* */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ProgressBar mStepProgress;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private TextView stepView;
    private TextView distanceView;
    private TextView calorieView;
    private Sensor mStepDetectorSensor;
    private int stepsGoal = 1000;// TODO : take this input from the user
    private LinearLayout stepLayout;
    private LinearLayout caloriesLayour;
    private LinearLayout milesLayout;
    private TextView time_since_reset;
    private int recentStepCount;
    private Button resetButton;
    private long startTime;
    private Timer timer;

    /*
    * @author: Darshan Reddy
    *
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepProgress = (ProgressBar) findViewById(R.id.step_progress_count);
        stepLayout = (LinearLayout) findViewById(R.id.step_layout);
        resetButton = (Button) findViewById(R.id.reset_btn);
        time_since_reset = (TextView) findViewById(R.id.time_since_reset);
        resetButton.setOnClickListener(getResetListener());
        stepLayout.setOnClickListener(new LinearLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        caloriesLayour = (LinearLayout) findViewById(R.id.calorie_layout);
        caloriesLayour.setOnClickListener(new LinearLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        milesLayout = (LinearLayout) findViewById(R.id.distance_layout);
        milesLayout.setOnClickListener(new LinearLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        mStepProgress.setMax(stepsGoal);
        mStepProgress.setIndeterminate(false);
        mStepProgress.setProgress(0);
        mSensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        stepView = (TextView) findViewById(R.id.step_count);
        distanceView = (TextView) findViewById(R.id.distance_count);
        calorieView = (TextView) findViewById(R.id.calorie_count);
        readAgain();

        time_since_reset.setText(new SimpleDateFormat("hh:mm", Locale.US).format(new Date(System.currentTimeMillis())));
    }

    /*
    * @author: darshan Reddy
    * */
    protected void onResume() {

        super.onResume();

        mSensorManager.registerListener(this, mStepCounterSensor,

                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this, mStepDetectorSensor,

                SensorManager.SENSOR_DELAY_FASTEST);

    }

    /*
    * @author: darshan Bidkar
    *We will not unregister the listner cause we need that to keep listning even when the app is not running
    * */
    protected void onStop() {
        super.onStop();
        // mSensorManager.unregisterListener(this, mStepCounterSensor);
        // mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    /*
    * @author: darshan bidkar
    *This function senses the step counts and increments the count
    * */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }
        TextView textView = (TextView) findViewById(R.id.step_count);
        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            updateStepsCount(value);
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            updateStepsCount(value);
            Log.d("step", "" + value);
        }
    }

    /*
    * @author: darshan bidkar
    *
    * This function converts the step count to calorie count and miles
    * */
    public void updateStepsCount(int steps) {
        String miles = new DecimalFormat("#.##").format(steps / 2112.0);
        stepView.setText("" + steps);
        calorieView.setText("" + steps * 40 / 1000);
        distanceView.setText(String.format("" + miles));
        mStepProgress.setProgress(steps);
    }

    /*
    *
    * @author: darshan reddy
    * */

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        Toast.makeText(MainActivity.this, ""+accuracy, Toast.LENGTH_SHORT).show();
    }

    /*
    * @author: darshan bidkar
    *
    * This function resets the count of the steps
    * */
    public View.OnClickListener getResetListener() {
        return new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("step_counter.txt", MODE_WORLD_READABLE | MODE_APPEND)));
                    writer.append(stepView.getText().toString().trim() + "\n");
                    updateStepsCount(0);
                    writer.close();
                    // Reset counter
                    stepView.setText("0");
                    // readAgain();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /*
    * @author: Darshan Bidkar
    *
    * helper function to  read the previous steps history from the files
    * */
    private void readAgain() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput("step_counter.txt")));
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                Log.d("Reading from file", input);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}