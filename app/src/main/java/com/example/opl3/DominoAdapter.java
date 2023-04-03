package com.example.opl3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DominoAdapter extends RecyclerView.Adapter<DominoAdapter.DominoViewHolder> {

    private List<Tile> tiles;
    private Context context;

    public DominoAdapter(Context context, List<Tile> tiles) {
        this.context = context;
        this.tiles = tiles;
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
        holder.leftNumber.setText(String.valueOf(tile.getLeft()));
        holder.rightNumber.setText(String.valueOf(tile.getRight()));
    }

    @Override
    public int getItemCount() {
        return tiles.size();
    }

    class DominoViewHolder extends RecyclerView.ViewHolder {
        TextView leftNumber, rightNumber;

        DominoViewHolder(@NonNull View itemView) {
            super(itemView);
            leftNumber = itemView.findViewById(R.id.left_number);
            rightNumber = itemView.findViewById(R.id.right_number);
        }
    }
}