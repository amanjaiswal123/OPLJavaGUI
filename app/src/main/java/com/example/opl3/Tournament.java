package com.example.opl3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.System.exit;

public class Tournament  {
    private Controller mainController;
    List<Player> players;
    private Player currentPlayer;
    private int handNum;

    public Tournament(Controller mainController_) {
        mainController = mainController_;
        this.players = new ArrayList<>();
    }

    public void start_new_tournament(int humanRoundWins, int computerRoundWins) {
        // Create a new player object
        handNum = 1;
        Player humanPlayer = new humanPlayer();
        humanPlayer.createNewPlayer("Human", "B");
        humanPlayer.setRoundsWon(humanRoundWins);
        Player computerPlayer = new computerPlayer();
        computerPlayer.createNewPlayer("Computer", "W");
        computerPlayer.setRoundsWon(computerRoundWins);
        this.players = new ArrayList<>();
        this.players.add(humanPlayer);
        this.players.add(computerPlayer);
        this.determineOrder();
        this.play_round();
        this.play_again();
    }


    public void startNewRound(){
        // Create a new player object
        int humanRoundWins = 0;
        int computerRoundWins = 0;
        for (Player player : players) {
            if (player.getPlayerID().equals("Human")) {
                humanRoundWins = player.getRoundsWon();
            } else {
                computerRoundWins = player.getRoundsWon();
            }
        }
        this.players = new ArrayList<>();
        Player humanPlayer = new Player();
        humanPlayer.createNewPlayer("Human", "B");
        Player computerPlayer = new computerPlayer();
        computerPlayer.createNewPlayer("Computer", "W");
        this.players = new ArrayList<>();
        this.players.add(humanPlayer);
        this.players.add(computerPlayer);
        computerPlayer.setRoundsWon(computerRoundWins);
        humanPlayer.setRoundsWon(humanRoundWins);
        this.determineOrder();
    }

