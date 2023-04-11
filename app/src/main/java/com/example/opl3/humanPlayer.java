package com.example.opl3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class humanPlayer extends Player {

    public humanPlayer(){

        // The human player class is a subclass of the player class and is used to represent a
        // human player in the game. It overrides the getMove method to get the move from the
        // user and the getValidMove method to check if the move is valid.


        //Call the superclass constructor from the player class
        super();
    }

    /* *********************************************************************
    Function Name: getValidMove
    Purpose: To determine the user's selected move in a game of domino's.
    Parameters:
    players[], a List passed by value. It holds the Player objects in the game.
    recMove[], a List passed by value. It holds the recommended move for the user.
    mainController, an object of the Controller class. It is passed by reference and is used to communicate with the GUI and provide notifications to the user.
    Return Value: A List of two Tile objects or a String "pass". The first Tile object represents the user's selected hand tile, and the second Tile object represents the user's selected stack tile. If the user decides to pass, the return value will be a List containing only one element, the String "pass".
    Algorithm:
    1) Check if the user wants to accept the recommended move.
    2) If the user does not accept the recommended move, prompt the user for their move.
    3) Check the validity of the user's selected move.
    4) If the move is invalid, repeat from step 2.
    5) If the move is valid, return the user's selected move.
    Assistance Received: None.
    ********************************************************************* */
    @Override
    public List<Object> getValidMove(List<Player> players, List<Object> recMove, Controller mainController) {
        // Check if the user wants to accept the recommended move.

        // The boolean variable askUserRecMove is used to determine if the user wants to accept the
        // recommended move.
        boolean askUserRecMove;

        //Reset the yes/no prompt in the mainController to make sure previous input are not used.
        mainController.ResetYesNoPrompt();
        //Notify the mainController that the model is waiting for a yes/no response from the user.
        mainController.notifyaskRecMove();
        //Wait for the user to respond to the yes/no prompt.
        while (mainController.getUserYesNo() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Set the boolean variable askUserRecMove to true if the user wants to
        // accept the recommended move.
        askUserRecMove = mainController.getUserYesNo() == "y";
        //Intialize the message variable to null.
        String message = null;
        //Set the message variable to the appropriate message based on the
        // user's response to the yes/no prompt.
        if (recMove.get(0).equals("pass") && askUserRecMove) {
            //If the user wants to accept the recommended move and there are no valid moves,
            // set the message variable to the appropriate message.
            message = "There are no valid moves. You must pass.";
        }
        //If the user wants to accept the recommended move and there are valid moves,
        // set the message variable to the appropriate message.
        if (askUserRecMove && !recMove.get(0).equals("pass")) {
            //If the user wants to accept the recommended move and there are valid moves, set the message variable to the appropriate message.
            message = "The Best Move is " + recMove.get(0) + " on " + recMove.get(1) + " because it has a difference of " + recMove.get(2) + " which is the lowest difference move on an opponent's stack.";
        }
        //If the user does not want to accept the recommended move, set the message variable to
        // the appropriate message.
        System.out.println(message);
        //Notify the mainController model has received the data from the view.
        mainController.notifyReciviedRecMove(message);
        List<Object> move = this.getMove(players, recMove, mainController);
        //Wait for the view to update the model
        if (!move.get(0).equals("pass") && !recMove.get(0).equals("pass")) {
            //If the user did not pass and the recommended move was not a pass,
            // Get the user's selected hand tile.
            Tile handTile = (Tile) move.get(0);
            //Get the user's selected stack tile.
            Tile stackTile = (Tile) move.get(1);
            //Check if the user's selected move is valid.
            if (checkValidMove(handTile, stackTile)) {
                //If the user's selected move is valid, return the user's selected move.
                return move;
            } else {
                //If the user's selected move is invalid, notify the user and repeat the process.
                System.out.println("Invalid move. Please try again.");
                //Recursively call the getValidMove method to get the user's selected move.
                return this.getValidMove(players, recMove, mainController);
            }
        }
        //If the user passed or the recommended move was a pass, return the user's selected move.
        else{
            //If the user passed or the recommended move was a pass, return the user's selected move.
            if (recMove.get(0).equals("pass") && move.get(0).equals("pass")) {
                return move;
            //If the user passed and the recommended move was not a pass,
                // notify the user and repeat the process.
            } else {
                System.out.println("Cannot Pass when valid moves available.");
                //Notify the mainController that the user has entered an invalid pass.
                mainController.notifyInvalidPass();
                //Recursively call the getValidMove method to get the user's selected move.
                return this.getValidMove(players, recMove, mainController);
            }
        }
    }

    /* *********************************************************************
    Function Name: savePlayer
    Purpose: The purpose of this function is to save the information of the human player to a string
    Parameters: None
    Return Value: A string that holds the information of the human player
    Algorithm:
    1) Initialize a string with the text "Human:\n"
    2) Add the information of the player's stack by concatenating each tile's string representation to the string.
    3) Add the information of the player's boneyard in the same way as the stack.
    4) Add the information of the player's hand in the same way as the stack and boneyard.
    5) Add the player's score and rounds won to the string.
    6) Return the final string.
    Assistance Received: None
    ********************************************************************* */
    @Override
    public String savePlayer() {
        //Initialize a string with the text "Human:\n"
        String string = "Human:\n";
        //Add the information of the player's stack by concatenating each tile's string representation to the string.
        string += "   Stacks: ";
        //Iterate through the player's stack.
        for (Tile tile : this.getStack()) {
            //Add the tile's string representation to the string.
            string += tile.toString().substring(1, 4) + " ";
        }
        //Add the information of the player's boneyard in the same way as the stack.
        string += "\n";
        //Populate the string with the player's boneyard.
        string += "   Boneyard: ";
        //Iterate through the player's boneyard.
        for (Tile tile : this.getBoneyard()) {
            //Add the tile's string representation to the string.
            string += tile.toString().substring(1, 4) + " ";
        }
        //Add the information of the player's hand in the same way as the stack and boneyard.
        string += "\n";
        //Populate the string with the player's hand.
        string += "   Hand: ";
        for (Tile tile : this.getHand()) {
            //Add the tile's string representation to the string.
            string += tile.toString().substring(1, 4) + " ";
        }
        //Add the player's score and rounds won to the string.
        string += "\n";
        //Add the player's score won to the string.
        string += "   Score: " + this.getScore() + "\n";
        //Add the  rounds won to the string.
        string += "   Rounds Won: " + this.getRoundsWon() + "\n";

        return string;
    }
}
