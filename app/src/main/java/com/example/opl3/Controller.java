package com.example.opl3;


import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controller implements Parcelable {
    private Tile stackSelected;
    private Tile handSelected;
    private Tournament tournament;

    private hand activity;

    public Controller(hand activity){
        this.activity = activity;
        tournament = new Tournament(this);
    }

    protected Controller(Parcel in) {
        stackSelected = in.readParcelable(Tile.class.getClassLoader());
        handSelected = in.readParcelable(Tile.class.getClassLoader());
        tournament = in.readParcelable(Tournament.class.getClassLoader());
    }

    public static final Creator<Controller> CREATOR = new Creator<Controller>() {
        @Override
        public Controller createFromParcel(Parcel in) {
            return new Controller(in);
        }

        @Override
        public Controller[] newArray(int size) {
            return new Controller[size];
        }
    };

    public void startGame(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tournament.start_new_tournament();
                // code to handle the result of start_new_tournament() on the new thread
            }
        });
        thread.start();
    }

    public void selectStackTile(Tile tile){
        stackSelected = tile;
    }

    public void selectHandTile(Tile tile){
        handSelected = tile;
    }

    public Tile getStackSelected(){
        return stackSelected;
    }


    public Tile getHandSelected(){
        return handSelected;
    }

    public Tournament getTournament(){
        return tournament;
    }

    public void newHand(){

    }

    public void resetHandSelected(){
        handSelected = null;
    }

    public void resetStackSelected(){
        stackSelected = null;
    }


    public void notifyNewTurn(){
        List<Tile> stackTiles = new ArrayList<>();
        for (Player player : tournament.getPlayers()) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

        List<Tile> handTiles = new ArrayList<>();
        Player cPlayer = tournament.getCurrentPlayer();
        for (Tile tile : cPlayer.getHand()) {
            handTiles.add(tile);
        }


        while (activity.getStackAdapter() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        activity.getStackAdapter().setTiles(stackTiles);
        activity.getHandAdapter().setTiles(handTiles);
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.getStackAdapter().notifyDataSetChanged();
                        activity.getHandAdapter().notifyDataSetChanged();
                    }
                }
        );
    }

    public void notifyEndHand(Map<String, Integer> finalScores) {
        Intent Intent = new Intent(activity.getApplicationContext(), drawScores.class);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(stackSelected, flags);
        dest.writeParcelable(handSelected, flags);
        dest.writeParcelable(tournament, flags);
    }
}

