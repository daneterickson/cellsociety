package cellsociety.model.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PredatorPreyCellTest {

  private PredatorPreyCell myCell;
  private String myStartColors;
  private String myParameters;

  @BeforeEach
  void setUp() {
    myCell = new PredatorPreyCell(0, 0, myStartColors, myParameters, 0);
  }

  @Test
  void testChangeState() {
    myCell.changeState(1);
    assertEquals(1, Integer.valueOf(myCell.getCellProperty("StateNumber")));
    myCell.changeState(2);
    assertEquals(2, Integer.valueOf(myCell.getCellProperty("StateNumber")));
  }

  @Test
  void testSetColor() {
    assertEquals("c0c0c0", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testChangeColor() {
    myCell.changeState(1);
    assertEquals("00ff00", myCell.getCellProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("0000ff", myCell.getCellProperty("StateColor"));
  }

  @Test
  void testSetName() {
    assertEquals("empty", myCell.getCellProperty("StateName"));
  }

  @Test
  void testChangeName() {
    myCell.changeState(1);
    assertEquals("fish", myCell.getCellProperty("StateName"));
    myCell.changeState(2);
    assertEquals("shark", myCell.getCellProperty("StateName"));
  }

  @Test
  void testSetParameters() {
    myParameters = "1,2,3,4";
    myCell = new PredatorPreyCell(0, 0, myStartColors, myParameters, 0);
    assertEquals(1.0, myCell.getCellParameter("FishReproduction"));
    assertEquals(2.0, myCell.getCellParameter("SharkReproduction"));
    assertEquals(3.0, myCell.getCellParameter("SharkEnergyStart"));
    assertEquals(4.0, myCell.getCellParameter("SharkEnergyGain"));
  }

  @Test
  void testInitialColorStates() {
    myStartColors = "000000,111111,222222";
    myCell = new PredatorPreyCell(0, 0, myStartColors, myParameters, 0);
    myCell.changeState(1);
    assertEquals("111111", myCell.getCellProperty("StateColor"));
    myCell.changeState(2);
    assertEquals("222222", myCell.getCellProperty("StateColor"));
  }


}
