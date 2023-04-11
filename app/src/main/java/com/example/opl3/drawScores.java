package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class drawScores extends AppCompatActivity {
    /* *********************************************************************
    Function Name: onCreate
    Purpose: The purpose of this function is to initialize the activity and set the content view to the activity_draw_scores layout.
    Parameters:
    savedInstanceState, a Bundle object passed by value. It holds the saved state information of the activity.
    Return Value: None
    Algorithm:
    1) Call the superclass onCreate method with the savedInstanceState parameter
    2) Set the content view of the activity to the activity_draw_scores layout.
    Assistance Received: None
    ********************************************************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass onCreate method with the savedInstanceState parameter
        super.onCreate(savedInstanceState);
        // Set the content view of the activity to the activity_draw_scores layout.
        setContentView(R.layout.activity_draw_scores);
    }
}