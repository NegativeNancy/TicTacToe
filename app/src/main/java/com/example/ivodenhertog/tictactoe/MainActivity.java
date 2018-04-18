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

        game = new Game();
        for (int i = 0, l = gridId.length; i < l; i++ ) {
            String buttonId = "button" + (i + 1);
            int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
            gridId[i] = findViewById(resId).getId();
        }
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
}
