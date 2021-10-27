package cellsociety.view.right;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public abstract class RightPanel {
  private VBox theRightPanel;
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


  protected VBox getTheRightPanel() {
    return theRightPanel;
  }

}
