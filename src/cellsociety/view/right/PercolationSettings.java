package cellsociety.view.right;

import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


/**
 * This is subclass of right panel
 *
 * @author Aaric Han, Nick Strauch
 */

public class PercolationSettings extends RightPanel {

  /**
   * Constructor that makes a specific right panel
   *
   * @param bundle
   * @param controller
   */
  public PercolationSettings(ResourceBundle bundle, Controller controller) {
    super(bundle, controller);
  }

  @Override
  public void makeSettingsPanel(VBox rightPanel) {
    rightPanel.getChildren()
        .addAll(makeButtons(), makeEdgeCaseChoiceBox(), makeNeighborChoiceBox());
  }

  @Override
  protected Node makeButtons() {
    return makeGridLinesToggleButton();
  }

  @Override
  protected Node makeSliders() {
    return null;
  }

  @Override
  protected Node makeTextBox() {
    return null;
  }

  @Override
  protected void setProbSettings(ArrayList probability) {
  }

}
