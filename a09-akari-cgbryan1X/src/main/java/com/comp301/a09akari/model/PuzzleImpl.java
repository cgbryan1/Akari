package com.comp301.a09akari.model;

import java.util.List;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException("Board cannot be null.");
    }
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.board[r][c] < 5) {
      return CellType.CLUE;
    } else if (this.board[r][c] == 5) {
      return CellType.WALL;
    } else {
      return CellType.CORRIDOR;
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.board[r][c] > 4) {
      throw new IllegalArgumentException("This is not a clue cell.");
    }
    return this.board[r][c];
  }
}
