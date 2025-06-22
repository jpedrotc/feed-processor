package com.example.feedprocessor.provider.beta.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedBetMessage;
import com.example.feedprocessor.provider.beta.message.BetaBetMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BetaBetNormalizerTest {

    private final BetaBetNormalizer normalizer = new BetaBetNormalizer();

    @Test
    @DisplayName("Should support BetaBetMessage")
    void shouldSupportBetaBetMessage() {
        BetaBetMessage message = BetaBetMessage.builder().build();

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
    @DisplayName("Should correctly normalize BetaBetMessage to NormalizedBetMessage")
    void shouldNormalizeBetaBetMessage() {
        BetaBetMessage message = BetaBetMessage.builder()
                .eventId("ev123")
                .result("home")
                .build();

        NormalizedBetMessage normalized = normalizer.from(message);

        assertThat(normalized).isNotNull();
        assertThat(normalized.getEventId()).isEqualTo("ev123");
        assertThat(normalized.getWinner()).isEqualTo("home");
    }
}