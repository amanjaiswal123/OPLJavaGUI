package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private Controller mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainController = new Controller();
        this.setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String handnum = String.valueOf(mainController.startGame());
                Intent intent = new Intent(getApplicationContext(), hand.class);
                intent.putExtra("handNum", handnum);
                intent.putExtra("controller", mainController);
                startActivity(intent);
            }
        });
    }
}