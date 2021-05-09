package utils;

import jodd.bean.BeanCopy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 对象属性拷贝工具类
 *
 */
public class BeanCopier extends BeanCopy {

    private Map<String, Set<Object>> defaultFieldValues = new HashMap<>();

    private Map<Class, Set<Object>> defaultTypeValues = new HashMap<>();


    private Set<Class> includeTypes = new HashSet<>();
    private Set<Class> excludeTypes = new HashSet<>();

    public BeanCopier(Object source, Object destination) {
        super(source, destination);
        this.ignoreNulls(true);
    }

    /**
     * 忽略特定值的属性
     *
     * @param name
     * @param values
     * @return
     */
    public BeanCopier ignore(String name, Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        if (!this.defaultFieldValues.containsKey(name)) {
            this.defaultFieldValues.put(name, new HashSet<>());
        }
        this.defaultFieldValues.get(name).addAll(Arrays.asList(values));
        return this;
    }

    /**
     * 忽略特定值的类型
     *
     * @param type
     * @param values
     * @return
     */
    public BeanCopier ignore(Class type, Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        if (!this.defaultTypeValues.containsKey(type)) {
            this.defaultTypeValues.put(type, new HashSet<>());
        }
        this.defaultTypeValues.get(type).addAll(Arrays.asList(values));
        return this;
    }

    /**
     * 指定需要拷贝的字段类型
     *
     * @param types
     * @return
     */
    public BeanCopier include(Class... types) {
        this.includeTypes.addAll(Arrays.asList(types));
        return this;
    }

    /**
     * 指定不需要拷贝的字段类型
     *
     * @param types
     * @return
     */
    public BeanCopier exclude(Class... types) {
        this.excludeTypes.addAll(Arrays.asList(types));
        return this;
    }


    @Override
    protected boolean visitProperty(String name, Object value) {
        if (shouldIgnore(name, value)) {
            return false;
        }
        return super.visitProperty(name, value);
    }

    /**
     * 是否忽略该字段
     *
     * @param name
     * @param value
     * @return
     */
    protected boolean shouldIgnore(String name, Object value) {
        assert value != null;
        //如果包含类型不为空,则不包含被忽略
        if (!includeTypes.isEmpty()) {
            boolean ignore = true;
            for (Class type : includeTypes) {
                if (type.isInstance(value)) {
                    ignore = false;
                    break;
                }
            }
            if (ignore) {
                return true;
            }
        }

        // 排除类型
        for (Class type : excludeTypes) {
            if (type.isInstance(value)) {
                return true;
            }
        }

        Set<Object> set = defaultFieldValues.get(name);
        if (set != null) {
            if (set.contains(value)) {
                return true;
            }
        }
        for (Entry<Class, Set<Object>> entry : defaultTypeValues.entrySet()) {
            Class type = entry.getKey();
            if (type.isInstance(value)) {
                for (Object ignore : entry.getValue()) {
                    if (value.equals(ignore)
                            || (type == Number.class &&
                            ((Number) ignore).doubleValue()
                                    == ((Number) value).doubleValue())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * fix NPE
     */
    @Override
    public void copy() {
        if (source == null || destination == null) {
            return;
        }
        super.copy();
    }
}
