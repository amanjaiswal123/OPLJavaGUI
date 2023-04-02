package com.example.opl3;

import java.util.List;

public class computerPlayer extends Player {
    public computerPlayer() {
        super();
    }
    @Override
    public List<Object> getValidMove(List<Player> players, List<Object> recMove) {
        if (recMove.get(0).equals("pass")) {
            System.out.println("\nThe Computer Chose to Pass because there are no Valid Moves.");
            return recMove;
        }
        System.out.println("\nThe Computer Chose to Play " + recMove.get(0) + " on " + recMove.get(1) + " because it has a difference of " + recMove.get(2) + " which is the lowest difference move on an opponent's stack.");
        return recMove;
    }

    @Override
    public String savePlayer() {
        String string = "Computer:\n";
        string += "   Stacks: ";
        for (Tile tile : getStack()) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Boneyard: ";
        for (Tile tile : getBoneyard()) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Hand: ";
        for (Tile tile : getHand()) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Score: " + getScore() + "\n";
        string += "   Rounds Won: " + getRoundsWon() + "\n";

        return string;
    }
}
