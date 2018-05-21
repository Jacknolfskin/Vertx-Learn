package com.hello.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/14 10:19
 * @Path: io.example.vertxexample
 */
public class MyFirstVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> f) {
        vertx.createHttpServer()
                .requestHandler(req -> {
                    req.response().putHeader("content-type", "text/plain").end("Hello World!");
                }).listen(8080, result -> {
            if (result.succeeded()) {
                f.complete();
            } else {
                f.fail(result.cause());
            }
        });
    }
}
