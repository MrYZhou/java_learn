package com.larry.message.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/msg")
public class MsgController {

    @GetMapping("/connect/{id}")
    public SseEmitter  connectUser(@PathVariable String id) throws Exception {
        return connect(id);

    }

    @GetMapping("/send")
    public void sened() throws Exception {
        sendMessage("77777","hello larry");
    }
    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 创建连接
     *
     * @date: 2022/7/12 14:51
     * @auther: 公众号：程序员小富
     */
    public static SseEmitter connect(String userId) {
        try {
            // 设置超时时间，0表示不过期。默认30秒
            SseEmitter sseEmitter = new SseEmitter(0L);
            // 注册回调
//            sseEmitter.onCompletion(completionCallBack(userId));
//            sseEmitter.onError(errorCallBack(userId));
//            sseEmitter.onTimeout(timeoutCallBack(userId));

            sseEmitterMap.put(userId, sseEmitter);

            return sseEmitter;
        } catch (Exception e) {
            log.info("创建新的sse连接异常，当前用户：{}", userId);
        }
        return null;
    }

    /**
     * 给指定用户发送消息
     */
    public static void sendMessage(String userId, String message) {

        if (sseEmitterMap.containsKey(userId)) {
            try {
                sseEmitterMap.get(userId).send(message);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", userId, e.getMessage());
            }
        }
    }

}
