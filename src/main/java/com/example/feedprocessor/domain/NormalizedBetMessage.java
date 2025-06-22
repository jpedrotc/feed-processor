package com.example.feedprocessor.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class NormalizedBetMessage implements NormalizedMessage {
    @Builder.Default
    private NormalizedMessageType type = NormalizedMessageType.BET_SETTLEMENT;
    private String eventId;
    private String winner;
}

