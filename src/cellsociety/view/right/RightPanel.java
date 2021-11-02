package cellsociety.view.right;


import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public abstract class RightPanel {
  private VBox theRightPanel;
  private static final int NUM_MINOR_TICKS = 2;
  protected static final String RESOURCE = "cellsociety.view.right.";
  protected static final String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "RightSettings.css";
  private static final String CENTER_PATH = "cellsociety.view.center.%s";


  private ResourceBundle myResource;
  private Controller myController;

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


  protected Button makeAButton(String className, String buttonAction, String label, String cssLabel) {
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

  protected Slider makeASlider(double min, double max, double start, String cssLabel, boolean ticks, double tickSpacing){
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

  protected Label makeALabel(String text, String cssLabel){
    Label theLabel = new Label(text);
    theLabel.getStyleClass().add(cssLabel);
    theLabel.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    return theLabel;
  }

  public VBox getTheRightPanel() {
    return theRightPanel;
  }

  public ResourceBundle getMyResource() { return myResource; }

  public Node setResource(ResourceBundle bundle) {
    myResource = bundle;
    theRightPanel = new VBox();
    makeSettingsPanel(theRightPanel);
    return theRightPanel;
  }

  protected Controller getMyController() { return myController; }

  protected Button makeGridLinesToggleButton(){
    Button lineToggle = new Button(myResource.getString("ToggleGridLines"));
    lineToggle.getStyleClass().add("toggleGridLines");
    lineToggle.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    lineToggle.setId("toggleGridLines");
    lineToggle.setOnAction(e -> myController.toggleCenterLines());
    return lineToggle;
  }

}
