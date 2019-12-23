package tech.wetech.mybatis.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页list
 *
 * @author cjbi
 */
public class PageList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 1L;

    private int total;
    private int pageNumber;
    private int pageSize;

    public PageList(Collection<? extends T> c, Page page, int total) {
        super(c);
        this.pageNumber = page.getPageNumber();
        this.pageSize = page.getPageSize();
        this.total = total;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public long getOffset() {
        return (this.pageNumber * this.pageSize);
    }

    public boolean isFirst() {
        return false;
    }

    public boolean isLast() {
        return !hasNext();
    }

    public boolean hasNext() {
        //TODO
        return false;
    }

    public boolean hasPrevious() {
        return this.pageNumber > 0;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "total=" + total +
                ", page=" + pageNumber +
                ", size=" + pageSize +
                ", rows=" + super.toString() +
                '}';
    }
}
