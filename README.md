# 															PoliteMic



<p align="center">
<a target="_blank" href="https://gitee.com/zheng-bohang/politemic">
    	<img src="https://img.shields.io/hexpm/l/plug.svg" ></img>
		<img src="https://img.shields.io/badge/JDK-1.8+-green.svg" ></img>
        <img src="https://img.shields.io/badge/springboot-2.3.7.RELEASE-green" ></img>
</a></p>



#### 介绍



这是一个 [博客系统] 的后端项目,可以发挥你的聪明才智为其设计一个合理的前端页面,期待你的精彩操作。



#### 技术栈



- Spring Boot

  ​		[[Spring | Home](https://spring.io/)]

- TK-Mybatis

  ​		[[GitHub - godlike110/tk-mybatis: tk-mybatis 修复一写问题](https://github.com/godlike110/tk-mybatis)]

- MySQL

  ​		[[MySQL](https://www.mysql.com/)]

- Redis

  ​		[[Redis](https://redis.io/)]

- RabbitMQ

  ​		[[https://www.rabbitmq.com](https://www.rabbitmq.com/)]

- ElasticSearch

  ​		[[开源搜索：Elasticsearch、ELK Stack 和 Kibana 的开发者 | Elastic](https://www.elastic.co/cn/)]

- LOGBack

  ​		[[Logback Home (qos.ch)](https://logback.qos.ch/)]


- knife4j

  ​		[[knife4j (xiaominfo.com)](https://doc.xiaominfo.com/)]



#### 安装教程



- 安装运行环境JDK1.8、idea2021。

- 安装rabbitMQ3.9.5(erl 23.2)、redis3.2、mysql8.0.2、elasticsearch7.15.1(必须安装analysis-ik分词插件,可以安装kibana使用它的命令行来测试es)

- 执行sql文件[common/src/main/schemas/politemic.sql]。

- 启动项目,接口文档本地访问地址[http://localhost:8080/politemic/doc.html]。



#### 接口文档截图



![interface_doc](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/interface_doc001.png)


![interface_doc](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/interface_doc002.png)


![interface_doc](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/interface_doc003.png)



#### 系统流程说明



- ###### 用户注册流程


![用户注册流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/userRegister.png)


- ###### 用户登录流程


![用户登录流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/userLogin.png)


- ###### 用户激活流程


![用户激活模块](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/userActivate.png)


- ###### 用户信息获取流程


![用户信息获取模块](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/getUserInfo.png)


- ###### 未读(私信/通知)个数获取流程


![私信_通知未读个数获取流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/getUnReadNoticeCnt.png)


- ###### 修改用户信息


	用户信息的修改一个接口就可将密码、邮箱、评论尾巴及尾巴状态等用户信息进行修改。


- ###### 分页获取(通知/私信)流程


![分页获取(通知/私信)流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/pageNotice.png)


- ###### 获取(通知/私信)详情流程


![获取(通知/私信)详情流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/getNoticeDetail.png)


- ###### 分页查询帖子流程


![分页查询帖子流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/pageGetPosts.png)


- ###### 搜索帖子流程


![搜索帖子流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/searchPosts.png)


- ###### 发布帖子流程


![发布帖子流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/publishPost.png)


- ###### 获取帖子详情流程


![获取帖子详情流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/getPostDetail.png)


- ###### 发送评论流程


![发送评论流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/publishComment.png)


- ###### 关注用户流程


![关注用户流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/followUser.png)


- ###### 私信发送流程


![私信发送流程](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/sendLetter.png)


- ###### 帖子点赞 / 取消点赞


![帖子点赞 / 取消点赞](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/likePost.png)


- ###### 统计访问量


![当天访问量](https://gitee.com/zheng-bohang/politemic/raw/master/common/src/main/img/views.png)




#### 使用说明



- 表sysconfig为系统配置，表schedule_task为定时任务配置，sys_log为系统日志，sys_exception_log为系统异常日志、表comment为评论、表discuss_posts为帖子，表notice为通知，表queue_msg为队列消息，表sys_user为用户信息，表user_token为用户令牌，表sys_interface_auth暂未用到。
- 后端接口文档[http://localhost:8080/politemic/doc.html]，记得启动项目，登录名密码见[application.yml]。
- [application-dev.yml]中的邮件发送令牌自己可以去申请一个,具体步骤见百度。



#### 参与贡献



- [Echo: 🦄 开源社区系统：基于 SpringBoot + MyBatis + MySQL + Redis + Kafka + Elasticsearch + Spring Security + ... 并提供详细的开发文档和配套教程。包含帖子、评论、私信、系统通知、点赞、关注、搜索、用户设置、数据统计等模块。 (gitee.com)](https://gitee.com/veal98/Echo) 灵感来源于这。

- https://v1.jinrishici.com/all.json 随机古诗文。

- [Multiavatar: Multiavatar 是一个随机头像生成器，声称可生成 12 亿个唯一的头像 (gitee.com)](https://gitee.com/mirrors/Multiavatar?_from=gitee_search) 随机绘制头像。

- [Hutool — 🍬A set of tools that keep Java sweet.](https://www.hutool.cn/) 好用的第三方类库。



#### 特技




	感谢每一个开源作者的辛苦付出🍖。
	
	想找一个写前端的合作👬。
	
	我会用膝盖写代码。🤪