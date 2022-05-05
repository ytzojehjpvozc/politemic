package com.xbh.politemic.common.util;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @PageUtil: 分页工具
 * @author: ZBoHang
 * @time: 2021/12/13 18:00
 */
@Data
@Accessors(chain = true)
public class PageUtil {

    /**
     * 当前页数 从1开始
     */
    private Integer currentPageNum;

    /**
     * 当前页数据个数 不小于0
     */
    private Integer currentPageSize;

    /**
     * 总页数
     */
    private Long totalPageSize;

    /**
     * 总结果条数
     */
    private Long totalResultSize;
}
