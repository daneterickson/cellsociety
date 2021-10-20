package cellsociety.controller;

import cellsociety.model.Grid;
import cellsociety.model.Model;
import cellsociety.model.parser.ParserCSV;
import cellsociety.view.mainView.MainView;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

  private Model myModel;
  private ParserCSV myParserCSV;
  private MainView myMainView;
  private Stage myStage;
  private Grid currGrid;

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;
  private static final int DEFAULT_GRID_WIDTH = 20;
  private static final int DEFAULT_GRID_HEIGHT = 20;
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];
  private static final Grid DEFAULT_GRID = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES);


  public Controller(Stage stage) {
    myMainView = new MainView(stage, this);
    currGrid = DEFAULT_GRID;
    myModel = new Model(this, currGrid);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.updateView();
  }

  public void openCSVFile(File csvFile) {
    myParserCSV = new ParserCSV();
    myParserCSV.readFile(csvFile);
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates());
    myModel = new Model(this, currGrid);
    myMainView.updateView();
  }

  public void updateModel(){
    myModel.iterateGrid(currGrid);
    currGrid = myModel.getGrid();
    myMainView.updateView();
  }

  public int getCellState(int i, int j){
    return currGrid.getCellState(i, j);
  }

  public void openSIMFile(File simFile) {

  }
}
