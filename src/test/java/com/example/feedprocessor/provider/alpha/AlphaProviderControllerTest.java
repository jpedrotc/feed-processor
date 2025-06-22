package com.example.feedprocessor.provider.alpha;

import com.example.feedprocessor.normalization.MessageProcessor;
import com.example.feedprocessor.provider.alpha.message.AlphaBetMessage;
import com.example.feedprocessor.provider.alpha.message.AlphaOddsMessage;
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

@WebMvcTest(controllers = AlphaProviderController.class)
class AlphaProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageProcessor messageProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return 202 Accepted and delegate message to processor when receiving AlphaBetMessage")
    void shouldProcessFeedSuccessfullyForAlphaBetMessage() throws Exception {
        AlphaBetMessage message = AlphaBetMessage.builder()
                .msgType("settlement")
                .eventId("ev123")
                .outcome("win")
                .build();

        mockMvc.perform(post("/provider-alpha/feed").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isAccepted());

        verify(messageProcessor).process(message);
    }

    @Test
    @DisplayName("Should return 202 Accepted and delegate message to processor when receiving AlphaOddsMessage")
    void shouldProcessFeedSuccessfullyForAlphaOddsMessage() throws Exception {
        AlphaOddsMessage.Values values = AlphaOddsMessage.Values.builder()
                .home(1.5)
                .draw(3.0)
                .away(5.0)
                .build();
        AlphaOddsMessage message = AlphaOddsMessage.builder()
                .msgType("odds_update")
                .eventId("ev123").
                values(values)
                .build();

        mockMvc.perform(post("/provider-alpha/feed").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isAccepted());

        verify(messageProcessor).process(message);
    }

    @Test
    @DisplayName("Should return 400 Bad Request when receiving invalid message type")
    void shouldFailWithBadRequestForInvalidMessageType() throws Exception {
        AlphaBetMessage message = AlphaBetMessage.builder()
                .msgType("invalid")
                .build();

        mockMvc.perform(post("/provider-alpha/feed").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(messageProcessor);
    }
}
