package tech.wetech.mybatis.domain;

import org.apache.ibatis.session.RowBounds;
import tech.wetech.mybatis.example.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageBounds extends RowBounds implements Serializable {
    private static final long serialVersionUID = -6414350656252331011L;
    public final static int NO_PAGE = 1;
    /** 页号 */
    protected int page = NO_PAGE;
    /** 分页大小 */
    protected int limit = NO_ROW_LIMIT;
    /** 分页排序信息 */
    protected List<Sort.Order> orders = new ArrayList<Sort.Order>();
    /** 结果集是否包含TotalCount */
    protected boolean containsTotalCount;

    protected Boolean asyncTotalCount;

    public PageBounds(){
        containsTotalCount = false;
    }

    public PageBounds(RowBounds rowBounds) {
        if(rowBounds instanceof PageBounds){
            PageBounds pageBounds = (PageBounds)rowBounds;
            this.page = pageBounds.page;
            this.limit = pageBounds.limit;
            this.orders = pageBounds.orders;
            this.containsTotalCount = pageBounds.containsTotalCount;
            this.asyncTotalCount = pageBounds.asyncTotalCount;
        }else{
            this.page = (rowBounds.getOffset()/rowBounds.getLimit())+1;
            this.limit = rowBounds.getLimit();
        }

    }

    /**
     * Query TOP N, default containsTotalCount = false
     * @param limit
     */
    public PageBounds(int limit) {
        this.limit = limit;
        this.containsTotalCount = false;
    }

    public PageBounds(int page, int limit) {
        this(page, limit, new ArrayList<Sort.Order>(), true);
    }

    public PageBounds(int page, int limit, boolean containsTotalCount) {
        this(page, limit, new ArrayList<Sort.Order>(), containsTotalCount);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * @param orders
     */
    public PageBounds(List<Sort.Order> orders) {
        this(NO_PAGE, NO_ROW_LIMIT,orders ,false);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * @param order
     */
    public PageBounds(Sort.Order... order) {
        this(NO_PAGE, NO_ROW_LIMIT,order);
        this.containsTotalCount = false;
    }

    public PageBounds(int page, int limit, Sort.Order... order) {
        this(page, limit, Arrays.asList(order), true);
    }

    public PageBounds(int page, int limit, List<Sort.Order> orders) {
        this(page, limit, orders, true);
    }

    public PageBounds(int page, int limit, List<Sort.Order> orders, boolean containsTotalCount) {
        this.page = page;
        this.limit = limit;
        this.orders = orders;
        this.containsTotalCount = containsTotalCount;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isContainsTotalCount() {
        return containsTotalCount;
    }

    public void setContainsTotalCount(boolean containsTotalCount) {
        this.containsTotalCount = containsTotalCount;
    }

    public List<Sort.Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Sort.Order> orders) {
        this.orders = orders;
    }

    public Boolean getAsyncTotalCount() {
        return asyncTotalCount;
    }

    public void setAsyncTotalCount(Boolean asyncTotalCount) {
        this.asyncTotalCount = asyncTotalCount;
    }

    @Override
    public int getOffset() {
        if(page >= 1){
            return (page-1) * limit;
        }
        return 0;
    }
}
