package utils;

import jodd.bean.BeanUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具类
 *
 * @version 1.0.0
 */
public class ReflectionUtils {
  /**
   * 调用对象的指定方法
   *
   * @param bean
   * @param methodName
   * @param args
   * @return
   * @throws Exception
   */
  public static Object invoke(Object bean, String methodName, Object[] args) throws Exception {
    Class beanClass = bean.getClass();
    Method method = find(beanClass, methodName, args);
    return invoke(bean, method, args);
  }


  /**
   * 调用对象的指定方法
   *
   * @param bean
   * @param method
   * @param args
   * @return
   * @throws Exception
   */
  public static Object invoke(Object bean, Method method, Object[] args) throws Exception {
    Class[] types = method.getParameterTypes();
    // 构造参数转型
    Object[] castArgs = new Object[args.length];
    for (int i = 0; i < types.length; i++) {
      if (types[i].isInstance(args[i])) {
        castArgs[i] = args[i];
      }
      castArgs[i] = simpleInstance(types[i], args[i].toString());
    }
    return method.invoke(bean, castArgs);
  }

  /**
   * 根据方法名和参数查找方法对象
   *
   * @param beanClass
   * @param methodName
   * @param args
   * @return
   * @throws Exception
   */
  public static Method find(Class beanClass, String methodName, Object[] args) {
    try {
      Class<?>[] types = new Class<?>[args.length];
      for (int i = 0; i < args.length; i++) {
        types[i] = args[i].getClass();
      }
      return beanClass.getMethod(methodName, types);
    } catch (NoSuchMethodException e) {
      //适合的方法
      List<Method> suitable = new ArrayList();
      //参数相同的方法
      List<Method> matched = new ArrayList();
      for (Method method : beanClass.getMethods()) {
        if (method.getName().equals(methodName)
                && method.getParameterTypes().length == args.length) {
          if (matchType(args, method.getParameterTypes())) {
            suitable.add(method);
          } else {
            matched.add(method);
          }
        }
      }
      if (suitable.size() == 1) {
        return suitable.get(0);
      }
      if (suitable.size() > 1) {
        throw new IllegalArgumentException("ambiguous method [" + methodName + "] for class [" + beanClass + "] with args: "
                + Arrays.toString(args));
      }
      if (matched.isEmpty()) {
        throw new IllegalArgumentException("no method [" + methodName + "] for class [" + beanClass + "] with args: "
                + Arrays.toString(args));
      }
      if (matched.size() > 1) {
        throw new IllegalArgumentException("ambiguous method [" + methodName + "] for class [" + beanClass + "] with args: "
                + Arrays.toString(args));
      }
      return matched.get(0);
    }
  }

  /**
   * 构建简单对象实例
   *
   * @param clazz
   * @param valueString
   * @param <T>
   * @return
   */
  public static <T> T simpleInstance(Class<T> clazz, String valueString) {
    if (clazz.equals(String.class)) {
      return (T) valueString;
    }
    if (clazz.equals(Boolean.TYPE) || clazz.equals(Boolean.class)) {
      return (T) Boolean.valueOf(valueString);
    }
    if (clazz.equals(Byte.TYPE) || clazz.equals(Byte.class)) {
      return (T) Byte.valueOf(valueString);
    }
    if (clazz.equals(Short.TYPE) || clazz.equals(Short.class)) {
      return (T) Short.valueOf(valueString);
    }
    if (clazz.equals(Integer.TYPE) || clazz.equals(Integer.class)) {
      return (T) Integer.valueOf(valueString);
    }
    if (clazz.equals(Long.TYPE) || clazz.equals(Long.class)) {
      return (T) Long.valueOf(valueString);
    }
    if (clazz.equals(Float.TYPE) || clazz.equals(Float.class)) {
      return (T) Float.valueOf(valueString);
    }
    if (clazz.equals(Double.TYPE) || clazz.equals(Double.class)) {
      return (T) Double.valueOf(valueString);
    }
    if (clazz.equals(Character.TYPE) || clazz.equals(Character.class)) {
      return (T) Character.valueOf(valueString.charAt(0));
    }
    if (clazz.isEnum()) {
      return (T) Enum.valueOf((Class<Enum>) clazz, valueString);
    }
    try {
      return clazz.getConstructor(String.class).newInstance(valueString);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * 获取指定对象的属性值,如果类型不匹配,返回null
   *
   * @param target
   * @param name
   * @param type
   * @param <T>
   * @return
   */
  public static <T> T getProperty(Object target, String name, Class<T> type) {

    Object value = getProperty(target, name);
    if (type.isInstance(value)
            || type == int.class && Integer.class.isInstance(value)
            || type == long.class && Long.class.isInstance(value)
            || type == float.class && Float.class.isInstance(value)
            || type == double.class && Double.class.isInstance(value)
            || type == char.class && Character.class.isInstance(value)
            || type == byte.class && Byte.class.isInstance(value)
            || type == boolean.class && Boolean.class.isInstance(value)
            ) {
      return (T) value;
    }
    return null;
  }

  /**
   * 获取指定对象的属性值,如果类型不匹配,返回null
   *
   * @param target
   * @param name
   * @return
   */
  public static Object getProperty(Object target, String name) {

    if(target==null){
      return null;
    }
    Object value = null;
    try {
      value = BeanUtil.getProperty(target, name);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return value;
  }


  /**
   * 判断类型是否匹配
   *
   * @param args
   * @param cls
   * @return
   */
  private static boolean matchType(Object[] args, Class[] cls) {
    if (args.length != cls.length) {
      return false;
    }
    for (int i = 0; i < args.length; i++) {
      if (!cls[i].isInstance(args[i])) {
        return false;
      }
    }
    return true;
  }
}
