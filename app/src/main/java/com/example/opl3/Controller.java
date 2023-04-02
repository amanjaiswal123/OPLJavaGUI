package com.example.opl3;


public class Controller {
    Tournament tournament;
    MainActivity activity;
    public Controller(MainActivity activity_){
        activity = activity_;
        tournament = new Tournament(this);
    }

    public void startGame(){
        tournament.start_new_tournament();
    }

    public void newHand(){

    }
}
