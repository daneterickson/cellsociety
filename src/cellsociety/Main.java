package cellsociety;


import cellsociety.view.mainView.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  private static final int SCENE_WIDTH = 500;
  private static final int SCENE_HEIGHT = 500;

  @Override
  public void start(Stage stage) {
    MainView mainView = new MainView();
    Scene scene = mainView.makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    stage.setScene(scene);
    stage.show();

    mainView.updateView();
  }

}
