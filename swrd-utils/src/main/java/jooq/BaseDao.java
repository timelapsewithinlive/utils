package jooq;

import com.sm.audit.commons.utils.CollectionUtils;
import com.sm.audit.commons.utils.Generics;
import com.sm.audit.commons.utils.Lambdas;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基于Jooq的基础DAO
 *
 */
@Getter
public class BaseDao<E extends Serializable> {

    private static final Field<Long> ID = DSL.field("id", Long.class);

    @Setter
    protected DSLContext dslContext;

    private Class<E> pojoClass;

    private Table<? extends UpdatableRecord> table;

    @SneakyThrows
    public BaseDao() {
        this.pojoClass = Generics.find(this.getClass(), com.sm.audit.commons.jooq.BaseDao.class, 0);
        Class tableClass = Class.forName(pojoClass.getName().replaceAll("\\.pojos", ""));
        this.table = (Table<? extends UpdatableRecord>)tableClass.newInstance();
    }

    @SneakyThrows
    public BaseDao(Class<? extends UpdatableRecord> clazz) {
        Class tableClass = Class.forName(StringUtils.removeEnd(clazz.getName().replaceAll("\\.records", ""), "Record"));
        this.table = (Table<? extends UpdatableRecord>)tableClass.newInstance();
        this.pojoClass = (Class<E>)Class.forName(tableClass.getName().replaceAll("(^.*?)([^.]*)$", "$1pojos.$2"));
    }

    /**
     * 通过ID来查找单个实体
     *
     * @param id ID列
     * @return
     */
    public E getById(long id) {
        return dslContext.select().from(getTable()).where(idField().eq(id)).fetchOneInto(getPojoClass());
    }

    /**
     * 通过ID来批量查找实体
     *
     * @param ids ID列集合
     * @return
     */
    public List<E> getByIds(Collection<Long> ids) {
        return dslContext.select().from(getTable()).where(idField().in(ids)).fetchInto(getPojoClass());
    }

    /**
     * 获取所有的实体
     *
     * @return
     */
    public List<E> getAll() {
        return dslContext.select().from(getTable()).fetchInto(getPojoClass());
    }

    /**
     * 根据条件查询出ID列
     *
     * @param condition 条件
     * @return
     */
    public List<Long> findIdsByCondition(Condition condition) {
        return findFieldByCondition(condition, idField());
    }

    /**
     * 根据条件查询出一列（去重）
     *
     * @param condition 条件
     * @param field
     * @return
     */
    public <T> List<T> findDistinctFieldByCondition(Condition condition, Field<T> field) {
        return dslContext.selectDistinct(field).from(getTable()).where(condition).fetch(field);
    }

    /**
     * 根据条件查询出一列
     *
     * @param condition 条件
     * @param field
     * @return
     */
    public <T> List<T> findFieldByCondition(Condition condition, Field<T> field) {
        return dslContext.select(field).from(getTable()).where(condition).fetch(field);
    }

    /**
     * 条件查询
     *
     * @param condition 条件
     * @return
     */
    public List<E> findByCondition(Condition condition) {
        return dslContext.select().from(getTable()).where(condition).fetchInto(getPojoClass());
    }

    /**
     * 条件查询
     *
     * @param condition 条件
     * @return
     */
    public List<E> findByCondition(String condition) {
        return dslContext.select().from(getTable()).where(condition).fetchInto(getPojoClass());
    }

    /**
     * 条件查询
     *
     * @param condition 条件
     * @param bindings 条件参数
     * @return
     */
    public List<E> findByCondition(String condition, Object... bindings) {
        return dslContext.select().from(getTable()).where(condition, bindings).fetchInto(getPojoClass());
    }

    /**
     * 条件删除
     *
     * @param condition 条件
     * @return
     */
    public boolean deleteByCondition(Condition condition) {
        return dslContext.delete(getTable()).where(condition).execute() > 0;
    }

    /**
     * 删除
     *
     * @param sql
     * @return
     */
    public boolean delete(String sql, Object... bindings) {
        return dslContext.execute("delete from " + getTable().getName() + " where " + sql, bindings) > 0;
    }

    /**
     * 插入，唯一键冲突则更新
     *
     * @param e 实体
     * @return
     */
    public boolean insert(E e) {
        Record record = createRecord(e);
        int execute = dslContext.insertInto(getTable()).set(record).onDuplicateKeyUpdate().set(record).execute();
        return execute > 0;
    }

    /**
     * 插入，唯一键冲突则忽略
     *
     * @param e 实体
     * @return
     */
    public boolean insertOnDuplicateKeyIgnore(E e) {
        Record record = createRecord(e);
        int execute = dslContext.insertInto(getTable()).set(record).onDuplicateKeyIgnore().execute();
        return execute > 0;
    }

    /**
     * 插入，并返回ID
     *
     * @param e 实体
     * @return
     */
    public Long insertReturnId(E e) {
        Record record = createRecord(e);
        Record r = dslContext.insertInto(getTable()).set(record).returning(ID).fetchOne();
        return r.get(ID);
    }

    /**
     * 批量创建
     *
     * @param entities 实体集合
     * @return
     */
    public boolean insert(Collection<E> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return true;
        }

        List<UpdatableRecord<?>> records = Lambdas.mapToList(entities, entity -> (UpdatableRecord<?>) this.createRecord(entity));
        int[] results = dslContext.batchInsert(records).execute();

        return results != null && results.length == records.size();
    }

    /**
     * 条件更新
     *
     * @param e         实体
     * @param condition 条件
     * @return
     */
    public boolean update(E e, Condition condition) {
        Record record = createRecord(e);

        return dslContext.update(getTable()).set(record).where(condition).execute() > 0;
    }

    /**
     * 实体与Record转换
     *
     * @param e
     * @return
     */
    public <R extends UpdatableRecord<R>> R createRecord(E e) {
        R record = (R) getTable().newRecord();
        record.from(e);
        return record;
    }

    /**
     * 主键
     * @return
     */
    protected Field<Long> idField() {
        return ID;
    }
}
