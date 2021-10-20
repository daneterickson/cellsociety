package cellsociety.view.bottom;

import cellsociety.controller.Controller;
import cellsociety.view.center.GridView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * SimControl - Simulation Control View
 * <p>
 * This class makes elements that are used on the bottom of the display The elements include start,
 * pause, stop, and step buttons
 *
 * @author Aaric Han
 */

public class SimControl {

  private String RESOURCE = "cellsociety.view.bottom.";
  private String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "SimControl.css";
  private String ICONS = "/" + RESOURCE.replace(".", "/") + "SimControlIcons/";
  private static final double ANIMATION_DELAY = .1;

  private HBox mySimControl;
  private GridView myGridView;
  private Timeline myAnimation;
  private Controller myController;
  private boolean isPaused;

  private ImageView playIcon = new ImageView(ICONS + "play.png");
  private ImageView pauseIcon = new ImageView(ICONS + "pause.png");
  private ImageView stopIcon = new ImageView(ICONS + "stop.png");
  private ImageView stepIcon = new ImageView(ICONS + "step.png");

  public SimControl(GridView gridView, Controller controller) {
    myGridView = gridView;
    mySimControl = new HBox();
    mySimControl.getChildren().add(makeControlButtons());
    myController = controller;
    setStyles();
  }

  private void setStyles() {
    mySimControl.getStyleClass().add("root");
    mySimControl.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

    playIcon.setFitWidth(20);
    playIcon.setFitHeight(20);
    pauseIcon.setFitWidth(20);
    pauseIcon.setFitHeight(20);
    stopIcon.setFitWidth(20);
    stopIcon.setFitHeight(20);
    stepIcon.setFitWidth(20);
    stepIcon.setFitHeight(20);

  }

  private Node makeControlButtons() {
    HBox buttonHBox = new HBox(15);

    buttonHBox.getChildren().add(makePlayPauseButton());
    buttonHBox.getChildren().add(makeStopButton());
    buttonHBox.getChildren().add(makeStepButton());

    return buttonHBox;
  }

  private Node makePlayPauseButton() {
    Button playPauseButton = new Button("", playIcon);
    playPauseButton.setOnAction(value -> play(playPauseButton));
    return playPauseButton;
  }


  private Node makeStopButton() {
    Button stopButton = new Button("", stopIcon);
    stopButton.setOnAction(value -> stop());
    return stopButton;
  }

  private Node makeStepButton() {
    Button stepButton = new Button("", stepIcon);
    stepButton.setOnAction(value -> step());
    return stepButton;
  }

  private void play(Button playPauseButton) {
    if (myAnimation == null) {
      myAnimation = new Timeline();
      myAnimation.setCycleCount(Timeline.INDEFINITE);
      myAnimation.getKeyFrames()
          .add(new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step()));
      myAnimation.play();
      isPaused = false;
      playPauseButton.setGraphic(pauseIcon);
    }
    else if(isPaused){
      myAnimation.play();
      isPaused = false;
      playPauseButton.setGraphic(pauseIcon);
    }
    else if (!isPaused) {
      myAnimation.pause();
      isPaused = true;
      playPauseButton.setGraphic(playIcon);
    }

  }

  private void pause() {
    if (isPaused) {
      myAnimation.play();
    } else {
      myAnimation.pause();
    }
    isPaused = !isPaused;
  }

  private void stop() {
    if (myAnimation != null) {
      myAnimation.stop();
    }
  }

  private void step() {
    if(myController.getHasUpdate()){
      myController.updateModel();
      myGridView.updateGrid();
    }
  }

  /**
   * getSimControl is used to return the made buttons at the bottom of the view
   *
   * @return an HBox containing all the simulation control buttons
   */
  public Node getSimControl() {
    return mySimControl;
  }
}
