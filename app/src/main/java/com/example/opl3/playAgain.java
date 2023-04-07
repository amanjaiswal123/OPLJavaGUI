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

    private TextView winnerTXt;
    private ImageButton yes;
    private ImageButton no;

    private LinearLayout playAgain;

    private FrameLayout overall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);
        winnerTXt = findViewById(R.id.winner_text_view);
        yes = findViewById(R.id.yesButton);
        no = findViewById(R.id.noButton);
        overall = findViewById(R.id.overall_layout);
        playAgain = findViewById(R.id.playRound);
        winnerTXt.setVisibility(TextView.INVISIBLE);
        overall.setBackgroundResource(R.drawable.background);
        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        int humanRoundWins = intent.getIntExtra("humanRoundWins", 0);
        int computerRoundWins = intent.getIntExtra("computerRoundWins", 0);
        yes.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, hand.class);
            intent1.putExtra("humanRoundWins", humanRoundWins);
            intent1.putExtra("computerRoundWins", computerRoundWins);
            startActivity(intent1);
        });
        no.setOnClickListener(v -> {
            overall.setBackgroundResource(R.drawable.win);
            winnerTXt.setVisibility(TextView.VISIBLE);
            playAgain.setVisibility(LinearLayout.INVISIBLE);

            if (winner.equals("Tie")) {
                winnerTXt.setText("Tournament is a Draw!");
            }
            else {
                winnerTXt.setText(winner + " won the tournament!");
            }
            overall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAndRemoveTask();
                }
            });
        });
    }
}