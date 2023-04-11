package com.example.opl3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.System.exit;

public class Tournament  {


    public Tournament(Controller mainController_) {
        //Set the controller object
        mainController = mainController_;
        //Create a new player object
        this.players = new ArrayList<>();
    }

    /*********************************************************************
     Function Name: startNewRound
     Purpose: The purpose of this function is to start a new round of a game.
     It sets up new player objects for the human player and the computer player and assigns them to the players array.
     It also sets the number of rounds won by each player from the previous round.
     Parameters: None
     Return Value: None
     Algorithm:

     Create a new player object for the human player and the computer player
     Set the number of rounds won by each player from the previous round
     Add the human player and the computer player to the players array
     Determine the order of play for the new round
     Assistance Received: None
     ********************************************************************* */

    public void start_new_tournament(int humanRoundWins, int computerRoundWins) {
        // Create a new player object
        handNum = 1;
        // Create a new player object
        Player humanPlayer = new humanPlayer();
        //Set the player name and the color of the player
        humanPlayer.createNewPlayer("Human", "B");
        //Set the number of rounds won by the human player from the previous round
        humanPlayer.setRoundsWon(humanRoundWins);
        // Create a new player object
        Player computerPlayer = new computerPlayer();
        //Set the player name and the color of the player
        computerPlayer.createNewPlayer("Computer", "W");
        //Set the number of rounds won by the computer player from the previous round
        computerPlayer.setRoundsWon(computerRoundWins);
        // Add the human player and the computer player to the players array
        this.players = new ArrayList<>();
        this.players.add(humanPlayer);
        this.players.add(computerPlayer);
        // Determine the order of play for the new round, we are not loading from a saved state
        // so we pass false
        this.determineOrder(false);
        // Start the new round
        this.play_round();
        //Initiate the tournament
        this.endTournament();
        //Initiate Play again sequence
        this.play_again();
    }

    /* *********************************************************************
    Function Name: play_again
    Purpose: To determine the winner of a game and print the results
    Parameters: None
    Return Value: None
    Algorithm:
    1) Check if the scores of players are different
    a) If scores are different, loop through the players and determine the one with the highest score
    b) If scores are equal, print "The Tournament is a Tie!"
    2) Create a map and store the rounds won by each player
    3) Determine the winner of the tournament
    4) Print the results and notify the main controller of the game over
    Assistance Received: none
    ********************************************************************* */

    public void play_again(){
        //Show the tournament results
        System.out.println("\nTournament Results:");
        //Check if the scores of players are different to determine a tie
        if (players.get(0).getScore() != players.get(1).getScore()) {
            //If scores are different, loop through the players and determine the one with
            // the highest score
            boolean first = true;
            Player winner = null;
            for (Player player : players) {
                //If it is the first player, set the winner to the first player
                if (first) {
                    winner = player;
                    //Set the first flag to false
                    first = false;
                }
                //If the score of the current player is greater than the winner's score,
                //and the first flag is false, set the winner to the current player
                else {
                    if (player.getScore() > winner.getScore()) {
                        //Set the winner to the current player
                        winner = player;
                    }
                }
            }
            //Print the winner of the tournament
            System.out.println("Player " + winner.getPlayerID() + " won the round!\n");;
        } else {
            //If scores are equal, print "The Tournament is a Tie!"
            System.out.println("The Tournament is a Tie!");
        }
        //Create a map and store the rounds won by each player
        Map<String, Integer> scores = new HashMap<>();
        //Put the scores of each player in the map
        for (Player player : players) {
            scores.put(player.getPlayerID(), player.getRoundsWon());
            System.out.println("Player " + player.getPlayerID() + " won " + player.getRoundsWon() + " rounds.");
        }
        //Determine the winner of the tournament
        String winner = "";
        //If the scores of the players are equal, the winner is a tie
        if (players.get(0).getRoundsWon() == players.get(1).getRoundsWon()) {
            winner = "Tie";
        //If the score of the first player is greater than the second player, the first player is the winner
        } else if (players.get(0).getRoundsWon() > players.get(1).getRoundsWon()) {
            winner = players.get(0).getPlayerID();
        //If the score of the second player is greater than the first player, the second player is the winner
        } else {
            winner = players.get(1).getPlayerID();
        }
        //Print the results and notify the main controller of the game over
        System.out.println("Thanks for playing!");
        //Notify the main controller of the model is finished
        mainController.notifyGameOver(scores, winner);
    }

