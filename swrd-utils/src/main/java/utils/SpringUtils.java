package utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 根据{@link Qualifier}注解从Spring上下文中获取Bean
 */
public class SpringUtils {

  /**
   * 根据ownerClass声明的{@link Qualifier}注解获取相应的Bean
   *
   * @param beanClass  bean类型
   * @param ownerClass 声明了{@link Qualifier}注解的宿主类
   * @param context
   * @param <T>
   * @return
   */
  public static <T> T getQualifiedBean(Class<T> beanClass, Class ownerClass, ApplicationContext context) {
    Qualifier qualifier = AnnotationUtils.findAnnotation(ownerClass, Qualifier.class);
    String value = null;
    if (qualifier != null) {
      value = context.getEnvironment().resolvePlaceholders(qualifier.value());
    }
    //未使用Qualifier注解
    if (StringUtils.isEmpty(value)) {
      return context.getBean(beanClass);
    }
    return getBean(value, beanClass, context);
  }

  /**
   * 根据ownerClass声明的{@link Qualifier}注解获取相应的Bean
   *
   * @param ownerClass 声明了{@link Qualifier}注解的宿主类
   * @param context
   * @return
   */
  public static Object getQualifiedBean(Class ownerClass, ApplicationContext context) {
    Qualifier qualifier = AnnotationUtils.findAnnotation(ownerClass, Qualifier.class);
    String value = null;
    if (qualifier != null) {
      value = context.getEnvironment().resolvePlaceholders(qualifier.value());
    }
    return getBean(value, context);
  }


  /**
   * @param qualifier Bean名称或具体类型
   * @param beanClass Bean声明类型
   * @param <T>
   * @return
   */
  public static <T> T getBean(String qualifier, Class<T> beanClass, ApplicationContext context) {
    try {
      Object bean = context.getBean(qualifier);
      if (beanClass.isInstance(bean)) {
        return (T) bean;
      }
    } catch (NoSuchBeanDefinitionException e) {
      Class aClass;
      try {
        aClass = Class.forName(qualifier);
      } catch (ClassNotFoundException e1) {
        //ignore
        return null;
      }
      if (beanClass.isAssignableFrom(aClass)) {
        throw new ClassCastException("required type: " + beanClass.getName() + " is not assignable from: " + aClass.getName());
      }
      return (T) context.getBean(aClass);
    }
    return null;
  }

  /**
   * @param qualifier Bean的name或具体类型
   * @return
   */
  public static Object getBean(String qualifier, ApplicationContext context) {
    try {
      return context.getBean(qualifier);
    } catch (NoSuchBeanDefinitionException e) {
      Class aClass;
      try {
        aClass = Class.forName(qualifier);
      } catch (ClassNotFoundException e1) {
        //ignore
        return null;
      }
      return context.getBean(aClass);
    }
  }
}
