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
    public Tile draw(int col, int row) {
        if (board[col][row] == Tile.BLANK && !gameOver) {
            if (playerOneTurn) {
                playerOneTurn = false;
                board[col][row] = Tile.CROSS;
                movesPlayed++;
                return Tile.CROSS;
            } else {
                playerOneTurn = true;
                board[col][row] = Tile.CIRCLE;
                movesPlayed++;
                return Tile.CIRCLE;
            }
        } else if (gameOver) {
            return Tile.GAMEOVER;
        } else {
            return Tile.INVALID;
        }
    }

    // Check if player has made a move to win the game.
    // Start from 5th move because that is the first move that can be a winning move.
    public boolean checkScore(int col, int row, Tile tile) {
        if (movesPlayed > 4) {
            if (checkVertical(col, row, tile)) {
                gameOver = true;
                return true;
            } else if (checkHorizontal(col, row, tile)) {
                gameOver = true;
                return true;
            } else if (checkDiagonal(col, row, tile)) {
                gameOver = true;
                return true;
            } else
                return false;
        }
        return false;
    }

    // Check vertical if move is a winning move.
    private boolean checkVertical(int col, int row, Tile tile) {
        if (row == 0) {
            return board[col][row + 1] == tile && board[col][row + 2] == tile;
        } else if (row == 1) {
            return board[col][row + 1] == tile && board[col][row - 1] == tile;
        } else if (row == 2) {
            return board[col][row - 1] == tile && board[col][row - 2] == tile;
        }
        return false;
    }

    // Check horizontal if move is a winning move.
    private boolean checkHorizontal(int col, int row, Tile tile) {
        if (col == 0) {
            return board[col + 1][row] == tile && board[col + 2][row] == tile;
        } else if (col == 1) {
            return board[col + 1][row] == tile && board[col - 1][row] == tile;
        } else if (col == 2) {
            return board[col - 1][row] == tile && board[col - 2][row] == tile;
        }
        return false;
    }

    // Check diagonal if move is a winning move.
    private boolean checkDiagonal(int col, int row, Tile tile) {
        if (col == 0 && row == 0) {
            return board[col + 1][row + 1] == tile && board[col + 2][row + 2] == tile;
        } else if (col == 1 && row == 1) {
            return board[col + 1][row + 1] == tile && board[col - 1][row - 1] == tile;
        } else if (col == 2 && row == 2) {
            return board[col - 1][row - 1] == tile && board[col - 2][row - 2] == tile;
        } else if (col == 2 && row == 0) {
            return board[col - 1][row + 1] == tile && board[col - 2][row + 2] == tile;
        } else if (col == 0 && row == 2) {
            return board[col + 1][row - 1] == tile && board[col + 2][row - 2] == tile;
        }
        return false;
    }

    // Reload the game based on state of board that is provided.
    // And return array of board state to be displayed on screen.
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
}
