package com.example.opl3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class playAgain extends AppCompatActivity {


    /* *********************************************************************
    Function Name: onCreate
    Purpose: To create the main activity of the game.
    Parameters:
    savedInstanceState, a Bundle object. It holds the state of the activity if it was previously created.
    Return Value: None
    Algorithm:
    1) Set the content view of the activity to the specified layout
    2) Initialize the UI elements and set the visibility of some elements to INVISIBLE
    3) Set the onClickListener for the image button to call the askLoad() method
    ********************************************************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the content view of the activity to the specified layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);
        // Initialize the UI elements and set the visibility of some elements to INVISIBLE
        winnerTXt = findViewById(R.id.winner_text_view);
        yes = findViewById(R.id.yesButton);
        no = findViewById(R.id.noButton);
        overall = findViewById(R.id.overall_layout);
        playAgain = findViewById(R.id.playRound);
        //Set the winner text to invisible
        winnerTXt.setVisibility(TextView.INVISIBLE);
        //Set the background of the overall layout to the background image
        overall.setBackgroundResource(R.drawable.background);
        //Get the intent that started this activity
        Intent intent = getIntent();
        //Get the winner of the tournament
        String winner = intent.getStringExtra("winner");
        //Get the number of human and computer round wins
        int humanRoundWins = intent.getIntExtra("humanRoundWins", 0);
        int computerRoundWins = intent.getIntExtra("computerRoundWins", 0);

        //Set the onClickListener for the image button to call the askLoad() method
        yes.setOnClickListener(v -> {
            //Create a new intent to start the hand activity if the user wants to play another round
            Intent intent1 = new Intent(this, hand.class);
            //Pass the number of human and computer round wins to the hand activity
            intent1.putExtra("humanRoundWins", humanRoundWins);
            intent1.putExtra("computerRoundWins", computerRoundWins);
            //Start the hand activity
            startActivity(intent1);
        });
        no.setOnClickListener(v -> {
            //If the user does not want to play another round,
            // set the background of the overall layout to the win image
            overall.setBackgroundResource(R.drawable.win);
            //Set the visibility of the winner text to visible
            winnerTXt.setVisibility(TextView.VISIBLE);
            //Set the visibility of the play again buttons to invisible
            playAgain.setVisibility(LinearLayout.INVISIBLE);
            //If the winner is a tie, set the winner text to "Tournament is a Draw!"
            if (winner.equals("Tie")) {
                //Set the winner text to "Tournament is a Draw!"
                winnerTXt.setText("Tournament is a Draw!");
            }
            //Otherwise, set the winner text to the winner of the tournament
            else {
                //Set the winner text to the winner of the tournament
                winnerTXt.setText(winner + " won the tournament!");
            }

            //Set the onClickListener for the overall layout to finish the activity
            overall.setOnClickListener(new View.OnClickListener() {
                @Override
                //Finish the activity
                public void onClick(View v) {
                    finishAndRemoveTask();
                }
            });
        });
    }


    //Declare the UI elements

    //Declare the winner text view
    private TextView winnerTXt;

    //Declare the image buttons
    private ImageButton yes;
    private ImageButton no;

    //Declare the linear layout that holds the play again buttons and text view
    private LinearLayout playAgain;

    //The overall layout
    private FrameLayout overall;
}