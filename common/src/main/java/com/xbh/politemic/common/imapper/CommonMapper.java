package com.xbh.politemic.common.imapper;

import tk.mybatis.mapper.common.*;

/**
 * @CommonMapper: 通用接口
 * @author: ZBoHang
 * @time: 2021/10/12 14:49
 */
public interface CommonMapper<T> extends BaseMapper<T>,
                                         MySqlMapper<T>,
                                         ConditionMapper<T>,
                                         ExampleMapper<T>,
                                         RowBoundsMapper<T> {
}
