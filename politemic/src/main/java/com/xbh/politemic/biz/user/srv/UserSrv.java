package com.xbh.politemic.biz.user.srv;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.biz.user.builder.UserBuilder;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.biz.user.vo.GetUserInfoResponseVO;
import com.xbh.politemic.biz.user.vo.ModifyUserInfoRequestVO;
import com.xbh.politemic.biz.user.vo.UserLoginRequestVO;
import com.xbh.politemic.biz.user.vo.UserRegisterRequestVO;
import com.xbh.politemic.common.constant.UserConstant;
import com.xbh.politemic.common.enums.user.UserStatusEnum;
import com.xbh.politemic.common.enums.user.UserTailStatusEnum;
import com.xbh.politemic.common.util.SensitiveWordFilter;
import com.xbh.politemic.common.util.ServiceAssert;
import com.xbh.politemic.common.util.StrKit;
import com.xbh.politemic.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 用户-用户业务层实现类
 * @Author: zhengbohang
 * @Date: 2021/10/3 15:23
 */
@Service
public class UserSrv extends BaseUserSrv {

    /**
     * 用户默认头像前缀 感谢https://github.com/multiavatar/multiavatar-php提供的接口
     */
    // "https://api.multiavatar.com/{}.svg";
    /**
     * 用户默认评论尾巴 感谢https://github.com/xenv/gushici提供的接口
     */
     // "https://v1.jinrishici.com/all.json";

    @Autowired
    private BaseUserTokenSrv baseUserTokenSrv;
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private Environment environment;

    /**
     * 用户登录
     * @param vo
     * userName 用户名
     * userPass 密码
     * @author: ZBoHang
     * @time: 2021/12/10 9:19
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> doLogin(UserLoginRequestVO vo) {

        SysUser user = this.selectOne(new SysUser().setUserName(vo.getUserName()));
        // 使用用户名查找未找见 混淆视野 防止一直试
        ServiceAssert.notNull(user, "用户名或者密码错误!");

        String encryptPass = StrKit.MD5Code(vo.getUserPass() + user.getSalt());

        ServiceAssert.isTrue(StrUtil.equals(user.getUserPass(), encryptPass), "用户名或者密码错误!");
        // TODO: 2021/10/9 登录失败 次数校验
        // 用户状态 0:未激活 1:已激活
        ServiceAssert.isFalse(StrUtil.equals(UserStatusEnum.INACTIVATED.getCode(), user.getStatus()), "用户处于未激活状态!");
        // 生成一个登录令牌
        String token = StrKit.getUUID();
        // 从数据库中查找是否登录过
        UserToken userToken = this.baseUserTokenSrv.selectOne(new UserToken().setUserId(user.getId()));
        // 获取原 token
        String originalToken = userToken != null ? userToken.getToken() : StrUtil.EMPTY;
        // 构建新的token
        userToken = UserBuilder.buildNewToken(user.getId(), token);
        // 保存令牌 和 用户信息  同类中未开启事务方法调用事务方法，事务不生效
        this.saveUserToken(userToken, originalToken, user);
        // TODO: 2021/10/12 登录后的数据返回
        return MapUtil.of(Pair.of("token", token));
    }

    /**
     * @description: 注册 默认普通用户
     * @author: zhengbohang
     * @date: 2021/10/4 10:12
     */
    @Transactional(rollbackFor = Exception.class)
    public String register(UserRegisterRequestVO vo) {
        // 验证其是否符合要求
        ServiceAssert.isTrue(ReUtil.isMatch(RegexPool.EMAIL, vo.getEmail()), "邮箱格式不正确!");
        //用户名和密码中不能有空格
        ServiceAssert.isFalse(StrUtil.containsBlank(vo.getUserName()) || StrUtil.containsBlank(vo.getUserPass()), "用户名和密码中不能有空白!");
        // 校验用户名和邮箱是否已经注册
        ServiceAssert.isFalse(this.checkEmailOrUserNameInDB(vo.getUserName(), vo.getEmail()), "用户名或邮箱重复!");
        // TODO: 2021/10/9 长度、是否汉字等相关校验
        SysUser sysUser = UserBuilder.buildNewSysUser(vo);
        // 持久化
        this.insert(sysUser);
        // 异步交给队列发送邮件
        this.asyncTask.createActivateEmailMsg(sysUser);
        // 拿到自定义环境变量中的用户默认评论尾巴获取url
        String tailUrl = this.environment.getProperty(UserConstant.TAIL_URL_KEY_IN_ENV);
        // 异步交给队列获取尾巴
        this.asyncTask.getTail(sysUser.getId(), tailUrl);
        // 注册成功后无需返回数据,由邮件传输数据
        return "注册成功,移步邮箱激活!";
    }

