package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.example.Sort;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页list
 * @author cjbi
 */
public class PageList<T> extends ArrayList<T> implements Page {

    private static final long serialVersionUID = 1L;

    private long total;

    private Page page;

    public PageList(Page page) {
        this.page = page;
    }

    public PageList(Collection<? extends T> c) {
        super(c);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public int getPageNumber() {
        return page.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return page.getPageSize();
    }

    @Override
    public long getOffset() {
        return page.getOffset();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }
}
