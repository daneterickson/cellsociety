package cellsociety.view.top;

import cellsociety.controller.Controller;
import java.io.File;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TopLoadSave {
  private String RESOURCE = "cellsociety.view.top.";
  private String STYLESHEET = "/"+RESOURCE.replace(".", "/")+"TopLoadSave.css";

  private HBox myTopLoadSave;
  private Stage myStage;
  private Controller myController;

  public TopLoadSave(Stage stage, Controller controller) {
    myStage = stage;
    myTopLoadSave = new HBox();
    myTopLoadSave.getChildren().add(makeLoadSaveButtons());
    setStyles();
    myController = controller;
  }

  private void setStyles() {
    myTopLoadSave.getStyleClass().add("root");
    myTopLoadSave.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
  }

  private Node makeLoadSaveButtons() {
    HBox topLoadSave = new HBox(15);
//    topLoadSave.getChildren().add(makeLoadCSVButton());
//    topLoadSave.getChildren().add(makeSaveCSVButton());
    topLoadSave.getChildren().add(makeLoadSIMButton());
    topLoadSave.getChildren().add(makeSaveSIMButton());
    return topLoadSave;
  }

  private Node makeLoadCSVButton() {
    Button loadCSVButton = new Button("Load CSV File");
    loadCSVButton.setOnAction(e -> loadCSVFile());
    return setID(loadCSVButton, "LoadCSVButton");
  }

  private Node makeLoadSIMButton() {
    Button loadButton = new Button("Load Sim File");
    loadButton.setOnAction(e -> loadSIMFile());
    return loadButton;
  }

  private Node makeSaveCSVButton() {
    Button loadButton = new Button("Save CSV File");
    loadButton.setOnAction(e -> saveCSVFile());
    return setID(loadButton, "SaveCSVButton");
  }

  private Node makeSaveSIMButton() {
    Button saveSIMButton = new Button("Save SIM");
    saveSIMButton.setOnAction(e -> saveSIM());
    return setID(saveSIMButton, "SaveSIMButton");
  }

  private void loadCSVFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load CSV File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", "*.csv"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if (selectedFile == null) {
      return;
    }
    myController.openCSVFile(selectedFile);
  }

  private void loadSIMFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Load SIM File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SIM", "*.sim"));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if (selectedFile == null) {
      return;
    }
    myController.openSIMFile(selectedFile);
  }

  private void saveCSVFile() {
    myController.saveCSVFile();
  }

  private void saveSIM() {
    saveCSVFile();
    // TODO: Add a method to save the SIM file
  }

  private Node setID(Node disp, String id) {
    disp.setId(id);
    return disp;
  }

  public Node getTopLoadSave() { return myTopLoadSave; }

}
