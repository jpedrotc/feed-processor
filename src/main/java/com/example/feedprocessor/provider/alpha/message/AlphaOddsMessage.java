package com.example.feedprocessor.provider.alpha.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class AlphaOddsMessage extends AlphaMessage {
    private Values values;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Values {
        @JsonProperty("1")
        private double home;
        @JsonProperty("X")
        private double draw;
        @JsonProperty("2")
        private double away;
    }
}
