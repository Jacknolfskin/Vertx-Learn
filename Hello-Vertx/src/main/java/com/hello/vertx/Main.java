package com.hello.vertx;

import io.vertx.core.Vertx;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/14 10:17
 * @Path: io.example.vertxexample
 */
public class Main {
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(MyFirstVerticle.class.getName());
    }
}
