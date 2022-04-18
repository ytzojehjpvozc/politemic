package com.xbh.politemic.biz.user.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class UserToken implements Serializable {
    /**
     * 用户id
     */
    @Id
    private String userId;

    /**
     * token令牌
     */
    private String token;

    /**
     * 到期时间
     */
    private Date expire;

    private static final long serialVersionUID = 1L;
}