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
    private RecyclerView stackSection1RecyclerView, handRecyclerView;
    private DominoAdapter stackAdapter, handAdapter;
    private List<Tile> stackTiles, handTiles;
    private Controller mainController;

    private Intent intent;

    private TextView messageBoard;

    private LinearLayout boardDisplay;

    private LinearLayout table_display;

    private RelativeLayout askYesNo;

    private ImageButton yesButton;
    private ImageButton noButton;
    private TextView yesNoTitle;

    private LinearLayout fileName;

    private  EditText fileNameTXT;

    private TextView invalidPath;


    private LinearLayout buttonContainer;

    private FrameLayout overall_layout;
    private boolean tap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.domino_layout); // Change this to the correct layout file for the hand activity
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

        String filepath = intent.getStringExtra("filepath");
        int humanRoundWins = intent.getIntExtra("humanRoundWins", 0);
        int computerRoundWins = intent.getIntExtra("computerRoundWins", 0);
        if (filepath == null) {
            mainController.startGame(humanRoundWins, computerRoundWins);
        } else {
            File file = new File(getExternalFilesDir(null), filepath);
            mainController.loadGame(file);
        }

        List<Player> players = mainController.getPlayers();

        for (Player player : players) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

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

    public void draw_scores(Map<String, Integer> finalScores, String tableTitle, String columnTitle){
        TextView tableTitleView = findViewById(R.id.titleTextView);
        TextView columnTitleView = findViewById(R.id.value_column);
        tableTitleView.setText(tableTitle);
        columnTitleView.setText(columnTitle);
        String p1ID = finalScores.keySet().toArray()[0].toString();
        String p2ID = finalScores.keySet().toArray()[1].toString();
        int p1Score = finalScores.get(p1ID);
        int p2Score = finalScores.get(p2ID);

        TextView p1IDView = findViewById(R.id.player1ID);
        TextView p2IDView = findViewById(R.id.player2ID);

        TextView p1ScoreView = findViewById(R.id.player1Score);
        TextView p2ScoreView = findViewById(R.id.player2Score);

        p1IDView.setText(p1ID);
        p2IDView.setText(p2ID);
        p1ScoreView.setText(Integer.toString(p1Score));
        p2ScoreView.setText(Integer.toString(p2Score));
        hideBoardDisplay();
        showScoreDisplay();
    }

    public void showRecMove() {
        yesNoTitle.setText("Would you like a recommended move?");
        askYesNo.setVisibility(View.VISIBLE);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.setYesNo("y");
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
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

    public void showPass() {
        yesNoTitle.setText("Do you want to pass?");
        askYesNo.setVisibility(View.VISIBLE);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.setYesNo("y");
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.setYesNo("n");
            }
        });
    }

    public void askSaveGame(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    100
            );
        }
        messageBoard.setVisibility(View.INVISIBLE);
        askYesNo.setVisibility(View.VISIBLE);
        yesNoTitle.setText("Would you like to save the game?");
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.setYesNo("y");
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.setYesNo("n");
            }
        });

    }

    public void askFileName(){
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
        fileName.setVisibility(View.VISIBLE);
        fileNameTXT.setVisibility(View.VISIBLE);
        fileNameTXT.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String filePath = fileNameTXT.getText().toString();
                    //filePath = "/storage/emulated/0/seralize5.txt";
                    File file = new File(getExternalFilesDir(null), filePath);
                    if (!file.exists() && !file.isDirectory()) {
                        mainController.setFile(file);
                        finish();
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

    public void askPlayAgain(String winner, int humanRoundWins, int computerRoundWins) {
        Intent intent = new Intent(this, playAgain.class);
        intent.putExtra("humanRoundWins", humanRoundWins);
        intent.putExtra("computerRoundWins", computerRoundWins);
        intent.putExtra("winner", winner);
        startActivity(intent);
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

    public void waitforTap() {

        mainController.resetTap();
        overall_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainController.setTap(true);
            }
        });
    }

    public void showCurrentPlayer() {
        messageBoard.setText("It is " + mainController.getCurrentPlayer().getPlayerID() + "'s turn");
    }
}