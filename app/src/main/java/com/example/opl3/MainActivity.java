package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.Manifest;
import org.w3c.dom.Text;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private ConstraintLayout askStart;
    private RelativeLayout askLoad;

    private ImageButton yes;
    private ImageButton no;

    private LinearLayout loadGame;

    private EditText fileName;

    private TextView invalidPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        button = findViewById(R.id.imageButton);
        askStart = findViewById(R.id.askStart);
        askLoad = findViewById(R.id.askLoad);
        yes = findViewById(R.id.yesButton);
        no = findViewById(R.id.noButton);
        invalidPath = findViewById(R.id.invalidpath);
        loadGame = findViewById(R.id.loadGame);
        fileName = findViewById(R.id.fileName);
        askLoad.setVisibility(View.INVISIBLE);
        loadGame.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askLoad();
            }
        });
    }

    public void askLoad(){
        button.setOnClickListener(null);
        this.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        askStart.setVisibility(View.INVISIBLE);
                        loadGame.setVisibility(View.INVISIBLE);
                        askLoad.setVisibility(View.VISIBLE);
                    }
                }
        );
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askFileName();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), hand.class);
                intent.putExtra("load", "no");
                startActivity(intent);
            }
        });
    }

    public void askFileName(){
        yes.setOnClickListener(null);
        no.setOnClickListener(null);
        this.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        loadGame.setVisibility(View.VISIBLE);
                        askStart.setVisibility(View.INVISIBLE);
                        askLoad.setVisibility(View.INVISIBLE);
                    }
                }
        );

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    100
            );
        }
        fileName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String filePath = fileName.getText().toString();
                    //filePath = "/storage/emulated/0/seralize1.txt";
                    File file = new File(filePath);
                    if (file.exists() && !file.isDirectory()) {
                        Intent intent = new Intent(getApplicationContext(), hand.class);
                        intent.putExtra("filepath", filePath);
                        intent.putExtra("load", "Y");
                        startActivity(intent);
                        return true;
                    } else {
                        System.out.println("Invalid file path");
                        invalidPath.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
    }
}