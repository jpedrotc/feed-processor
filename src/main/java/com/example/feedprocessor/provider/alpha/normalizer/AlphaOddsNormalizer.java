package com.example.feedprocessor.provider.alpha.normalizer;

import com.example.feedprocessor.domain.Message;
import com.example.feedprocessor.domain.NormalizedOddsMessage;
import com.example.feedprocessor.normalization.MessageNormalizer;
import com.example.feedprocessor.provider.alpha.message.AlphaOddsMessage;
import org.springframework.stereotype.Component;

@Component
public class AlphaOddsNormalizer implements MessageNormalizer<AlphaOddsMessage, NormalizedOddsMessage> {
    @Override
    public boolean supports(Message message) {
        return message instanceof AlphaOddsMessage;
    }

    @Override
    public NormalizedOddsMessage from(AlphaOddsMessage msg) {
        return NormalizedOddsMessage.builder()
                .eventId(msg.getEventId())
                .homeOdds(msg.getValues().getHome())
                .drawOdds(msg.getValues().getDraw())
                .awayOdds(msg.getValues().getAway())
                .build();
    }
}
