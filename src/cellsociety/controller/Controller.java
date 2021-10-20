package cellsociety.controller;

import cellsociety.model.Model;
import cellsociety.model.parser.ParserCSV;
import cellsociety.model.parser.ParserSIM;
import cellsociety.view.mainView.MainView;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

  private Model myModel;
  private ParserCSV myParserCSV;
  private ParserSIM myParserSIM;
  private MainView myMainView;
  private Stage myStage;

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;


  public Controller(Stage stage) {
    myMainView = new MainView(stage, this);
    Scene scene = myMainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();
    myMainView.updateView();
    myParserCSV = new ParserCSV();
    myParserSIM = new ParserSIM();
  }

  public void openCSVFile(File simFile) {
    myParserSIM.readFile(simFile);
    File csvFile = new File(myParserSIM.getInitialStates());
    myParserCSV.readFile(csvFile);
    myModel = new Model(myParserCSV.getNumRows(), myParserCSV.getNumCols(), myParserCSV.getStartStates());
  }

  public void updateModel(){
    myModel.iterateGrid();
  }

  public int getCellState(int i, int j){
    return myModel.getNewGrid().getCellState(i, j);
  }

  public void openSIMFile(File simFile) {

  }
}
