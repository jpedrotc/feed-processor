package com.example.feedprocessor.provider.beta.message;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class BetaOddsMessage extends BetaMessage {
    private Odds odds;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Odds {
        private double home;
        private double draw;
        private double away;
    }
}
