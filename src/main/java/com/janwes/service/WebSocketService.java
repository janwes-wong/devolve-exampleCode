package com.janwes.service;

import com.janwes.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.service
 * @date 2021/6/2 17:54
 * @description WebSocket核心类
 * 该@ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{nickName}")
public class WebSocketService {
    /**
     * 用来存放每个客户端对应的WebSocket对象
     */
    private static final CopyOnWriteArraySet<WebSocketService> SOCKET_SERVICES = new CopyOnWriteArraySet<>();

    /**
     * 用session作为key，保存用户信息
     */
    private static final ConcurrentHashMap<Session, UserInfo> CONNECT_MAP = new ConcurrentHashMap<>();

    /**
     * 静态变量，用来记录当前在线连接数
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickName") String nickName) {
        this.session = session;
        UserInfo userInfo = new UserInfo(session.getId(), nickName);
        CONNECT_MAP.put(session, userInfo);
        SOCKET_SERVICES.add(this); // 加入set中
        log.info(nickName + " 上线了！当前在线人数为" + SOCKET_SERVICES.size());
        // 群发消息，告诉每一位
        broadcast(nickName + " 上线了！-->当前在线人数为：" + SOCKET_SERVICES.size());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        String nickName = CONNECT_MAP.get(session).getNickName();
        CONNECT_MAP.remove(session);
        SOCKET_SERVICES.remove(this);  // 从set中删除
        log.info(nickName + " 下线了！当前在线人数为" + SOCKET_SERVICES.size());
        // 群发消息，告诉每一位
        broadcast(nickName + " 下线，当前在线人数为：" + SOCKET_SERVICES.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        // 群发消息
        String nickName = CONNECT_MAP.get(session).getNickName();
        broadcast(nickName + " 说：" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }

    //发送消息
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                log.info("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 群发自定义消息
     *
     * @param message
     */
    public void broadcast(String message) {
        try {
            for (WebSocketService socketService : SOCKET_SERVICES) {
                // 同步发送消息(阻塞)
                // this.session.getBasicRemote().sendText(message);
                // 异步发送消息(非阻塞)
                socketService.session.getAsyncRemote().sendText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给指定用户发送信息
     *
     * @param nickname
     * @param message
     */
    public void sendInfo(String nickname, String message) {
        /*Session session = CONNECT_MAP.get(nickname);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static CopyOnWriteArraySet<WebSocketService> getSocketServices() {
        return SOCKET_SERVICES;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
