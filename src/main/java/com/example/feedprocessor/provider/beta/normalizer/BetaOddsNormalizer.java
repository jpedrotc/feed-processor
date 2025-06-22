package com.example.feedprocessor.provider.beta.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedOddsMessage;
import com.example.feedprocessor.normalization.MessageNormalizer;
import com.example.feedprocessor.provider.beta.message.BetaOddsMessage;
import org.springframework.stereotype.Component;

@Component
public class BetaOddsNormalizer implements MessageNormalizer<BetaOddsMessage, NormalizedOddsMessage> {
    @Override
    public boolean supports(Message message) {
        return message instanceof BetaOddsMessage;
    }

    @Override
    public NormalizedOddsMessage from(BetaOddsMessage msg) {
        return NormalizedOddsMessage.builder()
                .eventId(msg.getEventId())
                .homeOdds(msg.getOdds().getHome())
                .drawOdds(msg.getOdds().getDraw())
                .awayOdds(msg.getOdds().getAway())
                .build();
    }
}
