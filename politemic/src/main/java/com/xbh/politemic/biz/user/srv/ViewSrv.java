package com.xbh.politemic.biz.user.srv;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.common.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ViewSrv: 访问量 srv
 * @author: ZBoHang
 * @time: 2022/1/17 14:14
 */
@Service
public class ViewSrv {

    @Autowired
    private RedisClient redisClient;

    /**
     * 获取当天访问量
     * @return: java.lang.Long
     * @author: ZBoHang
     * @time: 2022/1/17 14:26
     */
    public Long getTodayViews() {
        // 处理模板 key
        String viewsKey = StrUtil.format(CommonConstants.REDIS_KEY_TEMPLATE_SINGLE_DAY_VIEWS, DateUtil.date().toDateStr());

        return this.redisClient.sizeHyperLog(viewsKey);
    }
}
