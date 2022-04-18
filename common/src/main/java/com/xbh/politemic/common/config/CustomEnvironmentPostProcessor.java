package com.xbh.politemic.common.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @A:
 * @author: ZBoHang
 * @time: 2021/10/8 14:38
 */
@Slf4j
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
    private final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    private final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    /**
     * 自定义配置查询sql
     */
    private final String CONFIG_QUERY_SQL = "select sc.key,sc.value from sys_config sc where status = 'Y'";
    /**
     * 配置文件path
     */
    private final String PATH_YML_CONFIG_FILE = "applicationConfig: [classpath:/application-dev.yml]";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        // 获取yml中的相关配置 当然其他方式也行 prop.load()
        PropertySource<?> propertySource = environment.getPropertySources().get(this.PATH_YML_CONFIG_FILE);
        String url = String.valueOf(((Map) propertySource.getSource()).get(this.SPRING_DATASOURCE_URL));
        String userName = String.valueOf(((Map) propertySource.getSource()).get(this.SPRING_DATASOURCE_USERNAME));
        String userPass = String.valueOf(((Map) propertySource.getSource()).get(this.SPRING_DATASOURCE_PASSWORD));
        String driverClassName = String.valueOf(((Map) propertySource.getSource()).get(this.SPRING_DATASOURCE_DRIVER_CLASS_NAME));

        Map<String, String> cfgMap = this.getSysConfig(url, userName, userPass, driverClassName);
        if (cfgMap.isEmpty()) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>没有自定义的系统配置");
            return;
        }
        Properties customEnv = new Properties();
        cfgMap.forEach((k, v) -> {
            if (StrUtil.isNotBlank(k) || StrUtil.isNotBlank(v)) {
                System.out.println(k + " --> " + v);
                customEnv.setProperty(k, v);
            }
        });
        PropertiesPropertySource source = new PropertiesPropertySource("custom", customEnv);
        environment.getPropertySources().addLast(source);
        environment.addActiveProfile("custom");
    }

    /**
     * 从数据库获取系统自定义配置
     * @author: ZBoHang
     * @time: 2021/10/8 15:50
     */
    private Map<String, String> getSysConfig(String url, String userName, String userPass, String driverClassName) {
        Map<String, String> map = new HashMap<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 获得连接
            conn = DriverManager.getConnection(url, userName, userPass);
            // 加载驱动
            Class.forName(driverClassName).newInstance();
            // 创建执行器对象
            statement = conn.createStatement();
            // 执行sql语句
            resultSet = statement.executeQuery(this.CONFIG_QUERY_SQL);
            // 处理结果
            if (resultSet != null) {
                while (resultSet.next()) {
                    map.put(resultSet.getString("key"), resultSet.getString("value"));
                }
            }
        } catch (Exception e) {
            log.error("@@@@@系统配置获取过程中出现异常");
        } finally {
            // 释放资源
            try {
                this.close(conn, statement, resultSet);
            } catch (SQLException throwables) {
                log.error("@@@@@系统配置获取过程 释放资源时出现异常");
            }
        }
        return map;
    }

    /**
     * 释放资源
     * @author: ZBoHang
     * @time: 2021/10/8 15:48
     */
    private void close(Connection conn, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
