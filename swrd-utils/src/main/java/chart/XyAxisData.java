package chart;

import com.google.common.collect.ImmutableMap;
import org.mvel2.MVEL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * XY轴多维数据模型
 *
 */
public class XyAxisData<X extends Comparable<X>, Y extends Number> {

  /**
   * 图例对象
   */
  public class Legend implements Comparable<Legend> {

    /**
     * 图例名称
     */
    private String name;
    /**
     * 数据类型
     */
    private String type = "";
    /**
     * 是否聚合
     */
    private boolean agg = true;
    /**
     * 排序
     */
    private int order;

    /**
     * 在二维坐标轴上是否展示
     */
    private boolean show = true;

    /**
     *
     */
    public Legend(String name) {
      this(name, "", true, counter.getAndIncrement(), true);
    }

    /**
     * @param name
     * @param type
     * @param agg
     * @param order
     */
    public Legend(String name, String type, boolean agg, int order, boolean show) {
      this.name = name;
      this.type = type;
      this.agg = agg;
      this.order = order;
      this.show = show;
    }


    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }

    public Legend setType(String type) {
      this.type = type;
      return this;
    }

    public boolean isAgg() {
      return agg;
    }

    public Legend setAgg(boolean agg) {
      this.agg = agg;
      return this;
    }

    public int getOrder() {
      return order;
    }

    public boolean isShow() {
      return show;
    }

    public Legend setShow(boolean show) {
      this.show = show;
      return this;
    }

    public Legend setOrder(int order) {
      this.order = order;
      return this;
    }

    @Override
    public int compareTo(Legend o) {
      return this.order - o.order;
    }

