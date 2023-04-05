package com.example.opl3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class humanPlayer extends Player implements Parcelable {

    public humanPlayer(){
        super();
    }

    protected humanPlayer(Parcel in) {
    }

    public static final Creator<humanPlayer> CREATOR = new Creator<humanPlayer>() {
        @Override
        public humanPlayer createFromParcel(Parcel in) {
            return new humanPlayer(in);
        }

        @Override
        public humanPlayer[] newArray(int size) {
            return new humanPlayer[size];
        }
    };

    @Override
    public List<Object> getValidMove(List<Player> players, List<Object> recMove, Controller mainController) {
        boolean askUserRecMove;
        mainController.ResetYesNoPrompt();
        mainController.notifyaskRecMove();
        while (mainController.getUserYesNo() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        askUserRecMove = mainController.getUserYesNo() == "y";
        mainController.notifyReciviedPass();
        String message;
        if (recMove.get(0).equals("pass") && askUserRecMove) {
            message = "There are no valid moves. You must pass.";
            System.out.println(message);
            mainController.notifyReciviedRecMove(message);
        }
        if (askUserRecMove && !recMove.get(0).equals("pass")) {
            message = "The Best Move is " + recMove.get(0) + " on " + recMove.get(1) + " because it has a difference of " + recMove.get(2) + " which is the lowest difference move on an opponent's stack.";
            System.out.println(message);
            mainController.notifyReciviedRecMove(message);
        }
        List<Object> move = this.getMove(players, recMove, mainController);
        if (!move.get(0).equals("pass")) {
            Tile handTile = (Tile) move.get(0);
            Tile stackTile = (Tile) move.get(1);
            if (checkValidMove(handTile, stackTile)) {
                return move;
            } else {
                System.out.println("Invalid move. Please try again.");
                return this.getValidMove(players, recMove, mainController);
            }
        }
        else{
            if (recMove.get(0).equals("pass")) {
                return move;
            } else {
                System.out.println("Cannot Pass when valid moves available.");
                return this.getValidMove(players, recMove, mainController);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        List<Tile> stack = getStack();
        Parcelable[] parcelableArray = stack.toArray(new Parcelable[stack.size()]);
        dest.writeParcelableArray(parcelableArray, flags);
        List <Tile> boneyard = getBoneyard();
        Parcelable[] parcelableArray2 = boneyard.toArray(new Parcelable[boneyard.size()]);
        dest.writeParcelableArray(parcelableArray2, flags);
        List <Tile> hand = getHand();
        Parcelable[] parcelableArray3 = hand.toArray(new Parcelable[hand.size()]);
        dest.writeParcelableArray(parcelableArray3, flags);
        dest.writeInt(getScore());
        dest.writeInt(getRoundsWon());
    }
}
