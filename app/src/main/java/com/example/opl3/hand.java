package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class hand extends AppCompatActivity {
    private RecyclerView stackSection1RecyclerView, handRecyclerView;
    private DominoAdapter stackAdapter, handAdapter;
    private List<Tile> stackTiles, handTiles;
    private Controller mainController;

    private Intent intent;

    private Player cPlayer;

    static hand INSTANCE;

    private TextView messageBoard;

    private LinearLayout boardDisplay;

    private LinearLayout table_display;


    private FrameLayout overallLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.domino_layout); // Change this to the correct layout file for the hand activity
        overallLayout = findViewById(R.id.overall_layout);
        messageBoard = findViewById(R.id.message_board);
        boardDisplay = findViewById(R.id.board_display);
        boardDisplay.setVisibility(View.VISIBLE);
        table_display = findViewById(R.id.table_display);
        table_display.setVisibility(View.INVISIBLE);

        // Initialize the RecyclerViews
        stackSection1RecyclerView = findViewById(R.id.stack_section_1_recycler_view);
        handRecyclerView = findViewById(R.id.hand_recycler_view);

        // Initialize the lists of stack and hand tiles
        stackTiles = new ArrayList<>();
        handTiles = new ArrayList<>();
        Intent intent = getIntent();
        mainController = new Controller();
        mainController.setActivity(this);
        mainController.startGame();
        List<Player> players = mainController.getTournament().getPlayers();

        for (Player player : players) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

        Player cPlayer = mainController.getTournament().getCurrentPlayer();
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

        Log.d("HAND_ACTIVITY", "stackTiles size: " + stackTiles.size());
        Log.d("HAND_ACTIVITY", "handTiles size: " + handTiles.size());



            // Add your tiles to the stackTiles and handTiles lists, then call the adapters' notifyDataSetChanged() method
            // stackTiles.add(...);
            // stackSection1Adapter.notifyDataSetChanged();

            // handTiles.add(...);
    }

    public static hand getActivityInstance()
    {
        return INSTANCE;
    }

    public Controller getData()
    {
        return this.mainController;
    }

    public void setStackTiles(List<Tile> stackTiles) {
        this.stackTiles = stackTiles;
    }

    public void setHandTiles(List<Tile> handTiles) {
        this.handTiles = handTiles;
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
}