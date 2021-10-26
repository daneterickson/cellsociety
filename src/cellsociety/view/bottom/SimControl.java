package cellsociety.view.bottom;

import cellsociety.controller.Controller;
import cellsociety.view.center.GridView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
  private String SLIDER_ICONS = "/" + RESOURCE.replace(".", "/") + "SliderIcons/";

  private static final int BUTTON_SPACING = 15;
  private static final int BUTTON_SLIDER_SPACING = 15;
  private static final int SLIDER_LENGTH = 200;
  private static final double DELAY = .1;
  private static final double MIN_SLIDER_VAL = .01;
  private static final double MAX_SLIDER_VAL = 3;
  private static final double INITIAL_RATE = 1;

  private double myAnimationRate;
  private VBox mySimControl;
  private GridView myGridView;
  private Timeline myAnimation;
  private Controller myController;
  private boolean isPaused;

  private ImageView playIcon = new ImageView(ICONS + "play.png");
  private ImageView pauseIcon = new ImageView(ICONS + "pause.png");
  private ImageView stopIcon = new ImageView(ICONS + "stop.png");
  private ImageView stepIcon = new ImageView(ICONS + "step.png");
  private ImageView turtleIcon = new ImageView(SLIDER_ICONS + "turtle.png");
  private ImageView rabbitIcon = new ImageView(SLIDER_ICONS + "rabbit.png");


  public SimControl(GridView gridView, Controller controller) {
    myAnimationRate = INITIAL_RATE;
    myGridView = gridView;
    mySimControl = new VBox(BUTTON_SLIDER_SPACING);
    mySimControl.getChildren().add(makeControlButtons());
    mySimControl.getChildren().add(makeSpeedSlider());
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
    turtleIcon.setFitWidth(20);
    turtleIcon.setFitHeight(15);
    rabbitIcon.setFitWidth(20);
    rabbitIcon.setFitHeight(20);
  }

  private Node makeControlButtons() {
    HBox buttonHBox = new HBox(BUTTON_SPACING);
    buttonHBox.getChildren().add(makePlayPauseButton());
    buttonHBox.getChildren().add(makeStopButton());
    buttonHBox.getChildren().add(makeStepButton());
    buttonHBox.getStyleClass().add("buttons");
    return buttonHBox;
  }

  private Node makeSpeedSlider(){
    HBox sliderBox = new HBox();
    Slider speedSlider = new Slider( MIN_SLIDER_VAL,MAX_SLIDER_VAL, INITIAL_RATE);
    speedSlider.setPrefWidth(SLIDER_LENGTH);
    speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      myAnimationRate = speedSlider.getValue();
      if(myAnimation!=null){
        myAnimation.setRate(myAnimationRate);
      }
    });
    speedSlider.setId("speedSlider");
    sliderBox.getChildren().add(turtleIcon);
    sliderBox.getChildren().add(speedSlider);
    sliderBox.getChildren().add(rabbitIcon);
    sliderBox.getStyleClass().add("speedSlider");
    return sliderBox;
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
      myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(DELAY), e -> step()));
      myAnimation.setRate(myAnimationRate);
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
