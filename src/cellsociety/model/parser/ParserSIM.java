package cellsociety.model.parser;

import java.io.File;

public class ParserSIM implements Parser {
  private File myFile;
  private String myType;
  private String myTitle;
  private String myAuthor;
  private String myDescription;
  private String myInitialStates;

  public ParserSIM (File file) {
    myFile = file;
  }

  public void readFile() {
    
  }



}