    @Override
    public String toString() {
      return "Legend{" +
              "name='" + name + '\'' +
              ", type='" + type + '\'' +
              ", agg=" + agg +
              ", order=" + order +
              '}';
    }
  }

  /**
   * 默认计算器
   */
  private AtomicInteger counter = new AtomicInteger();
  /**
   * x轴数据
   */
  private List<X> xAxis;
  /**
   * y轴数据的单位
   */
  private String yAxisesUnit = "";
  /**
   * y轴数据
   */
  private LinkedHashMap<String, List<Y>> yAxises;

  /**
   * 数据图例
   */
  private Map<String, Legend> legends = new LinkedHashMap<>();

  /**
   * 构造方法
   *
   * @param xAxis
   * @param yAxises
   */
  public XyAxisData(List<X> xAxis, Map<String, List<Y>> yAxises) {
    this.xAxis = xAxis;
    this.yAxises = new LinkedHashMap<>(yAxises);
    //设置默认图例
    for (String name : this.yAxises.keySet()) {
      this.legends.put(name, new Legend(name));
    }
  }

  public String getyAxisesUnit() {
    return yAxisesUnit;
  }

  private void setyAxisesUnit(String yAxisesUnit) {
    this.yAxisesUnit = yAxisesUnit;
  }

  /**
   * X轴数据
   */
  public List<X> getxAxis() {
    return xAxis;
  }

  /**
   * 在二维坐标轴上需要展示的Y轴数据
   * 返回Map为不可变对象，不允许在外面对其进行修改
   */
  public Map<String, List<Y>> getyAxises() {
    ImmutableMap.Builder<String, List<Y>> mapBuilder = ImmutableMap.builder();
    for (String name : yAxises.keySet()) {
      Legend legend = legends.get(name);
      // 查不到属性或者show属性为true，则是需要展示的数据
      if (legend == null || legend.show) {
        mapBuilder.put(name, yAxises.get(name));
      }
    }
    return mapBuilder.build();
  }


  /**
   * 汇总数据
   */
  public Map<String, Y> getSummary() {
    Map<String, Y> summary = new LinkedHashMap<>();
    for (String name : yAxises.keySet()) {
      Legend legend = legends.get(name);
      if (legend != null && legend.agg) {
        //这里初始化为最低优先级类型：Integer
        Y sum = (Y) (Integer) 0;
        List<Y> yList = yAxises.get(legend.name);
        if (yList != null) {
          for (Y y : yList) {
            sum = add(sum, y);
          }
          summary.put(name, sum);
        }
      }
    }
    return summary;
  }

  /**
   * 获取数据图例
   *
   * @return
   */
  public Map<String, Legend> getLegends() {
    List<Legend> list = new ArrayList<>(this.legends.values());
    Collections.sort(list);
    LinkedHashMap<String, Legend> map = new LinkedHashMap();
    for (Legend legend : list) {
      map.put(legend.name, legend);
    }
    return map;
  }

  /**
   * 获取legend对象
   *
   * @param category
   * @return
   */
  public Legend getLegend(String category) {
    return this.legends.get(category);
  }

  /**
   * 数值求和
   *
   * @param n1
   * @param n2
   * @param <T>
   * @return
   */

  public static <T extends Number> T add(T n1, T n2) {
    //类型优先
    if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
      int scale = getMaxScale(n1, n2);
      return (T) new BigDecimal(n1.doubleValue() + n2.doubleValue()).setScale(scale, BigDecimal.ROUND_HALF_UP);
    } else if (n1 instanceof Double || n2 instanceof Double) {
      return (T) (Double) (n1.doubleValue() + n2.doubleValue());
    } else if (n1 instanceof Float || n2 instanceof Float) {
      return (T) (Float) (n1.floatValue() + n2.floatValue());
    } else if (n1 instanceof Long || n2 instanceof Long) {
      return (T) (Long) (n1.longValue() + n2.longValue());
    } else {
      return (T) (Integer) (n1.intValue() + n2.intValue());
    }
  }

  private static <T extends Number> int getMaxScale(T n1, T n2) {
    if (n1 instanceof BigDecimal && n2 instanceof BigDecimal) {
      return Math.max(((BigDecimal)n1).scale(), ((BigDecimal)n2).scale());
    } else if (n1 instanceof BigDecimal) {
      return ((BigDecimal)n1).scale();
    } else if (n2 instanceof BigDecimal) {
      return ((BigDecimal)n2).scale();
    }
    return 0;
  }

  /**
   * 图表数据构造器
   */
  public static class XyAxisDataBuilder<X extends Comparable<X>, Y extends Number> {

    /**
     * x轴数据
     */
    private Collection<X> xAxis;
    /**
     * y轴原始数据
     */
    private Map<String, Map<X, Y>> rawData = new HashMap<>();

    /**
     * x轴使用插入顺序
     */
    private boolean xAxisUseInsertOrder = false;

    /**
     * y轴数据的单位
     */
    private String yAxisesUnit = "";

    public XyAxisDataBuilder() {
      this(false);
    }

    public XyAxisDataBuilder(boolean xAxisUseInsertOrder) {
      this.xAxisUseInsertOrder = xAxisUseInsertOrder;
      if (xAxisUseInsertOrder) {
        xAxis = new ArrayList<>();
      } else {
        xAxis = new HashSet<>();
      }
    }


    /**
     * X轴补零
     *
     * @param x x轴坐标
     */
    public void fill(X x) {
      if (!this.xAxis.contains(x)) {
        this.xAxis.add(x);
      }
    }


    /**
     * 添加维度数据
     *
     * @param category 维度
     * @param x
     * @param y
     */
    public XyAxisDataBuilder<X, Y> add(String category, X x, Y y) {
      fill(x);
      if (!rawData.containsKey(category)) {
        rawData.put(category, new HashMap<X, Y>());
      }
      Map<X, Y> xyMap = rawData.get(category);
      if (!xyMap.containsKey(x)) {
        xyMap.put(x, y);
      } else {
        xyMap.put(x, XyAxisData.add(y, xyMap.get(x)));
      }
      return this;
    }

    /**
     * @param category
     * @param data
     */
    public XyAxisDataBuilder<X, Y> add(String category, Map<X, Y> data) {
      for (Entry<X, Y> entry : data.entrySet()) {
        this.add(category, entry.getKey(), entry.getValue());
      }
      return this;
    }

    /**
     * 增加计算列
     *
     * @param category
     * @param expression 表达式,使用$['name']引用维度数据
     * @return
     */
    public XyAxisDataBuilder<X, Y> compute(String category, String expression, Class<? extends Number> type) {
      Map<X, Y> data = new HashMap<>();
      for (X x : xAxis) {
        Map varMap = new HashMap() {
          @Override
          public Object get(Object key) {
            Object value = super.get(key);
            return value == null ? 0 : value;
          }
        };
        for (Entry<String, Map<X, Y>> entry : rawData.entrySet()) {
          varMap.put(entry.getKey(), entry.getValue().get(x));
        }
        Map root = new HashMap();
        root.put("$", varMap);
        Number eval = MVEL.eval("r=(" + expression + ");Double.isNaN(r) ? r: (new java.math.BigDecimal(r)).setScale(2,4).doubleValue()", root, type);
        if (eval != null && eval.equals(Double.NaN)) {
          eval = 0;
        }
        data.put(x, (Y) eval);
      }
      this.add(category, data);
      return this;
    }

    public XyAxisDataBuilder<X, Y> setyAxisesUnit(String yAxisesUnit) {
      this.yAxisesUnit = yAxisesUnit;
      return this;
    }

    /**
     * 构建表格数据
     */
    public XyAxisData<X,Y> build() {
      List<X> xList = new ArrayList<>(xAxis);
      if (!xAxisUseInsertOrder) {
        Collections.sort(xList);
      }
      Map<String, List<Y>> yMap = new TreeMap<>();
      for (Entry<String, Map<X, Y>> entry : rawData.entrySet()) {
        Map<X, Y> data = entry.getValue();
        List<Y> yAxis = new ArrayList<>();
        for (X xAxi : xList) {
          Y y = data.get(xAxi);
          yAxis.add(y != null ? y : (Y) new Integer(0));
        }
        yMap.put(entry.getKey(), yAxis);
      }
      XyAxisData<X, Y> xyXyAxisData = new XyAxisData<>(xList, yMap);
      xyXyAxisData.setyAxisesUnit(yAxisesUnit);
      return xyXyAxisData;
    }
  }

}
