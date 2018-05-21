package com.hello.spring;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/21 17:22
 * @Path: com.hello.spring
 */
public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route("/spring/hello").handler(
                // 唤起vert.x的事件总线，并发送一个简单消息
                ctx -> vertx.eventBus().<String>send(
                        // 消息地址
                        SpringVerticle.GET_HELLO_MSG_SERVICE_ADDRESS,
                        // 消息内容
                        "event bus calls spring service",
                        // 异步结果处理
                        result -> {
                            if (result.succeeded()) {
                                // 成功的话，返回处理结果给前台，这里的处理结果就是service返回的一段字符串
                                ctx.response()
                                        .putHeader("content-type",
                                                "application/json")
                                        .end(result.result().body());
                            } else {
                                ctx.response().setStatusCode(400)
                                        .end(result.cause().toString());
                            }
                        }));
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}
