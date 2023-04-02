package com.example.opl3;

import java.util.*;

public class Player {
    // The player class is used to create a player object. The player object is used to store the player's data including
    // their player id, color, boneyard, hand, stacks, score, and rounds won.
    private String playerID;
    private String color;
    private List<Tile> boneyard;
    private List<Tile> hand;
    private List<Tile> stack;
    private int score;
    private int roundsWon;
    private int handOffset;
    private Scanner scanner;

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
        // Used to scroll through the player's hand in gui.hand_listener
        this.handOffset = 0;
        this.scanner = new Scanner(System.in);
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

    public int getHandOffset() {
        return handOffset;
    }

    public void setHandOffset(int handOffset) {
        this.handOffset = handOffset;
    }

    public void generateBoneyard(int n) {
        for (int x = 0; x <= n; x++) {
            for (int y = 0; y <= x; y++) {
                Tile cTile = new Tile(x, x - y, this);
                boneyard.add(cTile);
            }
        }
    }

    public void moveFromBoneyardToHandN(int n) {
        for (int x = 0; x < n; x++) {
            hand.add(boneyard.remove(0));
        }
    }

    public void moveFromHandToStackN(int n) {
        for (int x = 0; x < n; x++) {
            stack.add(hand.remove(0));
        }
    }

    public void shuffleBoneyard() {
        Collections.shuffle(boneyard);
    }

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

    public String savePlayer() {
        String string = "Human:\n";
        string += "   Stacks: ";
        for (Tile tile : stack) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Boneyard: ";
        for (Tile tile : boneyard) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Hand: ";
        for (Tile tile : hand) {
            string += tile.toString().substring(1, 4) + " ";
        }
        string += "\n";
        string += "   Score: " + score + "\n";
        string += "   Rounds Won: " + roundsWon + "\n";

        return string;
    }


    public List<Object> recommendMove(List<Player> players) {
        List<List<Object>> validMoves = new ArrayList<>();

        for (Tile handTile : hand) {
            for (Player currentPlayer : players) {
                for (Tile stackTile : currentPlayer.getStack()) {
                    if (checkValidMove(handTile, stackTile)) {
                        int difference = handTile.difference(stackTile);
                        List<Object> move = new ArrayList<>();
                        move.add(handTile);
                        move.add(stackTile);
                        move.add(difference);
                        validMoves.add(move);
                    }
                }
            }
        }

        if (validMoves.isEmpty()) {
            List<Object> passMove = new ArrayList<>();
            passMove.add("pass");
            return passMove;
        }

        validMoves.sort((move1, move2) -> Integer.compare((int) move1.get(2), (int) move2.get(2)));

        List<Object> bestMove = validMoves.get(0);

        for (List<Object> move : validMoves) {
            Tile stackTile = (Tile) move.get(1);
            String stackTilePlayerId = stackTile.getPlayer().getPlayerID();

            if (!stackTilePlayerId.equals(playerID)) {
                bestMove = move;
                break;
            }
        }

        return bestMove;
    }


    public String getValidInput(String prompt, List<String> validInputs) {
        String input;

        while (true) {
            System.out.print(prompt);
            input = this.scanner.nextLine().trim().toUpperCase();
            //capitlize all elements in validInputs
            for (int i = 0; i < validInputs.size(); i++) {
                validInputs.set(i, validInputs.get(i).toUpperCase());
            }
            if (validInputs.contains(input)) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        return input;
    }

    public void displayBoneyard() {
        for (Tile tile : boneyard) {
            tile.displayTile();
        }
        System.out.println();
    }

    public void displayHand() {
        System.out.print("Player " + this.playerID + "'s Hand: ");
        for (Tile tile : hand) {
            tile.displayTile();
        }
        System.out.println();
    }

    public void moveFromHandToBoneyardN(int n) {
        for (int x = 0; x < n; x++) {
            boneyard.add(hand.remove(0));
        }
    }

    public List<Object> getMove(List<Player> players, List<Object> recMove) {
        List<String> validHandInputs = new ArrayList<>();
        if (recMove.get(0).equals("pass")) {
            validHandInputs.add("pass");
            this.getValidInput("Enter a tile from your hand to play: ", validHandInputs);
            return recMove;
        } else {
            for (Tile handTile : hand) {
                validHandInputs.add(handTile.toString().substring(1, 4));
            }
            String handTileSTR = this.getValidInput("Enter a tile from your hand to play: ", validHandInputs);
            Tile handTile = null;
            for (Tile c_handTile : hand) {
                if (c_handTile.toString().substring(1, 4).equals(handTileSTR)) {
                    handTile = c_handTile;
                    break;
                }
            }
            if (handTile == null) {
                System.out.println("Invalid input. Please enter a tile from the hand");
                return this.getMove(players, recMove);
            }
            List<String> ValidStackInputs = new ArrayList<>();
            for (Player currentPlayer : players) {
                for (Tile stackTile : currentPlayer.getStack()) {
                    ValidStackInputs.add(stackTile.toString().substring(1, 4));
                }
            }
            String stackTileSTR = this.getValidInput("Enter a tile from the stack to play on: ", ValidStackInputs);
            Tile stackTile = null;
            for (Player currentPlayer : players) {
                for (Tile c_stackTile : currentPlayer.getStack()) {
                    if (c_stackTile.toString().substring(1, 4).equals(stackTileSTR)) {
                        stackTile = c_stackTile;
                        break;
                    }
                }
                if (stackTile != null) {
                    break;
                }
            }
            if (stackTile == null) {
                System.out.println("Invalid input. Please enter a tile from the stack.");
                return this.getMove(players, recMove);
            }
            List<Object> move = new ArrayList<>();
            move.add(handTile);
            move.add(stackTile);
            return move;
        }
    }

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

    public int scoreHand() {
        int sum = 0;
        for (Tile tile : this.hand) {
            sum += tile.sum();
        }
        return sum;
    }

    public int scoreStack() {
        int sum = 0;
        for (Tile tile : this.stack) {
            sum += tile.sum();
        }
        return sum;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void clearHand() {
        hand.clear();
    }

    public void addRoundWins() {
        this.roundsWon++;
    }

    public void playTurn(){

    }
}
