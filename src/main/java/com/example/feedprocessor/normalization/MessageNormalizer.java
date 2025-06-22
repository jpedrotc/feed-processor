package com.example.feedprocessor.normalization;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedMessage;

public interface MessageNormalizer<T extends Message, R extends NormalizedMessage> {
    boolean supports(Message message);
    R from(T message);
}
