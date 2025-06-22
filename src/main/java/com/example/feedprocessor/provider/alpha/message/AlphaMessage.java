package com.example.feedprocessor.provider.alpha.message;

import com.example.feedprocessor.domain.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "msg_type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlphaOddsMessage.class, name = "odds_update"),
        @JsonSubTypes.Type(value = AlphaBetMessage.class, name = "settlement")
})
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class AlphaMessage implements Message {
    @JsonProperty("msg_type")
    private String msgType;
    @JsonProperty("event_id")
    private String eventId;
}
