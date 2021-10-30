package cellsociety.model.model.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PercolationRuleTest {

  private Rule rule = new PercolationRule();
  private int nearby[];
  private List<Integer> nearbyList;

  @Test
  void testPercolate() {
    nearby = new int[]{0,1,0,0,0,0,0,0};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(1,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
  }

  @Test
  void testNoPercolate() {
    nearby = new int[]{2,2,2,2,2,2,2,2};
    nearbyList = makeNearbyList(nearby);
    int actual = rule.determineState(0,0,0,nearbyList);
    assertEquals(0,actual);
    actual = rule.determineState(0,0,1,nearbyList);
    assertEquals(1,actual);
  }

  private List<Integer> makeNearbyList(int[] nearby) {
    List<Integer> ret = new ArrayList<>();
    for (int i : nearby) {
      ret.add(i);
    }
    return ret;
  }

}
