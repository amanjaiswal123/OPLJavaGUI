package com.example.opl3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.*;

public class Player {
    // The player class is used to create a player object. The player object is used to store the player's data including
    // their player id, color, boneyard, hand, stacks, score, and rounds won.


    public Player() {
        // The player's ID used to identify the player
        this.playerID = "";
        // The player's color used to identify the player's tiles
        this.color = "";
        // The player's boneyard is a list of tile objects in the player's boneyard. A list of tile objects
        this.boneyard = new ArrayList<>();
        // The player's hand is a list of tile objects inplayer's hand
        this.hand = new ArrayList<>();
        // The player's stack is a list of tile objects in the stacks
        this.stack = new ArrayList<>();
        // Represents the score of the player per round. Is reset every new round
        this.score = 0;
        // Represents the number of rounds the player has won
        this.roundsWon = 0;
    }

    // Getters and setters for the class members
    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerId(String playerID_) {
        this.playerID = playerID_;

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Tile> getBoneyard() {
        return boneyard;
    }

    public void setBoneyard(List<Tile> boneyard) {
        this.boneyard = boneyard;
    }

    public List<Tile> getHand() {
        return hand;
    }

    public void setHand(List<Tile> hand) {
        this.hand = hand;
    }

    public List<Tile> getStack() {
        return stack;
    }

    public void setStack(List<Tile> stack) {
        this.stack = stack;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }


    /* *********************************************************************
    Function Name: generateBoneyard
    Purpose: To generate a boneyard of unique tiles for a player
    Parameters:
    n, an integer. Represents the maximum value of tile to be generated
    Return Value: None
    Algorithm:
    1) Loop through x from 0 to n inclusive
    2) Loop through y from 0 to x inclusive
    3) Create a new Tile with values x and x - y, and associate it with the player
    4) Add the tile to the boneyard
    Assistance Received: None
    ********************************************************************* */
    public void generateBoneyard(int n) {
        // Generate a boneyard of size doubleSetLength

        // Loop through x from 0 to n inclusive
        for (int x = 0; x <= n; x++) {
            // Loop through y from 0 to x inclusive
            for (int y = 0; y <= x; y++) {
                // Create a new Tile with values x and x - y, and associate it with the player
                Tile cTile = new Tile(x, x - y, this);
                // Add the tile to the boneyard
                boneyard.add(cTile);
            }
        }
    }

    /* *********************************************************************
    Function Name: moveFromBoneyardToHandN
    Purpose: To move N tiles from the boneyard to the player's hand
    Parameters:
    n, an integer. The number of tiles to be moved from boneyard to hand
    Return Value: None
    Algorithm:
    1) Loop through x from 0 to n
    2) Add the first tile from the boneyard to the hand
    3) Remove the first tile from the boneyard
    Assistance Received: None
    ********************************************************************* */
    public void moveFromBoneyardToHandN(int n) {
        // Move the double set length amount of tiles from the boneyard to the player's hand
        for (int x = 0; x < n; x++) {
            // Add the first tile from the boneyard to the hand and remove it from the boneyard
            hand.add(boneyard.remove(0));
        }
    }

    /* *********************************************************************
    Function Name: moveFromHandToStackN
    Purpose: To move N tiles from the player's hand to the player's stack
    Parameters:
    n, an integer. The number of tiles to be moved from hand to stack
    Return Value: None
    Algorithm:
    1) Loop through x from 0 to n
    2) Add the first tile from the hand to the stack
    3) Remove the first tile from the hand
    Assistance Received: None
    ********************************************************************* */
    public void moveFromHandToStackN(int n) {
        // Move the double set length amount of tiles from the player's hand to the player's stack
        for (int x = 0; x < n; x++) {
            // Add the first tile from the hand to the stack and remove it from the hand
            stack.add(hand.remove(0));
        }
    }


    public void shuffleBoneyard() {
        Collections.shuffle(boneyard);
    }

    /* *********************************************************************
    Function Name: createNewPlayer
    Purpose: To create a new player with specified player ID and color, generate and shuffle boneyard, initialize hand and stack
    Parameters:
    playerID_, a string. The unique player ID to be assigned to the player
    color, a string. The unique player color to be assigned to the player
    Return Value: None
    Algorithm:
    1) Set player ID and color
    2) Generate a boneyard of size doubleSetLength (6)
    3) Initialize the player's hand to an empty list
    4) Shuffle the boneyard
    5) Move 6 tiles from the boneyard to the player's hand
    6) Initialize the player's stack to an empty list
    7) Move 6 tiles from the player's hand to the player's stack
    8) Set the player's score to 0
    9) Set the player's rounds won to 0
    Assistance Received: None
    ********************************************************************* */

    public void createNewPlayer(String playerID_, String color) {
        // Assign a unique player ID
        this.setPlayerId(playerID_);
        // Assign a unique player color
        this.setColor(color);
        // Generate a boneyard of size doubleSetLength
        this.generateBoneyard(6);
        // Initialize the player's hand to an empty list
        this.hand.clear();
        // Shuffle the boneyard
        this.shuffleBoneyard();
        // Move the double set length amount of tiles from the boneyard to the player's hand
        this.moveFromBoneyardToHandN(6);
        // Initialize the player's stack to an empty list
        this.stack.clear();
        // Move the double set length amount of tiles from the player's hand to the player's stack
        this.moveFromHandToStackN(6);
        // Set the player's score to 0
        score = 0;
        // Set the player's rounds won to 0
        roundsWon = 0;
    }

    /* *********************************************************************
    Function Name: loadPlayer
    Purpose: To load an existing player with specified attributes and move tiles accordingly
    //from a serialized file
    Parameters:
    playerID_, a string. The unique player ID to be assigned to the player
    color_, a string. The unique player color to be assigned to the player
    boneyard_, a list of Tile objects. The player's boneyard to be set
    hand_, a list of Tile objects. The player's hand to be set
    stack_, a list of Tile objects. The player's stack to be set
    score_, an integer. The player's score to be set
    roundsWon_, an integer. The player's rounds won to be set
    Return Value: None
    Algorithm:
    1) Set player ID and color
    2) Set the boneyard with the given boneyard_
    3) If the size of the boneyard_ is 28, shuffle the boneyard, move 6 tiles from boneyard to hand, and move 6 tiles from hand to stack
    4) Else, set the hand and stack with the given hand_ and stack_
    5) Set the player's score and rounds won to the given score_ and roundsWon_
    Assistance Received: None
    ********************************************************************* */


    public void loadPlayer(String playerID_, String color_, List<Tile> boneyard_, List<Tile> hand_, List<Tile> stack_, int score_, int roundsWon_) {
        // Assign a unique player ID
        this.setPlayerId(playerID_);
        // Assign a unique player color
        this.setColor(color_);
        // Generate a boneyard of size doubleSetLength
        this.setBoneyard(boneyard_);
        // Initialize the player's hand to an empty list
        // Move the double set length amount of tiles from the boneyard to the player's hand
        if (boneyard_.size() == 28) {
            // Shuffle the boneyard
            this.shuffleBoneyard();
            this.moveFromBoneyardToHandN(6);
            this.moveFromHandToStackN(6);
        } else {
            this.setHand(hand_);
            this.setStack(stack_);
        }
        // Move the double set length amount of tiles from the player's hand to the player's stack

        // Set the player's score to 0
        this.score = score_;
        // Set the player's rounds won to 0
        this.roundsWon = roundsWon_;
    }


    /* *********************************************************************
    Function Name: checkValidMove
    Purpose: To determine if a move is valid based on the current hand tile and stack tile
    Parameters:
    handTile, a Tile object passed by value. It represents the tile in the player's hand
    stackTile, a Tile object passed by value. It represents the tile in the player's stack
    Return Value: A boolean value indicating if the move is valid or not
    Algorithm:
    1) Check if the stackTile is a double
    a) If handTile is a double, move is valid if handTile > stackTile
    b) If handTile is not a double, move is valid if handTile >= stackTile
    2) If the stackTile is not a double
    a) If handTile is a double, the move is always valid
    b) If handTile is not a double, move is valid if handTile >= stackTile
    Assistance Received: None
    ********************************************************************* */
    public boolean checkValidMove(Tile handTile, Tile stackTile) {
        // By default, validMove is false until proven otherwise by the if statements below
        boolean validMove = false;

        if (stackTile.isDouble()) {
            if (handTile.isDouble()) {
                if (handTile.compareTo(stackTile) > 0) {
                    // If the hand tile is a double and the stack tile is a double,
                    // we can use it if the hand tile is greater than the stack tile
                    validMove = true;
                }
            } else {
                if (handTile.compareTo(stackTile) >= 0) {
                    // If the hand tile is not a double and the stack tile is a double,
                    // the hand tile must be greater than or equal to the stack tile
                    validMove = true;
                }
            }
        } else {
            if (handTile.isDouble()) {
                // If the hand tile is a double and the stack tile is not a double, the move is valid no matter what
                validMove = true;
            } else {
                if (handTile.compareTo(stackTile) >= 0) {
                    // If the hand tile is not a double and the stack tile is not a double,
                    // the hand tile must be greater than or equal to the stack tile
                    validMove = true;
                }
            }
        }

        return validMove;
    }

    public List<Object> getValidMove(List<Player> players, List<Object> recMove, Controller mainController) {
        return new ArrayList<>();
    }

    public String savePlayer(){
        return "";
    }


    /* *********************************************************************
    Function Name: recommendMove
    Purpose: To recommend a valid move for a player given a list of players and their current stacks of tiles
    Parameters:
    players, a list of Player objects passed by value. It holds the current state of the game
    Return Value: A list of recommended moves, each represented as a list of 3 objects: a Tile object representing the tile to play from the player's hand,
    a Tile object representing the tile to play on the stack, and an integer representing the difference between the two tiles
    Algorithm:
    1) Create an empty list to hold all valid moves
    2) Iterate through each tile in the player's hand
    3) Iterate through each player in the list of players
    4) Iterate through each tile in the current player's stack
    5) Check if the move is valid by calling the checkValidMove function with the current hand tile and stack tile as arguments
    6) If the move is valid, calculate the difference between the two tiles and add the three objects representing the move to the list of valid moves
    7) Return the list of valid moves
    Assistance Received: none
    ********************************************************************* */
    public List<Object> recommendMove(List<Player> players) {
        // Create an empty list to hold all valid moves
        List<List<Object>> validMoves = new ArrayList<>();
        // Iterate through each tile in the player's hand
        for (Tile handTile : hand) {
            // Iterate through each player in the list of players
            for (Player currentPlayer : players) {
                // Iterate through each tile in the current player's stack
                for (Tile stackTile : currentPlayer.getStack()) {
                    // Check if the move is valid by calling the checkValidMove function with
                    // the current hand tile and stack tile as arguments
                    if (checkValidMove(handTile, stackTile)) {
                        // If the move is valid, calculate the difference between the two tiles
                        int difference = handTile.difference(stackTile);
                        // Add the three objects representing the move to the list of valid moves
                        List<Object> move = new ArrayList<>();
                        // Add the hand tile to the move
                        move.add(handTile);
                        // Add the stack tile to the move
                        move.add(stackTile);
                        // Add the difference between the two tiles to the move
                        move.add(difference);
                        // Add the move to the list of valid moves
                        validMoves.add(move);
                    }
                }
            }
        }

        //If there are no valid moves, return an a list containing the string "pass"
        if (validMoves.isEmpty()) {
            List<Object> passMove = new ArrayList<>();
            passMove.add("pass");
            return passMove;
        }

        // Sort the list of valid moves by the difference between the two tiles if the list of
        // valid moves is not empty
        validMoves.sort((move1, move2) -> Integer.compare((int) move1.get(2), (int) move2.get(2)));

        //Make the first move in the list of valid moves the best move by default
        List<Object> bestMove = validMoves.get(0);

        // Iterate through the list of valid moves, it will select the move with the lowest
        // difference that is on the opponent stack if there is one, if there is no move on
        //the opponent's stack, it will select the move with the lowest difference on its own stack
        for (List<Object> move : validMoves) {
            // Get the stack tile from the current move
            Tile stackTile = (Tile) move.get(1);
            // Get the player ID of the player who owns the stack tile
            String stackTilePlayerId = stackTile.getPlayer().getPlayerID();

            // If the player ID of the player who owns the stack tile is not the same as the
            // player ID of the current player,
            if (!stackTilePlayerId.equals(playerID)) {
                // Set the best move to the current move and break out of the loop
                bestMove = move;
                // Break out of the loop
                break;
            }
        }

        // Return the best move
        return bestMove;
    }


    /* *********************************************************************
    Function Name: displayBoneyard
    Purpose: To display the current state of the boneyard in a readable format
    Parameters: None
    Return Value: None
    Algorithm:
    1) Iterate through each tile in the boneyard
    2) Call the displayTile method for each tile to output its value
    3) Print a newline character after displaying all tiles
    Assistance Received: None
    ********************************************************************* */
    public void displayBoneyard() {
        //Iterate through the boneyard and display each tile
        for (Tile tile : boneyard) {
            //Display the tile
            tile.displayTile();
        }
        //Print a new line
        System.out.println();
    }

    /* *********************************************************************
    Function Name: displayHand
    Purpose: To display the current state of a player's hand in a readable format
    Parameters: None
    Return Value: None
    Algorithm:
        1) Print the player's ID along with the text "Hand:"
        2) Iterate through each tile in the player's hand
        3) Call the displayTile method for each tile to output its value
        4) Print a newline character after displaying all tiles in the hand
    Assistance Received: None
    ********************************************************************* */

    public void displayHand() {
        //Display the player's hand
        System.out.print("Player " + this.playerID + "'s Hand: ");
        //Iterate through the player's hand and display each tile
        for (Tile tile : hand) {
            //Display the tile
            tile.displayTile();
        }
        //Print a new line
        System.out.println();
    }

    /* *********************************************************************
    Function Name: moveFromHandToBoneyardN
    Purpose: To move the first n tiles from a player's hand to the boneyard
    Parameters:
                n, an integer representing the number of tiles to be moved
    Return Value: None
    Algorithm:
                1) Iterate through the first n tiles in the player's hand
                2) For each tile, remove it from the hand and add it to the boneyard
    Assistance Received: None
    ********************************************************************* */

    public void moveFromHandToBoneyardN(int n) {
        // Iterate through the first n tiles in the player's hand
        for (int x = 0; x < n; x++) {
            // Add the tile to the boneyard
            boneyard.add(hand.remove(0));
        }
    }


    /* *********************************************************************
    Function Name: getMove
    Purpose: To get a valid move from the user, either selecting a hand tile and a stack tile or passing
    Parameters:
                players, a List of Player objects representing all players in the game
                recMove, a List of Strings containing the recommended move for the user
                mainController, a reference to the MainController object handling the game loop and user input
    Return Value: A List of Objects containing the selected hand tile and stack tile, or an empty string if the user chooses to pass
    Algorithm:
                1) Check if the recommended move is not "pass"
                2) If not "pass", prompt the user to select a hand tile and a stack tile
                3) Validate the selected hand tile and stack tile, ensuring they belong to the user's hand and a player's stack, respectively
                4) If the selected tiles are valid, return them as a List of Objects
                5) If the recommended move is "pass", prompt the user to confirm passing
                6) If the user confirms passing, return a List of Objects containing an empty string
                7) If the user does not confirm passing, restart the process from step 1
    Assistance Received: None
    ********************************************************************* */
    public List<Object> getMove(List<Player> players, List<Object> recMove, Controller mainController) {

        //If the recommended move is not "pass" notify the controller that the user needs to
        // select a hand tile and a stack tile
        if (recMove.get(0) != "pass") {
            //Reset the hand variable to make sure no prior input is still stored
            mainController.resetHandSelected();

            //Wait for the user to select a hand tile
            while (mainController.getHandSelected() == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Get the selected hand tile
            String handTileSTR = mainController.getHandSelected().toString();

            //Reset the hand variable to make sure the next time the variable is clear
            mainController.resetHandSelected();

            //Intialize the hand tile variable
            Tile handTile = null;

            //Find the tile in the player's hand that matches the selected tile
            for (Tile c_handTile : hand) {
                if (c_handTile.toString().equals(handTileSTR)) {
                    handTile = c_handTile;
                    break;
                }
            }

            //If the tile is not found, notify the user and restart the process
            if (handTile == null) {
                System.out.println("Invalid input. Please enter a tile from the hand");
                return this.getMove(players, recMove, mainController);
            }


            //Reset the stack variable to make sure no prior input is still stored
            mainController.resetStackSelected();

            //Wait for the user to select a stack tile
            while (mainController.getStackSelected() == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Get the selected stack tile
            String stackTileSTR = mainController.getStackSelected().toString();

            //Reset the stack variable to make sure the next time the variable is clear
            mainController.resetStackSelected();
            //Intialize the stack tile variable
            Tile stackTile = null;
            //Find the tile in the player's stack that matches the selected tile
            for (Player currentPlayer : players) {
                for (Tile c_stackTile : currentPlayer.getStack()) {
                    if (c_stackTile.toString().equals(stackTileSTR)) {
                        stackTile = c_stackTile;
                        break;
                    }
                }
                if (stackTile != null) {
                    break;
                }
            }
            //If the tile is not found, notify the user and restart the process
            if (stackTile == null) {
                System.out.println("Invalid input. Please enter a tile from the stack.");
                return this.getMove(players, recMove, mainController);
            }

            //If the selected tiles are valid, return them as a List of Objects
            List<Object> move = new ArrayList<>();
            //Add the hand tile and the stack tile to the list
            //Add the hand tile to the list
            move.add(handTile);
            //Add the stack tile to the list
            move.add(stackTile);
            return move;
        }
        //If the recommended move is "pass", prompt the user to confirm passing
        else {
            //Reset the yes/no variable to make sure no prior input is still stored
            mainController.ResetYesNoPrompt();
            //Notify the controller that the model is waiting for user input
            mainController.notifyaskPass();

            //Wait for the user to select a stack tile
            while (mainController.getUserYesNo() == null){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Notify the controller the model has received the user input
            mainController.notifyReciviedPass();

            //If the user confirms passing, return a List of Objects containing an empty string
            List<Object> passMove = new ArrayList<>();
            //Add an empty string to the list
            passMove.add("pass");
            return passMove;
        }
    }


    /* *********************************************************************
    Function Name: scoreHand
    Purpose: To calculate the sum of the values of all the tiles in the player's hand
    Parameters: None
    Return Value: The sum of the values of all the tiles in the player's hand, an integer
    Algorithm:
                1) Initialize a sum variable to 0
                2) Iterate through the tiles in the player's hand
                3) Add the sum of the tile's values to the sum variable
                4) Return the sum
    Assistance Received: None
    ********************************************************************* */

    public int scoreHand() {
        //Initialize the sum variable
        int sum = 0;
        //Iterate through the hand and add the sum of each tile to the sum variable
        for (Tile tile : this.hand) {
            //Add the sum of the tile to the sum variable
            sum += tile.sum();
        }

        return sum;
    }


    /* *********************************************************************
    Function Name: scoreStack
    Purpose: To calculate the sum of the values of all the tiles in the player's stack and another player's stack
    Parameters:
            otherStack, a List of Tile objects representing the stack of another player
    Return Value: The sum of the values of all the tiles in the player's stack and the other player's stack, an integer
    Algorithm:
            1) Initialize a sum variable to 0
            2) Iterate through the tiles in the player's stack
            3) If the tile belongs to the current player, add the sum of the tile's values to the sum variable
            4) Iterate through the tiles in the other player's stack
            5) If the tile belongs to the current player, add the sum of the tile's values to the sum variable
            6) Return the sum
    Assistance Received: None
    ********************************************************************* */
    public int scoreStack(List<Tile> otherStack) {
        //Initialize the sum variable
        int sum = 0;

        //Iterate through the stack and add the sum of each tile to the sum variable
        // if the tile belongs to the player
        for (Tile tile : this.stack) {
            //Check if the tile belongs to the player
            if (tile.getPlayer().getPlayerID() == this.playerID) {
                //Add the sum of the tile to the sum variable
                sum += tile.sum();
            }
        }
        //Iterate through the other stack and add the sum of each tile to the sum variable
        for (Tile tile : otherStack) {
            //Check if the tile belongs to the player
            if (tile.getPlayer().getPlayerID() == this.playerID) {
                //Add the sum of the tile to the sum variable
                sum += tile.sum();
            }
        }
        //Return the sum
        return sum;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addRoundWins() {
        this.roundsWon++;
    }


    //The player's ID used to identify the player
    private String playerID;

    //The player's color used to identify the player and their tiles
    private String color;

    //The player's game attributes
    private List<Tile> boneyard;
    private List<Tile> hand;
    private List<Tile> stack;
    private int score;
    private int roundsWon;
}
