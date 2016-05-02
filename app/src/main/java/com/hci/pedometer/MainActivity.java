package com.hci.pedometer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
/*
* Created by Darshan reddy and Darshan Bidkar
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
    private int recentStepCount;
    private Button resetButton;

    /*
    * @author: Darshan Reddy
    *
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepProgress = (ProgressBar) findViewById(R.id.step_progress_count);
        stepLayout = (LinearLayout)findViewById(R.id.step_layout);
        resetButton = (Button) findViewById(R.id.reset_btn);
        resetButton.setOnClickListener(getResetListener());
        stepLayout.setOnClickListener(new LinearLayout.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        caloriesLayour = (LinearLayout)findViewById(R.id.calorie_layout);
        caloriesLayour.setOnClickListener(new LinearLayout.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        milesLayout = (LinearLayout)findViewById(R.id.distance_layout);
        milesLayout.setOnClickListener(new LinearLayout.OnClickListener(){

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
    *
    * */
    protected void onStop() {
        super.onStop();
       // mSensorManager.unregisterListener(this, mStepCounterSensor);
       // mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    /*
    * @author: darshan bidkar
    *
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
            Log.d("step",""+value);
        }
    }
    /*
    * @author: darshan bidkar
    * */
    public void updateStepsCount(int steps) {
        String  miles =  new DecimalFormat("#.##").format(steps/2112.0);
        stepView.setText("" + steps);
        calorieView.setText("" + steps*40/1000);
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
    * */
    public View.OnClickListener getResetListener() {
        return new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Need to implement", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
