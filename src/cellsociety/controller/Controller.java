package cellsociety.controller;

import cellsociety.model.Grid;
import cellsociety.model.Model;
import cellsociety.model.parser.ParserCSV;
import cellsociety.model.parser.ParserSIM;
import cellsociety.view.mainView.MainView;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.management.DescriptorAccess;

public class Controller {

  private Model myModel;
  private ParserCSV myParserCSV;
  private ParserSIM myParserSIM;
  private MainView myMainView;
  private Stage myStage;
  private Grid currGrid;

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;
  private static final int DEFAULT_GRID_WIDTH = 20;
  private static final int DEFAULT_GRID_HEIGHT = 20;
  private static final String DEFAULT_TYPE = "GameOfLife";
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];
  private static final Grid DEFAULT_GRID = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES, DEFAULT_TYPE);


  public Controller(Stage stage) {
    currGrid = DEFAULT_GRID;
    myModel = new Model(this, currGrid, DEFAULT_TYPE);
    myMainView = new MainView(stage, this);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.initiateGridView();
    myParserCSV = new ParserCSV();
    myParserSIM = new ParserSIM();
  }

  public void openCSVFile(File csvFile) {
    myParserCSV.readFile(csvFile);
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(),
        myParserCSV.getStartStates(), DEFAULT_TYPE);
    myModel = new Model(this, currGrid, DEFAULT_TYPE);
    myMainView.initiateGridView();
  }

  public void updateModel(){
    myModel.updateModel(currGrid);
    myMainView.updateView();
  }

  public int getCellState(int i, int j){
    return currGrid.getCellState(i, j);
  }

  public void openSIMFile(File simFile) {
    // TODO: Not working fix this
    myParserSIM.readFile(simFile);
    System.out.println(myParserSIM.getInitialStates().split("/")[1]);
    File csvFile = new File(myParserSIM.getInitialStates().split("/")[1]);
    myParserCSV.readFile(csvFile);
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates(), myParserSIM.getType());
    myModel = new Model(this, currGrid, myParserSIM.getType());
    myMainView.initiateGridView();
  }

  public int getNumGridCols(){
    return myModel.getGrid().getNumCols();
  }

  public int getNumGridRows(){
    return myModel.getGrid().getNumRows();
  }
}
