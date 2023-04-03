package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class hand extends AppCompatActivity {
    private RecyclerView stackSection1RecyclerView, handRecyclerView;
    private DominoAdapter stackSection1Adapter, handAdapter;
    private List<Tile> stackTiles, handTiles;
    private Controller mainController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domino_layout); // Change this to the correct layout file for the hand activity

        // Initialize the RecyclerViews
        stackSection1RecyclerView = findViewById(R.id.stack_section_1_recycler_view);
        handRecyclerView = findViewById(R.id.hand_recycler_view);

        // Initialize the lists of stack and hand tiles
        stackTiles = new ArrayList<>();
        handTiles = new ArrayList<>();
        Intent intent = getIntent();
        mainController = (Controller) intent.getSerializableExtra("controller");
        List<Player> players = mainController.getTournament().getPlayers();

        for (Player player: players){
            for (Tile tile: player.getBoneyard()){
                stackTiles.add(tile);
            }
        }

        List<Tile> cPlayerHand = players.get(mainController.getTournament().getCurrentPlayer()).getHand();
        for (Tile tile: cPlayerHand){
            handTiles.add(tile);
        }

        // Initialize the adapters
        stackSection1Adapter = new DominoAdapter(this, stackTiles, mainController, true);
        handAdapter = new DominoAdapter(this, handTiles, mainController, false);

        // Set the RecyclerViews' layout managers
        stackSection1RecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        handRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Set the adapters to the RecyclerViews
        stackSection1RecyclerView.setAdapter(stackSection1Adapter);
        handRecyclerView.setAdapter(handAdapter);




        Log.d("HAND_ACTIVITY", "stackTiles size: " + stackTiles.size());
        Log.d("HAND_ACTIVITY", "handTiles size: " + handTiles.size());

        // Add your tiles to the stackTiles and handTiles lists, then call the adapters' notifyDataSetChanged() method
        // stackTiles.add(...);
        // stackSection1Adapter.notifyDataSetChanged();

        // handTiles.add(...);
    }
}