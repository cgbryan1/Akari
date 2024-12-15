package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent {
  private final ClassicMvcController controller;

  // display the puzzle controls, including buttons, to move through the puzzle library

  public ControlView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox vbox = new VBox();
    // vbox.setSpacing(15);

    HBox topRow = new HBox();
    topRow.setAlignment(Pos.CENTER);
    topRow.getStyleClass().add("control-panel");

    Label title = new Label("Caroline's Akari!");
    title.getStyleClass().add("title");
    topRow.getChildren().add(title);

    // keep all my controls in a row across the bottom
    HBox bottomRow = new HBox();
    // bottomRow.setSpacing(10);
    bottomRow.setAlignment(Pos.CENTER);
    bottomRow.getStyleClass().add("control-panel");

    Button previousButton = new Button();
    previousButton.getStyleClass().add("control-button");

    // previousButton.setText("Previous puzzle");
    previousButton.setText("\u21E6 Previous puzzle");

    previousButton.setOnAction(
        (ActionEvent event) -> {
          this.controller.clickPrevPuzzle(); // corresponding controller call
        });
    bottomRow.getChildren().add(previousButton);

    Button resetButton = new Button("Reset puzzle");
    resetButton.getStyleClass().add("control-button");
    // resetButton.setText("Reset puzzle");
    resetButton.setOnAction(
        (ActionEvent event) -> {
          this.controller.clickResetPuzzle(); // corresponding controller call
        });
    bottomRow.getChildren().add(resetButton);

    Button randomButton = new Button("Random puzzle"); //
    randomButton.getStyleClass().add("control-button");
    // randomButton.setText("Random puzzle");
    randomButton.setOnAction(
        (ActionEvent event) -> {
          this.controller.clickRandPuzzle(); // corresponding controller call
        });
    bottomRow.getChildren().add(randomButton);

    // Button nextButton = new Button("Next puzzle");
    Button nextButton = new Button("Next puzzle \u21E8");
    nextButton.getStyleClass().add("control-button");
    // nextButton.setText("Next puzzle");
    nextButton.setOnAction(
        (ActionEvent event) -> {
          this.controller.clickNextPuzzle(); // corresponding controller call
        });
    bottomRow.getChildren().add(nextButton);

    vbox.getChildren().add(topRow);
    vbox.getChildren().add(bottomRow);
    return vbox;
  }
}
