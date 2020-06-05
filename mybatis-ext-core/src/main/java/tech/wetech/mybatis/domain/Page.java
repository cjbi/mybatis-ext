package tech.wetech.mybatis.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author cjbi
 */
public class Page<E> extends ArrayList<E> {

    private int pageNumber;
    private int pageSize;
    private boolean countable;
    private long total;

    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    public Page(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, false);
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
