package utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 属性文件加载类
 *
 */
public class PropertiesUtil {

  /**
   * local cache
   */
  private static final Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

  /**
   * 加载properties文件
   *
   * @param resource 资源文件
   * @param charset  字符编码
   * @param cached   是否缓存
   * @return Properties对象
   */
  public static Properties load(String resource, String charset, boolean cached) {
    try {
      if (propertiesMap.containsKey(resource)) {
        return propertiesMap.get(resource);
      }
      ResourceLoader resourceLoader = new DefaultResourceLoader();
      Properties properties = PropertiesLoaderUtils.loadProperties(
              new EncodedResource(resourceLoader.getResource(resource), charset));
      if (cached) {
        propertiesMap.put(resource, properties);
      }
      return properties;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 加载properties文件
   *
   * @param resource 资源文件
   * @param charset  文件编码
   * @return Properties对象
   */
  public static Properties load(String resource, String charset) {
    return load(resource, charset, true);
  }

  /**
   * 加载properties文件
   *
   * @param resource 资源文件
   * @return Properties对象
   */
  public static Properties load(String resource) {
    return load(resource, "UTF-8");
  }

  /**
   * 获取指定资源的属性
   *
   * @param property
   * @param resource
   * @return
   */
  public static String getProperty(String property, String resource) {
    Properties properties = load(resource);
    return properties != null ? properties.getProperty(property) : null;
  }
}
