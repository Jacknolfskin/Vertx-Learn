package com.hello.spring;

import io.vertx.core.AbstractVerticle;
import org.springframework.context.ApplicationContext;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/21 17:22
 * @Path: com.hello.spring
 */
public class SpringVerticle extends AbstractVerticle {

    public static final String GET_HELLO_MSG_SERVICE_ADDRESS = "get_hello_msg_service";
    private SpringService service;

    public SpringVerticle(ApplicationContext ctx) {
        // 从spring上下文获取service
        this.service = (SpringService) ctx.getBean("springService");
    }

    @Override
    public void start() throws Exception {
        // 唤起事件总线，注册一个事件处理者，或者直译叫事件消费者
        vertx.eventBus()
                .<String>consumer(GET_HELLO_MSG_SERVICE_ADDRESS)
                .handler(msg -> {
                    // 获取事件内容后，调用service服务
                    System.out.println("bus msg body is:" + msg.body());
                    String helloMsg = service.getHello();
                    System.out.println("msg from hello service is: "
                            + helloMsg);
                    // 将service返回的字符串，回应给消息返回体
                    msg.reply(helloMsg);
                });
    }

}
