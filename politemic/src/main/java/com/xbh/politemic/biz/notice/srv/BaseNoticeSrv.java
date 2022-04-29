package com.xbh.politemic.biz.notice.srv;

import com.xbh.politemic.biz.notice.domain.Notice;
import com.xbh.politemic.biz.notice.mapper.NoticeMapper;
import com.xbh.politemic.common.srv.CommonSrv;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @BaseNoticeSrv: base 通知 srv
 * @author: ZBoHang
 * @time: 2021/12/9 16:38
 */
@Service
public class BaseNoticeSrv extends CommonSrv<Notice> {

    @Resource
    protected NoticeMapper noticeMapper;
}
