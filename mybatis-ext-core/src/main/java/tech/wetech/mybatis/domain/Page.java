package tech.wetech.mybatis.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * <p>对请求进行分页</p>
 * <b>使用示例:</b>
 * <pre>
 * // 用法一：Mapper拦截方式
 * // 查询分页，支持任何列表查询方法拦截
 * Page page = Page.of(1, 10).list(()-&gt; userMapper.selectAll());
 * // 总数
 * int total = page.getTotal();
 * Assert.assertEquals(page.size(),10);
 *
 * // 用法二：作为入参方式
 * public interface UserMapper {
 *     List&lt;User&gt; selectByNameLike(String nameLike,Page page);
 * }
 * // 使用Page
 * Page page = new Page();
 * // 第几页
 * page.setPageNum(1);
 * // 分页数
 * page.setPageSize(10);
 * // 开启总数查询，默认为true
 * page.setCountable(false);
 * List list = userMapper.selectByNameLike("zh",page);
 * Assert.assertEquals(list.size(), 10);
 * </pre>
 *
 * @param <E> 实体类
 * @author cjbi
 */
public class Page<E> extends ArrayList<E> {
    /**
     * 第几页
     */
    private int pageNumber;
    /**
     * 分页数
     */
    private int pageSize;
    /**
     * 开启总数查询，默认为true
     */
    private boolean countable;
    /**
     * 总数
     */
    private long total;
    /**
     * 分页的ThreadLocal
     */
    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    public Page(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, true);
    }

    public Page(int pageNumber, int pageSize, boolean countable) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.countable = countable;
    }

    public static Page of(int pageNumber, int pageSize) {
        return new Page(pageNumber, pageSize, true);
    }

    public static Page of(int pageNumber, int pageSize, boolean countable) {
        return new Page(pageNumber, pageSize, countable);
    }

    public Page<E> list(Supplier<? extends Page<E>> supplier) {
        PAGE.set(this);
        return supplier.get();
    }

    public Page() {
    }

    public Page(Collection<? extends E> c, long total) {
        super(c);
        this.total = total;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getOffset() {
        return ((this.pageNumber - 1) * this.pageSize);
    }

    public boolean isCountable() {
        return countable;
    }

    public void setCountable(boolean countable) {
        this.countable = countable;
    }

    public boolean isFirst() {
        return !hasPrevious();
    }

    public boolean isLast() {
        return !hasNext();
    }

    public boolean hasNext() {
        return this.pageNumber + 1 < total;
    }

    public boolean hasPrevious() {
        return this.pageNumber > 0;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", countable=" + countable +
                ", total=" + total +
                ", list=" + super.toString() +
                '}';
    }

    public static Page getThreadPage() {
        return PAGE.get();
    }

    public void remove() {
        PAGE.remove();
    }
}
