package com.example.feedprocessor.provider.alpha.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedBetMessage;
import com.example.feedprocessor.normalization.MessageNormalizer;
import com.example.feedprocessor.provider.alpha.message.AlphaBetMessage;
import org.springframework.stereotype.Component;

@Component
public class AlphaBetNormalizer implements MessageNormalizer<AlphaBetMessage, NormalizedBetMessage> {
    @Override
    public boolean supports(Message message) {
        return message instanceof AlphaBetMessage;
    }

    @Override
    public NormalizedBetMessage from(AlphaBetMessage msg) {
        return NormalizedBetMessage.builder()
                .eventId(msg.getEventId())
                .winner(msg.getOutcome())
                .build();
    }
}
