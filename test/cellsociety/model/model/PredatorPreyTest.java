package cellsociety.model.model;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cellsociety.controller.Controller;
import cellsociety.model.Grid;
import cellsociety.model.exceptions.KeyNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PredatorPreyTest {

  private Model myModel;
  private Grid myGrid;
  private int[][] myStates;
  private int numRows;
  private int numCols;
  private Controller myController;
  private String myParameters;
  private String myStartColors;
  private String type;

  @BeforeEach
  void setUp() {
    myStates = new int[][]{{0, 0, 0, 0, 0},
        {0, 2, 1, 2, 0},
        {0, 0, 1, 2, 0},
        {0, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}};
    myParameters = "3,3,3,3";
    numRows = 5;
    numCols = 5;
    type = "PredatorPrey";
    //fishrepro=3, sharkrepro=3, sharkenergy=1
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PredatorPreyModel(myController, myGrid);
  }

  @Test
  void testEmptyCell()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Method currRule = Model.class.getDeclaredMethod("currRule", int.class, int.class, int.class,
        List.class);
    currRule.setAccessible(true);
    int ret;
    List<Integer> list;
    int currRow = 2;
    int currCol = 2;

    //empty cell
    list = Arrays.asList(0, 0, 0, 1);
    ret = (int) currRule.invoke(myModel, currRow, currCol, 0, list);
    assertEquals(0, ret, "empty cell should remain empty(0). got: " + ret);
  }

  @Test
  void testCurrRuleReproducing()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KeyNotFoundException {
    myStates = new int[][]{{1, 1, 1, 1, 2},
        {1, 1, 1, 1, 0},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1}};
    myParameters = "1,1,2,3";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PredatorPreyModel(myController, myGrid);

    try{
    myModel.updateModel(myGrid);
    } catch (NullPointerException e) {

    }
//    System.out.println(" --- ");
    printgrid();

    int parentShark = parseInt(myGrid.getModelCell(0,3).getCellProperty("StateNumber"));
    assertEquals(2, parentShark, "at (0,3) should be shark. got: " + parentShark);
    int childShark = parseInt(myGrid.getModelCell(0,4).getCellProperty("StateNumber"));
    assertEquals(2, childShark, "at (0,4) should be shark. got: " + childShark);
    int childFish = parseInt(myGrid.getModelCell(1,4).getCellProperty("StateNumber"));
    assertEquals(1, childFish, "at (1,4), should be fish. got: " + childFish);

  }

  @Test
  void testCurrRuleEating()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, KeyNotFoundException {
    myStates = new int[][]{{0, 0, 0, 1, 2},
        {0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0}};
    //fishrepro=2, sharkrepro=2, sharkenergy=2, energy gain=2
    myParameters = "2,2,2,2";
    myGrid = new Grid(numRows, numCols, myStates, myStartColors, myParameters, type);
    myModel = new PredatorPreyModel(myController, myGrid);

    try{
      myModel.updateModel(myGrid);
    } catch (NullPointerException e) {

    }
    try {
      printgrid();
    } catch (KeyNotFoundException e) {
      System.out.println("Cannot print grid - Exception reached");
    }

    int shark = parseInt(myGrid.getModelCell(0,3).getCellProperty("StateNumber"));
    assertEquals(2, shark, "at (0,3) should be shark. got: " + shark);
    int PossibleFish1 = parseInt(myGrid.getModelCell(0,2).getCellProperty("StateNumber"));
    int PossibleFish2 = parseInt(myGrid.getModelCell(1,3).getCellProperty("StateNumber"));

    assertEquals(0, PossibleFish1, "fish should be dead. got: " + PossibleFish1);
    assertEquals(0, PossibleFish2, "fish should be dead. got: " + PossibleFish2);


  }

  private void printgrid() throws KeyNotFoundException {
    for (int r = 0; r< myGrid.getNumRows();r++){
      System.out.println(" ");
      for (int c = 0; c< myGrid.getNumRows();c++){
        System.out.print(myGrid.getModelCell(r,c).getCellProperty("StateNumber"));
      }
    }
    System.out.println(" ");
  }


}
