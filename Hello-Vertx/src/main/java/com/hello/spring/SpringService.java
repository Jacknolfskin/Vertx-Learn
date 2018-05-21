package com.hello.spring;

import org.springframework.stereotype.Component;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/21 17:22
 * @Path: com.hello.spring
 */
@Component(value = "springService")
public class SpringService {

    public String getHello() {
        return "hello spring";
    }
}
