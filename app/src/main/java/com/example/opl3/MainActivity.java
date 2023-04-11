package com.example.opl3;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.Manifest;
import org.w3c.dom.Text;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {


    /* *********************************************************************
    Function Name: onItemSelected
    Purpose: To load the game from an external file
    Parameters:
    parent, an AdapterView object passed by value. It holds the parent view information
    view, a View object passed by value. It holds the view information
    position, an integer. It refers to the position of the item selected
    id, a long. It refers to the id of the item selected
    Return Value: None
    Algorithm:
    1) Get the file path of the selected item
    2) Check if the file exists and is not a directory
    3) If the file exists, start the activity hand.class and pass the file path and "Y" as extras
    4) If the file does not exist, print "Invalid file path" and show the invalidPath TextView
    Assistance Received: none
    ********************************************************************* */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // The file path is the name of the file selecte
        String filePath = parent.getItemAtPosition(position).toString();
        // Create a file object with the file path
        File file = new File(getExternalFilesDir(null), filePath);
        // Check if the file exists and is not a directory
        if (file.exists() && !file.isDirectory()) {
            // Start the activity hand.class and pass the file path and "Y" as extras
            Intent intent = new Intent(getApplicationContext(), hand.class);
            // Pass the file path through the intent
            intent.putExtra("filepath", filePath);
            //Pass "Y" through the intent to indicate that the game is being loaded
            intent.putExtra("load", "Y");
            //Start the activity hand.class
            startActivity(intent);
            //Finish the activity MainActivity.class
            finish();
        } else {
            // If the file does not exist, print "Invalid file path" and show the invalidPath TextView
            System.out.println("Invalid file path");
            // Show the invalidPath TextView
            invalidPath.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        int x = 1;
    }


    /* *********************************************************************
    Function Name: askLoad
    Purpose: To ask the user if they want to load a saved game
    Parameters: None
    Return Value: None
    Algorithm:
    1) Set the onClickListener of the button to null
    2) Make the askStart ConstraintLayout invisible
    3) Make the askLoad RelativeLayout visible
    4) Populate the spinner with the file names in the external files directory
    5) Set the onClickListener of the yes ImageButton to call the askFileName method
    6) Set the onClickListener of the no ImageButton to start the activity hand.class with "no" as an extra
    Assistance Received: none
    ********************************************************************* */
    public void askLoad(){
        // Set the onClickListener of the button to null
        button.setOnClickListener(null);
        //create a local variable to hold the activity as we cannot reference this in the
        // runOnUiThread method
        MainActivity activity = this;
        // Run the code in the runOnUiThread method on the UI thread
        this.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Make the askStart ConstraintLayout invisible
                        askStart.setVisibility(View.INVISIBLE);
                        loadGame.setVisibility(View.INVISIBLE);
                        askLoad.setVisibility(View.VISIBLE);

                        // Populate the spinner with the file names in the external files directory
                        List<String> fileNames = new ArrayList<>();
                        // Get the external files directory
                        File directory = getExternalFilesDir(null);
                        // Get the files in the directory
                        File[] files = directory.listFiles();
                        // Create a dummy file to be the first item in the spinner
                        File dummyFile = new File(getExternalFilesDir(null), "Please Select a File:");
                        // To get file names as a List<String>
                        fileNames.add("Please Select a File:");
                        for (File file : files) {
                            // Add the file name to the list
                            fileNames.add(file.getName());
                        }
                        // Set the first dummy item in the spinner to be selected
                        fileName.setSelection(1);

                        // Create an ArrayAdapter using the default spinner layout and the file names list
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, fileNames);
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        fileName.setAdapter(adapter);
                    }
                }
        );

        // Set the onClickListener of the yes ImageButton to call the askFileName method
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            // Call the askFileName method if the yes ImageButton is clicked
            public void onClick(View v) {
                askFileName();
            }
        });

        // Set the onClickListener of the no ImageButton to start the activity hand.class
        // with "no" as an extra
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity hand.class with "no" as an extra
                Intent intent = new Intent(getApplicationContext(), hand.class);
                intent.putExtra("load", "no");
                // Start the activity hand.class
                startActivity(intent);
                // Finish the activity MainActivity.class
                finish();
            }
        });
    }

    /* *********************************************************************
    Function Name: askFileName
    Purpose: To ask the user to select a file name
    Parameters: None
    Return Value: None
    Algorithm:
    1) Set the onClickListener of the yes and no ImageButtons to null
    2) Make the askStart ConstraintLayout and askLoad RelativeLayout invisible
    3) Make the loadGame LinearLayout visible
    4) Check if the app has the necessary permissions to read and write to external storage
    5) If the permissions are not granted, request for them
    6) Set the onItemSelectedListener of the fileName Spinner to this activity
    Assistance Received: none
    ********************************************************************* */
    public void askFileName(){
        // Set the onClickListener of the yes and no ImageButtons to null to prevent the user from
        // clicking the buttons multiple times
        yes.setOnClickListener(null);
        no.setOnClickListener(null);
        // Run the code in the runOnUiThread method on the UI thread
        this.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Make the askStart ConstraintLayout and askLoad RelativeLayout invisible
                        loadGame.setVisibility(View.VISIBLE);
                        askStart.setVisibility(View.INVISIBLE);
                        askLoad.setVisibility(View.INVISIBLE);
                    }
                }
        );

        // Check if the app has the necessary permissions to read and write to external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // If the permissions are not granted, request for them
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    100
            );
        }
        // Set the onItemSelectedListener of the fileName Spinner to this activity
        fileName.setOnItemSelectedListener(MainActivity.this);
    }


    /* *********************************************************************
    Function Name: onCreate
    Purpose: To create the main activity of the game.
    Parameters:
    savedInstanceState, a Bundle object. It holds the state of the activity if it was previously created.
    Return Value: None
    Algorithm:
    1) Set the content view of the activity to the specified layout
    2) Initialize the UI elements and set the visibility of some elements to INVISIBLE
    3) Set the onClickListener for the image button to call the askLoad() method
    ********************************************************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the content view of the activity to the specified layout

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        // Initialize the UI elements and set the visibility of some elements to INVISIBLE
        button = findViewById(R.id.imageButton);
        askStart = findViewById(R.id.askStart);
        askLoad = findViewById(R.id.askLoad);
        yes = findViewById(R.id.yesButton);
        no = findViewById(R.id.noButton);
        invalidPath = findViewById(R.id.invalidpath);
        loadGame = findViewById(R.id.loadGame);
        fileName = findViewById(R.id.fileName);

        // Make the askStart ConstraintLayout and askLoad RelativeLayout invisible
        askLoad.setVisibility(View.INVISIBLE);
        loadGame.setVisibility(View.INVISIBLE);

        // Set the onClickListener for the image button to call the askLoad() method
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the askLoad() method when the image button is clicked
                askLoad();
                return;
            }
        });
    }


    // Declare the UI elements

    // The image button that starts the game
    private ImageButton button;
    // The ConstraintLayout that asks the user if they want to load a game
    private ConstraintLayout askStart;
    // The RelativeLayout that asks the user if they want to load a game
    private RelativeLayout askLoad;

    //The yes image buttons that ask the user if they want to load a game
    private ImageButton yes;
    // The no image buttons that ask the user if they want to load a game
    private ImageButton no;
    // The LinearLayout that asks the user to select a file name
    private LinearLayout loadGame;
    // The Spinner that holds the file names
    private Spinner fileName;
    // The TextView that displays an error message if the user enters an invalid file name
    private TextView invalidPath;

}