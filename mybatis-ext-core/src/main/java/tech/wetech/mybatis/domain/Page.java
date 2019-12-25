package tech.wetech.mybatis.domain;

import java.io.Serializable;

/**
 * @author cjbi
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
    private int pageNumber;
    private int pageSize;
    private boolean count;

    public Page(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, true);
    }

    public Page(int pageNumber, int pageSize, boolean count) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if (pageNumber < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
        this.count = count;
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

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", count=" + count +
                '}';
    }
}
