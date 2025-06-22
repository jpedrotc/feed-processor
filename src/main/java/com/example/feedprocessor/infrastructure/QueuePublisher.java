package com.example.feedprocessor.infrastructure;

import com.example.feedprocessor.domain.NormalizedMessage;

public interface QueuePublisher {
    void publish(NormalizedMessage message);
}

