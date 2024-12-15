package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageView implements FXComponent {
  // may show the "success" message when the user successfully finishes the puzzle
  private final ClassicMvcController controller;

  public MessageView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox done = new VBox();
    HBox puzzleRow = new HBox();
    HBox messageRow = new HBox();

    String curr =
        "Puzzle "
            + (controller.getModel().getActivePuzzleIndex() + 1)
            + " out of "
            + controller.getModel().getPuzzleLibrarySize();
    Label puzzleTracker = new Label(curr);

    puzzleTracker.getStyleClass().add("puzzle-number-display");
    puzzleRow.getStyleClass().add("puzzle-tracker-row"); // TODO revisit padding idk if good
    puzzleRow.getChildren().add(puzzleTracker);

    Label won = new Label();

    if (this.controller.getModel().isSolved()) {
      won.setText(
          "You've solved Puzzle #" + (controller.getModel().getActivePuzzleIndex() + 1) + "!");
    } // do i need else to keep blank or will it stay that way?
    won.getStyleClass().add("win-message");

    messageRow
        .getStyleClass()
        .add("puzzle-tracker-row"); // TODO being lazy w this style sheet make new one
    messageRow.getChildren().add(won);

    done.getStyleClass().add("message-holder"); // TODO
    done.getChildren().add(messageRow);
    done.getChildren().add(puzzleRow);
    return done;
  }
}
