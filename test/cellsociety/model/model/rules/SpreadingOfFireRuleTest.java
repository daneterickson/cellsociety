package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SpreadingOfFireRuleTest {

  private Rule rule;
  private int nearby[];
  private List<Integer> nearbyList;

  @Test
  void testAllFireNoCatch() {
    rule = new SpreadingOfFireRule(0);
    nearby = new int[]{2,2,2,2,2,2,2,2};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
    actual = rule.determineState(0,0,2,nearbyList);
    assertEquals(0,actual);
  }

  @Test
  void testAllFireCatch() {
    rule = new SpreadingOfFireRule(1);
    nearby = new int[]{2,2,2,2,2,2,2,2};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(2,actual);
    actual = rule.determineState(0,0,2,nearbyList);
    assertEquals(0,actual);
  }

  @Test
  void testAllTrees() {
    rule = new SpreadingOfFireRule(0.5);
    nearby = new int[]{1,1,1,1,1,1,1,1};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
    actual = rule.determineState(0,0,2,nearbyList);
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
