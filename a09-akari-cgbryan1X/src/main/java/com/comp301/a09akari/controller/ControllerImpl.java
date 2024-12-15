package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.ModelObserver;
import com.comp301.a09akari.model.Puzzle;
import com.comp301.a09akari.view.View;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (model.getActivePuzzleIndex() < model.getPuzzleLibrarySize() - 1) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
      model.resetPuzzle();
    }
  }

  @Override
  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() > 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
      model.resetPuzzle();
    }
  }

  @Override
  public void clickRandPuzzle() {
    int ran = this.model.getActivePuzzleIndex();
    while (ran == this.model.getActivePuzzleIndex()) {
      ran = (int) (Math.random() * this.model.getPuzzleLibrarySize());
    }
    this.model.setActivePuzzleIndex(ran);
    this.model.resetPuzzle();
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (r >= model.getActivePuzzle().getHeight()
        || c >= model.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException("R and C must be within bounds of map.");
    }
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c); // should i add an x?
    } else {
      model.addLamp(r, c);
    }
  }

  public Puzzle getPuzzle() {
    return model.getActivePuzzle();
  }

  public Model getModel() {
    return this.model;
  }

  @Override
  public void addObserver(ModelObserver mo) {
    this.model.addObserver(mo);
  }
}
