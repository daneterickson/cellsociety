package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class centerViewTest extends DukeApplicationTest {
  // keep only if needed to call application methods in tests
  Controller myController;
  Canvas myGrid;
  Button myStepButton;


  @Override
  public void start (Stage stage) {
    myController = new Controller(stage);
  }

  @Test
  public void testClickingOnGridInTopLeft(){
    myGrid = lookup("#canvas").query();
    int stateBeforeClick = myController.getCellStateNumber(0,0);
    clickOn(myGrid, 0, 0);
    assertEquals( 1-stateBeforeClick, myController.getCellStateNumber(0,0));
  }


  @Test
  public void testClickingOnGridInBottomRightAfterOtherClicks(){
    myGrid = lookup("#canvas").query();
    int stateBeforeClick = myController.getCellStateNumber(9,9);
    clickOn(myGrid, 0,  0);
    clickOn(myGrid, 100,  0);
    clickOn(myGrid, 70,  100);
    clickOn(myGrid, 0,  290);
    clickOn(myGrid, 290,  290);
    assertEquals(1-stateBeforeClick, myController.getCellStateNumber(9,9));
  }


  @Test
  public void testClickingOnGridToMakeBlinkerThenStepSim(){
    myGrid = lookup("#canvas").query();
    myStepButton = lookup("#stepButton").query();
    int stateBeforeClick = myController.getCellStateNumber(3,4);
    clickOn(myGrid, 100,  100);
    clickOn(myGrid, 133,  100);
    clickOn(myGrid, 163,  100);
    clickOn(myStepButton);
    clickOn(myStepButton);
    clickOn(myStepButton);
    clickOn(myStepButton);
    clickOn(myStepButton);
    assertEquals(1-stateBeforeClick, myController.getCellStateNumber(3,4));
  }

}
