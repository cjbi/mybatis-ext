package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.example.Sort;

import java.io.Serializable;

/**
 * @author cjbi
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
    private int pageNumber;
    private int pageSize;

    public Page(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, null);
    }

    public Page(int pageNumber, int pageSize, Sort sort) {
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if (pageNumber < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }
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
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Page)) {
            return false;
        }

        Page that = (Page) obj;

        return super.equals(that) && this.equals(that);
    }

    @Override
    public String toString() {
        return String.format("Page request [pageNumber: %d, pageSize %d]", getPageNumber(), getPageSize());
    }
}
