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


    /* *********************************************************************
    Function Name: DominoAdapter
    Purpose: To create a custom adapter for RecyclerView for displaying and handling domino tiles
    Parameters:
    context, a Context object. It provides the context for the adapter
    tiles, a List of Tile objects. It holds the list of tiles to be displayed
    mainController, a Controller object. It is used to communicate with the main controller
    stack, a boolean value. It indicates whether the tiles are in the stack or hand
    Return Value: None
    Algorithm:

    Initialize context, tiles, mainController, and stack in the constructor
    In the onCreateViewHolder method, inflate the layout and return a new DominoViewHolder object
    In the onBindViewHolder method, bind the values of the tiles to the corresponding domino button
    In the getItemCount method, return the number of tiles in the list
    In the setTiles method, update the tiles in the adapter
    In the DominoViewHolder class, initialize the domino button and set its onClickListener to handle tile clicks
    In the onClick method of the onClickListener, start animation, print the clicked tile, and call the appropriate method in the mainController based on the value of stack
    Assistance Received: None
    ********************************************************************* */
    public DominoAdapter(Context context, List<Tile> tiles, Controller mainController, boolean stack) {
        // Initialize context, tiles, mainController, and stack in the constructor
        this.context = context;
        this.tiles = tiles;
        this.mainController = mainController;
        this.stack = stack;
    }


    /* *********************************************************************
    Function Name: onCreateViewHolder
    Purpose: To create a ViewHolder object to store and manage the view objects.
    Parameters:
    parent, a ViewGroup object passed by value. It refers to the parent ViewGroup object
    viewType, an integer. It indicates the type of view object to be inflated.
    Return Value: The ViewHolder object.
    Algorithm:
    1) Inflate the view object using LayoutInflater from the context.
    2) Create a DominoViewHolder object with the inflated view object.
    3) Return the DominoViewHolder object.
    Assistance Received: none
    ********************************************************************* */
    @NonNull
    @Override
    public DominoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view object using LayoutInflater from the context
        View itemView = LayoutInflater.from(context).inflate(R.layout.domino_border, parent, false);
        // Create a DominoViewHolder object with the inflated view object
        return new DominoViewHolder(itemView);
    }

    /* *********************************************************************
    Function Name: onBindViewHolder
    Purpose: To bind the view for each tile to its data
    Parameters:
    holder, a DominoViewHolder object. It holds the view for each tile
    position, an integer. It refers to the position of the tile in the tiles list
    Return Value: None
    Algorithm:
    1) Get the Tile object for the current position in the tiles list
    2) Set the text of the button in the view to display the color and values of the tile
    Assistance Received: None
    ********************************************************************* */
    @Override
    public void onBindViewHolder(@NonNull DominoViewHolder holder, int position) {
        // Get the Tile object for the current position in the tiles list
        Tile tile = tiles.get(position);
        // Set the text of the button in the view to display the color and values of the tile
        holder.dominoButton.setText(String.format("%s%d\n%s%d", tile.getColor(), tile.getLeft(), tile.getColor(), tile.getRight()));
    }


    @Override
    public int getItemCount() {
        return tiles.size();
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    class DominoViewHolder extends RecyclerView.ViewHolder {
        Button dominoButton;

        /* *********************************************************************
    Function Name: DominoViewHolder
    Purpose: To initialize the domino button and set its onClickListener to handle tile clicks
    Members:
    dominoButton, a Button object. It represents the button for each domino tile
    Methods:
    Constructor, initializes the domino button and sets its onClickListener
    onClick, starts animation, prints the clicked tile, and calls the appropriate method in the mainController based on the value of stack
    Assistance Received: None
    ********************************************************************* */
        DominoViewHolder(@NonNull View itemView) {
            // Initialize the domino button and set its onClickListener to handle tile clicks
            super(itemView);
            // Initialize the domino button
            dominoButton = itemView.findViewById(R.id.domino_button);
            // Set its onClickListener to handle tile clicks
            dominoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Start animation to flash button after click
                    Animation animation = new AlphaAnimation(1, 0.2f);
                    animation.setDuration(300);
                    animation.setInterpolator(new LinearInterpolator());
                    animation.setRepeatCount(2);
                    animation.setRepeatMode(Animation.REVERSE);
                    view.startAnimation(animation);
                    //Get the position of the tile in the list
                    int position = getAdapterPosition();
                    // Get the Tile object for the current position in the tiles list
                    Tile clickedTile = tiles.get(position);
                    // Print the clicked tile
                    System.out.println("Clicked tile: " + clickedTile);
                    // Call the appropriate method in the mainController based on the value of stack
                    if (stack){
                        // If the tile is in the stack, call the selectStackTile method
                        mainController.selectStackTile(clickedTile);
                    }
                    else{
                        // If the tile is in the hand, call the selectHandTile method
                        mainController.selectHandTile(clickedTile);
                    }
                }
            });
        }
    }

    // List of tiles to be displayed

    private List<Tile> tiles;
    // Context for the adapter
    private Context context;
    // Main controller
    private Controller mainController;
    // Whether the tiles are in the stack or hand
    private boolean stack;
}