    public String getValidInput(String prompt, List<String> validInputs) {
        String input;

        while (true) {
            System.out.print(prompt);
            //input = this.scanner.nextLine().trim().toUpperCase();
            input = "";
            if (validInputs.contains(input)) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        return input;
    }



    public void play_again(){
        System.out.println("\nTournament Results:");
        if (players.get(0).getScore() != players.get(1).getScore()) {
            boolean first = true;
            Player winner = null;
            for (Player player : players) {
                if (first) {
                    winner = player;
                    first = false;
                }
                else {
                    if (player.getScore() > winner.getScore()) {
                        winner = player;
                    }
                }
            }
            System.out.println("Player " + winner.getPlayerID() + " won the round!\n");;
        } else {
            System.out.println("The Tournament is a Tie!");
        }

        Map<String, Integer> scores = new HashMap<>();
        for (Player player : players) {
            scores.put(player.getPlayerID(), player.getRoundsWon());
            System.out.println("Player " + player.getPlayerID() + " won " + player.getRoundsWon() + " rounds.");
        }
        String winner = "";
        if (players.get(0).getRoundsWon() == players.get(1).getRoundsWon()) {
            winner = "Tie";
        } else if (players.get(0).getRoundsWon() > players.get(1).getRoundsWon()) {
            winner = players.get(0).getPlayerID();
        } else {
            winner = players.get(1).getPlayerID();
        }
        System.out.println("Thanks for playing!");
        mainController.notifyGameOver(scores, winner);
    }

    public void determineOrder() {
        boolean equal = true;
        while (equal) {
            equal = false;
            System.out.println("\nDetermining Order:");
            for (Player currentPlayer : players) {
                System.out.println("Player " + currentPlayer.getPlayerID() + " boneyard");
                // Assuming displayBoneyard() is implemented in the Player class
                currentPlayer.displayBoneyard();
                System.out.println("Shuffling Boneyard...");
                currentPlayer.shuffleBoneyard();
                System.out.println("Player " + currentPlayer.getPlayerID() + " shuffled boneyard");
                currentPlayer.displayBoneyard();
                currentPlayer.moveFromBoneyardToHandN(1);
            }

            System.out.println("\nPlayers hands:");
            for (Player currentPlayer : players) {
                System.out.print("Player " + currentPlayer.getPlayerID() + " has tile ");
                // Assuming displayHand() is implemented in the Player class
                currentPlayer.displayHand();
            }

            System.out.println("\nComparing Tiles...");
            players.sort((p1, p2) -> p2.getHand().get(0).compareTo(p1.getHand().get(0)));

            System.out.println("\nOrder is:");
            for (Player compPlayer : players) {
                System.out.println("Player " + compPlayer.getPlayerID() + " with tile " + compPlayer.getHand().get(0));
            }

            outerLoop:
            for (Player compPlayer : players) {
                for (Player currentPlayer : players) {
                    if (compPlayer.getHand().get(0).equals(currentPlayer.getHand().get(0)) && !compPlayer.equals(currentPlayer)) {
                        System.out.println("\nPlayer " + compPlayer.getPlayerID() + " and Player " + currentPlayer.getPlayerID() + " have equal tiles re-shuffling");
                        equal = true;
                        for (Player currentPlayer2 : players) {
                            currentPlayer2.moveFromHandToBoneyardN(1);
                        }
                        break outerLoop;
                    }
                }
            }
        }
    }

    public void play_round() {
        mainController.notifyHandChange();
        if (handNum == 1) {
            if (players.get(0).getHand().size() == 1 && players.get(1).getHand().size() == 1) {
                for (Player currentPlayer : players) {
                    currentPlayer.moveFromBoneyardToHandN(5);
                }
            }
            System.out.println("\nHand Number: " + String.valueOf(handNum) + "\n");
            playHand();
            handNum += 1;
        }
        mainController.notifyHandChange();
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
        mainController.notifyHandChange();
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
        mainController.notifyHandChange();
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
        if (handNum > 4) {
            System.out.println("\nRound Finished Scoring Round\n");
            //Return Score to View
            Map<String, Integer> finalRoundWins = this.scoreRound();
            int humanWins = finalRoundWins.get("Human");
            int computerWins = finalRoundWins.get("Computer");
            String winner = "Tie";
            if (humanWins > computerWins) {
                winner = "Human";
            } else if (humanWins < computerWins) {
                winner = "Computer";
            }
            mainController.notifyRoundEnd(finalRoundWins, winner);
        }
    }

    public void displayAllStacks() {
        for (Player player : this.players) {
            System.out.print("Player " + player.getPlayerID() + "'s Stack: ");
            for (Tile tile : player.getStack()) {
                tile.displayTile();
            }
            System.out.println();
        }
    }
    public void displayAllHands() {
        for (Player player : this.players) {
            System.out.print("Player " + player.getPlayerID() + "'s hand: ");
            for (Tile tile : player.getHand()) {
                tile.displayTile();
            }
            System.out.println();
        }
    }

    public Map<String, Integer> scoreHand() {
        Map<String, Integer> scores = new HashMap<>();
        for (Player player : this.players) {
            scores.put(player.getPlayerID(), player.scoreHand());
        }
        return scores;
    }

    public Map<String, Integer> scoreStacks() {
        Map<String, Integer> scores = new HashMap<>();
        for (Player player : this.players) {
            scores.put(player.getPlayerID(), player.scoreStack());
        }
        return scores;
    }
    public void playHand() {
        boolean first = true;
        int consecutivePasses = 0;
        boolean allEmptyHands = true;
        Tile handTile;
        for (Player c_player : this.players) {
            if (c_player.getHand().size() != 0) {
                allEmptyHands = false;
                break;
            }
        }
        boolean askSaveGame = false;
        while (consecutivePasses < players.size() && !allEmptyHands) {
            for (Player currentPlayer : players) {
                this.currentPlayer = currentPlayer;
                mainController.notifyNewTurn();
                if (first){
                    mainController.notifyNewHandStart(handNum);
                    first = false;
                }
                System.out.println("Player " + currentPlayer.getPlayerID() + "'s turn:\n");
                currentPlayer.displayHand();
                System.out.println();
                this.displayAllStacks();
                List<Object> recMove = currentPlayer.recommendMove(players);
                List<Object> move = currentPlayer.getValidMove(players, recMove, mainController);
                if (move.get(0) != "pass") {
                    handTile = (Tile) move.get(0);
                    Tile stackTile = (Tile) move.get(1);
                    System.out.print("Player " + currentPlayer.getPlayerID() + " played tile ");
                    handTile.displayTile();
                    System.out.print("to tile ");
                    stackTile.displayTile();
                    System.out.println("\n");
                    List<Tile> stack = new ArrayList<>();
                    executeMove(handTile, stackTile);
                    consecutivePasses = 0;
                } else {
                    System.out.println("\nPlayer " + currentPlayer.getPlayerID() + " passed");
                    consecutivePasses++;
                    if (consecutivePasses >= players.size()) {
                        break;
                    }
                }
                if (consecutivePasses >= players.size()) {
                    break;
                }
                mainController.notifyTurnEnd();
                mainController.notifySaveGame();
                mainController.ResetYesNoPrompt();
                while (mainController.getUserYesNo() == null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                askSaveGame = mainController.getUserYesNo() == "y";
                if (askSaveGame) {
                    mainController.notifyConfirmSave();
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
                    while (mainController.getFile() == null){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    File file = mainController.getFile();
                    try (FileOutputStream fos = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                        osw.write(save.toString());
                        osw.flush();
                        System.out.println("Data has been saved to the file.");
                        exit(0);
                    } catch (IOException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
                } else {
                    System.out.println("Please provide a valid file name.");
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
            System.out.println("Player Human wins the hand!");
            winner = "Human";
        } else if (finalScores.get("Human") < finalScores.get("Computer")) {
            System.out.println("Player Computer wins the hand!");
            winner = "Computer";
        } else {
            System.out.println("The hand is a tie!");
            winner = "Tie";
        }
        mainController.notifyHandEnd(finalScores, winner);
    }

    public static boolean isFileTaken(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

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
        handNum = 4-(humanPlayer.getBoneyard().size()/6);
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
            this.determineOrder();
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
}



