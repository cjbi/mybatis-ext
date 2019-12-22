package tech.wetech.mybatis.domain;

import tech.wetech.mybatis.example.Sort;

public interface Page {

    int getPageNumber();

    int getPageSize();

    long getOffset();

    Sort getSort();

    boolean isFirst();

    boolean isLast();

    boolean hasNext();

    boolean hasPrevious();
}
