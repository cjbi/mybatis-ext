package tech.wetech.mybatis.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author cjbi
 */
public class Page<E> extends ArrayList<E> {

    private int pageNumber;
    private int pageSize;
    private boolean countable;
    private int total;

    public Page(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, false);
    }

    public Page(int pageNumber, int pageSize, boolean countable) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.countable = countable;
    }

    public Page() {
    }

    public Page(Collection<? extends E> c, int total) {
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

}
