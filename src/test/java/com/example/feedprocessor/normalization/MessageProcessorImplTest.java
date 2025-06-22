package com.example.feedprocessor.normalization;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedMessage;
import com.example.feedprocessor.infrastructure.QueuePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MessageProcessorImplTest {

    private QueuePublisher queuePublisher;
    private MessageNormalizer<Message, NormalizedMessage> normalizer;
    private MessageProcessorImpl messageProcessor;

    @BeforeEach
    void setup() {
        queuePublisher = mock(QueuePublisher.class);
        normalizer = mock(MessageNormalizer.class);
        messageProcessor = new MessageProcessorImpl(List.of(normalizer), queuePublisher);
    }

    @Test
    @DisplayName("Should normalize and publish message when normalizer is found")
    void shouldNormalizeAndPublishMessage() {
        Message inputMessage = mock(Message.class);
        NormalizedMessage normalizedMessage = mock(NormalizedMessage.class);
        when(normalizer.supports(inputMessage)).thenReturn(true);
        when(normalizer.from(inputMessage)).thenReturn(normalizedMessage);

        messageProcessor.process(inputMessage);

        verify(queuePublisher).publish(normalizedMessage);
    }

    @Test
    @DisplayName("Should throw exception when no normalizer supports the message")
    void shouldThrowIfNoNormalizerSupportsMessage() {
        Message unsupportedMessage = mock(Message.class);
        when(normalizer.supports(unsupportedMessage)).thenReturn(false);

        assertThatThrownBy(() -> messageProcessor.process(unsupportedMessage))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No normalizer configured for given message:");
        verifyNoInteractions(queuePublisher);
    }
}

