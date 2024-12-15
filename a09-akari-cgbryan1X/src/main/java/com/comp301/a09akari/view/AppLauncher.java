package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // Create Model, View, and Controller instances and launch your GUI
    PuzzleLibraryImpl lib = new PuzzleLibraryImpl();
    lib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    lib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    lib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    lib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    lib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    Model model = new ModelImpl(lib);
    ClassicMvcController controller = new ControllerImpl(model);
    View view = new View(controller, stage);

    // Setting up stage
    stage.setTitle("Welcome!");

    // Adding styling
    Scene scene = view.getScene();
    scene.getStylesheets().add("main.css");
    view.update(model);

    // Setting scene
    stage.setScene(scene);
    stage.show(); // YESSSSSSSS
  }
}
