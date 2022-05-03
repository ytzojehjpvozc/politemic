package com.xbh.politemic.biz.notice.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.user.srv.BaseUserSrv;
import com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO;
import com.xbh.politemic.common.enums.notice.NoticeStatusEnum;
import com.xbh.politemic.common.util.ServiceAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @NoticeSrv: 通知 srv
 * @author: ZBoHang
 * @time: 2021/12/13 16:07
 */
@Service
public class NoticeSrv extends BaseNoticeSrv {

    @Autowired
    private BaseUserSrv baseUserSrv;

    /**
     * 获取指定用户未读通知个数 通过用户id
     * @param userId 用户id
     * @return: java.lang.Integer
     * @author: ZBoHang
     * @time: 2021/12/13 16:24
     */
    public Integer getUnReadNoticeCnt(String userId) {

        Example example = Example.builder(Notice.class).build();
        // 构建查询条件
        Example.Criteria criteria = example.createCriteria();
        // 未读状态的 通知状态 0-未读 1-已读 2-删除
        criteria.andEqualTo("status", NoticeStatusEnum.UNREAD_STATUS.getCode())
                // 接收方为 userId用户的
                .andEqualTo("toId", userId);
        // 查找个数
        return this.selectCountByExample(example);
    }

    /**
     * 获取 通知/私信 详情
     * @param userId 当前登录用户id
     * @param noticeId 通知/私信 id
     * @return: com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO
     * @author: ZBoHang
     * @time: 2021/12/13 17:07
     */
    public GetNoticeDetailResponseVO getNoticeDetail(String noticeId, String userId) {
        // 查询通知详情
        Notice notice = this.selectByPrimaryKey(noticeId);

        ServiceAssert.notNull(notice, "未获取到通知详情!");
        // 私信接受用户必须为当前登录用户
        ServiceAssert.isTrue(StrUtil.equals(userId, notice.getToId()), "没有权限读取通知详情!");
        // 被删除的通知无法查看
        ServiceAssert.isFalse(StrUtil.equals(notice.getStatus(), NoticeStatusEnum.DELETE_STATUS.getCode()), "通知已删除,不能查看!");

        String fromId = notice.getFromId();
        // 默认系统通知
        String userName = "系统通知";
        // 当来源方不为空时 即来源方不为系统时
        if (StrUtil.isNotBlank(fromId)) {
            // 获取通知来源方名称
            userName = this.baseUserSrv.selectByPrimaryKey(fromId).getUserName();
        }

        return GetNoticeDetailResponseVO.build(notice, userName);
    }


}
