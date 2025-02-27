package com.kafein.client.redis.service;

import com.kafein.common.exception.ResourceNotFoundException;
import com.kafein.common.model.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RedisClientService {

    private static final Logger logger = LoggerFactory.getLogger(RedisClientService.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisClientService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Retrieves a log message from Redis using the given event ID.
     *
     * @param eventId The unique identifier for the log message.
     * @return The log message payload.
     * @throws IllegalArgumentException If the eventId is null or empty.
     * @throws ResourceNotFoundException If the log message is not found or has expired.
     */
    public String getLog(String eventId) {
        validateEventId(eventId);

        LogMessage logMessage = (LogMessage) redisTemplate.opsForValue().get(eventId);

        if (Objects.nonNull(logMessage)) {
            logger.info("Message found in Redis with ID: {}", eventId);
            return logMessage.getPayload();
        }

        logger.warn("Message with ID: {} not found or expired", eventId);
        throw new ResourceNotFoundException("Log message with ID: " + eventId + " not found or expired.");
    }

    /**
     * Validates the event ID.
     *
     * @param eventId The event ID to be validated.
     * @throws IllegalArgumentException If the event ID is null or empty.
     */
    private void validateEventId(String eventId) {
        if (eventId == null || eventId.trim().isEmpty()) {
            logger.error("Invalid event ID received: {}", eventId);
            throw new IllegalArgumentException("Invalid event ID: must be a non-empty UUID.");
        }
    }
}
