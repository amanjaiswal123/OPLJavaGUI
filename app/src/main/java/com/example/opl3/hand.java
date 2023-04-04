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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hand extends AppCompatActivity {
    private RecyclerView stackSection1RecyclerView, handRecyclerView;
    private DominoAdapter stackAdapter, handAdapter;
    private List<Tile> stackTiles, handTiles;
    private Controller mainController;

    private Intent intent;

    private Player cPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainController = new Controller(this);
        mainController.startGame();
        setContentView(R.layout.domino_layout); // Change this to the correct layout file for the hand activity

        // Initialize the RecyclerViews
        stackSection1RecyclerView = findViewById(R.id.stack_section_1_recycler_view);
        handRecyclerView = findViewById(R.id.hand_recycler_view);

        // Initialize the lists of stack and hand tiles
        stackTiles = new ArrayList<>();
        handTiles = new ArrayList<>();
        Intent intent = getIntent();
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

        List<Tile> stackTiles = Collections.singletonList(cPlayer.getStack().get(0));



            // Add your tiles to the stackTiles and handTiles lists, then call the adapters' notifyDataSetChanged() method
            // stackTiles.add(...);
            // stackSection1Adapter.notifyDataSetChanged();

            // handTiles.add(...);
    }

    public void setStackTiles(List<Tile> stackTiles) {
        this.stackTiles = stackTiles;
    }

    public void setHandTiles(List<Tile> handTiles) {
        this.handTiles = handTiles;
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            while (true){
                if (mainController.getTournament().getCurrentPlayer() != cPlayer){
                    break;
                }
            }
            return "result";
        }

        @Override
        protected void onPostExecute(String result) {
            // update UI here with the result
        }
    }


    //Get stackAdapter
    public DominoAdapter getStackAdapter(){
        return stackAdapter;
    }
    //Get handAdapter
    public DominoAdapter getHandAdapter(){
        return handAdapter;
    }

}