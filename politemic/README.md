# politemic

#### 介绍

简单的博客系统 建造中···

#### 软件架构
##### 系统流程说明

###### 1.用户注册流程

![](<img src="https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/userRegister.png">)

###### 2.用户登录流程

![](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/userLogin.png)

###### 3.用户激活流程

![用户激活模块](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/userActivate.png)

###### 4.用户信息获取流程

![用户信息获取模块](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/getUserInfo.png)

###### 5.未读(私信/通知)个数获取流程

![私信_通知未读个数获取流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/getUnReadNoticeCnt.png)




#### 安装教程

1.  JDK1.8、idea
2.  安装redis、rabbitMQ3.9.5(erl 23.2)、redis3.2、mysql8.0.2
3.  sql文件在[common/src/main/schemas/politemic.sql]

#### 使用说明

1.  表sysconfig为系统配置，表schedule_task为定时任务配置，sys_log为系统日志，sys_exception_log为系统异常日志、表comment为评论、表discuss_posts为帖子，表notice为通知，表queue_msg为队列消息，表sys_user为用户信息，表user_token为用户令牌，表sys_interface_auth暂未用到
2.  后端接口文档[http://localhost:8080/politemic/doc.html]，记得启动项目，登录名密码见[application.yml]
3.  [application-dev.yml]中的邮件发送密码自己可以去申请一个,具体步骤见百度

#### 参与贡献

1.  [Echo: 🦄 开源社区系统：基于 SpringBoot + MyBatis + MySQL + Redis + Kafka + Elasticsearch + Spring Security + ... 并提供详细的开发文档和配套教程。包含帖子、评论、私信、系统通知、点赞、关注、搜索、用户设置、数据统计等模块。 (gitee.com)](https://gitee.com/veal98/Echo) 灵感来源于这。
2.  https://v1.jinrishici.com/all.json 随机古诗文
3.  https://api.multiavatar.com/{}.svg 随机绘制头像
4.  [Hutool — 🍬A set of tools that keep Java sweet.](https://www.hutool.cn/) 好用的第三方类库

#### 特技

1.  
