package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.example.Sort;

import java.io.Serializable;

/**
 * @author cjbi
 */
public class PageRequest implements Page, Serializable {

    private static final long serialVersionUID = 1L;
    private final int page;
    private final int size;
    private final Sort sort;

    public PageRequest(int page, int size, Sort sort) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if (page < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.page = page;
            this.size = size;
            this.sort = sort;
        }
    }

    @Override
    public int getPageNumber() {
        return this.page;
    }

    @Override
    public int getPageSize() {
        return this.size;
    }

    @Override
    public long getOffset() {
        return (this.page * this.size);
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public boolean isFirst() {
        return false;
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasNext() {
        //TODO
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return this.page > 0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageRequest)) {
            return false;
        }

        PageRequest that = (PageRequest) obj;

        return super.equals(that) && this.sort.equals(that.sort);
    }

    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(), sort);
    }

}
