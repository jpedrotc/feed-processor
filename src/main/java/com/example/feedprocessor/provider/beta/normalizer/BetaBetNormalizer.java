package com.example.feedprocessor.provider.beta.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedBetMessage;
import com.example.feedprocessor.normalization.MessageNormalizer;
import com.example.feedprocessor.provider.beta.message.BetaBetMessage;
import org.springframework.stereotype.Component;

@Component
public class BetaBetNormalizer implements MessageNormalizer<BetaBetMessage, NormalizedBetMessage> {
    @Override
    public boolean supports(Message message) {
        return message instanceof BetaBetMessage;
    }

    @Override
    public NormalizedBetMessage from(BetaBetMessage msg) {
        return NormalizedBetMessage.builder()
                .eventId(msg.getEventId())
                .winner(msg.getResult())
                .build();
    }
}
