package com.example.feedprocessor.normalization;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedMessage;
import com.example.feedprocessor.infrastructure.QueuePublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageProcessorImpl implements MessageProcessor {

    private final List<MessageNormalizer<?, ?>> normalizers;
    private final QueuePublisher queuePublisher;

    public MessageProcessorImpl(List<MessageNormalizer<?, ?>> normalizers, QueuePublisher queuePublisher) {
        this.normalizers = normalizers;
        this.queuePublisher = queuePublisher;
    }

    public void process(Message message) {
        NormalizedMessage normalized = normalizeMessage(message);
        queuePublisher.publish(normalized);
    }

    @SuppressWarnings("unchecked")
    private NormalizedMessage normalizeMessage(Message message) {
        return normalizers.stream()
                .filter(normalizer -> normalizer.supports(message))
                .findAny()
                .map(normalizer -> ((MessageNormalizer<Message, NormalizedMessage>) normalizer).from(message))
                .orElseThrow(() ->
                        new IllegalArgumentException("No normalizer configured for given message: " +
                                message.getClass().getSimpleName()));
    }
}
