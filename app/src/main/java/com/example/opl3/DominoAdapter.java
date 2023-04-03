package com.example.opl3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DominoAdapter extends RecyclerView.Adapter<DominoAdapter.DominoViewHolder> {

    private List<Tile> tiles;
    private Context context;

    private Controller mainController;

    private boolean stack;

    public DominoAdapter(Context context, List<Tile> tiles, Controller mainController, boolean stack) {
        this.context = context;
        this.tiles = tiles;
        this.mainController = mainController;
        this.stack = stack;
    }

    @NonNull
    @Override
    public DominoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.domino_border, parent, false);
        return new DominoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DominoViewHolder holder, int position) {
        Tile tile = tiles.get(position);
        holder.dominoButton.setText(String.format("%s%d\n%s%d", tile.getColor(), tile.getLeft(), tile.getColor(), tile.getRight()));
    }


    @Override
    public int getItemCount() {
        return tiles.size();
    }

    class DominoViewHolder extends RecyclerView.ViewHolder {
        Button dominoButton;

        DominoViewHolder(@NonNull View itemView) {
            super(itemView);
            dominoButton = itemView.findViewById(R.id.domino_button);
            dominoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation = new AlphaAnimation(1, 0.2f);
                    animation.setDuration(300);
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(2);
                    animation.setRepeatMode(Animation.REVERSE);
                    view.startAnimation(animation);
                    int position = getAdapterPosition();
                    Tile clickedTile = tiles.get(position);
                    System.out.println("Clicked tile: " + clickedTile);
                    if (stack){
                        mainController.selectStackTile(clickedTile);
                    }
                    else{
                        mainController.selectHandTile(clickedTile);
                    }
                }
            });
        }
    }
}
