package com.xbh.politemic.biz.user.srv;

import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.biz.user.dto.UserDTO;
import com.xbh.politemic.biz.user.vo.UserLoginRequestVO;
import com.xbh.politemic.biz.user.vo.UserRegisterRequestVO;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.common.constant.UserConstant;
import com.xbh.politemic.common.util.ServiceAssert;
import com.xbh.politemic.common.util.StrKit;
import com.xbh.politemic.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户-用户业务层实现类
 * @Author: zhengbohang
 * @Date: 2021/10/3 15:23
 */
@Service
public class UserSrv {

    /**
     * 用户状态 激活状态
     */
    private final String ACTIVATE_STATUS = "1";
    /**
     * token是新增还是更新
     */
    private final String TOKEN_MODIFY = "TOKEN_MODIFY";

    private final String TOKEN_ADD = "TOKEN_ADD";
    /**
     * 实体类属性名
     */
    private final String COLUMN_NAME_USERNAME = "userName";

    private final String COLUMN_NAME_EMAIL = "email";
    /**
     * 用户默认头像前缀 感谢https://github.com/multiavatar/multiavatar-php提供的接口
     */
    private final String HEADER_URL_KEY_IN_ENV = "https://api.multiavatar.com/{}.svg";
    /**
     * 用户默认评论尾巴 感谢https://github.com/xenv/gushici提供的接口
     */
    private final String TAIL_URL_KEY_IN_ENV = "https://v1.jinrishici.com/all.json";

    @Autowired
    private BaseUserSrv baseUserSrv;
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
    public Map<String, Object> doLogin(UserLoginRequestVO vo) {

        SysUser user = this.baseUserSrv.selectOne(new SysUser().setUserName(vo.getUserName()));
        // 使用用户名查找未找见 混淆视野 防止一直试
        ServiceAssert.notNull(user, "用户名或者密码错误!");

        String encryptPass = StrKit.MD5Code(vo.getUserPass() + user.getSalt());

        ServiceAssert.isTrue(StrUtil.equals(user.getUserPass(), encryptPass), "用户名或者密码错误!");
        // TODO: 2021/10/9 登录失败 次数校验
        // 用户状态 0:未激活 1:已激活
        ServiceAssert.isFalse(StrUtil.equals(Constants.STATUS_STR_ZERO, user.getStatus()), "用户处于未激活状态!");
        // 生成一个登录令牌
        String token = StrKit.getUUID();
        // 从数据库中查找是否登录过
        UserToken userToken = this.baseUserTokenSrv.selectOne(new UserToken().setUserId(user.getId()));
        // 判断该修改还是新增token
        String addOrModify = userToken != null ? this.TOKEN_MODIFY : this.TOKEN_ADD;
        // 构建新的token
        userToken = UserDTO.buildNewToken(user.getId(), token);
        // 保存令牌 和 用户信息
        this.saveUserToken(userToken, addOrModify, user);
        // 返回map initialCapacity = 数量 / 负载因子 + 1
        Map<String, Object> resMap = new HashMap<>(4);

        resMap.put("token", userToken.getToken());

        resMap.put("userId", userToken.getUserId());
        // TODO: 2021/10/12 登录后的数据返回
        return resMap;
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
        SysUser sysUser = UserDTO.buildNewSysUser(vo);
        // 持久化
        this.baseUserSrv.insert(sysUser);
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
    public String activate(String id, String activateCode) {
        // 得到数据库的验证码
        String validCode = this.baseUserSrv.selectByPrimaryKey(id).getActivationCode();

        ServiceAssert.isTrue(activateCode.equals(validCode), "验证码错误,请输入正确的验证码!");
        // 若一致 修改用户状态
        this.baseUserSrv.updateByPrimaryKeySelective(new SysUser()
                .setId(id)
                // 用户状态 0:未激活 1:已激活
                .setStatus(Constants.STATUS_STR_ONE));

        return "激活成功!";
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
        criteria.orEqualTo(this.COLUMN_NAME_USERNAME, userName)

                .orEqualTo(this.COLUMN_NAME_EMAIL, email);
        // 包含返回 true
        if (this.baseUserSrv.selectCountByExample(example) > 0) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * 保存token至redis和数据库
     * @author: ZBoHang
     * @time: 2021/10/11 17:03
     */
    @Transactional(rollbackFor = Exception.class)
    void saveUserToken(UserToken userToken, String addOrModify, SysUser user) {

        if (StrUtil.equals(this.TOKEN_ADD, addOrModify)) {
            // 新增
            this.baseUserTokenSrv.insert(userToken);
        }

        if (StrUtil.equals(this.TOKEN_MODIFY, addOrModify)) {
            // 修改
            this.baseUserTokenSrv.updateByPrimaryKey(userToken);
        }
        // redis存储 "User_Token_" + 令牌
        this.redisClient.set(UserConstant.USER_TOKEN_PRE + userToken.getToken(),

                JSONObject.toJSONString(user), UserConstant.TOKEN_TIME_OUT_IN_REDIS);
    }
}
