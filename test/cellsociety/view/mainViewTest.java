package cellsociety.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.controller.Controller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.geometry.HorizontalDirection;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


public class mainViewTest extends DukeApplicationTest {

  // keep only if needed to call application methods in tests
  Controller myController;

  Button loadCSVButton;
  Button saveCSVButton;
  Slider speedSlider;

  @Override
  public void start(Stage stage) {
    myController = new Controller(stage);
  }

//  @Test
//  void testLoadCSV() {
//    loadCSVButton = lookup("#LoadCSVButton").query();
//    clickOn(loadCSVButton);
//  }

//  @Test
//  void testSaveCSV() {
//    saveCSVButton = lookup("#SaveCSVButton").query();
//    clickOn(saveCSVButton);
//    File file = new File("data/game_of_life/blank.csv");
//    File filetwo = new File("data/game_of_life/simple.csv");
//    // Bytes diff
//    byte[] b1, b2;
//    try {
//      b1 = Files.readAllBytes(file.toPath());
//      b2 = Files.readAllBytes(filetwo.toPath());
//      boolean equals = Arrays.equals(b1, b2);
//      assertTrue(equals);
//
//    }
//    catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

  @Test
  void testAnimationSpeedSliderMaxSpeed() {
    speedSlider = lookup("#speedSlider").query();
    setValue(speedSlider, 3);
  }

  @Test
  void testAnimationSpeedSliderMinSpeed() {
    speedSlider = lookup("#speedSlider").query();
    setValue(speedSlider, .01);
  }

}
