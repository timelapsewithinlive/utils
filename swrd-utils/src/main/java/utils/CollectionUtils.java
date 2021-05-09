package utils;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 集合工具类,提供对集合各种扩展操作的支持
 *
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    /**
     * {@link Iterable}元素处理类
     *
     * @author skyfalling
     */
    public interface IterableHandler<E, C extends Iterable<E>> {
        void handle(C iterable);
    }


    /**
     * {@link Collection}元素处理类
     *
     * @author skyfalling
     */
    public interface CollectionHandler<E> extends IterableHandler<E, Collection<E>> {
        void handle(Collection<E> collection);
    }

    /**
     * {@link List}元素处理类
     *
     * @author skyfalling
     */
    public interface ListHandler<E> extends IterableHandler<E, List<E>> {
        void handle(List<E> list);
    }


    /**
     * 分批处理集合元素
     *
     * @param collection 集合
     * @param limit      每次处理元素的最大限制
     * @param handler    集合处理类
     * @deprecated replaced by {@link #doBatch(Iterable, int, ListHandler)}
     */
    @Deprecated
    public static <E> void batchHandle(Collection<E> collection, int limit, final CollectionHandler<E> handler) {
        doBatch(collection, limit, list -> handler.handle(list));
    }


    /**
     * 分批处理{@link Iterable}元素
     *
     * @param iterable 可遍历集合
     * @param limit    批量处理元素数量限制
     * @param handler  元素处理对象
     */
    public static <E> void doBatch(Iterable<E> iterable, int limit, ListHandler<E> handler) {
        List<E> subList = new ArrayList<>(limit);
        for (E e : iterable) {
            subList.add(e);
            if (subList.size() == limit) {
                handler.handle(subList);
                //NOTE:这里需要重新声明变量,避免回调函数持有引用
                subList = new ArrayList<>(limit);
            }
        }
        if (subList.size() > 0) {
            handler.handle(subList);
        }
    }

    /**
     * 分批处理{@link Iterator}元素
     *
     * @param iterator 迭代器
     * @param limit    批量处理元素数量限制
     * @param handler  元素处理对象
     */
    public static <E> void doBatch(Iterator<E> iterator, int limit, ListHandler<E> handler) {
        List<E> subList = new ArrayList<>(limit);
        while (iterator.hasNext()) {
            E e = iterator.next();
            subList.add(e);
            if (subList.size() == limit) {
                handler.handle(subList);
                //NOTE:这里需要重新声明变量,避免回调函数持有引用
                subList = new ArrayList<>(limit);
            }
        }
        if (subList.size() > 0) {
            handler.handle(subList);
        }
    }

    /**
     * 将集合中元素按照指定大小分组
     *
     * @param iterable
     * @param size     分组大小
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> grouped(Iterable<T> iterable, int size) {
        List<List<T>> groups = new ArrayList<>();
        doBatch(iterable, size, list -> groups.add(list));
        return groups;
    }


    /**
     * 将Iterator对象转化成Enumeration对象
     *
     * @param iterator Iterator对象实例
     * @return Enumeration对象实例
     */
    public static <T> Enumeration<T> enumeration(final Iterator<T> iterator) {

        return new Enumeration<T>() {

            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public T nextElement() {
                return iterator.next();
            }

        };

    }

    /**
     * 将Enumeration对象转化成Iterator对象
     *
     * @param enumeration Enumeration对象实例
     * @return Iterator对象实例
     */
    public static <T> Iterator<T> iterator(Enumeration<T> enumeration) {
        List<T> list = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list.iterator();
    }

    /**
     * 集合转数组
     */
    public static <T> T[] array(Collection<T> list, Class<T> clazz) {
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        int i = 0;
        for (T t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static boolean[] booleanArray(Collection<Boolean> list) {
        boolean[] array = new boolean[list.size()];
        int i = 0;
        for (boolean t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static byte[] byteArray(Collection<Byte> list) {
        byte[] array = new byte[list.size()];
        int i = 0;
        for (byte t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static char[] charArray(Collection<Character> list) {
        char[] array = new char[list.size()];
        int i = 0;
        for (char t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static short[] shortArray(Collection<Short> list) {
        short[] array = new short[list.size()];
        int i = 0;
        for (short t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static int[] intArray(Collection<Integer> list) {
        int[] array = new int[list.size()];
        int i = 0;
        for (int t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static long[] longArray(Collection<Long> list) {
        long[] array = new long[list.size()];
        int i = 0;
        for (long t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static float[] floatArray(Collection<Float> list) {
        float[] array = new float[list.size()];
        int i = 0;
        for (float t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     */
    public static double[] doubleArray(Collection<Double> list) {
        double[] array = new double[list.size()];
        int i = 0;
        for (double t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 删除链表从from到to(不包括)索引位置的元素
     */
    public static <T> void remove(List<T> list, int from, int to) {
        list.subList(from, to).clear();
    }

    /**
     * 删除集合中指定元素
     */
    public static <T> void remove(Collection<T> list, Object... items) {
        list.removeAll(Arrays.asList(items));
    }

    /**
     * 删除集合中null元素
     */
    public static <T> void removeNull(Collection<T> list) {
        list.removeAll(Collections.singleton(null));
    }

    /**
     * 删除Map中value为null的键值对
     */
    public static <K, V> void removeNull(Map<K, V> map) {
        Iterator<V> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                iterator.remove();
            }
        }
    }


    /**
     * 数组转链表
     *
     * @return List泛型实例
     */
    public static <T> List<T> list(T[] array) {
        List<T> list = new ArrayList<>(array.length);
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    /**
     * 可枚举对象转链表
     *
     * @return List泛型实例
     */
    public static <T> List<T> list(Iterable<T> iterable) {
        if (iterable instanceof List) {
            return (List<T>) iterable;
        }
        if (iterable instanceof Collection) {
            return new ArrayList<>((Collection) iterable);
        }
        List<T> list = new ArrayList<>();
        for (T t : iterable) {
            list.add(t);
        }
        return list;
    }


    /**
     * 取元素的某个属性形成新的链表
     *
     * @param propertyName 属性名
     * @param propertyType 属性类型
     * @param <T>          属性类型的泛型约束
     * @return 属性列表
     */
    public static <T, V> List<T> list(Iterable<V> iterable, String propertyName, Class<T> propertyType) {

        List<T> list;
        if (iterable instanceof Collection) {
            list = new ArrayList<>(((Collection) iterable).size());
        } else {
            list = new ArrayList<>();
        }
        for (V o : iterable) {
            list.add((T) getProperty(o, propertyName));
        }
        return list;
    }


    /**
     * 取元素的某个属性形成新的链表
     *
     * @param func 生成元素的函数
     * @param <T>  属性类型的泛型约束
     * @return 属性列表
     */
    public static <T, V> List<T> list(Iterable<V> iterable, Function<V, T> func) {

        List<T> list;
        if (iterable instanceof Collection) {
            list = new ArrayList<>(((Collection) iterable).size());
        } else {
            list = new ArrayList<>();
        }
        for (V o : iterable) {
            list.add(func.apply(o));
        }
        return list;
    }

    /**
     * 可枚举对象转链表
     *
     * @return List泛型实例
     */
    public static <T> List<T> list(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 集合转Map,属性keyProperty作为Map的key值,属性valueProperty作为Map的value值
     *
     * @param keyProperty   作为key的属性名
     * @param valueProperty 作为value的属性名
     * @param keyType       作为key的属性类型
     * @param valueType     作为value的属性类型
     * @param <K>           key的泛型约束
     * @param <V>           value的泛型约束
     * @param <T>           元素的泛型约束
     */
    public static <K, V, T> Map<K, V> map(Iterable<T> iterable, String keyProperty, String valueProperty,
                                          Class<K> keyType, Class<V> valueType) {
        Map<K, V> map;
        if (iterable instanceof Collection) {
            map = new HashMap<>(((Collection) iterable).size());
        } else {
            map = new HashMap<>();
        }
        for (T obj : iterable) {
            Object keyObj = getProperty(obj, keyProperty);
            Object valueObj = getProperty(obj, valueProperty);
            map.put((K) keyObj, (V) valueObj);
        }
        return map;
    }

    /**
     * 集合转Map,属性keyProperty作为Map的key值,元素本身作为Map的value值
     *
     * @param keyProperty 作为key的属性名
     * @param keyType     作为key的属性类型
     * @param <K>         key的泛型约束
     * @param <V>         value的泛型约束
     */
    public static <K, V> Map<K, V> map(Iterable<V> iterable, String keyProperty, Class<K> keyType) {
        Map<K, V> map;
        if (iterable instanceof Collection) {
            map = new HashMap<>(((Collection) iterable).size());
        } else {
            map = new HashMap<>();
        }
        for (V obj : iterable) {
            Object keyObj = getProperty(obj, keyProperty);
            map.put((K) keyObj, obj);
        }
        return map;
    }

    /**
     * 集合转Map,属性keyProperty作为Map的key值,元素本身作为Map的value值
     *
     * @param keyGen 生成key的函数
     * @param <K>    key的泛型约束
     * @param <V>    value的泛型约束
     */
    public static <K, V> Map<K, V> map(Iterable<V> iterable, Function<V, K> keyGen) {
        return map(iterable, keyGen, (e) -> e);
    }

    /**
     * 集合转Map,属性keyProperty作为Map的key值,属性valueProperty作为Map的value值
     *
     * @param keyGen   生成Key的函数
     * @param valueGen 作为Value的函数
     * @param <K>      key的泛型约束
     * @param <V>      value的泛型约束
     * @param <T>      元素的泛型约束
     */
    public static <K, V, T> Map<K, V> map(Iterable<T> iterable, Function<T, K> keyGen, Function<T, V> valueGen) {
        Map<K, V> map;
        if (iterable instanceof Collection) {
            map = new HashMap<>(((Collection) iterable).size());
        } else {
            map = new HashMap<>();
        }
        for (T obj : iterable) {
            map.put(keyGen.apply(obj), valueGen.apply(obj));
        }
        return map;
    }

    /**
     * 将集合中元素按照指定属性分组
     *
     * @param keyProperty 作为key的属性名
     * @param keyType     作为key的属性类型
     * @param <K>         key的泛型约束
     * @param <V>         value的泛型约束
     */
    public static <K, V> Map<K, List<V>> groupBy(Iterable<V> iterable, String keyProperty, Class<K> keyType) {
        Map<K, List<V>> map;
        if (iterable instanceof Collection) {
            map = new HashMap<>(((Collection) iterable).size());
        } else {
            map = new HashMap<>();
        }
        for (V obj : iterable) {
            K keyObj = (K) getProperty(obj, keyProperty);
            map.computeIfAbsent(keyObj, (K k) -> new ArrayList<>()).add(obj);
        }
        return map;
    }

    /**
     * 将集合中元素按照指定属性分组
     *
     * @param keyGen 生成key的函数
     * @param <K>    key的泛型约束
     * @param <V>    value的泛型约束
     */
    public static <K, V> Map<K, List<V>> groupBy(Iterable<V> iterable, Function<V, K> keyGen) {
        Map<K, List<V>> map;
        if (iterable instanceof Collection) {
            map = new HashMap<>(((Collection) iterable).size());
        } else {
            map = new HashMap<>();
        }
        for (V obj : iterable) {
            K keyObj = keyGen.apply(obj);
            map.computeIfAbsent(keyObj, (K k) -> new ArrayList<>()).add(obj);
        }
        return map;
    }

    /**
     * 将集合中元素按照指定属性分组
     *
     * @param iterable 待处理集合
     * @param keyGen   生成key的函数
     * @param <K>      key的泛型约束
     * @param <V>      value的泛型约束
     */
    public static <K, V, T> Map<K, List<V>> groupBy(Iterable<T> iterable,
                                                    Function<T, K> keyGen, Function<T, V> valueGen) {
        Map<K, List<V>> map;
        if (iterable instanceof Collection) {
            map = new HashMap<>(((Collection) iterable).size());
        } else {
            map = new HashMap<>();
        }
        for (T obj : iterable) {
            K keyObj = keyGen.apply(obj);
            V valueObj = valueGen.apply(obj);
            map.computeIfAbsent(keyObj, (K k) -> new ArrayList<>()).add(valueObj);
        }
        return map;
    }


    /**
     * 过滤集合中的元素，根据predicate条件过滤
     *
     * @see com.sm.audit.commons.utils.CollectionUtils.FieldSelector
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        List<T> list = new ArrayList<>(collection.size());
        for (T v : collection) {
            if (predicate.test(v)) {
                list.add(v);
            }
        }
        return list;
    }

    /**
     * 根据propertyName和values过滤集合
     *
     * @see #filter(Collection, Predicate)
     */
    public static <T, V> List<T> filter(Collection<T> collection, String propertyName, V... values) {
        return filter(collection, new FieldSelector<>(propertyName, values));
    }

    /**
     * 根据propertyName和values过滤集合
     *
     * @see #filter(Collection, Predicate)
     */
    public static <T, V> List<T> filter(Collection<T> collection, String propertyName, Iterable<V> values) {
        return filter(collection, new FieldSelector<>(propertyName, values));
    }

    /**
     * 根据单个字段，筛选元素，返回一个新Collection
     */
    public static class FieldSelector<T, V> implements Predicate<T> {
        /**
         * 需要筛选的字段名
         */
        private String fieldName;
        /**
         * 要筛选的字段值集合
         */
        private Set<V> values = new HashSet<>();

        public FieldSelector(String fieldName, V... values) {
            this(fieldName, Arrays.asList(values));
        }

        public FieldSelector(String fieldName, Iterable<V> values) {
            this.fieldName = fieldName;
            for (V value : values) {
                this.values.add(value);
            }
        }

        @Override
        public boolean test(T t) {
            return values.contains(getProperty(t, fieldName));
        }
    }

    /**
     * 获取对象属性
     */
    private static Object getProperty(Object obj, String property) {
        return obj instanceof Map ? ((Map) obj).get(property) : ReflectionUtils.getProperty(obj, property);
    }

}
