package cellsociety.controller;

import cellsociety.model.Grid;
import cellsociety.model.model.GameOfLifeModel;
import cellsociety.model.model.Model;
import cellsociety.model.parser.ParserCSV;
import cellsociety.model.parser.ParserSIM;
import cellsociety.view.mainView.MainView;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;
  private static final int DEFAULT_GRID_WIDTH = 10;
  private static final int DEFAULT_GRID_HEIGHT = 10;
  public static final Map<Integer, String> DEFAULT_COLOR_MAP = new HashMap<>();
  private static final String DEFAULT_TYPE = "GameOfLife";
  private static final int[][] DEFAULT_CELL_STATES = new int[DEFAULT_GRID_WIDTH][DEFAULT_GRID_HEIGHT];
  private static final Grid DEFAULT_GRID = new Grid(DEFAULT_GRID_HEIGHT, DEFAULT_GRID_WIDTH, DEFAULT_CELL_STATES, DEFAULT_COLOR_MAP, DEFAULT_TYPE);


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

  public void openCSVFile(File csvFile) {
    try {
      myParserCSV.readFile(csvFile);
    } catch (CsvValidationException | IOException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(),
        myParserCSV.getStartStates(), DEFAULT_COLOR_MAP, DEFAULT_TYPE);
    myModel = new GameOfLifeModel(this, currGrid);
    myMainView.initiateGridView();
  }

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

  public void openSIMFile(File simFile) {
    try {
      myParserSIM.readFile(simFile);
    } catch (FileNotFoundException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
    File csvFile = new File(String.format("data/%s", myParserSIM.getInitialStates()));
    try {
      myParserCSV.readFile(csvFile);
    } catch (CsvValidationException | IOException e) {
      // TODO: handle the invalid file exception with pop-up in view
      e.printStackTrace();
    }
    currGrid = new Grid(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates(), myParserSIM.getStateColorMap(), myParserSIM.getType());
    myModel = new GameOfLifeModel(this, currGrid);
    myMainView.initiateGridView();
  }

  public int getNumGridCols(){
    return currGrid.getNumCols();
  }

  public int getNumGridRows(){
    return currGrid.getNumRows();
  }

  public void saveCSVFile() {
    try {
      PrintWriter csvFile = new PrintWriter(new File("data/game_of_life/simple.csv"));
      csvFile.write(currGrid.getNumRows() + "," + currGrid.getNumCols() + "\n");
      for (int i = 0; i < currGrid.getNumRows(); i++) {
        StringBuilder rowCSV = new StringBuilder();
        for (int j = 0; j < currGrid.getNumCols(); j++) {
          if (j != currGrid.getNumCols() - 1) {
            rowCSV.append(currGrid.getCellStateNumber(i, j) + ",");
          }
          else {
            rowCSV.append(currGrid.getCellStateNumber(i, j) + "\n");
          }
        }
        csvFile.write(rowCSV.toString());

      }
      csvFile.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
