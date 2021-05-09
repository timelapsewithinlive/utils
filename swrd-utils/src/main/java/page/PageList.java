/**
 * com.sm.zilong.common.page.PageList.java
 * created by Tianxin(tianjige@163.com) on 2015年9月7日 下午3:22:58
 */
package page;

import com.google.common.collect.Maps;
import com.sm.audit.commons.utils.CollectionUtils;
import com.sm.audit.commons.utils.Lambdas;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 返回给前端的分页列表
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageList<T> implements Serializable {
    private static final long serialVersionUID = -6111478329477575230L;

    /**
     * 当前页码
     */
    private int pageNo;

    /**
     * 每页多少条记录
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 当页的数据
     */
    private List<T> list;

    /**
     * 额外附加数据
     */
    private Map<Object, Object> extra;

    public PageList(int pageNo, int pageSize, int totalCount, List<T> list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
    }

    public PageList(PageParam pageParam, int totalCount, List<T> list) {
        this.pageNo = pageParam.getPageNo();
        this.pageSize = pageParam.getPageSize();
        this.totalCount = totalCount;
        this.list = list;
    }

    /**
     * 总页数
     *
     * @return
     */
    public int getTotalPageCount() {
        return (totalCount + pageSize - 1) / pageSize;
    }

    public void addExtra(Object key, Object value) {
        if (extra == null) {
            extra = Maps.newHashMap();
        }

        extra.put(key, value);
    }

    public <E> PageList<E> convertListData(Function<List<T>, List<E>> converter) {
        return new PageList<>(pageNo, pageSize, totalCount, converter.apply(list));
    }

    public <E> PageList<E> convertListType(Function<T, E> converter) {
        return new PageList<>(pageNo, pageSize, totalCount, Lambdas.mapToList(list, converter));
    }

    public static <T> PageList<T> emptyPageList(PageParam pageParam) {
        return PageList.<T>builder()
            .pageNo(pageParam.getPageNo()).pageSize(pageParam.getPageSize())
            .totalCount(0).list(Collections.emptyList()).build();
    }

    public static <T> PageList<T> memoryPageList(List<T> list, PageParam pageParam) {
        if (CollectionUtils.isEmpty(list)) {
            return emptyPageList(pageParam);
        }

        return PageList.<T>builder()
                .pageNo(pageParam.getPageNo()).pageSize(pageParam.getPageSize())
                .totalCount(list.size()).list(pageParam.getSubList(list)).build();
    }

    public static class PageListBuilder<T> {
        /**
         * 设置分页参数
         *
         * @param param
         * @return
         */
        public PageListBuilder<T> page(PageParam param) {
            return this.pageNo(param.getPageNo())
                    .pageSize(param.getPageSize())
                    ;
        }

        public static <T> PageListBuilder<T> builder() {
            return new PageListBuilder<T>();
        }
    }

}
