package cellsociety.controller;

import cellsociety.model.Model;
import cellsociety.model.Parser;
import cellsociety.view.mainView.MainView;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
  private Model myModel;
  private Parser myParser;
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
  }

  public void openCSVFile(File csvFile) {
    myParser = new Parser();
    myParser.readCSV(csvFile);

    myModel = new Model(myParser.getNumRows(), myParser.getNumCols(), myParser.getStartStates());
  }

  public void updateModel(){
    myModel.iterateGrid();
  }

  public int getCellState(int i, int j){
    return myModel.getNewGrid().getCellState(i, j);
  }
}
