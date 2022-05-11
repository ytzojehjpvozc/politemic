package com.xbh.politemic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@MapperScan("com.xbh.politemic.biz.*.mapper")
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class PolitemicApplication {

    public static void main(String[] args) {

        SpringApplication.run(PolitemicApplication.class, args);

        log.info("## Politemsc 启动完成! ##");

    }

}
