package com.example.opl3;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Controller{
    private Tile stackSelected;
    private Tile handSelected;
    private Tournament tournament;

    private hand activity;
    private String askUserRecMove;

    private File saveFile;
    private boolean tap;


    public Controller(hand activity){
        tournament = new Tournament(this);
        this.activity = activity;
    }

    public void setActivity(hand activity){
        this.activity = activity;
    }

    public void startGame(int humanRoundWins, int computerRoundWins) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tournament.start_new_tournament(humanRoundWins, computerRoundWins);
            }
        });
        thread.start();
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


    public void notifyNewTurn(){
        if (activity == null){
            return;
        }
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.showCurrentPlayer();
                    }
                }
        );
        waitforTap();
        List<Tile> stackTiles = new ArrayList<>();
        for (Player player : tournament.getPlayers()) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

        List<Tile> handTiles = new ArrayList<>();
        Player cPlayer = tournament.getCurrentPlayer();
        for (Tile tile : cPlayer.getHand()) {
            handTiles.add(tile);
        }


        while (activity.getStackAdapter() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        activity.getStackAdapter().setTiles(stackTiles);
        activity.getHandAdapter().setTiles(handTiles);
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.clearMessageBoard();

                        activity.showBoardDisplay();
                        activity.hideScoreDisplay();
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

    public void notifyHandEnd(Map<String, Integer> finalScores, String winner) {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (winner.equals("Tie")){
                            activity.draw_scores(finalScores, winner+"Hand is a Draw! ", "Score ");
                        }
                        else{
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

    public void notifyReciviedRecMove(String message) {
        askUserRecMove = null;
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.hideRecMove();
                        activity.showMessageBoard();
                        activity.setMessageBoard(message);
                    }
                }
        );
        waitforTap();
    }

    public void notifyaskPass() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.hideMessageBoard();
                        activity.showPass();
                    }
                }
        );
    }

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

    public void notifyRoundEnd(Map<String, Integer> finalRoundWins, String winner) {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (winner.equals("Tie")){
                            activity.draw_scores(finalRoundWins, winner+"Tournament is currently a Draw! ", "Wins ");
                        }
                        else{
                            activity.draw_scores(finalRoundWins, winner+" is leading the Tournament! ", "Wins ");
                        }
                    }
                }
        );
        waitforTap();
    }

    public Player getCurrentPlayer() {
        return tournament.getCurrentPlayer();
    }

    public void notifySaveGame() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
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

    public void notifyConfirmSave() {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.askFileName();
                    }
                }
        );
    }

    public void loadGame(File file) {
        tournament = new Tournament(this);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tournament.load_tournament(file);
                // code to handle the result of start_new_tournament() on the new thread
            }
        });
        thread.start();
        while (tournament.getCurrentPlayer() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notifyGameOver(Map<String, Integer> scores, String winner) {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        int humanRoundWins = 0;
                        int computerRoundWins = 0;
                        for (Player player : tournament.getPlayers()) {
                            if (player.getPlayerID().equals("Human")) {
                                humanRoundWins = player.getRoundsWon();
                            }
                            else{
                                computerRoundWins = player.getRoundsWon();
                            }
                        }
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

    public void notifyTurnEnd() {
        if (activity == null){
            return;
        }
        List<Tile> stackTiles = new ArrayList<>();
        for (Player player : tournament.getPlayers()) {
            for (Tile tile : player.getStack()) {
                stackTiles.add(tile);
            }
        }

        List<Tile> handTiles = new ArrayList<>();
        Player cPlayer = tournament.getCurrentPlayer();
        for (Tile tile : cPlayer.getHand()) {
            handTiles.add(tile);
        }


        while (activity.getStackAdapter() == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        activity.getStackAdapter().setTiles(stackTiles);
        activity.getHandAdapter().setTiles(handTiles);
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.showBoardDisplay();
                        activity.hideScoreDisplay();
                        activity.getStackAdapter().notifyDataSetChanged();
                        activity.getHandAdapter().notifyDataSetChanged();
                    }
                }
        );
    }

    public void notifyNewHandStart(int handNum) {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.hideRecMove();
                        activity.showMessageBoard();
                        String message = "Hand " + String.valueOf(handNum) + " of 4 has started!";
                        activity.setMessageBoard(message);
                    }
                }
        );
        waitforTap();
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        activity.clearMessageBoard();
                    }
                }
        );
    }
}

