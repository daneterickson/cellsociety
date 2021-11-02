package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class rightViewTest extends DukeApplicationTest {

  Controller myController;
  ChoiceBox myChoiceBox;
  Button myLineToggleButton;

  @Override
  public void start(Stage stage) {
    myController = new Controller(stage);
  }

  @BeforeEach
  public void setUp() {
    myChoiceBox = lookup("#viewChoiceBox").query();
    myLineToggleButton = lookup("#toggleGridLines").query();
  }

  @Test
  public void testTogglingGridLinesForMultipleGridTypes() {
    clickOn(myLineToggleButton);
    select(myChoiceBox, "TriangleGrid");
    clickOn(myLineToggleButton);
    select(myChoiceBox, "CircleGrid");
    clickOn(myLineToggleButton);
    select(myChoiceBox, "HexagonGrid");
    clickOn(myLineToggleButton);
  }

}
