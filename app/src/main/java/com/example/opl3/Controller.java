package com.example.opl3;


import java.io.Serializable;

public class Controller implements Serializable {
    private Tile stackSelected;
    private Tile handSelected;
    private Tournament tournament;

    public Controller(){
        tournament = new Tournament(this);
    }

    public int startGame(){
       return tournament.start_new_tournament();
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

    public void resetSelected(){
        stackSelected = null;
        handSelected = null;
    }


    public String getHandNum() {
        return String.valueOf(tournament.getHandNum());
    }


}
