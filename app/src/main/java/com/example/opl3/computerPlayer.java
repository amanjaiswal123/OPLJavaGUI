package com.example.opl3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class computerPlayer extends Player {
    public computerPlayer(){super();}

    @Override
    public List<Object> getValidMove(List<Player> players, List<Object> recMove, Controller mainController) {
        String message;
        if (recMove.get(0).equals("pass")) {
            message = "There are no valid moves. Therefore the computer passed.";
        }
        else {
            message = "The Computer chose " + recMove.get(0) + " on " + recMove.get(1) + " because it has a difference of " + recMove.get(2) + " which is the lowest difference move on an opponent's stack.";
        }
        System.out.println(message);
        mainController.notifyReciviedRecMove(message);
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
