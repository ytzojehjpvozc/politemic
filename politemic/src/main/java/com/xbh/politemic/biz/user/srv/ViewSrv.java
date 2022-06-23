package com.xbh.politemic.biz.user.srv;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.common.constant.CommonConstants;
import com.xbh.politemic.common.util.ServiceAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ViewSrv: 访问量 srv
 * @author: ZBoHang
 * @time: 2022/1/17 14:14
 */
@Service
public class ViewSrv {

    private Long SEG_VIEWS_RESULT_SURVIVAL_TIME = 7 * 24 * 60 * 60L;

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

    /**
     *
     * @param startDateStr 开始日期字符串
     * @param endDateStr 结束日期字符串
     * @return: java.lang.Long
     * @author: ZBoHang
     * @time: 2022/1/17 15:09
     */
    public Long getViewsBySegment(String startDateStr, String endDateStr) {
        // 解析开始日期
        DateTime startDate = DateUtil.parse(startDateStr, DatePattern.NORM_DATE_PATTERN);
        // 解析结束日期
        DateTime endDate = DateUtil.parse(endDateStr, DatePattern.NORM_DATE_PATTERN);

        ServiceAssert.isFalse(startDate.isAfter(endDate), "开始日期不能在结束日期之后!");

        String segmentViewsKey = StrUtil.format(CommonConstants.REDIS_KEY_TEMPLATE_HYPER_LOG_UNION_NAME, startDate.toDateStr(), endDate.toDateStr());
        // 判断对应键是否存在
        if (this.redisClient.ttl(segmentViewsKey).compareTo(0L) <= 0) {
            // 将日期转换为对应字符串
            List<String> rangeToList = DateUtil.rangeToList(startDate, endDate, DateField.DAY_OF_YEAR).stream()

                    .map(dateTime -> StrUtil.format(CommonConstants.REDIS_KEY_TEMPLATE_SINGLE_DAY_VIEWS, dateTime.toDateStr()))

                    .collect(Collectors.toList());
            // 将结果汇总到新键
            this.redisClient.unionHyperLog(segmentViewsKey, rangeToList);
            // 拿到结果
            Long sizeHyperLog = this.redisClient.sizeHyperLog(segmentViewsKey);
            // 删除汇总键
            this.redisClient.deleteHyperLog(segmentViewsKey);
            // 重新设置带有生存时间的 key
            this.redisClient.set(segmentViewsKey, String.valueOf(sizeHyperLog), this.SEG_VIEWS_RESULT_SURVIVAL_TIME);
            // 返回汇总结果
            return sizeHyperLog;
        }
        // 有则查
        return Long.parseLong(this.redisClient.get(segmentViewsKey));
    }
}
