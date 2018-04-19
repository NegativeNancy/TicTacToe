package com.example.ivodenhertog.tictactoe;

import java.io.Serializable;

class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private Tile[][] board;
    private Boolean playerOneTurn; // true if player 1's turn
    private int movesPlayed;
    private Boolean gameOver;

    // Create base board for game.
    public Game() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i < BOARD_SIZE; i++)
            for (int j=0; j < BOARD_SIZE; j++)
                board[i][j] = Tile.BLANK;

        playerOneTurn = true;
        gameOver = false;
    }

    // Draw check if move is valid and return answer to view.
    public Tile draw(int row, int column) {
        if (board[row][column] != Tile.CROSS || board[row][column] != Tile.CIRCLE) {
            if (playerOneTurn) {
                playerOneTurn = false;
                board[row][column] = Tile.CROSS;
                return Tile.CROSS;
            } else {
                playerOneTurn = true;
                board[row][column] = Tile.CIRCLE;
                return Tile.CIRCLE;
            }
        } else {
            return Tile.INVALID;
        }
    }

    // Reload the game based on state of board that is provided. And return array of board state
    // to be displayed on screen.
    public int[] reloadGame() {
        int[] tile = new int[9];
        int count = 0;
        for (int i=0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == Tile.BLANK) {
                    tile[count] = 0;
                    count++;
                } else if (board[i][j] == Tile.CIRCLE) {
                    tile[count] = 1;
                    count++;
                } else if (board[i][j] == Tile.CROSS) {
                    tile[count] = 2;
                    count++;
                } else {
                    tile[count] = -1;
                    count++;
                }
            }
        }
        return tile;
    }

    public void checkScore() {
        // Todo: create score check with win conditions
    }

    // Todo: Create algorithm for Player versus Computer

}
