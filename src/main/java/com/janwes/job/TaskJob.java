package com.janwes.job;

import com.janwes.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.job
 * @date 2021/6/3 14:00
 * @description 定时任务类
 */
@Slf4j
@Component
public class TaskJob {

    /**
     * 每隔10s发送消息
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void sendMessage() {
        log.info(">>> ********** task job start **********");
        CopyOnWriteArraySet<WebSocketService> socketServices = WebSocketService.getSocketServices();
        socketServices.forEach(webSocketService -> webSocketService.getSession().getAsyncRemote().sendText("系统公告：长期使用电脑有害视力健康" + LocalDateTime.now().toString()));
        log.info(">>> ********** task job end **********");
    }
}
