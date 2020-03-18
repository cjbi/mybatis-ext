package tech.wetech.mybatis.wrapper;

import tech.wetech.mybatis.domain.Page;

import java.util.Collection;

public class PageWrapper<T> extends Page<T> {

    public PageWrapper() {
    }

    public PageWrapper(int pageNumber, int pageSize) {
        super(pageNumber, pageSize, true);
    }

    public PageWrapper(int pageNumber, int pageSize, boolean countable) {
        super(pageNumber, pageSize, countable);
    }

    public PageWrapper(Collection c, int total) {
        super(c, total);
    }
}
