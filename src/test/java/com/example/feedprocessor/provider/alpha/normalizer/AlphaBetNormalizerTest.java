package com.example.feedprocessor.provider.alpha.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedBetMessage;
import com.example.feedprocessor.provider.alpha.message.AlphaBetMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphaBetNormalizerTest {

    private final AlphaBetNormalizer normalizer = new AlphaBetNormalizer();

    @Test
    @DisplayName("Should support AlphaBetMessage")
    void shouldSupportAlphaBetMessage() {
        AlphaBetMessage message = new AlphaBetMessage();

        assertThat(normalizer.supports(message)).isTrue();
    }

    @Test
    @DisplayName("Should not support other types")
    void shouldNotSupportOtherTypes() {
        class UnsupportedMessage implements Message { }
        UnsupportedMessage notSupported = new UnsupportedMessage();

        assertThat(normalizer.supports(notSupported)).isFalse();
    }

    @Test
    @DisplayName("Should correctly normalize AlphaBetMessage to NormalizedBetMessage")
    void shouldNormalizeAlphaBetMessage() {
        AlphaBetMessage message = AlphaBetMessage.builder()
                .msgType("settlement")
                .eventId("ev123")
                .outcome("home")
                .build();

        NormalizedBetMessage normalized = normalizer.from(message);

        assertThat(normalized).isNotNull();
        assertThat(normalized.getEventId()).isEqualTo("ev123");
        assertThat(normalized.getWinner()).isEqualTo("home");
    }
}