    /**
     * 激活用户
     * @author: zhengbohang
     * @date: 2021/10/4 16:24
     */
    @Transactional(rollbackFor = Exception.class)
    public String activate(String id, String activateCode) {
        // 得到数据库的验证码
        String validCode = this.selectByPrimaryKey(id).getActivationCode();

        ServiceAssert.isTrue(activateCode.equals(validCode), "验证码错误,请输入正确的验证码!");
        // 构建一个已激活状态的用户
        SysUser sysUser = UserBuilder.buildActivatedUser(id);
        // 若一致 修改用户状态
        this.updateByPrimaryKeySelective(sysUser);

        return "激活成功!";
    }

    /**
     * 获取用户信息
     * @param token 用户令牌
     * @return: com.xbh.politemic.biz.user.vo.GetUserInfoResponseVO
     * @author: ZBoHang
     * @time: 2021/12/14 12:39
     */
    public GetUserInfoResponseVO getUserInfo(String token) {
        // 获取信息
        SysUser sysUser = this.getUserInfoByToken(token);
        // 构建响应的用户信息
        return GetUserInfoResponseVO.build(sysUser);
    }

    /**
     * 解绑邮箱
     * @param userPass 用户密码
     * @param token 用户令牌
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/23 9:21
     */
    public String unBindMailBox(String userPass, String token) {
        // 获取用户信息
        SysUser sysUser = this.getUserInfoByToken(token);
        // 验证用户状态
        ServiceAssert.isFalse(StrUtil.equals(UserStatusEnum.INACTIVATED.getCode(), sysUser.getStatus()), "用户处于未激活状态,不能进行解绑操作!");

        ServiceAssert.isTrue(StrUtil.isNotBlank(sysUser.getEmail()), "用户邮箱已解绑!");

        String s = StrKit.MD5Code(userPass + sysUser.getSalt());
        // 验证密码
        ServiceAssert.isTrue(StrUtil.equals(sysUser.getUserPass(), s), "用户密码错误!");
        // 解绑
        this.updateByPrimaryKey(sysUser.setEmail(null));
        // 清除缓存中的数据
        this.redisClient.del(UserConstant.USER_TOKEN_PRE + token);

        return "邮箱解绑成功!";
    }

    /**
     * 修改用户信息
     * @param token 用户令牌
     * @param vo
     *         oldUserPass 旧密码
     *         newUserPass 新密码
     *         email 邮箱
     *         tail 评论尾巴
     *         tailStatus 尾巴开启状态 Y-开启 N-关闭 默认关闭
     * @return: void
     * @author: ZBoHang
     * @time: 2021/12/23 10:20
     */
    public String modifyUserInfo(String token, ModifyUserInfoRequestVO vo) {

        SysUser sysUser = this.getUserInfoByToken(token);
        // 密码
        String userPass = vo.getNewUserPass();

        if (StrUtil.isNotBlank(userPass)) {

            ServiceAssert.isTrue(StrUtil.equals(sysUser.getUserPass(), StrKit.MD5Code(vo.getOldUserPass() + sysUser.getSalt())), "旧密码错误!");

            ServiceAssert.isFalse(StrUtil.containsBlank(userPass), "密码中不能有空白!");

            sysUser.setUserPass(userPass);
        }
        // 邮箱
        String email = vo.getEmail();

        if (StrUtil.isNotBlank(email)) {

            ServiceAssert.isTrue(ReUtil.isMatch(RegexPool.EMAIL, vo.getEmail()), "邮箱格式不正确!");

            ServiceAssert.isFalse(this.checkEmailOrUserNameInDB(null, email), "该邮箱已被绑定!");

            sysUser.setEmail(email);
        }
        // 评论尾巴
        String tail = vo.getTail();

        if (StrUtil.isNotBlank(tail)) {

            ServiceAssert.isFalse(SensitiveWordFilter.isContainsSensitiveWord(tail), "评论尾巴中含有敏感词!");

            sysUser.setTail(tail);
        }
        // 评论尾巴状态 Y-开启 N-关闭
        String tailStatus = vo.getTailStatus();

        if (StrUtil.isNotBlank(tailStatus)) {

            sysUser.setTailStatus(UserTailStatusEnum.getTailStatusByStr(tailStatus));
        }

        ServiceAssert.isFalse(StrUtil.isAllBlank(userPass, email, tail, tailStatus), "未获取到要修改的数据!");

        this.updateByPrimaryKey(sysUser);

        return "修改成功!";
    }

