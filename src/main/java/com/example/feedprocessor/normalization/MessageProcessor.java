package com.example.feedprocessor.normalization;

import com.example.feedprocessor.domain.Message;

public interface MessageProcessor {

    void process(Message message);
}
