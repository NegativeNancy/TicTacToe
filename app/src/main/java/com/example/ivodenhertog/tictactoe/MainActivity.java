package com.example.ivodenhertog.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Global variables.
    private Game game;
    private int[] gridId = new int[9];
    private int tileId;

    // Function that creates the base game or recreates the game after orientation change.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recreate game when instance state is saved.
        if (savedInstanceState != null) {
            Log.i("MSG", "Loading Game State");

            // Get stored game
            game = (Game) savedInstanceState.getSerializable("game");

            // Use stored game to find state of tiles.
            int[] status = game.reloadGame();

            // Based on state of the tile the button will be changed to correct image.
            for (int i = 0; i < status.length; i++) {
                Log.v("Verbose", "" + status[i]);
                // Get Id of relevant button.
                String buttonId = "button" + (i + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Button b = findViewById(resId);

                // Change value of the button.
                if (status[i] == 0) {
                } else if (status[i] == 1) {
                    b.setBackgroundResource(R.mipmap.circle);
                } else if (status[i] == 2) {
                    b.setBackgroundResource(R.mipmap.cross);
                } else {
                    Log.e("Error", "No valid return provided");
                }
            }
        }
        // Create base game on startup.
        else {
            Log.i("MSG", "Starting New Game");
            // Initialize the game.
            game = new Game();

            // Save button Id's for later use.
            for (int i = 0, l = gridId.length; i < l; i++) {
                String buttonId = "button" + (i + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                gridId[i] = findViewById(resId).getId();
            }
        }
    }

    // Save state of the game when orientation changes.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("MSG", "Saving Game State");
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    // Function that handles the onClick event.
    public void tileClicked(View view) {
        // Get id of pressed button and use id to get number of the button.
        int id = view.getId();
        for (int i = 0, l = gridId.length; i < l; i++) {
            if (gridId[i] == id)
                tileId = i;
        }

        // Determine row and column of selected tile.
        int row = tileId / 3;
        int col = tileId % 3;

        // Feed coordinates to Games draw method:
        Tile tile = game.draw(col, row);

        // Check if move is winning move.
        Boolean hasWon = game.checkScore(col, row, tile);

        // Depending on result of draw method, update selected button, use switch to update selected button
        Button b = (Button) findViewById(gridId[tileId]);
        switch (tile) {
            case CROSS:
                b.setBackgroundResource(R.mipmap.cross);
                if (hasWon) {
                    // Print a toast when player wins.
                    Toast.makeText(this, R.string.player_one_won,
                            Toast.LENGTH_LONG).show();
                    Log.i("MSG", "Player one won");
                }
                break;
            case CIRCLE:
                b.setBackgroundResource(R.mipmap.circle);
                if (hasWon) {
                    // Print a toast when player wins
                    Toast.makeText(this, R.string.player_two_won,
                            Toast.LENGTH_LONG).show();
                    Log.i("Game over", "Player two won");
                }
                break;
            case INVALID:
                // Print a toast when proposed move is invalid.
                Toast.makeText(this, R.string.invalid_move,
                        Toast.LENGTH_SHORT).show();
                Log.e("Error", "Invalid move");
                break;
            case GAMEOVER:
                // Print a toast when proposed move is done after game is over.
                Toast.makeText(this, R.string.game_over,
                        Toast.LENGTH_SHORT).show();
                Log.e("Error", "Game Over - reset game");
                break;
        }
    }

    // Reset function for on-screen reset button.
    public void resetClicked(View view) {
        resetClicked();
    }

    // Reset function for reset via menu.
    private void resetClicked() {
        game = new Game();
        setContentView(R.layout.activity_main);
    }

    // Function to create on-screen menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return(true);
    }

    // Function that handles the events when menu button is pressed.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                resetClicked();
                return(true);
            case R.id.about:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
                return true;
            case R.id.exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // Todo: Create switch mode from Player vs Player to Player vs Computer.
}
