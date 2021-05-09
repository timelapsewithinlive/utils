package utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSON与Java对象相互转换的工具类
 *
 * @author scorpio
 */
public abstract class JsonUtils {


    private static ObjectMapper objectMapper;

    /**
     * 构造函数,使用默认的ObjectMapper实例, 拥有以下特性:<br>
     * 1) 允许字段名不使用引号<br>
     * 2) 允许字段名和字符串使用单引号<br>
     * 3) 允许数字含有前导符0<br>
     * 4) 允许有不存在的属性<br>
     * 5) 支持以下日期格式:<br>
     * "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "MM-dd","HH:mm:ss", "HH:mm"<br/>
     */
    static {
        objectMapper = new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL)
                // 允许字段名不用引号
                .configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                // 允许使用单引号
                .configure(Feature.ALLOW_SINGLE_QUOTES, true)
                // 允许数字含有前导0
                .configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true)
                .configure(Feature.STRICT_DUPLICATE_DETECTION, true)
                // 允许未知的属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 空字符转null
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ;
        //keep Map.Entry as POJO
        objectMapper.configOverride(Map.Entry.class)
                .setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT));
        setDatePatterns(new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "MM-dd HH:mm"});

    }


    /**
     * 获取当前ObjectMapper对象
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtils.objectMapper = objectMapper;
    }

    /**
     * 设置识别的日期格式集合
     *
     * @param datePatterns
     * @return
     */
    private static void setDatePatterns(final String[] datePatterns) {
        objectMapper.setDateFormat(new SimpleDateFormat(datePatterns[0]) {
            @Override
            public Date parse(String source) {
                try {
                    return DateUtils.parseDate(source, datePatterns);
                } catch (Exception e) {
                    throw new IllegalArgumentException("date [" + source + "] should comply with one the formats:" + Arrays.toString(datePatterns), e);
                }
            }
        });
    }


    /**
     * json转T对象
     * <pre>
     *     String json="{\"key\":[1,2,3]}";
     *     TypeReference ref = new TypeReference<Map<String,String[]>>() { };
     *     Map<String,String[]> map=toBean(json,ref)
     * </pre>
     *
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json, TypeReference<T> typeReference) {
        return readValue(json, objectMapper.getTypeFactory().constructType(typeReference));
    }

    /**
     * json转Object对象, 根据json字符串的结构自动调整为对应的数据类型, 具体对应关系如下：<br>
     * 1)字符串->String类型<br>
     * 2)整数->int类型<br>
     * 3)长整数->long类型<br>
     * 4)实数->double类型 <br>
     * 5)键值对->(LinkedHash)Map类型<br>
     * 6)数组->(Array)List类型<br>
     *
     * @param json
     * @return
     */
    @SneakyThrows
    public static Object toObject(String json) {
        return toBean(json, Object.class);
    }


    /**
     * json转T对象
     *
     * @param <T>
     * @param json
     * @param beanType
     * @return
     */
    @SneakyThrows
    public static <T> T toBean(String json, Class<T> beanType) {
        return readValue(json, objectMapper.getTypeFactory().constructType(beanType));
    }

    /**
     * byte数组转T对象
     *
     * @param bytes
     * @param beanType
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> T toBean(byte[] bytes, Class<T> beanType) {
        return objectMapper.readValue(bytes, objectMapper.getTypeFactory().constructType(beanType));
    }

    /**
     * json转T对象数组
     *
     * @param json
     * @param elementType
     * @return
     */
    @SneakyThrows
    public static <T> T[] toArray(String json, Class<T> elementType) {
        return readValue(json, objectMapper.getTypeFactory().constructArrayType(elementType));

    }

    /**
     * json转List&lt;T>对象
     *
     * @param <T>
     * @param json
     * @param elementType
     * @return
     */
    @SneakyThrows
    public static <T> List<T> toList(String json, Class<T> elementType) {
        return readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementType));
    }

    /**
     * json转Set&lt;T>对象
     *
     * @param <T>
     * @param json
     * @param elementType
     * @return
     */
    public static <T> Set<T> toSet(String json, Class<T> elementType) {
        return readValue(json,
            objectMapper.getTypeFactory().constructCollectionType(HashSet.class, elementType));
    }

    /**
     * json转Map&lt;K,V>对象
     *
     * @param json
     * @param keyType
     * @param valueType
     * @return
     */
    @SneakyThrows
    public static <K, V> Map<K, V> toMap(String json, Class<K> keyType, Class<V> valueType) {
        return readValue(json, objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, keyType, valueType));
    }

    /**
     * 对象类型转JSON字符串
     *
     * @param obj
     * @return
     */
    @SneakyThrows
    public static String toJson(Object obj) {
        return obj == null ? null : objectMapper.writeValueAsString(obj);
    }


    /**
     * 对象类型转美化后的JSON字符串
     *
     * @param obj
     * @return
     */
    @SneakyThrows
    public static String toPrettyJson(Object obj) {
        return obj == null ? null : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }

    /**
     * 反序列化
     *
     * @param json
     * @param valueType
     * @param <T>
     * @return
     */
    @SneakyThrows
    private static <T> T readValue(String json, JavaType valueType) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return objectMapper.readValue(json, valueType);
    }
}
