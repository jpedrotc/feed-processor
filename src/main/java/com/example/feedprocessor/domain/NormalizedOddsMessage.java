package com.example.feedprocessor.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class NormalizedOddsMessage implements NormalizedMessage {
    @Builder.Default
    private NormalizedMessageType type = NormalizedMessageType.ODDS_CHANGE;
    private String eventId;
    private double homeOdds;
    private double drawOdds;
    private double awayOdds;
}

