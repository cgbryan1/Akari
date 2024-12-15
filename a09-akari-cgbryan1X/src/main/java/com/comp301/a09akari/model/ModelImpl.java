package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library; //  available akari puzzles for the user to solve
  private final ArrayList<ModelObserver>
      observers; // active model observers in support of the observer design pattern
  private int pIndex; // which puzzle is currently active
  private Puzzle puzzle;
  private boolean[][] lamps; // should i make this ints instead?

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException("Puzzle library cannot be null.");
    }
    this.library = library;
    this.observers = new ArrayList<>();
    this.pIndex = 0;
    //        setActivePuzzleIndex(0);
    this.puzzle = this.getActivePuzzle();
    this.lamps = new boolean[this.puzzle.getHeight()][this.puzzle.getWidth()];

    // this.resetPuzzle();
  }

  @Override
  public void addLamp(int r, int c) {
    this.puzzle = getActivePuzzle();
    if (r >= this.puzzle.getHeight() || c >= this.puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("This is not a corridor cell, you cannot add a lamp.");
    }
    lamps[r][c] = true;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    this.puzzle = getActivePuzzle();
    if (r >= this.puzzle.getHeight() || c >= this.puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("This is not a corridor cell.");
    }
    lamps[r][c] = false;
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    this.puzzle = this.library.getPuzzle(pIndex);
    if (r >= this.puzzle.getHeight() || c >= this.puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Cell is not type CORRIDOR.");
    }
    if (this.isLamp(r, c)) {
      return true; // If the cell itself contains a lamp, this method should also return true
    }
    for (int ri = r + 1; ri < this.puzzle.getHeight(); ri++) { // look down:
      if (this.puzzle.getCellType(ri, c) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
      if (isLamp(ri, c)) {
        return true;
      }
    }
    for (int ri = r - 1; ri > -1; ri--) { // look up:
      if (this.puzzle.getCellType(ri, c) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
      if (isLamp(ri, c)) {
        return true;
      }
    }
    for (int ci = c + 1; ci < this.puzzle.getWidth(); ci++) { // look right:
      if (this.puzzle.getCellType(r, ci) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
      if (isLamp(r, ci)) {
        return true;
      }
    }
    for (int ci = c - 1; ci > -1; ci--) { // look left:
      if (this.puzzle.getCellType(r, ci) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
      if (isLamp(r, ci)) {
        return true;
      }
    }
    return false;
    // no notify for this one
  }

  @Override
  public boolean isLamp(int r, int c) {
    this.puzzle = getActivePuzzle();
    if (r >= this.puzzle.getHeight() || c >= this.puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("This is not a corridor cell.");
    }
    return this.lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    this.puzzle = getActivePuzzle();
    if (r >= this.puzzle.getHeight() || c >= this.puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("This cell does not contain a lamp.");
    }
    for (int ri = r - 1; ri > -1; ri--) { // look up:
      if (this.puzzle.getCellType(ri, c) == CellType.CORRIDOR && isLamp(ri, c)) {
        return true;
      }
      if (this.puzzle.getCellType(ri, c) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
    }
    for (int ri = r + 1; ri < this.puzzle.getHeight(); ri++) { // look down:
      if (this.puzzle.getCellType(ri, c) == CellType.CORRIDOR && isLamp(ri, c)) {
        return true;
      }
      if (this.puzzle.getCellType(ri, c) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
    }
    for (int ci = c + 1; ci < this.puzzle.getWidth(); ci++) { // look right:
      if (this.puzzle.getCellType(r, ci) == CellType.CORRIDOR && isLamp(r, ci)) {
        return true;
      }
      if (this.puzzle.getCellType(r, ci) != CellType.CORRIDOR) {
        break; // blocked by wall or clue
      }
    }
    for (int ci = c - 1; ci > -1; ci--) { // look left:
      if (this.puzzle.getCellType(r, ci) == CellType.CORRIDOR && isLamp(r, ci)) {
        return true;
      }
      if (this.puzzle.getCellType(r, ci) == CellType.CLUE
          || this.puzzle.getCellType(r, ci) == CellType.WALL) {
        break; // blocked by wall or clue
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.library.getPuzzle(this.pIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.pIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index > this.getPuzzleLibrarySize() - 1) {
      throw new IndexOutOfBoundsException("This index is not within the active puzzle library.");
    }
    this.pIndex = index;
    this.puzzle = getActivePuzzle();
    this.lamps = new boolean[this.puzzle.getHeight()][this.puzzle.getWidth()];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return this.library.size();
  }

  @Override
  public void resetPuzzle() {
    this.puzzle = getActivePuzzle();
    this.lamps = new boolean[this.puzzle.getHeight()][this.puzzle.getWidth()];

    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    this.puzzle = getActivePuzzle();
    for (int ro = 0; ro < this.puzzle.getHeight(); ro++) {
      for (int col = 0; col < this.puzzle.getWidth(); col++) {
        if (puzzle.getCellType(ro, col) == CellType.CLUE) { // make sure clue is fulfilled
          if (!isClueSatisfied(ro, col)) {
            return false;
          }
        }
        if (this.puzzle.getCellType(ro, col) == CellType.CORRIDOR) { // if its a corridor...
          if (isLamp(ro, col) && isLampIllegal(ro, col)) { // make sure present lamps are legal
            return false;
          }
          if (!isLit(ro, col)) { // and squares w/o lamps are lit
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    this.puzzle = getActivePuzzle();
    if (r >= this.puzzle.getHeight() || c >= this.puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("Coordinates must be within cell.");
    }
    if (this.puzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("This is not a clue cell.");
    }
    int count = 0;
    if (r + 1 < this.puzzle.getHeight()
        && this.puzzle.getCellType(r + 1, c) == CellType.CORRIDOR
        && isLamp(r + 1, c)) {
      count++;
    }
    if (c + 1 < this.puzzle.getWidth()
        && this.puzzle.getCellType(r, c + 1) == CellType.CORRIDOR
        && isLamp(r, c + 1)) {
      count++;
    }
    if (c - 1 > -1 && this.puzzle.getCellType(r, c - 1) == CellType.CORRIDOR && isLamp(r, c - 1)) {
      count++;
    }
    if (r - 1 > -1 && this.puzzle.getCellType(r - 1, c) == CellType.CORRIDOR && isLamp(r - 1, c)) {
      count++;
    }
    return count == this.puzzle.getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null.");
    }
    this.observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null.");
    }
    this.observers.remove(observer);
  }

  private void notifyObservers() {
    if (this.observers == null) {
      throw new IllegalArgumentException("Observers is a null list.");
    }
    for (ModelObserver mo : this.observers) {
      mo.update(this);
    }
  }
}
