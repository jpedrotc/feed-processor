package com.example.feedprocessor.provider.alpha.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class AlphaBetMessage extends AlphaMessage {
    private String outcome;
}
