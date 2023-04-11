package com.example.opl3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Tile {

    /* *********************************************************************
    Class Name: Tile
    Purpose: To represent a tile object with left and right side values, a player owner,
             and methods to manipulate and compare tiles in a game.
    ********************************************************************* */
    public Tile(int left, int right, Player player) {
        super();
        // The left and right attributes store the value of the left and right side of the tile
        this.left = left;
        this.right = right;
        // The player attribute stores the player object that owns the tile, useful for scoring
        this.player = player;
        // If both sides of the tile are the same, the tile is a double
        this.doubleTile = this.left == this.right;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public boolean isDouble() {
        return this.doubleTile;
    }

    public int getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    @Override
    public String toString() {
        return "|" + this.player.getColor() + this.left + this.right + "|";
    }

    public void displayTile() {
        System.out.print("|" + this.player.getColor() + this.left + this.right + "| ");
    }

    // Override basic operators to make it easier to work with tiles
    public int sum() {
        return this.left + this.right;
    }

    public int difference(Tile other) {
        return (this.left + this.right) - (other.left + other.right);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + left;
        result = prime * result + right;
        return result;
    }



    public boolean equals(Tile obj) {
        if (this.sum() == obj.sum()){
            return true;
        }
        else{
            return false;
        }
    }

    public int compareTo(Tile other) {
        // Compare tiles based on their sum
        return Integer.compare(this.sum(), other.sum());
    }

    public Player getPlayer() {
        return this.player;
    }



    public Object getColor() {
        return this.player.getColor();
    }

    //The left and right attributes store the value of the left and right side of the tile
    private int left;
    private int right;

    //The player attribute stores the player object that owns the tile, useful for scoring
    private Player player;

    //If this tile is selected or not
    private boolean selected;

    //If both sides of the tile are the same, the tile is a double
    private boolean doubleTile;
}
