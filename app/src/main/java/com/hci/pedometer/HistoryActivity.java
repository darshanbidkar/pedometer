package com.hci.pedometer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

/*
*
* Created by Darshan reddy and Darshan Bidkar
* This activity is to view the Last few days activity
*
* */
public class HistoryActivity extends AppCompatActivity {
    /*
    *
    * @author Darshan Reddy
    *
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ProgressBar first = (ProgressBar) findViewById(R.id.first);
        ProgressBar second = (ProgressBar) findViewById(R.id.second);
        ProgressBar third = (ProgressBar) findViewById(R.id.third);
        ProgressBar fourth = (ProgressBar) findViewById(R.id.fourth);
        ProgressBar fifth = (ProgressBar) findViewById(R.id.fifth);
        ProgressBar sixth = (ProgressBar) findViewById(R.id.sixth);
        first.setMax(1000);
        second.setMax(1000);
        third.setMax(1000);
        fourth.setMax(1000);
        fifth.setMax(1000);
        sixth.setMax(1000);
        first.setProgress(300);
        second.setProgress(40);
        third.setProgress(600);
        fourth.setProgress(150);
        fifth.setProgress(450);
        sixth.setProgress(20);
    }
}
