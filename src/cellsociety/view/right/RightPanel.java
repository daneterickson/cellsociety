package cellsociety.view.right;


import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * This is the abstract class that makes the right panel of main view
 *
 * @author Aaric Han, Nick Strauch
 */


public abstract class RightPanel {

  private VBox theRightPanel;
  private static final int NUM_MINOR_TICKS = 2;
  private static final String RESOURCE = "cellsociety.view.right.";
  private static final String STYLESHEET = String.format("/%sRightSettings.css",
      RESOURCE.replace(".", "/"));
  private static final String CENTER_PATH = "cellsociety.view.center.%s";

  private ResourceBundle myResource;
  private Controller myController;

  /**
   * Constructor that makes a right panel
   *
   * @param bundle
   * @param controller
   */
  public RightPanel(ResourceBundle bundle, Controller controller) {
    myResource = bundle;
    myController = controller;
    theRightPanel = new VBox();
    makeSettingsPanel(theRightPanel);
  }

  protected abstract void makeSettingsPanel(VBox rightPanel);

  protected abstract Node makeButtons();

  protected abstract Node makeSliders();

  protected abstract Node makeTextBox();

  protected abstract void setProbSettings(ArrayList probability);


  protected Button makeAButton(String className, String buttonAction, String label,
      String cssLabel) {
    Button theButton = new Button(label);
    theButton.getStyleClass().add(cssLabel);
    theButton.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    theButton.setOnAction(value -> {
      try {
        Class.forName(String.format(CENTER_PATH, className)).getMethod(buttonAction);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
    return theButton;
  }

  protected Slider makeASlider(double min, double max, double start, String cssLabel, boolean ticks,
      double tickSpacing) {
    Slider theSlider = new Slider(min, max, start);
    theSlider.setShowTickMarks(ticks);
    theSlider.setSnapToTicks(true);
    theSlider.setMinorTickCount(NUM_MINOR_TICKS);
    theSlider.setMajorTickUnit(tickSpacing);
    theSlider.setShowTickLabels(ticks);
    theSlider.getStyleClass().add(cssLabel);
    theSlider.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    return theSlider;
  }

  protected Label makeALabel(String text, String cssLabel) {
    Label theLabel = new Label(text);
    theLabel.getStyleClass().add(cssLabel);
    theLabel.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    return theLabel;
  }

  public Node setResource(ResourceBundle bundle) {
    myResource = bundle;
    theRightPanel = new VBox();
    makeSettingsPanel(theRightPanel);
    return theRightPanel;
  }

  protected Controller getMyController() {
    return myController;
  }

  protected Button makeGridLinesToggleButton() {
    Button lineToggle = new Button(myResource.getString("ToggleGridLines"));
    lineToggle.getStyleClass().add("toggleGridLines");
    lineToggle.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    lineToggle.setId("toggleGridLines");
    lineToggle.setOnAction(e -> myController.toggleCenterLines());
    return lineToggle;
  }

  protected ChoiceBox makeEdgeCaseChoiceBox() {
    ChoiceBox edgeCaseChooser = new ChoiceBox();
    edgeCaseChooser.getItems().addAll(myResource.getString("EdgeTypes").split(","));
    edgeCaseChooser.setOnAction(
        e -> myController.setEdgePolicy(edgeCaseChooser.getValue().toString()));
    edgeCaseChooser.setId("edgeCaseChooser");
    return edgeCaseChooser;
  }

  protected ChoiceBox makeNeighborChoiceBox() {
    ChoiceBox neighborChooser = new ChoiceBox();
    neighborChooser.getItems().addAll(myResource.getString("NeighborTypes").split(","));
    neighborChooser.setOnAction(
        e -> myController.setNeighborFinder(neighborChooser.getValue().toString()));
    neighborChooser.setId("neighborChooser");
    return neighborChooser;
  }

  /**
   * Gets the right panel
   *
   * @return theRightPanel
   */
  public VBox getTheRightPanel() {
    return theRightPanel;
  }

  /**
   * Gets the right panel resource bundle
   *
   * @return myResource
   */
  public ResourceBundle getMyResource() {
    return myResource;
  }

}
