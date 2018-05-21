package com.hello.spring;

import io.vertx.core.Vertx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/21 17:23
 * @Path: com.hello.spring
 */
public class SpringMain {

    public static void main(String[] args) {
        // 注解方式配置，不需要配置文件
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        // 扫描哪些包内的注解
        ctx.scan("com.hello.spring");
        ctx.refresh();
        Vertx vertx = Vertx.vertx();
        // 部署spring模块
        vertx.deployVerticle(new SpringVerticle(ctx));
        // 部署服务器模块
        vertx.deployVerticle(new ServerVerticle());
    }

}
