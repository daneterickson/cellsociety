package cellsociety.model.cell;

import cellsociety.model.exceptions.KeyNotFoundException;
import java.util.Map;

public interface ViewCellInterface {

  String getCellProperty(String property) throws KeyNotFoundException;

  Map getNameColorMap();
}
