package com.example.feedprocessor.provider.alpha.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedOddsMessage;
import com.example.feedprocessor.provider.alpha.message.AlphaOddsMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlphaOddsNormalizerTest {

    private final AlphaOddsNormalizer normalizer = new AlphaOddsNormalizer();

    @Test
    @DisplayName("Should support AlphaOddsMessage")
    void shouldSupportAlphaOddsMessage() {
        AlphaOddsMessage message = AlphaOddsMessage.builder().build();

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
    @DisplayName("Should correctly normalize AlphaOddsMessage to NormalizedOddsMessage")
    void shouldNormalizeAlphaBetMessage() {
        AlphaOddsMessage.Values values = AlphaOddsMessage.Values.builder()
                .home(1.5)
                .draw(3.0)
                .away(5.0)
                .build();
        AlphaOddsMessage message = AlphaOddsMessage.builder()
                .msgType("odds_update")
                .eventId("ev123")
                .values(values)
                .build();

        NormalizedOddsMessage normalized = normalizer.from(message);

        assertThat(normalized).isNotNull();
        assertThat(normalized.getEventId()).isEqualTo("ev123");
        assertThat(normalized.getHomeOdds()).isEqualTo(1.5);
        assertThat(normalized.getDrawOdds()).isEqualTo(3.0);
        assertThat(normalized.getAwayOdds()).isEqualTo(5.0);
    }
}