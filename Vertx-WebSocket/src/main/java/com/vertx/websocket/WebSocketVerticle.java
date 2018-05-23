package com.vertx.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jacknolfskin
 * @Date: 2018/5/23 11:17
 * @Path: com.vertx.websocket
 */
public class WebSocketVerticle extends AbstractVerticle {
    // 保存每一个连接到服务器的通道
    private Map<String, ServerWebSocket> connectionMap = new HashMap<>(16);

    @Override
    public void start() throws Exception {

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);

        router.route("/").handler(routingContext -> {
            routingContext.response().sendFile("html/ws.html");
        });
        websocketMethod(server);
        server.requestHandler(router::accept).listen(8080);
    }

    public void websocketMethod(HttpServer server) {
        server.websocketHandler(webSocket -> {
            // 获取每一个链接的ID
            String id = webSocket.binaryHandlerID();
            // 判断当前连接的ID是否存在于map集合中，如果不存在则添加进map集合中
            if (!checkID(id)) {
                connectionMap.put(id, webSocket);
            }

            //　WebSocket 连接
            webSocket.frameHandler(handler -> {
                String textData = handler.textData();
                String currID = webSocket.binaryHandlerID();
                //给非当前连接到服务器的每一个WebSocket连接发送消息
                for (Map.Entry<String, ServerWebSocket> entry : connectionMap.entrySet()) {
                    if (currID.equals(entry.getKey())) {
                        continue;
                    }
                    /* 发送文本消息
                    文本信息似乎不支持图片等二进制消息
                    若要发送二进制消息，可用writeBinaryMessage方法
                    */
                    entry.getValue().writeTextMessage(textData);
                }
            });
            // 客户端WebSocket 关闭时，将当前ID从map中移除
            webSocket.closeHandler(handler -> connectionMap.remove(id));
        });
    }

    /**
     * 检查当前ID是否已经存在与map中
     *
     * @param id
     * @return
     */
    public boolean checkID(String id) {
        return connectionMap.containsKey(id);
    }
}
