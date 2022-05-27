package com.xbh.politemic.biz.user.builder;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.biz.user.vo.UserRegisterRequestVO;
import com.xbh.politemic.common.constant.UserConstant;
import com.xbh.politemic.common.enums.user.UserAuthFieldLevelEnum;
import com.xbh.politemic.common.enums.user.UserStatusEnum;
import com.xbh.politemic.common.enums.user.UserTailStatusEnum;
import com.xbh.politemic.common.util.StrKit;
import org.springframework.core.env.Environment;

/**
 * @UserDTO: 用户业务数据传输转换
 * @author: ZBoHang
 * @time: 2021/12/10 9:13
 */
public class UserBuilder {

    /**
     * 构建一个新的token
     * @author: ZBoHang
     * @time: 2021/12/10 9:10
     */
    public static UserToken buildNewToken(String userId, String token) {

        UserToken userToken = null;

        if (StrUtil.isAllNotBlank(userId, token)) {

            userToken = new UserToken().setUserId(userId)

                    .setToken(token)

                    .setExpire(DateUtil.date(System.currentTimeMillis() + UserConstant.TOKEN_TIME_OUT_IN_DB));


        }

        return userToken;
    }

    /**
     * 构建一个新的系统用户
     * @param vo
     *  userName 用户名
     *  userPass 密码
     *  email 绑定邮箱
     * @return: com.xbh.politemic.biz.user.domain.SysUser
     * @author: ZBoHang
     * @time: 2021/12/10 11:24
     */
    public static SysUser buildNewSysUser(UserRegisterRequestVO vo) {

        SysUser sysUser = null;

        if (vo != null) {
            //生成随机id
            String id = StrKit.getUUID();
            // 盐值
            String salt = StrKit.getSalt(UserConstant.SALT_START_INDEX, UserConstant.SALT_END_INDEX);
            // 加密
            String userPass = StrKit.MD5Code(vo.getUserPass() + salt);
            // 验证码
            String validateCode = StrKit.getValidateCode();
            // 头像url从 自定义环境变量中取得
            String headerUrl = SpringUtil.getBean(Environment.class).getProperty(UserConstant.HEADER_URL_KEY_IN_ENV);
            // 用户id
            sysUser = new SysUser().setId(id)
                    // 用户名
                    .setUserName(vo.getUserName())
                    // 密码盐值
                    .setSalt(salt)
                    // 加密密码
                    .setUserPass(userPass)
                    // 邮箱
                    .setEmail(vo.getEmail())
                    // 权限等级 0:超管 1:普通管理 2:普通用户
                    .setAuthFieldLevel(UserAuthFieldLevelEnum.USER.getCode())
                    // 用户状态 0:未激活 1:已激活
                    .setStatus(UserStatusEnum.INACTIVATED.getCode())
                    // 验证码
                    .setActivationCode(validateCode)
                    // 用户默认头像前缀 感谢https://github.com/multiavatar/multiavatar-php的接口
                    .setHeaderUrl(StrUtil.format(headerUrl, id))
                    // 尾巴开启状态 Y-开启 N-关闭 默认关闭
                    .setTailStatus(UserTailStatusEnum.TURN_OFF.getCode());
        }
        return sysUser;
    }

    /**
     * 构建一个已激活状态的用户
     * @param userId 用户id
     * @return: com.xbh.politemic.biz.user.domain.SysUser
     * @author: ZBoHang
     * @time: 2021/12/13 9:10
     */
    public static SysUser buildActivatedUser(String userId) {

        SysUser sysUser = null;

        if (StrUtil.isNotBlank(userId)) {
            // 用户id
            sysUser = new SysUser().setId(userId)
                    // 用户状态 0:未激活 1:已激活
                    .setStatus(UserStatusEnum.ACTIVATED.getCode());
        }

        return sysUser;
    }
}
