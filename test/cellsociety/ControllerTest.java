package cellsociety;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.model.Model;

import static cellsociety.controller.Controller.DEFAULT_GRID_HEIGHT;
import static cellsociety.controller.Controller.DEFAULT_GRID_WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.view.left.CellProperties;
import cellsociety.view.mainview.MainView;
import java.lang.reflect.Field;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ControllerTest extends DukeApplicationTest {

  private Model myModel;
  private Grid myGrid;
  private int[][] myStates;
  private int numRows;
  private int numCols;
  private Controller myController;
  private String myStartColors;
  private String myParameters;

  @Override
  public void start(Stage stage) {
    myController = new Controller(stage);
  }

  @Test
  void testHasUpdate() {
    assertEquals(true, myController.getHasUpdate(),
        "has update is true by default. got: " + myController.getHasUpdate());
    myController.setHasUpdate(false);
    assertEquals(false, myController.getHasUpdate(),
        "has update should be set false. got: " + myController.getHasUpdate());
  }

  @Test
  void testStopAnimation() {
    assertEquals(false, myController.getStopAnimation(),
        "stop animation is false by default. got: " + myController.getStopAnimation());
    myController.setStopAnimation(true);
    assertEquals(true, myController.getStopAnimation(),
        "stop animation should be set true. got: " + myController.getStopAnimation());
  }

  @Test
  void testGridGetters() {
    assertEquals(0, myController.getCellStateNumber(0, 0),
        "cell state is 0 by default. got: " + myController.getCellStateNumber(0, 0));
    myController.setCellState(0, 0, 1);
    assertEquals(1, myController.getCellStateNumber(0, 0),
        "cell state should be set 1. got: " + myController.getCellStateNumber(0, 0));

    assertEquals(DEFAULT_GRID_WIDTH, myController.getNumGridCols(),
        "num cols should be 10. got: " + myController.getNumGridRows());
    assertEquals(DEFAULT_GRID_HEIGHT, myController.getNumGridRows(),
        "num rows should be 10. got: " + myController.getNumGridRows());

  }

//  @Test
//  void testSetLang(){
//    try {
//      Field field;
//      field = Controller.class.getDeclaredField("myMainView");
//      field.setAccessible(true);
//      MainView myMainView = (MainView) field.get(myController);
//
//      field = CellProperties.class.getDeclaredField("myResource");
//      field.setAccessible(true);
//      ResourceBundle myResource = (ResourceBundle) field.get(myMainView.getMyCellProperties());
//
//      assertEquals("lang.English",myResource.getBaseBundleName(), "current resource bundle should be the english one. got: "+myResource.getBaseBundleName());
//
//      myController.setLang("SP");
//
//      field = Controller.class.getDeclaredField("myMainView");
//      field.setAccessible(true);
//      myMainView = (MainView) field.get(myController);
//
//      field = CellProperties.class.getDeclaredField("myResource");
//      field.setAccessible(true);
//      myResource = (ResourceBundle) field.get(myMainView.getMyCellProperties());
//
//      assertEquals("lang.Spanish",myResource.getBaseBundleName(), "current resource bundle should be the spanish one. got: "+myResource.getBaseBundleName());
//
//    } catch (NoSuchFieldException | IllegalAccessException e) {
//
//    }
//  }


}
