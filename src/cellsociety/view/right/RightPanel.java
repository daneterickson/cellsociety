package cellsociety.view.right;

import java.lang.reflect.Method;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.ObjectUtils.Null;

public abstract class RightPanel {
  private VBox theRightPanel;
  protected static final String RESOURCE = "cellsociety.view.right.";
  protected static final String STYLESHEET = "/" + RESOURCE.replace(".", "/") + "RightSettings.css";

  public RightPanel() {
     theRightPanel = new VBox();
  }

  protected abstract void makeSettingsPanel();

  protected abstract Node makeButtons();

  protected abstract Node makeSliders();

  protected abstract Node makeTextBox();


  protected Node makeAButton(String className, String buttonAction, String label) {
    Button theButton = new Button(label);
    theButton.setOnAction(value -> {
      try {
        Class.forName(className).getMethod(buttonAction);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    });
    return theButton;
  }

  protected Slider makeASlider(double min, double max, double start, String cssLabel, boolean ticks){
    Slider theSlider = new Slider(min, max, start);
    theSlider.setShowTickLabels(ticks);
    theSlider.setShowTickMarks(ticks);
    theSlider.getStyleClass().add(cssLabel);
    theSlider.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    return theSlider;
  }

  public VBox getTheRightPanel() {
    return theRightPanel;
  }

}
