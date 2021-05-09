package jooq;

import org.apache.commons.collections.CollectionUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.Collection;

/**
 * jooq的帮助类
 *
 */
public class JooqUtil {

    public static Condition and(Collection<? extends Condition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return DSL.trueCondition();
        }
        return DSL.and(conditions);
    }

    public static <T> Field<Integer> findInSet(T element, Field<T> field) {
        return DSL.field("FIND_IN_SET({0}, {1})", Integer.class, element, field);
    }

    /**
     * VALUES()函数只在INSERT...UPDATE语句中有意义，其它时候会返回NULL
     *
     * @param field
     * @param <T>
     * @return
     */
    public static <T> Field<T> values(Field<T> field) {
        return DSL.field("VALUES({0})", field.getDataType(), field);
    }

}
