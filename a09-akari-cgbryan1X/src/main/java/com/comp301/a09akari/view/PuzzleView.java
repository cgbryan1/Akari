package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class PuzzleView implements FXComponent {
  private final ClassicMvcController controller;

  public PuzzleView(ClassicMvcController controller) {
    this.controller = controller;
  }

  // game plan: make a grid of buttons and update each one? then update entire board whenever
  // needed?
  //  display the clues and the game board inside a GridPanel
  @Override
  public Parent render() {
    GridPane pane = new GridPane();
    // pane.setGridLinesVisible(true); // this is probs helpful for game although ugly
    pane.getStyleClass().add("game-board"); // same bg as unlit but to be safe...
    // TODO size panel
    // done i hope
    HBox hbox = new HBox();

    // is this explicit sizing gonna be bad for dynamic screens? eek
    for (int r = 0; r < controller.getPuzzle().getHeight(); r++) {
      RowConstraints rowcons = new RowConstraints();
      // rowcons.setMaxHeight(40);
      // rowcons.setMinHeight(40);
      rowcons.setPercentHeight(100.0 / controller.getPuzzle().getHeight());
      pane.getRowConstraints().add(rowcons);
    }
    for (int c = 0; c < controller.getPuzzle().getWidth(); c++) {
      ColumnConstraints colcons = new ColumnConstraints();
      // colcons.setMaxWidth(40);
      // colcons.setMinWidth(40);
      colcons.setPercentWidth(100.0 / controller.getPuzzle().getWidth());
      pane.getColumnConstraints().add(colcons);
    }
    // each cell should be a button so that user can press it
    // added method to controller to get puzzle so can get height and width
    Button[][] cellButtons =
        new Button[controller.getPuzzle().getHeight()][controller.getPuzzle().getWidth()];

    // set each button in grid
    for (int r = 0; r < controller.getPuzzle().getHeight(); r++) {
      for (int c = 0; c < controller.getPuzzle().getWidth(); c++) {
        Button button = new Button();
        button.setPrefSize(40, 40);
        // tried dynamic sizing but didn't work:
        // button.setPrefHeight(100.0 / controller.getPuzzle().getHeight());
        // button.setPrefWidth(100.0 / controller.getPuzzle().getWidth());

        // TODO why aren't my r and c working here what
        int currentR = r;

        // int currentC = r;
        int currentC = c;
        // hopefully this will fix

        // Button
        button.setOnAction(
            (ActionEvent event) -> {
              this.controller.clickCell(
                  currentR, currentC); // left off here. now need to actually update this button
              setButton(button, currentR, currentC); // turned into a helper method after all
            }); // end button.setOnAction(). probably shouldve made this a helper method. oh well.

        // created button but now store:
        cellButtons[r][c] = button;

        //                setButton(button, r, c); // is this necessary? better safe than sorry?
        //                pane.add(button,r ,c);
        setButton(button, r, c); // r, c, here
        pane.add(button, c, r);
      }
    }
    // pane.getStyleClass().add("fx-alignment: center");
    // return pane;
    hbox.getChildren().add(pane);
    hbox.setAlignment(Pos.CENTER);
    hbox.setStyle("-fx-padding: 10;");
    return hbox;
  }

  //    private void setButton(Button button, int r, int c){
  //        button.getStyleClass().clear();
  //        button.getStyleClass().add("unlit"); // default to unlit just in case
  //
  //        if (this.controller.getPuzzle().getCellType(r,c) == CellType.WALL){
  //            button.setText(""); // in case it previously had text
  //            button.setGraphic(null); // in case it previously had graphic
  //            button.getStyleClass().add("wall"); // dark blue walls
  //            // pane.add(button, r, c);
  //        }
  //        else if (this.controller.getPuzzle().getCellType(r,c) == CellType.CORRIDOR){
  //            if (controller.getModel().isLamp(r,c)){
  //                button.setText(""); // in case it previously had text
  //                button.setGraphic(null); // in case it previously had graphic
  //                // TODO add lightbulb image? or maybe a star oooh
  //                button.getStyleClass().add("lamp"); // lamps have bright yellow BG
  //
  //                if (controller.getModel().isLampIllegal(r,c)){
  //                    button.setText(""); // in case it previously had text
  //                    // TODO should i add some kind of txt to tell the user their lamp is
  // illegal? or is the red enough?
  //                    button.setGraphic(null); // in case it previously had graphic
  //                    button.getStyleClass().add("illegal-lamp"); // light red if illegal
  //                }
  //            }
  //            else if (controller.getModel().isLit(r,c)){
  //                button.getStyleClass().add("lit"); // lit squares have light yellow BG
  //                button.setText(""); // in case it previously had text
  //                button.setGraphic(null); // in case it previously had graphic
  //            }
  //            else{
  //                // make sure unlit corridor cells are grey
  //                button.setText(""); // in case it previously had text
  //                button.setGraphic(null); // in case it previously had graphic
  //                button.getStyleClass().add("unlit"); // grey BG
  //            }
  //        }
  //        else{ // CLUE CELLS
  //            button.setText(String.valueOf(controller.getPuzzle().getClue(r,c))); // bc given as
  // int
  //            button.setStyle("-fx-alignment: center;"); // i want the number centered
  //            button.getStyleClass().add("clue");
  //        }
  //    }

  private void setButton(Button button, int r, int c) {
    // Get the number
    CellType type = this.controller.getPuzzle().getCellType(r, c);
    if (type == CellType.CLUE) {
      int num = this.controller.getPuzzle().getClue(r, c);
      button.setText(String.valueOf(num));
      if (this.controller.getModel().isClueSatisfied(r, c)) {
        button.setText(num + "\u2713");
      }
    } else if (type == CellType.CORRIDOR) {
      if (controller.getModel().isLamp(r, c)) {
        if (controller.getModel().isLampIllegal(r, c)) {
          button.setText("iL");
          button.getStyleClass().add("illegal-lamp");
        } else {
          button.setText("L");
          button.getStyleClass().add("lamp");
        }
      } else if (controller.getModel().isLit(r, c)) {
        button.setText("I");
        button.getStyleClass().add("lit");
      } else {
        button.getStyleClass().add("unlit");
      }
    } else if (type == CellType.WALL) {
      button.setText("W");
      button.getStyleClass().add("wall");
    }
  }
}
