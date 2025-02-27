package com.kafein.client.redis.controller;

import com.kafein.client.redis.service.RedisClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisClientController {

    private final RedisClientService redisClientService;

    public RedisClientController(RedisClientService redisClientService) {
        this.redisClientService = redisClientService;
    }

    @GetMapping("/get/{eventId}")
    public String getLog(@PathVariable String eventId) {
       return redisClientService.getLog(eventId);
    }
}
