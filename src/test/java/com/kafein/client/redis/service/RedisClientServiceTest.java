package com.kafein.client.redis.service;

import com.kafein.common.exception.ResourceNotFoundException;
import com.kafein.common.model.LogMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisClientServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private RedisClientService redisClientService;

    private static final String VALID_EVENT_ID = "123e4567-e89b-12d3-a456-426614174000";

    private LogMessage mockLogMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockLogMessage = new LogMessage();
        mockLogMessage.setEventId(VALID_EVENT_ID);
        mockLogMessage.setPayload("Test Log Payload");

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    /**
     * ✅ Test Case: Successfully retrieving a log from Redis
     */
    @Test
    void testGetLog_Success() {
        when(valueOperations.get(VALID_EVENT_ID)).thenReturn(mockLogMessage);

        String result = redisClientService.getLog(VALID_EVENT_ID);

        assertEquals("Test Log Payload", result);
        verify(valueOperations, times(1)).get(VALID_EVENT_ID);
    }

    /**
     * ✅ Test Case: Throws IllegalArgumentException for null eventId
     */
    @Test
    void testGetLog_ThrowsException_WhenEventIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                redisClientService.getLog(null)
        );

        assertEquals("Invalid event ID: must be a non-empty UUID.", exception.getMessage());
        verify(valueOperations, never()).get(anyString());
    }

    /**
     * ✅ Test Case: Throws IllegalArgumentException for empty eventId
     */
    @Test
    void testGetLog_ThrowsException_WhenEventIdIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                redisClientService.getLog(" ")
        );

        assertEquals("Invalid event ID: must be a non-empty UUID.", exception.getMessage());
        verify(valueOperations, never()).get(anyString());
    }

    /**
     * ✅ Test Case: Throws ResourceNotFoundException when log not found in Redis
     */
    @Test
    void testGetLog_ThrowsException_WhenLogNotFound() {
        when(valueOperations.get(VALID_EVENT_ID)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                redisClientService.getLog(VALID_EVENT_ID)
        );

        assertEquals("Log message with ID: " + VALID_EVENT_ID + " not found or expired.", exception.getMessage());
        verify(valueOperations, times(1)).get(VALID_EVENT_ID);
    }
}
