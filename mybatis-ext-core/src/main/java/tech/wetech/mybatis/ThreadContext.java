package tech.wetech.mybatis;

import tech.wetech.mybatis.domain.Page;

/**
 * @author cjbi
 */
@Deprecated
public class ThreadContext {

    private static final ThreadLocal<Page> PAGE = new ThreadLocal<>();

    public static <T extends Page> void setPage(T page) {
        PAGE.set(page);
    }

    public static void setPage(int pageNumber, int pageSize) {
        Page page = new Page(pageNumber, pageSize);
        PAGE.set(page);
    }

    public static void setPage(int pageNumber, int pageSize, boolean countable) {
        Page page = new Page(pageNumber, pageSize, countable);
        PAGE.set(page);
    }

    public static void setPageWithOffset(int offset, int limit) {
        Page page = new Page(offset / limit + 1, limit);
        PAGE.set(page);
    }

    public static void setPageWithOffset(int offset, int limit, boolean count) {
        Page page = new Page(offset / limit + 1, limit, count);
        PAGE.set(page);
    }

    public static <T extends Page> T getPage() {
        return (T) PAGE.get();
    }

    public static void removePage() {
        PAGE.remove();
    }

    public static void removeAll() {
        removePage();
    }
}
