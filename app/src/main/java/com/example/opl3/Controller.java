package com.example.opl3;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controller{



    public Controller(hand activity){
        tournament = new Tournament(this);
        this.activity = activity;
    }

    public void setActivity(hand activity){
        this.activity = activity;
    }

    /* *********************************************************************
    Function Name: startGame
    Purpose: To start a new game in the tournament
    Parameters:
    humanRoundWins, an integer passed by value. It holds the number of round wins for the human player
    computerRoundWins, an integer passed by value. It holds the number of round wins for the computer player
    Return Value: None
    Algorithm:
    1) Create a new thread
    2) Start the thread
    3) Wait for the tournament to set the current player
    4) Sleep for 100ms if the current player is not set
    Assistance Received: None
    ********************************************************************* */
    public void startGame(int humanRoundWins, int computerRoundWins) {
        // Create a new thread to start the tournament
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tournament.start_new_tournament(humanRoundWins, computerRoundWins);
            }
        });
        thread.start();
        // Wait for the tournament to set the current player before the display starts again.
        // This is to prevent the display from starting before the tournament has started
       while (tournament.getCurrentPlayer() == null){
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }
    }

    public void selectStackTile(Tile tile){
        stackSelected = tile;
    }

    public void selectHandTile(Tile tile){
        handSelected = tile;
    }

    public Tile getStackSelected(){
        return stackSelected;
    }


    public Tile getHandSelected(){
        return handSelected;
    }

    public Tournament getTournament(){
        return tournament;
    }

    public void newHand(){

    }

    public void resetHandSelected(){
        handSelected = null;
    }

    public void resetStackSelected(){
        stackSelected = null;
    }


    /* *********************************************************************
    Function Name: notifyNewTurn
    Purpose: Provide the hand and stack data to view from model through the controller
    Parameters: None
    Return Value: None
    Algorithm:
    1) Return if the activity is null
    2) Run the showCurrentPlayer method on the UI thread
    3) Wait for a tap
    4) Add all tiles from each player's stack and hand to separate lists
    5) Set the tiles in the stack and hand adapters
    6) Clear the message board and update the board display
    7) Notify the stack and hand adapters of the data change
    Assistance Received: None
    ********************************************************************* */
    public void notifyNewTurn(){
        // Return if the activity is null
        if (activity == null){
            return;
        }
        // Run the showCurrentPlayer method on the UI thread
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.showCurrentPlayer();
                    }
                }
        );
        // Wait for the user to tap the screen
        waitforTap();
        // Add all tiles from each player's stack and hand to separate lists
        List<Tile> stackTiles = new ArrayList<>();
        for (Player player : tournament.getPlayers()) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }
        // Add all tiles from each player's stack and hand to separate lists
        List<Tile> handTiles = new ArrayList<>();
        Player cPlayer = tournament.getCurrentPlayer();
        for (Tile tile : cPlayer.getHand()) {
            handTiles.add(tile);
        }

        //Wait for the view to create the adapters
        while (activity.getStackAdapter() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //Set the tiles in the stack and hand adapters
        activity.getStackAdapter().setTiles(stackTiles);
        activity.getHandAdapter().setTiles(handTiles);
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Clear the message board and update the board display

                        activity.clearMessageBoard();
                        activity.showBoardDisplay();
                        activity.hideScoreDisplay();

                        // Notify the stack and hand adapters of the data change
                        activity.getStackAdapter().notifyDataSetChanged();
                        activity.getHandAdapter().notifyDataSetChanged();
                    }
                }
        );
    }
    public void notifyHandChange() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.setMessageBoard("Hand " + String.valueOf(tournament.getHandNum()) + " of " + 4);
                    }
                }
        );
    }


    /* *********************************************************************
    Function Name: waitforTap
    Purpose: To wait for user's tap on the screen
    Parameters: None
    Return Value: None
    Algorithm:
    1) Set the tap flag to false
    2) Invoke the waitforTap method in the activity
    3) Wait in a loop until the tap flag is set to true
    Assistance Received: None
    ********************************************************************* */
    public void waitforTap(){
        tap = false;
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.waitforTap();
                    }
                }
        );
        while (!tap){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /* *********************************************************************
    Function Name: notifyHandEnd
    Purpose: To pass the final scores and winner of the hand to the view through the controller
    Parameters:
    finalScores, a Map of String and Integer. It holds the names of the players as keys and their respective scores as values.
    winner, a String. It holds the name of the winner of the hand.
    Return Value: None.
    Algorithm:
    1) Run the draw_scores method on the UI thread.
    2) If the winner is a tie, call the draw_scores function with the finalScores and "Hand is a Draw!" as arguments.
    3) If there is a winner, call the draw_scores function with the finalScores, the winner's name concatenated with " has won the Hand!" and "Score " as arguments.
    4) Wait for a tap event.
    Assistance Received: None.
    ********************************************************************* */
    public void notifyHandEnd(Map<String, Integer> finalScores, String winner) {
        // Pass the final scores and winner of the hand to the view through the controller
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (winner.equals("Tie")){
                            // Pass the final scores and winner of the hand to the view through the controller
                            activity.draw_scores(finalScores, "Hand is a Draw! ", "Score ");
                        }
                        else{
                            // Pass the final scores and winner of the hand to the view through the controller
                            activity.draw_scores(finalScores, winner+" has won the Hand! ", "Score ");
                        }
                    }
                }
        );
        waitforTap();
    }

    public void ResetYesNoPrompt() {
        this.askUserRecMove = null;
    }

    public String getUserYesNo() {
        return this.askUserRecMove;
    }


    /* *********************************************************************
    Function Name: setYesNo
    Purpose: Called by the view to set the result of the Yes/No prompt so that the model can access it
    Parameters:
    b, a string passed by value. It holds the result of the Yes/No prompt, either "Yes" or "No"
    Return Value: None
    Algorithm:
    1) Stop Yes/No listeners
    2) Assign the result of the Yes/No prompt to the askUserRecMove variable
    3) Reset Message board
    4) Hide the recommendation move UI element
    5) Show the message board UI element
    Assistance Received: None
    ********************************************************************* */
    public void setYesNo(String b) {
        activity.stopYesNoListeners();
        this.askUserRecMove = b;
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.hideRecMove();
                        activity.showMessageBoard();
                    }
                }
        );
    }


    /* *********************************************************************
    Function Name: notifyaskRecMove
    Purpose: To notify the view the model is waiting for a recommended move
    Parameters: None
    Return Value: None
    Algorithm:

    Run the runOnUiThread method on the activity instance
    Call the drawRecMove method on the activity instance
    Assistance Received: None
    ********************************************************************* */
    public void notifyaskRecMove() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.drawRecMove();
                    }
                }
        );
    }


    /* *********************************************************************
    Function Name: notifyReciviedRecMove
    Purpose: To pass the recommended move data from the model to the view through the controller
    Parameters:
    message, a string passed by value. It holds the received recommendation move
    Return Value: None
    Algorithm:
    1) Set askUserRecMove to null
    2) Update the user interface by hiding the recommendation move dialog, showing the message board
     and setting the message board text to the received recommendation move.
    3) Wait for user to tap on the screen.
    Assistance Received: None
    ********************************************************************* */
    public void notifyReciviedRecMove(String message) {
        // Pass the recommended move data from the model to the view through the controller

        // Set askUserRecMove to null to prevent false positives on next prompt
        askUserRecMove = null;

        // Update the user interface by hiding the recommendation move dialog, showing the message
        // board
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // hiding the recommendation move dialog
                        activity.hideRecMove();
                        // showing the message board
                        activity.showMessageBoard();
                        // setting the message board text to the received recommendation move.
                        activity.setMessageBoard(message);
                    }
                }
        );

        // Wait for user to tap on the screen.
        if (getCurrentPlayer().getPlayerID().equals("Computer"))
            waitforTap();
    }

    /* *********************************************************************
    Function Name: notifyaskPass
    Purpose: To notify the view the model is waiting for a pass
    Parameters: None
    Return Value: None
    Algorithm:
    1) Run the runOnUiThread method on the activity instance to read the pass from the user
    and update the model
    Assistance Received: None
    ********************************************************************* */
    public void notifyaskPass() {

        // Set the view to read the pass from the user and update the model
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Set the view to read the pass from the user and update the model
                        // Hide the message board
                        activity.hideMessageBoard();
                        // Show the pass button prompt
                        activity.showPass();
                    }
                }
        );
    }
    /* *********************************************************************
    Function Name: notifyReciviedPass
    Purpose: To pass the pass data from the model to the view through the controller
    Parameters: None
    Return Value: None
    Algorithm:
    1) Run the activity on the UI thread
    2) Update the view by running the showpass method on the activity instance
    Assistance Received: None
    ********************************************************************* */
    public void notifyReciviedPass() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.showpass();
                    }
                }
        );
    }

    public List<Player> getPlayers(){
        return tournament.getPlayers();
    }

    /* *********************************************************************
    Function Name: notifyEndTournamentEnd
    Purpose: To pass the final scores and winner of the tournament to the view through the controller
    Parameters:
    finalRoundWins, a Map passed by value. It holds the number of wins for each player in the final round
    winner, a String passed by value. It refers to the name of the player who is leading the tournament or "Tie" if it is a draw
    Return Value: None
    Algorithm:
    1) Pass the final scores and winner of the tournament to the view through the controller
    3) Wait for user tap
    Assistance Received: none
    ********************************************************************* */
    public void notifyEndTournamentEnd(Map<String, Integer> finalRoundWins, String winner) {
        // Pass the final scores and winner of the tournament to the view through the controller
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Pass the final scores and winner of the tournament to the view through the controller
                        if (winner.equals("Tie")){
                            // Pass the final scores and winner of the tournament to the view through the controller
                            activity.draw_scores(finalRoundWins, "Tournament is currently a Draw! ", "Wins ");
                        }
                        else{
                            // Pass the final scores and winner of the tournament to the view through the controller
                            activity.draw_scores(finalRoundWins, winner+" is leading the Tournament! ", "Wins ");
                        }
                    }
                }
        );
        // Wait for user tap
        waitforTap();
    }


    /* *********************************************************************
    Function Name: notifyRoundEnd
    Purpose: To pass the final scores and winner of the round to the view through the controller
    Parameters:
    finalRoundWins, a Map passed by value. It holds the final score for each player in the round
    winner, a string passed by value. It holds the name of the winner or a message indicating a tie
    Return Value: None
    Algorithm:
    1) Pass the final scores and winner of the round to the view through the controller
    2) Call the waitforTap() method to wait for user input.
    Assistance Received: None
    ********************************************************************* */
    public void notifyRoundEnd(Map<String, Integer> finalRoundWins, String winner) {
        // Pass the final scores and winner of the round to the view through the controller
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (winner.equals("Tie")){
                            // Pass the final scores and winner of the round to the view through the controller
                            activity.draw_scores(finalRoundWins, "Round is a Draw! ", "Score: ");
                        }
                        else{
                            // Pass the final scores and winner of the round to the view through the controller
                            activity.draw_scores(finalRoundWins, winner+" won the Round! ", "Score: ");
                        }
                    }
                }
        );
        // Wait for a tap
        waitforTap();
    }
    public Player getCurrentPlayer() {
        return tournament.getCurrentPlayer();
    }


    /* *********************************************************************
    Function Name: notifySaveGame
    Purpose: Notify the view the model is waiting for a save game input
    Parameters: None
    Return Value: None
    Algorithm:
    1) Notify the view the model is waiting for a save game input
    Assistance Received: None
    ********************************************************************* */
    public void notifySaveGame() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Notify the view the model is waiting for a save game input
                        activity.askSaveGame();
                    }
                }
        );
    }

    public File getFile() {
        return saveFile;
    }

    public void setFile(File file) {
        saveFile = file;
    }

    /* *********************************************************************
    Function Name: notifyConfirmSave
    Purpose: Notify the view the model is waiting for a file name input
    Parameters: None
    Return Value: None
    Algorithm:
    1) Notify the view the model is waiting for a file name input
    Assistance Received: None
    ********************************************************************* */
    public void notifyConfirmSave() {
        // Notify the view the model is waiting for a file name input
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    // Notify the view the model is waiting for a file name input
                    public void run() {
                        activity.askFileName();
                    }
                }
        );
    }

    /* *********************************************************************
    Function Name: loadGame
    Purpose: Update the model with the file path of the saved game from the view through the controller
    Parameters:
    file, a java.io.File object passed by value. It holds the file path of the saved game.
    Return Value: None
    Algorithm:
    1) Create a new tournament object with the file object as an argument
    2) Create a new thread to run the tournament.load_tournament method with the file as an argument
    3) Wait for the current player to be set in the tournament object
    Assistance Received: None
    ********************************************************************* */
    public void loadGame(File file) {
        // Create a new tournament object with the file object as an argument
        tournament = new Tournament(this);
        // Create a new thread to run the tournament.load_tournament method with the file as an argument
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tournament.load_tournament(file);
                // code to handle the result of start_new_tournament() on the new thread
            }
        });
        thread.start();
        // Wait for the current player to be set in the tournament object before resuming the view
        while (tournament.getCurrentPlayer() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* *********************************************************************
    Function Name: notifyGameOver
    Purpose: To pass the final scores and winner of the game to the view through the controller
    Parameters:
    scores, a Map<String, Integer> passed by value. It holds the scores of each player
    winner, a String passed by value. It holds the name of the winner of the game
    Return Value: None
    Algorithm:
    1) Pass the final scores and winner of the game to the view through the controller
    2) Notify the view the model is waiting for a play again input
    Assistance Received: None
    ********************************************************************* */

    public void notifyGameOver(Map<String, Integer> scores, String winner) {
        // Pass the final scores and winner of the game to the view through the controller
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Pass the final scores and winner of the game to the view through the controller
                        // Set the default values for the round wins
                        int humanRoundWins = 0;
                        int computerRoundWins = 0;
                        // Get the round wins for each player
                        for (Player player : tournament.getPlayers()) {
                            if (player.getPlayerID().equals("Human")) {
                                humanRoundWins = player.getRoundsWon();
                            }
                            else{
                                computerRoundWins = player.getRoundsWon();
                            }
                        }
                        // Pass the final scores and winner of the game to the view through the controller
                        activity.askPlayAgain(winner, humanRoundWins, computerRoundWins);
                    }
                }
        );
    }

    public void resetTap() {
        this.tap = false;
    }

    public void setTap(boolean b) {
        this.tap = b;
    }

    /* *********************************************************************
    Function Name: notifyTurnEnd
    Purpose: To update the view with the tiles in the stack and hand of each player
    Parameters: None
    Return Value: None
    Algorithm:
    1) Get all the tiles in the game from the stack and hand of each player
    2) Wait until the activity's StackAdapter and HandAdapter are not null
    3) Pass the tiles to the view through the controller
    Assistance Received: none
    ********************************************************************* */

    public void notifyTurnEnd() {
        //If the activity is null, return to avoid a null pointer exception
        if (activity == null){
            return;
        }
        // Get all the tiles in the game from the stack and hand of each player
        List<Tile> stackTiles = new ArrayList<>();
        for (Player player : tournament.getPlayers()) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

        // Get all the tiles in the game from the stack and hand of each player
        List<Tile> handTiles = new ArrayList<>();
        Player cPlayer = tournament.getCurrentPlayer();
        for (Tile tile : cPlayer.getHand()) {
            handTiles.add(tile);
        }

        // Wait until the activity's StackAdapter and HandAdapter are not null to prevent a
        // null pointer exception
        while (activity.getStackAdapter() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Pass the tiles to the view through the controller
        activity.getStackAdapter().setTiles(stackTiles);
        activity.getHandAdapter().setTiles(handTiles);
        // Notify the view that the controller has updated the tiles
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        //Set the board
                        activity.showBoardDisplay();
                        activity.hideScoreDisplay();
                        // Notify the view that the controller has updated the tiles
                        activity.getStackAdapter().notifyDataSetChanged();
                        activity.getHandAdapter().notifyDataSetChanged();
                    }
                }
        );
    }
    /* *********************************************************************
    Function Name: notifyNewHandStart
    Purpose: To pass the current hand number to the view through the controller from the model
    Parameters:
    handNum, an integer. It refers to the current hand number
    Return Value: None
    Algorithm:
    1) Set the message board to display the current hand number
    3) Pass the hand num to the view through the controller
    Assistance Received: None
    ********************************************************************* */
    public void notifyNewHandStart(int handNum) {


        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Set the message board to display the current hand number
                        activity.hideRecMove();
                        activity.showMessageBoard();
                        // Pass the hand num to the view through the controller
                        String message = "Hand " + String.valueOf(handNum) + " of 4";
                        activity.setMessageBoard(message);
                    }
                }
        );
        // Wait for the user to tap the screen before clearing the message board
        waitforTap();
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    // Clear the message board
                    public void run() {
                        activity.clearMessageBoard();
                    }
                }
        );
    }

    /* *********************************************************************
    Function Name: notifyInvalidPass
    Purpose: Notify the view of an invalid pass
    Parameters: None
    Return Value: None
    Algorithm:
    1) Pass the message to the view through the controller
    2) Wait for the player to tap the screen
    3) Clear the message board
    Assistance Received: None
    ********************************************************************* */
    public void notifyInvalidPass() {
        // Pass the message to the view through the controller
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        // Set the message board
                        activity.showMessageBoard();
                        // Pass the message to the view through the controller
                        activity.setMessageBoard("Cannot Pass when valid moves available!");
                    }
                }
        );
        // Wait for the player to tap the screen
        waitforTap();
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    // Clear the message board
                    public void run() {
                        activity.clearMessageBoard();
                    }
                }
        );
    }

    // The tile selected from the stack
    private Tile stackSelected;
    // The tile selected from the hand
    private Tile handSelected;
    // The model of the game
    private Tournament tournament;

    // The view of the game
    private hand activity;
    // The flag to check if the user wants a recommended move
    private String askUserRecMove;

    // The file to save the game
    private File saveFile;
    // The flag to check if the user tapped the screen
    private boolean tap;
}

