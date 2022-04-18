package com.xbh.politemic.biz.user.srv;

import cn.hutool.core.util.StrUtil;
import com.xbh.politemic.bean.RedisClient;
import com.xbh.politemic.common.constant.Constants;
import com.xbh.politemic.biz.user.domain.SysUser;
import com.xbh.politemic.biz.user.domain.UserToken;
import com.xbh.politemic.biz.user.mapper.SysUserMapper;
import com.xbh.politemic.biz.user.mapper.UserTokenMapper;
import com.xbh.politemic.task.AsyncTask;
import com.xbh.politemic.common.util.Result;
import com.xbh.politemic.common.util.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
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
     * 随机盐值的获取设置
     */
    private final int SALT_START_INDEX = 0;

    private final int SALT_END_INDEX = 6;
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
    private final String HEADER_URL_PRE = "https://api.multiavatar.com/{uid}.svg";
    /**
     * 用户默认评论尾巴 感谢https://github.com/xenv/gushici提供的接口
     */
    private final String TAIL_URL = "https://v1.jinrishici.com/all.json";

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private UserTokenMapper userTokenMapper;
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private RedisClient redisClient;

    /**
     * @description: 用户登录
     * @param userName: 用户名
     * @param userPass: 密码
     * @author: zhengbohang
     * @date: 2021/10/3 15:29
     */
    public Result doLogin(String userName, String userPass) {
        // 返回map
        Map<String, Object> resMap = new HashMap<>(16);
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(userPass)) {
            return Result.failure("用户名或者密码不能为空白");
        }
        SysUser user = this.sysUserMapper.selectOne(new SysUser().setUserName(userName));
        // 使用用户名查找未找见
        if (user == null) {
            return Result.failure("用户名或者密码错误");
        }
        String encryptPass = StrKit.MD5Code(userPass + user.getSalt());
        if (!StrUtil.equals(user.getUserPass(), encryptPass)) {
            // TODO: 2021/10/9 登录失败 次数校验
            return Result.failure("用户名或者密码错误");
        }
        if (StrUtil.equals(Constants.DEFAULT_REGISTER_STATUS, user.getStatus())) {
            return Result.failure("用户处于未激活状态");
        }
        // 生成一个登录令牌
        String token = StrKit.getUUID();
        // 从数据库中查找是否登录过
        UserToken userToken = this.userTokenMapper.selectOne(new UserToken().setUserId(user.getId()));
        String addOrModify;
        if (userToken != null) {
            // 如果登录过
            addOrModify = this.TOKEN_MODIFY;
        } else {
            // 如果没有登录过
            addOrModify = this.TOKEN_ADD;
            userToken = new UserToken();
        }
        userToken.setUserId(user.getId());
        userToken.setToken(token);
        userToken.setExpire(new Timestamp(System.currentTimeMillis() + Constants.TOKEN_TIME_OUT));
        // 保存token
        this.saveUserToken(userToken, addOrModify);
        resMap.put("token", userToken);
        // TODO: 2021/10/12 登录后的数据返回
        return Result.success(resMap);
    }

    /**
     * @description: 注册 默认普通用户
     * @author: zhengbohang
     * @date: 2021/10/4 10:12
     */
    public Result register(SysUser sysUser) {
        // 用户名、密码、邮箱不能为空白
        if (StrUtil.isBlank(sysUser.getUserName()) ||
                StrUtil.isBlank(sysUser.getUserPass()) ||
                StrUtil.isBlank(sysUser.getEmail())) {
            return Result.failure("用户名、密码或邮箱不能为空白");
        }
        // 验证其是否符合要求
        if (!StrKit.validEmail(sysUser.getEmail())) {
            return Result.failure("邮箱格式错误");
        }
        //用户名和密码中不能有空格
        if (StrUtil.contains(sysUser.getUserName(), Constants.SPACE_STRING) ||
                StrUtil.contains(sysUser.getUserPass(), Constants.SPACE_STRING))  {
            return Result.failure("用户名和密码中不能有空格");
        }
        if (!this.checkEmailOrUserNameInDB(sysUser.getUserName(), sysUser.getEmail())){
            return Result.failure("邮箱或用户名已被使用");
        }
        // TODO: 2021/10/9 长度、是否汉字等相关校验
        //生成随机id
        String id = StrKit.getUUID();
        // 盐值
        String salt = StrKit.getSalt(this.SALT_START_INDEX, this.SALT_END_INDEX);
        // 加密
        String userPass = StrKit.MD5Code(sysUser.getUserPass() + salt);
        // 验证码
        String validateCode = StrKit.getValidateCode();
        sysUser.setId(id)
                .setSalt(salt)
                .setUserPass(userPass)
                .setAuthFieldLevel(Constants.DEFAULT_REGISTER_AUTH_LEVEL)
                .setStatus(Constants.DEFAULT_REGISTER_STATUS)
                .setActivationCode(validateCode)
                .setHeaderUrl(StrUtil.format(this.HEADER_URL_PRE, id))
                .setTailStatus(Constants.STATUS_N);
        // 持久化
        this.saveRegisterInfo(sysUser);
        // 异步交给队列发送邮件
        this.asyncTask.createActivateEmailMsg(sysUser);
        // 异步交给队列获取尾巴
        this.asyncTask.getTail(id, this.TAIL_URL);
        return Result.success("注册成功,移步邮箱激活");
    }

    /**
     * 激活用户
     * @author: zhengbohang
     * @date: 2021/10/4 16:24
     */
    public Result activate(String id, String activateCode) {
        // 得到数据库的验证码
        String validCode = this.sysUserMapper.selectByPrimaryKey(id).getActivationCode();
        // 若一致 修改用户状态
        if (validCode.equals(activateCode)) {
            this.modifyUserState(id);
            return Result.success("激活成功");
        }
        return Result.failure("激活失败,联系管理处理");
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

        if (this.sysUserMapper.selectCountByExample(example) > 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 保存token至redis和数据库
     * @author: ZBoHang
     * @time: 2021/10/11 17:03
     */
    @Transactional(rollbackFor = Exception.class)
    void saveUserToken(UserToken userToken, String addOrModify) {
        if (StrUtil.equals(this.TOKEN_ADD, addOrModify)) {
            this.userTokenMapper.insert(userToken);
        }
        if (StrUtil.equals(this.TOKEN_MODIFY, addOrModify)) {
            this.userTokenMapper.updateByPrimaryKey(userToken);
        }
        redisClient.set(Constants.USER_TOKEN_PRE + userToken.getToken(), userToken.getUserId(), Constants.TOKEN_TIME_OUT);
    }

    /**
     *  激活成功后修改用户状态
     * @author: zhengbohang
     * @date: 2021/10/4 16:45
     */
    @Transactional(rollbackFor = Exception.class)
    void modifyUserState(String id) {
        this.sysUserMapper.updateByPrimaryKeySelective(new SysUser().setId(id)
                                                                    .setStatus(this.ACTIVATE_STATUS));
    }

    /**
     * @description: 保存注册信息 到mysql
     * @author: zhengbohang
     * @date: 2021/10/4 12:37
     */
    @Transactional(rollbackFor = Exception.class)
    void saveRegisterInfo(SysUser sysUser) {
        this.sysUserMapper.insert(sysUser);
    }
}
