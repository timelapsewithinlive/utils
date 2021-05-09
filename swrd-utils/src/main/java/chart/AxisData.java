package chart;

import java.util.Map;
import java.util.TreeMap;

/**
 * 一维数据
 *
 */
public class AxisData<Y extends Number> {

  private TreeMap<String, Y> data = new TreeMap();


  public Map<String, Y> getData() {
    return data;
  }

  public void add(String category, Y y) {
    Y number = data.get(category);
    if (number == null) {
      data.put(category, y);
      return;
    }
    data.put(category, XyAxisData.add(number, y));
  }


}
