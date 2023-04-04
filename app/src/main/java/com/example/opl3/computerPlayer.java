package com.example.opl3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class computerPlayer extends Player implements Parcelable {
    public computerPlayer(){super();}

    protected computerPlayer(Parcel in) {
    }

    public static final Creator<computerPlayer> CREATOR = new Creator<computerPlayer>() {
        @Override
        public computerPlayer createFromParcel(Parcel in) {
            return new computerPlayer(in);
        }

        @Override
        public computerPlayer[] newArray(int size) {
            return new computerPlayer[size];
        }
    };

    @Override
    public List<Object> getValidMove(List<Player> players, List<Object> recMove, Controller mainController) {
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
