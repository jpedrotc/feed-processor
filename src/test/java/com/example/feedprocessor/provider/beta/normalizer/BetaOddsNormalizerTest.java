package com.example.feedprocessor.provider.beta.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedOddsMessage;
import com.example.feedprocessor.provider.beta.message.BetaOddsMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BetaOddsNormalizerTest {

    private final BetaOddsNormalizer normalizer = new BetaOddsNormalizer();

    @Test
    @DisplayName("Should support BetaOddsMessage")
    void shouldSupportBetaOddsMessage() {
        BetaOddsMessage message = BetaOddsMessage.builder().build();

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
    @DisplayName("Should correctly normalize BetaOddsMessage to NormalizedOddsMessage")
    void shouldNormalizeAlphaBetMessage() {
        BetaOddsMessage.Odds values = BetaOddsMessage.Odds.builder()
                .home(1.5)
                .draw(3.0)
                .away(5.0)
                .build();
        BetaOddsMessage message = BetaOddsMessage.builder()
                .type("odds_update")
                .eventId("ev123")
                .odds(values)
                .build();

        NormalizedOddsMessage normalized = normalizer.from(message);

        assertThat(normalized).isNotNull();
        assertThat(normalized.getEventId()).isEqualTo("ev123");
        assertThat(normalized.getHomeOdds()).isEqualTo(1.5);
        assertThat(normalized.getDrawOdds()).isEqualTo(3.0);
        assertThat(normalized.getAwayOdds()).isEqualTo(5.0);
    }
}