    /* *********************************************************************
    Function Name: determineOrder
    Purpose: To determine the order of the players in the game
    Parameters:
    load, a boolean value indicating whether the game is being loaded from a saved state
    Return Value: None
    Algorithm:
    1) Check if the game is being loaded from a saved state
    2) If not, shuffle the boneyard of each player
    3) Draw one tile from the boneyard for each player
    4) Compare the tiles of each player to determine the order
    5) If there are two players with equal tiles, re-shuffle the boneyard and draw tiles again
    6) Repeat step 4 and 5 until the order of players is determined
    Assistance Received: None
    ********************************************************************* */

    public void determineOrder(boolean load) {
        //Initialize the equal flag to true
        boolean equal = true;
        //Loop until the order of players is determined
        while (equal) {
            //Set the equal flag to false
            equal = false;
            //Loop through the players
            System.out.println("\nDetermining Order:");
            for (Player currentPlayer : players) {
                //If we are loading a game we do not want to shuffle the boneyard the first time
                if (load) {
                    //Display the boneyard of the current player
                    System.out.println("Player " + currentPlayer.getPlayerID() + " boneyard");
                    currentPlayer.displayBoneyard();
                    //Draw one tile from the boneyard of the current player
                    currentPlayer.moveFromBoneyardToHandN(1);
                } else {
                    //Display the boneyard of the current player
                    System.out.println("Player " + currentPlayer.getPlayerID() + " boneyard");
                    currentPlayer.displayBoneyard();
                    //Shuffle the boneyard of the current player
                    System.out.println("Shuffling Boneyard...");
                    currentPlayer.shuffleBoneyard();
                    //Display the shuffled boneyard of the current player
                    System.out.println("Player " + currentPlayer.getPlayerID() + " shuffled boneyard");
                    currentPlayer.displayBoneyard();
                    //Draw one tile from the boneyard of the current player
                    currentPlayer.moveFromBoneyardToHandN(1);
                }
            }
            //Set the load flag to false
            load = false;

            //Display the tiles of each player hand
            System.out.println("\nPlayers hands:");
            //Loop through the players and display their hands
            for (Player currentPlayer : players) {
                System.out.print("Player " + currentPlayer.getPlayerID() + " has tile ");
                currentPlayer.displayHand();
            }

            //Compare the tiles of each player to determine the order by sorting the players
            System.out.println("\nComparing Tiles...");
            players.sort((p1, p2) -> p2.getHand().get(0).compareTo(p1.getHand().get(0)));

            //Announce the order of the players
            System.out.println("\nOrder is:");
            for (Player compPlayer : players) {
                System.out.println("Player " + compPlayer.getPlayerID() + " with tile " + compPlayer.getHand().get(0));
            }


            //Check if there are two players with equal tiles
            outerLoop:
            for (Player compPlayer : players) {
                for (Player currentPlayer : players) {
                    //If the tiles are equal and the players are not the same player,
                    // re-shuffle the boneyard and draw tiles again by setting the equal flag to true
                    if (compPlayer.getHand().get(0).equals(currentPlayer.getHand().get(0)) && !compPlayer.equals(currentPlayer)) {
                        System.out.println("\nPlayer " + compPlayer.getPlayerID() + " and Player " + currentPlayer.getPlayerID() + " have equal tiles re-shuffling");
                        //Set the equal flag to true
                        equal = true;
                        //Move all tiles from the hands of the players to the boneyard
                        for (Player currentPlayer2 : players) {
                            currentPlayer2.moveFromHandToBoneyardN(1);
                        }
                        //Break out of the outer loop
                        break outerLoop;
                    }
                }
            }
        }
    }

    /* *********************************************************************
    Function Name: play_round
    Purpose: This function plays the round of the game. The game is played in multiple hands and the winner of each round is determined.
    Parameters: None
    Return Value: None
    Algorithm:
    1) The function first checks if it is the first hand, and if so, it moves 5 cards from the boneyard to each player's hand.
    2) It then prints the current hand number and calls the playHand function.
    3) The function then repeats the same steps for the next hands until all hands are finished.
    4) Once all hands are finished, it declares the winner of the round, adds the round wins to the winner's count, and notifies the mainController of the round end.
    Assistance Received: None
    ********************************************************************* */

