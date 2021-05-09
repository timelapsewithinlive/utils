/**
 * com.sm.zilong.common.page.PageParam.java
 * created by Tianxin(tianjige@163.com) on 2015年9月7日 下午3:29:01
 */
package page;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * 分页的参数
 */
@Data
@NoArgsConstructor
public class PageParam {

    /**
     * 单页最大数
     */
    public static final int MAX_PAGE_NO = 100000;
    /**
     *
     */
    public static final int MAX_PAGE_SIZE = 10000;
    /**
     * 每页多少条
     */
    private int pageSize = 20;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 正序还是倒序
     */
    private boolean asc = false;

    public void setPageSize(int pageSize) {
        if (pageSize > 0 && pageSize < MAX_PAGE_SIZE) {
            this.pageSize = pageSize;
        }
    }

    public void setPageSizeNoLimit(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public void setPageNo(int pageNo) {
        if (pageNo > 0 && pageNo < MAX_PAGE_NO) {
            this.pageNo = pageNo;
        }
    }

    /**
     * 获取当前页第一条记录索引位置
     *
     * @return
     */
    public int getFirstResult() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 根据分页参数获取当前页数据
     *
     * @param totalList 全量数据
     * @param <T>
     * @return
     */
    public <T> List<T> getSubList(List<T> totalList) {
        if (this.pageSize == -1 || CollectionUtils.isEmpty(totalList)) {
            return totalList;
        }
        if (this.getFirstResult() > Math.min(totalList.size(), this.getFirstResult() + this.pageSize)) {
            return Collections.emptyList();
        }
        return totalList.subList(this.getFirstResult(), Math.min(totalList.size(),
                this.getFirstResult() + this.pageSize));
    }
}
