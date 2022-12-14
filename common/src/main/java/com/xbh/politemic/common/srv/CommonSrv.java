package com.xbh.politemic.common.srv;

import com.xbh.politemic.common.imapper.CommonMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @CommonSrv: 公共srv
 * @author: ZBoHang
 * @time: 2021/12/9 15:09
 */
public abstract class CommonSrv<T> {

    @Autowired
    protected CommonMapper<T> commonMapper;

    //********************* 查 询 **************************

    /**
     * 查找符合条件的一个
     * @author: ZBoHang
     * @time: 2021/12/9 15:12
     */
    public T selectOne(T t) {
        return this.commonMapper.selectOne(t);
    }

    /**
     * 通过主键查
     * @author: ZBoHang
     * @time: 2021/12/9 15:15
     */
    public T selectByPrimaryKey(Object key) {
        return this.commonMapper.selectByPrimaryKey(key);
    }

    /**
     * 通过example查
     * @author: ZBoHang
     * @time: 2021/12/9 15:16
     */
    public T selectOneByExample(Object example) {
        return this.commonMapper.selectOneByExample(example);
    }

    /**
     * 通过example查找个数
     * @author: ZBoHang
     * @time: 2021/12/9 15:30
     */
    public int selectCountByExample(Object example) {
        return this.commonMapper.selectCountByExample(example);
    }

    /**
     * 通过record查找个数
     * @author: ZBoHang
     * @time: 2021/12/9 15:30
     */
    public int selectCount(T t) {
        return this.commonMapper.selectCount(t);
    }

    /**
     * 分页查询 通过条件
     * @param example 条件
     * @param pageNum 当前页 从1开始
     * @param pageSize 当前页数据量 大于0
     * @return: java.util.List<T>
     * @author: ZBoHang
     * @time: 2021/12/14 14:00
     */
    public List<T> selectByExampleAndRowBounds(Object example, Integer pageNum, Integer pageSize) {
         return this.commonMapper.selectByExampleAndRowBounds(example, new RowBounds((pageNum - 1) * pageSize, pageSize));
    }

    /**
     * 分页查询 通过实体
     * @param t
     * @param pageNum 当前页 从1开始
     * @param pageSize 当前页数据量 大于0
     * @return: java.util.List<T>
     * @author: ZBoHang
     * @time: 2021/12/15 11:31
     */
    public List<T> selectByRowBounds(T t, Integer pageNum, Integer pageSize) {
        return this.commonMapper.selectByRowBounds(t, new RowBounds((pageNum - 1) * pageSize, pageSize));
    }

    /**
     * 通过example查找多个
     * @author: ZBoHang
     * @time: 2021/12/9 15:30
     */
    public List<T> selectByExample(Object example) {
        return this.commonMapper.selectByExample(example);
    }

    //********************* 增 加 **************************

    /**
     * 新增
     * @author: ZBoHang
     * @time: 2021/12/9 15:32
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(T t) {
        return this.commonMapper.insert(t);
    }

    /**
     * 有选择性地新增
     * @author: ZBoHang
     * @time: 2021/12/9 15:32
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(T t) {
        this.commonMapper.insertSelective(t);
    }

    /**
     * 自动生成主键的 地新增
     * @author: ZBoHang
     * @time: 2021/12/9 15:32
     */
    @Transactional(rollbackFor = Exception.class)
    public int insertUseGeneratedKeys(T t) {
        return this.commonMapper.insertUseGeneratedKeys(t);
    }

    //********************* 修 改 **************************


    /**
     * 通过主键修改
     * @author: ZBoHang
     * @time: 2021/12/9 16:10
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByPrimaryKey(T t) {
        this.commonMapper.updateByPrimaryKey(t);
    }

    /**
     * 通过主键选择性修改
     * @author: ZBoHang
     * @time: 2021/12/9 15:27
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateByPrimaryKeySelective(T t) {
        this.commonMapper.updateByPrimaryKeySelective(t);
    }

    //********************* 删 除 **************************

}
