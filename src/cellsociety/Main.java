package cellsociety;


import cellsociety.controller.Controller;
import cellsociety.view.mainView.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    Controller myController = new Controller(stage);
  }

}