    public void play_round() {
        //Notify the main controller that the model has changed hands
        mainController.notifyHandChange();

        //Distribute the correct number of tiles to each player for each hand
        if (handNum == 1) {
            //If the number of tiles in each player's hand is 1,
            // move 5 tiles from the boneyard to each player's hand
            if (players.get(0).getHand().size() == 1 && players.get(1).getHand().size() == 1) {
                for (Player currentPlayer : players) {
                    currentPlayer.moveFromBoneyardToHandN(5);
                }
            }
            System.out.println("\nHand Number: " + String.valueOf(handNum) + "\n");
            //Play the hand
            playHand();
            //Increment the hand number
            handNum += 1;
        }
        //Notify the main controller that the model has changed hands
        mainController.notifyHandChange();
        //If the hand number is 2, repeat the same steps as above
        if (handNum == 2) {
            if (players.get(0).getHand().size() == 0 && players.get(1).getHand().size() == 0) {
                for (Player currentPlayer : players) {
                    currentPlayer.moveFromBoneyardToHandN(6);
                }
            }
            System.out.println("\nHand Number: " + String.valueOf(handNum) + "\n");
            playHand();
            handNum += 1;
        }
        //Notify the main controller that the model has changed hands
        mainController.notifyHandChange();
        //If the hand number is 3, repeat the same steps as above
        if (handNum == 3) {
            if (players.get(0).getHand().size() == 0 && players.get(1).getHand().size() == 0) {
                for (Player currentPlayer : players) {
                    currentPlayer.moveFromBoneyardToHandN(6);
                }
            }
            System.out.println("\nHand Number: " + String.valueOf(handNum) + "\n");
            playHand();
            handNum += 1;
        }
        //Notify the main controller that the model has changed hands
        mainController.notifyHandChange();
        //If the hand number is 4, repeat the same steps as above except move 4 tiles from
        // the boneyard to each player's hand
        if (handNum == 4) {
            if (players.get(0).getHand().size() == 0 && players.get(1).getHand().size() == 0) {
                for (Player currentPlayer : players) {
                    currentPlayer.moveFromBoneyardToHandN(4);
                }
            }
            System.out.println("\nHand Number: " + String.valueOf(handNum) + "\n");
            playHand();
            handNum += 1;
        }
        //If the hand number is 5, initiate the end round sequence
        if (handNum > 4) {
            System.out.println("\nRound Finished Scoring Round\n");
            String winner = "";
            //Create a map of the players and their scores
            Map<String, Integer> finalScores = new HashMap<>();
            for (Player player : players) {
                //Add the player's score to the map
                finalScores.put(player.getPlayerID(), player.getScore());
            }
            //Determine the winner of the round
            if (players.get(0).getScore() > players.get(1).getScore()) {
                players.get(0).addRoundWins();
                winner = players.get(0).getPlayerID();
                System.out.println("Player " + players.get(0).getPlayerID() + " won the round with a score of " + players.get(0).getScore());
            } else if (players.get(0).getScore() < players.get(1).getScore()) {
                players.get(1).addRoundWins();
                winner = players.get(1).getPlayerID();
                System.out.println("Player " + players.get(1).getPlayerID() + " won the round with a score of " + players.get(1).getScore());
            } else {
                System.out.println("The round is a tie!");
            }
            //Pass the final scores and the winner to the main controller
            mainController.notifyRoundEnd(finalScores, winner);
        }
    }

    /* *********************************************************************
    Function Name: displayAllStacks
    Purpose: To display the stacks of all players in the game
    Parameters: None
    Return Value: None
    Algorithm:
    1) Loop through all players in the game
    2) For each player, print the player's ID and their stack of tiles
    3) Display each tile in the stack of the player
    Assistance Received: None
    ********************************************************************* */


    public void displayAllStacks() {
        //Iterate through all players in the game and display their stacks
        for (Player player : this.players) {
            System.out.print("Player " + player.getPlayerID() + "'s Stack: ");
            for (Tile tile : player.getStack()) {
                tile.displayTile();
            }
            System.out.println();
        }
    }

    /* *********************************************************************
    Function Name: displayAllHands
    Purpose: To display the hands of all players in the game
    Parameters: None
    Return Value: None
    Algorithm:
    1) Loop through all players in the game
    2) For each player, print the player's ID and their hand of tiles
    3) Display each tile in the hand of the player
    Assistance Received: None
    ********************************************************************* */
    public void displayAllHands() {
        //Iterate through the players in the game and display their hands
        for (Player player : this.players) {
            System.out.print("Player " + player.getPlayerID() + "'s hand: ");
            for (Tile tile : player.getHand()) {
                tile.displayTile();
            }
            System.out.println();
        }
    }

