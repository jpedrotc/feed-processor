package com.example.feedprocessor.provider.beta;

import com.example.feedprocessor.normalization.MessageProcessor;
import com.example.feedprocessor.provider.beta.message.BetaBetMessage;
import com.example.feedprocessor.provider.beta.message.BetaOddsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BetaProviderController.class)
class BetaProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageProcessor messageProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 202 Accepted and delegate message to processor when receiving BetaBetMessage")
    void shouldProcessFeedSuccessfullyForBetaBetMessage() throws Exception {
        BetaBetMessage message = BetaBetMessage.builder()
                .type("SETTLEMENT")
                .eventId("ev123")
                .result("away")
                .build();

        mockMvc.perform(post("/provider-beta/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isAccepted());

        verify(messageProcessor).process(message);
    }

    @Test
    @DisplayName("Should return 202 Accepted and delegate message to processor when receiving BetaOddsMessage")
    void shouldProcessFeedSuccessfullyForBetaOddsMessage() throws Exception {
        BetaOddsMessage.Odds values = BetaOddsMessage.Odds.builder()
                .home(1.5)
                .draw(3.0)
                .away(5.0)
                .build();
        BetaOddsMessage message = BetaOddsMessage.builder()
                .type("ODDS")
                .eventId("ev123")
                .odds(values)
                .build();

        mockMvc.perform(post("/provider-beta/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isAccepted());

        verify(messageProcessor).process(message);
    }

    @Test
    @DisplayName("Should return 400 Bad Request when receiving invalid message type")
    void shouldFailWithBadRequestForInvalidMessageType() throws Exception {
        BetaBetMessage message = BetaBetMessage.builder()
                .type("invalid")
                .build();

        mockMvc.perform(post("/provider-beta/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(messageProcessor);
    }
}