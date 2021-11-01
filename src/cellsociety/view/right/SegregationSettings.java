package cellsociety.view.right;

import cellsociety.controller.Controller;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SegregationSettings extends RightPanel{
  private final double MIN_SIMILARITY = 0;
  private final double MAX_SIMILARITY = 1;
  private final double STARTING_SIMILARITY = .5;

  private static final double TICK_SPACING = .1;


  public SegregationSettings(ResourceBundle bundle, Controller controller){
    super(bundle, controller);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){
    rightPanel.getChildren().addAll(makeSliders(), makeButtons());
  }

  @Override
  protected Node makeButtons(){
    return makeGridLinesToggleButton();
  }

  @Override
  protected Node makeSliders(){
    VBox sliderGroup = new VBox();
    Label similarityLabel = makeALabel(super.getMyResource().getString("SegregationSimilarityLabel"), "similarityLabel");
    Slider similaritySlider = makeASlider(MIN_SIMILARITY,MAX_SIMILARITY,STARTING_SIMILARITY, "similaritySlider", true, TICK_SPACING);
    similaritySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      ArrayList settingsPkg = new ArrayList();
      settingsPkg.add(similaritySlider.getValue());
      setProbSettings(settingsPkg);
    });
    sliderGroup.getChildren().addAll(similarityLabel, similaritySlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }

  @Override
  protected void setProbSettings(ArrayList probability) {
    super.getMyController().updateModelSettings(probability);
  }


}