    /**
     * 通过令牌获取用户 源 信息 只能获取token对应的用户信息
     * @param token 用户令牌
     * @return: com.xbh.politemic.biz.user.domain.SysUser
     * @author: ZBoHang
     * @time: 2021/12/14 10:03
     */
    public SysUser getUserInfoByToken(String token) {

        ServiceAssert.noneBlank(token, "令牌不能为空!");
        // 查询redis中是否存在
        String realTokenKey = UserConstant.USER_TOKEN_PRE + token;
        // 先查redis
        if (this.redisClient.hasKey(realTokenKey)) {
            // redis中存的为用户信息 str
            String userJsonStr = this.redisClient.get(realTokenKey);
            // 转换 返回
            return JSONUtil.toBean(userJsonStr, SysUser.class);
        }
        // redis中没有 查库判断token是否有效
        UserToken userToken = this.baseUserTokenSrv.selectOne(new UserToken().setToken(token));

        ServiceAssert.isTrue(userToken != null, "令牌不存在!");
        // 当前时间
        DateTime currentTime = DateUtil.date();
        // 到期时间
        Date expire = userToken.getExpire();
        // 数据库中能查到且到期时间在当前时间之后 则有效
        ServiceAssert.isTrue(expire.after(currentTime), "令牌已失效!");
        // 查用户信息
        SysUser sysUser = this.selectByPrimaryKey(userToken.getUserId());
        // redis存储 "User_Token_" + 令牌
        this.redisClient.set(UserConstant.USER_TOKEN_PRE + userToken.getToken(),

                JSONUtil.toJsonStr(sysUser), DateUtil.between(currentTime, expire, DateUnit.SECOND));

        return sysUser;
    }

    /**
     * 校验数据库中是否包含该用户名和邮箱 true-不包含 false-包含
     * @author: ZBoHang
     * @time: 2021/10/14 9:35
     */
    private boolean checkEmailOrUserNameInDB(String userName, String email) {

        Example example = new Example(SysUser.class);

        Example.Criteria criteria = example.createCriteria();
        // 用户名或者邮箱都不能重复
        if (StrUtil.isNotBlank(userName)) {

            criteria.orEqualTo("userName", userName);
        }
        if (StrUtil.isNotBlank(email)) {

            criteria.orEqualTo("email", email);
        }
        // 包含返回 true
        if (this.selectCountByExample(example) > 0) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * 保存token至redis和数据库
     * @author: ZBoHang
     * @time: 2021/10/11 17:03
     */
    void saveUserToken(UserToken userToken, String originalToken, SysUser user) {

        if (StrUtil.equals(StrUtil.EMPTY, originalToken)) {
            // 新增
            this.baseUserTokenSrv.insert(userToken);
        } else {
            // 修改
            this.baseUserTokenSrv.updateByPrimaryKey(userToken);
            // 删除redis中原有的用户信息
            this.redisClient.del(UserConstant.USER_TOKEN_PRE + originalToken);
        }
        // redis存储 "User_Token_" + 令牌
        this.redisClient.set(UserConstant.USER_TOKEN_PRE + userToken.getToken(),

                JSONUtil.toJsonStr(user), UserConstant.TOKEN_TIME_OUT_IN_REDIS);
    }
}
