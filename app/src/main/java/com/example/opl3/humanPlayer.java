package com.example.opl3;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class humanPlayer extends Player implements Serializable {

    public humanPlayer(){
        super();
    }
    @Override
    public List<Object> getValidMove(List<Player> players, List<Object> recMove) {
        boolean askUserRecMove = this.getValidInput("\nWould you like a recommended move? (Y/N): ", Arrays.asList("Y", "N")).equals("Y");
        if (recMove.get(0).equals("pass") && askUserRecMove) {
            System.out.println("There are no valid moves. You must pass.");
        }
        if (askUserRecMove && !recMove.get(0).equals("pass")) {
            System.out.println("The Best Move is " + recMove.get(0) + " on " + recMove.get(1) + " because it has a difference of " + recMove.get(2) + " which is the lowest difference move on an opponent's stack.\n");
        }
        List<Object> move = this.getMove(players, recMove);
        if (!move.get(0).equals("pass")) {
            Tile handTile = (Tile) move.get(0);
            Tile stackTile = (Tile) move.get(1);
            if (checkValidMove(handTile, stackTile)) {
                return move;
            } else {
                System.out.println("Invalid move. Please try again.");
                return this.getValidMove(players, recMove);
            }
        }
        else{
            if (recMove.get(0).equals("pass")) {
                return move;
            } else {
                System.out.println("Cannot Pass, valid moves available.");
                return this.getValidMove(players, recMove);
            }
        }
    }


    @Override
    public String savePlayer() {
        String string = "Human:\n";
        string += "   Stacks: ";
        for (Tile tile : this.getStack()) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Boneyard: ";
        for (Tile tile : this.getBoneyard()) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Hand: ";
        for (Tile tile : this.getHand()) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Score: " + this.getScore() + "\n";
        string += "   Rounds Won: " + this.getRoundsWon() + "\n";

        return string;
    }
}
