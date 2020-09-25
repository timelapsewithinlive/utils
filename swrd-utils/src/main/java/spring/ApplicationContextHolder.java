package spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware, EnvironmentAware {

    /**
     * 获取静态bean
     */

    private static ApplicationContext applicationContext;
    /**
     * 获取静态变量
     */
    private static Environment environment;

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        if (!applicationContext.containsBean(name)) {
            return null;
        }
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> var1, Object... var2) {
        return (T) applicationContext.getBean(var1,var2);
    }


    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContexts) throws BeansException {
        applicationContext = applicationContexts;

    }

    /**
     * Set the {@code Environment} that this component runs in.
     */
    @Override
    public void setEnvironment(Environment environments) {
        environment = environments;
    }

    /**
     * 获取spring的环境变量
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Environment getEnvironment() {
        return environment;
    }
}
