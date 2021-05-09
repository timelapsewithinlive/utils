package utils;

/**
 * 类型判断
 *
 */
public class TypeDetector {


  /**
   * 判断对象类型符合期望类型
   * <p/>
   * 表达式可以是类全名、简单名称或者正则式
   * <p/>
   * 匹配顺序
   * <p/>
   * 1. 如果是类全名,则判断对象是否为该类的实例
   * <p/>
   * 2. 如果是类简单名称,则判断名称是否与表达式相同
   * <p/>
   * 3. 如果是正则式,则判断类全名是否匹配正则式
   *
   * @param obj
   * @param expression 类全名、类简单名称或者正则表达式
   * @return
   */
  public static boolean expected(Object obj, String expression) {
    try {
      Class clazz = Class.forName(expression);
      if (clazz.isInstance(obj)) {
        return true;
      }
    } catch (ClassNotFoundException e) {
      //ignore e
    }
    Class clazz = obj.getClass();
    if (clazz.getSimpleName().equals(expression) || clazz.getName().matches(expression)) {
      return true;
    }
    return false;
  }

}
