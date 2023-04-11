package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class hand extends AppCompatActivity {

    public void setMessageBoard(String message) {
        messageBoard.setText(message);
    }


    //Get stackAdapter
    public DominoAdapter getStackAdapter(){
        return stackAdapter;
    }
    //Get handAdapter
    public DominoAdapter getHandAdapter(){
        return handAdapter;
    }

    public void hideBoardDisplay(){
        boardDisplay.setVisibility(View.INVISIBLE);
    }

    public void showBoardDisplay(){
        boardDisplay.setVisibility(View.VISIBLE);
    }

    public void showScoreDisplay(){
        table_display.setVisibility(View.VISIBLE);
    }

    public void hideScoreDisplay(){
        table_display.setVisibility(View.INVISIBLE);
    }

    /* *********************************************************************
    Function Name: draw_scores
    Purpose: To display the scores in a table form
    Parameters:
    finalScores, a Map passed by reference. It holds the final scores of the players, with player IDs as keys and scores as values
    tableTitle, a String. It is the title for the table to be displayed
    columnTitle, a String. It is the title for the column of scores to be displayed
    Return Value: None
    Algorithm:
    1) Set the table title and column title by getting the TextView and setting its text
    2) Get the first and second player IDs and scores from the finalScores map
    3) Get the TextViews for player IDs and scores and set their text with the player IDs and scores obtained
    4) Hide the board display and show the score display
    Assistance Received: None
    ********************************************************************* */

    public void draw_scores(Map<String, Integer> finalScores, String tableTitle, String columnTitle){
        //Find the TextViews for the table title and column title
        TextView tableTitleView = findViewById(R.id.titleTextView);
        TextView columnTitleView = findViewById(R.id.value_column);
        //Set the text for the table title and column title
        tableTitleView.setText(tableTitle);
        columnTitleView.setText(columnTitle);
        //Get the first and second player IDs and scores from the finalScores map
        String p1ID = finalScores.keySet().toArray()[0].toString();
        String p2ID = finalScores.keySet().toArray()[1].toString();
        //Get the first and second player scores from the finalScores map
        int p1Score = finalScores.get(p1ID);
        int p2Score = finalScores.get(p2ID);

        //Find the TextViews for the player IDs and scores
        TextView p1IDView = findViewById(R.id.player1ID);
        TextView p2IDView = findViewById(R.id.player2ID);

        //Find the TextViews for the player IDs and scores
        TextView p1ScoreView = findViewById(R.id.player1Score);
        TextView p2ScoreView = findViewById(R.id.player2Score);

        //Set the text for the player IDs and scores
        p1IDView.setText(p1ID);
        p2IDView.setText(p2ID);
        p1ScoreView.setText(Integer.toString(p1Score));
        p2ScoreView.setText(Integer.toString(p2Score));

        //Hide the board display and show the score display
        hideBoardDisplay();
        showScoreDisplay();
    }

    /* *********************************************************************
    Function Name: showRecMove
    Purpose: To show the user a dialog to ask if they want a recommended move
    Parameters: None
    Return Value: None
    Algorithm:
    1) Set the text of the yesNoTitle view to "Would you like a recommended move?"
    2) Set the visibility of the askYesNo view to VISIBLE
    3) Set the onClickListener for the yesButton to call the setYesNo method of mainController with "y" as an argument
    4) Set the onClickListener for the noButton to call the setYesNo method of mainController with "n" as an argument
    Assistance Received: None
    ********************************************************************* */

    public void showRecMove() {
        //Set the text of the yesNoTitle view to "Would you like a recommended move?"
        yesNoTitle.setText("Would you like a recommended move?");
        //Set the visibility of the askYesNo view to VISIBLE
        askYesNo.setVisibility(View.VISIBLE);
        //Set the onClickListener for the yesButton to call the setYesNo method of mainController with "y" as an argument
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Call the setYesNo method of mainController with "y" as an argument
            public void onClick(View v) {
                mainController.setYesNo("y");
            }
        });
        //Set the onClickListener for the noButton to call the setYesNo method of mainController with "n" as an argument
        noButton.setOnClickListener(new View.OnClickListener() {
            //Call the setYesNo method of mainController with "n" as an argument
            @Override
            //Call the setYesNo method of mainController with "n" as an argument
            public void onClick(View v) {
                mainController.setYesNo("n");
            }
        });
    }

    public void hideRecMove() {
        askYesNo.setVisibility(View.INVISIBLE);
    }

    public void showMessageBoard() {
        messageBoard.setVisibility(View.VISIBLE);
    }

    public void hideMessageBoard() {
        messageBoard.setVisibility(View.INVISIBLE);
    }

    public void stopYesNoListeners() {
        yesButton.setOnClickListener(null);
        noButton.setOnClickListener(null);
    }

    public void clearMessageBoard() {
        messageBoard.setText("");
    }


    /* *********************************************************************
    Function Name: showPass
    Purpose: Show a prompt asking the user if they want to pass.
    Parameters: None
    Return Value: None
    Algorithm:
    Set the text of the yesNoTitle TextView to "Do you want to pass?"
    Set the visibility of the askYesNo View to VISIBLE
    Set the onClickListener for the yesButton to call mainController.setYesNo("y")
    Set the onClickListener for the noButton to call mainController.setYesNo("n")
    Assistance Received: None
    ********************************************************************* */
    public void showPass() {
        //Set the text of the yesNoTitle TextView to "Do you want to pass?"
        yesNoTitle.setText("Do you want to pass?");
        //Set the visibility of the askYesNo View to VISIBLE
        askYesNo.setVisibility(View.VISIBLE);
        //Set the onClickListener for the yesButton to call mainController.setYesNo("y")
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Call mainController.setYesNo("y")
            public void onClick(View v) {
                mainController.setYesNo("y");
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {

            @Override
            //Call mainController.setYesNo("n")
            public void onClick(View v) {
                mainController.setYesNo("n");
            }
        });
    }


    /* *********************************************************************
    Function Name: askSaveGame
    Purpose: To ask the user if they would like to save the game and grant necessary permissions
    Parameters: None
    Return Value: None
    Algorithm:
    1) Check if the app has permission to read and write to external storage
    2) If permission is not granted, request for permission
    3) Show the "Would you like to save the game?" prompt to the user
    4) Set the onClickListeners for the yes and no buttons to send "y" and "n" respectively to the mainController
    Assistance Received: None
    ********************************************************************* */
    public void askSaveGame(){
        //Check if the app has permission to read and write to external storage
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //If permission is not granted, request for permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    100
            );
        }
        //Show the "Would you like to save the game?" prompt to the user
        messageBoard.setVisibility(View.INVISIBLE);
        //Show the yes and no buttons
        askYesNo.setVisibility(View.VISIBLE);
        //Set the text of the yesNoTitle TextView to "Would you like to save the game?"
        yesNoTitle.setText("Would you like to save the game?");
        //Set the onClickListener for the yesButton to call mainController.setYesNo("y")
        // and the onClickListener for the noButton to call mainController.setYesNo("n")
        yesButton.setOnClickListener(new View.OnClickListener() {
            //Call mainController.setYesNo("y")
            @Override
            //Call mainController.setYesNo("y")
            public void onClick(View v) {
                mainController.setYesNo("y");
            }
        });

        //Set the onClickListener for the noButton to call mainController.setYesNo("n")
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Call mainController.setYesNo("n")
            public void onClick(View v) {
                mainController.setYesNo("n");
            }
        });

    }

    /* *********************************************************************
    Function Name: askFileName
    Purpose: To ask the user for a file name to save the game and verify that the file name is valid
    Parameters: None
    Return Value: None
    Algorithm:
    1) Check if the write and read external storage permissions have been granted
    2) Make the message board invisible and show the file name input components
    3) Listen for the user to enter a file name and press "Done"
    4) Check if the entered file name is a valid file path
    5) If the file path is valid, set the file path in the main controller and finish the activity
    6) If the file path is invalid, show an error message
    Assistance Received: None
    ********************************************************************* */
    public void askFileName(){
        //Check if the write and read external storage permissions have been granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //If permission is not granted, request for permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    100
            );
        }
        //Make the message board invisible and show the file name input components
        messageBoard.setVisibility(View.INVISIBLE);
        fileName.setVisibility(View.VISIBLE);
        fileNameTXT.setVisibility(View.VISIBLE);
        //Listen for the user to enter a file name
        fileNameTXT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //Check if the entered file name is a valid file path
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Get the file path from the text box
                    String filePath = fileNameTXT.getText().toString();
                    //Create a file object with the file path
                    File file = new File(getExternalFilesDir(null), filePath);
                    //If the file path is valid, set the file path in the main controller and finish the activity
                    if (!file.exists() && !file.isDirectory()) {
                        //Set the file path in the main controller
                        mainController.setFile(file);
                        finish();
                        return true;
                    } else {
                        //If the file path is invalid, show an error message
                        System.out.println("Invalid file path");
                        //Make the error message visible
                        invalidPath.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
    }


    /* *********************************************************************
    Function Name: askPlayAgain
    Purpose: To ask the user if they want to play the game again after a round has been completed
    Parameters:
    winner, a String passed by value. It holds the name of the winner of the current round
    humanRoundWins, an integer passed by value. It holds the number of rounds won by the human player
    computerRoundWins, an integer passed by value. It holds the number of rounds won by the computer player
    Return Value: None
    Algorithm:
    1) Create an Intent object to start the playAgain activity
    2) Pass the humanRoundWins, computerRoundWins, and winner information to the new activity
    3) Start the playAgain activity
    4) End the current activity
    Assistance Received: None
    ********************************************************************* */
    public void askPlayAgain(String winner, int humanRoundWins, int computerRoundWins) {
        //Create an Intent object to start the playAgain activity
        Intent intent = new Intent(this, playAgain.class);
        //Pass the humanRoundWins, computerRoundWins, and winner information to the new activity
        intent.putExtra("humanRoundWins", humanRoundWins);
        intent.putExtra("computerRoundWins", computerRoundWins);
        intent.putExtra("winner", winner);
        //Start the playAgain activity
        startActivity(intent);
        //End the current activity
        finish();
    }

    public void drawRecMove() {
        hideMessageBoard();
        showRecMove();
    }

    public void showpass() {
        hideRecMove();
        showMessageBoard();
    }


    /* *********************************************************************
    Function Name: waitforTap
    Purpose: To wait for a user tap on the screen
    Parameters: None
    Return Value: None
    Algorithm:
    1) Reset the tap in mainController
    2) Set an onClickListener on overall_layout
    3) When the layout is clicked, set tap in mainController to true
    Assistance Received: None
    ********************************************************************* */
    public void waitforTap() {

        //Reset the tap in mainController
        mainController.resetTap();
        //Set an onClickListener on overall_layout
        overall_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            //When the layout is clicked, set tap in mainController to true
            public void onClick(View v) {
                mainController.setTap(true);
            }
        });
    }


    public void showCurrentPlayer() {
        messageBoard.setText(mainController.getCurrentPlayer().getPlayerID() + "'s Turn");
    }

    /* *********************************************************************
    Function Name: onCreate
    Purpose: To set up the Domino activity
    Parameters:
    savedInstanceState, a Bundle object. It holds the state of the activity if it was previously saved
    Return Value: None
    Algorithm:
    1) Call the super.onCreate method and pass savedInstanceState to it
    2) Set the content view to the domino_layout file
    3) Initialize the UI elements
    4) Get the filepath and round wins passed from the previous activity and start or load the game
    accordingly
    5) Populate the lists of stack and hand tiles from the players in the game
    6) Initialize the adapters for the stack and hand RecyclerViews
    7) Set the layout managers for the RecyclerViews
    8) Set the adapters for the RecyclerViews
    Assistance Received: None
    ********************************************************************* */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Call the super.onCreate method and pass savedInstanceState to it
        super.onCreate(savedInstanceState);

        //Set the content view to the domino_layout file
        setContentView(R.layout.domino_layout);
        //Initialize the UI elements
        overall_layout = findViewById(R.id.overall_layout);
        messageBoard = findViewById(R.id.message_board);
        askYesNo = findViewById(R.id.askYesNo);
        boardDisplay = findViewById(R.id.board_display);
        yesNoTitle = findViewById(R.id.questionTextView);
        table_display = findViewById(R.id.table_display);
        invalidPath = findViewById(R.id.invalidpath);
        fileName = findViewById(R.id.fileName);
        fileNameTXT = findViewById(R.id.fileNameTXT);
        buttonContainer = findViewById(R.id.buttonContainer);

        //Set the visibility of the UI elements
        boardDisplay.setVisibility(View.VISIBLE);
        messageBoard.setVisibility(View.VISIBLE);
        table_display.setVisibility(View.INVISIBLE);
        askYesNo.setVisibility(View.INVISIBLE);
        fileName.setVisibility(View.INVISIBLE);
        invalidPath.setVisibility(View.INVISIBLE);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);


        // Initialize the RecyclerViews
        stackSection1RecyclerView = findViewById(R.id.stack_section_1_recycler_view);
        handRecyclerView = findViewById(R.id.hand_recycler_view);

        // Initialize the lists of stack and hand tiles
        stackTiles = new ArrayList<>();
        handTiles = new ArrayList<>();
        mainController = new Controller(this);
        intent = getIntent();

        // Get the filepath and round wins passed from the previous activity and start or load the
        // game accordingly
        String filepath = intent.getStringExtra("filepath");
        // Get the round wins passed from the previous activity for the human player
        int humanRoundWins = intent.getIntExtra("humanRoundWins", 0);
        // Get the round wins passed from the previous activity for the computer player
        int computerRoundWins = intent.getIntExtra("computerRoundWins", 0);
        // Start or load the game accordingly
        if (filepath == null) {
            // Start a new game
            mainController.startGame(humanRoundWins, computerRoundWins);
        } else {
            // Load a saved game
            File file = new File(getExternalFilesDir(null), filepath);
            mainController.loadGame(file);
        }

        // Populate the lists of stack and hand tiles from the players in the game
        List<Player> players = mainController.getPlayers();

        // Add the tiles from the stack of each player to the stackTiles list
        for (Player player : players) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

        // Add the tiles from the hand of the current player to the handTiles list
        Player cPlayer = mainController.getCurrentPlayer();
        for (Tile tile : cPlayer.getHand()) {
            handTiles.add(tile);
        }

        // Initialize the adapters
        stackAdapter = new DominoAdapter(this, stackTiles, mainController, true);
        handAdapter = new DominoAdapter(this, handTiles, mainController, false);

        // Set the RecyclerViews' layout managers
        stackSection1RecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        handRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Set the adapters to the RecyclerViews
        stackSection1RecyclerView.setAdapter(stackAdapter);
        handRecyclerView.setAdapter(handAdapter);


    }


    // The RecyclerView adapter for the stack and hand RecyclerViews
    private RecyclerView stackSection1RecyclerView, handRecyclerView;

    // The adapters for the stack and hand RecyclerViews
    private DominoAdapter stackAdapter, handAdapter;
    // The lists of stack and hand tiles
    private List<Tile> stackTiles, handTiles;
    // The main controller
    private Controller mainController;

    // The intent used to start the activity and get the filepath and round wins passed from the
    // previous activity
    private Intent intent;

    // The TextViews used to display the message board and the question
    private TextView messageBoard;

    // The LinearLayouts used to display the board and the table
    private LinearLayout boardDisplay;

    // The LinearLayout used to display the table
    private LinearLayout table_display;

    // The RelativeLayout used to display the question
    private RelativeLayout askYesNo;

    // The ImageButtons used to display the yes and no buttons
    private ImageButton yesButton;
    // The ImageButtons used to display the yes and no buttons
    private ImageButton noButton;
    // The TextView used to display the question
    private TextView yesNoTitle;
    // The LinearLayout used to display the file name

    private LinearLayout fileName;
    // The EditText used to get the file name

    private  EditText fileNameTXT;
    // The TextView used to display the invalid path message

    private TextView invalidPath;

    // The LinearLayout used to display the buttons
    private LinearLayout buttonContainer;
    // The overall layout of the activity
    private FrameLayout overall_layout;
    // The boolean used to determine if the layout was tapped
    private boolean tap;
}