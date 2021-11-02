package cellsociety;


import cellsociety.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class runs the Application.
 *
 * @Authors Dane Erickson, Aaric Han, Nick Strauch, Albert Yuan
 */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    Controller myController = new Controller(stage);
  }

}
