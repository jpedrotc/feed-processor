package com.example.feedprocessor.infrastructure;

import com.example.feedprocessor.domain.NormalizedMessage;
import com.example.feedprocessor.utils.JsonFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockQueuePublisher implements QueuePublisher {

    @Override
    public void publish(NormalizedMessage message) {
        String jsonString = JsonFormatter.toJson(message).orElse("[failed to serialize message]");
        log.info("Publishing new message: {}", jsonString);
    }
}