    /* *********************************************************************
    Function Name: scoreHand
    Purpose: To calculate the scores of the hands of all players in the game
    Parameters: None
    Return Value: A map with player IDs as keys and their scores as values
    Algorithm:
    1) Initialize an empty map called scores
    2) Loop through all players in the game
    3) For each player, call the scoreHand() method to calculate the score of their hand
    4) Add the player's ID and score to the scores map
    5) Return the scores map
    Assistance Received: None
    ********************************************************************* */
    public Map<String, Integer> scoreHand() {
        //Create a map of the players and their scores by iterating through the players
        // and calling the scoreHand() method on each player
        Map<String, Integer> scores = new HashMap<>();
        for (Player player : this.players) {
            scores.put(player.getPlayerID(), player.scoreHand());
        }
        return scores;
    }


    /* *********************************************************************
    Function Name: scoreStacks
    Purpose: To calculate the scores of the stacks of all players in the game
    Parameters: None
    Return Value: A map with player IDs as keys and their scores as values
    Algorithm:
    1) Initialize an empty map called scores
    2) Loop through all players in the game
    3) For each player, find the other player in the game
    4) Call the scoreStack() method with the other player's stack as an argument to calculate the score of the current player's stack
    5) Add the player's ID and score to the scores map
    6) Return the scores map
    Assistance Received: None
    ********************************************************************* */
    public Map<String, Integer> scoreStacks() {
        //Create a map of the players and their scores by iterating through the players
        // and calling the scoreStack() method on each player
        Map<String, Integer> scores = new HashMap<>();
        for (Player player : this.players) {
            Player otherPlayer;
            if (players.indexOf(player) == 0){
                otherPlayer = players.get(1);
            } else {
                otherPlayer = players.get(0);
            }
            List<Tile> otherPlayerStack = otherPlayer.getStack();
            scores.put(player.getPlayerID(), player.scoreStack(otherPlayerStack));
        }
        return scores;
    }

        /* *********************************************************************
        Function Name: playHand
        Purpose: To simulate playing a hand of the game.
        Parameters: None.
        Return Value: None.
        Algorithm:
        1) Set the first variable to true and consecutivePasses variable to 0 and allEmptyHands variable to true.
        2) Iterate over all the players in players List and check if any player's hand size is not equal to 0. If yes, set allEmptyHands to false.
        3) Keep iterating until consecutivePasses becomes equal to the size of the players List or allEmptyHands is set to true.
        4) Within the iteration, set the currentPlayer as the current player of the iteration.
        5) Notify the mainController about the start of a new turn.
        6) If first is true, notify the mainController about the start of a new hand.
        7) Display the player's hand and all stacks.
        8) Get the recommended move and valid move of the current player and store it in a list.
        9) If the move list's first element is not "pass", execute the move by calling executeMove function with handTile and stackTile. Set consecutivePasses to 0.
        10) If the move list's first element is "pass", increment consecutivePasses by 1.
        11) Notify the mainController about the end of the turn.
        12) Notify the mainController about the save game and wait for the user's confirmation. If the user confirms, save the game and exit the program.
        13) After the iteration, set allEmptyHands to true and iterate over all the players in players List and check if any player's hand size is not equal to 0. If yes, set allEmptyHands to false.
        14) Display the final hands and stacks and scores of the hand.
        15) Get the scores for the hand and stacks and add the difference to the score of each player.
        16) Display the cumulative scores of each player.
        17) Clear the hand of each player.
        18) Notify the mainController about the end of the hand with the final scores and winner.
        Assistance Received: None.
        ********************************************************************* */

