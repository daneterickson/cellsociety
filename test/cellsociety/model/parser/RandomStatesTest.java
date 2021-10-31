package cellsociety.model.parser;
import cellsociety.model.Grid;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomStatesTest {

  private ParserSIM myParser = new ParserSIM();


  @Test
  void testRandomNumFilled() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_num_filled.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    int filled = 0;
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        if (grid.getCellStateNumber(r,c) != 0) filled++;
      }
    }
    assertEquals(9, filled);
  }

  @Test
  void testRandomNumFilledRows() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_num_filled.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    assertEquals(5, grid.getNumRows());
  }

  @Test
  void testRandomNumFilledCols() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_num_filled.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    assertEquals(5, grid.getNumCols());
  }

  @Test
  void testRandomProbs2Rows() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_probs2_alive.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    assertEquals(5, grid.getNumRows());
  }

  @Test
  void testRandomProbs2Cols() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_probs2_alive.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    assertEquals(5, grid.getNumCols());
  }

  @Test
  void testRandomProbs2Alive() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_probs2_alive.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(1, grid.getCellStateNumber(r,c));
      }
    }
  }

  @Test
  void testRandomProbs2Dead() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_probs2_dead.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(0, grid.getCellStateNumber(r,c));
      }
    }
  }

  @Test
  void testRandomProbs2() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/game_of_life/random_probs2_equal.sim"));
    RandomStates rs = new RandomStates(myParser, 1);
    Grid grid = rs.makeGrid();
    int expected[][] = {{0,1,1,1,0},{1,0,0,0,0},{1,1,1,0,1},{0,0,1,1,1},{0,0,1,0,0}};
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(expected[r][c], grid.getCellStateNumber(r,c));
      }
    }
  }

  @Test
  void testRandom3NumFilled() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_num3_filled.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    int filled = 0;
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        if (grid.getCellStateNumber(r,c) != 0) filled++;
      }
    }
    assertEquals(9, filled);
  }

  @Test
  void testRandomProbs3Rows() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_probs3_empty.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    assertEquals(5, grid.getNumRows());
  }

  @Test
  void testRandomProbs3Cols() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_probs3_empty.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    assertEquals(5, grid.getNumCols());
  }

  @Test
  void testRandomProbs3Empty() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_probs3_empty.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(0, grid.getCellStateNumber(r,c));
      }
    }
  }

  @Test
  void testRandomProbs3Tree() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_probs3_tree.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(1, grid.getCellStateNumber(r,c));
      }
    }
  }

  @Test
  void testRandomProbs3Burn() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_probs3_burn.sim"));
    RandomStates rs = new RandomStates(myParser);
    Grid grid = rs.makeGrid();
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(2, grid.getCellStateNumber(r,c));
      }
    }
  }

  @Test
  void testRandomProbs3() throws FileNotFoundException, NoSuchFieldException {
    myParser.readFile(new File("data/spreading_of_fire/random_probs3_equal.sim"));
    RandomStates rs = new RandomStates(myParser, 1);
    Grid grid = rs.makeGrid();
    int expected[][] = {{2,2,1,0,1},{0,1,0,2,1},{2,2,0,1,1},{1,2,1,2,2},{2,0,0,2,2}};
    for (int r=0; r<5; r++) {
      for (int c=0; c<5; c++) {
        assertEquals(expected[r][c], grid.getCellStateNumber(r,c));
      }
    }
  }
}
