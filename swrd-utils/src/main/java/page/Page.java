package page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 分页查询对象接口定义
 */

/**
 * 分页查询对象接口定义
 *
 * @author skyfalling
 */

public class Page<T> implements Serializable {
  private static final long serialVersionUID = 1L;
  protected int pageNo = 1;
  protected int pageSize = 20;
  protected long totalCount = 0;
  protected boolean autoCount = false;
  private Map<String, Boolean> sort = Collections.EMPTY_MAP;
  private boolean asc = true;
  protected List<T> result = Collections.emptyList();

  public Page() {
  }

  /**
   * 构造方法
   */
  public Page(int pageSize, int pageNo) {
    this(pageSize, pageNo, 0L);
  }

  /**
   * 构造方法
   */
  public Page(int pageSize, int pageNo, long totalCount) {
    setPageSize(pageSize);
    setPageNo(pageNo);
    setTotalCount(totalCount);
  }

  /**
   * 获得当前页的页号
   */

  public int getPageNo() {
    return pageNo;
  }

  /**
   * 设置当前页的页号,只能设置正数
   */

  public void setPageNo(int pageNo) {
    if (pageNo > 0) {
      this.pageNo = pageNo;
    }
  }

  /**
   * 获得每页的记录数量.
   */

  public int getPageSize() {
    return pageSize;
  }

  /**
   * 设置每页的记录数量
   */

  public void setPageSize(int pageSize) {
    if (pageSize > 0) {
      this.pageSize = pageSize;
    }
  }

  /**
   * 获取当前页的数据
   */

  public List<T> getResult() {
    return result;
  }

  /**
   * 设置当前页的数据
   */

  public void setResult(List<T> result) {
    this.result = result;
  }

  /**
   * 取得总记录数
   */

  public long getTotalCount() {
    return totalCount;
  }

  /**
   * 设置总记录数
   */

  public void setTotalCount(long totalCount) {
    if (totalCount > 0) {
      this.totalCount = totalCount;
    }
  }


  /**
   * 是否自动计算总数
   */

  public boolean isAutoCount() {
    return autoCount;
  }

  /**
   * 设置自动计算总数
   */

  public void setAutoCount(boolean autoCount) {
    this.autoCount = autoCount;
  }

  /**
   * 排序字段,逗号分割:如 a asc,b desc
   *
   * @return
   */
  public Map<String, Boolean> getSort() {
    return this.sort;
  }

  /**
   * 排序字段
   *
   * @param sort
   */
  public void setSort(Map<String, Boolean> sort) {
    if (sort != null) {
      this.sort = sort;
    }
  }

  /**
   * 是否升序
   *
   * @return
   */
  public boolean isAsc() {
    return asc;
  }

  /**
   * 是否升序
   *
   * @param asc
   */
  public void setAsc(boolean asc) {
    this.asc = asc;
  }

  /**
   * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置<br/> 起始位置为1
   */
  public int getFirst() {
    return getFirst(false);
  }

  /**
   * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置
   *
   * @param fromZero 是否从零开始
   * @return
   */
  public int getFirst(boolean fromZero) {
    return (pageNo - 1) * pageSize + (fromZero ? 0 : 1);
  }

  /**
   * 根据pageSize与totalCount计算总页数
   */

  public int getTotalPages() {
    int count = (int) (totalCount / pageSize);
    if (totalCount % pageSize > 0) {
      count++;
    }
    return count;
  }

  /**
   * 是否有下一页.
   */

  public boolean isHasNext() {
    return pageNo < getTotalPages();
  }

  /**
   * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
   */

  public int getNextPage() {
    return isHasNext() ? pageNo + 1 : pageNo;
  }

  /**
   * 是否有上一页.
   */

  public boolean isHasPre() {
    return (pageNo - 1 >= 1);
  }

  /**
   * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
   */

  public int getPrePage() {
    return isHasPre() ? pageNo - 1 : pageNo;
  }


  /**
   * 获取分页数据
   */
  public <E> List<E> pageList(List<E> list) {
    if (getFirst() > list.size()) {
      return Collections.EMPTY_LIST;
    }
    return list.subList(getFirst(), Math.min(list.size(),
            getFirst() + pageSize));
  }

}
