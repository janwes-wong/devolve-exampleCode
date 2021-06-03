package com.janwes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes
 * @date 2021/6/2 17:50
 * @description 应用启动类
 */
@SpringBootApplication
@EnableWebSocket
@EnableScheduling
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
