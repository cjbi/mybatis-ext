package tech.wetech.mybatis;

import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.example.Sort;

/**
 * @author cjbi
 */
public class ThreadContext {

    private static final ThreadLocal<Page> PAGE_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<Sort> SORT_THREAD_LOCAL = new ThreadLocal<>();

    public static void doSort(Sort sort) {
        SORT_THREAD_LOCAL.set(sort);
    }

    public static void doPage(Page page) {
        PAGE_THREAD_LOCAL.set(page);
    }

    public static void doPage(int pageNumber, int pageSize) {
        Page page = new Page(pageNumber, pageSize);
        PAGE_THREAD_LOCAL.set(page);
    }

    public static void doPage(int pageNumber, int pageSize, Sort sort) {
        Page page = new Page(pageNumber, pageSize);
        PAGE_THREAD_LOCAL.set(page);
        SORT_THREAD_LOCAL.set(sort);
    }

    public static void doPageWithOffset(int offset, int limit) {
        Page page = new Page(offset / limit + 1, limit);
        PAGE_THREAD_LOCAL.set(page);
    }

    public static void doPageWithOffset(int offset, int limit, Sort sort) {
        Page page = new Page(offset / limit + 1, limit);
        PAGE_THREAD_LOCAL.set(page);
        SORT_THREAD_LOCAL.set(sort);
    }

    public static void setPage(Page page) {
        PAGE_THREAD_LOCAL.set(page);
    }

    public static Page getPage() {
        return PAGE_THREAD_LOCAL.get();
    }

    public static void setSort(Sort sort) {
        SORT_THREAD_LOCAL.set(sort);
    }

    public static Sort getSort() {
        return SORT_THREAD_LOCAL.get();
    }

    public static void removePage() {
        PAGE_THREAD_LOCAL.remove();
    }

    public static void removeSort() {
        SORT_THREAD_LOCAL.remove();
    }

}
