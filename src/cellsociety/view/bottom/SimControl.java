package cellsociety.view.bottom;

import cellsociety.controller.Controller;
import cellsociety.view.center.CenterView;
import cellsociety.view.left.CellProperties;
import java.awt.Choice;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
 * @author Aaric Han, Nick Strauch
 */

public class SimControl {

  private String RESOURCE = "cellsociety.view.bottom.";
  private String STYLESHEET = String.format("/%sSimControl.css", RESOURCE.replace(".", "/"));
  private String ICONS = String.format("/%sSimControlIcons/", RESOURCE.replace(".", "/"));
  private String SLIDER_ICONS = String.format("/%sSliderIcons/", RESOURCE.replace(".", "/"));

  private static final int BUTTON_SPACING = 15;
  private static final int BUTTON_SLIDER_SPACING = 15;
  private static final int SLIDER_LENGTH = 200;
  private static final double DELAY = .1;
  private static final double MIN_SLIDER_VAL = .01;
  private static final double MAX_SLIDER_VAL = 3;
  private static final double INITIAL_RATE = 1;
  private static final int ICON_SIZE = 20;
  private static final String EN = "EN";
  private static final String ES = "ES";

  private double myAnimationRate;
  private VBox mySimControl;
  private CenterView myCenterView;
  private Timeline myAnimation;
  private Controller myController;
  private boolean isPaused;
  private ResourceBundle myResources;
  private CellProperties myCellProperties;

  private ImageView playIcon = new ImageView(String.format("%splay.png", ICONS));
  private ImageView pauseIcon = new ImageView(String.format("%spause.png", ICONS));
  private ImageView stepIcon = new ImageView(String.format("%sstep.png", ICONS));
  private ImageView turtleIcon = new ImageView(String.format("%sturtle.png", SLIDER_ICONS));
  private ImageView rabbitIcon = new ImageView(String.format("%srabbit.png", SLIDER_ICONS));


  public SimControl(CenterView centerView, Controller controller, ResourceBundle resources,
      CellProperties cellProperties) {
    myAnimationRate = INITIAL_RATE;
    myCenterView = centerView;
    myController = controller;
    myResources = resources;
    myCellProperties = cellProperties;
    mySimControl = new VBox(BUTTON_SLIDER_SPACING);
    mySimControl.getChildren().add(makeControlButtons());
    mySimControl.getChildren().add(makeSpeedSlider());
    mySimControl.getChildren().add(makeViewChoiceBox());
    mySimControl.getChildren().add(makeLangButton());
    setStyles();
  }

  private void setStyles() {
    mySimControl.getStyleClass().add("root");
    mySimControl.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

    setButtonIconSize(playIcon);
    setButtonIconSize(pauseIcon);
    setButtonIconSize(stepIcon);
    setButtonIconSize(turtleIcon);
    setButtonIconSize(rabbitIcon);
  }

  private void setButtonIconSize(ImageView img) {
    img.setFitWidth(ICON_SIZE);
    img.setFitHeight(ICON_SIZE);
  }

  private Node makeControlButtons() {
    HBox buttonHBox = new HBox(BUTTON_SPACING);
    buttonHBox.getChildren().add(makePlayPauseButton());
    buttonHBox.getChildren().add(makeStepButton());
    buttonHBox.getChildren().add(makeAddGridButton());
    buttonHBox.getStyleClass().add("buttons");
    return buttonHBox;
  }

  private Node makeSpeedSlider() {
    HBox sliderBox = new HBox();
    Slider speedSlider = new Slider(MIN_SLIDER_VAL, MAX_SLIDER_VAL, INITIAL_RATE);
    speedSlider.setPrefWidth(SLIDER_LENGTH);
    speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      myAnimationRate = speedSlider.getValue();
      if (myAnimation != null) {
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
    playPauseButton.setOnAction(value -> playPause(playPauseButton));
    return playPauseButton;
  }

  private Node makeStepButton() {
    Button stepButton = new Button("", stepIcon);
    stepButton.setOnAction(value -> step());
    stepButton.setId("stepButton");
    return stepButton;
  }

  private void playPause(Button playPauseButton) {
    if (myAnimation == null) {
      myAnimation = new Timeline();
      myAnimation.setCycleCount(Timeline.INDEFINITE);
      myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(DELAY), e -> step()));
      myAnimation.setRate(myAnimationRate);
      myAnimation.play();
      isPaused = false;
      playPauseButton.setGraphic(pauseIcon);
    } else if (isPaused) {
      myAnimation.play();
      isPaused = false;
      playPauseButton.setGraphic(pauseIcon);
    } else if (!isPaused) {
      myAnimation.pause();
      isPaused = true;
      playPauseButton.setGraphic(playIcon);
    }

  }

  private void step() {
    if (myController.getHasUpdate()) {
      myController.updateModels();
      myCenterView.updateView();
    }
  }

  private Button makeAddGridButton() {
    Button button = new Button(myResources.getString("AddGrid"));
    button.setOnAction(e -> myCenterView.addViewToCenter());
    button.getStyleClass().add("addGridButton");
    button.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    button.setId("addGridButton");
    return button;
  }

  private Node makeViewChoiceBox() {
    ChoiceBox views = new ChoiceBox();
    views.getItems().addAll(myResources.getString("ViewTypes").split(","));
    views.setOnAction(e -> {
      myController.updateCenterViewType(myResources.getString(views.getValue().toString()));
    });
    views.setId("viewChoiceBox");
    return views;
  }

  private Node makeLangButton() {
    HBox langHBox = new HBox();
    Button langButton = new Button(EN);
    langButton.setOnAction(e -> toggleLanguage(langButton));
    langHBox.getChildren().add(langButton);
    langHBox.getStyleClass().add("buttons");
    return langHBox;
  }

  private void toggleLanguage(Button langButton) {
    if (langButton.getText().equals(EN)) {
      langButton.setText(ES);
      myController.setLang(ES);
    } else {
      langButton.setText(EN);
      myController.setLang(EN);
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

  /**
   * Updates the component with a new language
   *
   * @param bundle
   * @return mySimControl
   */
  public Node setResource(ResourceBundle bundle) {
    HBox buttons = (HBox) mySimControl.getChildren().get(0);
    Button addGrid = (Button) buttons.getChildren().get(2);
    addGrid.setText(bundle.getString("AddGrid"));
    buttons.getChildren().set(2, addGrid);
    mySimControl.getChildren().set(0, buttons);

    ChoiceBox choice = new ChoiceBox();
    choice.getItems().addAll(bundle.getString("ViewTypes").split(","));
    choice.setOnAction(e -> {
      myController.updateCenterViewType(bundle.getString(choice.getValue().toString()));
    });
    mySimControl.getChildren().set(2, choice);

    setStyles();
    return mySimControl;
  }
}
