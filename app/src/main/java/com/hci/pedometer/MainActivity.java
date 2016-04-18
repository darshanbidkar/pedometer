package com.hci.pedometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mStepProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepProgress = (ProgressBar) findViewById(R.id.step_progress_count);
        mStepProgress.setMax(10000);
        mStepProgress.setIndeterminate(false);
        mStepProgress.setProgress(417);
    }
}
