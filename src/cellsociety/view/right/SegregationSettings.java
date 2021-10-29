package cellsociety.view.right;

import java.util.ResourceBundle;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SegregationSettings extends RightPanel{
  private final double MIN_SIMILARITY = 0;
  private final double MAX_SIMILARITY = 1;
  private final double STARTING_SIMILARITY = .5;


  public SegregationSettings(ResourceBundle bundle){
    super(bundle);
  }

  @Override
  protected void makeSettingsPanel(VBox rightPanel){
    rightPanel.getChildren().addAll(makeSliders());
  }

  @Override
  protected Node makeButtons(){
    return null;
  }

  @Override
  protected Node makeSliders(){
    VBox sliderGroup = new VBox();
    Slider similaritySlider = makeASlider(MIN_SIMILARITY,MAX_SIMILARITY,STARTING_SIMILARITY, "similaritySlider", true);
    sliderGroup.getChildren().add(similaritySlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }



}
