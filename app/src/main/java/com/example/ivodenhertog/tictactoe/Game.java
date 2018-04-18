package com.example.ivodenhertog.tictactoe;

class Game {
    final private int BOARD_SIZE = 3;
    private Tile[][] board;
    private Boolean playerOneTurn; // true if player 1's turn
    private int movesPlayed;
    private Boolean gameOver;

    public Game() {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i=0; i<BOARD_SIZE; i++)
            for (int j=0; j<BOARD_SIZE; j++)
                board[i][j] = Tile.BLANK;

        playerOneTurn = true;
        gameOver = false;
    }

    public Tile draw(int row, int column) {
        if (board[row][column] == Tile.BLANK) {
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

    public void checkScore() {
        // Todo: create score check with win conditions
    }

    // Todo: Create algorithm for Player versus Computer

}