    public void playHand() {
        //set the first variable to true and consecutivePasses variable to 0 and
        // allEmptyHands variable to true
        boolean first = true;
        int consecutivePasses = 0;
        boolean allEmptyHands = true;
        //Initialize the hand tile variable
        Tile handTile;
        //Check if all the playesr hand are empty
        for (Player c_player : this.players) {
            if (c_player.getHand().size() != 0) {
                allEmptyHands = false;
                break;
            }
        }
        //Initialize the ask save game variable
        boolean askSaveGame = false;
        //While the consecutive passes are less than the number of players and all the hands
        // are not empty
        while (consecutivePasses < players.size() && !allEmptyHands) {
            //Iterate over all the players
            for (Player currentPlayer : players) {
                //Set the current player
                this.currentPlayer = currentPlayer;
                //Notify the main controller about the start of the turn
                mainController.notifyNewTurn();
                //Check if the first variable is true
                if (first){
                    //Notify the main controller about the start of the hand
                    mainController.notifyNewHandStart(handNum);
                    //Set the first variable to false
                    first = false;
                }
                //Display the current player's hand and all the stacks
                System.out.println("Player " + currentPlayer.getPlayerID() + "'s turn:\n");
                currentPlayer.displayHand();
                System.out.println();
                this.displayAllStacks();
                //Get the recommended move and valid move of the current player
                List<Object> recMove = currentPlayer.recommendMove(players);
                List<Object> move = currentPlayer.getValidMove(players, recMove, mainController);
                //Check if the move is not pass
                if (move.get(0) != "pass") {
                    //Set the hand tile and stack tile
                    handTile = (Tile) move.get(0);
                    //Set the stack tile
                    Tile stackTile = (Tile) move.get(1);
                    //Display the move
                    System.out.print("Player " + currentPlayer.getPlayerID() + " played tile ");
                    handTile.displayTile();
                    System.out.print("to tile ");
                    stackTile.displayTile();
                    System.out.println("\n");
                    List<Tile> stack = new ArrayList<>();
                    //Execute the move
                    executeMove(handTile, stackTile);
                    //Set the consecutive passes to 0
                    consecutivePasses = 0;
                } else {
                    //Display the pass message
                    System.out.println("\nPlayer " + currentPlayer.getPlayerID() + " passed");
                    //Increment the consecutive passes by 1
                    consecutivePasses++;
                }

                //Notify the main controller about the end of the turn
                mainController.notifyTurnEnd();
                //Notify the main controller about the model is awaiting a save game input
                mainController.notifySaveGame();
                //Reset the yes no prompt so no prior input is used
                mainController.ResetYesNoPrompt();
                //Wait for the user's confirmation
                while (mainController.getUserYesNo() == null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //If the user confirms, save the game and exit the program
                askSaveGame = mainController.getUserYesNo() == "y";
                if (askSaveGame) {
                    //Notify the main controller about the model received a save game input
                    mainController.notifyConfirmSave();
                    //Create a string builder to save the data
                    StringBuilder save = new StringBuilder();
                    for (Player player : players) {
                        save.append(player.savePlayer());
                        save.append("\n");
                    }
                    if (players.indexOf(currentPlayer) == 1) {
                        save.append("Turn: ").append(this.players.get(0).getPlayerID());
                    } else {
                        save.append("Turn: ").append(this.players.get(1).getPlayerID());
                    }
                    //Notify the main controller about the model is awaiting a file input
                    while (mainController.getFile() == null){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //Get the file from the main controller
                    File file = mainController.getFile();
                    //Save the data to the file and exit the program
                    try (FileOutputStream fos = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                        osw.write(save.toString());
                        osw.flush();
                        System.out.println("Data has been saved to the file.");
                        exit(0);
                    } catch (IOException e) {
                        // If there is an error, display the error message
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                    if (consecutivePasses >= players.size()) {
                        break;
                    }
                }
            }
            allEmptyHands = true;
            for (Player c_player : this.players) {
                if (c_player.getHand().size() != 0) {
                    allEmptyHands = false;
                    break;
                }
            }
        }
        System.out.println("\nHand Over");
        System.out.println("Final Hands:");
        this.displayAllHands();
        System.out.println("Final Stacks:");
        this.displayAllStacks();
        // Score the hand
        System.out.println("\nHand Scores:");
        // Get the scores for the hand
        Map<String, Integer> handScores = this.scoreHand();
        //     // Get the scores for the stacks
        Map<String, Integer> stackScores = scoreStacks();
        // Create a dictionary to hold the final scores of the players
        for (Player currentPlayer : players) {
            int score = stackScores.get(currentPlayer.getPlayerID()) - handScores.get(currentPlayer.getPlayerID());
            currentPlayer.addScore(score);
            System.out.println("Player " + currentPlayer.getPlayerID() + " scored " + score + " points this hand");
        }
        Map<String, Integer> finalScores = new HashMap<>();
        System.out.println("\nCumulative Scores:");
        for (Player currentPlayer : players) {
            finalScores.put(currentPlayer.getPlayerID(), currentPlayer.getScore());
            System.out.println("Player " + currentPlayer.getPlayerID() + ": " + currentPlayer.getScore());
            currentPlayer.getHand().clear();
        }
        String winner;
        if (finalScores.get("Human") > finalScores.get("Computer")) {
            winner = "Human";
        } else if (finalScores.get("Human") < finalScores.get("Computer")) {
            winner = "Computer";
        } else {
            winner = "Tie";
        }
        mainController.notifyHandEnd(finalScores, winner);
    }


    /* *********************************************************************
    Function Name: executeMove
    Purpose: To update the current player's hand and stack with the played tile
    Parameters:
    handTile, a Tile object. It refers to the tile played by the current player
    stackTile, a Tile object. It refers to the stack on which the handTile is played
    Return Value: None
    Algorithm:
    1) Find the stack to which the handTile is played
    2) Replace the stackTile with the handTile
    3) Find the hand of the current player
    4) Remove the handTile from the current player's hand
    Assistance Received: None
    ********************************************************************* */
    public void executeMove (Tile handTile, Tile stackTile){
        List<Tile> stack = new ArrayList<>();
        for (Player c_player_1 : this.players) {
            for (Tile tile_1 : c_player_1.getStack()) {
                if (tile_1.toString().equals(stackTile.toString())) {
                    stack = c_player_1.getStack();
                    int stackIndex = stack.indexOf(stackTile);
                    stack.set(stackIndex, handTile);
                    c_player_1.setStack(stack);
                    break;
                }
            }
            if (stack.size() != 0) {
                break;
            }
        }
        List<Tile> hand = new ArrayList<>();
        for (Player c_player_2 : this.players) {
            for (Tile tile_2 : c_player_2.getHand()) {
                if (tile_2.toString().equals(handTile.toString())) {
                    hand = c_player_2.getHand();
                    int handIndex = hand.indexOf(handTile);
                    hand.remove(handIndex);
                    c_player_2.setHand(hand);
                    break;
                }
            }
            if (hand.size() != 0) {
                break;
            }
        }
    }

    /* *********************************************************************
    Function Name: scoreRound
    Purpose: To calculate the final score and winner of the round
    Parameters: None
    Return Value: A map with player IDs as keys and the number of rounds won as values
    Algorithm:
    1) Initialize variables to keep track of the winner and the highest score
    2) Iterate over the list of players and get each player's score
    3) Compare each player's score to the current highest score, update the winner and highest score if necessary
    4) Check if there is a tie, if not, announce the winner of the round and increment the winner's number of rounds won
    5) Add the number of rounds won for each player to the finalScores map
    Assistance Received: None
    ********************************************************************* */
    public Map<String, Integer> scoreRound () {
        Map<String, Integer> finalScores = new HashMap<>();
        boolean first = true;
        int highestScore = 0;
        Player winner = null;
        for (Player currentPlayer : players) {
            int score = currentPlayer.getScore();
            if (first) {
                winner = currentPlayer;
                highestScore = score;
                first = false;
            } else if (currentPlayer.getScore() > highestScore) {
                winner = currentPlayer;
                highestScore = score;
            }
            currentPlayer.addScore(score);
            System.out.println("Player " + currentPlayer.getPlayerID() + " scored " + score + " points");
        }
        if (players.get(0).getScore() == players.get(1).getScore()) {
            System.out.println("\nIt's a tie!\n");
        } else if (players.get(0).getScore() > players.get(1).getScore()) {
            System.out.println("Player " + winner.getPlayerID() + " won the round");
            players.get(0).addRoundWins();
        }
        else {
            System.out.println("Player " + winner.getPlayerID() + " won the round");
            players.get(1).addRoundWins();
        }
        for (Player currentPlayer : players) {
            finalScores.put(currentPlayer.getPlayerID(), currentPlayer.getRoundsWon());
        }
        return finalScores;
    }

    /* *********************************************************************
    Function Name: extractPlayerData
    Purpose: The purpose of this function is to extract player data from a file and store it in a List.
    Parameters:
    file, a File object passed by value. It refers to the file from which the player data is to be extracted.
    Return Value: A List object that contains a map of player data and a string representing the turn.
    Algorithm:
    1) Create a List of Maps to store player data
    2) Create two maps to store data for Computer and Human players
    3) Read the file line by line
    4) If the line starts with "Computer:", store "Computer" as the current player and add "playerID" key to the map with value "Computer".
    5) If the line starts with "Human:", store "Human" as the current player and add "playerID" key to the map with value "Human".
    6) If the line starts with "Stacks:", extract the stacks data, split it by spaces, and add it to the map with key "stacks".
    7) If the line starts with "Boneyard:", extract the boneyard data, split it by spaces, and add it to the map with key "boneyard".
    8) If the line starts with "Hand:", extract the hand data, split it by spaces, and add it to the map with key "hand".
    9) If the line starts with "Score:", extract the score data, parse it into an integer, and add it to the map with key "score".
    10) If the line starts with "Rounds Won:", extract the rounds won data, parse it into an integer, and add it to the map with key "rounds_won".
    11) If the line starts with "Turn:", extract the turn data and store it in the turn string.
    12) Add the maps for Computer and Human players to the playerData List.
    13) Return the playerData List and turn string.
    Assistance Received: None
    ********************************************************************* */
    public List<Object> extractPlayerData(File file) {
        List<Map<String, Object>> playerData = new ArrayList<>();
        Map<String, Object> ComputerPlayerData = new HashMap<>();
        Map<String, Object> HumanPlayerData = new HashMap<>();
        String turn = "";
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String currentPlayer = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Computer:")) {
                    currentPlayer = "Computer";
                    ComputerPlayerData.put("playerID", "Computer");
                } else if (line.startsWith("Human:")) {
                    currentPlayer = "Human";
                    HumanPlayerData.put("playerID", "Human");
                } else if (line.startsWith("   Stacks:")) {
                    String[] stacks = new String[0];
                    if (line.split(":").length > 1){
                        stacks = line.split(":")[1].trim().split("\\s+");
                    }
                    if (currentPlayer.equals("Computer")) {
                        ComputerPlayerData.put("stacks", stacks);
                    } else if (currentPlayer.equals("Human")) {
                        HumanPlayerData.put("stacks", stacks);
                    }
                } else if (line.startsWith("   Boneyard:")) {
                    String[] boneyard = new String[0];
                    if (line.split(":").length > 1) {
                        boneyard = line.split(":")[1].trim().split("\\s+");
                    }
                    if (currentPlayer.equals("Computer")) {
                        ComputerPlayerData.put("boneyard", boneyard);
                    } else if (currentPlayer.equals("Human")) {
                        HumanPlayerData.put("boneyard", boneyard);
                    }
                } else if (line.startsWith("   Hand:")) {
                    String[] hand = new String[0];
                    if (line.split(":").length > 1) {
                        hand = line.split(":")[1].trim().split("\\s+");
                    }
                    if (currentPlayer.equals("Computer")) {
                        ComputerPlayerData.put("hand", hand);
                    } else if (currentPlayer.equals("Human")) {
                        HumanPlayerData.put("hand", hand);
                    }
                } else if (line.startsWith("   Score:")) {
                    int score = 0;
                    if (line.split(":").length > 1) {
                        score = Integer.parseInt(line.split(":")[1].trim());
                    }
                    if (currentPlayer.equals("Computer")) {
                        ComputerPlayerData.put("score", score);
                    } else if (currentPlayer.equals("Human")) {
                        HumanPlayerData.put("score", score);
                    }
                } else if (line.startsWith("   Rounds Won:")) {
                    int roundsWon = 0;
                    if (line.split(":").length > 1) {
                        roundsWon = Integer.parseInt(line.split(":")[1].trim());
                    }
                    if (currentPlayer.equals("Computer")) {
                        ComputerPlayerData.put("rounds_won", roundsWon);
                    } else if (currentPlayer.equals("Human")) {
                        HumanPlayerData.put("rounds_won", roundsWon);
                    }
                } else if (line.startsWith("Turn:")) {
                    if (line.split(":").length > 1) {
                        turn = line.split(":")[1].trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerData.add(ComputerPlayerData);
        playerData.add(HumanPlayerData);
        return List.of(playerData, turn);
    }

    /* *********************************************************************
    Function Name: load_tournament
    Purpose: To load the saved state of a tournament from a file and resume playing
    Parameters:
    file, a File object passed by value. It holds the data for the saved tournament
    Return Value: None
    Algorithm:
    1) Call extractPlayerData function to get the saved data from the file
    2) Create humanPlayer and computerPlayer objects
    3) Deserialize the saved data and store it in the respective objects
    4) Determine the turn order based on the saved data
    5) Call play_round function to start the game
    6) Call play_again function to determine if the tournament should be continued
    Assistance Received: None
    ********************************************************************* */

    public void load_tournament (File file) {
        List<Object> playerData = extractPlayerData(file);


        humanPlayer humanPlayer = new humanPlayer();
        computerPlayer computerPlayer_ = new computerPlayer();
        List<Map<String, Object>> serializedPlayers = (List<Map<String, Object>>) playerData.get(0);
        String turn = (String) playerData.get(1);
        String[] c_boneyard = null;
        List<Tile> c_boneyard_converted = new ArrayList<>();
        String[] c_hand = null;
        List<Tile> c_hand_converted = new ArrayList<>();
        String[] c_stacks = null;
        List<Tile> c_stacks_converted = new ArrayList<>();
        for (Map<String, Object> player : serializedPlayers) {
            String playerID = (String) player.get("playerID");
            c_boneyard = (String[]) player.get("boneyard");
            for (String tile : c_boneyard) {
                String color = tile.substring(0,1);
                int left = Integer.parseInt(tile.substring(1,2));
                int right = Integer.parseInt(tile.substring(2,3));
                Tile newTile = null;
                if (color.equals("B")){
                    newTile = new Tile(left, right, humanPlayer);
                }
                else{
                    newTile = new Tile(left, right, computerPlayer_);
                }
                c_boneyard_converted.add(newTile);
            }
            c_hand = (String[]) player.get("hand");
            for (String tile : c_hand) {
                String color = tile.substring(0,1);
                int left = Integer.parseInt(tile.substring(1,2));
                int right = Integer.parseInt(tile.substring(2,3));
                Tile newTile = null;
                if (color.equals("B")){
                    newTile = new Tile(left, right, humanPlayer);
                }
                else{
                    newTile = new Tile(left, right, computerPlayer_);
                }
                c_hand_converted.add(newTile);
            }
            c_stacks = (String[]) player.get("stacks");
            for (String tile : c_stacks) {
                String color = tile.substring(0,1);
                int left = Integer.parseInt(tile.substring(1,2));
                int right = Integer.parseInt(tile.substring(2,3));
                Tile newTile = null;
                if (color.equals("B")){
                    newTile = new Tile(left, right, humanPlayer);
                }
                else{
                    newTile = new Tile(left, right, computerPlayer_);
                }
                c_stacks_converted.add(newTile);
            }
            int score = (int) player.get("score");
            int roundsWon = (int) player.get("rounds_won");
            if (playerID.equals("Computer")) {
                computerPlayer_.loadPlayer(playerID, "W", (c_boneyard_converted), c_hand_converted, c_stacks_converted, score, roundsWon);
            } else if (playerID.equals("Human")) {
                humanPlayer.loadPlayer(playerID, "B", c_boneyard_converted, c_hand_converted, c_stacks_converted, score, roundsWon);
            }
            c_hand_converted = new ArrayList<>();
            c_boneyard_converted = new ArrayList<>();
            c_stacks_converted = new ArrayList<>();
        }
        handNum = 4-((humanPlayer.getBoneyard().size()+2)/6);
        if (handNum == 0){
            for (Player c_player : players){
                c_player.shuffleBoneyard();
                c_player.moveFromBoneyardToHandN(6);
                c_player.moveFromHandToStackN(6);
            }
            handNum++;
        }
        if (turn.equals("Computer")) {
            players.add(computerPlayer_);
            players.add(humanPlayer);
        } else if (turn.equals("Human")) {
            players.add(humanPlayer);
            players.add(computerPlayer_);
        }
        else{
            players.add(humanPlayer);
            players.add(computerPlayer_);
            this.determineOrder(true);
        }
        this.play_round();
        this.play_again();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public int getHandNum() {
        return handNum;
    }


    /* *********************************************************************
    Function Name: endTournament
    Purpose: To determine the winner of the tournament and display the final results
    Parameters: None
    Return Value: None
    Algorithm:
    1) Call the scoreRound function to get the final number of wins for the human player and computer player
    2) Compare the number of wins to determine the winner
    3) Display the winner of the tournament
    4) Notify the mainController of the end of the tournament and the final results
    Assistance Received: None
    ********************************************************************* */
    private void endTournament() {
        Map<String, Integer> finalRoundWins = this.scoreRound();
        int humanWins = finalRoundWins.get("Human");
        int computerWins = finalRoundWins.get("Computer");
        String winner = "Tie";
        if (humanWins > computerWins) {
            winner = "Human";
        } else if (humanWins < computerWins) {
            winner = "Computer";
        }
        if (winner.equals("Tie")) {
            System.out.println("The Tournament has ended in a Tie!");
        } else {
            System.out.println("The Tournament has ended and the winner is " + winner + "!");
        }
        mainController.notifyEndTournamentEnd(finalRoundWins, winner);

    }

    //The main controller
    private Controller mainController;

    //A list of players
    List<Player> players;

    //The current player
    private Player currentPlayer;

    //The current hand number
    private int handNum;

}



