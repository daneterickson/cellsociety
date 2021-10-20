package cellsociety.view.bottom;

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
  private static final double ANIMATION_DELAY = .01;

  private HBox mySimControl;
  private GridView myGridView;
  private Timeline myAnimation;
  private boolean isPaused;

  public SimControl(GridView gridView) {
    myGridView = gridView;
    mySimControl = new HBox();
    mySimControl.getChildren().add(makeControlButtons());
    setStyles();
  }

  private void setStyles() {
    mySimControl.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    mySimControl.getStyleClass().add("root");
  }

  private Node makeControlButtons() {
    HBox buttonHBox = new HBox(15);

    buttonHBox.getChildren().add(makePlayButton());
    buttonHBox.getChildren().add(makePauseButton());
    buttonHBox.getChildren().add(makeStopButton());
    buttonHBox.getChildren().add(makeStepButton());

    return buttonHBox;
  }

  private Node makePlayButton() {
    ImageView playIcon = new ImageView(ICONS + "play.png");
    playIcon.setFitWidth(20);
    playIcon.setFitHeight(20);
    Button playButton = new Button("", playIcon);
    playButton.setOnAction(value -> play());
    return playButton;
  }

  private Node makePauseButton() {
    ImageView pauseIcon = new ImageView(ICONS + "pause.png");
    pauseIcon.setFitWidth(20);
    pauseIcon.setFitHeight(20);
    Button pauseButton = new Button("", pauseIcon);
    pauseButton.setOnAction(value -> pause());
    return pauseButton;
  }

  private Node makeStopButton() {
    ImageView stopIcon = new ImageView(ICONS + "stop.png");
    stopIcon.setFitWidth(20);
    stopIcon.setFitHeight(20);
    Button stopButton = new Button("", stopIcon);
    stopButton.setOnAction(value -> stop());
    return stopButton;
  }

  private Node makeStepButton() {
    ImageView stepIcon = new ImageView(ICONS + "step.png");
    stepIcon.setFitWidth(20);
    stepIcon.setFitHeight(20);
    Button stepButton = new Button("", stepIcon);
    stepButton.setOnAction(value -> step());
    return stepButton;
  }

  private void play() {
    if (myAnimation == null) {
      myAnimation = new Timeline();
      myAnimation.setCycleCount(Timeline.INDEFINITE);
      myAnimation.getKeyFrames()
          .add(new KeyFrame(Duration.seconds(ANIMATION_DELAY), e -> step()));
      myAnimation.play();
      isPaused = false;
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
    //TODO
    //update model
    myGridView.illustrate();
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
