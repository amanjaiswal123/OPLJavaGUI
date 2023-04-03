package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    private Tournament tournament;
    private Controller mainController;
    private String handNum;
    private TextView handNumtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Intent intent = getIntent();
        handNum = intent.getStringExtra("handNum");
        mainController = (Controller) intent.getSerializableExtra("controller");
        handNumtxt = findViewById(R.id.messageBoard);
        handNumtxt.setText("Hand Number: " + handNum);
        // rest of the code
    }
}