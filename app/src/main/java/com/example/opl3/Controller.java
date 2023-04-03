package com.example.opl3;


import java.io.Serializable;

public class Controller implements Serializable {
    Tournament tournament;
    public Controller(){
        tournament = new Tournament(this);
    }

    public int startGame(){
        return tournament.start_new_tournament();

    }

    public void newHand(){

    }
}
