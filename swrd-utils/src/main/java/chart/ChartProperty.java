package chart;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChartProperty {

  /**
   * 监控统计名称
   */
  String name();

  /**
   * 展示类型，空表示柱状图，line表示曲线图
   */
  String type() default "";

  /**
   * 是否聚合
   */
  boolean agg() default true;

  /**
   * 排序
   */
  int order() default 0;

  /**
   * 统计方式，全局统计或者增量统计
   * @return
   */
  StatWay statWay() default StatWay.INCREMENT;

  enum StatWay {
    OVERALL, // 全量
    INCREMENT // 增量
  }
}
