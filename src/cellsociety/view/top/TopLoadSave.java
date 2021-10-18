package cellsociety.view.top;

import java.io.File;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class TopLoadSave {
  private String RESOURCE = "cellsociety.view.top.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"TopLoadSave.css";

  private HBox myTopLoadSave;

  public TopLoadSave() {
    myTopLoadSave = new HBox();
    myTopLoadSave.getChildren().add(makeLoadSaveButtons());
    setStyles();
  }

  private void setStyles() {
    myTopLoadSave.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    myTopLoadSave.getStyleClass().add("root");
  }

  private Node makeLoadSaveButtons() {
    HBox topLoadSave = new HBox();
    topLoadSave.getChildren().add(makeLoadButton());
    return topLoadSave;
  }

  private Node makeLoadButton() {
    Button loadButton = new Button("Load File");
    loadButton.setOnAction(e -> loadFile());
    return loadButton;
  }


  private void loadFile() {
    // pop up window to choose file
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
    File selectedFile = fileChooser.showOpenDialog(root);
    if (selectedFile == null) {
      return;
    }

  }

  public Node getTopLoadSave() { return myTopLoadSave; }
}
