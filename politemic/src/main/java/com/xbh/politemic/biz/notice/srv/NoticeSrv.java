package com.xbh.politemic.biz.notice.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.biz.notice.builder.NoticeBuilder;
import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.notice.vo.PageNoticeRequestVO;
import com.xbh.politemic.biz.notice.vo.PageNoticeResponseVO;
import com.xbh.politemic.biz.user.srv.UserSrv;
import com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO;
import com.xbh.politemic.common.constant.NoticeConstant;
import com.xbh.politemic.common.enums.notice.NoticeStatusEnum;
import com.xbh.politemic.common.enums.notice.NoticeTypeEnum;
import com.xbh.politemic.common.util.PageUtil;
import com.xbh.politemic.common.util.ServiceAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @NoticeSrv: 通知 srv
 * @author: ZBoHang
 * @time: 2021/12/13 16:07
 */
@Service
public class NoticeSrv extends BaseNoticeSrv {

    @Autowired
    private UserSrv userSrv;

    /**
     * 获取指定用户未读通知个数 通过用户id
     * @param userId 用户id
     * @return: java.lang.Integer
     * @author: ZBoHang
     * @time: 2021/12/13 16:24
     */
    public Map<String, Integer> getUnReadNoticeCnt(String userId) {
        // 通知
        Example unReadNoticeExample = Example.builder(Notice.class).build();
        // 私信
        Example unReadPrivateLetterExample = Example.builder(Notice.class).build();
        // 构建查询条件
        Example.Criteria unReadNoticeCriteria = unReadNoticeExample.createCriteria();

        Example.Criteria unReadPrivateLetterCriteria = unReadPrivateLetterExample.createCriteria();
        // 未读状态的通知 0-未读 1-已读 2-删除
        unReadNoticeCriteria.andEqualTo("status", NoticeStatusEnum.UNREAD_STATUS.getCode())
                // 接收方为 userId用户的
                .andEqualTo("toId", userId)
                // 系统通知 fromId 字段为空
                .andIsNull("fromId");
        // 未读状态的私信 0-未读 1-已读 2-删除
        unReadPrivateLetterCriteria.andEqualTo("status", NoticeStatusEnum.UNREAD_STATUS.getCode())
                // 接收方为 userId用户的
                .andEqualTo("toId", userId)
                // 用户私信 fromId 字段不为空
                .andIsNotNull("fromId");
        // 查找未读通知个数
        Integer unReadNoticeCnt = this.selectCountByExample(unReadNoticeExample);
        // 查找未读私信个数
        Integer unReadPrivateLetterCnt = this.selectCountByExample(unReadPrivateLetterExample);

        return new HashMap<String, Integer>(){
            {
                put("notice", unReadNoticeCnt);
                put("letter", unReadPrivateLetterCnt);
            }
        };
    }

    /**
     * 获取 通知/私信 详情
     * @param userId 当前登录用户id
     * @param noticeId 通知/私信 id
     * @return: com.xbh.politemic.biz.user.vo.GetNoticeDetailResponseVO
     * @author: ZBoHang
     * @time: 2021/12/13 17:07
     */
    @Transactional(rollbackFor = Exception.class)
    public GetNoticeDetailResponseVO getNoticeDetail(Integer noticeId, String userId, String token) {
        // 查询通知详情
        Notice notice = this.selectByPrimaryKey(noticeId);

        ServiceAssert.notNull(notice, "未获取到通知详情!");
        // 私信接受用户必须为当前登录用户
        ServiceAssert.isTrue(StrUtil.equals(userId, notice.getToId()), "没有权限读取通知详情!");
        // 被删除的通知无法查看
        ServiceAssert.isFalse(StrUtil.equals(notice.getStatus(), NoticeStatusEnum.DELETE_STATUS.getCode()), "通知已删除,不能查看!");
        // 构建一个已读状态的通知
        Notice readStatusNotice = NoticeBuilder.buildReadStatusNotice(noticeId);
        // 读取详情后修改状态
        this.updateByPrimaryKeySelective(readStatusNotice);
        // 通知方 不填为系统通知 有值则为私信(对应用户id)
        String fromId = notice.getFromId();
        // 默认系统通知
        String userName = NoticeConstant.SYSTEM_NOTICE_FROM_NAME;
        // 当来源方不为空时 即来源方不为系统时
        if (StrUtil.isNotBlank(fromId)) {
            // 获取通知来源方名称
            userName = this.userSrv.getUserInfoByToken(token).getUserName();
        }
        // 构建响应 vo
        return GetNoticeDetailResponseVO.build(notice, userName);
    }

    /**
     * 分页获取 私信/通知 默认未读通知
     * @param vo 请求参数
     * @return: com.xbh.politemic.common.util.PageUtil<com.xbh.politemic.biz.log.domain.SysLog>
     * @author: ZBoHang
     * @time: 2021/12/14 11:27
     */
    public PageUtil<PageNoticeResponseVO> pageNotice(PageNoticeRequestVO vo, String userId, String token) {
        // 当前页数
        Integer pageNum = vo.getCurrentPageNum();
        // 每页数据大小
        Integer pageSize = vo.getCurrentPageSize();
        // 筛选条件 状态
        String noticeStatus = vo.getStatus();
        // 筛选条件 类型
        String noticeType = vo.getType();

        Example example = Example.builder(Notice.class).build();

        Example.Criteria criteria = example.createCriteria();
        // 按时间倒序
        example.setOrderByClause("time DESC");

        // 接收方为 userId用户的
        criteria.andEqualTo("toId", userId)
                // 状态默认未读
                .andEqualTo("status", NoticeStatusEnum.getStatusByStr(noticeStatus));
        // 类型
        if (StrUtil.equals(NoticeTypeEnum.LETTER.getCode(), noticeType)) {
            // 若为letter 则为私信
            criteria.andIsNotNull("fromId");
        } else {
            // 其它则为通知(默认类型通知)
            criteria.andIsNull("fromId");
        }
        // 总数
        Integer count = this.selectCountByExample(example);
        // 分页列表数据
        List<Notice> noticeList = this.selectByExampleAndRowBounds(example, pageNum, pageSize);

        List<PageNoticeResponseVO> voList;
        // 再次判断是私信还是通知
        if (StrUtil.equals(NoticeTypeEnum.LETTER.getCode(), noticeType)) {
            // 构建私信列表
            voList = PageNoticeResponseVO.buildUserLetter(noticeList);
        } else {
            // 构建系统通知列表
            voList = PageNoticeResponseVO.buildSystemNotice(noticeList, NoticeConstant.SYSTEM_NOTICE_FROM_NAME);
        }
        // 返回page
        return new PageUtil<>(pageNum, pageSize, count.longValue(), voList);
    }
}
