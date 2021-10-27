package cellsociety.controller;

import cellsociety.model.Grid;
import cellsociety.model.cell.ModelCell;
import cellsociety.model.model.GameOfLifeModel;
import cellsociety.model.model.Model;
import cellsociety.model.model.SpreadingOfFireModel;
import cellsociety.model.parser.ParserCSV;
import cellsociety.model.parser.ParserSIM;
import cellsociety.view.mainView.MainView;
import cellsociety.view.right.RightPanel;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

  private Model myModel;
  private ParserCSV myParserCSV;
  private ParserSIM myParserSIM;
  private MainView myMainView;
  private Stage myStage;
  private Grid currGrid;
  private boolean hasUpdate;
  private Map<String, String> simProperties;

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;
  private static final int DEFAULT_GRID_WIDTH = 10;
  private static final int DEFAULT_GRID_HEIGHT = 10;
  public static final String DEFAULT_STATE_COLORS= "";
  public static final String DEFAULT_PARAMETERS = "";
  private static final String DEFAULT_TYPE = "GameOfLife";
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];
  private static final Grid DEFAULT_GRID = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES, DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);


  public Controller(Stage stage) {
    currGrid = DEFAULT_GRID;
    myModel = new GameOfLifeModel(this, currGrid);
    myMainView = new MainView(stage, this);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.initiateGridView();
    myParserCSV = new ParserCSV();
    myParserSIM = new ParserSIM();
    hasUpdate = true;
  }

//  public void openCSVFile(File csvFile) {
//    try {
//      myParserCSV.readFile(csvFile);
//    } catch (CsvValidationException | IOException e) {
//      // TODO: handle the invalid file exception with pop-up in view
//      e.printStackTrace();
//    }
//    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(),
//        myParserCSV.getStartStates(), DEFAULT_STATE_COLORS, DEFAULT_PARAMETERS, DEFAULT_TYPE);
//    myModel = new GameOfLifeModel(this, currGrid);
//    myMainView.initiateGridView();
//  }

  public void setHasUpdate(boolean hasUpdate) {
    this.hasUpdate = hasUpdate;
  }

  public boolean getHasUpdate(){
    return hasUpdate;
  }

  public void updateModel(){
    hasUpdate = false;
    myModel.updateModel(currGrid);
    myMainView.updateView();
  }

  public int getCellStateNumber(int i, int j){
    return currGrid.getCellStateNumber(i, j);
  }

  public void setCellState(int i, int j, int state){
    currGrid.updateCell(i, j, state);
  }

  public String getCellColor(int i, int j){
    return currGrid.getCell(i, j).getCellProperty("StateColor");
  }

  public void openSIMFile(File simFile) {
    try {
      myParserSIM.readFile(simFile);
    } catch (FileNotFoundException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
    File csvFile = new File(String.format("data/%s", myParserSIM.getInfo("InitialStates")));
    try {
      myParserCSV.readFile(csvFile);
    } catch (CsvValidationException | IOException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates(), myParserSIM.getInfo("StateColors"), myParserSIM.getInfo("Parameters"), myParserSIM.getInfo("Type"));
    simProperties = myParserSIM.getMap();

    try {
      Class<?> clazz = Class.forName("cellsociety.model.model." + simProperties.get("Type") + "Model");
      Object modell = clazz.getDeclaredConstructor(Controller.class, Grid.class).newInstance(this, currGrid);
      myModel = (Model) modell;
    }
    catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }

    try {
      Object rightPanel = Class.forName("cellsociety.view.right." + simProperties.get("Type") + "Settings").getConstructor().newInstance();
      myMainView.myRightPanel = (RightPanel) rightPanel;
      myMainView.updateRightPanel();
    }
    catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }



    myMainView.initiateGridView();
  }

  /**
   * Getter method that returns the number of columns in the model grid
   * @return integer number of columns
   */
  public int getNumGridCols(){
    return currGrid.getNumCols();
  }

  /**
   * Getter method that returns the number of rows in the model grid
   * @return integer number of rows
   */
  public int getNumGridRows(){
    return currGrid.getNumRows();
  }

  /**
   * Method to get the Grid for saving CSV
   */
  public Grid getGrid() {
    return currGrid;
  }

  public Map getMap() {
    return simProperties;
  }
}
