package com.xbh.politemic.biz.user.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Follow implements Serializable {

    private static final long serialVersionUID = 1899779424087515216L;
    /**
     * 关注用户id
     */
    @Id
    private String followUserId;

    /**
     * 被关注用户id
     */
    private String followedUserId;

    /**
     * 关注时间
     */
    private Date followTime;

    /**
     * 是否有效 1-有效 2-无效
     */
    private String status;
}