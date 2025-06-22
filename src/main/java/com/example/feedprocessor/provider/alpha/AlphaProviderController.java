package com.example.feedprocessor.provider.alpha;

import com.example.feedprocessor.normalization.MessageProcessor;
import com.example.feedprocessor.provider.alpha.message.AlphaMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider-alpha")
public class AlphaProviderController {

    private final MessageProcessor messageProcessorService;

    public AlphaProviderController(MessageProcessor messageProcessingService) {
        this.messageProcessorService = messageProcessingService;
    }

    @PostMapping("/feed")
    public ResponseEntity<Void> processFeed(@RequestBody AlphaMessage message) {
        messageProcessorService.process(message);
        return ResponseEntity.accepted().build();
    }
}