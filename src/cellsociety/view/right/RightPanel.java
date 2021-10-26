package cellsociety.view.right;

import java.lang.reflect.Method;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.ObjectUtils.Null;

public abstract class RightPanel {
  private VBox theRightPanel;
  public RightPanel() {
     theRightPanel = new VBox();
  }

  protected abstract void makeSettingsPanel();

  protected abstract Node makeButtons();

  protected abstract Node makeSliders();

  protected abstract Node makeTextBox();

  public Node makeAButton(String className, String buttonAction, String label) {
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

  protected Node getTheRightPanel() {
    return theRightPanel;
  }
}
