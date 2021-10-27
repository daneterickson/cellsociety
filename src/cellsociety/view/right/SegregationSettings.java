package cellsociety.view.right;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SegregationSettings extends RightPanel{
  private VBox myRightPanel;
  private final double MIN_SIMILARITY = 0;
  private final double MAX_SIMILARITY = 1;
  private final double STARTING_SIMILARITY = .5;


  public SegregationSettings(){
    super();
    myRightPanel = getTheRightPanel();
  }

  @Override
  public void makeSettingsPanel(){
    myRightPanel.getChildren().addAll(makeSliders());
  }

  @Override
  protected Node makeButtons(){
    return null;
  }

  @Override
  protected Node makeSliders(){
    Group sliderGroup = new Group();
    Slider similaritySlider = makeASlider(MIN_SIMILARITY,MAX_SIMILARITY,STARTING_SIMILARITY, "similaritySlider");
    sliderGroup.getChildren().add(similaritySlider);
    return sliderGroup;
  }

  @Override
  protected Node makeTextBox(){
    return null;
  }



}
