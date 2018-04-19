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

    private Game game;
    private int[] gridId = new int[9];
    private int tileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            Log.i("MSG", "Loading Game State");

            String[] tileNr = new String[9];
            int[] rowNr = new int[9];
            int[] colNr = new int[9];
            for (int i = 0; i < 9; i++) {
                String buttonId = "button" + i;
                String rowId = "row" + i;
                String colId = "col" + i;
                tileNr[i] = savedInstanceState.getString(buttonId);
                rowNr[i] = savedInstanceState.getInt(rowId);
                colNr[i] = savedInstanceState.getInt(colId);
                Log.i("Loading", "buttonNr: " + buttonId + " has value " + tileNr[i] + " rowNr " + rowNr[i] + " and colNr " + colNr[i]);
                String set = game.reloadGame(tileNr[i], rowNr[i], colNr[i]);
                Log.i("Tile", set);
            }

        } else {
            Log.i("MSG", "Starting New Game");
            // Initialize the game.
            game = new Game();
            for (int i = 0, l = gridId.length; i < l; i++) {
                String buttonId = "button" + (i + 1);
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                gridId[i] = findViewById(resId).getId();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i("MSG", "Saving Game State");
        super.onSaveInstanceState(outState);
        int tileNr = 0;

        for (int i = 0; i < 9; i++) {
            int buttonId = gridId[tileNr];
            Button b = findViewById(buttonId);
            String state = b.getText().toString();

            int row = i / 3;
            int col = i % 3;
            Log.i("Cords", "row = " + row + " col = " + col);

            String button = "button" + i;
            String rowNr = "row" + i;
            String colNr = "col" + i;

            if (state.equals("X") || state.equals("O")) {
                Log.i("ButtonNr", button);
                Log.i("RowNr", rowNr);
                Log.i("ColNr", button);
                Log.i("State", "" + state);
                outState.putString(button, state);
                outState.putInt(rowNr, row);
                outState.putInt(colNr, col);
            } else {
                Log.i("ButtonNr", button);
                Log.i("RowNr", rowNr);
                Log.i("ColNr", button);
                Log.i("State", "BLANK");
                outState.putString(button, "BLANK");
                outState.putInt(rowNr, row);
                outState.putInt(colNr, col);
            }
            tileNr++;
        }
        outState.putSerializable("tileNr", tileNr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return(true);
    }

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
        Tile tile = game.draw(row, col);

        // Depending on result of draw method, update selected button, use switch to update selected button
        switch (tile) {
            case CROSS:
                Button cross = findViewById(gridId[tileId]);
                cross.setBackgroundResource(R.mipmap.cross);
                break;
            case CIRCLE:
                Button circle = findViewById(gridId[tileId]);
                circle.setBackgroundResource(R.mipmap.circle);
                break;
            case INVALID:
                // Print a toast when proposed move is invalid.
                Toast.makeText(this, "Invalid move! Please try again!",
                        Toast.LENGTH_SHORT).show();
                Log.e("Error", "Invalid move");
                break;
        }
    }

    public void resetClicked(View view) {
        resetClicked();
    }

    private void resetClicked() {
        game = new Game();
        setContentView(R.layout.activity_main);
    }

    // Todo: Create switch mode from Player vs Player to Player vs Computer.
}
