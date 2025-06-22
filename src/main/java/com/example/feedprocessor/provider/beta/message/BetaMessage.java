package com.example.feedprocessor.provider.beta.message;

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
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BetaOddsMessage.class, name = "ODDS"),
        @JsonSubTypes.Type(value = BetaBetMessage.class, name = "SETTLEMENT")
})
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BetaMessage implements Message {
    private String type;
    @JsonProperty("event_id")
    private String eventId;
}

