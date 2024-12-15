package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View implements ModelObserver {
  // need to encap controller and stage here

  private final ClassicMvcController controller;
  private final Stage stage;

  private final ControlView controlView;
  private final MessageView messageView;

  private final PuzzleView puzzleView;
  private final Scene scene;

  public View(ClassicMvcController controller, Stage stage) {
    // VBox vbox = new VBox();
    this.controller = controller;
    this.stage = stage;
    this.controller.addObserver(this);

    controlView = new ControlView(controller);
    messageView = new MessageView(controller);
    puzzleView = new PuzzleView(controller);

    this.scene = new Scene(render());
  }

  public Scene getScene() {
    return scene;
  }

  public Parent render() {
    VBox vbox = new VBox(); // should do HBox?

    vbox.getChildren().add(this.controlView.render());
    vbox.getChildren().add(this.messageView.render());
    vbox.getChildren().add(this.puzzleView.render());

    return vbox;
  }

  @Override
  public void update(Model model) {
    // Scene scene = new Scene(this.render());
    // scene.getStylesheets().add("main.css");
    // this.stage.setScene(new Scene(this.render()));
    scene.setRoot(render());
  }
}
