package com.example.feedprocessor.provider.beta;

import com.example.feedprocessor.normalization.MessageProcessor;
import com.example.feedprocessor.provider.beta.message.BetaMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider-beta")
public class BetaProviderController {

    private final MessageProcessor messageProcessorService;

    public BetaProviderController(MessageProcessor messageProcessingService) {
        this.messageProcessorService = messageProcessingService;
    }

    @PostMapping("/feed")
    public ResponseEntity<Void> processFeed(@RequestBody BetaMessage message) {
        messageProcessorService.process(message);
        return ResponseEntity.accepted().build();
    }
}