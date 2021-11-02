package cellsociety.view.right;

import cellsociety.controller.Controller;
import java.lang.reflect.Array;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * This is subclass of right panel
 *
 * @author Aaric Han, Nick Strauch
 */
public class PredatorPreySettings extends RightPanel {

  private static final int MIN_FISH_REP = 1;
  private static final int MAX_FISH_REP = 21;
  private static final int START_FISH_REP = 5;
  private static final int MIN_SHARK_REP = 1;
  private static final int MAX_SHARK_REP = 21;
  private static final int START_SHARK_REP = 5;
  private static final int MIN_SHARK_E = 1;
  private static final int MAX_SHARK_E = 21;
  private static final int START_SHARK_E = 5;
  private static final int MIN_E_GAINED = 1;
  private static final int MAX_E_GAINED = 21;
  private static final int START_E_GAINED = 5;

  private int slidersChanged = 0;

  private static final int TICK_SPACING = 5;

  /**
   * Constructor that makes a specific right panel
   * @param bundle
   * @param controller
   */
  public PredatorPreySettings(ResourceBundle bundle, Controller controller) {
    super(bundle, controller);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel) {
    rightPanel.getChildren().addAll(makeSliders(), makeButtons(), makeEdgeCaseChoiceBox(), makeNeighborChoiceBox());
  }

  @Override
  protected Node makeButtons() {return makeGridLinesToggleButton();}

  @Override
  protected Node makeSliders() {
    VBox sliderGroup = new VBox();


    ArrayList settingsPkg = new ArrayList(4);
    settingsPkg.add(0, START_FISH_REP);
    settingsPkg.add(1, START_SHARK_REP);
    settingsPkg.add(2, START_SHARK_E);
    settingsPkg.add(3, START_E_GAINED);

    Label fishRepLabel = makeALabel(super.getMyResource().getString("PredatorPreyFishRepLabel"), "fishRepLabel");
    Slider fishRepSlider = makeASlider(MIN_FISH_REP, MAX_FISH_REP, START_FISH_REP, "fishRepSlider",
        true, TICK_SPACING);
    Label sharkRepLabel = makeALabel(super.getMyResource().getString("PredatorPreySharkRepLabel"), "sharkRepLabel");
    Slider sharkRepSlider = makeASlider(MIN_SHARK_REP, MAX_SHARK_REP, START_SHARK_REP,
        "sharkRepSlider", true, TICK_SPACING);
    Label sharkELabel = makeALabel(super.getMyResource().getString("PredatorPreySharkEnergyLabel"), "sharkELabel");
    Slider sharkEnergySlider = makeASlider(MIN_SHARK_E, MAX_SHARK_E, START_SHARK_E,
        "sharkEnergySlider", true, TICK_SPACING);

    Label fishEValLabel = makeALabel(super.getMyResource().getString("PredatorPreyEnergyGainedLabel"), "fishEValLabel");
    Slider fishEnergyValueSlider = makeASlider(MIN_E_GAINED, MAX_E_GAINED, START_E_GAINED,
        "fishEValSlider", true, TICK_SPACING);

    fishRepSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
      settingsPkg.set(0, fishRepSlider.getValue());
      slidersChanged(settingsPkg);
    }));
    sharkRepSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
      settingsPkg.set(1, sharkRepSlider.getValue());
      slidersChanged(settingsPkg);
    }));
    sharkEnergySlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
      settingsPkg.set(2, sharkEnergySlider.getValue());
      slidersChanged(settingsPkg);
    }));
    fishEnergyValueSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {
      settingsPkg.set(3, fishEnergyValueSlider.getValue());
      slidersChanged(settingsPkg);
    }));




    sliderGroup.getChildren()
        .addAll(fishRepLabel, fishRepSlider, sharkRepLabel, sharkRepSlider, sharkELabel,
            sharkEnergySlider, fishEValLabel, fishEnergyValueSlider);
    return sliderGroup;
  }

  private void slidersChanged(ArrayList settingsToSend) {
    setProbSettings(settingsToSend);
  }

  @Override
  protected Node makeTextBox() {
    return null;
  }

  @Override
  protected void setProbSettings(ArrayList probability) { }
}
