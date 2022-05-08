package com.xbh.politemic.common.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @PageUtil: 分页工具
 * @author: ZBoHang
 * @time: 2021/12/13 18:00
 */
@Data
@Accessors(chain = true)
public class PageUtil<T> {

    /**
     * 当前页数 从1开始
     */
    private Integer currentPageNum = 1;

    /**
     * 当前页数据个数 不小于0
     */
    private Integer currentPageSize = 10;

    /**
     * 总页数
     */
    protected Long totalPageSize;

    /**
     * 总结果条数
     */
    protected Long totalResultSize;

    /**
     * 数据
     */
    protected List<T> data;

    public PageUtil() {
    }

    public PageUtil(Integer currentPageNum, Integer currentPageSize, Long totalResultSize, List<T> data) {
        this.currentPageNum = currentPageNum;
        this.currentPageSize = currentPageSize;
        this.totalResultSize = totalResultSize;
        Double num = Math.ceil( totalResultSize.doubleValue() / currentPageSize.doubleValue());
        this.totalPageSize = num.longValue();
        this.data = data;
    }

    public PageUtil<T> setCurrentPageNum(Integer currentPageNum) {

        this.currentPageNum = currentPageNum != null && currentPageNum > 0 ? currentPageNum : 1;

        return this;
    }

    public PageUtil<T> setCurrentPageSize(Integer currentPageSize) {

        this.currentPageSize = currentPageSize != null && currentPageSize > 0 ? currentPageSize : 10;

        return this;
    }
}
