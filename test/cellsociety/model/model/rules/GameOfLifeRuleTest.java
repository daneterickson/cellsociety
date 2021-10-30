package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GameOfLifeRuleTest {

  private Rule rule = new GameOfLifeRule();
  private int nearby[];
  private List<Integer> nearbyList;

  @Test
  void testAllDead() {
    nearby = new int[]{0,0,0,0,0,0,0,0};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(0,actual);
  }

  @Test
  void testStableCell() {
    nearby = new int[]{1,1,0,0,0,0,0,0};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
  }

  @Test
  void testGenerateCell() {
    nearby = new int[]{1,1,1,0,0,0,0,0};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(1,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
  }

  @Test
  void testRemoveCell() {
    nearby = new int[]{1,1,1,1,0,0,0,0};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(0,actual);
  }

  private List<Integer> makeNearbyList(int[] nearby) {
    List<Integer> ret = new ArrayList<>();
    for (int i : nearby) {
      ret.add(i);
    }
    return ret;
  }

}
