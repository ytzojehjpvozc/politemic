package com.xbh.politemic.biz.notice.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Notice implements Serializable {
    /**
     * 通知id
     */
    @Id
    private Integer id;

    /**
     * 通知方
     */
    private String fromId;

    /**
     * 接收方
     */
    private String toId;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 通知状态 0-未读 1-已读 2-删除
     */
    private String status;

    /**
     * 时间
     */
    private Date time;

    private static final long serialVersionUID = 1L;
}