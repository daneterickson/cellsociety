package cellsociety.view.bottom;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * SimControl - Simulation Control View
 *
 * This class makes elements that are used on the bottom of the display
 * The elements include start, pause, stop, and step buttons
 *
 * @author Aaric Han
 */

public class SimControl {
  private String RESOURCE = "cellsociety.view.bottom.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"SimControl.css";
  private String ICONS = "/"+RESOURCE.replace(".", "/")+"icons/";

  private HBox mySimControl;

  public SimControl() {
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
    ImageView playIcon = new ImageView(ICONS+"play.png");
    playIcon.setFitWidth(20);
    playIcon.setFitHeight(20);
    Button playButton = new Button("", playIcon);
    playButton.setOnAction(value -> play());
    return playButton;
  }

  private Node makePauseButton() {
    ImageView pauseIcon = new ImageView(ICONS+"pause.png");
    pauseIcon.setFitWidth(20);
    pauseIcon.setFitHeight(20);
    Button pauseButton = new Button("", pauseIcon);
    pauseButton.setOnAction(value -> pause());
    return pauseButton;
  }

  private Node makeStopButton() {
    ImageView stopIcon = new ImageView(ICONS+"stop.png");
    stopIcon.setFitWidth(20);
    stopIcon.setFitHeight(20);
    Button stopButton = new Button("", stopIcon);
    stopButton.setOnAction(value -> stop());
    return stopButton;
  }

  private Node makeStepButton() {
    ImageView stepIcon = new ImageView(ICONS+"step.png");
    stepIcon.setFitWidth(20);
    stepIcon.setFitHeight(20);
    Button stepButton = new Button("", stepIcon);
    stepButton.setOnAction(value -> step());
    return stepButton;
  }

  private void play() {
    //TODO: Performs action when play button is pressed
  }

  private void pause() {
    //TODO: Performs action when pause button is pressed
  }

  private void stop() {
    //TODO: Performs action when stop button is pressed
  }

  private void step() {
    //TODO: Performs action when step button is pressed
  }

  /**
   * getSimControl is used to return the made buttons at the bottom of the view
   *
   * @return an HBox containing all of the simulation control buttons
   */
  public Node getSimControl() {
    return mySimControl;
  }
}
